package universe.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

public class UnFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();

    public static final Effect

        bronzerFire = new Effect(100f, e -> {
            color(Color.valueOf("96CDCD"), Color.valueOf("668B8B"), e.fin());
            randLenVectors(e.id, 10, e.finpow() * 23f, e.rotation, 25f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 6f + 0.3f);
    });
        }),

        tetanus = new Effect(40f, e -> {
            color(Color.valueOf("96CDCD"), Color.valueOf("668B8B"), e.fin());
            randLenVectors(e.id, 3, 2f + e.fin() * 7f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 1.4f);
            });
        }),

        bronzerBlastExplosion = new Effect(30, e -> {
            color(Color.valueOf("96CDCD"));

            e.scaled(6, i -> {
                stroke(3f * i.fout());
                Lines.circle(e.x, e.y, 3f + i.fin() * 15f);
            });

            color(Color.gray);

            randLenVectors(e.id, 5, 2f + 23f * e.finpow(), (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f);
            });

            color(Color.valueOf("668B8B"));
            stroke(e.fout());

            randLenVectors(e.id + 1, 4, 1f + 23f * e.finpow(), (x, y) -> {
                lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
            });

            Drawf.light(e.x, e.y, 45f, Pal.missileYellowBack, 0.8f * e.fout());
        }),

        bronzerIncendTrail = new Effect(60, e -> {
            color(Color.valueOf("96CDCD"), Color.valueOf("668B8B"), e.fin());
            Fill.circle(e.x, e.y, e.rotation * e.fout());
        }),

        hyperPlasia = new Effect(60f, e -> {
            color(Color.valueOf("98FB98"), Color.valueOf("32CD32"), e.fin());
            randLenVectors(e.id, 3, 2f + e.fin() * 7f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 1.4f);
            });
        }),

        plasmahit = new Effect(120, e -> {
            color(Color.valueOf("96CDCD"), Color.valueOf("668B8B"), e.fin());
            stroke(e.fout() * 0.3f);

            randLenVectors(e.id, 18, e.finpow() * 27f, e.rotation, 360f, (x, y) -> {
                float ang = Mathf.angle(x, y);
                lineAngle(e.x + x, e.y + y, ang, e.fout() * 4 + 1f);
            });
        }),

        plasmaSmoke = new Effect(45f, e -> {
            color(Color.valueOf("96CDCD"), Color.lightGray, Color.gray, e.fin());

            randLenVectors(e.id, 9, e.finpow() * 10f, e.rotation, 20f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fout() * 1.2f + 0.2f);
            });
        }),

        plasmatrail = new Effect(20f, e -> {
            color(Color.valueOf("96CDCD"), Color.valueOf("668B8B"), e.fin());
            for(int s : Mathf.signs){
                Drawf.tri(e.x, e.y, 1f, 5f * e.fslope(), e.rotation + 90f*s);
            }
        }),

        none = new Effect(0, 0f, e -> {});
}
