package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class PowerUp extends Element {
    public PowerUp(Level level) {
        super('O',level);
    }

    @Override
    public Element isTraversable(char type) {
        if(type == 'P'){
            level.setPowerUp(true);
            level.addPoints(10);
            return new Empty(level);
        }
        return this;
    }
}