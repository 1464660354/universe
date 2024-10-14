package universe.content;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

import universe.UnPal;
import universe.Untype.ScorpionAbility;
import universe.Untype.ScorpionBulletType;

import static arc.input.KeyCode.r;

public class UnUnitTypes {
    public static UnitType dragonClaws, scorpion;

    public static void load() {

        /*case "flying" -> UnitEntity::create;
        case "mech" -> MechUnit::create;
        case "legs" -> LegsUnit::create;
        case "naval" -> UnitWaterMove::create;
        case "payload" -> PayloadUnit::create;
        case "missile" -> TimedKillUnit::create;
        case "tank" -> TankUnit::create;
        case "hover" -> ElevationMoveUnit::create;
        case "tether" -> BuildingTetherPayloadUnit::create;
        case "crawl" -> CrawlUnit::create;*/

        dragonClaws = new UnitType("dragon-Claws") {{
            constructor = MechUnit::create;
            speed = 0.7f;
            hitSize = 8f;
            health = 200;
            weapons.add(new Weapon(name + "-claws") {{
                reload = 10f;
                x = 4f;
                y = 2f;
                top = false;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(2.7f, 14) {{
                    width = 7f;
                    height = 9f;
                    lifetime = 90f;
                }};
            }});
        }};

        scorpion = new UnitType("scorpion") {{
            constructor = LegsUnit::create;
            drag = 0.3f;
            speed = 0.7f;
            hitSize = 26f;
            health = 66000;
            armor = 20f;
            lightRadius = 200f;
            rotateSpeed = 1.2f;
            drownTimeMultiplier = 3f;
            legCount = 6;
            legMoveSpace = 0.6f;
            legPairOffset = 5;
            legLength = 50f;
            legExtension = -15;
            legBaseOffset = 6f;
            stepShake = 1f;
            legLengthScl = 0.8f;
            rippleScale = 3f;
            legSpeed = 0.3f;
            ammoType = new ItemAmmoType(Items.graphite, 8);
            legSplashDamage = 100;
            legSplashRange = 80;
            hovering = true;
            shadowElevation = 0.8f;
            groundLayer = Layer.legUnit;
            abilities.add(new ScorpionAbility(40f, 65f, 180f) {{
                statusDuration = 60f * 10f;
                maxTargets = 5;
            }});
            weapons.add(new Weapon(name + "-large-scorpion-mount") {{
                y = -3f;
                x = 15f;
                shootY = 15f;
                reload = 120;
                shake = 5f;
                recoil = 5f;
                rotateSpeed = 0.5f;
                ejectEffect = Fx.casing3;
                shootSound = Sounds.artillery;
                rotate = true;
                shadow = 10f;
                rotationLimit = 50f;
                shoot = new ShootSpread(4, 10f);
                bullet = new ArtilleryBulletType(5f, 150) {{
                    hitEffect = Fx.sapExplosion;
                    knockback = 0.8f;
                    lifetime = 30f;
                    width = height = 20f;
                    collidesTiles = collides = true;
                    ammoMultiplier = 4f;
                    splashDamageRadius = 120f;
                    splashDamage = 100f;
                    backColor = UnPal.scoBulletBack;
                    frontColor = lightningColor = UnPal.scoBullet;
                    lightning = 5;
                    lightningLength = 10;
                    smokeEffect = Fx.shootBigSmoke2;
                    hitShake = 5f;
                    lightRadius = 10f;
                    lightColor = UnPal.sco;
                    lightOpacity = 0.8f;
                    status = UnStatusEffects.erosion;
                    statusDuration = 60f * 10;
                }};
            }});
            weapons.add(new Weapon(name + "-cannon") {{
                y = -14f;
                x = 0f;
                shootX = 3.5f;
                shootY = 20f;
                mirror = true;
                shake = 2f;
                reload = 60f;
                recoil = 3f;
                rotateSpeed = 0.3f;
                rotationLimit = 60f;
                rotate = true;
                shadow = 10f;
                shootSound = Sounds.artillery;
                for(int i = 0; i < 3; i++){
                    int fi = i;
                    parts.add(new RegionPart("-blade"){{
                        under = true;
                        layerOffset = -0.001f;
                        heatColor = UnPal.sco;
                        heatProgress = PartProgress.heat.add(0.2f).min(PartProgress.warmup);
                        progress = PartProgress.warmup.blend(PartProgress.reload, 0.1f);
                        x = 12;
                        y = 0f;
                        moveY = 1f - fi * 1f;
                        moveX = fi * 0.3f;
                        moveRot = -45f - fi * 20f;

                        moves.add(new PartMove(PartProgress.reload.inv().mul(1.8f).inv().curve(fi / 5f, 0.2f), 0f, 0f, 36f));
                    }});
                }
                bullet = new ScorpionBulletType(7.5f, 400){{
                    hitSize = 16f;
                    width = 30f;
                    height = 40f;
                    lifetime = 30f;
                    shootEffect = UnFx.none;
                    ammoMultiplier = 1;
                    pierceCap = 1;
                    collidesTiles = collides = true;
                    splashDamageRadius = 150f;
                    splashDamage = 350f;
                    pierce = true;
                    pierceBuilding = true;
                    hitColor = backColor = trailColor = UnPal.scoBulletBack;
                    frontColor = UnPal.scoBullet;
                    trailWidth = 4f;
                    trailLength = 6;
                    hitEffect = Fx.sapExplosion;
                    despawnEffect = Fx.hitBulletColor;
                    buildingDamageMultiplier = 1.5f;
                    status = UnStatusEffects.erosion;
                    statusDuration = 60f * 10;
                }};
            }});
        }};

    }
}