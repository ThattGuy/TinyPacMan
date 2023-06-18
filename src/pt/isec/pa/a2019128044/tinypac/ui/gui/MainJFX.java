package pt.isec.pa.a2019128044.tinypac.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pt.isec.pa.a2019128044.tinypac.TinyPAcMain;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;

import java.util.Optional;


public class MainJFX extends Application {
     GameManager gameManager;


    @Override
    public void init() throws Exception {
        super.init();
        gameManager = TinyPAcMain.gameManager;
    }

    @Override
    public void start(Stage stage) {
        newStage(stage);
    }


    /**
     * Cria a cena principal
     * @param stage recebe o stage da cena
     * ajusta o tamanho da janela
     */
    private void newStage(Stage stage) {
        RootPane root = new RootPane(gameManager);

        Scene scene = new Scene(root,1100,500);

        stage.setOnCloseRequest(e -> {
            e.consume();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Are you sure you want to exit?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Tiny Pacman");
        stage.setMinWidth(800);
        stage.setMinHeight(670);
        Image icon = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/PacmanIcon.png");
        stage.getIcons().add(icon);
        stage.show();
    }
}
