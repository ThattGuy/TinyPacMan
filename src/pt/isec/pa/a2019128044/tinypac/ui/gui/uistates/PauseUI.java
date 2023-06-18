package pt.isec.pa.a2019128044.tinypac.ui.gui.uistates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.ImageManager;

public class PauseUI extends BorderPane {

    GameManager gameManager;
    Button btnResume, btnSave, btnExit;

    public PauseUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        ImageView imageView = new ImageView(ImageManager.getImage("logo.png"));
        imageView.fitWidthProperty().bind(this.widthProperty().multiply(0.3));
        imageView.setPreserveRatio(true);

        ImageView resume = new ImageView(ImageManager.getImage("resume.png"));
        resume.fitWidthProperty().bind(this.widthProperty().multiply(0.1));
        resume.setPreserveRatio(true);

        btnResume = new Button();
        btnResume.setGraphic(resume);
        btnResume.setMinWidth(200);
        btnResume.setMinHeight(30);

        ImageView save = new ImageView(ImageManager.getImage("save.png"));
        save.fitWidthProperty().bind(this.widthProperty().multiply(0.06));
        save.setPreserveRatio(true);
        btnSave = new Button();
        btnSave.setGraphic(save);
        btnSave.setMinWidth(200);
        btnSave.setMinHeight(30);

        ImageView exitV = new ImageView(ImageManager.getImage("exit.png"));
        exitV.fitWidthProperty().bind(this.widthProperty().multiply(0.05));
        exitV.setPreserveRatio(true);
        btnExit = new Button();
        btnExit.setGraphic(exitV);
        btnExit.setMinWidth(200);
        btnExit.setMinHeight(30);

        VBox vbox = new VBox(imageView, btnResume, btnSave, btnExit);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setMargin(btnResume, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnSave, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnExit, new Insets(10, 0, 0, 0));

        double buttonHeightPercentage = 0.05;
        btnResume.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnSave.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnExit.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));

        double buttonWidthPercentage = 0.25;
        btnResume.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnSave.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnExit.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));

        this.setCenter(vbox);
    }

    private void registerHandlers() {

        gameManager.addPropertyChangeListener(evt -> { update(); });

        btnResume.setOnAction(event -> {
            gameManager.pressKey(KEYPRESS.ESC);
            this.setVisible(false);
        });
        btnSave.setOnAction(event -> {
            gameManager.serialize();
        });
        btnExit.setOnAction(event -> {
            System.exit(0);
        });

        this.setOnKeyPressed((key) -> {
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
