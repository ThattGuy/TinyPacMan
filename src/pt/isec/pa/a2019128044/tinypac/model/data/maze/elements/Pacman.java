package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts.Ghost;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

public class Pacman extends Element {

    public Pacman(Level level) {
        super('P', level);
        oldElement = new PacmanSpawn(level);
    }

    @Override
    public void evolve(long currentTime) {

        Level.Position myPos = level.getPositionOf(this);
        Level.Position neighborPosition = level.getNeighborPosition(myPos, level.getDirection());
        Element neighbor = (Element) level.getElement(neighborPosition);
        if(neighbor == null){
            return;
        }

        Element replacementElem = null;

        if(neighbor instanceof Ghost ghost) {
            if (ghost.isVulnerable()) {
                Element element = ghost.getOldElement();
                while (element instanceof Ghost innerGhost) {
                    if (innerGhost.isVulnerable()){
                        element = innerGhost.getOldElement();
                        ghost.setOldElement(element);
                        level.respawnGhost(innerGhost.getSymbol());
                    }else
                        level.killPacman();
                }

                replacementElem = ghost.getOldElement();
                level.respawnGhost(ghost.getSymbol());
            } else {
                level.killPacman();
                return;
            }
        }

        if(replacementElem == null) {
            replacementElem = neighbor.isTraversable(this.getSymbol());
        }

        if(replacementElem != null){
            level.setElementPosition(this,neighborPosition);
            oldElement = replacementElem;
        }
    }
}
