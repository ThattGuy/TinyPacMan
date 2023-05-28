package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Element;

import java.util.ArrayList;
import java.util.List;

public abstract class Ghost extends Element {

    protected boolean inSpawn;
    protected long lastMovedTime = 0;
    protected boolean isAlive;
    protected boolean isVulnerable;

    protected KEYPRESS KEYPRESS;
    protected List<Integer[][]> movements;

    public Ghost(char symbol, Level level) {
        super(symbol,level);
        this.isAlive = true;
        this.movements = new ArrayList<>();
        inSpawn = true;
    }
}
