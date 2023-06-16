package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngine;
import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameManager implements IGameEngineEvolve {

    private GameContext fsm;
    private PropertyChangeSupport pcs;
    public GameManager(IGameEngine gameEngine) {
        pcs = new PropertyChangeSupport(this);
        fsm = new GameContext();
        //gameEngine.registerClient(fsm);
        gameEngine.registerClient(this);
    }

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

    @Override
    public void evolve(IGameEngine engine,long currentTime) {
        System.out.print("#");
        fsm.evolve(currentTime);
        pcs.firePropertyChange("evolve", null, null);
    }

    public char[][] getMaze(){
        if(fsm != null){
            return fsm.getLevel();
        }
        return null;
    }

    public int getLives() {
        return fsm.getLives();
    }

    public int getPoints() {
        return fsm.getPoints();
    }
}
