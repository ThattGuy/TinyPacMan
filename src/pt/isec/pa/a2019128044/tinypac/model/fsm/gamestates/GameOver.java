package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

import java.io.Serializable;

public class GameOver extends GameStateAdapter implements Serializable {
    public GameOver(GameContext context, GameData data) {
        super(context, data);
    }

    @Override
    public boolean evolve(long currentTime) {
        return false;
    }

    @Override
    public GameState getState() {
        return GameState.GAMEOVER;
    }
}
