package universe.Untype;

import arc.util.Nullable;
import mindustry.entities.pattern.ShootPattern;

public class UnNoYaShoot extends ShootPattern {
    public int barrels = 3;
    public float spread = 2f;
    public int barrelOffset = 0;
    public UnNoYaShoot(int shots, float spread){
        this.spread = spread;
    }
    public UnNoYaShoot(){
    }

    @Override
    public void shoot(int totalShots, BulletHandler handler, @Nullable Runnable barrelIncrementer){
        handler.shoot(0, 0, 0f, 0);
        handler.shoot(-20, 10, 0f, 0);
        handler.shoot(20, 10, 0f, 0);
        if(barrelIncrementer != null) barrelIncrementer.run();
    }
}
