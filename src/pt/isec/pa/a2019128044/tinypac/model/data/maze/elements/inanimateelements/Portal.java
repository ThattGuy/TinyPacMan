package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class Portal extends Element {

    public Portal(Level level) {
        super('Y',level);
    }


    @Override
    public char getSymbol() {
        return symbol;
    }
}
