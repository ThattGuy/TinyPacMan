package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class Fruit extends Element {

    public Fruit(Level level) {
        super('f', level);
        if(level == null){
            return;
        }

        if(level.getElement(level.getFruitZonePosition()) instanceof Element element)
        oldElement = element;
    }



}
