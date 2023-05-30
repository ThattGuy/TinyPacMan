package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.Elements;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Empty;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Portal;
import pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.inanimateelements.Warp;

import java.util.Random;

public class Pinky extends Ghost {
    public Pinky(Level level) {
        super('R', level);
    }

    @Override
    public boolean evolve(long currentTime) {

        if(isVulnerable){
            return run();
        }
        follow();
        return true;
    }

    private boolean run(){
        if(isAlive)
            return true;
        else
            return false;
    }

    private void follow(){

    }

}
