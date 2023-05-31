package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Empty;

public class Pinky extends Ghost {
    public Pinky(Level level) {
        super('R', level);
    }

    @Override
    protected void leaveCavern() {
        Level.Position myPos = level.getPositionOf(this);
        Level.Position portal = level.getPortalPosition();

        System.out.println("tou auqi");

        if (isAdjacentToExit(myPos, portal)) {
            // Move the element inside the exit
            if (level.setElementPosition(this, portal)) {
                level.setElementPosition(new Empty(level), myPos);
            }
        } else {
            // Move towards the portal
            int nextY = myPos.y();
            int nextX = myPos.x();

            if (myPos.x() < portal.x()) {
                nextX++;
            } else if (myPos.x() > portal.x()) {
                nextX--;
            }

            if (myPos.y() < portal.y()) {
                nextY++;
            } else if (myPos.y() > portal.y()) {
                nextY--;
            }

            Level.Position nextPos = new Level.Position(nextY, nextX);

            if (level.getElement(nextPos) instanceof Ghost) {
                if (level.setElementPosition(this, nextPos)) {
                    level.setElementPosition(new Empty(level), myPos);
                }
            }
        }
    }

    private boolean isAdjacentToExit(Level.Position position, Level.Position exitPosition) {
        int diffX = Math.abs(position.x() - exitPosition.x());
        int diffY = Math.abs(position.y() - exitPosition.y());
        return (diffX == 1 && diffY == 0) || (diffX == 0 && diffY == 1);
    }

    @Override
    void follow() {

    }

}
