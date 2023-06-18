package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;

import java.io.Serializable;


public abstract class Element implements IMazeElement, Serializable {

    protected final char symbol;
    protected Level level;
    protected boolean evolved;
    protected Element oldElement;

    public Element(char symbol, Level level) {
        this.symbol = symbol;
        this.level = level;
        evolved = false;
        oldElement = null;
    }

    public void evolve(){

    };

    public void setEvolved(boolean hasEvolved) {
        this.evolved = hasEvolved;
    }

    public boolean hasEvolved() {
        return evolved;
    }

    /**
     * este metodo permite com que outro elemento verifique se se pode mover para cima deste
     * @param type elemento que chama o metodo
     * @return
     */
    public Element isTraversable(char type){
        return null;
    }

    public Element getOldElement() {
        return oldElement;
    }

    public void setOldElement(Element oldElement) {
        this.oldElement = oldElement;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

}
