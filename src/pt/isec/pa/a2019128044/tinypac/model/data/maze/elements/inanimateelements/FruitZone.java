package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class FruitZone extends Element {
    boolean hasFruit;

    int numberOfFruits;

    public FruitZone(Level level) {
        super('F',level);
        hasFruit = false;
        numberOfFruits = 1;
    }

    /**
     * a cada 20 pontos criar uma fruta
     */
    @Override
    public void evolve() {
        if(level.getPoints()%20 == 0 && !hasFruit){
            hasFruit = true;
            numberOfFruits ++;
        }
    }

    /**
     *
     * @param type elemento que chama o metodo
     * @return caso seja umg ghost pode ser atravessado no entando não desaparece
     */
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
