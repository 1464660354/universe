package universe.content;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.type.StatusEffect;

import static mindustry.content.StatusEffects.*;

public abstract class UnStatusEffects {
    public static StatusEffect tetanus,hyperPlasia,flashStrike;
    public static void load(){

        tetanus = new StatusEffect("tetanus"){{
            color = Color.valueOf("FF7F00");
            damage = 0.2f;
            effect = UnFx.tetanus;
            transitionDamage = 10f;
            init(() -> {
                opposite(wet,freezing);
                affinity(tarred, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    UnFx.bronzerShoot.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(tetanus, Math.min(time + result.time, 300f));
                });
            });
        }};

        hyperPlasia = new StatusEffect("hyperPlasia"){{
            color = Color.valueOf("dba9e0");
            damage = 0.1f;
            effect = UnFx.hyperPlasia;
            transitionDamage = 20f;
            init(() -> {
                opposite(burning,freezing,tarred);
                affinity(wet, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    UnFx.hyperPlasia.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(tetanus, Math.min(time + result.time, 600f));
                });
                affinity(shocked, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    UnFx.hyperPlasia.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(tetanus, Math.min(time + result.time, 600f));
                });
            });
        }};

        flashStrike = new StatusEffect("thunderThunder"){{

        }};

        //@Override
        boss = new StatusEffect("boss"){{
            boss.damageMultiplier = 5f;
            boss.healthMultiplier = 5f;
        }};

    }
}
