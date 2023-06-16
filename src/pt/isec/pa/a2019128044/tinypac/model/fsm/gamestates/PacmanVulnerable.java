package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

public class PacmanVulnerable  extends GameStateAdapter {
    public PacmanVulnerable(GameContext context, GameData data) {
        super(context, data);
        System.out.println("Pacman vulnerable");
    }

    @Override
    public boolean evolve(long currentTime) {

        if(!data.isPacmanAlive()){
            if(data.getLives() <= 0){
                changeState(GameState.GAMEOVER,this.getState());
                return true;
            }
            data.restartLevel();
            changeState(GameState.WARMUP,this.getState());
            return true;
        }

        data.evolveAll(currentTime);
        if (data.atePowerUp()){
            changeState(GameState.GHOSTS_VULNERABLE,this.getState());
            return true;
        }

        /*if(data.getBalls() <= 280 && data.getBalls() != -1){
            //todo perguntar ao professor
            data.changeLevel();
            changeState(GameState.INITIAL,this.getState());
            return true;
        }*/

        return true;
    }

    @Override
    public boolean pressKey(KEYPRESS keypress) {

        if(keypress == KEYPRESS.ESC){
            changeState(GameState.PAUSE,this.getState());
        }else{
            return data.setDirection(keypress);
        }
        return false;
    }
    @Override
    public GameState getState() {
        return GameState.PACMAN_VULNERABLE;
    }
}
