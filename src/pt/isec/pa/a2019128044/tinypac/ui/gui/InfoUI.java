package pt.isec.pa.a2019128044.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.ImageManager;

public class InfoUI extends VBox{

    GameManager gameManager;
    VBox vb;
    public InfoUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        setPadding(new Insets(10));

        vb = new VBox();
        vb.setAlignment(Pos.CENTER);

        this.getChildren().addAll(vb);
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { Platform.runLater(this::update);});
    }

    private void update() {
        if (gameManager.getState() == GameState.PAUSE || gameManager.getState() == GameState.GAMEOVER) {
            setVisible(false);
            return;
        }
        setVisible(true);

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setPadding(new Insets(5));

        int lives = gameManager.getLives();
        for (int i = 0; i < lives; i++) {
            ImageView imageView = new ImageView(ImageManager.getImage("heart.png"));
            imageView.setFitHeight(25);
            imageView.setFitWidth(25);
            hb.getChildren().add(imageView);
        }


        vb.getChildren().clear();
        vb.getChildren().add(hb);

        int points = gameManager.getPoints();
        Label pointsLabel = new Label("Points: " + points);
        pointsLabel.getStyleClass().add("info");
        pointsLabel.setTextFill(Color.YELLOW);
        vb.getChildren().add(pointsLabel);
    }

}
