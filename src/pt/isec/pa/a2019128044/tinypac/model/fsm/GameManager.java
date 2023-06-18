package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngine;
import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.Player;
import pt.isec.pa.a2019128044.tinypac.model.data.TopFive;
import pt.isec.pa.a2019128044.tinypac.ui.gui.TopFiveUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GameManager implements IGameEngineEvolve {

    private GameContext fsm;
    private PropertyChangeSupport pcs;

    public static final String START = "_start_";
    public static final String RESTART = "_restart_";
    public static final String HASDATA = "_hasdata_";
    public static final String EVOLVE = "_evolve_";

    IGameEngine gameEngine;
    public GameManager(IGameEngine gameEngine) {
        this.gameEngine = gameEngine;
        pcs = new PropertyChangeSupport(this);
        fsm = new GameContext();
        gameEngine.registerClient(this);
        if(Files.exists(Paths.get("", "topFive"))) {
            deserializeTopFive();
        }
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void start() {
        if(Files.exists(Paths.get("", "savedata"))) {
            pcs.firePropertyChange(HASDATA,null,null);
        }

        gameEngine.start(200);
        pcs.firePropertyChange(START,null,null);
    }

    public void load(){
        deserialize();
        gameEngine.start(200);
    }

    public void restart() {
        fsm = new GameContext();
        pcs.firePropertyChange(null,null,null);
    }

    public void pressKey(KEYPRESS keypress) {
        fsm.pressKey(keypress);
        pcs.firePropertyChange(null,null,null);
    }

    public GameState getState() {
        return fsm.getState();
    }

    @Override
    public  void evolve(IGameEngine engine,long currentTime) {
        boolean res = fsm.evolve(currentTime);
        pcs.firePropertyChange(EVOLVE, null, res);
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

    public void addPlayer(String name){
        System.out.println(name + fsm.getPoints());
        TopFive.addPlayer(name,fsm.getPoints());
        serializeTopFive();
    }

    public List<String> getTopFive(){
        return TopFive.getTopFiveString();
    }

    public void serialize() {
        try(FileOutputStream outputStream = new FileOutputStream("savedata"); ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(fsm);


        } catch (IOException e) {
            System.out.println("ERROR LOADING SAVED DATA");
            e.printStackTrace();
        }
    }

    public void deserialize() {
        try(FileInputStream inputStream = new FileInputStream("savedata"); ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            fsm = (GameContext) objectInputStream.readObject();
        } catch (Exception e) {
            System.out.println("ERROR LOADING SAVED DATA");
            e.printStackTrace();
        }
    }

    public static void serializeTopFive() {
        try(FileOutputStream outputStream = new FileOutputStream("topFive"); ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(TopFive.getTopFive());

        } catch (IOException e) {
            System.out.println("ERROR LOADING SAVED DATA: TOP FIVE");
            e.printStackTrace();
        }
    }

    public void deserializeTopFive() {
        try(FileInputStream inputStream = new FileInputStream("topFive"); ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            List<Player> players = (List<Player>) objectInputStream.readObject();
            TopFive.setTopFive(players);
        } catch (Exception e) {
            System.out.println("ERROR LOADING SAVED DATA: TOP FIVE");
            e.printStackTrace();
        }
    }

    public static final String TOPFIVE = "_topfive_";
    public void topFive(){
        pcs.firePropertyChange(TOPFIVE,null,null);
    }
}
