package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

import java.util.Random;

public class Clyde extends Ghost {

    public Clyde(Level level) {
        super('C', level);
    }

    @Override
    void follow() {
        if (level.isPacmanVisible(this)) {
            chasePacman();
        } else {
            normalMovement();
        }
    }

    private void chasePacman() {
        Level.Position myPos = level.getPositionOf(this);
        Level.Position pacmanPos = level.getPacmanPos();

        KEYPRESS pacmanDir = getNextDirectionTowardsPacman(myPos, pacmanPos);

        if (pacmanDir != null) {
            this.direction = pacmanDir;
        }

        Level.Position neighborPosition = level.getNeighborPosition(myPos, this.direction);
        Element neighbor = (Element) level.getElement(neighborPosition);

        if(neighbor !=null){
            if (neighbor.isTraversable(this.getSymbol()) != null) {
                level.setElementPosition(this, neighborPosition);
                oldElement = neighbor.isTraversable(this.getSymbol());
                addMove(myPos.y(), myPos.x());
            }
        }
    }

    private void normalMovement() {
        super.follow();
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
