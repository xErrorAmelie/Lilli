package ccameliek.lilli;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Ping implements CommandExecutor {

	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {

		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("ping")) {
			if (args.length == 0) {
				p.sendMessage(Lilli.prefix + "Du hast einen Ping von §c" + p.getPing() + "§7 ms");
			}
		} else {
			p.sendMessage(Lilli.prefix + "Du hast keine §cRechte §7dafür.");
		}

		return false;
	}
}