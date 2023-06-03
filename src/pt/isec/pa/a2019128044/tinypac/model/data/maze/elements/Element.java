package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;

public abstract class Element implements IMazeElement {

    protected final char symbol;
    protected Level level;
    protected boolean evolved;

    public Element(char symbol, Level level) {
        this.symbol = symbol;
        this.level = level;
        evolved = false;
    }

    public void evolve(long currentTime){

    };

    public void setEvolved(boolean hasEvolved) {
        this.evolved = hasEvolved;
    }

    public boolean hasEvolved() {
        return evolved;
    }

    public boolean isTransversable(char type){
        return false;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

}
