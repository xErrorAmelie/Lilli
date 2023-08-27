package ccameliek.lilli;

import ccameliek.lilli.commands.*;
import ccameliek.lilli.events.*;
import ccameliek.lilli.strings.prefixes;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public class Lilli extends JavaPlugin implements Listener, CommandExecutor {
    private static Lilli instance;
    public Sidebar sidebar;
    public HashMap<Player, Player> tpa = new HashMap<Player, Player>();
    public ArrayList<Player> tpaSent = new ArrayList<Player>();
    public boolean defaultperm;
    public ScoreboardLibrary scoreboardLibrary;
    public Component serverpre = Component.text(Objects.requireNonNull(getConfig().getString(".SERVERNAME")).replace("&", "§"));


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
        registerEvents();
        registerCommands();
        loadConfig();
        enableTeams();
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


    public void enableTeams(){
        Rangs teams = new Rangs();
        teams.create("admin", 1, prefixes.permissionPrefixes.get("Lilli.admin"), null, "Lilli.admin");
        teams.create("supporter/in", 2, prefixes.permissionPrefixes.get("Lilli.support"), null, "Lilli.support");
        teams.create("developer/in", 3, prefixes.permissionPrefixes.get("Lilli.dev"), null, "Lilli.dev");
        teams.create("moderator/in", 3, prefixes.permissionPrefixes.get("Lilli.mod"), null, "Lilli.mod");
        teams.create("stammspieler/in", 4, prefixes.permissionPrefixes.get("Lilli.stammspieler"), null, "Lilli.stammspieler");
        teams.create("spieler/inplus", 4, prefixes.permissionPrefixes.get("Lilli.spielerplus"), null, "Lilli.spielerplus");
        teams.create("elite", 4, prefixes.permissionPrefixes.get("Lilli.elite"), null, "Lilli.elite");
        teams.create("neko", 4, prefixes.permissionPrefixes.get("Lilli.neko"), null, "Lilli.neko");
        teams.create("spieler/in", 5, prefixes.permissionPrefixes.get("Lilli.spieler"), null, "Lilli.spieler");
        teams.create("spieler/in", 5, prefixes.defaultNamePrefix, null, null);
        teams.update();
    }

    public void registerEvents(){
        PluginManager pl = Bukkit.getPluginManager();
        pl.registerEvents(this, this);
        pl.registerEvents(new ChatListener(this), this);
        pl.registerEvents(new SpawnListener(this), this);
        pl.registerEvents(new Bans(this), this);
        pl.registerEvents(new BaurechteListener(this), this);
        pl.registerEvents(new AFKListener(), this);
        pl.registerEvents(new MondListener(this), this);
        pl.registerEvents(new Scoreboard(this, sidebar), this);
    }

    public void registerCommands(){
        Objects.requireNonNull(getCommand("gm")).setExecutor(new GamemodeS());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new FlyS());
        Objects.requireNonNull(getCommand("ping")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("invsee")).setExecutor(new Invsee());
        Objects.requireNonNull(getCommand("ban")).setExecutor(new Bans(this));
        Objects.requireNonNull(getCommand("rip")).setExecutor(new Bans(this));
        Objects.requireNonNull(getCommand("unban")).setExecutor(new Bans(this));
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
        Objects.requireNonNull(getCommand("stats")).setExecutor(new Stats(this));
        //Objects.requireNonNull(getCommand("showtps")).setExecutor(new Scoreboard(this));
        Objects.requireNonNull(getCommand("mute")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("unmute")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("rtp")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("rules")).setExecutor(new Cmds(this));
        Objects.requireNonNull(getCommand("regeln")).setExecutor(new Cmds(this));
    }




    public Component permprefix (Player player) {
        for(String permission: prefixes.permissionPrefixes.keySet()){
            if(player.hasPermission(permission)) {
                return prefixes.permissionPrefixes.get(permission);
            }
        }
        return prefixes.defaultNamePrefix;
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
