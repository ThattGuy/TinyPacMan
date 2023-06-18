package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.data.TopFive;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

import java.io.Serializable;

public class GameOver extends GameStateAdapter implements Serializable {

    boolean checked;
    public GameOver(GameContext context, GameData data) {
        super(context, data);
        checked = false;
    }

    @Override
    public boolean evolve(long currentTime) {
        if(TopFive.checkIfTopFive(data.getPoints()) && !checked){
            checked = true;
            return false;
        }
        return true;
    }

    @Override
    public GameState getState() {
        return GameState.GAMEOVER;
    }
}
