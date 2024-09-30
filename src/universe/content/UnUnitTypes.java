package universe.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class UnUnitTypes {
    public static UnitType
    //mech
    dragonClaws, dragonTeeth, dragonScales, dragonBody, dragonHead,
    //naval
    leech,asquid,piranha,deepSeaFish,beluga;
    public static void load(){

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

        dragonClaws = new UnitType("dragon-Claws"){{
            constructor = MechUnit::create;
            speed = 0.7f;
            hitSize = 8f;
            health = 200;
            weapons.add(new Weapon("claws-weapon"){{
                reload = 10f;
                x = 4f;
                y = 2f;
                top = false;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(2.7f, 14){{
                    width = 7f;
                    height = 9f;
                    lifetime = 90f;
                }};
            }});
        }};
    }
}
