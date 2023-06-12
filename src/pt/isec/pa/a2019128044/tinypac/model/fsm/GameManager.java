package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngine;
import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameManager {

    private GameContext fsm;
    private PropertyChangeSupport pcs;

    public GameManager() {
        fsm = new GameContext();
        pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void pressKey(KEYPRESS keypress) {
        //boolean ret = fsm.pressKey(keypress);
        //pcs.firePropertyChange(null, null, null);
        //return ret;
    }

    public void evolve(long currentTime) {
        //boolean ret = fsm.evolve(currentTime);
        //pcs.firePropertyChange(null, null, null);
        //return ret;
    }

    public GameState getState() {
        return fsm.getState();
    }

}
