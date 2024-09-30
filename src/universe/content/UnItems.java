package universe.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.type.*;

import static mindustry.content.Items.*;

public class UnItems {

    public static Item bronzer, aurum;
    public static final Seq<Item> luoPingItems = new Seq<>();

     /*silver,monocrystallineSilicon,graphene,cohenite,fireRock,flowingPhosphorusIngot
     meltSol,starstripes,doomIngots,duvuPowder,parentCalciumBody,denseNucleus,vaticanCity
     abundantDust,zincBaseAlloy,rareEarthElement;*/

    public static void load() {

        bronzer = new Item("bronzer", Color.valueOf("92DD7EFF")){{
            hardness = 2;
            cost = 0.7f;
            radioactivity = 0.1f;
            charge = 0.2f;
            explosiveness = 0.1f;
            flammability = 0.1f;
        }};

        aurum = new Item("aurum", Color.valueOf("F3E979FF")){{
            hardness = 3;
            cost = 1f;
            radioactivity = 0.1f;
            charge = 1.0f;
        }};

//        luoPingItems.addAll(scrap, copper, lead, graphite, coal,
//                titanium, thorium, silicon, plastanium,
//                phaseFabric, surgeAlloy, sporePod, sand,
//                blastCompound, pyratite, metaglass,
//                bronzer, aurum);
//
//        luoPingItems.removeAll(luoPingItems);
    }
}
