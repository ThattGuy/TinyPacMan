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
    }

    /**
     * evolve warmup
     * caso o nivel seja o ultima transita para o estado gameover
     * passado 5 segundos transita para o estado pacmanVulnerable
     * @param currentTime hora atual
     */
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

        data.movePacman();

        if (currentTime - stateTimer >= warmUpTime) {
                changeState(GameState.PACMAN_VULNERABLE,null);
        }

        return true;
    }

    /**
     * indica aos dados que se deve mudar de direção
     * caso keypress seja ESC transita para o estado PAUSE passando lhe o nome do estado atual
     * @param keypress recla que representa a direção
     */
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