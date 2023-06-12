package pt.isec.pa.a2019128044.tinypac.ui.gui.uistates;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;

public class GamePlayUI extends BorderPane {

    GameManager gameManager;
    int oneBlockSize = 20;
    int score = 0;
    double wallSpaceWidth = oneBlockSize / 1.6;
    double wallOffset = (oneBlockSize - wallSpaceWidth) / 2;
    Color wallInnerColor = Color.BLACK;

    char [][] map;

    public GamePlayUI(GameManager gameManager) {
        this.gameManager = gameManager;

        map = gameManager.getMaze();

        registerHandlers();

        Pane root = new Pane();
        Scene scene = new Scene(root, oneBlockSize * map[0].length, oneBlockSize * map.length);

        createViews(root);
        update();
    }

    private void createViews(Pane root) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'x') {
                    Rectangle outerWall = new Rectangle(j * oneBlockSize, i * oneBlockSize, oneBlockSize, oneBlockSize);
                    outerWall.setFill(Color.valueOf("#342DCA"));
                    root.getChildren().add(outerWall);

                    if (j > 0 && map[i][j - 1] == 'x') {
                        Rectangle innerWall = new Rectangle(j * oneBlockSize, i * oneBlockSize + wallOffset,
                                wallSpaceWidth + wallOffset, wallSpaceWidth);
                        innerWall.setFill(wallInnerColor);
                        root.getChildren().add(innerWall);
                    }

                    if (j < map[0].length - 1 && map[i][j + 1] == 'x') {
                        Rectangle innerWall = new Rectangle(j * oneBlockSize + wallOffset, i * oneBlockSize + wallOffset,
                                wallSpaceWidth + wallOffset, wallSpaceWidth);
                        innerWall.setFill(wallInnerColor);
                        root.getChildren().add(innerWall);
                    }

                    if (i < map.length - 1 && map[i + 1][j] == 'x') {
                        Rectangle innerWall = new Rectangle(j * oneBlockSize + wallOffset, i * oneBlockSize + wallOffset,
                                wallSpaceWidth, wallSpaceWidth + wallOffset);
                        innerWall.setFill(wallInnerColor);
                        root.getChildren().add(innerWall);
                    }

                    if (i > 0 && map[i - 1][j] == 'x') {
                        Rectangle innerWall = new Rectangle(j * oneBlockSize + wallOffset, i * oneBlockSize,
                                wallSpaceWidth, wallSpaceWidth + wallOffset);
                        innerWall.setFill(wallInnerColor);
                        root.getChildren().add(innerWall);
                    }
                }
            }
        }
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> {
            update();
        });

        this.setFocusTraversable(true);
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
        if (gameManager.getState() == GameState.PAUSE || gameManager.getState() == GameState.GAMEOVER || gameManager.getState() == null) {
            this.setVisible(false);
        }

        setVisible(true);
    }

    public void evolve() {

    }

}
