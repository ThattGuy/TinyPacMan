package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class FruitZone extends Element {

    //TODO GARANTIR QUE APENAS EXISTE UMA ZONA DETAS; Garantir que apenas é criada uma fruta caso n exista
    //todo buscar os pontos para criar fruta
    boolean hasFruit;

    int numberOfFruits;

    public FruitZone(Level level) {
        super('F',level);
        hasFruit = false;
        numberOfFruits = 1;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public void evolve(long currentTime) {
        if(level.getPoints()%20 == 0){
            hasFruit = true;
        }
    }

    @Override
    public boolean isTransversable(char type) {
        if(type == 'P' && hasFruit){
            level.addPoints(numberOfFruits * 25);
        }
        return true;
    }

}
