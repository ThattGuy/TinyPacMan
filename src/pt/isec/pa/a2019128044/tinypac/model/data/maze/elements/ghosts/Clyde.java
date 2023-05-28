package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Elements;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Empty;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Portal;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Warp;

import java.util.Random;

public class Clyde extends Ghost {
    public Clyde(Level level) {
        super('C', level);
    }


    @Override
    public void evolve(long currentTime) {

        if (currentTime - lastMovedTime >= 10) {

            Level.Position myPos = level.getPositionOf(this);
            if (myPos == null)
                return;

            if (inSpawn) {
                KEYPRESS = KEYPRESS.UP;
                Level.Position neighborPosition = level.getNeighboorPosition(myPos, this.KEYPRESS);

                if (level.getElement(neighborPosition) instanceof Ghost || level.getElement(neighborPosition).getSymbol() == 'x') {

                    KEYPRESS = KEYPRESS.LEFT;
                    neighborPosition = level.getNeighboorPosition(myPos, this.KEYPRESS);
                    if (level.getElement(neighborPosition) instanceof Ghost || level.getElement(neighborPosition).getSymbol() == 'x') {

                        KEYPRESS = KEYPRESS.RIGHT;
                        neighborPosition = level.getNeighboorPosition(myPos, this.KEYPRESS);
                        if (level.getElement(neighborPosition) instanceof Ghost || level.getElement(neighborPosition).getSymbol() == 'x') {

                        } else {
                            boolean moved = level.setElementPosition(this, neighborPosition);
                            if (moved) {
                                level.setElementPosition(new Empty(level), myPos);
                                if (level.getElement(myPos).getSymbol() == Elements.PORTAL.getValue())
                                    inSpawn = false;
                            }
                        }
                    } else {
                        boolean moved = level.setElementPosition(this, neighborPosition);
                        if (moved) {
                            level.setElementPosition(new Empty(level), myPos);
                        }
                    }
                } else {
                    boolean moved = level.setElementPosition(this, neighborPosition);
                    if (moved) {
                        level.setElementPosition(new Empty(level), myPos);
                    }
                }
            }
        } else {

            Level.Position myPos = level.getPositionOf(this);
            if (myPos == null)
                return;

            Level.Position neighboorPosition = level.getNeighboorPosition(myPos, level.getDirection());

            if (level.getElement(neighboorPosition) instanceof Portal || level.getElement(neighboorPosition) instanceof Warp) {

            } else {
                boolean moved = level.setElementPosition(this, neighboorPosition);
                if (moved) {
                    level.setElementPosition(new Empty(level), myPos);
                } else {
                    KEYPRESS[] values = KEYPRESS.values();
                    Random random = new Random();
                    KEYPRESS = values[random.nextInt(values.length)];
                }
            }
        }
        lastMovedTime = currentTime;
    }
}







