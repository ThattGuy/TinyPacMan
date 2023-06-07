package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

public class Pacman extends Element {
    public Pacman(Level level) {
        super('P', level);
        oldElement = new PacmanSpawn(level);
    }

    @Override
    public void evolve(long currentTime) {

        Level.Position myPos = level.getPositionOf(this);
        Level.Position neighborPosition = level.getNeighborPosition(myPos, level.getDirection());
        Element neighbor = (Element) level.getElement(neighborPosition);
        if(neighbor == null){
            return;
        }

        if(neighbor.isTraversable(this.getSymbol()) != null){
            level.setElementPosition(this,neighborPosition);
            oldElement = neighbor.isTraversable(this.getSymbol());
        }
    }
}
