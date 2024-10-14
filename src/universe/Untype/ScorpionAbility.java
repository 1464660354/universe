package universe.Untype;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.StatusEffect;
import universe.UnPal;

import static mindustry.Vars.state;

public class ScorpionAbility extends Ability {

    private static final Seq<Healthc> all = new Seq<>();
    protected static final float descriptionWidth = 350f;
    public float damage = 1, reload = 100, range = 60;
    public Effect hitEffect = Fx.hitLaserBlast, damageEffect = Fx.chainLightning;
    public StatusEffect status = StatusEffects.electrified;
    public Sound shootSound = Sounds.spark;
    public float statusDuration = 60f * 6f;
    public float x, y;
    public boolean targetGround = true, targetAir = true, hitBuildings = true, hitUnits = true;
    public int maxTargets = 6;
    public static final int tilesize = 8;
    public boolean displayHeal = true;
    public float layer = Layer.bullet - 0.001f;
    public float sectorRad = 0.14f, rotateSpeed = 0.5f;
    public int sectors = 5;
    public Color color = UnPal.sco;
    public boolean useAmmo = true;
    protected float timer, curStroke;
    protected boolean anyNearby = false;

    ScorpionAbility(){}
    public ScorpionAbility(float damage, float reload, float range){
        this.damage = damage;
        this.reload = reload;
        this.range = range;
    }

    public String abilityStat(String stat, Object... values){
        return Core.bundle.format("ability.stat." + stat, values);
    }
    private String getBundle() {
        var type = getClass();
        return "ability." + (type.isAnonymousClass() ? type.getSuperclass() : type).getSimpleName().replace("Ability", "").toLowerCase();
    }

    @Override
    public void addStats(Table t) {
        if(displayHeal){
            t.add(Core.bundle.get(getBundle() + ".description")).wrap().width(descriptionWidth);
        }
        t.row();
        t.add(Core.bundle.format("bullet.range", Strings.autoFixed(range / tilesize, 2)));
        t.row();
        t.add(abilityStat("firingrate", Strings.autoFixed(60f / reload, 2)));
        t.row();
        t.add(abilityStat("maxtargets", maxTargets));
        t.row();
        t.add(Core.bundle.format("bullet.damage", damage));
        if(status != StatusEffects.none){
            t.row();
            t.add((status.hasEmoji() ? status.emoji() : "") + "[stat]" + status.localizedName);
        }
    }

    @Override
    public void draw(Unit unit){
        super.draw(unit);

        Draw.z(layer);
        Draw.color(UnPal.sco);
        Tmp.v1.trns(unit.rotation - 90, x, y).add(unit.x, unit.y);
        float rx = Tmp.v1.x, ry = Tmp.v1.y;
        Lines.stroke(Lines.getStroke() * curStroke);
        if(curStroke > 0){
            for(int i = 0; i < sectors; i++){
                float rot = unit.rotation + i * 360f/sectors + Time.time * rotateSpeed;
                Lines.arc(rx, ry, range, sectorRad, rot);
            }
        }

        Draw.reset();
    }

    @Override
    public void update(Unit unit){

        curStroke = Mathf.lerpDelta(curStroke, anyNearby ? 1 : 0, 0.09f);

        if((timer += Time.delta) >= reload && (!useAmmo || unit.ammo > 0 || !state.rules.unitAmmo)){
            Tmp.v1.trns(unit.rotation - 90, x, y).add(unit.x, unit.y);
            float rx = Tmp.v1.x, ry = Tmp.v1.y;
            anyNearby = false;

            all.clear();

            if(hitUnits){
                Units.nearby(null, rx, ry, range, other -> {
                    if(other != unit && other.checkTarget(targetAir, targetGround) && other.targetable(unit.team) && (other.team != unit.team || other.damaged())){
                        all.add(other);
                    }
                });
            }

            if(hitBuildings && targetGround){
                Units.nearbyBuildings(rx, ry, range, b -> {
                    if((b.team != Team.derelict || state.rules.coreCapture) && (b.team != unit.team || b.damaged())){
                        all.add(b);
                    }
                });
            }

            all.sort(h -> h.dst2(rx, ry));
            int len = Math.min(all.size, maxTargets);
            for(int i = 0; i < len; i++){
                Healthc other = all.get(i);
                var absorber = Damage.findAbsorber(unit.team, rx, ry, other.getX(), other.getY());
                if(absorber != null){
                    other = absorber;
                }

                if(((Teamc)other).team() != unit.team){
                    anyNearby = true;
                    if(other instanceof Building b){
                        b.damage(unit.team, damage * state.rules.unitDamage(unit.team));
                    }else{
                        other.damage(damage * state.rules.unitDamage(unit.team));
                    }
                    if(other instanceof Statusc s){
                        s.apply(status, statusDuration);
                    }
                    hitEffect.at(other.x(), other.y(), unit.angleTo(other), color);
                    damageEffect.at(rx, ry, 0f, color, other);
                    hitEffect.at(rx, ry, unit.angleTo(other), color);
                }
            }

            if(anyNearby){
                shootSound.at(unit);

                if(useAmmo && state.rules.unitAmmo){
                    unit.ammo --;
                }
            }

            timer = -120f;
        }
    }

}
