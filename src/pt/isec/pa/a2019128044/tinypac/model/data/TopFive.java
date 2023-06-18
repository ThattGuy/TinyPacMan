package pt.isec.pa.a2019128044.tinypac.model.data;

import java.io.Serializable;
import java.util.*;

public class TopFive implements Serializable {

    private static final SortedSet<Player> topFivePlayers = new TreeSet<>(Comparator.comparingInt(Player::getScore).reversed()); ;

    public static void addPlayer(String name, int score) {

        Player player = new Player(name,score);

        if (!checkIfTopFive(player.getScore())) {
            return;
        }

        topFivePlayers.add(player);

        if (topFivePlayers.size() > 5) {
            topFivePlayers.remove(topFivePlayers.last());
        }

    }

    public static boolean checkIfTopFive(int score) {

        if(topFivePlayers.size() == 0 || topFivePlayers.size() < 5){
            return true;
        }

        for (Player player : topFivePlayers.stream().toList()) {
            int existingScore = player.getScore();
            if (score > existingScore) {
                return true;
            }
        }
        return false;
    }

    public static List<Player> getTopFive() {
        return topFivePlayers.stream().toList();
    }

    public static List<String> getTopFiveString() {

        List<String> ret = new ArrayList<>();
        for (Player player: topFivePlayers) {
            ret.add(String.format("Player: %s \nScore: %s", player.getName(), player.getScore()));
        }
        return ret;
    }

    public static void setTopFive(List<Player> players) {
        topFivePlayers.clear();
        topFivePlayers.addAll(players);
    }
}
