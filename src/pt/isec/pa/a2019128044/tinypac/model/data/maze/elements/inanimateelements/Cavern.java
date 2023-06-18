package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class Cavern extends Element {
    public Cavern(Level level) {
        super('y',level);
    }

    /**
     *
     * @param type elemento que chama o metodo
     * @return caso seja o pacman não pode ser atravessado
     */
    @Override
    public Element isTraversable(char type) {

        if(type == 'P'){
            return null;
        }

        return this;

    }
}
