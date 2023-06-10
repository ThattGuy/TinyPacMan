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

        KEYPRESS newDirection = null;
        if (!moveTowardsDirection(this.direction)) {
            newDirection = getRandomSideDirection(this.direction);

            if (!moveTowardsDirection(newDirection)) {
                this.direction = getOppositeDirection(newDirection);
                if (!moveTowardsDirection(newDirection)) {
                    return; // Unable to move in any direction, stop moving
                }
            }

        }
        if (newDirection != null) {
            this.direction = newDirection;
        }

    }

}
