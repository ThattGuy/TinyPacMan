package pt.isec.pa.a2019128044.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.ImageManager;

import java.util.Optional;

public class GameOverUI extends BorderPane {

    GameManager gameManager;
    Button btnNewGame, btnExit;

    VBox vbox;

    public GameOverUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        setVisible(false);
    }

    private void createViews() {
        ImageView imageView = new ImageView(ImageManager.getImage("gameover.png"));
        imageView.fitWidthProperty().bind(this.widthProperty().multiply(0.3));
        imageView.setPreserveRatio(true);

        ImageView newGame = new ImageView(ImageManager.getImage("newgame.png"));
        newGame.fitWidthProperty().bind(this.widthProperty().multiply(0.1));
        newGame.setPreserveRatio(true);

        btnNewGame = new Button();
        btnNewGame.setGraphic(newGame);
        btnNewGame.setMinWidth(200);
        btnNewGame.setMinHeight(30);

        ImageView exitV = new ImageView(ImageManager.getImage("exit.png"));
        exitV.fitWidthProperty().bind(this.widthProperty().multiply(0.05));
        exitV.setPreserveRatio(true);
        btnExit = new Button();
        btnExit.setGraphic(exitV);
        btnExit.setMinWidth(200);
        btnExit.setMinHeight(30);

        vbox = new VBox(imageView, btnNewGame, btnExit);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setMargin(btnNewGame, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnExit, new Insets(10, 0, 0, 0));

        double buttonHeightPercentage = 0.05;
        btnNewGame.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnExit.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));

        double buttonWidthPercentage = 0.25;
        btnNewGame.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnExit.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));

        this.setCenter(vbox);
    }

    private void registerHandlers() {

        gameManager.addPropertyChangeListener(GameManager.EVOLVE, evt -> {
            Platform.runLater(() -> update((boolean)evt.getNewValue()));
        });

        btnNewGame.setOnAction(event -> {
            gameManager.restart();
            this.setVisible(false);
        });
        btnExit.setOnAction(event -> {
            System.exit(0);
        });

    }

    private void update(Boolean res) {
        if (gameManager.getState() != GameState.GAMEOVER) {
            this.setVisible(false);
            return;
        }
        setVisible(true);

        if(!res){
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("TOP FIVE");
            dialog.setHeaderText("Congrats, you are in the TOP 5 of players!");
            dialog.setContentText("Add your name:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                gameManager.addPlayer(name);
            });
        }
    }

}
