package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class Warp extends Element {

    public Warp(Level level) {
        super('W',level);
    }

    /**
     *
     * @param type elemento que chama o metodo
     * @return caso seja o pacman pode ser atravessado e  desaparece
     */
    @Override
    public Element isTraversable(char type) {

        if(type == 'P'){
            return this;
        }
        return null;
    }
}