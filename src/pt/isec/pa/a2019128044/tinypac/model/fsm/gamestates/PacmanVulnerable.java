package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

public class PacmanVulnerable  extends GameStateAdapter {
    int simultaneio = 0;
    public PacmanVulnerable(GameContext context, GameData data) {
        super(context, data);
        System.out.println("Pacman vulnerable");
    }

    @Override
    public  boolean evolve(long currentTime) {
        System.out.println("entrei");
        ++simultaneio;
        if (simultaneio>1)
            System.out.printf("SIMULTANEIO %d\n", simultaneio);

        if(!data.isPacmanAlive()){
            if(data.getLives() <= 0){
                changeState(GameState.GAMEOVER,this.getState());
                System.out.println("saiu is dead");
                --simultaneio;
                return true;
            }
            data.restartLevel();
            changeState(GameState.WARMUP,this.getState());
            System.out.println("saiu restart level");
            --simultaneio;
            return true;
        }

        data.evolveAll(currentTime);
        if (data.atePowerUp()){
            changeState(GameState.GHOSTS_VULNERABLE,this.getState());
            System.out.println("saiu para power up");
            --simultaneio;
            return true;
        }

        if(data.getBalls() <= 0 && data.getBalls() != -1){
            //todo perguntar ao professor
            data.changeLevel();
            changeState(GameState.INITIAL,this.getState());
            System.out.println("saiu mudou nivel");
            --simultaneio;
            return true;
        }

        System.out.println(".");
        --simultaneio;
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
