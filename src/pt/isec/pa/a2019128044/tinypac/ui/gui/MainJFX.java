package pt.isec.pa.a2019128044.tinypac.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.TinyPAcMain;
import javafx.scene.image.Image;

import java.util.Optional;


public class MainJFX extends Application {
     GameManager gameManager;


    @Override
    public void init() throws Exception {
        super.init();
        gameManager = TinyPAcMain.gameManager;
    }

    @Override
    public void start(Stage stage) throws Exception {
        newStage(stage,"Tiny Pacman");
    }


    private void newStage(Stage stage, String title) {
        RootPane root = new RootPane(gameManager);

        Scene scene = new Scene(root,1100,500);

        stage.setOnCloseRequest(e -> {
            e.consume();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Are you sure you want to exit?");;

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        });

        stage.setScene(scene);
        stage.setTitle(title);
        stage.setMinWidth(1000);
        stage.setMinHeight(670);
        Image icon = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/PacmanIcon.png");
        stage.getIcons().add(icon);
        stage.show();
    }
}
