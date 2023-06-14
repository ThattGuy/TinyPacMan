package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Pacman;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Cavern;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Ghost extends Element {

    protected boolean inSpawn;
    protected boolean isVulnerable;
    protected KEYPRESS direction;
    protected List<Level.Position> movements;

    public Ghost(char symbol, Level level) {
        super(symbol, level);
        this.movements = new ArrayList<>();
        inSpawn = true;
        oldElement = new Cavern(level);
        isVulnerable = false;
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }

    public void addMove(int y, int x) {
        movements.add(new Level.Position(y, x));
    }

    @Override
    public Element isTraversable(char type) {
        return this;
    }

    @Override
    public void evolve(long currentTime) {

        if (inSpawn) {
            leaveCavern();
        } else if (isVulnerable()) {
            run();
        } else {
            follow();
            if(oldElement.getSymbol() == 'y'){
                inSpawn = true;
            }
        }
    }
    protected boolean moveTo(Level.Position position) {

        Level.Position myPosition = level.getPositionOf(this);
        int myRow = myPosition.y();
        int myColumn = myPosition.x();

        int elementRow = position.y();
        int elementCol = position.x();

        if (myRow == elementRow) {

            if (myColumn < elementCol) {
                direction = KEYPRESS.RIGHT;
            } else if (myColumn > elementCol) {
                direction = KEYPRESS.LEFT;
            } else {
                return true;
            }
        } else if (myColumn == elementCol) {

            if (myRow < elementRow) {
                direction = KEYPRESS.DOWN;
            } else if (myRow > elementRow) {
                direction = KEYPRESS.UP;
            } else {
                return true;
            }
        } else {

            if (myColumn < elementCol) {
                direction = KEYPRESS.RIGHT;
            } else {
                direction = KEYPRESS.LEFT;
            }
        }

        moveTowardsDirection(this.direction);

        return false;
    }

    protected void leaveCavern() {
        if (moveTo(level.getPortalPosition())) {
            inSpawn = false;
        }
    }

    protected boolean moveTowardsDirection(KEYPRESS direction) {

        Level.Position myPos = level.getPositionOf(this);
        Level.Position neighborPosition = level.getNeighborPosition(myPos, direction);
        Element neighbor = (Element) level.getElement(neighborPosition);

        if (neighbor != null) {

            if (neighbor.isTraversable(symbol) != null) {
                addMove(myPos.y(), myPos.x());
                level.setElementPosition(this, neighborPosition);
                oldElement = neighbor.isTraversable(this.getSymbol());
                return true;
            }
        }
        return false;
    }

    protected void run() {
        int lastPos = movements.size() - 1;

        if(this.oldElement.getSymbol() == 'y'){
            isVulnerable = false;
        }

        if (lastPos >= 0) {
            Element neighbor = (Element) level.getElement(movements.get(lastPos));
            if (neighbor == null) {
                return;
            }

            if (neighbor instanceof Pacman) {

                Element element = this.getOldElement();
                while (element instanceof Ghost innerGhost) {
                    if (innerGhost.isVulnerable()){
                        element = innerGhost.getOldElement();
                        this.setOldElement(element);
                        level.respawnGhost(innerGhost.symbol);
                    }else
                        level.killPacman();
                }

                level.respawnGhost(this.symbol);
                level.killElement(this);

                return;
            }

            Element element = neighbor.isTraversable(this.getSymbol());

            if (element != null) {
                level.setElementPosition(this, movements.get(lastPos));
                oldElement = element;
                movements.remove(lastPos);
            }
            return;
        }

        isVulnerable = false;
        inSpawn = true;
    }

    protected boolean checkForPacman() {
        Level.Position myPos = level.getPositionOf(this);
        Level.Position neighborPosition = level.getNeighborPosition(myPos, direction);
        Element neighbor = (Element) level.getElement(neighborPosition);

        if (neighbor instanceof Pacman) {
            level.killPacman();
            return true;
        }

        return false;
    }

    protected void follow() {

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

    protected KEYPRESS getSideDirection(KEYPRESS direction) {
        if (direction == KEYPRESS.UP) {
            return KEYPRESS.RIGHT;
        } else if (direction == KEYPRESS.DOWN) {
            return KEYPRESS.RIGHT;
        } else if (direction == KEYPRESS.LEFT) {
            return KEYPRESS.UP;
        } else {
            return KEYPRESS.UP;
        }
    }

    protected KEYPRESS getRandomSideDirection(KEYPRESS direction){
        Random random = new Random();
        int randomIndex = random.nextInt(2);

        KEYPRESS sideDir = getSideDirection(direction);

        if(randomIndex == 0){
            return getOppositeDirection(sideDir);
        }

        return sideDir;
    }

    @Override
    public char getSymbol() {
        if(isVulnerable){
            return 'v';
        }
        return symbol;
    }

    public void setVulnerability(boolean value) {
        isVulnerable = value;
    }
}
