package universe;

import arc.Core;
import arc.Events;
import arc.Graphics;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.Interval;
import arc.util.Timer;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.ItemSeq;
import mindustry.type.ItemStack;
import mindustry.type.Sector;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.campaign.LaunchPad;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;


import java.util.TimerTask;

import static mindustry.Vars.*;
import static universe.UnAnnotations.*;

public class UnLaunchPad extends LaunchPad {
    /** Time inbetween launches. */
    public float launchTime = 1f;
    public Sound launchSound = Sounds.none;
    public @Load("@-light") TextureRegion lightRegion;
    public @Load(value = "@-pod", fallback = "launchpod") TextureRegion podRegion;
    public Color lightColor = Color.valueOf("eab678");

    public UnLaunchPad(String name){
        super(name);
        hasItems = true;
        solid = true;
        update = true;
        configurable = true;
        flags = EnumSet.of(BlockFlag.launchPad);
    }

    public class UnLaunchPadBuild extends LaunchPadBuild {
        public float launchCounter;
    }
    static abstract class LaunchPayloadComp implements Drawc, Timedc, Teamc{
        @Import float x,y;

        Seq<ItemStack> stacks = new Seq<>();
        transient Interval in = new Interval();

        @Override
        public void draw(){
            float alpha = fout(Interp.pow5Out);
            float scale = (1f - alpha) * 1.3f + 1f;
            float cx = cx(), cy = cy();
            float rotation = fin() * (130f + Mathf.randomSeedRange(id(), 50f));

            Draw.z(Layer.effect + 0.001f);

            Draw.color(Pal.engine);

            float rad = 0.2f + fslope();

            Fill.light(cx, cy, 10, 25f * (rad + scale-1f), Tmp.c2.set(Pal.engine).a(alpha), Tmp.c1.set(Pal.engine).a(0f));

            Draw.alpha(alpha);
            for(int i = 0; i < 4; i++){
                Drawf.tri(cx, cy, 6f, 40f * (rad + scale-1f), i * 90f + rotation);
            }

            Draw.color();

            Draw.z(Layer.weather - 1);

            TextureRegion region = blockOn() instanceof mindustry.world.blocks.campaign.LaunchPad p ? p.podRegion : Core.atlas.find("launchpod");
            scale *= region.scl();
            float rw = region.width * scale, rh = region.height * scale;

            Draw.alpha(alpha);
            Draw.rect(region, cx, cy, rw, rh, rotation);

            Tmp.v1.trns(225f, fin(Interp.pow3In) * 250f);

            Draw.z(Layer.flyingUnit + 1);
            Draw.color(0, 0, 0, 0.22f * alpha);
            Draw.rect(region, cx + Tmp.v1.x, cy + Tmp.v1.y, rw, rh, rotation);

            Draw.reset();
        }

        float cx(){
            return x + fin(Interp.pow2In) * (12f + Mathf.randomSeedRange(id() + 3, 4f));
        }

        float cy(){
            return y + fin(Interp.pow5In) * (100f + Mathf.randomSeedRange(id() + 2, 30f));
        }

        @Override
        public void update(){
            float r = 3f;
            if(in.get(4f - fin()*2f)){
                Fx.rocketSmoke.at(cx() + Mathf.range(r), cy() + Mathf.range(r), fin());
            }
        }

        @Override
        public void remove(){
            if(!state.isCampaign()) return;

            Sector destsec = state.rules.sector.info.getRealDestination();

            //actually launch the items upon removal
            if(team() == state.rules.defaultTeam){
                if(destsec != null && (destsec != state.rules.sector || net.client())){
                    ItemSeq dest = new ItemSeq();

                    for(ItemStack stack : stacks){
                        dest.add(stack);

                        //update export
                        state.rules.sector.info.handleItemExport(stack);
                        Events.fire(new EventType.LaunchItemEvent(stack));
                    }

                    if(!net.client()){
                        destsec.addItems(dest);
                    }
                }
            }
        }
    }
}
