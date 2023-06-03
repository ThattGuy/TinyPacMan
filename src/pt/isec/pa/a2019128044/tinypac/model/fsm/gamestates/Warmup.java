package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

public class Warmup  extends GameStateAdapter {

    public Warmup(GameContext context, GameData data) {
        super(context, data);
        System.out.println("Warmup State");
    }

    @Override
    public boolean evolve(long currentTime) {

        if(stateTimer == 0){
            stateTimer = currentTime;
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