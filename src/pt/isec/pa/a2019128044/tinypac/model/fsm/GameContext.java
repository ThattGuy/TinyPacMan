package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngine;
import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;

public class GameContext implements IGameEngineEvolve {
    private GameData data;
    private IGameState state;
    public GameContext() {
        data = new GameData();
        state = GameState.INITIAL.createState(this, data);
    }

    public char[][] getLevel(){
        return data.getLevel();
    }
    void changeState(IGameState newState) {
        this.state = newState;
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        if(!state.evolve(currentTime))
           gameEngine.stop();
    }

    public void pressKey(KEYPRESS KEYPRESS)  {
        state.pressKey(KEYPRESS);
    }
}