package ccameliek.lilli;

import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.File;
import java.io.IOException;

public class Spawn implements CommandExecutor, Listener {

	public Spawn(Lilli plugin) {
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

	// ---------------------------------------------------Beim
	// Join------------------------------------------------------------------------------
	@EventHandler
	public void onJoinSpawn(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (!p.hasPlayedBefore()) {
			File file = new File("plugins//Lilli//spawn.yml");
			if (!file.exists()) {
				p.sendMessage("Es wurde kein Spawn gesetzt");
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

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			System.out.println("???");
			return true;
		}
		Player p = (Player) sender;

		if (!p.hasPermission("Lilli.setspawn")) {
			p.sendMessage("Du hast keine Permission");
			return true;
		}

		File ordner = new File("plugins//Lilli");
		File file = new File("plugins//Lilli//spawn.yml");

		if (!ordner.exists()) {
			ordner.mkdir();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				p.sendMessage(NamedTextColor.RED + "Die Datei konnte auf dem Pfad nicht erstellt werden");
			}
		}

		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		Location loc = p.getLocation();

		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		double yaw = loc.getYaw();
		double pitch = loc.getPitch();
		String worldname = loc.getWorld().getName();

		cfg.set("X", x);
		cfg.set("Y", y);
		cfg.set("Z", z);
		cfg.set("Yaw", yaw);
		cfg.set("Pitch", pitch);
		cfg.set("Worldname", worldname);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.sendMessage(Lilli.prefix.toString() + NamedTextColor.GREEN + "Du hast den globalen Spawn gesetzt");

		return true;
	}
}
