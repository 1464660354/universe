package universe.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.math.Rand;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class UnFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();

    public static final Effect
            none = new Effect(0, 0f, e -> {}),
    //region bronzeAncientCannon
            bronzerFire = new Effect(100f, e -> {
                color(Color.valueOf("96CDCD"), Color.valueOf("668B8B"), e.fin());
                randLenVectors(e.id, 10, e.finpow() * 23f, e.rotation, 25f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fout() * 6f + 0.3f);
        });
            }),
            bronzerblast = new Effect(60f, e -> {

            }),
            bronzertrajectory = new Effect(60f, e -> {

            });
    //endregion
}
