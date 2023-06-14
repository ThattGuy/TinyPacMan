package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Inky extends Ghost {

    private final List<Level.Position> corners;
    private int currentCornerIndex;
    private final int targetDistance;

    public Inky(Level level) {
        super('I', level);
        corners = new ArrayList<>();

        corners.add(level.getCorner("BottomRight"));
        corners.add(level.getCorner("BottonLeft"));
        corners.add(level.getCorner("TopRight"));
        corners.add(level.getCorner("TopLeft"));

        currentCornerIndex = 0;
        targetDistance = (int) (level.getWidth() * 0.2); // 10% of the maze width as target distance
    }

    @Override
    protected void follow() {
        if (checkForPacman()) {
            return;
        }

        if (inIntersection()) {
            direction = getDirectionTowardsCorner();
            if(!moveTowardsDirection(direction))
                move();

            Level.Position myPos = level.getPositionOf(this);
            Level.Position targetCornerPos = corners.get(currentCornerIndex);
            int distanceToTarget = level.getDistanceBetweenPositions(myPos, targetCornerPos);
            if (distanceToTarget <= targetDistance) {
                // Pinky is close enough to the target corner, update the current corner index
                currentCornerIndex = (currentCornerIndex + 1) % corners.size();
            }
        } else {
            move();
        }
    }



    private void move(){
        KEYPRESS nextDir = direction;
        if (!moveTowardsDirection(nextDir)) {
            nextDir = getRandomSideDirection(direction);

            if (!moveTowardsDirection(nextDir)) {
                nextDir = getRandomSideDirection(direction);

                if (!moveTowardsDirection(nextDir)) {
                    nextDir = getOppositeDirection(nextDir);

                    if (!moveTowardsDirection(nextDir)) {
                        nextDir = getOppositeDirection(direction);
                        moveTowardsDirection(nextDir);
                    }
                }
            }
        }
        direction = nextDir;
    }

    private KEYPRESS getDirectionTowardsCorner() {

        Random random = new Random();
        int randomIndex = random.nextInt(2);

        switch (currentCornerIndex) {
            case 0 -> {
                if (randomIndex == 0) {
                    return KEYPRESS.DOWN;
                }
                return KEYPRESS.RIGHT;
            }
            case 1 -> {
                if (randomIndex == 0) {
                    return KEYPRESS.DOWN;
                }
                return KEYPRESS.LEFT;
            }
            case 2 -> {
                if (randomIndex == 0) {
                    return KEYPRESS.UP;
                }
                return KEYPRESS.RIGHT;
            }
            case 3 -> {
                if (randomIndex == 0) {
                    return KEYPRESS.UP;
                }
                return KEYPRESS.LEFT;
            }
        }

        return null;
    }

    private boolean inIntersection() {
        Level.Position myPos = level.getPositionOf(this);
        Level.Position neighborSideOnePos = level.getNeighborPosition(myPos, getSideDirection(this.direction));
        Element neighborSideOne = (Element) level.getElement(neighborSideOnePos);
        Level.Position neighborSideTwoPos = level.getNeighborPosition(myPos, getOppositeDirection(getSideDirection(this.direction)));
        Element neighborSideTwo = (Element) level.getElement(neighborSideTwoPos);

        return neighborSideOne.isTraversable(this.getSymbol()) != null || neighborSideTwo.isTraversable(this.getSymbol()) != null;
    }


}
