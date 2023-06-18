package pt.isec.pa.a2019128044.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameManager;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.a2019128044.tinypac.ui.gui.resources.MediaManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GamePlayUI extends BorderPane {

    GameManager gameManager;
    int oneBlockSize = 20;
    double wallSpaceWidth = oneBlockSize / 1.4;
    double wallOffset = (oneBlockSize - wallSpaceWidth) / 2;
    Color wallInnerColor = Color.BLACK;

    private long lastUpdateTime = 0; // Time of the last frame update
    char[][] map;
    int prevPacmanRow;
    int prevPacmanCol;
    String oldPacmanDirPic;
    List<ImageView> mapView;
    Pane level;
    MediaPlayer mediaPlayer;
    MediaPlayer pacmanSound;
    MediaPlayer ghostSound;
    boolean playSound;

    /**
     *
     * @param gameManager recebe o game manager de modo a poder tratar de eventos e acededer aos dados
     *                    inicializa as variaveis necessárias para criar as imageView, e os sons
     *
     */
    public GamePlayUI(GameManager gameManager) {
        this.gameManager = gameManager;

        mapView = new ArrayList<>();
        oldPacmanDirPic = "pacmanRight.gif";
        prevPacmanRow = -1;
        prevPacmanCol = -1;
        level = new Pane();
        pacmanSound = new MediaPlayer(MediaManager.getMedia("eat.wav"));
        ghostSound = new MediaPlayer(MediaManager.getMedia("ghostVulnerable.wav"));
        registerHandlers();
        createViews();
        update();
        playSound = true;
        setVisible(false);
    }

    /**
     *Este metodo cria e formata as imagens
     * @param name Nome da imagens
     * @param j linha
     * @param i coluna
     * @return retorna a imageView formatada
     */
    private ImageView getSizedImageView(String name, int j, int i) {
        ImageView imageView = new ImageView(ImageManager.getImage(name));
        imageView.setFitWidth(oneBlockSize);
        imageView.setFitHeight(oneBlockSize);
        imageView.setX(j * oneBlockSize);
        imageView.setY(i * oneBlockSize);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Metodo usado para atualizar as imagens do array do imageView
     * @param imageView Imagigeview da posição necessária
     * @param name nome da imagem
     * @param j coluna
     * @param i linha
     */
    private void setFormatedImage(ImageView imageView,String name, int j, int i) {
        imageView.setImage(ImageManager.getImage(name));
        imageView.setFitWidth(oneBlockSize);
        imageView.setFitHeight(oneBlockSize);
        imageView.setX(j * oneBlockSize);
        imageView.setY(i * oneBlockSize);
        imageView.setPreserveRatio(true);
    }

    /**
     * Cria as view assim que a classe é criada, e quando o nível é mudado
     * adiciona o numero de elentos necessário ao array de Imagevies
     */
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


    /**
     * Regista os handlers necessários
     * existe um handler para o evolve, start, e para receber inputs das teclas de jogo
     */
    private void registerHandlers() {


        gameManager.addPropertyChangeListener(GameManager.EVOLVE, evt -> {
            Platform.runLater(this::update);
        });

        gameManager.addPropertyChangeListener(GameManager.START, evt -> {
            setVisible(true);
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

    /**
     * atualiza os imageView de modo a aparecer a imagem que corresponde ao elemento do imaze,
     * atualiza o audio e se o BorderPane se encontra visível
     */
    private void update() {
        if (gameManager.getState() == GameState.PAUSE || gameManager.getState() == GameState.GAMEOVER) {
            playSound = true;
            ghostSound.stop();
            pacmanSound.stop();
            this.setVisible(false);
            return;
        }
        if(gameManager.getState() == GameState.INITIAL){
            if(playSound){
                mediaPlayer = new MediaPlayer(MediaManager.getMedia("start.wav"));
                mediaPlayer.play();
                playSound = false;
            }
            //todo fix this
            createViews();
        }

        if(gameManager.getState() != GameState.GHOSTS_VULNERABLE){
            ghostSound.stop();
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
                    case 'v' ->{
                        setFormatedImage(image,"blueghost.gif",j,i);
                        ghostSound.setOnEndOfMedia(() -> {
                            ghostSound.seek(ghostSound.getStartTime());
                            ghostSound.play();
                        });
                        ghostSound.play();
                    }
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


    /**
     *
     * Toca o audio do pacman
     * @param currentPacmanRow recebe a linha anterior que o pacman se encontravarecebe a linha anterior que o pacman se encontrava
     * @param currentPacmanCol recebe a coluna anterior que o pacman se encontravarecebe a linha anterior que o pacman se encontrava
     * @return Retorna o nome da imagem correta consoante a direção que o pacman se move
     */
    private String getPacmanDirImageName(int currentPacmanRow, int currentPacmanCol) {

        if(gameManager.getState() == GameState.PACMAN_VULNERABLE || gameManager.getState() == GameState.WARMUP){
            pacmanSound.setOnEndOfMedia(() -> {
                pacmanSound.seek(pacmanSound.getStartTime());
                pacmanSound.play();
            });

            pacmanSound.play();
        }

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