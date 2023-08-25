package ccameliek.lilli;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class AFK implements Listener {

	public static ArrayList<Player> afk = new ArrayList<Player>();

	@EventHandler
	public void onAFK(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (afk.contains(p)) {
			afk.remove(p);
			Bukkit.broadcast(Component.text("*"+p.getName()+" ist jetzt nicht mehr AFK").color(NamedTextColor.GRAY));
		}
	}
}
