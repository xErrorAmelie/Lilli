package ccameliek.lilli;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class Lilli extends JavaPlugin implements Listener, CommandExecutor {
	public static TextComponent prefix = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("Lilli").color(NamedTextColor.RED)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
	public static TextComponent adminprefix = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("AdminLilli").color(NamedTextColor.RED)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
	public static TextComponent kowaiprefix = Component.text("[").color(NamedTextColor.DARK_GRAY)
			.append(Component.text("Kowaineko").color(NamedTextColor.DARK_AQUA)
					.append(Component.text("] ").color(NamedTextColor.DARK_GRAY)));
	public static TextComponent admin = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("Owner/in").color(NamedTextColor.LIGHT_PURPLE)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
    public static TextComponent supporter = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("Supporter/in").color(NamedTextColor.GREEN)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
	public static TextComponent spieler = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("Spieler/in").color(NamedTextColor.GREEN)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
	public static TextComponent developer = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("Developer/in").color(NamedTextColor.DARK_GREEN)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
	public static TextComponent moderator = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("Moderator/in").color(NamedTextColor.DARK_GREEN)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
	public static TextComponent stammspieler = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("Stammspieler/in").color(NamedTextColor.GOLD)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
	public static TextComponent spielerplus = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("Spieler/in+").color(NamedTextColor.GOLD)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
	public static TextComponent elite = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("Elite").color(NamedTextColor.DARK_AQUA)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
	public static TextComponent neko = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("Neko").color(NamedTextColor.DARK_PURPLE)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
	public static TextComponent iwie = Component.text("[").color(NamedTextColor.GRAY)
			.append(Component.text("dfdsfs").color(NamedTextColor.GREEN)
					.append(Component.text("] ").color(NamedTextColor.GRAY)));
    public static TextComponent name = Component.text(NamedTextColor.AQUA + "");
    private static Lilli instance;
    public final Map<String, Location> backLocation = new HashMap<String, Location>(10);
    private final ConsoleCommandSender console = getServer().getConsoleSender();
    public Component serverpre = Component.text(Objects.requireNonNull(getConfig().getString(".SERVERNAME")).replace("&", "§"));
    HashMap<Player, Player> tpa = new HashMap<Player, Player>();
    ArrayList<Player> tpaSent = new ArrayList<Player>();
    public boolean defaultperm;
    public ScoreboardLibrary scoreboardLibrary;
    public Sidebar sidebar;

    public static Lilli getInstance() {
        return instance;
    }

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        getConfig().options().parseComments(true);
        saveDefaultConfig();
        saveConfig();
        defaultperm = getConfig().getBoolean("Permission");
        instance = this;
        try {
            scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(this);
        } catch (NoPacketAdapterAvailableException e) {
            getLogger().log(Level.SEVERE, "No packet adapter available");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        sidebar = scoreboardLibrary.createSidebar();
        Scoreboard x = new Scoreboard(this, sidebar);

        Objects.requireNonNull(getCommand("gm")).setExecutor(new GamemodeS());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new FlyS());
        Objects.requireNonNull(getCommand("ping")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("invsee")).setExecutor(new Invsee());
        Objects.requireNonNull(getCommand("ban")).setExecutor(new BanListener(this));
        Objects.requireNonNull(getCommand("rip")).setExecutor(new BanListener(this));
        Objects.requireNonNull(getCommand("unban")).setExecutor(new BanListener(this));
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new Spawn(this));
        Objects.requireNonNull(getCommand("msg")).setExecutor(new Msg());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("lilli")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("tpaccept")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("tpdeny")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("slots")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("discord")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("reply")).setExecutor(new Msg());
        Objects.requireNonNull(getCommand("r")).setExecutor(new Msg());
        Objects.requireNonNull(getCommand("fixtps")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("afk")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("stats")).setExecutor(new StatsListener(this));
        //Objects.requireNonNull(getCommand("showtps")).setExecutor(new Scoreboard(this));
        Objects.requireNonNull(getCommand("mute")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("unmute")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("rtp")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("rules")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("regeln")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("weltraumhelm")).setExecutor(new Mond(this));

        PluginManager pl = Bukkit.getPluginManager();
        pl.registerEvents(this, this);
        pl.registerEvents(new ChatListener(this), this);
        pl.registerEvents(new Spawn(this), this);
        pl.registerEvents(new BanListener(this), this);
        pl.registerEvents(new Baurechte(this), this);
        pl.registerEvents(new AFK(), this);
        pl.registerEvents(new Mond(this), this);
        pl.registerEvents(new Scoreboard(this, sidebar), this);
        loadConfig();

        RangListener teams = new RangListener();

        teams.create("admin", 1, Lilli.admin, null, "Lilli.admin");
        teams.create("supporter/in", 2, Lilli.supporter, null, "Lilli.support");
        teams.create("developer/in", 3, Lilli.developer, null, "Lilli.dev");
        teams.create("moderator/in", 3, Lilli.moderator, null, "Lilli.mod");
        teams.create("stammspieler/in", 4, Lilli.stammspieler, null, "Lilli.stammspieler");
        teams.create("spieler/inplus", 4, Lilli.spielerplus, null, "Lilli.spielerplus");
        teams.create("elite", 4, Lilli.elite, null, "Lilli.elite");
        teams.create("neko", 4, Lilli.neko, null, "Lilli.neko");
        teams.create("spieler/in", 5, Lilli.spieler, null, "Lilli.spieler");
        teams.create("spieler/in", 5, Lilli.spieler, null, null);

        teams.update();
    }

    public void onDisable() {
        if (scoreboardLibrary != null) {
            scoreboardLibrary.close();
        }
        instance = null;
    }

    // Other---------------------------------------------------------------------------------------------------------------------------------------------------

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        new RangListener().removePlayer(event.getPlayer());
        sidebar.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new RangListener().addPlayer(event.getPlayer());
        sidebar.addPlayer(event.getPlayer());
        Bukkit.broadcast(Component.text("Test(!): " + sidebar.players().toString()));
        (new BukkitRunnable() {
            public void run() {
                event.getPlayer().getWorld().playEffect(event.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 0);
                event.getPlayer().getWorld().playEffect(event.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 0);
                event.getPlayer().getWorld().playEffect(event.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 0);
            }
        }).runTaskLater(this, 20L);

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        this.backLocation.put(p.getName(), p.getLocation());
    }

    @EventHandler
    public void ServerPing(ServerListPingEvent event) {
        event.setMaxPlayers(getInstance().getConfig().getInt(".Slots"));
        Component motd = Component.text(Objects.requireNonNull(Objects.requireNonNull(getInstance().getConfig().getString(".Motd")).replaceAll("&", "§")));
        event.motd(motd);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        this.backLocation.put(event.getPlayer().getName(), event.getPlayer().getLocation());
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
