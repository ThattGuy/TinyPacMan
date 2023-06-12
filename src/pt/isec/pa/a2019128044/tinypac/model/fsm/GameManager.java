package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngine;
import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameManager implements IGameEngineEvolve{

    private GameContext fsm;
    private PropertyChangeSupport pcs;


    public GameManager() {
        fsm = GameContext.getContextSingleton();
        pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void start() {
        pcs.firePropertyChange(null,null,null);
    }

    public void pressKey(KEYPRESS keypress) {
        fsm.pressKey(keypress);
    }

    public GameState getState() {
        return fsm.getState();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        pcs.firePropertyChange(null, null, null);
    }

    public char[][] getMaze(){
        return fsm.getLevel();
    }
}
