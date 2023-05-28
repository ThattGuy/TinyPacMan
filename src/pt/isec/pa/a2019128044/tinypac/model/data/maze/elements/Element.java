package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

import java.util.HashMap;
import java.util.Map;

public abstract class Element implements IMazeElement {

    protected final char symbol;
    protected Level level;

    public Element(char symbol, Level level) {
        this.symbol = symbol;
        this.level = level;
    }

    public void evolve(long currentTime){};

    @Override
    public char getSymbol() {
        return symbol;
    }

}
