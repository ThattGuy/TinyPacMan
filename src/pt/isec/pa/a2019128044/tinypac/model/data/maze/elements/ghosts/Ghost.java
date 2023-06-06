package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Portal;

import java.util.ArrayList;
import java.util.List;

public abstract class Ghost extends Element {

    protected boolean inSpawn;
    protected long lastMovedTime = 0;
    protected boolean isAlive;
    protected boolean isVulnerable;

    protected KEYPRESS direction;
    protected List<Integer[][]> movements;

    public Ghost(char symbol, Level level) {
        super(symbol, level);
        this.isAlive = true;
        this.movements = new ArrayList<>();
        inSpawn = true;
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }

    public void addMove(int x, int y) {
        movements.add(new Integer[x][y]);
    }

    @Override
    public void evolve(long currentTime) {

        if (inSpawn) {
            leaveCavern();
        } else if (isVulnerable()) {
            run();
        } else {
            follow();
        }
    }

    protected void leaveCavern() {

        Level.Position myPos = level.getPositionOf(this);
        Level.Position portalPos = level.getPortalPosition();

        int nextY = myPos.y();
        int nextX = myPos.x();

        if (myPos.x() < portalPos.x()) {
            nextX++;
        } else if (myPos.x() > portalPos.x()) {
            nextX--;
        }

        if (myPos.y() < portalPos.y()) {
            nextY++;
        } else if (myPos.y() > portalPos.y()) {
            nextY--;
        }

        Level.Position neighboorPosition = new Level.Position(nextY, nextX);
        Element neighboor = (Element) level.getElement(neighboorPosition);

        if(neighboor.isTransversable(this.getSymbol()) != null){
            level.setElementPosition(this,neighboorPosition);
            oldElement = neighboor.isTransversable(this.getSymbol());
        }

        if (level.getElement(neighboorPosition).getSymbol() == 'Y'){
            inSpawn = false;
        }

    }

    protected void run() {
        //TODO REVERTER MOVIMENTOS
        //todo voltar a seguir assim que voltar ao spawn
    }

    abstract void follow();

}
