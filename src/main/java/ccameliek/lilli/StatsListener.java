package ccameliek.lilli3;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class StatsListener implements CommandExecutor, Listener {

	public StatsListener(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();

		this.plugin = plugin;
	}

	ConsoleCommandSender console;
	@SuppressWarnings("unused")
	private Lilli plugin;

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("stats")) {
			if (args.length == 1) {
				Player target = (Player) Bukkit.getOfflinePlayer(args[0]);
				int timesincedeath = target.getStatistic(Statistic.TIME_SINCE_DEATH) / 20 / 60 / 60;
				int timesincedeathmin = target.getStatistic(Statistic.TIME_SINCE_DEATH) / 20 / 60;
				int playtime = target.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60 / 60;
				int playtimemin = target.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;
				player.sendMessage(Lilli.kowaiprefix + ChatColor.GREEN + "Stats von " + ChatColor.AQUA + target.getName());
				if (timesincedeath > 1) {
					player.sendMessage(ChatColor.GRAY + "Spielzeit: " + ChatColor.GOLD + playtime + " Stunden");
				} else {
					player.sendMessage(ChatColor.GRAY + "Spielzeit: " + ChatColor.GOLD + playtimemin + " Minuten");
				}
				player.sendMessage(ChatColor.GRAY + "Tode: " + ChatColor.GOLD + target.getStatistic(Statistic.DEATHS));
				player.sendMessage(ChatColor.GRAY + "Spielerkills: " + ChatColor.GOLD + target.getStatistic(Statistic.PLAYER_KILLS));
				player.sendMessage(ChatColor.GRAY + "Mobkills: " + ChatColor.GOLD + target.getStatistic(Statistic.MOB_KILLS));
				if (timesincedeath > 1) {
					player.sendMessage(ChatColor.GRAY + "Letztes Mal gestorben:" + ChatColor.GOLD + " vor " + timesincedeath + " Stunden");
				} else {
					player.sendMessage(ChatColor.GRAY + "Letztes Mal gestorben:" + ChatColor.GOLD + " vor " + timesincedeathmin + " Minuten");
				}
			} else {
				int timesincedeath = player.getStatistic(Statistic.TIME_SINCE_DEATH) / 20 / 60 / 60;
				int timesincedeathmin = player.getStatistic(Statistic.TIME_SINCE_DEATH) / 20 / 60;
				int playtime = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60 / 60;
				int playtimemin = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;

				player.sendMessage(Lilli.kowaiprefix + ChatColor.GREEN + "Stats von " + ChatColor.AQUA + player.getName());
				if (playtime > 1) {
					player.sendMessage(ChatColor.GRAY + "Spielzeit: " + ChatColor.GOLD + playtime + " Stunden");
				} else {
					player.sendMessage(ChatColor.GRAY + "Spielzeit: " + ChatColor.GOLD + playtimemin + " Minuten");
				}
				player.sendMessage(ChatColor.GRAY + "Tode: " + ChatColor.GOLD + player.getStatistic(Statistic.DEATHS));
				player.sendMessage(ChatColor.GRAY + "Spielerkills: " + ChatColor.GOLD + player.getStatistic(Statistic.PLAYER_KILLS));
				player.sendMessage(ChatColor.GRAY + "Mobkills: " + ChatColor.GOLD + player.getStatistic(Statistic.MOB_KILLS));
				if (timesincedeath > 1) {
					player.sendMessage(ChatColor.GRAY + "Letztes Mal gestorben:" + ChatColor.GOLD + " vor " + timesincedeath + " Stunden");
				} else {
					player.sendMessage(ChatColor.GRAY + "Letztes Mal gestorben:" + ChatColor.GOLD + " vor " + timesincedeathmin + " Minuten");
				}
			}
		}
		return false;
	}
}
