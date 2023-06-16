package pt.isec.pa.a2019128044.tinypac.model.data;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Elements;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameData {
    private final int FIRSTLEVEL = 1;
    private final int MAXLEVEL = 20;
    private volatile static String currentLevel;
    private Level level;
    private int levelNumber;
    //todo change level
    int points;
    int playerLives;

    static final Map<Character, Class<? extends Element>> elements = new HashMap<>() {{
        put('y', Cavern.class);
        put('F', FruitZone.class);
        put('M', PacmanSpawn.class);
        put('Y', Portal.class);
        put('O', PowerUp.class);
        put('o', SmallBall.class);
        put('x', Wall.class);
        put('W', Warp.class);
    }};

    public GameData() {
        setLevelNumber(FIRSTLEVEL);
        currentLevel = "files/Level" + String.format("%02d", getLevelNumber()) + ".txt";
        createLevel();
        if(level == null){
            return;
        }
        level.spawnLiveElements();
        level.getLevel();
        playerLives = 3;
    }

    private void createLevel() {
        FileReader fileReader = null;

        try {
            File file = new File(currentLevel);
            if (!file.exists()) {
                return;
            }

            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Scanner scanner = new Scanner(bufferedReader);


            int numRows = 0, numCols = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                numRows++;
                if (line.length() > numCols) {
                    numCols = line.length();
                }
            }

              level = new Level(numRows, numCols);

            scanner.close();
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            scanner = new Scanner(bufferedReader);

            int row = 0, col = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (col = 0; col < line.length(); col++) {
                    char character = line.charAt(col);
                    level.addElement(createElement(character,row,col), row, col);
                }
                row++;
            }
            scanner.close();

        } catch (IOException exception) {
            System.err.println("ERRO: ");
            exception.printStackTrace();
        } catch (Exception exception) {
            System.err.println("ERRO: ");
            exception.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException exception) {
                    System.err.println("ERRO: ");
                    exception.printStackTrace();
                }
            }
        }
    }

    private Element createElement(char symbol, int  row, int col){

        //todo garantir a existencia
        Element element = null;
        Elements elements = Elements.getElement(symbol);
        switch (elements){
            case WALL -> element = new Wall(level);
            case WARP-> {
                element = new Warp(level);
                level.setWarpPos(row,col);
            }
            case SMALLBALL -> element = new SmallBall(level);
            case FRUITSPAWN ->element = new FruitZone(level);
            case PACMANSPAWN-> element = new PacmanSpawn(level);
            case POWERUP -> element = new PowerUp(level);
            case PORTAL -> {
                element = new Portal(level);
                level.setPortalPos(row, col);
            }
            case CAVERN -> element = new Cavern(level);
        }

        return element;
    }

    public int getPoints(){
        return points;
    }

    public void changeLevel() {
        int currentLevelNumber = getLevelNumber();
        if (currentLevelNumber >= FIRSTLEVEL && currentLevelNumber <= MAXLEVEL){
            currentLevel = "files/Level" + '0' + (getLevelNumber() + 1) + ".txt";
            setLevelNumber(currentLevelNumber + 1);
            createLevel();
            level.spawnLiveElements();
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int newLevelNumber) {
        levelNumber = newLevelNumber;
    }

    public char[][] getLevel(){
        return level.getLevel();
    }

    public boolean atePowerUp(){
        return level.atePowerUp();
    }

    public void setPowerUp(boolean value){
        level.setPowerUp(value);
    }

    public boolean setDirection(KEYPRESS KEYPRESS) {
        return level.setDirection(KEYPRESS);
    }

    public void movePacman(long currentTime) {
        if(level == null)
            return;

        points = level.getPoints();

        level.movePacman(currentTime);
    }

    public void evolveAll(long currentTime){
        if(level == null)
            return;

        points = level.getPoints();
        level.evolveAll(currentTime);
    }

    public void setGhostsVulnerability(boolean value) {
        level.setGhostsVulnerability(value);
    }

    public boolean isPacmanAlive() {
        return level.isPacmanAlive();
    }

    public int getLives() {
        return playerLives;
    }

    public void restartLevel() {
        level.removeLiveElements();
        level.spawnLiveElements();
        playerLives--;
    }

    public int getBalls() {
        if(level != null){
            return level.getBalls();
        }

        return -1;
    }

    public boolean checkGhostsVulnerability() {
        return level.checkGhostsVulnerability();
    }
}