package universe;

import arc.struct.ObjectMap;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.blocks.defense.turrets.ContinuousLiquidTurret;
import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.consumers.ConsumeLiquidFilter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import mindustry.world.meta.Stats;

public class UnNoYaTurret extends ContinuousTurret {
    public ObjectMap<Liquid, BulletType> LiquidammoTypes = new ObjectMap();
    public ObjectMap<Items, BulletType> ItemsammoeType = new ObjectMap();
    public float liquidConsumed = 0.016666668F;

    public UnNoYaTurret(String name) {
        super(name);
        this.hasLiquids = true;
        this.loopSound = Sounds.minebeam;
        this.shootSound = Sounds.none;
        this.smokeEffect = Fx.none;
        this.shootEffect = Fx.none;
    }

    public void ammo(Object... objects) {
        this.LiquidammoTypes = ObjectMap.of(objects);
    }

    public void setStats() {
        super.setStats();
        this.stats.remove(Stat.ammo);
        this.stats.add(Stat.ammo, (table) -> {
            table.row();
            StatValues.number(this.liquidConsumed * 60.0F, StatUnit.perSecond, true).display(table);
        });
        this.stats.add(Stat.ammo, StatValues.ammo(this.LiquidammoTypes));
    }

    public void init() {
        this.consume(new ConsumeLiquidFilter((i) -> {
            return this.LiquidammoTypes.containsKey(i);
        }, this.liquidConsumed) {
            public void display(Stats stats) {
            }
        });
        this.LiquidammoTypes.each((item, type) -> {
            this.placeOverlapRange = Math.max(this.placeOverlapRange, this.range + type.rangeChange + this.placeOverlapMargin);
        });
        super.init();
    }

    public class UnNoYaTurretBuild extends ContinuousTurret.ContinuousTurretBuild {
        public UnNoYaTurretBuild() {
            super();
        }

        public boolean shouldActiveSound() {
            return this.wasShooting && this.enabled;
        }

        public void updateTile() {
            this.unit.ammo((float)this.unit.type().ammoCapacity * this.liquids.currentAmount() / UnNoYaTurret.this.liquidCapacity);
            super.updateTile();
        }

        public boolean canConsume() {
            return this.hasCorrectAmmo() && super.canConsume();
        }

        public BulletType useAmmo() {
            return this.peekAmmo();
        }

        public BulletType peekAmmo() {
            return (BulletType) UnNoYaTurret.this.LiquidammoTypes.get(this.liquids.current());
        }

        public boolean hasAmmo() {
            return this.hasCorrectAmmo() && UnNoYaTurret.this.LiquidammoTypes.get(this.liquids.current()) != null && this.liquids.currentAmount() >= 1.0F / ((BulletType) UnNoYaTurret.this.LiquidammoTypes.get(this.liquids.current())).ammoMultiplier;
        }

        public boolean hasCorrectAmmo() {
            return !this.bullets.any() || ((Turret.BulletEntry)this.bullets.first()).bullet.type == this.peekAmmo();
        }

        public boolean acceptItem(Building source, Item item) {
            return false;
        }

        public boolean acceptLiquid(Building source, Liquid liquid) {
            return UnNoYaTurret.this.LiquidammoTypes.get(liquid) != null && (this.liquids.current() == liquid || !UnNoYaTurret.this.LiquidammoTypes.containsKey(this.liquids.current()) || this.liquids.get(this.liquids.current()) <= 1.0F / ((BulletType) UnNoYaTurret.this.LiquidammoTypes.get(this.liquids.current())).ammoMultiplier + 0.001F);
        }
    }
}
