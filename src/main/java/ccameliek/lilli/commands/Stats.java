package ccameliek.lilli.commands;

import ccameliek.lilli.Lilli;
import ccameliek.lilli.strings.prefixes;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class Stats implements CommandExecutor, Listener {

	public Stats(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();

	}

	ConsoleCommandSender console;
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("stats")) {
			if (args.length == 1) {
				Player target = (Player) Bukkit.getOfflinePlayer(args[0]);
				int timesincedeath = target.getStatistic(Statistic.TIME_SINCE_DEATH) / 20 / 60 / 60;
				int timesincedeathmin = target.getStatistic(Statistic.TIME_SINCE_DEATH) / 20 / 60;
				int playtime = target.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60 / 60;
				int playtimemin = target.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;
				player.sendMessage(prefixes.kowaiprefix.toString() + NamedTextColor.GREEN + "Stats von " + NamedTextColor.AQUA + target.getName());
				if (timesincedeath > 1) {
					player.sendMessage(NamedTextColor.GRAY + "Spielzeit: " + NamedTextColor.GOLD + playtime + " Stunden");
				} else {
					player.sendMessage(NamedTextColor.GRAY + "Spielzeit: " + NamedTextColor.GOLD + playtimemin + " Minuten");
				}
				player.sendMessage(NamedTextColor.GRAY + "Tode: " + NamedTextColor.GOLD + target.getStatistic(Statistic.DEATHS));
				player.sendMessage(NamedTextColor.GRAY + "Spielerkills: " + NamedTextColor.GOLD + target.getStatistic(Statistic.PLAYER_KILLS));
				player.sendMessage(NamedTextColor.GRAY + "Mobkills: " + NamedTextColor.GOLD + target.getStatistic(Statistic.MOB_KILLS));
				if (timesincedeath > 1) {
					player.sendMessage(NamedTextColor.GRAY + "Letztes Mal gestorben:" + NamedTextColor.GOLD + " vor " + timesincedeath + " Stunden");
				} else {
					player.sendMessage(NamedTextColor.GRAY + "Letztes Mal gestorben:" + NamedTextColor.GOLD + " vor " + timesincedeathmin + " Minuten");
				}
			} else {
				int timesincedeath = player.getStatistic(Statistic.TIME_SINCE_DEATH) / 20 / 60 / 60;
				int timesincedeathmin = player.getStatistic(Statistic.TIME_SINCE_DEATH) / 20 / 60;
				int playtime = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60 / 60;
				int playtimemin = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60;

				player.sendMessage(prefixes.kowaiprefix.toString() + NamedTextColor.GREEN + "Stats von " + NamedTextColor.AQUA + player.getName());
				if (playtime > 1) {
					player.sendMessage(NamedTextColor.GRAY + "Spielzeit: " + NamedTextColor.GOLD + playtime + " Stunden");
				} else {
					player.sendMessage(NamedTextColor.GRAY + "Spielzeit: " + NamedTextColor.GOLD + playtimemin + " Minuten");
				}
				player.sendMessage(NamedTextColor.GRAY + "Tode: " + NamedTextColor.GOLD + player.getStatistic(Statistic.DEATHS));
				player.sendMessage(NamedTextColor.GRAY + "Spielerkills: " + NamedTextColor.GOLD + player.getStatistic(Statistic.PLAYER_KILLS));
				player.sendMessage(NamedTextColor.GRAY + "Mobkills: " + NamedTextColor.GOLD + player.getStatistic(Statistic.MOB_KILLS));
				if (timesincedeath > 1) {
					player.sendMessage(NamedTextColor.GRAY + "Letztes Mal gestorben:" + NamedTextColor.GOLD + " vor " + timesincedeath + " Stunden");
				} else {
					player.sendMessage(NamedTextColor.GRAY + "Letztes Mal gestorben:" + NamedTextColor.GOLD + " vor " + timesincedeathmin + " Minuten");
				}
			}
		}
		return false;
	}
}
