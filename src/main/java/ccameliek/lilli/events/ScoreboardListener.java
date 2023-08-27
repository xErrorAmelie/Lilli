package ccameliek.lilli.events;

import ccameliek.lilli.Lilli;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardListener implements Listener {
    public ScoreboardListener(Lilli plugin) {
        this.console = Bukkit.getServer().getConsoleSender();

        this.plugin = plugin;
    }

    ConsoleCommandSender console;
    private final Lilli plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.plugin.sidebar.addPlayer(event.getPlayer());
        Bukkit.broadcast(Component.text("Test(!): " + this.plugin.sidebar.players()));
        (new BukkitRunnable() {
            public void run() {
                event.getPlayer().getWorld().playEffect(event.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 0);
                event.getPlayer().getWorld().playEffect(event.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 0);
                event.getPlayer().getWorld().playEffect(event.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 0);
            }
        }).runTaskLater(this.plugin, 20L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.plugin.sidebar.removePlayer(event.getPlayer());
    }
}
