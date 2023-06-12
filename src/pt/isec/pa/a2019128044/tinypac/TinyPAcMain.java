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


    static {
        gameManager = new GameManager();
    }
    public static void main(String[] args) throws IOException {

        IGameEngine gameEngine = new GameEngine();
        GameContext context = GameContext.getContextSingleton();
        //TextUI ui = new TextUI(context);
        gameEngine.registerClient(context);
        //gameEngine.registerClient(ui);
        gameEngine.start(200);

        Application.launch(MainJFX.class,args);

        gameEngine.waitForTheEnd();
    }
}