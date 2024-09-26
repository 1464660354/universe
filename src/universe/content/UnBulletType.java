//package universe.content;
//
//import mindustry.entities.Damage;
//import mindustry.gen.Bullet;
//
//// UnBulletType类用于处理无子弹类型的伤害特性。
//public class UnBulletType {
//    public float damageRadius = -1f; // 爆炸伤害半径
//    public float splash = 0f; // 爆炸伤害
//    public boolean damagePierce = false; // 是否穿透伤害
//    public boolean collidesAir = true; // 是否与空气碰撞
//    public boolean collidesGround = true; // 是否与地面碰撞
//    public boolean scaledSplashDamage = false; // 是否按比例缩放爆炸伤害
//    // 处理氧化效果的方法，计算并应用伤害
//
//    public boolean oxidation(Bullet b, float x, float y){
//        if(damageRadius > 0 && !b.absorbed){
//            Damage.damage(b.team, x, y, damageRadius, splash *
//                            b.damageMultiplier(), damagePierce, collidesAir, collidesGround,
//                    scaledSplashDamage, b);
//        }
//        return false;
//    }
//}


