package universe.content;

import mindustry.content.TechTree;

import static universe.content.LuoPingPresets.beBorn;
import static universe.content.UnBlocks.universeCore;
import static universe.content.UnUnitTypes.dragonClaws;

public class UnTechTree {

    public static void load() {
        TechTree.TechNode root = TechTree.nodeRoot("universe", universeCore, () -> {
            TechTree.node(UnBlocks.plasmaCannon, () -> {
                TechTree.node(UnBlocks.proliferationCannon, () -> {});
                TechTree.node(UnBlocks.refractiveNoYa, () -> {});
            });
            TechTree.node(UnBlocks.bronzerSmelter, () -> {
                TechTree.node(UnItems.bronzer, () -> {
                    TechTree.node(UnBlocks.bronzeDrillBit, () -> {
                    });
                    TechTree.node(UnBlocks.bronzeAncientCannon);
                    TechTree.node(UnBlocks.bronzerWall, () -> {
                        TechTree.node(UnBlocks.bronzerWallLarge, () -> {
                        });
                    });
                });
            });
            TechTree.node(UnBlocks.armyCorps, () -> {
                TechTree.node(UnUnitTypes.dragonClaws, () -> {});
            });
            TechTree.node(LuoPingPresets.beBorn, () -> {});
        });
    }
}
