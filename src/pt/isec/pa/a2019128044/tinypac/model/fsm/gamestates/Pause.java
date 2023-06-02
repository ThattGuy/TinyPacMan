package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

public class Pause extends GameStateAdapter {

    public Pause(GameContext context, GameData data) {
        super(context, data);
    }

    //TODO perguntar como retornar ao estado anterior
    @Override
    public boolean pressKey(KEYPRESS keypress) {
        if(keypress == KEYPRESS.ESC){
           // volta para o estado anterior
        }
        return true;
    }

    @Override
    public GameState getState() {
        return GameState.PAUSE;
    }
}
