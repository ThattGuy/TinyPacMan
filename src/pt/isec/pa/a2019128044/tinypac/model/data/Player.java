package pt.isec.pa.a2019128044.tinypac.model.data;

import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private int score;

    public Player(String name, int points) {
        this.name = name;
        this.score = points;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
