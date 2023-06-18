package pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameContext;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameState;
import pt.isec.pa.a2019128044.tinypac.model.fsm.GameStateAdapter;

import java.io.Serializable;

public class GhostsVulnerable extends GameStateAdapter implements Serializable {
    public GhostsVulnerable(GameContext context, GameData data) {
        super(context, data);
        data.setGhostsVulnerability(true);
    }

    /**
     * Evolve ghost vulnerable
     * verifica se o pacman já está morto caso esteja muda para o gameOver e caso tenho vidas recomeça o nivel
     * caso passem 10 segundos transita para o estado PacmanVulnerable
     * caso o nível ja não tenha bolas, transita de nível
     * caso todos os fantasmas voltem ao spawn transita para o estado PacmanVulnerable
     * @param currentTime hora atual
     *
     */
    @Override
    public boolean evolve(long currentTime) {
        if(stateTimer == 0){
            stateTimer = currentTime;
        }

        if(!data.isPacmanAlive()){
            if(data.getLives() == 0){
                changeState(GameState.GAMEOVER,this.getState());
                return true;
            }
            data.restartLevel();
            changeState(GameState.WARMUP,this.getState());
            return true;
        }


        data.evolveAll();

        if (currentTime - stateTimer >= 10000000000L) {
                data.setPowerUp(false);
                data.setGhostsVulnerability(false);
                changeState(GameState.PACMAN_VULNERABLE,this.getState());
        }

        if(!data.checkGhostsVulnerability()){
            changeState(GameState.PACMAN_VULNERABLE,this.getState());
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
        return GameState.GHOSTS_VULNERABLE;
    }
}
