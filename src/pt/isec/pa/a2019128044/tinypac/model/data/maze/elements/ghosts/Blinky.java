package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

import java.util.Random;

public class Blinky extends Ghost {
    public Blinky(Level level) {
        super('B', level);
    }

    @Override
    public void follow() {
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
    }


}
