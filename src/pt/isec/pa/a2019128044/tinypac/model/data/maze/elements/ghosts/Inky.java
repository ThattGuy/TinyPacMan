package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class Inky extends Ghost {
    public Inky(Level level) {
        super('I', level);
    }

    public Inky(char symbol, Level level) {
        super(symbol, level);
    }

}

