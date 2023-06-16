package pt.isec.pa.a2019128044.tinypac.ui.gui.uistates;

import javafx.animation.AnimationTimer;
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
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.ImageManager;

import java.util.ArrayList;
import java.util.List;

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

    KEYPRESS pacmanDir;
    int prevPacmanRow;
    int prevPacmanCol;
    String oldPacmanDirPic;

    List<ImageView> mapView;
    Pane level;

    public GamePlayUI(GameManager gameManager) {
        this.gameManager = gameManager;
        mapView = new ArrayList<>();
        oldPacmanDirPic = "pacmanRight.gif";
        prevPacmanRow = -1;
        prevPacmanCol = -1;
        level = new Pane();
        registerHandlers();
        createViews();
        update();
        setVisible(false);
    }

    private ImageView getSizedImageView(String name, int j, int i) {
        ImageView imageView = new ImageView(ImageManager.getImage(name));
        imageView.setFitWidth(oneBlockSize);
        imageView.setFitHeight(oneBlockSize);
        imageView.setX(j * oneBlockSize);
        imageView.setY(i * oneBlockSize);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private void setFormatedImage(ImageView imageView,String name, int j, int i) {
        imageView.setImage(ImageManager.getImage(name));
        imageView.setFitWidth(oneBlockSize);
        imageView.setFitHeight(oneBlockSize);
        imageView.setX(j * oneBlockSize);
        imageView.setY(i * oneBlockSize);
        imageView.setPreserveRatio(true);
    }

    private void createViews() {
        level = new Pane();
        map = gameManager.getMaze();
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, null, null);
        Background background = new Background(backgroundFill);
        level.setBackground(background);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'x') {
                    Rectangle outerWall = new Rectangle(j * oneBlockSize, i * oneBlockSize, oneBlockSize, oneBlockSize);
                    outerWall.setFill(Color.BLUE);
                    mapView.add(new ImageView());
                    level.getChildren().add(outerWall);
                }


                if (j > 0 && map[i][j - 1] == 'x') {
                    Rectangle innerWall = new Rectangle(j * oneBlockSize, i * oneBlockSize + wallOffset,
                            wallSpaceWidth + wallOffset, wallSpaceWidth);
                    innerWall.setFill(wallInnerColor);
                    mapView.add(new ImageView());
                    level.getChildren().add(innerWall);
                }

                if (j < map[0].length - 1 && map[i][j + 1] == 'x') {
                    Rectangle innerWall = new Rectangle(j * oneBlockSize + wallOffset, i * oneBlockSize + wallOffset,
                            wallSpaceWidth + wallOffset, wallSpaceWidth);
                    innerWall.setFill(wallInnerColor);
                    mapView.add(new ImageView());
                    level.getChildren().add(innerWall);
                }

                if (i < map.length - 1 && map[i + 1][j] == 'x') {
                    Rectangle innerWall = new Rectangle(j * oneBlockSize + wallOffset, i * oneBlockSize + wallOffset,
                            wallSpaceWidth, wallSpaceWidth + wallOffset);
                    innerWall.setFill(wallInnerColor);
                    mapView.add(new ImageView());
                    level.getChildren().add(innerWall);
                }

                if (i > 0 && map[i - 1][j] == 'x') {
                    Rectangle innerWall = new Rectangle(j * oneBlockSize + wallOffset, i * oneBlockSize,
                            wallSpaceWidth, wallSpaceWidth + wallOffset);
                    innerWall.setFill(wallInnerColor);
                    mapView.add(new ImageView());
                    level.getChildren().add(innerWall);
                }

                if (map[i][j] == 'Y') {
                    Rectangle outerWall = new Rectangle(j * oneBlockSize, i * oneBlockSize, oneBlockSize, oneBlockSize);
                    outerWall.setFill(Color.BLACK);
                    outerWall.setStroke(Color.WHITE);
                    level.getChildren().add(outerWall);
                    mapView.add(new ImageView());
                }


                if (map[i][j] == 'W') {
                    ImageView imageView = getSizedImageView("warp.gif", j, i);
                    mapView.add(imageView);
                    level.getChildren().add(mapView.get(map[0].length * i + j));
                }
                if (map[i][j] == 'F') {
                    mapView.add(new ImageView());
                    level.getChildren().add(mapView.get(map[0].length * i + j));
                }

                if (map[i][j] == 'P') {
                    String pacmanPic = getPacmanDirImageName(i,j);
                    ImageView imageView = getSizedImageView(pacmanPic, j, i);
                    mapView.add(imageView);
                    level.getChildren().add(mapView.get(map[0].length * i + j));
                    oldPacmanDirPic = pacmanPic;
                    prevPacmanRow = i;
                    prevPacmanCol = j;
                }

                if (map[i][j] == 'C') {
                    ImageView imageView = getSizedImageView("clyde.png", j, i);
                    mapView.add(imageView);
                    level.getChildren().add(mapView.get(map[0].length * i + j));
                }

                if (map[i][j] == 'I') {
                    ImageView imageView = getSizedImageView("inky.png", j, i);
                    mapView.add(imageView);
                    level.getChildren().add(mapView.get(map[0].length * i + j));
                }

                if (map[i][j] == 'B') {
                    ImageView imageView = getSizedImageView("blinky.png", j, i);
                    mapView.add(imageView);
                    level.getChildren().add(mapView.get(map[0].length * i + j));
                }

                if (map[i][j] == 'R') {
                    ImageView imageView = getSizedImageView("pinky.png", j, i);
                    mapView.add(imageView);
                    level.getChildren().add(mapView.get(map[0].length * i + j));
                }

                if (map[i][j] == 'O') {
                    ImageView imageView = new ImageView(ImageManager.getImage("ball.png"));
                    imageView.setFitWidth((double) oneBlockSize / 1.7);
                    imageView.setFitHeight((double) oneBlockSize / 1.7);
                    imageView.setX(j * oneBlockSize + (oneBlockSize - imageView.getFitWidth()) / 2);
                    imageView.setY(i * oneBlockSize + (oneBlockSize - imageView.getFitHeight()) / 2);
                    imageView.setPreserveRatio(true);
                    mapView.add(imageView);
                    level.getChildren().add(mapView.get(map[0].length * i + j));
                }


                if (map[i][j] == 'o') {
                    ImageView imageView = new ImageView(ImageManager.getImage("ball.png"));
                    imageView.setFitWidth((double) oneBlockSize / 4.5);
                    imageView.setFitHeight((double) oneBlockSize / 4.5);
                    imageView.setX(j * oneBlockSize + (oneBlockSize - imageView.getFitWidth()) / 2);
                    imageView.setY(i * oneBlockSize + (oneBlockSize - imageView.getFitHeight()) / 2);
                    imageView.setPreserveRatio(true);
                    mapView.add(imageView);
                    level.getChildren().add(mapView.get(map[0].length * i + j));
                }
            }
        }


        level.setMaxSize(map[0].length * oneBlockSize, map.length * oneBlockSize);
        StackPane stackPane = new StackPane(level);
        stackPane.setAlignment(Pos.CENTER);
        this.setCenter(stackPane);
    }


    private void registerHandlers() {
        // gameManager.addPropertyChangeListener("evolve", ....);
        gameManager.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("evolve") && start) {
                Platform.runLater(this::update);
                return;
            }

            if (evt.getPropertyName().equals("start")) {
                start = true;
                return;
            }

            if (evt.getPropertyName().equals("keypress") && evt.getNewValue() != null) {
                pacmanDir = (KEYPRESS) evt.getNewValue();
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


        //todo perguntar ao professor se posso usar
        /*AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                double elapsedTime = (currentTime - lastUpdateTime) / 1_000_000_000.0; // Convert nanoseconds to seconds

                if (elapsedTime >= INTERVAL) {
                    if(start) {
                        update();
                    }
                    lastUpdateTime = currentTime;
                }
            }
        };

        animationTimer.start();*/
    }

    private void update() {
        if (gameManager.getState() == GameState.PAUSE || gameManager.getState() == GameState.GAMEOVER) {
            this.setVisible(false);
            return;
        }
        if(gameManager.getState() == GameState.INITIAL && start){
            //todo fix this
            createViews();
        }

        setVisible(true);

        map = gameManager.getMaze();

        for (int i = 0; i < map.length; i++) {
            int rowLength = map[0].length;
            for (int j = 0; j < rowLength; j++) {
                ImageView image = mapView.get(rowLength * i + j);
                switch(map[i][j]) {
                    case 'W' -> setFormatedImage(image,"warp.gif",j,i);
                    case 'P' -> {
                        String pacmanPic = getPacmanDirImageName(i,j);
                        setFormatedImage(image, pacmanPic, j, i);
                        oldPacmanDirPic = pacmanPic;
                        prevPacmanRow = i;
                        prevPacmanCol = j;
                    }
                    case 'C' -> setFormatedImage(image,"clyde.png",j,i);
                    case 'I' -> setFormatedImage(image,"inky.png",j,i);
                    case 'B' -> setFormatedImage(image,"blinky.png",j,i);
                    case 'R' -> setFormatedImage(image,"pinky.png",j,i);
                    case 'v' -> setFormatedImage(image,"blueghost.gif",j,i);
                    case 'O' -> {
                        setFormatedImage(image,"ball.png",j,i);
                        image.setFitWidth((double) oneBlockSize / 1.7);
                        image.setFitHeight((double) oneBlockSize / 1.7);
                        image.setX(j * oneBlockSize + (oneBlockSize - mapView.get(rowLength * i + j).getFitWidth()) / 2);
                        image.setY(i * oneBlockSize + (oneBlockSize - mapView.get(rowLength * i + j).getFitHeight()) / 2);
                        image.setPreserveRatio(true);
                    }
                    case 'o' ->{
                        setFormatedImage(image,"ball.png",j,i);
                        image.setFitWidth((double) oneBlockSize / 4.5);
                        image.setFitHeight((double) oneBlockSize / 4.5);
                        image.setX(j * oneBlockSize + (oneBlockSize - mapView.get(rowLength * i + j).getFitWidth()) / 2);
                        image.setY(i * oneBlockSize + (oneBlockSize - mapView.get(rowLength * i + j).getFitHeight()) / 2);
                        image.setPreserveRatio(true);
                    }
                    case ' ', 'M', 'y', 'F'-> mapView.get(rowLength * i + j).setImage(null);
                    case 'f' -> setFormatedImage(image,"fruit.png",j,i);

                }
            }
        }

    }


    private String getPacmanDirImageName(int currentPacmanRow, int currentPacmanCol) {

        if (prevPacmanRow != -1 && prevPacmanCol != -1) {
            int rowDiff = prevPacmanRow - currentPacmanRow;
            int colDiff = prevPacmanCol - currentPacmanCol;

            if (rowDiff == -1) {
                return "pacmanDown.gif";
            } else if (rowDiff == 1) {
                return "pacmanUp.gif";
            } else if (colDiff == -1) {
                return "pacmanRight.gif";
            } else if (colDiff == 1) {
                return "pacmanLeft.gif";
            }
        }
        return oldPacmanDirPic;
    }

}