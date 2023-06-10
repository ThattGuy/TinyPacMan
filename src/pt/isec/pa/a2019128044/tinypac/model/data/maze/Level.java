package pt.isec.pa.a2019128044.tinypac.model.data.maze;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Elements;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Pacman;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts.*;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

public class Level {
    private int height, width;
    private Maze maze;
    private KEYPRESS keypress;
    private KEYPRESS nextDirection;
    int points;
    Position portalPosition;
    Position fruitZonePosition;
    boolean powerUp;
    boolean pacmanAlive;


    public record Position(int y, int x) {}

    public Level(int height, int width) {
        this.height = height;
        this.width = width;
        this.maze = new Maze(height, width);
        points = 0;
        powerUp = false;
    }

    public void addElement(Element element, int y, int x) {
        maze.set(y, x, element);
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
                    pacmanAlive = true;
                }
            }

        }
    }

    public char[][] getLevel() {
        return maze.getMaze();
    }

    public void movePacman(long currentTime) {
        Position pacmanPos = getPacmanPos();
        checkNextKey();

        if(maze.get(pacmanPos.y(), pacmanPos.x()) instanceof Element element){
            element.evolve(currentTime);
        }
    }

    public void evolveAll(long currentTime) {

        checkNextKey();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Element element) {
                    if(!element.hasEvolved()){
                        element.setEvolved(true);
                        element.evolve(currentTime);
                    }
                }
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Element element) {
                    element.setEvolved(false);
                }
            }
        }
    }

    public void setPortalPos(int y, int x){
        portalPosition = new Position(y,x);
    }

    public Position getPortalPosition() {
        return portalPosition;
    }

    public void setPowerUp(boolean powerUp) {
        this.powerUp = powerUp;
    }

    public boolean atePowerUp() {
        return powerUp;
    }

    public void setGhostsVulnerability(boolean value) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Ghost ghost) {
                    ghost.setVulnerability(value);
                    if(ghost.getOldElement() instanceof Ghost oldGhost){
                        oldGhost.setVulnerability(value);
                    }
                }
            }
        }
    }

    public void setFruitZonePos(int row, int col) {
        fruitZonePosition = new Position(row,col);
    }

    public Position getFruitZonePosition(){
        return fruitZonePosition;
    }

    public Position getNeighborPosition(Position currentPosition, KEYPRESS KEYPRESS) {
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

    public Position getPositionOf(Element element) {
        for(int y = 0; y < height;y++)
            for(int x = 0;x < width; x++)
                if (maze.get(y,x) == element)
                    return new Position(y,x);
        return null;
    }

    public Position getPacmanPos(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Pacman) {
                    return new Position(y,x);
                }
            }
        }
        return null;
    }

    public boolean isPacmanVisible(Element clyde) {
        Position clydePos = getPositionOf(clyde);
        Position pacmanPos = getPacmanPos();

        if (clydePos == null || pacmanPos == null) {
            return false;
        }

        // Check left direction
        for (int x = clydePos.x - 1; x >= 0; x--) {
            Element element = (Element) maze.get(clydePos.y, x);
            if (element == null) {
                break;
            } else if (element.getSymbol() == 'P') {
                return true;
            } else if (element.isTraversable(clyde.getSymbol()) == null) {
                break;
            }
        }

        // Check right direction
        for (int x = clydePos.x + 1; x < width; x++) {
            Element element = (Element) maze.get(clydePos.y, x);
            if (element == null) {
                break;
            } else if (element.getSymbol() == 'P') {
                return true;
            } else if (element.isTraversable(clyde.getSymbol()) == null) {
                break;
            }
        }

        // Check up direction
        for (int y = clydePos.y - 1; y >= 0; y--) {
            Element element = (Element) maze.get(y, clydePos.x);
            if (element == null) {
                break;
            } else if (element.getSymbol() == 'P') {
                return true;
            } else if (element.isTraversable(clyde.getSymbol()) == null) {
                break;
            }
        }

        // Check down direction
        for (int y = clydePos.y + 1; y < height; y++) {
            Element element = (Element) maze.get(y, clydePos.x);
            if (element == null) {
                break;
            } else if (element.getSymbol() == 'P') {
                return true;
            } else if (element.isTraversable(clyde.getSymbol()) == null) {
                break;
            }
        }

        return false;
    }

    public void setDirection(KEYPRESS keypress) {

        Position neightbor = getNeighborPosition(getPacmanPos(),keypress);

        nextDirection = keypress;

        if(getElement(neightbor) instanceof Wall || getElement(neightbor) instanceof Portal || getElement(neightbor) instanceof Warp){
            return;
        }

        this.keypress = keypress;
    }

    private void checkNextKey() {
        Position neightbor = getNeighborPosition(getPacmanPos(),nextDirection);

        if(!(getElement(neightbor) instanceof Wall || getElement(neightbor) instanceof Portal || getElement(neightbor) instanceof Warp)){
            keypress = nextDirection;
        }
    }

    public KEYPRESS getDirection() {
        return keypress;
    }

    public  IMazeElement getElement(Position position){
        if(position == null){
            return null;
        }

        return maze.get(position.y, position.x);
    }

    public void setElementPosition(Element element, Position nextPosition){
        if(nextPosition == null)
            return;

        Position elementPos = getPositionOf(element);

        maze.set(nextPosition.y,nextPosition.x,element);

        if(elementPos != null){
            maze.set(elementPos.y,elementPos.x, element.getOldElement());
        }

    }

    public Position findCavern(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Cavern) {
                    return new Position(y,x);
                }
            }
        }
        return null;
    }

    public boolean isPacmanAlive(){
        return pacmanAlive;
    }

    public void killPacman() {
        pacmanAlive = false;
    }

    public int getPoints(){
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void respawnGhost(char type){

        Position cavern = findCavern();

        switch (type) {
            case 'B' -> maze.set(cavern.y, cavern.x, new Blinky(this));
            case 'C' -> maze.set(cavern.y, cavern.x, new Clyde(this));
            case 'I' -> maze.set(cavern.y, cavern.x, new Inky(this));
            case 'R' -> maze.set(cavern.y, cavern.x, new Pinky(this));
        }
    }

    public void removeLiveElements(){

        Element oldElement;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Ghost ghost) {
                    if(ghost.getOldElement() instanceof Ghost oldGhost){
                        oldElement = oldGhost.getOldElement();
                        maze.set(y,x,oldElement);
                        continue;
                    }
                    oldElement = ghost.getOldElement();
                    maze.set(y,x,oldElement);
                }
                if(maze.get(y, x) instanceof Pacman pacman){
                    oldElement = pacman.getOldElement();
                    maze.set(y,x,oldElement);
                }
            }
        }

    }

    public void killElement (Element element){
        if(element == null)
            return;

        Position elementPos = getPositionOf(element);

        if(elementPos != null){
            maze.set(elementPos.y,elementPos.x, element.getOldElement());
        }

    }


    public Position getCorner(String corner){
        switch (corner){
            case "TopLeft" -> {
                return new Position(0,0);
            }
            case "TopRight" -> {
                return new Position(0,width - 1);
            }
            case "BottonLeft" -> {
                return new Position(height - 1, 0);
            }
            case "BottomRight" -> {
                return new Position(height - 1, width -1);
            }

        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDistanceBetweenPositions(Position myPos, Position targetCornerPos) {
        int deltaX = Math.abs(myPos.x() - targetCornerPos.x());
        int deltaY = Math.abs(myPos.y() - targetCornerPos.y());
        return (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }




}