package ccameliek.lilli3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.text.DecimalFormat;

public class Scoreboard implements CommandExecutor {

	public Scoreboard(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();

		this.plugin = plugin;
	}

	ConsoleCommandSender console;
	private Lilli plugin;

	int sched;
	private static DecimalFormat df2 = new DecimalFormat("#.##");

	@SuppressWarnings("deprecation")
	static double[] tps = org.bukkit.Bukkit.getTPS();

	@SuppressWarnings("deprecation")
	public static void sendScoreboard(Player player) {
		Scoreboard board = (Scoreboard) Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = ((org.bukkit.scoreboard.Scoreboard) board).getObjective("aaa") != null ? ((org.bukkit.scoreboard.Scoreboard) board).getObjective("aaa")
				: ((org.bukkit.scoreboard.Scoreboard) board).registerNewObjective("aaa", "bbb");

        assert obj != null;
        obj.setDisplayName("Test");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		obj.getScore("Coins").setScore(50);

		player.setScoreboard((org.bukkit.scoreboard.Scoreboard) board);
	}

	@SuppressWarnings("deprecation")
	public static void updateScoreboard(Player player) {
        player.getScoreboard();
        Objective obj = player.getScoreboard().getObjective("aaa") != null ? player.getScoreboard().getObjective("aaa") : player.getScoreboard().registerNewObjective("aaa", "bbb");
        assert obj != null;
        obj.getScore(ChatColor.GREEN + "TPS: " + ChatColor.GOLD + df2.format(tps[0])).setScore(0);
        ;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player p = (Player) sender;
		if (label.equalsIgnoreCase("showtps")) {
			if (p.isOp() || p.hasPermission("Lilli.admin.showtps")) {
				sendScoreboard(p);
				if (!Bukkit.getScheduler().isCurrentlyRunning(sched)) {
					Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
						@Override
						public void run() {
							updateScoreboard(p);
						}
					}, 20, 20);
				}
			}
		} else {
			p.sendMessage(ChatColor.RED + "Du hast keine Rechte dafür!");
		}
		return false;
	}
}
