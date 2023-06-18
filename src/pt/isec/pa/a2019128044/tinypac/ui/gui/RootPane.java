package pt.isec.pa.a2019128044.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.CSSManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.uistates.GameOverUI;
import pt.isec.pa.a2019128044.tinypac.ui.gui.uistates.GamePlayUI;
import pt.isec.pa.a2019128044.tinypac.ui.gui.uistates.PauseUI;

public class RootPane extends BorderPane {
    GameManager gameManager;

    public RootPane(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
    }

    /**
     * cria todos os panes necessários
     * carrega o ficheiro css
     * cria o backgroud
     */
    private void createViews() {
        CSSManager.applyCSS(this,"styles.css");

        StackPane stackPane = new StackPane(
                new MainMenuUI(gameManager),
                new GamePlayUI(gameManager),
                new PauseUI(gameManager),
                new GameOverUI(gameManager),
                new TopFiveUI(gameManager)
        );
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

        Label watermark = new Label("Developed by: Tiago Garcia Quintas, 2019128044");
        watermark.getStyleClass().add("watermark");
        StackPane.setAlignment(watermark, Pos.BOTTOM_RIGHT);
        stackPane.getChildren().add(watermark);

        ImageView watermarkImage = new ImageView(ImageManager.getImage("isec.png"));
        watermarkImage.setPreserveRatio(true);
        watermarkImage.setFitWidth(25);
        StackPane.setAlignment(watermarkImage, Pos.BOTTOM_LEFT);
        stackPane.getChildren().add(watermarkImage);

        this.setCenter(stackPane);
    }

    /**
     * regista o handler START
     */
    private void registerHandlers() {
        gameManager.addPropertyChangeListener(GameManager.START, evt -> {
            update();
        });
    }

    /**
     * assim que o evento START acontece é criado a InfoUI
     */
    private void update() {
        this.setRight(
                new InfoUI(gameManager)
        );
    }
}

