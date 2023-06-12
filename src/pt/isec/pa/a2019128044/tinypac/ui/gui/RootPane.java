package pt.isec.pa.a2019128044.tinypac.ui.gui;

import javafx.scene.layout.*;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.CSSManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.uistates.MainMenuUI;

public class RootPane extends BorderPane {
    GameManager gameManager;

    public RootPane(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        CSSManager.applyCSS(this,"styles.css");

        StackPane stackPane = new StackPane(
                new MainMenuUI(gameManager)
        ); // mencionar a possibilidade de apenas ir criando quando muda de estado
        stackPane.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("background1.jpg"),
                                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1,1,true,true,true,false)
                        )
                )
        );
        this.setCenter(stackPane);


    }

    private void registerHandlers() {
    }

    private void update() {
    }
}

