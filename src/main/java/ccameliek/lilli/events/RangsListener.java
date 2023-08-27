package ccameliek.lilli.events;

import ccameliek.lilli.Rangs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RangsListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        new Rangs().removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new Rangs().addPlayer(event.getPlayer());
    }
}
