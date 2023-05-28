package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

public class PacmanVulnerable  extends GameStateAdapter {
    public PacmanVulnerable(GameContext context, GameData data) {
        super(context, data);
    }

    @Override
    public boolean evolve(long currentTime) {
        data.evolve(currentTime);
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
        return GameState.PACMAN_VULNERABLE;
    }
}
