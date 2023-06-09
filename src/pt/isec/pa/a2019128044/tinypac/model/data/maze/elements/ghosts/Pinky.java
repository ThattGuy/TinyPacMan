package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pinky extends Ghost {

    private List<Level.Position> corners;
    private int currentCornerIndex;
    private int targetDistance;

    public Pinky(Level level) {
        super('R', level);
        corners = new ArrayList<>();

        corners.add(level.getCorner("TopLeft"));
        corners.add(level.getCorner("TopRight"));
        corners.add(level.getCorner("BottonLeft"));
        corners.add(level.getCorner("BottomRight"));
        currentCornerIndex = 0;
        targetDistance = (int) (level.getWidth() * 0.1); // 10% of the maze width as target distance
    }

    @Override
    protected void follow() {
        if(inCrossRoads()){
            switch (currentCornerIndex){
                case 0:
                    if(direction != KEYPRESS.UP){
                        direction = KEYPRESS.UP;
                    }
                    if(!moveTowardsDirection(this.direction)){
                        direction = KEYPRESS.LEFT;
                        moveTowardsDirection(this.direction);
                    }

            }
        }else{
            moveTowardsDirection(direction);
        }
    }

    private boolean inCrossRoads() {
        Level.Position myPos = level.getPositionOf(this);
        Level.Position neigborSideOnePos = level.getNeighborPosition(myPos,getSideDirection(this.direction));
        Element neigborSideOne = (Element) level.getElement(neigborSideOnePos);
        Level.Position neigborSideTwoPos = level.getNeighborPosition(myPos,getOppositeDirection(getSideDirection(this.direction)));
        Element neigborSideTwo = (Element) level.getElement(neigborSideTwoPos);

        if (neigborSideOne.isTraversable(this.getSymbol()) != null || neigborSideTwo.isTraversable(this.getSymbol()) != null){

            return true;
        }
        return false;
    }

    private KEYPRESS getSideDirection(KEYPRESS direction){
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