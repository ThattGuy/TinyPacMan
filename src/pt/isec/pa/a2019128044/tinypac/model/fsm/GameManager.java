package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngine;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameManager {

    private GameContext fsm;
    private PropertyChangeSupport pcs;
    public GameManager(IGameEngine gameEngine) {
        pcs = new PropertyChangeSupport(this);
        fsm = new GameContext();
        gameEngine.registerClient(fsm);
        gameEngine.registerClient((g,t) -> this.evolve(t));

    }

    /***
     *
     * PCS -> Retorna eventos ao listener
     * Nome do evento
     * Valor antigo
     * Valor novo
     */

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void start() {
        pcs.firePropertyChange("start",false,true);
    }

    public void pressKey(KEYPRESS keypress) {
        if(fsm.pressKey(keypress)){
            pcs.firePropertyChange("keypress",null,keypress);
        }
        pcs.firePropertyChange("keypress",null,null);
    }

    public GameState getState() {
        return fsm.getState();
    }

    public void evolve(long time) {
        pcs.firePropertyChange("evolve", null, time);
    }

    public char[][] getMaze(){
        if(fsm != null){
            return fsm.getLevel();
        }
        return null;
    }
}
