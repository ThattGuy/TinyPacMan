package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class SmallBall extends Element {

    public SmallBall(Level level) {
        super('o',level);
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public boolean isTransversable(char type) {
        if(type == 'P'){
            //todo indicar à fsm ou flag?

            level.addPoints(1);
        }
        return true;
    }
}
