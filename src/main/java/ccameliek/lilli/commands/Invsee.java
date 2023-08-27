package ccameliek.lilli.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Invsee implements CommandExecutor {
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		Player player = (Player) sender;
		String Playername = args[0];
		Player target = Bukkit.getPlayer(Playername);

		if (!(args.length == 1)) {
			player.sendMessage(Component.text("Verwende /invsee [Playername]").color(NamedTextColor.RED));
		} else {
			if (player.hasPermission("Lilli.invsee") || player.isOp()) {
                assert target != null;
                player.openInventory(target.getInventory());

			} else {
				player.sendMessage(Component.text("Du hast keine Berechtigung dafür!").color(NamedTextColor.RED));
			}
		}
		return true;
	}
}