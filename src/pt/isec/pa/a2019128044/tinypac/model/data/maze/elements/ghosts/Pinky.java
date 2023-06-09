package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

import java.util.ArrayList;
import java.util.List;

public class Pinky extends Ghost {

    private List<Level.Position> corners;
    private int currentCornerIndex;
    private int targetDistance;

    public Pinky(Level level) {
        super('R', level);
        corners = new ArrayList<>();

        corners.add(level.getCorner("TopRight"));
        corners.add(level.getCorner("BottomRight"));
        corners.add(level.getCorner("TopLeft"));
        corners.add(level.getCorner("BottonLeft"));
        currentCornerIndex = 0;
        targetDistance = (int) (level.getWidth() * 0.2); // 10% of the maze width as target distance
    }

    @Override
    protected void follow() {
        if(checkForPacman()){
            return;
        }
        if (inCrossRoads()) {
            switch (currentCornerIndex) {
                case 0: // TopRight corner
                    if (direction != KEYPRESS.UP) {
                        direction = KEYPRESS.UP;
                    }
                    if (!moveTowardsDirection(direction)) {
                        direction = KEYPRESS.RIGHT;
                        if (!moveTowardsDirection(direction)) {
                            direction = KEYPRESS.LEFT;
                            moveTowardsDirection(direction);
                        }
                    }
                    break;
                case 1: // BottomRight corner
                    if (direction != KEYPRESS.DOWN) {
                        direction = KEYPRESS.DOWN;
                    }
                    if (!moveTowardsDirection(direction)) {
                        direction = KEYPRESS.RIGHT;
                        if (!moveTowardsDirection(direction)) {
                            direction = KEYPRESS.LEFT;
                            moveTowardsDirection(direction);
                        }
                    }
                    break;
                case 2: // TopLeft corner
                    if (!moveTowardsDirection(direction)) {
                        direction = KEYPRESS.LEFT;
                        if (!moveTowardsDirection(direction)) {
                            direction = KEYPRESS.RIGHT;
                            moveTowardsDirection(direction);
                        }
                        if (direction != KEYPRESS.UP) {
                            direction = KEYPRESS.UP;
                        }
                    }
                    break;
                case 3: // BottomLeft corner
                    if (direction != KEYPRESS.DOWN) {
                        direction = KEYPRESS.DOWN;
                    }
                    if (!moveTowardsDirection(direction)) {
                        direction = KEYPRESS.RIGHT;
                        if (!moveTowardsDirection(direction)) {
                            direction = KEYPRESS.RIGHT;
                            moveTowardsDirection(direction);
                        }
                    }
                    break;
            }

            // Check if Pinky is close enough to the target corner
            Level.Position myPos = level.getPositionOf(this);
            Level.Position targetCornerPos = corners.get(currentCornerIndex);
            int distanceToTarget = level.getDistanceBetweenPositions(myPos, targetCornerPos);
            if (distanceToTarget <= targetDistance) {
                // Pinky is close enough to the target corner, update the current corner index
                currentCornerIndex = (currentCornerIndex + 1) % corners.size();
            }
        } else {
            moveTowardsDirection(direction);
        }
    }

    private boolean inCrossRoads() {
        Level.Position myPos = level.getPositionOf(this);
        Level.Position neighborSideOnePos = level.getNeighborPosition(myPos, getSideDirection(this.direction));
        Element neighborSideOne = (Element) level.getElement(neighborSideOnePos);
        Level.Position neighborSideTwoPos = level.getNeighborPosition(myPos, getOppositeDirection(getSideDirection(this.direction)));
        Element neighborSideTwo = (Element) level.getElement(neighborSideTwoPos);

        if (neighborSideOne.isTraversable(this.getSymbol()) != null || neighborSideTwo.isTraversable(this.getSymbol()) != null) {
            return true;
        }
        return false;
    }

    private KEYPRESS getSideDirection(KEYPRESS direction) {
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
}
