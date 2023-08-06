package ccameliek.lilli3;

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

public class Weltmessage implements Listener {
	public Weltmessage(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();

		this.plugin = plugin;
	}

	ConsoleCommandSender console;
	private Lilli plugin;

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		final Player p = e.getPlayer();
		if (!p.isOp()) {
			(new BukkitRunnable() {
				public void run() {
					Location playerloc = p.getLocation();
					World world = playerloc.getWorld();
					if (world.getName().contains("kreativ")) {
						p.setGameMode(GameMode.CREATIVE);
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set worldedit.*");
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set worldguard.*");
					} else if (p.hasPermission("worldedit.*") || p.hasPermission("worldguard.*")) {
						p.setGameMode(GameMode.SURVIVAL);
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission unset worldedit.*");
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission unset worldguard.*");
					}
				}
			}).runTaskLater(this.plugin, 40L);
		}
	}

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		if (!p.isOp()) {
			Location playerloc = p.getLocation();
			final World world = playerloc.getWorld();
			(new BukkitRunnable() {
				public void run() {
					if (world.getName().contains("kreativ")) {
						p.setGameMode(GameMode.CREATIVE);
						p.sendMessage("Du hast die Welt '" + world.getName() + "' betreten, du bist jetzt im Kreativ-Modus");
					} else {
						p.setGameMode(GameMode.SURVIVAL);
					}
				}
			}).runTaskLater(this.plugin, 40L);
		}
	}
}