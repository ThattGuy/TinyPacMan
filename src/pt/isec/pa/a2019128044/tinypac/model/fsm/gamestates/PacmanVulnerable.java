package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

import java.io.Serializable;

public class PacmanVulnerable  extends GameStateAdapter implements Serializable {
    public PacmanVulnerable(GameContext context, GameData data) {
        super(context, data);
    }

    /**
     * verifica se o pacman já está morto caso esteja muda para o gameOver e caso tenho vidas recomeça o nivel
     * todos os elemtos evoluem
     * caso tenho comido o power up transita para o ghostVulnerable
     * @param currentTime tempo atual
     * caso o nível ja não tenha bolas, transita de nível
     */
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

        data.evolveAll();
        if (data.atePowerUp()){
            changeState(GameState.GHOSTS_VULNERABLE,this.getState());
            return true;
        }

        if(data.getBalls() <= 0 && data.getBalls() != -1){
            data.changeLevel();
            changeState(GameState.INITIAL,this.getState());
            return true;
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
            return data.setDirection(keypress);
        }
        return false;
    }

    @Override
    public GameState getState() {
        return GameState.PACMAN_VULNERABLE;
    }
}
