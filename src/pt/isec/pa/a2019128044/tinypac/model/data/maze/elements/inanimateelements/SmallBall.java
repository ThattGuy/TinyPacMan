package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Result;

public class SmallBall extends Element {

    public SmallBall(Level level) {
        super('o', level);
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Element isTraversable(char type) {
        if (type == 'P') {

            level.addPoints(1);
            return new Empty(level);
        }
        return this;
    }
}
