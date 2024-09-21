package universe;

import arc.util.Log;
import mindustry.Vars;
import mindustry.mod.*;
import universe.content.UnFx;
import universe.content.UnItems;
import universe.content.UnBlocks;
import universe.content.UnTechTree;

public class Universe extends Mod {
    public static Mods.LoadedMod MOD;

    public void loadContent() {
        MOD = Vars.mods.getMod(this.getClass());
        Log.info("Loading some universe content.");
        UnItems.load();
        UnBlocks.load();
        UnTechTree.load();
    }
}
