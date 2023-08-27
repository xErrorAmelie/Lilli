package ccameliek.lilli.commands;

import ccameliek.lilli.events.back;
import ccameliek.lilli.Lilli;
import ccameliek.lilli.strings.ranks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

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
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("lilli")) {
            if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {

                if (sender.hasPermission("Lilli.reload")) {
                    this.plugin.reloadConfig();
                    sender.sendMessage(ranks.prefix + "Plugin reloaded");
                    return true;
                }

            }
        }
//----------------------------------------------/spawn------------------------------------------------------------
        {
            if (cmd.getName().equalsIgnoreCase("spawn")) {
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
                                player.sendMessage(Component.text("Teleport in " + i + " Sekunden").color(NamedTextColor.GRAY));
                                i--;
                            } else if (i == 1) {
                                player.sendMessage(Component.text("Teleport in " + i + " Sekunde").color(NamedTextColor.GRAY));
                                i--;

                            } else {
                                player.teleport(loc);
                                player.sendMessage(ranks.prefix.append(Component.text("Du wurdest zum Spawn teleportiert!").color(NamedTextColor.GREEN)));
                                i--;
                                Bukkit.getScheduler().cancelTask(c);
                                i = 3;
                            }
                        }
                    }, 0, 20);
                }
            }
        }
        //-----------------------------------------------/back----------------------------------------------------------
        if (cmd.getName().equalsIgnoreCase("back")) {
            if (!plugin.defaultperm || sender.hasPermission("Lilli.back") || sender.isOp()) {
                if (back.backLocation.containsKey(sender.getName())) {
                    player.teleport((Location) back.backLocation.get(player.getName()));
                } else {
                    sender.sendMessage(Component.text("Du hast keine letzte Position die gespeichert wurde!").color(NamedTextColor.RED));
                }
            } else {
                sender.sendMessage(Component.text("Du hast keine Rechte dafür!").color(NamedTextColor.RED));
            }
        }
        // ----------------------------------------------/tpa------------------------------------------------------------
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("TpaFromConsoleError")));
            return true;
        }


        if (cmd.getName().equalsIgnoreCase("tpa")) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("SpecifyPlayerError")));
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("PlayerDoesNotExistError")));
                return true;
            }
            this.plugin.tpa.put(target, player);
            if (!player.isOp()) {
                this.plugin.tpaSent.add(player);
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("RequestSent").replaceAll("/target/", target.getDisplayName())));
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Line1").replaceAll("/sender/", player.getDisplayName())));
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(this.plugin.getConfig().getString("Line2"))));

            return true;
        }

        // ----------------------------------------------/tpaccept------------------------------------------------------------
        if (cmd.getName().equalsIgnoreCase("tpaccept")) {
            if (this.plugin.tpa.get(player) == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("NoRequestToAccept")));
                return true;
            }
            if (this.plugin.tpa.get(player) != null) {
                ((Player) this.plugin.tpa.get(player)).teleport(player);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        this.plugin.getConfig().getString("RequestAcceptedTeleport").replaceAll("/sender/", ((Player) this.plugin.tpa.get(player)).getDisplayName())));
                ((Player) this.plugin.tpa.get(player))
                        .sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("RequestAccepted").replaceAll("/target/", player.getDisplayName())));
                this.plugin.tpa.put(player, null);
                return true;
            }
            return true;
        }
        // ----------------------------------------------/tpdeny------------------------------------------------------------
        if (cmd.getName().equalsIgnoreCase("tpdeny")) {
            if (this.plugin.tpa.get(player) == null) {
                player.sendMessage(LegacyComponentSerializer.legacy('&').serialize(Component.text(Objects.requireNonNull(this.plugin.getConfig().getString("NoRequestToDeny")))));
                return true;
            }
            if (this.plugin.tpa.get(player) != null) {
                player.sendMessage(LegacyComponentSerializer.legacy('&').serialize(Component.text(Objects.requireNonNull(this.plugin.getConfig().getString("RequestDemied")))));

                ((Player) this.plugin.tpa.get(player))
                        .sendMessage(LegacyComponentSerializer.legacy('&').serialize(Component.text(Objects.requireNonNull(this.plugin.getConfig().getString("RequestDeniedTeleport")).replaceAll("/target/", String.valueOf(player.displayName())))));

                this.plugin.tpa.put(player, null);
                return true;
            }
            return true;
        }
        // ---------------------------------------------/slots-------------------------------------------------------------
        if (player.hasPermission("Lilli.admin.slots") || player.isOp()) {
            if (label.equalsIgnoreCase("slots")) {
                if (args.length == 0) {
                    player.sendMessage(ranks.prefix
                            .append(Component.text("Der server hat: ").color(NamedTextColor.GREEN)
                                    .append(Component.text(this.plugin.getConfig().getInt(".Slots")).color(NamedTextColor.GRAY)
                                            .append(Component.text(" Slots!").color(NamedTextColor.GREEN)))));
                    return false;
                } else if (args.length == 1) {
                    int s = Integer.parseInt(args[0]);
                    this.plugin.getConfig().set(".Slots", s);
                    this.plugin.saveConfig();
                    player.sendMessage(ranks.prefix
                            .append(Component.text("Du hast die Serverslots erfolgreich auf ")).color(NamedTextColor.GREEN)
                            .append(Component.text(s)).color(NamedTextColor.GRAY)
                            .append(Component.text(" Slots gesetzt!").color(NamedTextColor.GREEN)));
                    return false;
                }

            }
        }
        // ----------------------------------------------/discord------------------------------------------------------------
        if (label.equalsIgnoreCase("discord")) {
            player.sendMessage(Component.text(" > Joine dem Server Discord: ").color(NamedTextColor.GREEN));
            player.sendMessage(Component.text(Objects.requireNonNull(plugin.getConfig().getString(".Discord"))).color(NamedTextColor.GREEN));
        }
        // -----------------------------------------------/fixtps-------------------------------------------------------------------
        if ((label.equalsIgnoreCase("fixtps")) && (player.isOp() || (player.hasPermission("Lilli.admin.fixtps")))) {
            Bukkit.getOnlinePlayers().forEach(Player -> {
                Bukkit.getWorlds().forEach(world -> {
                    world.getEntities().forEach(entity -> {
                        if ((entity instanceof Zombie) || (entity instanceof Skeleton) || (entity instanceof Spider) || (entity instanceof Creeper) || (entity instanceof Phantom)
                                || (entity instanceof Pillager) || (entity instanceof Item)) {
                            if (entity.customName() == null) {
                                Cmds.this.DeathCount++;
                                entity.remove();
                            }
                        }
                    });
                });
            });
            player.sendMessage(ranks.prefix.append(Component.text(DeathCount + " nutzlose Mobs wurden beseitigt").color(NamedTextColor.GREEN)));
            DeathCount = 0;
        }
        // ---------------------------------------------/ping--------------------------------------------------
        if (cmd.getName().equalsIgnoreCase("ping")) {
            if (args.length == 0) {
                player.sendMessage(ranks.prefix + "Du hast einen Ping von §c" + player.getPing() + "§7 ms");
            } else {
                player.sendMessage(ranks.prefix + "Du hast keine §cRechte §7dafür.");
            }
        }
        // -------------------------------------------/mute--------------------------------------------------------------------
        if (label.equalsIgnoreCase("mute")) {
            if (player.hasPermission("lilli.admin.mute") || player.isOp()) {
                if (args.length == 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    File mute = new File("plugins//Lilli//Mute", target.getUniqueId() + ".yml");
                    YamlConfiguration mutecfg = YamlConfiguration.loadConfiguration(mute);
                    if (mute.exists()) {
                        if (Objects.equals(mutecfg.get(".Mute"), true)) {
                            player.sendMessage(Component.text(target.getName() + " ist bereits gemutet").color(NamedTextColor.RED));
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
                            player.sendMessage(ranks.prefix.append(Component.text(target.getName() + " wurde gemutet!")));
                        }
                    } else {
                        mutecfg.set(".Mute", true);
                        try {
                            mutecfg.save(mute);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        player.sendMessage(ranks.prefix.append(Component.text(target.getName() + " wurde entmutet!")));
                    }
                } else {
                    player.sendMessage(Component.text("/mute <name>").color(NamedTextColor.RED));
                }
            }
        }
        //----------------------------------------------/unmute-----------------------------------------------------------
        if (label.equalsIgnoreCase("unmute")) {
            if (player.hasPermission("lilli.admin.unmute") || player.isOp()) {
                if (args.length == 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    Player targeton = Bukkit.getPlayer(args[0]);
                    File unmute = new File("plugins//Lilli//Mute", target.getUniqueId() + ".yml");
                    YamlConfiguration unmutecfg = YamlConfiguration.loadConfiguration(unmute);
                    if (unmute.exists()) {
                        if (Objects.equals(unmutecfg.get(".Mute"), false)) {
                            player.sendMessage(Component.text(target.getName() + " ist nicht gemutet").color(NamedTextColor.RED));
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
                            player.sendMessage(ranks.prefix.append(Component.text(target.getName() + " wurde entmutet!")));
                            assert targeton != null;
                            targeton.sendMessage(ranks.kowaiprefix.append(Component.text("Du wurdest entmutet!")));

                        }
                    } else {
                        unmutecfg.set(".Mute", false);
                        try {
                            unmutecfg.save(unmute);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        player.sendMessage(ranks.prefix + target.getName() + " wurde entmutet!");
                        assert targeton != null;
                        targeton.sendMessage(ranks.kowaiprefix + "Du wurdest entmutet!");
                    }
                } else {
                    player.sendMessage(Component.text("/unmute <name>").color(NamedTextColor.RED));
                }
            }
        }
        //----------------------------------------------/rtp-------------------------------------------------------
        if (label.equalsIgnoreCase("rtp")) {
            if (player.getWorld().getName().contains("farmwelt") || player.getWorld().getName().contains("world")) {

                if (rtp.get(player.getName()) == null) {
                    rtp.put(player.getName(), 0);
                }
                int d = rtp.get(player.getName());

                if (d == 0) {

                    Random random = new Random();
                    Location teleportLocation = null;
                    int x = random.nextInt(10000) + 1;
                    int y = 254;
                    int z = random.nextInt(10000) + 1;
                    boolean isOnLand = false;

                    while (!isOnLand) {
                        teleportLocation = new Location(player.getWorld(), x, y, z);
                        if (teleportLocation.getBlock().getType() != Material.AIR) {
                            isOnLand = true;
                        } else {
                            y--;
                        }
                    }
                    player.teleport(new Location(player.getWorld(), teleportLocation.getX(), teleportLocation.getY() + 1, teleportLocation.getZ()));
                    int t = 1;
                    rtp.put(player.getName(), t);
                    (new BukkitRunnable() {
                        public void run() {
                            int t = 0;
                            rtp.put(player.getName(), t);

                        }
                    }).runTaskLater(this.plugin, 200L);

                } else {
                    player.sendMessage(Component.text("Du kannst diesen Befehl nur alle 10 Sekunden verwenden!").color(NamedTextColor.RED));
                }
            } else {
                player.sendMessage(Component.text("Du kannst diesen Befehl nur in folgenden Welten benutzen: Farmwelt, Freebuild").color(NamedTextColor.RED));
            }
        }
        //----------------------------------------------/regeln---------------------------------------------------------
        if (label.equalsIgnoreCase("regeln") || label.equalsIgnoreCase("rules")) {
            Bukkit.dispatchCommand(player, "warp regeln");
        }
        return false;
    }
}
