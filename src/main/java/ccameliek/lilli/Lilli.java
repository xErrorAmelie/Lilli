package ccameliek.lilli3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lilli extends JavaPlugin implements Listener, CommandExecutor {

	private Map<String, Location> mm = new HashMap<String, Location>(10);
	HashMap<Player, Player> tpa = new HashMap<Player, Player>();
	ArrayList<Player> tpaSent = new ArrayList<Player>();
	private boolean perms;
	private ConsoleCommandSender console = getServer().getConsoleSender();
	public static String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "Lilli" + ChatColor.GRAY + "] ";
	public static String adminprefix = ChatColor.GRAY + "[" + ChatColor.RED + "AdminLilli" + ChatColor.GRAY + "] ";
	public static String kowaiprefix = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "Kowaineko" + ChatColor.DARK_GRAY + "] ";
	public String serverpre = getConfig().getString(".SERVERNAME").replace("&", "§");
	public static String admin = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "Owner" + ChatColor.GRAY + "] ";
	public static String supporter = ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + "Supporter" + ChatColor.GRAY + "] ";
	public static String player = ChatColor.GRAY + "[" + ChatColor.GREEN + "Spieler" + ChatColor.GRAY + "] ";
	public static String developer = ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + "Developer" + ChatColor.GRAY + "] ";
	public static String moderator = ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + "Moderator" + ChatColor.GRAY + "] ";
	public static String stammspieler = ChatColor.GRAY + "[" + ChatColor.GOLD + "Stammspieler" + ChatColor.GRAY + "] ";
	public static String spielerplus = ChatColor.GRAY + "[" + ChatColor.GOLD + "Spieler+" + ChatColor.GRAY + "] ";
	public static String elite = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Elite" + ChatColor.GRAY + "] ";
	public static String neko = ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "Neko" + ChatColor.GRAY + "] ";
	public static String iwie = ChatColor.GRAY + "[" + ChatColor.GREEN + "dfdsfs" + ChatColor.GRAY + "] ";
	public static String name = ChatColor.AQUA + "";
	public static Plugin pl;
	public static Lilli plugin;
	private static Lilli instance;

	public static Lilli getInstance() {
		return instance;
	}

	public void onEnable() {
		getConfig().options().copyDefaults(true);
		getConfig().options().copyHeader(true);
		saveDefaultConfig();
		saveConfig();
		perms = getConfig().getBoolean("Permission");
		instance = this;

		getCommand("gm").setExecutor(new GamemodeS());
		getCommand("fly").setExecutor(new FlyS());
		getCommand("ping").setExecutor(new Ping());
		getCommand("invsee").setExecutor(new Invsee());
		getCommand("ban").setExecutor(new BanListener(this));
		getCommand("rip").setExecutor(new BanListener(this));
		getCommand("unban").setExecutor(new BanListener(this));
		getCommand("setspawn").setExecutor(new Spawn(this));
		getCommand("msg").setExecutor(new Msg());
		getCommand("spawn").setExecutor(new Cmds(this));
		getCommand("lilli").setExecutor(new Cmds(this));
		getCommand("tpa").setExecutor(new Cmds(this));
		getCommand("tpaccept").setExecutor(new Cmds(this));
		getCommand("tpdeny").setExecutor(new Cmds(this));
		getCommand("slots").setExecutor(new Cmds(this));
		getCommand("discord").setExecutor(new Cmds(this));
		getCommand("reply").setExecutor(new Msg());
		getCommand("r").setExecutor(new Msg());
		getCommand("fixtps").setExecutor(new Cmds(this));
		getCommand("afk").setExecutor(new AFK());
		getCommand("stats").setExecutor(new StatsListener(this));
		getCommand("showtps").setExecutor(new Scoreboard(this));
		getCommand("mute").setExecutor(new Cmds(this));
		getCommand("unmute").setExecutor(new Cmds(this));
		getCommand("rtp").setExecutor(new Cmds(this));
		getCommand("rules").setExecutor(new Cmds(this));
		getCommand("regeln").setExecutor(new Cmds(this));
		getCommand("weltraumhelm").setExecutor(new Mond(this));

		PluginManager pl = Bukkit.getPluginManager();
		pl.registerEvents(this, this);
		pl.registerEvents(new ChatListener(this), this);
		pl.registerEvents(new Spawn(this), this);
		pl.registerEvents(new BanListener(this), this);
		pl.registerEvents(new Weltmessage(this), this);
		pl.registerEvents(new AFK(), this);
		pl.registerEvents(new Mond(this), this);
		loadConfig();

		RangListener teams = new RangListener();

		teams.create("admin", 1, Lilli.admin, null, "Lilli.admin");
		teams.create("supporter", 2, Lilli.supporter, null, "Lilli.supporter");
		teams.create("developer", 3, Lilli.developer, null, "Lilli.dev");
		teams.create("moderator", 3, Lilli.moderator, null, "Lilli.mod");
		teams.create("stammspieler", 4, Lilli.stammspieler, null, "Lilli.stammspieler");
		teams.create("spielerplus", 4, Lilli.spielerplus, null, "Lilli.spielerplus");
		teams.create("elite", 4, Lilli.elite, null, "Lilli.elite");
		teams.create("neko", 4, Lilli.neko, null, "Lilli.neko");
		teams.create("player", 5, Lilli.player, null, "Lilli.spieler");
		teams.create("player", 5, Lilli.player, null, null);

		teams.update();
		this.console.sendMessage(Lilli.prefix + "§afunktionsfähig geladen und aktiviert!");
	}

	public void onDisable() {
		this.console.sendMessage(Lilli.prefix + "deaktiviert und entladen, aufwiedersehen °-°");
	}

	// Other---------------------------------------------------------------------------------------------------------------------------------------------------

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		this.mm.put(p.getName(), p.getLocation());
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("back")) {
			if (!this.perms || (this.perms && (sender.hasPermission("Lilli.back") || sender.isOp()))) {
				if (this.mm.containsKey(sender.getName())) {

					Player p = (Player) sender;
					p.teleport((Location) this.mm.get(p.getName()));
				} else {
					sender.sendMessage(ChatColor.RED + "Du hast keine letzte Position die gespeichert wurde!");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Du hast keine Rechte dafür!");
			}
		}
		return false;
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		this.mm.put(p.getName(), p.getLocation());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		new RangListener().removePlayer(e.getPlayer());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		(new BukkitRunnable() {
			public void run() {
				e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 0);
				e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 0);
				e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 0);
			}
		}).runTaskLater(this, 20L);
	}

	@EventHandler
	public void ServerPing(ServerListPingEvent e) {
		e.setMaxPlayers(getConfig().getInt(".Slots"));
		String motd = getConfig().getString(".Motd");
		motd = motd.replaceAll("&", "\u00A7");
		e.setMotd(motd);
	}

	// ---------Oh Kinder 3 Uhr Morgens------------------
	/*
	 * public void dreiuhr() { while (true) { if
	 * ((Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 10) &&
	 * (Calendar.getInstance().get(Calendar.MINUTE) == 35)) {
	 * Bukkit.broadcastMessage("&dOh Kinder, 3 Uhr Morgens!"); } else { (new
	 * BukkitRunnable() { public void run() { Bukkit.broadcastMessage("" +
	 * Calendar.getInstance().get(Calendar.HOUR_OF_DAY)); Bukkit.broadcastMessage(""
	 * + Calendar.getInstance().get(Calendar.MINUTE)); } }).runTaskLater(this,
	 * 100L); } } }
	 */
}
