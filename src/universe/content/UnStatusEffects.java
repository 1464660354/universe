package universe.content;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.type.StatusEffect;

import static mindustry.content.StatusEffects.*;

public class UnStatusEffects {
    public static StatusEffect tetanus,hyperPlasia,flashStrike;
    public static void load(){

        tetanus = new StatusEffect("tetanus"){{
            color = Color.valueOf("FF7F00");
            damage = 0.2f;
            effect = UnFx.tetanus;
            transitionDamage = 10f;

            init(() -> {
                opposite(wet,freezing);

                affinity(tarred, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    UnFx.bronzerFire.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(tetanus, Math.min(time + result.time, 300f));
                });
            });
        }};

        hyperPlasia = new StatusEffect("hyperPlasia"){{
            color = Color.valueOf("dba9e0");
            damage = 0.1f;
            effect = UnFx.hyperPlasia;
            transitionDamage = 1f;

            init(() -> {
                opposite(burning,freezing,tarred);

                affinity(wet, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    UnFx.hyperPlasia.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(tetanus, Math.min(time + result.time, 60f));
                });
                affinity(shocked, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    UnFx.hyperPlasia.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(tetanus, Math.min(time + result.time, 60f));
                });
            });
        }};

        flashStrike = new StatusEffect("thunderThunder"){{

        }};
    }
}
