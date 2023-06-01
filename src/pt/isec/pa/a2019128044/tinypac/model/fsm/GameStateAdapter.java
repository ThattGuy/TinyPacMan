package pt.isec.pa.a2019128044.tinypac.model.fsm;

import pt.isec.pa.a2019128044.tinypac.model.data.KEYPRESS;
import pt.isec.pa.a2019128044.tinypac.model.data.GameData;

public abstract class GameStateAdapter implements IGameState {

    protected GameContext context;
    protected GameData data;
    protected long stateTimer = 0;

    protected GameStateAdapter(GameContext context, GameData data) {
        this.context = context;
        this.data = data;
    }

    protected void changeState(GameState newState, GameState lastState) {
        context.changeState(newState.createState(context,data,lastState));
    }

    //todo pause e unpause nos estados que usam

    @Override
    public boolean pressKey(KEYPRESS keypress) {
        return false;
    }

    public boolean evolve(long currentTime) {
        return true;
    }

}