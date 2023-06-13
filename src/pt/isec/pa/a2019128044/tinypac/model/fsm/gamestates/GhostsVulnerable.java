package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

public class GhostsVulnerable extends GameStateAdapter {
    public GhostsVulnerable(GameContext context, GameData data) {
        super(context, data);
        data.setGhostsVulnerability(true);
    }

    @Override
    public boolean evolve(long currentTime) {
        if(stateTimer == 0){
            stateTimer = currentTime;
        }

        if(!data.isPacmanAlive()){
            if(data.getLives() == 0){
                changeState(GameState.GAMEOVER,this.getState());
            }
            data.restartLevel();
            changeState(GameState.WARMUP,this.getState());
        }

        data.evolveAll(currentTime);

        if (currentTime - stateTimer >= 10000000000L) {
                data.setPowerUp(false);
                data.setGhostsVulnerability(false);
                changeState(GameState.PACMAN_VULNERABLE,this.getState());
        }
        return true;
    }

    @Override
    public boolean pressKey(KEYPRESS keypress) {

        if(keypress == KEYPRESS.ESC){
            changeState(GameState.PAUSE,this.getState());
        }else{
            data.setDirection(keypress);
        }
        return true;
    }
    @Override
    public GameState getState() {
        return GameState.GHOSTS_VULNERABLE;
    }
}
