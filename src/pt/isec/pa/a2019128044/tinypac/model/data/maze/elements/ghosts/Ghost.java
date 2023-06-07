package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Cavern;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Ghost extends Element {

    protected boolean inSpawn;
    protected long lastMovedTime = 0;
    protected boolean isAlive;
    protected boolean isVulnerable;

    protected KEYPRESS direction;
    protected List<Integer[][]> movements;

    public Ghost(char symbol, Level level) {
        super(symbol, level);
        this.isAlive = true;
        this.movements = new ArrayList<>();
        inSpawn = true;
        oldElement = new Cavern(level);
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }

    public void addMove(int y, int x) {
        movements.add(new Integer[y][x]);
    }

    @Override
    public void evolve(long currentTime) {

        if (inSpawn) {
            leaveCavern();
        } else if (isVulnerable()) {
            run();
        } else {
            follow();
        }
    }

    public void leaveCavern() {
        Level.Position exitPosition = level.getPortalPosition();


        Level.Position myPosition = level.getPositionOf(this);
        int myRow = myPosition.y();
        int myColumn = myPosition.x();

        int portalRow = exitPosition.y();
        int portalCol = exitPosition.x();

        if (myRow == portalRow) {

            if (myColumn < portalCol) {
                direction = KEYPRESS.RIGHT;
            } else if (myColumn > portalCol) {
                direction = KEYPRESS.LEFT;
            } else {
                inSpawn = false;
            }
        } else if (myColumn == portalCol) {

            if (myRow < portalRow) {
                direction = KEYPRESS.DOWN;
            } else if (myRow > portalRow) {
                direction = KEYPRESS.UP;
            } else {
                inSpawn = false;
            }
        } else {

            if (myColumn < portalCol) {
                direction = KEYPRESS.RIGHT;
            } else {
                direction = KEYPRESS.LEFT;
            }
        }

        moveTowardsExit();
    }

    private void moveTowardsExit() {
        Level.Position myPosition = level.getPositionOf(this);
        Level.Position neighborPosition = level.getNeighborPosition(myPosition, direction);
        Element neighbor = (Element) level.getElement(neighborPosition);

        if (neighbor != null && neighbor.isTraversable(symbol) != null) {
            addMove(myPosition.y(), myPosition.x());
            level.setElementPosition(this, neighborPosition);
            oldElement = neighbor.isTraversable(this.getSymbol());
        }
    }

    protected void run() {
        //TODO REVERTER MOVIMENTOS
        //todo voltar a seguir assim que voltar ao spawn
    }

    void follow() {
        Level.Position myPos = level.getPositionOf(this);
        Level.Position neighborPosition = level.getNeighborPosition(myPos, this.direction);
        Element neighbor = (Element) level.getElement(neighborPosition);

        if (neighbor == null || neighbor.isTraversable(this.getSymbol()) == null) {
            KEYPRESS newDirection = getRandomDirection();
            neighborPosition = level.getNeighborPosition(myPos, newDirection);
            neighbor = (Element) level.getElement(neighborPosition);

            if (neighbor != null && neighbor.isTraversable(this.getSymbol()) != null) {
                this.direction = newDirection;
            } else {
                // Go back if unable to turn either way
                this.direction = getOppositeDirection(this.direction);
                neighborPosition = level.getNeighborPosition(myPos, this.direction);
                neighbor = (Element) level.getElement(neighborPosition);

                if (neighbor == null || neighbor.isTraversable(this.getSymbol()) == null) {
                    return; // Unable to go back, stop moving
                }
            }
        }

        level.setElementPosition(this, neighborPosition);
        oldElement = neighbor.isTraversable(this.getSymbol());
        addMove(myPos.y(), myPos.x());

    }

    protected KEYPRESS getRandomDirection() {
        Random random = new Random();

        if (this.direction == KEYPRESS.UP || this.direction == KEYPRESS.DOWN) {
            int randomIndex = random.nextInt(2);
            return (randomIndex == 0) ? KEYPRESS.RIGHT : KEYPRESS.LEFT;
        } else {
            int randomIndex = random.nextInt(2);
            return (randomIndex == 0) ? KEYPRESS.UP : KEYPRESS.DOWN;
        }
    }

    protected KEYPRESS getOppositeDirection(KEYPRESS direction) {
        if (direction == KEYPRESS.UP) {
            return KEYPRESS.DOWN;
        } else if (direction == KEYPRESS.DOWN) {
            return KEYPRESS.UP;
        } else if (direction == KEYPRESS.LEFT) {
            return KEYPRESS.RIGHT;
        } else {
            return KEYPRESS.LEFT;
        }
    }

}
