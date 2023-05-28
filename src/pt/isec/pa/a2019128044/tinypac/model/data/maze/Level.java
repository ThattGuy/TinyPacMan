package pt.isec.pa.a2019128044.tinypac.model.data.maze;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Elements;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Pacman;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts.*;

public class Level {
    private int height, width;
    private Maze maze;
    private KEYPRESS KEYPRESS;
    private KEYPRESS lastKEYPRESS;

    public record Position(int y, int x) {}

    public Level(int height, int width) {
        this.height = height;
        this.width = width;
        this.maze = new Maze(height, width);
        KEYPRESS = null;
    }

    public void addElement(Element element, int y, int x) {
        maze.set(y, x, element);
    }

    public void evolve(long currentTime) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Element element) {
                    element.evolve(currentTime);
                }
            }
        }
    }

    public Position getPositionOf(Element element) {
        for(int y = 0; y < height;y++)
            for(int x = 0;x < width; x++)
                if (maze.get(y,x) == element)
                    return new Position(y,x);
        return null;
    }

    public Position getNeighboorPosition(Position currentPosition, KEYPRESS KEYPRESS) {
        if(currentPosition == null) {
            return null;
        }
        if(KEYPRESS == null) {
            return null;
        }
        int newX = currentPosition.x;
        int newY = currentPosition.y;
        switch (KEYPRESS){
            case UP:
                newY -= 1;
                break;
            case DOWN:
                newY += 1;
                break;
            case LEFT:
                newX -= 1;
                break;
            case RIGHT:
                newX += 1;
                break;
        };

        if(newX < 0 || newX >= width || newY < 0 || newY >= height) {
            return null;
        }

        return new Position(newY, newX);
    }


    public void spawnLiveElements() {

        int nGhosts = 0;
        boolean pacmanExists = false;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze.get(i, j).getSymbol() == Elements.CAVERN.getValue() && nGhosts < 4) {
                    switch (nGhosts) {
                        case 0 -> maze.set(i, j, new Blinky(this));
                        case 1 -> maze.set(i, j, new Clyde(this));
                        case 2 -> maze.set(i, j, new Inky(this));
                        case 3 -> maze.set(i, j, new Pinky(this));
                    }
                    nGhosts++;
                }

                if (maze.get(i, j).getSymbol() == Elements.PACMANSPAWN.getValue() && !pacmanExists) {
                    maze.set(i,j, new Pacman(this));
                }
            }

        }
    }

    public char[][] getLevel() {
        return maze.getMaze();
    }

    public void setDirection(KEYPRESS KEYPRESS) {
        lastKEYPRESS = this.KEYPRESS;
        this.KEYPRESS = KEYPRESS;
    }

    public KEYPRESS getDirection() {
        return KEYPRESS;
    }

    public  IMazeElement getElement(Position position){
        if(position == null){
            return null;
        }

        return maze.get(position.y, position.x);
    }

    public boolean setElementPosition(IMazeElement element, Position nextPosition){

        if(nextPosition == null)
            return false;

        IMazeElement elementAtPosition = this.getElement(nextPosition);

        if(elementAtPosition.getSymbol() == Elements.WALL.getValue()) {
            if(element.getSymbol() == Elements.PACMAN.getValue()){
                KEYPRESS = lastKEYPRESS;
            }
            return false;
        }


        maze.set(nextPosition.y,nextPosition.x,element);

        return true;
    }

}