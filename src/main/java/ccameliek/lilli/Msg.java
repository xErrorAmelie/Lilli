package ccameliek.lilli3;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Msg implements CommandExecutor {

	String message = "";

	private static HashMap<UUID, UUID> reply = new HashMap<UUID, UUID>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("msg")) {
			Player player = (Player) sender;
			UUID sendtarget = Bukkit.getPlayer(args[0]).getUniqueId();
			Player target = Bukkit.getPlayer(sendtarget);

			// ----------------------------------------------MSG----------------------------------
			if (sender instanceof Player) {
				if (player.hasPermission("Lilli.msg")) {
					if (args.length >= 2) {
						if (sendtarget != null) {
							final StringBuilder message = new StringBuilder();
							for (int i = 1; i < args.length; i++)
								message.append(args[i]).append(" ");
							if (!AFK.afk.contains(target)) {
								player.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.RED + "Msg" + ChatColor.GRAY + "] " + ChatColor.GREEN + "Du " + ChatColor.GRAY + "-> "
										+ ChatColor.AQUA + target.getName() + ": " + ChatColor.GRAY + message);
							} else {
								player.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.RED + "Msg" + ChatColor.GRAY + "] " + ChatColor.GREEN + "Du " + ChatColor.GRAY + "-> "
										+ ChatColor.AQUA + target.getName() + ": " + ChatColor.GRAY + message);
								player.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.RED + "Msg" + ChatColor.GRAY + "] " + ChatColor.GRAY + "(Dieser Spieler ist AFK)");
							}
							target.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.RED + "Msg" + ChatColor.GRAY + "] " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " -> "
									+ ChatColor.GREEN + "Dir: " + ChatColor.GRAY + message);

							reply.put(player.getUniqueId(), sendtarget);
							reply.put(sendtarget, player.getUniqueId());
							// ---------------------------For OP---------------------------------
							for (Player p1 : Bukkit.getOnlinePlayers()) {
								if (p1.isOp() && p1 != target && p1 != player) {
									p1.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.GRAY + "Msg Console" + ChatColor.GRAY + "] " + player.getName() + " " + "schreibt zu "
											+ ChatColor.GRAY + target.getName() + ": " + message);
								}
							}
							// ------------------------------------------------------------------
							for (String arg : args)
								message.append(arg).append(" ");
						} else {
							player.sendMessage(ChatColor.RED + "Der Spieler " + args[0] + " ist nicht online!");
						}
					} else {
						player.sendMessage(ChatColor.RED + "/msg <Name> <Nachricht>");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Du hast keine Berechtigung dafür!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Dieser Befehl kann nur als Spieler ausgeführt werden!");
			}

		}
		// ------------------------------------------REPLY---------------------------------------
		if (label.equalsIgnoreCase("reply") || label.equalsIgnoreCase("r")) {
			Player player = (Player) sender;
			UUID lastplayer = reply.get(player.getUniqueId());
			Player target = Bukkit.getPlayer(lastplayer);
			if (sender instanceof Player) {
				if (player.hasPermission("Lilli.msg.reply") || player.isOp()) {
					if (args.length >= 1) {
						if (lastplayer != null) {
							final StringBuilder message = new StringBuilder();
							for (int i = 0; i < args.length; i++)
								message.append(args[i]).append(" ");
							if (!AFK.afk.contains(target)) {
								player.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.RED + "Msg" + ChatColor.GRAY + "] " + ChatColor.GREEN + "Du " + ChatColor.GRAY + "-> "
										+ ChatColor.AQUA + target.getName() + ": " + ChatColor.GRAY + message);
							} else {
								player.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.RED + "Msg" + ChatColor.GRAY + "] " + ChatColor.GREEN + "Du " + ChatColor.GRAY + "-> "
										+ ChatColor.AQUA + target.getName() + ": " + ChatColor.GRAY + message);
								player.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.RED + "Msg" + ChatColor.GRAY + "] " + ChatColor.GRAY + "(Dieser Spieler ist AFK)");
							}
							target.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.RED + "Msg" + ChatColor.GRAY + "] " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " -> "
									+ ChatColor.GREEN + "Dir: " + ChatColor.GRAY + message);
							// ---------------------------For OP---------------------------------
							for (Player onlineplayers : Bukkit.getOnlinePlayers()) {
								if (onlineplayers.isOp() && onlineplayers != target && onlineplayers != player) {
									onlineplayers.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.GRAY + "Msg Console" + ChatColor.GRAY + "] " + player.getName() + " "
											+ "schreibt zu " + ChatColor.GRAY + target.getName() + ": " + message);
								}
							}
							// ------------------------------------------------------------------
							for (String arg : args)
								message.append(arg).append(" ");
						} else {
							player.sendMessage(ChatColor.RED + "Du wurdest von niemanden angeschrieben");
						}
					} else {
						player.sendMessage(ChatColor.RED + "/r <Nachricht>");
					}

				} else {
					player.sendMessage(ChatColor.RED + "Du hast keine Berechtigung dafür!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Dieser Befehl kann nur als Spieler ausgeführt werden!");
			}
		}
		return false;
	}
}
