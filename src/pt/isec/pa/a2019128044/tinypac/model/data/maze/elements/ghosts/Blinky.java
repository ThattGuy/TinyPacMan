package pt.isec.pa.a2019128044.tinypac.model.data.maze.elements.ghosts;

import pt.isec.pa.a2019128044.tinypac.model.data.maze.Level;


public class Blinky extends Ghost {
    public Blinky(Level level) {
        super('B', level);
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

