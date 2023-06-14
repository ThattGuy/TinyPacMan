package pt.isec.pa.a2019128044.tinypac.ui.gui.uistates;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.ImageManager;

public class GamePlayUI extends BorderPane {

    GameManager gameManager;
    int oneBlockSize = 20;
    int score = 0;
    double wallSpaceWidth = oneBlockSize / 1.4;
    double wallOffset = (oneBlockSize - wallSpaceWidth) / 2;
    Color wallInnerColor = Color.BLACK;

    private static final double FRAME_RATE = 60.0;
    private static final double INTERVAL = 1.0 / FRAME_RATE; // Time interval between frames

    private long lastUpdateTime = 0; // Time of the last frame update

    boolean start;

    char[][] map;

    public GamePlayUI(GameManager gameManager) {
        this.gameManager = gameManager;
        registerHandlers();
        createViews();
        update();
        setVisible(false);
    }

    private void createViews() {
        Pane level = new Pane();

        map = gameManager.getMaze();
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, null, null);
        Background background = new Background(backgroundFill);
        level.setBackground(background);

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

                if (map[i][j] == 'Y') {
                    Rectangle outerWall = new Rectangle(j * oneBlockSize, i * oneBlockSize, oneBlockSize, oneBlockSize);
                    outerWall.setFill(Color.BLACK);
                    outerWall.setStroke(Color.WHITE);
                    level.getChildren().add(outerWall);
                }


                if (map[i][j] == 'W') {
                    ImageView imageView = new ImageView(ImageManager.getImage("warp.gif"));
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'P') {
                    ImageView imageView = new ImageView(ImageManager.getImage("pacmanRight.gif"));
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'C') {
                    ImageView imageView = new ImageView(ImageManager.getImage("clyde.png"));
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'I') {
                    ImageView imageView = new ImageView(ImageManager.getImage("inky.png"));
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'B') {
                    ImageView imageView = new ImageView(ImageManager.getImage("blinky.png"));
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'R') {
                    ImageView imageView = new ImageView(ImageManager.getImage("pinky.png"));
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'O') {
                    ImageView imageView = new ImageView(ImageManager.getImage("ball.png"));
                    imageView.setFitWidth((double) oneBlockSize / 1.7);
                    imageView.setFitHeight((double) oneBlockSize / 1.7);
                    imageView.setX(j * oneBlockSize + (oneBlockSize - imageView.getFitWidth()) / 2);
                    imageView.setY(i * oneBlockSize + (oneBlockSize - imageView.getFitHeight()) / 2);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }


                if (map[i][j] == 'o') {
                    ImageView imageView = new ImageView(ImageManager.getImage("ball.png"));
                    imageView.setFitWidth((double) oneBlockSize / 4.5);
                    imageView.setFitHeight((double) oneBlockSize / 4.5);
                    imageView.setX(j * oneBlockSize + (oneBlockSize - imageView.getFitWidth()) / 2);
                    imageView.setY(i * oneBlockSize + (oneBlockSize - imageView.getFitHeight()) / 2);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'f') {
                    //todo fix fruit spawn
                    ImageView imageView = new ImageView(ImageManager.getImage("fruit.png"));
                    imageView.setFitWidth(oneBlockSize );
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize + (oneBlockSize - imageView.getFitWidth()) / 2);
                    imageView.setY(i * oneBlockSize + (oneBlockSize - imageView.getFitHeight()) / 2);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

                if (map[i][j] == 'v') {
                    ImageView imageView = new ImageView(ImageManager.getImage("blueGhost.gif"));
                    imageView.setFitWidth(oneBlockSize);
                    imageView.setFitHeight(oneBlockSize);
                    imageView.setX(j * oneBlockSize);
                    imageView.setY(i * oneBlockSize);
                    imageView.setPreserveRatio(true);
                    level.getChildren().add(imageView);
                }

            }
        }

        level.setMaxSize(map[0].length * oneBlockSize, map.length * oneBlockSize);
        StackPane stackPane = new StackPane(level);
        stackPane.setAlignment(Pos.CENTER);
        this.setCenter(stackPane);
    }


    private void registerHandlers() {

        gameManager.addPropertyChangeListener(evt -> {
            if(evt.getPropertyName().equals("start")){
                start = true;
                return;
            }



            if(evt.getPropertyName().equals("keypress") && evt.getNewValue() != null) {
                KEYPRESS keypress = (KEYPRESS) evt.getNewValue();
            }
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

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                // Calculate the elapsed time since the last frame
                double elapsedTime = (currentTime - lastUpdateTime) / 1_000_000_000.0; // Convert nanoseconds to seconds

                // Check if enough time has passed to execute the method
                if (elapsedTime >= INTERVAL) {
                    // Call your method here
                    if(start) {
                        update();
                    }

                    // Update the last update time
                    lastUpdateTime = currentTime;
                }
            }
        };

        // Start the animation timer
        animationTimer.start();
    }

    private void update() {
        if (gameManager.getState() == GameState.PAUSE || gameManager.getState() == GameState.GAMEOVER) {
            this.setVisible(false);
            return;
        }

        setVisible(true);
        createViews();
    }

}
