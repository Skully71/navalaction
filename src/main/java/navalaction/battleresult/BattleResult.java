package navalaction.battleresult;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class BattleResult {
    private Set<String> players = new HashSet<>();
    private String winTeam;

    public void addPlayer(final String player) {
        //System.out.println("Player " + player);
        this.players.add(player);
    }

    public void setWinTeam(final String winTeam) {
        this.winTeam = winTeam;
    }
}
