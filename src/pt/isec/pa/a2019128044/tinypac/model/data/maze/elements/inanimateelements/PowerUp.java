package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Result;

public class PowerUp extends Element {
    public PowerUp(Level level) {
        super('O',level);
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Element isTransversable(char type) {
        if(type == 'P'){
            //todo indicar à fsm ou flag?
            level.addPoints(10);
            return new Empty(level);
        }
        return this;
    }
}