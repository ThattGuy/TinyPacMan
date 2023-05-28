package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class FruitZone extends Element {
    public FruitZone(Level level) {
        super('F',level);
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

}
