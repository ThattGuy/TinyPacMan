package pt.isec.pa.a2019128044.tinypac.model.data.maze;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Elements;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Pacman;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts.*;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int height, width;
    private final Maze maze;
    private KEYPRESS keypress;
    private KEYPRESS nextDirection;
    int points;
    Position portalPosition;
    List<Position> warpPosition;
    boolean powerUp;
    boolean pacmanAlive;
    int ghostsEaten;
    int balls;
    int ghostVulnerable;
    public record Position(int y, int x) {
    }

    public Level(int height, int width) {
        this.height = height;
        this.width = width;
        this.maze = new Maze(height, width);
        points = 0;
        powerUp = false;
        warpPosition = new ArrayList<>();
        ghostsEaten = 1;
        ghostVulnerable = 0;
    }

    private int getNumberOfBalls() {
        int numberBalls = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze.get(i, j).getSymbol() == 'o' || maze.get(i, j).getSymbol() == 'O') {
                    numberBalls++;
                }
            }
        }
        return numberBalls;
    }

    public int getBalls() {
        return balls;
    }

    public void removeBall() {
        balls--;
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
                    maze.set(i, j, new Pacman(this));
                    pacmanAlive = true;
                }
            }

        }

        balls = getNumberOfBalls();
    }

    public char[][] getLevel() {
        return maze.getMaze();
    }

    public void movePacman(long currentTime) {
        Position pacmanPos = getPacmanPos();
        checkNextKey();

        if (maze.get(pacmanPos.y(), pacmanPos.x()) instanceof Element element) {
            element.evolve(currentTime);
        }
    }

    public void evolveAll(long currentTime) {

        checkNextKey();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Element element) {
                    if (!element.hasEvolved()) {
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

    public void setPortalPos(int y, int x) {
        portalPosition = new Position(y, x);
    }

    public void setWarpPos(int row, int col) {
        warpPosition.add(new Position(row, col));
    }

    public Position getPortalPosition() {
        return portalPosition;
    }

    public void setPowerUp(boolean powerUp) {
        if(powerUp){
            if(ghostsInSpawn()){
                return;
            }
        }

        this.powerUp = powerUp;
    }

    private boolean ghostsInSpawn() {
        int count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Ghost ghost) {
                    if (ghost.getOldElement().getSymbol() == 'y'){
                        count++;
                    }
                }
            }
        }
        if(count >= 4){
            return true;
        }
        return false;
    }

    public boolean atePowerUp() {
        return powerUp;
    }


    public Position getNeighborPosition(Position currentPosition, KEYPRESS KEYPRESS) {
        if (currentPosition == null) {
            return null;
        }
        if (KEYPRESS == null) {
            return null;
        }

        int newX = currentPosition.x;
        int newY = currentPosition.y;
        switch (KEYPRESS) {
            case UP -> newY -= 1;
            case DOWN -> newY += 1;
            case LEFT -> newX -= 1;
            case RIGHT -> newX += 1;
        }

        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            return null;
        }

        return new Position(newY, newX);
    }

    public Position getPositionOf(Element element) {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (maze.get(y, x) == element)
                    return new Position(y, x);
        return null;
    }

    public Position getPacmanPos() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Pacman) {
                    return new Position(y, x);
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

    public boolean setDirection(KEYPRESS keypress) {

        Position neightbor = getNeighborPosition(getPacmanPos(), keypress);

        nextDirection = keypress;

        if (getElement(neightbor) instanceof Wall || getElement(neightbor) instanceof Portal || getElement(neightbor) instanceof Warp) {
            return false;
        }

        this.keypress = keypress;
        return true;
    }

    private void checkNextKey() {
        Position neightbor = getNeighborPosition(getPacmanPos(), nextDirection);

        if (!(getElement(neightbor) instanceof Wall || getElement(neightbor) instanceof Portal || getElement(neightbor) instanceof Warp)) {
            keypress = nextDirection;
        }
    }

    public KEYPRESS getDirection() {
        return keypress;
    }

    public IMazeElement getElement(Position position) {
        if (position == null) {
            return null;
        }

        return maze.get(position.y, position.x);
    }

    public void setElementPosition(Element element, Position nextPosition) {
        if (nextPosition == null)
            return;

        Position elementPos = getPositionOf(element);

        if (element.getSymbol() == 'P') {

            if (checkIfSamePosition(nextPosition, warpPosition.get(0))) {
                maze.set(warpPosition.get(1).y, warpPosition.get(1).x, element);
                if (elementPos != null) {
                    maze.set(elementPos.y, elementPos.x, element.getOldElement());
                }
                return;
            }
            if (checkIfSamePosition(nextPosition, warpPosition.get(1))) {
                maze.set(warpPosition.get(0).y, warpPosition.get(0).x, element);
                if (elementPos != null) {
                    maze.set(elementPos.y, elementPos.x, element.getOldElement());
                }
                return;
            }

        }


        maze.set(nextPosition.y, nextPosition.x, element);

        if (elementPos != null) {
            maze.set(elementPos.y, elementPos.x, element.getOldElement());
        }

    }

    private boolean checkIfSamePosition(Position pos1, Position pos2) {

        if (pos1.x == pos2.x) {
            return pos1.y == pos2.y;
        }
        return false;
    }

    public Position findCavern() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Cavern) {
                    return new Position(y, x);
                }
            }
        }
        return null;
    }

    public boolean isPacmanAlive() {
        return pacmanAlive;
    }

    public void killPacman() {
        pacmanAlive = false;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }


    public void removeLiveElements() {

        Element oldElement;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Ghost ghost) {

                    Element element = ghost.getOldElement();
                    while (element instanceof Ghost innerGhost) {
                        element = innerGhost.getOldElement();
                        ghost.setOldElement(element);
                    }
                    oldElement = ghost.getOldElement();
                    maze.set(y, x, oldElement);
                }
                if (maze.get(y, x) instanceof Pacman pacman) {
                    oldElement = pacman.getOldElement();
                    maze.set(y, x, oldElement);
                }
            }
        }

    }

    public void setGhostsVulnerability(boolean value) {
        if (!value) {
            ghostsEaten = 0;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Ghost ghost) {
                    ghost.setVulnerability(value);
                    if (ghost.getOldElement() instanceof Ghost oldGhost) {
                        oldGhost.setVulnerability(value);
                        if(value){
                            ghostVulnerable++;
                        }else {
                            ghostVulnerable--;
                        }
                    }
                }
            }
        }
    }

    public void respawnGhost(char type) {

        Position cavern = findCavern();

        switch (type) {
            case 'B' -> maze.set(cavern.y, cavern.x, new Blinky(this));
            case 'C' -> maze.set(cavern.y, cavern.x, new Clyde(this));
            case 'I' -> maze.set(cavern.y, cavern.x, new Inky(this));
            case 'R' -> maze.set(cavern.y, cavern.x, new Pinky(this));
        }

        if (ghostsEaten <= 4) {
            ghostsEaten++;
        }

        points += (ghostsEaten * 50);
    }


    public void killElement(Element element) {
        if (element == null)
            return;

        Position elementPos = getPositionOf(element);

        if (elementPos != null) {
            maze.set(elementPos.y, elementPos.x, element.getOldElement());
        }

    }


    public Position getCorner(String corner) {
        switch (corner) {
            case "TopLeft" -> {
                return new Position(0, 0);
            }
            case "TopRight" -> {
                return new Position(0, width - 1);
            }
            case "BottonLeft" -> {
                return new Position(height - 1, 0);
            }
            case "BottomRight" -> {
                return new Position(height - 1, width - 1);
            }

        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public boolean checkGhostsVulnerability() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze.get(y, x) instanceof Ghost ghost) {
                    if(ghost.isVulnerable()){
                        return true;
                    }
                }
            }
        }
        setPowerUp(false);
        return false;
    }

    public int getDistanceBetweenPositions(Position myPos, Position targetCornerPos) {
        int deltaX = Math.abs(myPos.x() - targetCornerPos.x());
        int deltaY = Math.abs(myPos.y() - targetCornerPos.y());
        return (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}