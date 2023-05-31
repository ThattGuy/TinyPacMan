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
    private StringBuilder currentLevel;
    private Level level;
    private int levelNumber;
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
        currentLevel = new StringBuilder();
        currentLevel.append("files/Level").append(String.format("%02d", FIRSTLEVEL)).append(".txt");
        level = createLevel();
        if(level == null){
            return;
        }
        level.spawnLiveElements();
        level.getLevel();
        playerLives = 3;
    }

    private Level createLevel() {
        FileReader fileReader = null;
        Level newLevel = null;

        try {
            File file = new File(currentLevel.toString());
            if (!file.exists()) {
                return null;
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

            newLevel = new Level(numRows, numCols);

            scanner.close();
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            scanner = new Scanner(bufferedReader);

            int row = 0, col = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (col = 0; col < line.length(); col++) {
                    char character = line.charAt(col);
                    newLevel.addElement(createElement(character), row, col);
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

        return newLevel;
    }

    private Element createElement(char symbol){
        Element element = null;
        Elements elements = Elements.getElement(symbol);
        switch (elements){
            case WALL -> element = new Wall(level);
            case WARP-> element = new Warp(level);
            case SMALLBALL -> element = new SmallBall(level);
            case FRUITSPAWN -> element = new FruitZone(level);
            case PACMANSPAWN-> element = new PacmanSpawn(level);
            case POWERUP -> element = new PowerUp(level);
            case PORTAL -> element = new Portal(level);
            case CAVERN -> element = new Cavern(level);
        }

        return element;
    }


    public void setCurrentLevel(int currentLevel) {
        if (currentLevel >= FIRSTLEVEL && currentLevel <= MAXLEVEL){
            this.currentLevel.append("Level").append(currentLevel).append("." + "txt");

        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public char[][] getLevel(){
        return level.getLevel();
    }

    public void setDirection(KEYPRESS KEYPRESS) {
        level.setDirection(KEYPRESS);
    }

    public void evolve(long currentTime) {
        if(level == null)
            return;

        if(!level.isPacmanAlive() && playerLives > 0){
            level.spawnLiveElements();
            playerLives--;
        }

        level.evolve(currentTime);
    }
}