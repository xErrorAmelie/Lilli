package ccameliek.lilli3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Invsee implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		String Playername = args[0];
		Player target = Bukkit.getPlayer(Playername);

		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "/invsee [Playername]");
		} else if (args.length == 1) {
			if (p.hasPermission("Lilli.invsee") || p.isOp()) {
				p.openInventory(target.getInventory());

			} else {
				p.sendMessage(ChatColor.RED + "Du hast keine Berechtigung dafür!");
			}
		}
		return true;
	}
}