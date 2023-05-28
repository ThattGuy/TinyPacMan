package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Empty;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Portal;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.SmallBall;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Warp;

public class Pacman extends Element {
    private long lastMovedTime = 0;
    int lives;

    public Pacman(Level level) {
        super('P', level);
        lives = 3;
    }

    @Override
    public void evolve(long currentTime) {

        if (currentTime - lastMovedTime >= 10) {

            Level.Position myPos = level.getPositionOf(this);
            if (myPos == null)
                return;

            Level.Position neighboorPosition = level.getNeighboorPosition(myPos, level.getDirection());

            if (level.getElement(neighboorPosition) instanceof Portal || level.getElement(neighboorPosition)
                    instanceof Warp) {

            } else {
                boolean moved = level.setElementPosition(this, neighboorPosition);
                if (moved) {
                    level.setElementPosition(new Empty(level), myPos);
                }
            }
            lastMovedTime = currentTime;
        }

    }
}