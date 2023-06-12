package pt.isec.pa.a2019128044.tinypac.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.TinyPAcMain;
import javafx.scene.image.Image;

public class MainJFX extends Application {
     GameManager gameManager;

    @Override
    public void init() throws Exception {
        super.init();
        gameManager = TinyPAcMain.gameManager;
    }

    @Override
    public void start(Stage stage) throws Exception {
        newStageForTesting(stage,"Tiny Pacman");
    }

    private void newStageForTesting(Stage stage, String title) {
        RootPane root = new RootPane(gameManager);
        Scene scene = new Scene(root,700,400);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setMinWidth(700);
        stage.setMinHeight(400);
        Image icon = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/PacmanIcon.png");
        stage.getIcons().add(icon);
        stage.show();
    }
}
