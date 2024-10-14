package universe.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.type.*;

import static mindustry.content.Items.*;

public class UnItems {

    public static Item
    aurum, bronzer, cohenite, graphene, huojiangStone,
    flowingPhosphorusIngot;


    public static final Seq<Item> luoPingItems = new Seq<>();

     /*silver,monocrystallineSilicon,graphene,
     meltSol,starstripes,doomIngots,duvuPowder,parentCalciumBody,denseNucleus,vaticanCity
     abundantDust,zincBaseAlloy,rareEarthElement;*/

    public static void load() {
        aurum = new Item("aurum", Color.valueOf("F3E979FF")){{
            hardness = 2;
            cost = 1f;
            radioactivity = 0.1f;
            charge = 1.0f;
        }};
        bronzer = new Item("bronzer", Color.valueOf("92DD7EFF")){{
            hardness = 2;
            cost = 0.7f;
            radioactivity = 0.1f;
            charge = 0.2f;
            explosiveness = 0.1f;
            flammability = 0.1f;
        }};
        cohenite = new Item("cohenite", Color.valueOf("FFAA00FF")){{
            explosiveness = 0.1f;
            flammability = 0.8f;
            hardness = 3;
            cost = 1.2f;
            radioactivity = 1.5f;
            charge = 0f;
            buildable = true;
        }};
        graphene = new Item("graphene", Color.valueOf("00FF00FF")){{
            explosiveness = 0f;
            flammability = 1.0f;
            hardness = 1;
            cost = 1f;
            radioactivity = 0f;
            charge = 2f;
            buildable = true;
        }};
        huojiangStone = new Item("huojiangStone", Color.valueOf("FF0000FF")){{
            explosiveness = 0.2f;
            flammability = 0.6f;
            hardness = 3;
            cost = 1.1f;
            radioactivity = 0f;
            charge = 0f;
            buildable = true;
        }};
        flowingPhosphorusIngot = new Item("flowingPhosphorusIngot", Color.valueOf("B7DA00FF")){{
            explosiveness = 0.35f;
            flammability = 1f;
            hardness = 4;
            cost = 1.3f;
            radioactivity = 1.3f;
            charge = 0.5f;
            buildable = true;
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
