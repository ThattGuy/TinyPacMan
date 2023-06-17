package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngine;
import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class GameContext implements Serializable {
    private final GameData data;
    private transient IGameState state;

    public GameContext() {
        data = new GameData();
        state = GameState.INITIAL.createState(this, data, null);
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        state = GameState.PACMAN_VULNERABLE.createState(this, data, null);
    }

    public char[][] getLevel(){
        return data.getLevel();
    }

    void changeState(IGameState newState) {
        this.state = newState;
    }


    public boolean evolve(long currentTime) {
        return state.evolve(currentTime);
    }

    public boolean pressKey(KEYPRESS KEYPRESS)  {
        return state.pressKey(KEYPRESS);
    }

    public GameState getState() {
        return state.getState();
    }

    public int getLives() {
        return data.getLives();
    }

    public int getPoints() {
        return data.getPoints();
    }
}