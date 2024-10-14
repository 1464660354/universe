package universe.content;

import mindustry.type.SectorPreset;

import static universe.content.UnPlanets.luoPing;

public class LuoPingPresets {
    public static SectorPreset
            beBorn;
//    sector.universe-mod-grow.name = 成长
//    sector.universe-mod-firstEncounter.name = 初遇
//    sector.universe-mod-proliferation.name = 增殖
//    sector.universe-mod-multiply.name = 倍乘
//    sector.universe-mod-multiplication.name = 乘法
//    sector.universe-mod-multivariant.name = 多元
//    sector.universe-mod-normalize.name = 归一
//    sector.universe-mod-quiet.name  通幽
//    sector.universe-mod-phantomKing.name = 幽王
    public static void load(){

        beBorn = new SectorPreset("beBorn", luoPing, 10){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 10;
            difficulty = 1;
            overrideLaunchDefaults = true;
            noLighting = true;
            startWaveTimeMultiplier = 3f;
        }};
    }
}

