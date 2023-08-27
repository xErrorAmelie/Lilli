package ccameliek.lilli.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;

public class back implements Listener {

    public static final Map<String, Location> backLocation = new HashMap<String, Location>(10);

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        this.backLocation.put(p.getName(), p.getLocation());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        this.backLocation.put(event.getPlayer().getName(), event.getPlayer().getLocation());
    }
}
