package ccameliek.lilli;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class RangListener {

    public static HashMap<String, String> teams;

    static {
        teams = new HashMap<>();
    }

    public void create(String name, int rank, Component prefix, Component suffix, String permission) {
        String fullName = rank + "_" + name;
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = board.getTeam(fullName);

        if (team != null) {
            team.unregister();
        }
        team = board.registerNewTeam(fullName);

        if (prefix != null) {
            team.prefix(prefix);
        }
        if (suffix != null) {
            team.suffix(suffix);
        }
        teams.put(permission, fullName);
    }


    public void addPlayer(Player p) {
        Team team = null;

        for (String perm : teams.keySet()) {
            if (perm == null || p.hasPermission(perm)) {
                String currentTeamName = teams.get(perm);

                if (team == null || this.getRank(currentTeamName) < this.getRank(team.getName())) {
                    team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(currentTeamName);
                }

            }
        }

        if (team != null) {
            team.addPlayer(p);
        }
    }

    public void update() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            this.removePlayer(players);
            this.addPlayer(players);
        }
    }

    public void removePlayer(Player player) {
        for (String teamName : teams.values()) {
            Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName);

            if (team != null && team.hasPlayer(player)) {
                team.removePlayer(player);
            }
        }
    }

    public int getRank(String teamName) {
        if (!teamName.contains("_")) {
            return -1;
        }

        String[] array = teamName.split("_");
        try {
            return Integer.parseInt(array[0]);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}