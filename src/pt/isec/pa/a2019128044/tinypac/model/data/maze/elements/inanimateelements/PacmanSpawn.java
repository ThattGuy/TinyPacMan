package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class PacmanSpawn extends Element {

    public PacmanSpawn(Level level) {
        super('M',level);
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Element isTraversable(char type) {
        return this;
    }
}
