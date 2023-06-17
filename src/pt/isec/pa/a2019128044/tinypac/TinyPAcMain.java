package pt.isec.pa.a2019128044.tinypac;

import javafx.application.Application;
import pt.isec.pa.a2019128044.tinypac.gameengine.GameEngine;
import pt.isec.pa.a2019128044.tinypac.gameengine.IGameEngine;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.MainJFX;
import pt.isec.pa.a2019128044.tinypac.ui.text.TextUI;

import java.io.IOException;

public class TinyPAcMain {

    public static GameManager gameManager;
    private static IGameEngine gameEngine;

    static {
        gameEngine = new GameEngine();
        gameManager = new GameManager(gameEngine);
    }
    public static void main(String[] args) throws IOException {

        //gameEngine.registerClient(ev -> gameManager.evolve());
        //TextUI ui = new TextUI(new GameContext());
        //gameEngine.registerClient(ui);

        Application.launch(MainJFX.class,args);

        gameEngine.waitForTheEnd();
    }
}