package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

public class Initial extends GameStateAdapter {
    public Initial(GameContext context, GameData data) {
        super(context, data);
        System.out.println("Initial State");
    }

    @Override
    public boolean pressKey(KEYPRESS keypress) {

        if(keypress == KEYPRESS.ESC){
            changeState(GameState.PAUSE,null);
        }else{
            data.setDirection(keypress);
            changeState(GameState.WARMUP,null);
        }

        return true;
    }

    @Override
    public GameState getState() {
        return GameState.INITIAL;
    }
}
