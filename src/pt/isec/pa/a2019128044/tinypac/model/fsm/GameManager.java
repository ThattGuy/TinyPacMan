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
    public static final String HASDATA = "_hasdata_";
    public static final String EVOLVE = "_evolve_";

    IGameEngine gameEngine;

    /**
     * construtor do gameManager
     * cria o contexto da fsm
     * cria um PropertyChangeSupport
     * regista-se como cliente do gameEngine
     * caso exista um topFive da deserialize do mesmo
     * @param gameEngine motor de jogo
     */
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

    /**
     * evento start, caso exista um jogo guardado dispara a propertychange HASDATA
     * De seguida o START
     */
    public void start() {
        if(Files.exists(Paths.get("", "savedata"))) {
            pcs.firePropertyChange(HASDATA,null,null);
        }

        gameEngine.start(200);
        pcs.firePropertyChange(START,null,null);
    }

    /**
     * evento para carregar dados do jogo guardado
     */
    public void load(){
        deserialize();
        gameEngine.start(200);
    }

    /**
     * evento para recomeçar jogo
     */
    public void restart() {
        fsm = new GameContext();
        pcs.firePropertyChange(null,null,null);
    }

    /**
     *
     * @param keypress input do utilizador, envia para a fsm a tecla
     */
    public void pressKey(KEYPRESS keypress) {
        fsm.pressKey(keypress);
        pcs.firePropertyChange(null,null,null);
    }

    /**
     *
     * @return estado atual
     */
    public GameState getState() {
        return fsm.getState();
    }

    /**
     * evolve chamado pelo game Engine que em seguida chama o evolve da fsm
     * evento EVOLVE que com o valor do resultado da chamada do evolve da fsm
     * @param engine motor
     * @param currentTime tempo atual
     */
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

    /**
     * serialize dos dados de jogo
     */
    public void serialize() {
        try(FileOutputStream outputStream = new FileOutputStream("savedata"); ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(fsm);


        } catch (IOException e) {
            System.out.println("ERROR LOADING SAVED DATA");
            e.printStackTrace();
        }
    }

    /**
     * desirialize dos dados de jogo
     */
    public void deserialize() {
        try(FileInputStream inputStream = new FileInputStream("savedata"); ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            fsm = (GameContext) objectInputStream.readObject();
        } catch (Exception e) {
            System.out.println("ERROR LOADING SAVED DATA");
            e.printStackTrace();
        }
    }

    /**
     * serialize top5
     */
    public static void serializeTopFive() {
        try(FileOutputStream outputStream = new FileOutputStream("topFive"); ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(TopFive.getTopFive());

        } catch (IOException e) {
            System.out.println("ERROR LOADING SAVED DATA: TOP FIVE");
            e.printStackTrace();
        }
    }

    /**
     * deserialize top5
     */
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
