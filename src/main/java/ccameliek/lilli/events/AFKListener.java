package ccameliek.lilli.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class AFKListener implements Listener {

	public static ArrayList<Player> afk = new ArrayList<>();

	@EventHandler
	public void onAFK(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (afk.contains(p)) {
			afk.remove(p);
			Bukkit.broadcast(Component.text("*"+p.getName()+" ist jetzt nicht mehr AFK").color(NamedTextColor.GRAY));
		}
	}
}
