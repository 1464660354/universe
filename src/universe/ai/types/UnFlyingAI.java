package universe.ai.types;

import mindustry.ai.types.FlyingAI;
import static mindustry.Vars.state;

public class UnFlyingAI extends FlyingAI {

    @Override
    public void updateMovement(){
        unloadPayloads();

        if(target != null && unit.hasWeapons()){
            if(unit.type.circleTarget){
                circleAttack(135f);
            }else{
                moveTo(target, unit.type.range * 1.2f);
                unit.lookAt(target);
            }
        }

        if(target == null && state.rules.waves && unit.team == state.rules.defaultTeam){
            moveTo(getClosestSpawner(), state.rules.dropZoneRadius + 150f);
        }
    }
}
