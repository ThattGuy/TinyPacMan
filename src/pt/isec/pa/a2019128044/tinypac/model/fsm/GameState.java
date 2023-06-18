package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.model.data.GameData;
import pt.isec.pa.a2019128044.tinypac.model.fsm.gamestates.*;

/**
 * enum estados
 */
public enum GameState {
    INITIAL, WARMUP, PACMAN_VULNERABLE, GHOSTS_VULNERABLE, GAMEOVER, PAUSE;

    /**
     * factory estados
     * @param context contexto
     * @param game dados de jogo
     * @param lastState ultimo estado, usado no pause para reverter ao estado anterior
     * @return
     */
    IGameState createState(GameContext context, GameData game,GameState lastState) {
        return switch (this) {
            case INITIAL -> new Initial(context,game);
            case WARMUP -> new Warmup(context,game);
            case PACMAN_VULNERABLE -> new PacmanVulnerable(context,game);
            case GHOSTS_VULNERABLE -> new GhostsVulnerable(context,game);
            case GAMEOVER -> new GameOver(context,game);
            case PAUSE -> new Pause(context,game,lastState);
        };
    }
}
