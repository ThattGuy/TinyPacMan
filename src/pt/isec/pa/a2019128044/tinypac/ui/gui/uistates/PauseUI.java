package pt.isec.pa.a2019128044.tinypac.ui.gui.uistates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;

public class PauseUI extends BorderPane {

    GameManager gameManager;
    Button btnResume, btnTop5, btnExit;

    public PauseUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        Image logo = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/logo.png");

        ImageView imageView = new ImageView(logo);
        imageView.fitWidthProperty().bind(this.widthProperty().multiply(0.3));
        imageView.setPreserveRatio(true);

        btnResume = new Button("Resume");
        btnResume.setMinWidth(200);
        btnResume.setMinHeight(30);

        btnTop5 = new Button("Top 5");
        btnTop5.setMinWidth(200);
        btnTop5.setMinHeight(30);

        btnExit = new Button("Exit");
        btnExit.setMinWidth(200);
        btnExit.setMinHeight(30);

        VBox vbox = new VBox(imageView, btnResume, btnTop5, btnExit);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setMargin(btnResume, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnTop5, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnExit, new Insets(10, 0, 0, 0));

        double buttonHeightPercentage = 0.05; // Adjust this value to control the button height
        btnResume.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnTop5.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnExit.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));

        double buttonWidthPercentage = 0.25;
        btnResume.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnTop5.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnExit.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));

        this.setCenter(vbox);
    }

    private void registerHandlers() {

        gameManager.addPropertyChangeListener(evt -> { update(); });

        btnResume.setOnAction(event -> {
            gameManager.start();
            this.setVisible(false);
        });
        btnTop5.setOnAction(event -> {
            this.setVisible(false);
        });
        btnExit.setOnAction(event -> {
            //todo voltar menu
            System.exit(0);
        });

        this.setOnKeyPressed((key) -> {
            if (key.getCode() == KeyCode.UP) {
                gameManager.pressKey(KEYPRESS.UP);
            }
            if (key.getCode() == KeyCode.DOWN) {
                gameManager.pressKey(KEYPRESS.DOWN);
            }
            if (key.getCode() == KeyCode.LEFT) {
                gameManager.pressKey(KEYPRESS.LEFT);
            }
            if (key.getCode() == KeyCode.RIGHT) {
                gameManager.pressKey(KEYPRESS.RIGHT);
            }
            if (key.getCode() == KeyCode.ESCAPE) {
                gameManager.pressKey(KEYPRESS.ESC);
            }
        });
    }

    private void update() {
        if (gameManager.getState() != GameState.PAUSE) {
            this.setVisible(false);
            return;
        }

        setVisible(true);
    }
}
