package pt.isec.pa.a2019128044.tinypac.ui.gui.uistates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    }

    private void createViews() {
        Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/PacmanIcon.png");

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        btnStart = new Button("Start");
        btnStart.setMinWidth(200);
        btnStart.setMinHeight(30);

        btnTop5 = new Button("Top 5");
        btnTop5.setMinWidth(200);
        btnTop5.setMinHeight(30);

        btnExit = new Button("Exit");
        btnExit.setMinWidth(200);
        btnExit.setMinHeight(30);

        VBox vbox = new VBox(imageView, btnStart, btnTop5, btnExit);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setMargin(btnStart, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnTop5, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnExit, new Insets(10, 0, 0, 0));

        double buttonHeightPercentage = 0.05; // Adjust this value to control the button height
        btnStart.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnTop5.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnExit.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));

        double buttonWidthPercentage = 0.25;
        btnStart.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnTop5.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnExit.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));

        this.setCenter(vbox);
    }

    private void registerHandlers() {
        btnStart.setOnAction(event -> {
            gameManager.start();
            this.setVisible(false);
        });
        btnTop5.setOnAction(event -> {
            this.setVisible(false);
        });
        btnExit.setOnAction(event -> {
            System.exit(0);
        });
    }
}
