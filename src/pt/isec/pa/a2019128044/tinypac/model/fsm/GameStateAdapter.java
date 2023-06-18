package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;

public abstract class GameStateAdapter implements IGameState {

    protected GameContext context;
    protected GameData data;
    protected long stateTimer = 0;

    /**
     * state adapter fsm
     * @param context rece como parametro o contexto
     * @param data recebe como paremetro os dodos do jogo
     */
    protected GameStateAdapter(GameContext context, GameData data) {
        this.context = context;
        this.data = data;
    }

    protected void changeState(GameState newState, GameState lastState) {
        context.changeState(newState.createState(context,data,lastState));
    }

    @Override
    public boolean pressKey(KEYPRESS keypress) {
        return false;
    }

    public boolean evolve(long currentTime) {
        return true;
    }

}