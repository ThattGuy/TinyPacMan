package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class FruitZone extends Element {

    //TODO GARANTIR QUE APENAS EXISTE UMA ZONA DESTAS;
    boolean hasFruit;

    int numberOfFruits;

    public FruitZone(Level level) {
        super('F',level);
        hasFruit = false;
        numberOfFruits = 1;
    }

    @Override
    public void evolve(long currentTime) {
        if(level.getPoints()%20 == 0 && !hasFruit){
            hasFruit = true;
            numberOfFruits ++;
        }
    }

    @Override
    public Element isTraversable(char type) {
        if(type == 'P' && hasFruit){
            level.addPoints(numberOfFruits * 25);
            hasFruit = false;
        }
        return this;
    }

    @Override
    public char getSymbol() {
        if(hasFruit){
            return 'f';
        }
        return symbol;
    }

}
