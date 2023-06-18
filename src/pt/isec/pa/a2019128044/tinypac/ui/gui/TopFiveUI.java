package pt.isec.pa.a2019128044.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.RootPane;

import java.util.List;

public class TopFiveUI extends BorderPane{
    GameManager gameManager;
    public TopFiveUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        setVisible(false);
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(GameManager.TOPFIVE, evt -> {
            update();
        });
    }

    private void createViews() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        List<String> topFivePlayers = gameManager.getTopFive();

        System.out.println(topFivePlayers.size());

        for (String player : topFivePlayers) {
            Label Label = new Label(player);
            vbox.getChildren().addAll(Label);
        }

        this.setCenter(vbox);

    }

    private void update() {
        setVisible(true);
    }

}
