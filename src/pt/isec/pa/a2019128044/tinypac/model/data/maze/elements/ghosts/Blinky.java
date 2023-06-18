package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;

public class Blinky extends Ghost {
    public Blinky(Level level) {
        super('B', level);
    }

    /**
     * cada vez que encontra uma parede muda para uma direção aleatória
     * caso nao consgiga volta para tras
     */
    @Override
    public void follow() {

        if(checkForPacman()){
            return;
        }

        KEYPRESS newDirection = null;
        if (!moveTowardsDirection(this.direction)) {
            newDirection = getRandomSideDirection(this.direction);

            if (!moveTowardsDirection(newDirection)) {
                this.direction = getOppositeDirection(newDirection);
                if (!moveTowardsDirection(newDirection)) {
                    return;
                }
            }

        }
        if (newDirection != null) {
            this.direction = newDirection;
        }

    }

}
