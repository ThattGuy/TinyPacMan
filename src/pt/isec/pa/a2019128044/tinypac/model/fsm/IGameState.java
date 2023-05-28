package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;

public interface IGameState {
    boolean pressKey(KEYPRESS keypress);
    boolean allGhostsDead();
    boolean evolve(long currentTime);
    GameState getState();
}
