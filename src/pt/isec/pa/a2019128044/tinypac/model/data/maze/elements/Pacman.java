package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Pacman extends Element {
    public Pacman(Level level) {
        super('P', level);
        oldElement = new PacmanSpawn(level);
    }

    @Override
    public void evolve(long currentTime) {

        Level.Position myPos = level.getPositionOf(this);
        Level.Position neighboorPosition = level.getNeighboorPosition(myPos, level.getDirection());
        Element neighboor = (Element) level.getElement(neighboorPosition);
        if(neighboor == null){
            return;
        }

        if(neighboor.isTransversable(this.getSymbol()) != null){
            level.setElementPosition(this,neighboorPosition);
            oldElement = neighboor.isTransversable(this.getSymbol());
        }
    }
}
