package universe.content;

import arc.struct.*;
import mindustry.content.TechTree;
import mindustry.type.*;

import static mindustry.content.Planets.serpulo;
import static mindustry.content.TechTree.node;
import static universe.content.UnBlocks.universeCore;

public class UnTechTree {

    public static void load() {
        TechTree.TechNode root = TechTree.nodeRoot("universe", universeCore, () -> {
            TechTree.node(UnBlocks.bronzerSmelter, () -> {
                TechTree.node(UnBlocks.bronzeAncientCannon);
                TechTree.node(UnBlocks.bronzerWall, () -> {
                    TechTree.node(UnBlocks.bronzerWallLarge, () -> {
                    });
                });
            });
        });
    }
}
