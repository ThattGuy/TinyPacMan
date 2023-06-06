package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Result;

public class Cavern extends Element {
    public Cavern(Level level) {
        super('y',level);
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Element isTransversable(char type) {

        if(type == 'P'){
            return null;
        }

        return this;

    }
}
