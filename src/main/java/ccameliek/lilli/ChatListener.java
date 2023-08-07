package ccameliek.lilli;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import javax.naming.Name;
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

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player p = e.getPlayer();
        Component Msg = e.message();
        if (p.hasPermission("Lilli.admin")) {
            e.message(Lilli.admin
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                            .append(Component.text(": ").color(NamedTextColor.GRAY))
                                    .append(Msg.color(NamedTextColor.WHITE)));
        } else if (p.hasPermission("Lilli.supporter")) {
            e.message(Lilli.supporter
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Msg.color(NamedTextColor.WHITE)));
        } else if (p.hasPermission("Lilli.mod")) {
            e.message(Lilli.moderator
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Component.text(Msg.toString()).color(NamedTextColor.WHITE)));
        } else if (p.hasPermission("Lilli.dev")) {
            e.message(Lilli.developer
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Component.text(Msg.toString()).color(NamedTextColor.WHITE)));
        } else if (p.hasPermission("Lilli.stammspieler")) {
            e.message(Lilli.stammspieler
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Component.text(Msg.toString()).color(NamedTextColor.WHITE)));
        } else if (p.hasPermission("Lilli.stammspieler")) {
            e.message(Lilli.spielerplus
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Component.text(Msg.toString()).color(NamedTextColor.WHITE)));
        } else if (p.hasPermission("Lilli.stammspieler")) {
            e.message(Lilli.stammspieler
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Component.text(Msg.toString()).color(NamedTextColor.WHITE)));
        } else if (p.hasPermission("Lilli.stammspieler")) {
            e.message(Lilli.neko
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Component.text(Msg.toString()).color(NamedTextColor.WHITE)));

        } else if (p.hasPermission("Lilli.iwie")) {
            e.message(Lilli.iwie
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Component.text(Msg.toString()).color(NamedTextColor.WHITE)));
        } else {
            e.message(Lilli.spieler
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(Component.text(Msg.toString()).color(NamedTextColor.WHITE)));
        }
        File mute = new File("plugins//Lilli//Mute", p.getUniqueId() + ".yml");
        YamlConfiguration mutecfg = YamlConfiguration.loadConfiguration(mute);
        if (mutecfg.getBoolean(".Mute")) {
            e.setCancelled(true);
            p.sendMessage(NamedTextColor.RED + Lilli.kowaiprefix.toString() + "Du bist gemutet");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (p.hasPermission("Lilli.admin")) {
            e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.admin)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
            p.playerListName(Lilli.admin
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
            p.displayName(Lilli.admin
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        } else if (p.hasPermission("Lilli.supporter")) {
            e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.supporter)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
            p.playerListName(Lilli.supporter
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
            p.displayName(Lilli.supporter
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        } else if (p.hasPermission("Lilli.mod")) {
            e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.moderator)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
            p.playerListName(Lilli.moderator
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
            p.displayName(Lilli.moderator
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        } else if (p.hasPermission("Lilli.dev")) {
            e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.developer)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
            p.playerListName(Lilli.developer
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
            p.displayName(Lilli.developer
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        } else if (p.hasPermission("Lilli.stammspieler")) {
            e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.stammspieler)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
            p.playerListName(Lilli.stammspieler
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
            p.displayName(Lilli.stammspieler
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        } else if (p.hasPermission("Lilli.spielerplus")) {
            e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.spielerplus)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
            p.playerListName(Lilli.spielerplus
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
            p.displayName(Lilli.spielerplus
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        } else if (p.hasPermission("Lilli.elite")) {
            e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.elite)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
            p.playerListName(Lilli.elite
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
            p.displayName(Lilli.elite
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        } else if (p.hasPermission("Lilli.neko")) {
            e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.neko)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
            p.playerListName(Lilli.neko
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
            p.displayName(Lilli.neko
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        } else if (p.hasPermission("Lilli.iwie")) {
            e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.iwie)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
            p.playerListName(Lilli.iwie
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
            p.displayName(Lilli.iwie
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        } else {
            e.joinMessage(Component.text(">> ").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.spieler)
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA))
                    .append(Component.text(" hat den Server betreten!").color(NamedTextColor.GREEN)));
            p.playerListName(Lilli.spieler
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
            p.displayName(Lilli.spieler
                    .append(Component.text(p.getName()).color(NamedTextColor.AQUA)));
        }
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

        if (player.hasPermission("Lilli.admin")) {
            event.quitMessage(Component.text("<<").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.admin).append(Component.text(player.getName())).color(NamedTextColor.AQUA)
                    .append(Component.text(" hat den Server verlassen!")).color(NamedTextColor.RED));
        } else if (player.hasPermission("Lilli.supporter")) {
            event.quitMessage(Component.text("<<").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.supporter).append(Component.text(player.getName())).color(NamedTextColor.AQUA)
                    .append(Component.text(" hat den Server verlassen!")).color(NamedTextColor.RED));
        } else if (player.hasPermission("Lilli.mod")) {
            event.quitMessage(Component.text("<<").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.moderator).append(Component.text(player.getName())).color(NamedTextColor.AQUA)
                            .append(Component.text(" hat den Server verlassen!")).color(NamedTextColor.RED));
        } else if (player.hasPermission("Lilli.stammspieler")) {
            event.quitMessage(Component.text("<<").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.stammspieler).append(Component.text(player.getName())).color(NamedTextColor.AQUA)
                    .append(Component.text(" hat den Server verlassen!")).color(NamedTextColor.RED));
        } else if (player.hasPermission("Lilli.spielerplus")) {
            event.quitMessage(Component.text("<<").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.spielerplus).append(Component.text(player.getName())).color(NamedTextColor.AQUA)
                    .append(Component.text(" hat den Server verlassen!")).color(NamedTextColor.RED));
        } else if (player.hasPermission("Lilli.elite")) {
            event.quitMessage(Component.text("<<").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.elite).append(Component.text(player.getName())).color(NamedTextColor.AQUA)
                    .append(Component.text(" hat den Server verlassen!")).color(NamedTextColor.RED));
        } else if (player.hasPermission("Lilli.neko")) {
            event.quitMessage(Component.text("<<").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.neko).append(Component.text(player.getName())).color(NamedTextColor.AQUA)
                    .append(Component.text(" hat den Server verlassen!")).color(NamedTextColor.RED));
        } else if (player.hasPermission("Lilli.dev")) {
            event.quitMessage(Component.text("<<").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.developer).append(Component.text(player.getName())).color(NamedTextColor.AQUA)
                    .append(Component.text(" hat den Server verlassen!")).color(NamedTextColor.RED));
        } else if (player.hasPermission("Lilli.iwie")) {
            event.quitMessage(Component.text("<<").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.iwie).append(Component.text(player.getName())).color(NamedTextColor.AQUA)
                    .append(Component.text(" hat den Server verlassen!")).color(NamedTextColor.RED));
        } else {
            event.quitMessage(Component.text("<<").color(NamedTextColor.DARK_GRAY)
                    .append(Lilli.spieler).append(Component.text(player.getName())).color(NamedTextColor.AQUA)
                    .append(Component.text(" hat den Server verlassen!")).color(NamedTextColor.RED));
        }
    }

}