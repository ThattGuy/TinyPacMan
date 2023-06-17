package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

import java.io.Serializable;

public class Warmup  extends GameStateAdapter implements Serializable {

    public Warmup(GameContext context, GameData data) {
        super(context, data);
        System.out.println("Warmup State");
    }

    @Override
    public boolean evolve(long currentTime) {

        if(stateTimer == 0){
            stateTimer = currentTime;
        }

        if(data.getLevelNumber() == data.MAXLEVEL){
            changeState(GameState.GAMEOVER,this.getState());
            return true;
        }
        
        long warmUpTime = 5000000000L; // 5 segundos

        data.movePacman(currentTime);

        if (currentTime - stateTimer >= warmUpTime) {
                changeState(GameState.PACMAN_VULNERABLE,null);
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
        return GameState.WARMUP;
    }
}