package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Empty;

public class Pinky extends Ghost {
    public Pinky(Level level) {
        super('R', level);
    }

    @Override
    protected void leaveCavern() {


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
