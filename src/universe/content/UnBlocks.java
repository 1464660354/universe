package universe.content;


import arc.graphics.Color;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.RegionPart;
import mindustry.gen.ClearItemsCallPacket;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.UnitType;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ContinuousLiquidTurret;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.type.ItemStack;
import mindustry.world.Block;

import universe.UnPal;
import universe.Untype.DrawGraphite;
import universe.Untype.UnNoYaBulletType;
import universe.Untype.UnNoYaShoot;
import universe.Untype.UnOreBlock;


import static mindustry.content.UnitTypes.alpha;
import static mindustry.type.ItemStack.with;
import static universe.content.UnItems.aurum;

public class UnBlocks {
    public static Block

    //region ore

    oreAurum,oreHuoJiangStone,oreCohenite,

    //endregion

    //region production

    bronzeDrillBit,

    //endregion

    //region wall

    bronzerWall,bronzerWallLarge,

    //endregion

    //region Smelter

    bronzerSmelter,carbonizationMechanism,structuralEquipment,

    //endregion

    //region turret

    bronzeAncientCannon,plasmaCannon,
        proliferationCannon,refractiveNoYa,

    //endregion

    //region unit

    armyCorps,causalLevelBuilder,

    //endregion

    //region core

    universeCore,

    //endregion

    //region Pad

    UnLaunch;

    //endregion

public static void load(){

    //region ore

    oreAurum = new UnOreBlock("ore-aurum",UnItems.aurum){{
        oreDefault = true;
        oreThreshold = 0.899f;
        oreScale = 25.28378f;
    }};

    oreHuoJiangStone = new UnOreBlock("ore-huojiang-stone",UnItems.huojiangStone){{
        oreDefault = true;
        oreThreshold = 0.887f;
        oreScale = 25.38344f;
    }};

    oreCohenite = new UnOreBlock("ore-cohenite",UnItems.cohenite){{
        oreDefault = true;
        oreThreshold = 0.882f;
        oreScale = 25.39644f;
    }};

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
        requirements(Category.crafting, with
            (Items.copper, 20, Items.lead, 30));
        craftEffect = Fx.smeltsmoke;
        health = 400;
        outputItem = new ItemStack(UnItems.bronzer, 1);
        craftTime = 35f;//35*0.0165f=0.5775s    0.58s
        size = 2;
        hasPower = true;
        hasLiquids = false;
        drawer = new DrawMulti(new DrawRegion("-bottom"),new DrawFlame(Color.valueOf("00FF80")),
                new DrawDefault());
        consumeItems(with(Items.copper, 2, Items.lead, 1));
        consumePower(0.30f);//0.30*60f=18power/s
        researchCostMultiplier = 0.1f;
        envDisabled |= Env.scorching;
    }};
    carbonizationMechanism = new GenericCrafter("carbonization-mechanism"){{
        requirements(Category.crafting, with
                (Items.copper, 500, Items.lead, 300, UnItems.aurum, 15, UnItems.cohenite, 30));
        craftEffect = Fx.pulverizeMedium;
        health = 500;
        outputItem = new ItemStack(UnItems.graphene, 4);
        craftTime = 300f;//300*0.0165f=5.18s    5.2s
        size = 3;
        hasItems = true;
        hasPower = true;
        hasLiquids = false;
        drawer = new DrawMulti(new DrawRegion("-bottom"),
                new DrawGraphite(),new DrawDefault());
        consumeItems(with(Items.graphite, 6, Items.coal, 10));
        consumePower(3f);//30*60f=180power/s
        researchCostMultiplier = 0.1f;
    }};
    structuralEquipment = new GenericCrafter("structural-equipment"){{
        requirements(Category.crafting, with
                (Items.copper, 500, Items.lead, 300, Items.titanium, 150, UnItems.cohenite, 15));
        craftEffect = Fx.pulverizeMedium;
        health = 600;
        outputItem = new ItemStack(UnItems.flowingPhosphorusIngot, 1);
        craftTime = 300f;//300*0.0165f=5.18s    5.2s
        size = 4;
        hasItems = true;
        hasPower = true;
        hasLiquids = true;
        drawer = new DrawMulti(new DrawRegion("-bottom"),
                new DrawFlame(Color.valueOf("B7DA00FF")),new DrawDefault());
        consumeItems(with( Items.lead, 10,Items.surgeAlloy, 2,UnItems.graphene, 5));
        consumePower(5f);//30*60f=180power/s
        liquidCapacity = 90f;
        consumeLiquid(Liquids.cryofluid, 0.5f);//0.5*60f=30
        researchCostMultiplier = 0.1f;
    }};

    //endregion smelter

    //region turret

    bronzeAncientCannon = new ItemTurret("bronze-ancient-cannon"){{
        requirements(Category.turret, with
                (new Object[]{UnItems.bronzer, 50}));
        ammo(
                UnItems.bronzer, new ArtilleryBulletType(2f, 40){{
                    knockback = 1.5f;
                    lifetime = 240f;
                    width = height = 22f;
                    collidesTiles = false;
                    splashDamageRadius = 40f;
                    splashDamage = 80f;
                    reloadMultiplier = 1;
                    ammoMultiplier = 1f;
                    homingPower = 0.05f;
                    homingRange = 24f;
                    hitEffect = UnFx.bronzerHit;
                    status = UnStatusEffects.tetanus;
                    statusDuration = 500f;
                    frontColor = UnPal.bronzerBullet;
                    backColor = UnPal.bronzerBulletBack;
                    makeFire = true;
                    trailEffect = UnFx.bronzerTrail;
                }}
        );
        //shoot = new ShootAlternate(50f);炮口偏移
        drawer = new DrawTurret("reinforced-"){{
            parts.addAll(
                    new RegionPart("-end"){{
                        heatColor = UnPal.bronzerBulletBack;
                        heatProgress = PartProgress.warmup;
                        moveY = 0f;
                    }},
                    new RegionPart("-front"){{
                        heatColor = UnPal.bronzerBullet;
                        heatProgress = PartProgress.warmup;
                        mirror = true;
                        moveX = -1f;
                    }},
                    new RegionPart("-min"){{
                        heatColor = UnPal.bronzerBullet;
                        heatProgress = PartProgress.recoil;
                        moveY = -1f;
                    }}
            );
        }};
        size = 3;
        targetAir = false;
        reload = 300f;
        ammoPerShot = 3;
        recoil = 4f;
        range = 280f;
        inaccuracy = 1f;
        shootCone = 5f;
        health = 400;
        rotateSpeed = 4f;
        itemCapacity = 9;
        shootEffect = UnFx.bronzerShoot;
        shootSound = Sounds.bang;
        //coolant = consumeCoolant(0.1f);
        limitRange(0f);
        researchCostMultiplier = 0.1f;
        envDisabled |= Env.scorching;
    }};

    plasmaCannon = new ItemTurret("plasma-cannon"){{
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
                frontColor = UnPal.bronzerBullet;
                backColor = UnPal.bronzerBulletBack;
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
            }}
        );
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
                }}
            );
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
        loopSoundVolume = 1f;
        smokeEffect = UnFx.plasmaSmoke;
        shootEffect = UnFx.plasmaShoot;
        shootSound = Sounds.shootBig;
        limitRange();
        coolantMultiplier = 5f;//1:8
        coolant = consumeCoolant(0.2f);//0.2*60f=12liquids/s
        researchCostMultiplier = 0.1f;
    }};

    proliferationCannon = new ItemTurret("proliferation-cannon"){{
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
                    frontColor = Color.valueOf("98FB98");
                    backColor = Color.valueOf("32CD32");
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
        loopSoundVolume = 1f;
        rotateSpeed = 8f;
        itemCapacity = 5;
        ammoUseEffect = Fx.none;
        researchCostMultiplier = 0.1f;
        limitRange();
    }};

    refractiveNoYa = new ContinuousLiquidTurret("refractive-NoYa"){{
        requirements(Category.turret, with
            (Items.graphite,240,Items.silicon, 200, Items.plastanium, 100,
                Items.phaseFabric, 50, Items.surgeAlloy, 75));
        ammo(
            Liquids.water, new UnNoYaBulletType(30){{//30*120f=360s
                length = 565f;
                knockback = 1f;
                pierceCap = 4;
                buildingDamageMultiplier = 0.1f;
                colors = new Color[]{
                    Color.valueOf("eb7abe").a(0.55f),
                    Color.valueOf("e189f5").a(0.7f),
                    Color.valueOf("907ef7").a(0.8f),
                    Color.valueOf("91a4ff"), Color.white
                };
            }}
        );
        shoot = new UnNoYaShoot(3, 0);
        drawer = new DrawTurret("reinforced-") {{
            parts.addAll(
                new RegionPart("-front"){{
                    heatColor = Color.valueOf("e189f5");
                    heatProgress = DrawPart.PartProgress.warmup;
                    progress = PartProgress.warmup;
                    mirror = true;
                    moveRot = 45f;
                }},
                new RegionPart("-beck"){{
                    heatColor = Color.valueOf("eb7abe");
                    heatProgress = DrawPart.PartProgress.warmup;
                    progress = PartProgress.warmup;
                    mirror = true;
                    moveRot = 45f;
                }},
                new RegionPart("-fire"){{
                    heatColor = Color.valueOf("907ef7");
                    heatProgress = DrawPart.PartProgress.warmup;
                    progress = PartProgress.warmup;
                    mirror = true;
                    moveX = 10f;
                    moveY = 10f;
                }}
            );
        }};
        recoil = 0.5f;
        size = 5;
        shake = 2f;
        range = 560f;
        loopSoundVolume = 1f;
        liquidConsumed = 15f / 60f;
        heatColor = UnPal.bronzerBulletBack;
        shootCone = 40f;
        scaledHealth = 40;
        shootSound = Sounds.laserbig;
        loopSound = Sounds.beam;
        envEnabled |= Env.space;
    }};

    //endregion turret

    //region core

    universeCore = new CoreBlock("universe-core"){{
        requirements(Category.effect, BuildVisibility.editorOnly,
            with(Items.copper, 10000, Items.lead, 10000));
        alwaysUnlocked = false;
        isFirstTier = false;
        unitType = alpha;
        health = 600;
        itemCapacity = 10000;
        size = 3;
        unitCapModifier = 9;
        researchCostMultiplier = 1f;
    }};

    //endregion core

    //region unit

    armyCorps = new UnitFactory("army-corps"){{
        requirements(Category.units, with(Items.copper, 50, Items.lead, 120, Items.silicon, 50));
        plans = Seq.with(
            new UnitPlan(UnUnitTypes.dragonClaws, 60f * 10, with(Items.silicon, 15, Items.copper, 20))
        );
        size = 3;
        consumePower(1f);
    }};
    causalLevelBuilder = new Reconstructor("causal-level-builder"){{
        requirements(Category.units, with
                (Items.lead, 2000, Items.silicon, 1500,
                        Items.thorium, 1000, Items.plastanium, 400,
                        Items.phaseFabric, 200, Items.surgeAlloy, 1000,
                        UnItems.cohenite,800,UnItems.graphene,500));

        size = 5;
        consumePower(25f);
        consumeItems(with(Items.silicon, 500,
                Items.plastanium, 300, Items.phaseFabric, 150,
                Items.surgeAlloy, 800,UnItems.flowingPhosphorusIngot,50));
        consumeLiquid(Liquids.cryofluid, 3f);

        constructTime = 60f * 60f * 4 * 2;
        liquidCapacity = 180f;

        upgrades.addAll(
                new UnitType[]{UnitTypes.toxopid, UnUnitTypes.scorpion}
        );
    }};

    //endregion unit

    //region Pad

    /*UnLaunch = new UnLaunchPad("UnLaunch") {{
        requirements(Category.effect, BuildVisibility.campaignOnly, with
            (Items.copper, 350, Items.silicon, 140, Items.lead, 200, Items.titanium, 150));
        size = 3;
        itemCapacity = 100;
        launchTime = 60f * 20;
        hasPower = true;
        consumePower(4f);
    }};*/

    //endregion

    }
}

