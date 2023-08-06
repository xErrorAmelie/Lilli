package ccameliek.lilli3;

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

public class AFK implements CommandExecutor, Listener {

	public static ArrayList<Player> afk = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("afk")) {
			if (afk.contains(p)) {
				afk.remove(p);
				Bukkit.broadcastMessage(ChatColor.GRAY + "*" + p.getName() + " ist jetzt nicht mehr AFK");
			} else {
				afk.add(p);
				Bukkit.broadcastMessage(ChatColor.GRAY + "*" + p.getName() + " ist jetzt AFK");
			}
		}
		return false;
	}

	@EventHandler
	public void onAFK(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (afk.contains(p)) {
			afk.remove(p);
			Bukkit.broadcastMessage(ChatColor.GRAY + "*" + p.getName() + " ist jetzt nicht mehr AFK");
		}
	}
}
