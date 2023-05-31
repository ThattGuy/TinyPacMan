package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class FruitZone extends Element {

    //TODO GARANTIR QUE APENAS EXISTE UMA ZONA DETAS; Garantir que apenas é criada uma fruta caso n exista
    //todo buscar os pontos para criar fruta
    boolean fruit;

    public FruitZone(Level level) {
        super('F',level);
        fruit = false;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    public boolean hasFruit(){
        return fruit;
    }

}
