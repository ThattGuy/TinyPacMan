package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;

public class Clyde extends Ghost {

    public Clyde(Level level) {
        super('C', level);
    }

    /**
     * caso o pacman seja visivel chama o metodo chasepacman
     * caso nao seja chama o metodo normalMovement
     */
    @Override
    protected void follow() {
        if (level.isPacmanVisible(this)) {
            chasePacman();
        } else {
            normalMovement();
        }
    }

    /**
     * caso veja o pacman move-se em diração a ele
     */
    private void chasePacman() {
        Level.Position myPos = level.getPositionOf(this);
        Level.Position pacmanPos = level.getPacmanPos();

        if(checkForPacman()){
            return;
        }

        KEYPRESS pacmanDir = getNextDirectionTowardsPacman(myPos, pacmanPos);
        if (pacmanDir != null) {
            this.direction = pacmanDir;
        }


        moveTowardsDirection(this.direction);
    }

    /**
     * cada vez que encontra uma parede muda para uma direção aleatória
     * caso nao consgiga volta para tras
     */
    private void normalMovement() {
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

    private KEYPRESS getNextDirectionTowardsPacman(Level.Position ghostPos, Level.Position pacmanPos) {
        if (ghostPos.x() == pacmanPos.x()) {
            return (ghostPos.y() < pacmanPos.y()) ? KEYPRESS.DOWN : KEYPRESS.UP;
        } else if (ghostPos.y() == pacmanPos.y()) {
            return (ghostPos.x() < pacmanPos.x()) ? KEYPRESS.RIGHT : KEYPRESS.LEFT;
        }
        return null;
    }


    @Override
    protected void run() {
        super.run();
    }



}
