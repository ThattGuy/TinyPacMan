package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

public class GhostsVulnerable extends GameStateAdapter {
    public GhostsVulnerable(GameContext context, GameData data) {
        super(context, data);
    }

    @Override
    public boolean allGhostsDead() {
        return super.allGhostsDead();
    }

    @Override
    public boolean evolve(long currentTime) {
        if(stateTimer == 0){
            stateTimer = currentTime;
        }

        data.moveAll(currentTime);

        if (currentTime - stateTimer >= 10000000000L) {
                changeState(GameState.PACMAN_VULNERABLE);
        }
        return true;
    }
    @Override
    public boolean pressKey(KEYPRESS keypress) {

        if(keypress == KEYPRESS.ESC){
            changeState(GameState.PAUSE);
        }else{
            data.setDirection(keypress);
        }
        return true;
    }
    @Override
    public GameState getState() {
        return GameState.GHOSTS_VULNERABLE;
    }
}
