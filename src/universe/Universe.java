package universe;

import arc.util.Log;
import mindustry.Vars;
import mindustry.mod.*;
import universe.content.*;

public class Universe extends Mod {
    public static Mods.LoadedMod MOD;
    public void loadContent(){
        MOD = Vars.mods.getMod(this.getClass());
        Log.info("Loading some universe content.");
        UnStatusEffects.load();
        UnItems.load();
        UnBlocks.load();
        UnUnitTypes.load();
        UnPlanets.load();
        LuoPingPresets.load();
        UnTechTree.load();
    }
}

