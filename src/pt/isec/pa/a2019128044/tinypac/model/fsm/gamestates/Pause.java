package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

public class Pause extends GameStateAdapter {

    GameState lastState;

    public Pause(GameContext context, GameData data, GameState lastState) {
        super(context, data);
        this.lastState = lastState;
    }
    @Override
    public boolean pressKey(KEYPRESS keypress) {
        if(keypress == KEYPRESS.ESC){
            changeState(lastState,this.getState());
        }
        return true;
    }

    @Override
    public GameState getState() {
        return GameState.PAUSE;
    }
}
