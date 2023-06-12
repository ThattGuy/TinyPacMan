package pt.isec.pa.a2019128044.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;

public class MainMenuUI extends BorderPane {

    GameManager gameManager;
    Button btnStart, btnTop5, btnExit;

    public MainMenuUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnStart = new Button("Start");
        btnStart.setMinWidth(200);
        btnStart.setMinHeight(30);

        btnTop5 = new Button("Top 5");
        btnTop5.setMinWidth(200);
        btnTop5.setMinHeight(30);

        btnExit = new Button("Exit");
        btnExit.setMinWidth(200);
        btnExit.setMinHeight(30);

        VBox vbox = new VBox(btnStart, btnTop5, btnExit);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        this.setCenter(vbox);
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> {
            update();
        });
        btnStart.setOnAction(event -> {
            // Handle the Start button click event here
        });
        btnTop5.setOnAction(event -> {
            // Handle the Top 5 button click event here
        });
        btnExit.setOnAction(event -> {
            Platform.exit();
        });
    }

    private void update() {
        this.setVisible(true);
    }

}
