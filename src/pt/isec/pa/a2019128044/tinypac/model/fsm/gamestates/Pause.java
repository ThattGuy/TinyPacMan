package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

import java.io.Serializable;

public class Pause extends GameStateAdapter implements Serializable {

    GameState lastState;

    /**
     * contrutor Pause
     * @param context contexto
     * @param data dados jogo
     * @param lastState ultimo estado
     */
    public Pause(GameContext context, GameData data, GameState lastState) {
        super(context, data);
        this.lastState = lastState;
        System.out.println("Pause State");
    }


    /**
     * caso keypress seja ESC transita para o estado anterior
     * @param keypress recla que representa a direção
     */
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
