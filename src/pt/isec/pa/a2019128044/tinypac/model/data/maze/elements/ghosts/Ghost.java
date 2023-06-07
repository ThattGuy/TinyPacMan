package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Cavern;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.PacmanSpawn;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Portal;

import java.util.ArrayList;
import java.util.List;

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

    public void addMove(int x, int y) {
        movements.add(new Integer[x][y]);
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

        int exitRow = exitPosition.y();
        int exitColumn = exitPosition.x();

        if (myRow == exitRow) {

            if (myColumn < exitColumn) {

                moveTowardsExit(KEYPRESS.RIGHT);
            } else if (myColumn > exitColumn) {
                moveTowardsExit(KEYPRESS.LEFT);
            } else {
                inSpawn = false;
            }
        } else if (myColumn == exitColumn) {

            if (myRow < exitRow) {
                moveTowardsExit(KEYPRESS.DOWN);
            } else if (myRow > exitRow) {
                moveTowardsExit(KEYPRESS.UP);
            } else {
                inSpawn = false;
            }
        } else {
            if (myRow < exitRow) {
                moveTowardsExit(KEYPRESS.DOWN);
            } else {
                moveTowardsExit(KEYPRESS.UP);
            }

            if (myColumn < exitColumn) {
                moveTowardsExit(KEYPRESS.RIGHT);
            } else {
                moveTowardsExit(KEYPRESS.LEFT);
            }
        }
    }

    private void moveTowardsExit(KEYPRESS direction) {
        Level.Position myPosition = level.getPositionOf(this);
        Level.Position neighborPosition = level.getNeighboorPosition(myPosition, direction);
        Element neighbor = (Element) level.getElement(neighborPosition);

        if (neighbor != null && neighbor.isTransversable(symbol) != null) {
            movements.add(new Integer[myPosition.y()][myPosition.x()]);
            level.setElementPosition(this, neighborPosition);
        }
    }

    protected void run() {
        //TODO REVERTER MOVIMENTOS
        //todo voltar a seguir assim que voltar ao spawn
    }

    abstract void follow();

}
