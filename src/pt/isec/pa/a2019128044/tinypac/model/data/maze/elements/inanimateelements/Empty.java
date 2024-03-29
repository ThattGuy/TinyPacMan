package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class Empty extends Element {

    public Empty(Level level) {
        super(' ', level);
    }

    @Override
    public Element isTraversable(char type) {
        return this;
    }
}
