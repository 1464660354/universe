package universe.content;


import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawFlame;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.*;
import mindustry.type.ItemStack;
import mindustry.world.Block;

import universe.content.UnItems;

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
            bronzeAncientCannon,
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
        }};
        //endregion smelter

        //region turret
        // 创建一个名为 bronzeAncientCannon 的炮塔实例，类型为 ItemTurret
        bronzeAncientCannon = new ItemTurret("bronze-ancient-cannon"){{
            // 设置炮塔的构建要求，包含材料和数量
            requirements(Category.turret, ItemStack.with
                    (new Object[]{UnItems.bronzer, 50}));
            // 配置炮塔的弹药类型及其属性
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
                        hitEffect = Fx.blastExplosion; // 命中效果
                        status = StatusEffects.burning; // 状态效果
                        statusDuration = 600f; // 状态持续时间
                        frontColor = Color.valueOf("FF7F00"); // 前景颜色
                        backColor = Color.valueOf("CD6600"); // 背景颜色
                        makeFire = true; // 是否产生火焰
                        trailEffect = Fx.incendTrail; // 炮弹轨迹效果
                    }}
            );
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
        }};

        //endregion core
    }
}

