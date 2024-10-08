package universe;

import arc.util.Nullable;
import mindustry.entities.pattern.ShootPattern;

public class Unshoot extends ShootPattern {
    public int barrels = 3;
    public float spread = 2f;
    public int barrelOffset = 0;
    public Unshoot(int shots, float spread){
        this.spread = spread;
    }
    public Unshoot(){
    }

    @Override
    public void shoot(int totalShots, BulletHandler handler, @Nullable Runnable barrelIncrementer){
        handler.shoot(-40, 40, 0f, 0);
        handler.shoot(40, 40, 0f, 0);
        if(barrelIncrementer != null) barrelIncrementer.run();
    }
}