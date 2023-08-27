package ccameliek.lilli.events;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Objects;

import static ccameliek.lilli.Lilli.getInstance;

public class ServerListener implements Listener {

    @EventHandler
    public void ServerPing(ServerListPingEvent event) {
        event.setMaxPlayers(getInstance().getConfig().getInt(".Slots"));
        Component motd = Component.text(Objects.requireNonNull(Objects.requireNonNull(getInstance().getConfig().getString(".Motd")).replaceAll("&", "§")));
        event.motd(motd);
    }
}
