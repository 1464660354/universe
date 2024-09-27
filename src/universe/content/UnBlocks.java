package universe.content;


import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.StatusEffect;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawFlame;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.*;
import mindustry.type.ItemStack;
import mindustry.world.Block;

import universe.content.UnItems;

import static arc.graphics.g2d.Draw.color;
import static mindustry.content.UnitTypes.alpha;
import static mindustry.type.ItemStack.with;

public class UnBlocks {
    public static Block
            //region ore
            oreAurum,
            //endregion

            //region production
            bronzeDrillBit,
            //endregion

            //region wall
            bronzerWall,bronzerWallLarge,
            //endregion

            //region Smelter
            bronzerSmelter,
            //endregion

            //region turret
            bronzeAncientCannon,plasmaCannon,proliferationCannon,
            //endregion

            //region core
            universeCore;
            //endregion

    //load
    public static void load(){
        //region ore
//        oreAurum = new OreBlock(UnItems.aurum){{
//            oreDefault = true;
//            oreThreshold = 0.864f;
//            oreScale = 24.904762f;
//      }};
        //endregion ore

        //region production
        bronzeDrillBit = new Drill("bronze-drill-bit"){{
            requirements(Category.production, with
                    (new Object[]{UnItems.bronzer, 10}));
            tier = 3;
            drillTime = 400;
            size = 2;
            itemCapacity = 15;
            envEnabled ^= Env.space;
            consumeLiquid(Liquids.water, 0.05f).boost();
            researchCost = with(new Object[]{UnItems.bronzer, 10});
        }};

        //endregion production

        //region wall

        int wallHealthMultiplier = 4;

        bronzerWall = new Wall("bronzer-wall"){{
            requirements(Category.defense, with
                    (new Object[]{UnItems.bronzer, 10}));
            health = 95 * wallHealthMultiplier;
            size = 1;
            researchCostMultiplier = 0.1f;
            buildCostMultiplier = 1.1f;
            envDisabled |= Env.scorching;
        }};
        bronzerWallLarge = new Wall("large-bronzer-wall"){{
            requirements(Category.defense, with
                    (new Object[]{UnItems.bronzer, 40}));
            health = 95 * 4 * wallHealthMultiplier;
            size = 2;
            researchCostMultiplier = 0.1f;
            envDisabled |= Env.scorching;
        }};

        //endregion wall

        //region smelter
        bronzerSmelter = new GenericCrafter("bronzer-smelter"){{
            requirements(Category.crafting, with(
                    Items.copper, 20, Items.lead, 30));
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(UnItems.bronzer, 1);
            craftTime = 35f;//35*0.0165f=0.5775s    0.58s
            size = 2;
            hasPower = true;
            hasLiquids = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("00FF80")));
            //ambientSound = Sounds.smelter;
            //ambientSoundVolume = 0.09f;

            consumeItems(with(Items.copper, 2, Items.lead, 1));
            consumePower(0.30f);//0.30*60f=18power/s
            researchCostMultiplier = 0.1f;
            envDisabled |= Env.scorching;
        }};
        //endregion smelter

        //region turret
        bronzeAncientCannon = new ItemTurret("bronze-ancient-cannon"){{
            requirements(Category.turret, with
                    (new Object[]{UnItems.bronzer, 50}));
            ammo(
                    UnItems.bronzer, new ArtilleryBulletType(2f, 40){{
                        knockback = 1.5f; // 弹药击退力
                        lifetime = 240f; // 弹药生命期
                        width = height = 22f; // 弹药的宽度和高度
                        collidesTiles = false; // 是否与地形碰撞
                        splashDamageRadius = 40f; // 溅射伤害半径
                        splashDamage = 80f; // 溅射伤害值
                        reloadMultiplier = 1; // 重新装填倍率
                        ammoMultiplier = 1f; // 弹药倍率
                        homingPower = 0.05f; // 追踪能力
                        homingRange = 24f; // 追踪范围
                        hitEffect = UnFx.bronzerHit; // 命中效果
                        status = UnStatusEffects.tetanus; // 状态效果
                        statusDuration = 500f; // 状态持续时间
                        frontColor = Color.valueOf("96CDCD"); // 前景颜色
                        backColor = Color.valueOf("668B8B"); // 背景颜色
                        makeFire = true; // 是否产生火焰
                        trailEffect = UnFx.bronzerTrail; // 炮弹轨迹效果
                    }}
            );
            //shoot = new ShootAlternate(50f);炮口偏移
            drawer = new DrawTurret("reinforced-"){{
                parts.addAll(
                        new RegionPart("-end"){{
                            heatColor = Color.valueOf("668B8B");
                            heatProgress = PartProgress.warmup;
                            moveY = 0f;
                        }},
                        new RegionPart("-front"){{
                            heatColor = Color.valueOf("96CDCD");
                            heatProgress = PartProgress.warmup;
                            mirror = true;
                            moveX = -1f;
                        }},
                        new RegionPart("-min"){{
                            heatColor = Color.valueOf("96CDCD");
                            heatProgress = PartProgress.recoil;
                            moveY = -1f;
                        }}
                );
            }};
            size = 3; // 尺寸
            targetAir = false; // 是否攻击空中目标
            reload = 300f; // 重新装填时间
            ammoPerShot = 3;
            recoil = 4f; // 后坐力
            range = 280f; // 射程
            inaccuracy = 1f; // 不准确性
            shootCone = 5f;//射击角度判定
            health = 400; // 生命值
            rotateSpeed = 4f; // 转向速度
            itemCapacity = 9; // 弹药容量
            shootEffect = UnFx.bronzerShoot; // 射击效果
            shootSound = Sounds.bang; // 射击声音
            //coolant = consumeCoolant(0.1f);
            limitRange(0f); // 限制射程
            researchCostMultiplier = 0.1f;
            envDisabled |= Env.scorching;
        }};

        plasmaCannon = new ItemTurret("plasma-cannon") {{
            requirements(Category.turret, with
                    (Items.copper, 80, Items.graphite, 50, Items.titanium, 20));
            ammo(
                    Items.copper,  new BasicBulletType(2.8f, 15){{
                        knockback = 0.3f;
                        width = 7f;
                        height = 9f;
                        collidesTiles = true;
                        lifetime = 120f;
                        homingPower = 0.05f;
                        homingRange = 24f;
                        ammoMultiplier = 1;
                        statusDuration = 60f;
                        frontColor = Color.valueOf("96CDCD");
                        backColor = Color.valueOf("668B8B");
                        makeFire = false;
                        trailRotation = true;
                        trailInterval = 3f;
                        trailEffect = UnFx.plasmaTrail;
                        hitEffect = UnFx.plasmaHit;
                        intervalBullet = new LightningBulletType(){{
                        damage = 3;
                        collidesAir = false;
                        ammoMultiplier = 1f;
                        buildingDamageMultiplier = 0.1f;
                        lightningColor = Color.valueOf("00BFFF");
                        lightningLength = 1;
                        lightningLengthRand = 5;
                        lightningType = new BulletType(0.0001f, 0f){{
                            lifetime = Fx.lightning.lifetime;
                            despawnEffect = Fx.none;
                            status = StatusEffects.shocked;
                            statusDuration = 1f;
                            hittable = false;
                            lightColor = Color.white;
                            buildingDamageMultiplier = 0.1f;
                            }};
                        }};
                    }});

            drawer = new DrawTurret("reinforced-"){{
                parts.addAll(
                        new RegionPart("-side"){{
                        progress = PartProgress.warmup;
                        moveX = 0.6f;
                        moveRot = -15f;
                        mirror = true;
                        layerOffset = 0.001f;
                        moves.add(new PartMove(PartProgress.recoil, 0.5f, -0.5f, -8f));
                    }},
                        new RegionPart("-barrel"){{
                        progress = PartProgress.recoil;
                        moveY = -2.5f;
                    }});
                }};

            size = 2;
            range = 240f;
            reload = 90f;
            ammoPerShot = 2;
            consumeAmmoOnce = false;
            inaccuracy = 1f;
            ammoEjectBack = 4f;
            recoil = 0.1f;
            shake = 1.2f;
            shoot.shots = 5;
            shoot.shotDelay = 3f;
            itemCapacity = 15;
            ammoUseEffect = Fx.casing2;
            scaledHealth = 100;
            smokeEffect = UnFx.plasmaSmoke;
            shootEffect = UnFx.plasmaShoot;
            shootSound = Sounds.shootBig;
            limitRange();
            coolantMultiplier = 5f;//1:8
            coolant = consumeCoolant(0.2f);//0.2*60f=12liquids/s
            researchCostMultiplier = 0.1f;
        }};

        proliferationCannon = new ItemTurret("proliferation-cannon") {{
            requirements(Category.turret, with
                    (Items.plastanium, 5,Items.phaseFabric, 3,Items.titanium, 10,Items.graphite, 5));
            ammo(
                    Items.sporePod, new BasicBulletType(2.5f, 5){{
                        width = 7f;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = 3;
                        collidesTiles = true;
                        homingPower = 0.5f;
                        homingRange = 24f;
                        hitEffect = UnFx.proilHit;
                        status = UnStatusEffects.hyperPlasia;
                        statusDuration = 600f;
                        frontColor = Color.valueOf("98FB98"); // 前景颜色
                        backColor = Color.valueOf("32CD32"); // 背景颜色
                    }}
            );

            drawer = new DrawTurret(){{
                parts.addAll(
                        new RegionPart("-min"){{
                        heatColor = Color.valueOf("00FF7F");
                        heatProgress = PartProgress.warmup;
                        moveY = -0.5f;
                        }}
                );
            }};
            size = 1;
            health = 100;
            targetAir = false;
            reload = 60f;
            recoil = 1f;
            range = 400f;
            inaccuracy = 2f;
            shootCone = 15f;
            shootEffect = UnFx.proliShoot;
            rotateSpeed = 8f;
            itemCapacity = 5;
            ammoUseEffect = Fx.none;
            researchCostMultiplier = 0.1f;
            limitRange();

        }};
        //endregion turret

        //region core
        universeCore = new CoreBlock("universe-core"){{
            requirements(Category.effect, BuildVisibility.editorOnly,
                    with(Items.copper, 10000, Items.lead, 10000));
            alwaysUnlocked = true;
            isFirstTier = true;
            unitType = alpha;
            health = 600;
            itemCapacity = 10000;
            size = 3;
            unitCapModifier = 9;
            researchCostMultiplier = 0.1f;
        }};

        //endregion core
    }
}

