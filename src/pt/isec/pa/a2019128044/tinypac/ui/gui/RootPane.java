package pt.isec.pa.a2019128044.tinypac.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.CSSManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.uistates.GamePlayUI;
import pt.isec.pa.a2019128044.tinypac.ui.gui.uistates.MainMenuUI;
import pt.isec.pa.a2019128044.tinypac.ui.gui.uistates.PauseUI;

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
                new MainMenuUI(gameManager),
                new GamePlayUI(gameManager),
                new PauseUI(gameManager)
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

    private void registerHandlers() {
    }

    private void update() {
    }
}

