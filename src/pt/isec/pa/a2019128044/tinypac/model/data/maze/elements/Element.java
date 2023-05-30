package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;

public abstract class Element implements IMazeElement {

    protected final char symbol;
    protected Level level;

    public Element(char symbol, Level level) {
        this.symbol = symbol;
        this.level = level;
    }

    public boolean evolve(long currentTime){
        return true;
    };

    @Override
    public char getSymbol() {
        return symbol;
    }

}
