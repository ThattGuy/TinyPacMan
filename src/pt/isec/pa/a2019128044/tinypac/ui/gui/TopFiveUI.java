package pt.isec.pa.a2019128044.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.RootPane;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.ImageManager;

import java.awt.*;
import java.util.List;

public class TopFiveUI extends BorderPane{
    GameManager gameManager;

    Button btnExit;

    /**
     * incializa o Pane
     * @param gameManager recebe o gameManager
     */
    public TopFiveUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        setVisible(false);
    }

    /**
     * regista os handlers
     */
    private void registerHandlers() {
        gameManager.addPropertyChangeListener(GameManager.TOPFIVE, evt -> {
            update();
        });

        btnExit.setOnAction(event -> {
            setVisible(false);
        });
    }

    /**
     * adiciona elementos à vbox de modo a mostrar o top5
     */
    private void createViews() {

        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, null, null);
        Background background = new Background(backgroundFill);
        this.setBackground(background);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);


        ImageView exitV = new ImageView(ImageManager.getImage("exit.png"));
        exitV.fitWidthProperty().bind(this.widthProperty().multiply(0.05));
        exitV.setPreserveRatio(true);
        btnExit = new Button();
        btnExit.setGraphic(exitV);
        btnExit.setMinWidth(400);
        btnExit.setMinHeight(40);


        List<String> topFivePlayers = gameManager.getTopFive();

        System.out.println(topFivePlayers.size());

        for (String player : topFivePlayers) {
            Label Label = new Label(player);
            Label.getStyleClass().add("topFive");
            vbox.getChildren().addAll(Label);
        }

        vbox.getChildren().add(btnExit);

        vbox.setAlignment(Pos.CENTER);
        this.setCenter(vbox);

    }

    /**
     * atualiza a visibilidade
     */
    private void update() {
        setVisible(true);
    }

}
