package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

public class SmallBall extends Element {

    public SmallBall(Level level) {
        super('o', level);
    }

    /**
     *
     * @param type elemento que chama o metodo
     * @return caso seja o pacman pode ser atravessado e  desaparece
     * caso seja fantasma não desaparece
     */
    @Override
    public Element isTraversable(char type) {
        if (type == 'P') {
            level.addPoints(1);
            return new Empty(level);
        }
        return this;
    }
}
