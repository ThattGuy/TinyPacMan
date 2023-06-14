package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngine;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameManager{

    private GameContext fsm;
    private PropertyChangeSupport pcs;
    public GameManager(IGameEngine gameEngine) {
        pcs = new PropertyChangeSupport(this);
        fsm = new GameContext();
        gameEngine.registerClient(fsm);


    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void start() {
        pcs.firePropertyChange(null,null,null);
    }

    public void pressKey(KEYPRESS keypress) {
        fsm.pressKey(keypress);
        pcs.firePropertyChange(null,null,null);
    }

    public GameState getState() {
        return fsm.getState();
    }

    public void evolve() {
        pcs.firePropertyChange(null, null, null);
    }

    public char[][] getMaze(){
        if(fsm != null){
            return fsm.getLevel();
        }
        return null;
    }
}
