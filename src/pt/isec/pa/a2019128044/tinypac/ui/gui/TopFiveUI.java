package pt.isec.pa.a2019128044.tinypac.ui.gui;

import javafx.scene.layout.BorderPane;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.RootPane;

public class TopFiveUI extends BorderPane{
    GameManager gameManager;
    public TopFiveUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void registerHandlers() {
    }

    private void createViews() {
    }

    private void update() {

    }

}
