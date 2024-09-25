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
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.StatusEffect;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.environment.OreBlock;
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

public class UnBlocks {
    public static Block
            //region ore
            oreAurum,
            //endregion

            //region wall
            bronzerWall,bronzerWallLarge,
            //endregion

            //region Smelter
            bronzerSmelter,
            //endregion

            //region turret
            bronzeAncientCannon,plasmaCannon,
            //endregion

            //region core
            universeCore;
            //endregion

    //load
    public static void load(){
        //region ore
        oreAurum = new OreBlock(UnItems.aurum){{
            oreDefault = true;
            oreThreshold = 0.864f;
            oreScale = 24.904762f;
      }};
        //endregion 矿石

        //region wall

        int wallHealthMultiplier = 4;

        bronzerWall = new Wall("bronzer-wall"){{
            requirements(Category.defense, ItemStack.with
                    (new Object[]{UnItems.bronzer, 10}));
            health = 95 * wallHealthMultiplier;
            size = 1;
            researchCostMultiplier = 0.1f;
            buildCostMultiplier = 1.1f;
            envDisabled |= Env.scorching;
        }};
        bronzerWallLarge = new Wall("large-bronzer-wall"){{
            requirements(Category.defense, ItemStack.with
                    (new Object[]{UnItems.bronzer, 40}));
            health = 95 * 4 * wallHealthMultiplier;
            size = 2;
            researchCostMultiplier = 0.1f;
            envDisabled |= Env.scorching;
        }};

        //endregion wall

        //region smelter
        bronzerSmelter = new GenericCrafter("bronzer-smelter"){{
            requirements(Category.crafting, ItemStack.with(
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

            consumeItems(ItemStack.with(Items.copper, 2, Items.lead, 1));
            consumePower(0.30f);//0.30*60f=18power/s
            researchCostMultiplier = 0.1f;
            envDisabled |= Env.scorching;
        }};
        //endregion smelter

        //region turret
        bronzeAncientCannon = new ItemTurret("bronze-ancient-cannon"){{
            requirements(Category.turret, ItemStack.with
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
                        hitEffect = UnFx.bronzerBlastExplosion; // 命中效果
                        status = UnStatusEffects.tetanus; // 状态效果
                        statusDuration = 500f; // 状态持续时间
                        frontColor = Color.valueOf("96CDCD"); // 前景颜色
                        backColor = Color.valueOf("668B8B"); // 背景颜色
                        makeFire = true; // 是否产生火焰
                        trailEffect = UnFx.bronzerIncendTrail; // 炮弹轨迹效果
                    }}
            );
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
            // 配置炮塔的基本属性
            size = 3; // 尺寸
            targetAir = false; // 是否攻击空中目标
            reload = 300f; // 重新装填时间
            recoil = 4f; // 后坐力
            range = 280f; // 射程
            inaccuracy = 1f; // 不准确性
            //shootCone = 5f;
            health = 400; // 生命值
            shootEffect = UnFx.bronzerFire; // 射击效果
            shootSound = Sounds.bang; // 射击声音
            //coolant = consumeCoolant(0.1f);
            limitRange(0f); // 限制射程
            researchCostMultiplier = 0.1f;
            envDisabled |= Env.scorching;
        }};

        plasmaCannon = new ItemTurret("plasma-cannon") {{
            requirements(Category.turret, ItemStack.with
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
                        ammoMultiplier = 3;
                        statusDuration = 60f;
                        frontColor = Color.valueOf("96CDCD"); // 前景颜色
                        backColor = Color.valueOf("668B8B"); // 背景颜色
                        makeFire = false;
                        trailRotation = true;//轨迹旋转
                        trailInterval = 3f;//轨迹间隔
                        trailEffect = UnFx.plasmatrail;// 炮弹轨迹效果
                        hitEffect = UnFx.plasmahit;// 命中效果
                        intervalBullet = new LightningBulletType(){{
                        damage = 3;
                        collidesAir = false;
                        ammoMultiplier = 1f;
                        lightningColor = Color.valueOf("00BFFF");
                        lightningLength = 1;
                        lightningLengthRand = 5;
                        buildingDamageMultiplier = 0.1f;
                        lightningType = new BulletType(0.0001f, 0f){{
                            lifetime = Fx.lightning.lifetime;// 闪电的生命期
                            despawnEffect = Fx.none;// 闪电的消散效果
                            status = StatusEffects.shocked;// 闪电的状态效果
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
            consumeAmmoOnce = false;
            inaccuracy = 1f;
            ammoEjectBack = 4f;
            recoil = 0.1f;
            shake = 1.2f;
            shoot.shots = 5;
            shoot.shotDelay = 3f;
            ammoUseEffect = Fx.casing2;// 弹药使用效果
            scaledHealth = 100;
            smokeEffect = UnFx.plasmaSmoke;// 射击烟雾效果
            shootSound = Sounds.shootBig;// 射击声音
            limitRange();
            coolantMultiplier = 1.1f;
            coolant = consumeCoolant(0.2f);
            researchCostMultiplier = 0.1f;
        }};
        //endregion turret

        //region core
        universeCore = new CoreBlock("universe-core"){{
            requirements(Category.effect, BuildVisibility.editorOnly,
                    ItemStack.with(Items.copper, 10000, Items.lead, 10000));
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

