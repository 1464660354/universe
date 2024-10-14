package universe.Untype;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;
import universe.UnPal;
import universe.content.UnFx;

import static arc.graphics.Gl.alpha;
import static arc.graphics.g2d.Draw.color;
import static mindustry.content.Fx.rand;

public class ScorpionBulletType extends BulletType {

    public Color backColor = UnPal.scoBulletBack,
            frontColor = UnPal.scoBullet;
    public Color mixColorFrom = new Color(1f, 1f, 1f, 0f),
            mixColorTo = new Color(1f, 1f, 1f, 0f);
    public float width = 17f, height = 25f;
    public float shrinkX = 0f, shrinkY = 0.5f;
    public Interp shrinkInterp = Interp.linear;
    public float spin = 0, rotationOffset = 0f;
    public String sprite;
    public @Nullable String backSprite;

    public TextureRegion backRegion;
    public TextureRegion frontRegion;


    public ScorpionBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage);
        this.sprite = bulletSprite;
        collidesTiles = false;
        collides = false;
        collidesAir = false;
        scaleLife = true;
    }
    public ScorpionBulletType(float speed, float damage){
        this(speed, damage, "bullet");
    }
    public ScorpionBulletType(){
        this(1f, 1f, "bullet");
    }

    @Override
    public void load(){
        super.load();

        backRegion = Core.atlas.find(backSprite == null ? (sprite + "-back") : backSprite);
        frontRegion = Core.atlas.find(sprite);
    }

    @Override
    public void draw(Bullet b){
        super.draw(b);
        int quantity = 5;
        float shrink = shrinkInterp.apply(b.fout());
        float height = this.height * ((1f - shrinkY) + shrinkY * shrink);
        float width = this.width * ((1f - shrinkX) + shrinkX * shrink);
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f) + rotationOffset;

        Color mix = Tmp.c1.set(mixColorFrom).lerp(mixColorTo, b.fin());
        Draw.mixcol(mix, mix.a);
        if(backRegion.found()){
            Draw.color(backColor);
            Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() + offset);
        }
        Draw.color(frontColor);
        Draw.rect(frontRegion, b.x, b.y, width, height, b.rotation() + offset);
        Draw.reset();

    }

}
