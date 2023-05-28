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

    protected void changeState(GameState newState) {
        context.changeState(newState.createState(context,data));
    }

    //todo pause e unpause nos estados que usam

    @Override
    public boolean pressKey(KEYPRESS keypress) {
        return false;
    }

    @Override
    public boolean allGhostsDead() {
        return false;
    }

    @Override
    public boolean evolve(long currentTime) {
        return false;
    }


}