package ccameliek.lilli.events;

import ccameliek.lilli.Lilli;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.File;

public class SpawnListener implements Listener {

	public SpawnListener(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();
	}

	ConsoleCommandSender console;

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();

		File file = new File("plugins//Lilli//spawn.yml");

		if (p.getBedSpawnLocation() == null) {
			if (!file.exists()) {
				return;
			}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			Location loc = p.getLocation();
			double x = cfg.getDouble("X");
			double y = cfg.getDouble("Y");
			double z = cfg.getDouble("Z");
			double yaw = cfg.getDouble("Yaw");
			double pitch = cfg.getDouble("Pitch");
			String worldname = cfg.getString("Worldname");

            assert worldname != null;
            World welt = Bukkit.getWorld(worldname);

			loc.setX(x);
			loc.setY(y);
			loc.setZ(z);
			loc.setYaw((float) yaw);
			loc.setPitch((float) pitch);
			loc.setWorld(welt);
			e.setRespawnLocation(loc);
			p.teleport(loc);
		}
	}

	// -----------------------------------------------Beim Join--------------------------------------------------
	@EventHandler
	public void onJoinSpawn(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (!p.hasPlayedBefore()) {
			File file = new File("plugins//Lilli//spawn.yml");
			if (!file.exists()) {
				p.sendMessage(Component.text("Es wurde kein Spawn gesetzt"));
			}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			double x = cfg.getDouble("X");
			double y = cfg.getDouble("Y");
			double z = cfg.getDouble("Z");
			String worldname = cfg.getString("Worldname");
            assert worldname != null;
            World world = Bukkit.getWorld(worldname);

			Location loc = new Location(world, x, y, z);
			p.teleport(loc);
		}
	}
}
