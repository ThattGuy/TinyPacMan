package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts.Ghost;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.*;

public class Pacman extends Element {

    public Pacman(Level level) {
        super('P', level);
        oldElement = new PacmanSpawn(level);
    }

    /***
     * move o pacman
     * verifica se pode-se mover através do metodos tranversable da casa para que se quer mover
     * caso encontre um fantasma e o mesmo não esteja vulnerável morre,
     * caso esteja vulnerável o fantasma morre
     *
     */
    @Override
    public void evolve() {

        Level.Position myPos = level.getPositionOf(this);
        Level.Position neighborPosition = level.getNeighborPosition(myPos, level.getDirection());
        Element neighbor = (Element) level.getElement(neighborPosition);
        if(neighbor == null){
            return;
        }

        if(neighbor.getSymbol() == 'o' || neighbor.getSymbol() == 'O'){
            level.removeBall();
        }

        Element replacementElem = null;

        if(neighbor instanceof Ghost ghost) {
            if (ghost.isVulnerable()) {
                Element element = ghost.getOldElement();
                while (element instanceof Ghost innerGhost) {
                    if (innerGhost.isVulnerable()){
                        element = innerGhost.getOldElement();
                        ghost.setOldElement(element);
                        level.respawnGhost(innerGhost.symbol);
                    } else
                        level.killPacman();
                }

                replacementElem = ghost.getOldElement();
                level.respawnGhost(ghost.symbol);
            } else {
                level.killPacman();
                return;
            }
        }

        if(replacementElem == null) {
            replacementElem = neighbor.isTraversable(this.symbol);
        }

        if(replacementElem != null){
            level.setElementPosition(this,neighborPosition);
            oldElement = replacementElem;
        }
    }
}
