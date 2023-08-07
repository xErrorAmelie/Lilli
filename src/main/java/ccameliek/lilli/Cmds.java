package ccameliek.lilli;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Cmds implements Listener, CommandExecutor {

	public Cmds(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();

		this.plugin = plugin;
	}

	ConsoleCommandSender console;
	private final Lilli plugin;

	private int c;
	private int i = 3;
	private int DeathCount = 0;

	private static final Map<String, Integer> rtp = new HashMap<String, Integer>();

//----------------------------------------------/reload------------------------------------------------------------
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("lilli")) {
			if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {

					if (sender.hasPermission("Lilli.reload")) {
						this.plugin.reloadConfig();
						sender.sendMessage(Lilli.prefix + "Plugin reloaded");
						return true;
					}

			}
		}
//----------------------------------------------/spawn------------------------------------------------------------
		Player p = (Player) sender;
		{
			if(cmd.getName().equalsIgnoreCase("spawn")) {
			File file = new File("plugins//Lilli//spawn.yml");
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			double x = cfg.getDouble("X");
			double y = cfg.getDouble("Y");
			double z = cfg.getDouble("Z");
			String worldname = cfg.getString("Worldname");
			assert worldname != null;
			World world = Bukkit.getWorld(worldname);

			Location loc = new Location(world, x, y, z);

			// ----------------------------------------
			if (!Bukkit.getScheduler().isCurrentlyRunning(c)) {
				c = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {

					@Override
					public void run() {
						if (i > 1) {
							p.sendMessage(ChatColor.GRAY + "Teleport in " + i + " Sekunden");
							i--;
						} else if (i == 1) {
							p.sendMessage(ChatColor.GRAY + "Teleport in " + i + " Sekunde");
							i--;

						} else if (i < 1) {
							p.teleport(loc);
							p.sendMessage(Lilli.prefix.toString() + ChatColor.GREEN + "Du wurdest zum Spawn teleportiert!");
							i--;
							Bukkit.getScheduler().cancelTask(c);
							i = 3;
						}
					}
				}, 0, 20);
			}
		}
		}

		// ----------------------------------------------/tpa------------------------------------------------------------
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("TpaFromConsoleError")));
			return true;
		}

		final Player p1 = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("tpa")) {
			if (args.length == 0) {
				p1.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("SpecifyPlayerError")));
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) {
				p1.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("PlayerDoesNotExistError")));
				return true;
			}
			this.plugin.tpa.put(target, p1);
			if (!p1.isOp()) {
				this.plugin.tpaSent.add(p1);
			}
			p1.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("RequestSent").replaceAll("/target/", target.getDisplayName())));
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Line1").replaceAll("/sender/", p1.getDisplayName())));
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(this.plugin.getConfig().getString("Line2"))));

			return true;
		}

		// ----------------------------------------------/tpaccept------------------------------------------------------------
		if (cmd.getName().equalsIgnoreCase("tpaccept")) {
			if (this.plugin.tpa.get(p1) == null) {
				p1.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("NoRequestToAccept")));
				return true;
			}
			if (this.plugin.tpa.get(p1) != null) {
				((Player) this.plugin.tpa.get(p1)).teleport(p1);
				p1.sendMessage(ChatColor.translateAlternateColorCodes('&',
						this.plugin.getConfig().getString("RequestAcceptedTeleport").replaceAll("/sender/", ((Player) this.plugin.tpa.get(p1)).getDisplayName())));
				((Player) this.plugin.tpa.get(p1))
						.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("RequestAccepted").replaceAll("/target/", p1.getDisplayName())));
				this.plugin.tpa.put(p1, null);
				return true;
			}
			return true;
		}
		// ----------------------------------------------/tpdeny------------------------------------------------------------
		if (cmd.getName().equalsIgnoreCase("tpdeny")) {
			if (this.plugin.tpa.get(p1) == null) {
				p1.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("NoRequestToDeny")));
				return true;
			}
			if (this.plugin.tpa.get(p1) != null) {
				p1.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("RequestDenied")));
				((Player) this.plugin.tpa.get(p1))
						.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("RequestDeniedTeleport").replaceAll("/target/", p1.getDisplayName())));
				this.plugin.tpa.put(p1, null);
				return true;
			}
			return true;
		}
		// ---------------------------------------------/slots-------------------------------------------------------------
		if (p.hasPermission("Lilli.admin.slots") || p.isOp()) {
			if (label.equalsIgnoreCase("slots")) {
				if (args.length == 0) {
					p.sendMessage(Lilli.prefix.toString() + ChatColor.GREEN + "Der Server hat: " + ChatColor.GRAY + this.plugin.getConfig().getInt(".Slots") + ChatColor.GREEN + " Slots!");
					return false;
				}else if (args.length == 1) {
						int s = Integer.valueOf(args[0]);
						this.plugin.getConfig().set(".Slots", s);
						this.plugin.saveConfig();
						p.sendMessage(Lilli.prefix.toString() + ChatColor.GREEN + "Du hast die Serverslots erfolgreich auf " + ChatColor.GRAY + s + ChatColor.GREEN + " Slots gesetzt!");
						return false;
					}

			}
		}
		// ----------------------------------------------/discord------------------------------------------------------------
		if (label.equalsIgnoreCase("discord")) {
			p.sendMessage(ChatColor.GREEN + " > Joine dem Server Discord: ");
			p.sendMessage(ChatColor.WHITE + plugin.getConfig().getString(".Discord"));
		}
		// -----------------------------------------------/fixtps-------------------------------------------------------------------
		if ((label.equalsIgnoreCase("fixtps")) && (p.isOp() || (p.hasPermission("Lilli.admin.fixtps")))) {
			Bukkit.getOnlinePlayers().forEach(Player -> {
				Bukkit.getWorlds().forEach(world -> {
					world.getEntities().forEach(entity -> {
						if ((entity instanceof Zombie) || (entity instanceof Skeleton) || (entity instanceof Spider) || (entity instanceof Creeper) || (entity instanceof Phantom)
								|| (entity instanceof Pillager) || (entity instanceof Item)) {
							if (entity.getCustomName() == null) {
								Cmds.this.DeathCount++;
								entity.remove();
							}
						}
					});
				});
			});
			p.sendMessage(Lilli.prefix.toString() + ChatColor.GREEN + DeathCount + " nutzlose Mobs wurden beseitigt!");
			DeathCount = 0;
		}
		if (label.equalsIgnoreCase("mute")) {
			if (p.hasPermission("lilli.admin.mute") || p.isOp()) {
				if (args.length == 1) {
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					File mute = new File("plugins//Lilli//Mute", target.getUniqueId() + ".yml");
					YamlConfiguration mutecfg = YamlConfiguration.loadConfiguration(mute);
					if (mute.exists()) {
						if (mutecfg.get(".Mute").equals(true)) {
							p.sendMessage(ChatColor.RED + "" + target.getName() + " ist beretis gemutet");
							try {
								mutecfg.save(mute);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							mutecfg.set(".Mute", true);
							try {
								mutecfg.save(mute);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(Lilli.prefix + target.getName() + " wurde gemutet!");
						}
					} else {
						mutecfg.set(".Mute", true);
						try {
							mutecfg.save(mute);
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.sendMessage(Lilli.prefix + target.getName() + " wurde gemutet!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "/mute <name>");
				}
			}
		}
		if (label.equalsIgnoreCase("unmute")) {
			if (p.hasPermission("lilli.admin.unmute") || p.isOp()) {
				if (args.length == 1) {
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					Player targeton = Bukkit.getPlayer(args[0]);
					File unmute = new File("plugins//Lilli//Mute", target.getUniqueId() + ".yml");
					YamlConfiguration unmutecfg = YamlConfiguration.loadConfiguration(unmute);
					if (unmute.exists()) {
						if (unmutecfg.get(".Mute").equals(false)) {
							p.sendMessage(ChatColor.RED + "" + target.getName() + " ist nicht gemutet");
							try {
								unmutecfg.save(unmute);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							unmutecfg.set(".Mute", false);
							try {
								unmutecfg.save(unmute);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(Lilli.prefix + target.getName() + " wurde entmutet!");
							targeton.sendMessage(Lilli.kowaiprefix + "Du wurdest entmutet!");

						}
					} else {
						unmutecfg.set(".Mute", false);
						try {
							unmutecfg.save(unmute);
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.sendMessage(Lilli.prefix + target.getName() + " wurde entmutet!");
						targeton.sendMessage(Lilli.kowaiprefix + "Du wurdest entmutet!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "/unmute <name>");
				}
			}
		}
		if (label.equalsIgnoreCase("rtp")) {
			if (p.getWorld().getName().contains("farmwelt") || p.getWorld().getName().contains("world")) {

				if (rtp.get(p.getName()) == null) {
					rtp.put(p.getName(), 0);
				}
				int d = rtp.get(p.getName());

				if (d == 0) {

					Random random = new Random();
					Location teleportLocation = null;
					int x = random.nextInt(10000) + 1;
					int y = 254;
					int z = random.nextInt(10000) + 1;
					boolean isOnLand = false;

					while (isOnLand == false) {
						teleportLocation = new Location(p.getWorld(), x, y, z);
						if (teleportLocation.getBlock().getType() != Material.AIR) {
							isOnLand = true;
						} else {
							y--;
						}
					}
					p.teleport(new Location(p.getWorld(), teleportLocation.getX(), teleportLocation.getY() + 1, teleportLocation.getZ()));
					int t = 1;
					rtp.put(p.getName(), t);
					(new BukkitRunnable() {
						public void run() {
							int t = 0;
							rtp.put(p.getName(), t);

						}
					}).runTaskLater(this.plugin, 200L);

				} else {
					p.sendMessage(ChatColor.RED + "Du kannst diesen Befehl nur alle 10 Sekunden verwenden!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Du kannst diesen Befehl nur in folgenden Welten benutzen: Farmwelt, Freebuild");
			}
		}
		if (label.equalsIgnoreCase("regeln") || label.equalsIgnoreCase("rules")) {
			Bukkit.dispatchCommand(p, "warp regeln");
		}
		return false;
	}
}
