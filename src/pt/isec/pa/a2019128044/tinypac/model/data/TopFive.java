package pt.isec.pa.a2019128044.tinypac.model.data;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class TopFive implements Serializable {

    private static final SortedSet<Player> topFivePlayers = new TreeSet<>(Comparator.comparingInt(Player::getScore).reversed()); ;

    public static void addPlayer(Player player) {
        if (!checkIfTopFive(player.getScore())) {
            return;
        }

        topFivePlayers.add(player);

        if (topFivePlayers.size() > 5) {
            topFivePlayers.remove(topFivePlayers.last());
        }
    }

    public static boolean checkIfTopFive(int score) {
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

    public static void setTopFive(List<Player> players) {
        topFivePlayers.clear();
        topFivePlayers.addAll(players);
    }
}
