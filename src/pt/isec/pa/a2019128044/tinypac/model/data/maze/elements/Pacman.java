package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

public class Pacman extends Element {
    public Pacman(Level level) {
        super('P', level);
    }

    @Override
    public void evolve(long currentTime) {

        Level.Position myPos = level.getPositionOf(this);

        Level.Position neighboorPosition = level.getNeighboorPosition(myPos, level.getDirection());

        Element neighboor = (Element)level.getElement(neighboorPosition);

        System.out.println("myPos: " + myPos);
        System.out.println("neighboor position:" + neighboorPosition + "neighboor element: " + neighboor.getSymbol());

        System.out.println("direction:" + level.getDirection());

        if (neighboor.isTransversable(this.getSymbol())) {
            Element oldPositionElement = new Empty(level);
            boolean moved = level.setElementPosition(this, neighboorPosition);
            if (moved) {
                level.setElementPosition(oldPositionElement, myPos);
            }
        }
    }

}