package pt.isec.pa.a2019128044.tinypac.model.data;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Elements;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameData implements Serializable {
    public final int FIRSTLEVEL = 1;
    public final int MAXLEVEL = 20;
    private volatile static String currentLevel;
    private Level level;
    private int levelNumber;
    private int points;
    private int playerLives;

    /**
     * Construtor dos dados
     * inicializa o currentLevel, cria o level, adiciona os fantasmas e o pacman, inicia as vidas do jogador
     */
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

    /**
     * le o ficheiro que contemo nivel, cria o nível com o numero de coluna e linhas necessário, preenche o level com os caracteres do ficheiro
     * @return retorna true caso o ficheiro exista e a criação do level funcione, retorna false caso não consiga carregar o ficheiro ou não consiga criar o level
     */

    private boolean createLevel() {
        FileReader fileReader = null;

        try {
            File file = new File(currentLevel);
            if (!file.exists()) {
                return false;
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

        return true;
    }

    /**
     * Cria os elementos do level
     * @param symbol recebe o caracter
     * @param row linha em que o elemento se encontra de modo para que a  class Level possa gravar a posição do PORTAL
     * @param col  coluna em que o elemento se encontra de modo para que a  class Level possa gravar a posição do PORTAl
     *             Optei por fazer assim em vez de estar a percorrer os elementos do level à procura da posição do portal de modo a os fantasmas poderem sair do spawn
     * @return retorna o elemento
     */
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

    /**
     *
     * @return pontos do nível mais os pontos totais do jogador
     */
    public int getPoints(){
        return points + level.getPoints();
    }

    /**
     * muda de nível, caso não consiga avançar para o nível seguinte recarrega o mesmo nível
     */
    public void changeLevel() {
        points += level.getPoints();
        int currentLevelNumber = getLevelNumber();
        if (currentLevelNumber >= FIRSTLEVEL && currentLevelNumber <= MAXLEVEL){
            currentLevel = "files/Level" + '0' + (getLevelNumber() + 1) + ".txt";
            setLevelNumber(currentLevelNumber + 1);
            if(!createLevel()){
                currentLevel = "files/Level" + '0' + (getLevelNumber() - 1) + ".txt";
                setLevelNumber(currentLevelNumber - 1);
                createLevel();
            }
            level.spawnLiveElements();
        }
    }

    /**
     *
     * @return retorna numero do nível atual
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     *
     * @param newLevelNumber nível seguinte
     */
    private void setLevelNumber(int newLevelNumber) {
        levelNumber = newLevelNumber;
    }

    /**
     *
     * @return retorna um array de caracters que representa os elemtnos do nível
     *
     */
    public char[][] getLevel(){
        return level.getLevel();
    }

    /**
     *
     * @return retorna verdade caso o pacman tenha comido o powerUp
     */
    public boolean atePowerUp(){
        return level.atePowerUp();
    }

    /**
     * muda valor do power up
     * @param value true or false
     *              usado pelo estado ghost caso o tempo do powerUp este torna-se false
     */
    public void setPowerUp(boolean value){
        level.setPowerUp(value);
    }

    /**
     *
     * @param KEYPRESS tecla que represanta a direção desejada
     * @return retorna true caso a direção seja aceite
     */
    public boolean setDirection(KEYPRESS KEYPRESS) {
        return level.setDirection(KEYPRESS);
    }

    /**
     * indica ao level para mover apenas opacman
     */
    public void movePacman( ) {
        if(level == null)
            return;

        level.movePacman();
    }

    /**
     * indica ao level que todos os elementos devem evoluir
     */
    public void evolveAll( ){
        if(level == null)
            return;

        level.evolveAll();
    }

    /**
     *indica ao level para mudar a vulnerabilidade dos fantasmas
     * @param value true, vulneravel ou false, não vulnerável
     */
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