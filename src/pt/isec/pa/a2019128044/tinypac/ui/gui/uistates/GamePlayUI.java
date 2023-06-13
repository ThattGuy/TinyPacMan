package pt.isec.pa.a2019128044.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;

public class GamePlayUI extends BorderPane {

    GameManager gameManager;
    int oneBlockSize = 20;
    int score = 0;
    double wallSpaceWidth = oneBlockSize / 1.4;
    double wallOffset = (oneBlockSize - wallSpaceWidth) / 2;
    Color wallInnerColor = Color.BLACK;

    char[][] map;

    public GamePlayUI(GameManager gameManager) {
        this.gameManager = gameManager;
        map = gameManager.getMaze();

        registerHandlers();
        createViews();

        setVisible(false);
    }

    private void createViews() {
        Pane level = new Pane();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'x') {
                    Rectangle outerWall = new Rectangle(j * oneBlockSize, i * oneBlockSize, oneBlockSize, oneBlockSize);
                    outerWall.setFill(Color.BLUE);
                    level.getChildren().add(outerWall);
                }


                if (j > 0 && map[i][j - 1] == 'x') {
                    Rectangle innerWall = new Rectangle(j * oneBlockSize, i * oneBlockSize + wallOffset,
                            wallSpaceWidth + wallOffset, wallSpaceWidth);
                    innerWall.setFill(wallInnerColor);
                    level.getChildren().add(innerWall);
                }

                if (j < map[0].length - 1 && map[i][j + 1] == 'x') {
                    Rectangle innerWall = new Rectangle(j * oneBlockSize + wallOffset, i * oneBlockSize + wallOffset,
                            wallSpaceWidth + wallOffset, wallSpaceWidth);
                    innerWall.setFill(wallInnerColor);
                    level.getChildren().add(innerWall);
                }

                if (i < map.length - 1 && map[i + 1][j] == 'x') {
                    Rectangle innerWall = new Rectangle(j * oneBlockSize + wallOffset, i * oneBlockSize + wallOffset,
                            wallSpaceWidth, wallSpaceWidth + wallOffset);
                    innerWall.setFill(wallInnerColor);
                    level.getChildren().add(innerWall);
                }

                if (i > 0 && map[i - 1][j] == 'x') {
                    Rectangle innerWall = new Rectangle(j * oneBlockSize + wallOffset, i * oneBlockSize,
                            wallSpaceWidth, wallSpaceWidth + wallOffset);
                    innerWall.setFill(wallInnerColor);
                    level.getChildren().add(innerWall);
                }

                if (map[i][j] == 'P') {
                    Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/pacmanRight.gif");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'C') {
                    Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/clyde.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'I') {
                    Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/inky.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'B') {
                    Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/blinky.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'R') {
                    Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/pinky.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'O') {
                    Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/ball.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth((double) oneBlockSize / 1.7);
                    imageView.setFitHeight((double) oneBlockSize / 1.7);
                    imageView.setX(j * oneBlockSize + (oneBlockSize - imageView.getFitWidth()) / 2);
                    imageView.setY(i * oneBlockSize + (oneBlockSize - imageView.getFitHeight()) / 2);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }


                if (map[i][j] == 'o') {
                    Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/ball.png");
                    ImageView smallView = new ImageView(image);
                    smallView.setFitWidth((double) oneBlockSize / 4.5);
                    smallView.setFitHeight((double) oneBlockSize / 4.5);
                    smallView.setX(j * oneBlockSize + (oneBlockSize - smallView.getFitWidth()) / 2);
                    smallView.setY(i * oneBlockSize + (oneBlockSize - smallView.getFitHeight()) / 2);
                    smallView.setPreserveRatio(true);
                    level.getChildren().add(smallView);
                }

                if (map[i][j] == 'f') {
                    Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/fruit.png");
                    ImageView smallView = new ImageView(image);
                    smallView.setFitWidth((double) oneBlockSize / 4.5);
                    smallView.setFitHeight((double) oneBlockSize / 4.5);
                    smallView.setX(j * oneBlockSize + (oneBlockSize - smallView.getFitWidth()) / 2);
                    smallView.setY(i * oneBlockSize + (oneBlockSize - smallView.getFitHeight()) / 2);
                    smallView.setPreserveRatio(true);
                    level.getChildren().add(smallView);
                }

                if (map[i][j] == 'v') {
                    Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/blueghost.gif");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'W') {
                    Image image = new Image("pt/isec/pa/a2019128044/tinypac/ui/gui/resources/images/warp.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }


            }
        }

        StackPane stackPane = new StackPane(level);
        stackPane.setAlignment(Pos.CENTER);
        this.setCenter(stackPane);
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
        if (gameManager.getState() == GameState.PAUSE || gameManager.getState() == GameState.GAMEOVER) {
            this.setVisible(false);
        }

        setVisible(true);
    }

    public void evolve() {

    }

}
