package ccameliek.lilli;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.util.Objects;

public class ChatListener implements Listener {
    Scoreboard board;

    public ChatListener(Lilli plugin) {
        this.console = Bukkit.getServer().getConsoleSender();
        this.plugin = plugin;
    }

    ConsoleCommandSender console;
    private final Lilli plugin;

    private Component permprefix (Player player) {
        for(String permission: Lilli.permissionPrefixes.keySet()){
            if(player.hasPermission(permission)) {
                return Lilli.permissionPrefixes.get(permission);
            }
        }
        return Lilli.defaultNamePrefix;
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player p = e.getPlayer();
        Component Msg = e.message();
        if (p.hasPermission("Lilli.admin")) {
            e.message(permprefix(p)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Msg.color(NamedTextColor.WHITE)));
            File mute = new File("plugins//Lilli//Mute", p.getUniqueId() + ".yml");
            YamlConfiguration mutecfg = YamlConfiguration.loadConfiguration(mute);
            if (mutecfg.getBoolean(".Mute")) {
                e.setCancelled(true);
                p.sendMessage(NamedTextColor.RED + Lilli.kowaiprefix.toString() + "Du bist gemutet");
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                .append(permprefix(p))
                .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
        p.playerListName(permprefix(p)
                .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        p.displayName(permprefix(p)
                .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        if (!p.hasPlayedBefore()) {
            e.joinMessage(Lilli.name
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" ist neu hier!").color(NamedTextColor.GREEN))
                    .append(Component.text(".").decorate(TextDecoration.OBFUSCATED)));
            for (Player op : Bukkit.getOnlinePlayers()) {
                if (op.isOp()) {
                    op.playSound(op.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 1.0F);
                    op.sendMessage("§aYour IP address: " + getIP(p) + ":" + getPort(p));
                }
            }
        }
        // String welcomemsg =
        // this.plugin.getConfig().getString(".WELCOMEMSG").replace("&",
        // "§").replace("%Player%", p.getDisplayName());
        (new BukkitRunnable() {
            public void run() {
                p.sendMessage(Component.text("Willkommen, ").color(NamedTextColor.GOLD).append(Component.text(p.getName()).color(NamedTextColor.AQUA).append(Component.text("!")).color(NamedTextColor.GOLD)));
            }
        }).runTaskLater(this.plugin, 20L);
    }

    public static String getIP(Player player) {
        return Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress();
    }

    public static int getPort(Player player) {
        return Lilli.getInstance().getServer().getPort();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(Component.text("<< ").color(NamedTextColor.DARK_GRAY)
                .append(permprefix(player)).append(Component.text(player.getName()).color(NamedTextColor.GRAY))
                .append(Component.text(" hat den Server verlassen!").color(NamedTextColor.RED)));

    }
}