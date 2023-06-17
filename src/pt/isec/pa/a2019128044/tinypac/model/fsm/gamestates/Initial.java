package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

import java.io.Serializable;

public class Initial extends GameStateAdapter implements Serializable {
    public Initial(GameContext context, GameData data) {
        super(context, data);
        System.out.println("Initial State");
    }

    @Override
    public boolean pressKey(KEYPRESS keypress) {

        if(keypress == KEYPRESS.ESC){
            changeState(GameState.PAUSE,this.getState());
        }else{
            changeState(GameState.WARMUP,this.getState());
            return data.setDirection(keypress);
        }
        return false;
    }

    @Override
    public GameState getState() {
        return GameState.INITIAL;
    }
}
