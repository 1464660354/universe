package universe.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import static arc.graphics.Gl.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

public class UnFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final Effect

    //region status effects
    tetanus = new Effect(40f, e -> {
        color(Color.valueOf("96CDCD"), Color.valueOf("668B8B"), e.fin());
        randLenVectors(e.id, 3, 2f + e.fin() * 7f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 1.4f);
        });
    }),
    hyperPlasia = new Effect(600f, e -> {
        color(Color.valueOf("98FB98"), Color.valueOf("32CD32"), e.fin());
        randLenVectors(e.id, 3, 2f + e.fin() * 7f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 1.4f);
        });
    }),
    //endregion

    //region turret effects
    bronzerShoot = new Effect(100f, e -> {
        color(Color.valueOf("96CDCD"), Color.valueOf("668B8B"), e.fin());
        randLenVectors(e.id, 10, e.finpow() * 23f, e.rotation, 25f, (x, y) -> {
        Fill.circle(e.x + x, e.y + y, e.fout() * 6f + 0.3f);
});
    }),
    bronzerHit = new Effect(30, e -> {
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
    bronzerTrail = new Effect(60, e -> {
                color(Color.valueOf("96CDCD"), Color.valueOf("668B8B"), e.fin());
                Fill.circle(e.x, e.y, e.rotation * e.fout());
            }),

    plasmaHit = new Effect(120, e -> {
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
    plasmaTrail = new Effect(20f, e -> {
        color(Color.valueOf("96CDCD"), Color.valueOf("668B8B"), e.fin());
        for(int s : Mathf.signs){
            Drawf.tri(e.x, e.y, 1f, 5f * e.fslope(), e.rotation + 90f*s);
        }
    }),
    plasmaShoot = new Effect(40f, 40f, e -> {
        stroke(1.1f * e.fout());
        color(Color.valueOf("96CDCD"), e.color, e.fin());
        Lines.beginLine();
        Lines.linePoint(e.x, e.y);
        rand.setSeed(e.id);
        int len = 6;
        int linenum = 3;
            for(int j = 0; j < linenum; j++){
                Tmp.v1.setToRandomDirection(rand).scl(len);
                e.x += Tmp.v1.x;
                e.y += Tmp.v1.y;
                Lines.linePoint(e.x, e.y);
            }
        Lines.endLine();
    }),

    proilHit = new Effect(60f, e -> {
        color(Color.valueOf("98FB98"), Color.valueOf("32CD32"), e.fin());
        randLenVectors(e.id, 3, 2f + e.fin() * 7f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 1.4f);
        });
    }),

    //endregion


    //飞行尾焰
    AEffect =new Effect(100f, e -> {
                int divisions = 5,realLength = 10;
                float width = 3.7f, oscScl = 1.2f, oscMag = 0.02f;
                float[] lengthWidthPans = {
                        1.12f, 1.3f, 0.32f,
                        1f, 1f, 0.3f,
                        0.8f, 0.9f, 0.2f,
                        0.5f, 0.8f, 0.15f,
                        0.25f, 0.7f, 0.1f,
                };
                float sin = Mathf.sin(Time.time, oscScl, oscMag);;
                Color[] colors = {
                        Color.valueOf("eb7abe").a(0.55f),
                        Color.valueOf("e189f5").a(0.7f),
                        Color.valueOf("907ef7").a(0.8f),
                        Color.valueOf("91a4ff"),
                        Color.white.cpy()};
                for(int i = 0; i < 4; i++){
                    Draw.color(colors[i].write(Tmp.c1).mul(0.9f).mul(1f + Mathf.absin(Time.time, 1f, 0.1f)));
                    Drawf.flame(e.x , e.y - 40, divisions, e.rotation+180,
                            realLength * lengthWidthPans[i * 3] * (1f - sin),
                            width * lengthWidthPans[i * 3 + 1] * (1f + sin),
                            lengthWidthPans[i * 3 + 2]
                    );
                }
            }),
    //双重光环
    BEffect = new Effect(61f, e -> {
        float x = 0,y = 0,effectRadius = 5f,blinkScl = 20f,blinkSize = 0.1f,
                sectors = 5,rotateSpeed = 5f,sectorRad = 0.14f, range=60,curStroke =1;
        Tmp.v1.trns(e.rotation , x, y - 4).add(e.x, e.y);
        float rx = Tmp.v1.x, ry = Tmp.v1.y;
        float orbRadius = effectRadius * (1f + Mathf.absin(blinkScl, blinkSize));
        //内圈光环
        Lines.stroke((0.7f + Mathf.absin(blinkScl, 0.7f)), Color.valueOf("96CDCD"));
        for(int i = 0; i < sectors; i++){
            float rot = e.rotation + i * 360f/sectors - Time.time * rotateSpeed;
            Lines.arc(rx, ry, orbRadius + 3f, sectorRad, rot);
        }
        //外圈光环
        Lines.stroke(Lines.getStroke() * curStroke);
        if(curStroke > 0){
            for(int i = 0; i < sectors; i++){
                float rot = e.rotation + i * 360f/sectors + Time.time * rotateSpeed;
                Lines.arc(rx, ry, range, sectorRad, rot);
            }
        }
        /*球型
        Fill.circle(rx, ry, orbRadius);
        Draw.color();
        Fill.circle(rx, ry, orbRadius / 2f);
        发光
        Drawf.light(rx, ry, range * 1.5f, Color.valueOf("668B8B"), curStroke * 0.5f);
        Draw.reset();*/
    }),
    //六边形光环
    CEffect = new Effect(45f, e -> {
                color(Color.valueOf("98FB98"), alpha);
                Lines.poly(e.x, e.y, 6, e.finpow() * 5, 0.5F);
            }),
        /*废弃案例
        Lines.arc(e.x, e.y, 2f, 100 / 200f, 5f);
        Lines.arc(e.x, e.y, 3f, 220 / 320f, 8f);
        Lines.arc(e.x, e.y, 4f, 340 / 80f, 10f);
        Draw.color(Color.valueOf("96CDCD"),e.fin());
        Lines.arc(e.x, e.y, 10f, 10f, e.finpow() * 10, 90);
        Lines.arc(e.x, e.y, 10f, 10f, e.finpow() * 10, 20);*/
    none = new Effect(0, 0f, e -> {});
}
