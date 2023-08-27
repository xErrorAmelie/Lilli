package ccameliek.lilli.events;

import ccameliek.lilli.Lilli;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BaurechteListener implements Listener {
	public BaurechteListener(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();

		this.plugin = plugin;
	}

	ConsoleCommandSender console;
	private final Lilli plugin;

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		final Player player = event.getPlayer();
		if (!player.isOp()) {
			(new BukkitRunnable() {
				public void run() {
					Location playerloc = player.getLocation();
					World world = playerloc.getWorld();
					if (world.getName().contains("kreativ")) {
						player.setGameMode(GameMode.CREATIVE);
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set worldedit.*");
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set worldguard.*");
					} else if (player.hasPermission("worldedit.*") || player.hasPermission("worldguard.*")) {
						player.setGameMode(GameMode.SURVIVAL);
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission unset worldedit.*");
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission unset worldguard.*");
					}
				}
			}).runTaskLater(this.plugin, 40L);
		}
	}

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if (!player.isOp()) {
			Location playerloc = player.getLocation();
			final World world = playerloc.getWorld();
			(new BukkitRunnable() {
				public void run() {
					if (world.getName().contains("kreativ")) {
						player.setGameMode(GameMode.CREATIVE);
						player.sendMessage("Du hast die Welt '" + world.getName() + "' betreten, du bist jetzt im Kreativ-Modus");
					} else {
						player.setGameMode(GameMode.SURVIVAL);
					}
				}
			}).runTaskLater(this.plugin, 40L);
		}
	}
}