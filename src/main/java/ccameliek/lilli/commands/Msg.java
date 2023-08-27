package ccameliek.lilli.commands;

import ccameliek.lilli.events.AFKListener;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Msg implements CommandExecutor {

	String message = "";

	private static HashMap<UUID, UUID> reply = new HashMap<UUID, UUID>();

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("msg")) {
			Player player = (Player) sender;
			UUID sendtarget = Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId();
			Player target = Bukkit.getPlayer(sendtarget);

			// ----------------------------------------------MSG----------------------------------
            if (player.hasPermission("Lilli.msg")) {
                if (args.length >= 2) {
                    final StringBuilder message = new StringBuilder();
                    for (int i = 1; i < args.length; i++)
                        message.append(args[i]).append(" ");
                    if (!AFKListener.afk.contains(target)) {
                        player.sendMessage(TextDecoration.BOLD + "" + NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Msg" + NamedTextColor.GRAY + "] " + NamedTextColor.GREEN + "Du " + NamedTextColor.GRAY + "-> "
                                + NamedTextColor.AQUA + target.getName() + ": " + NamedTextColor.GRAY + message);
                    } else {
                        player.sendMessage(TextDecoration.BOLD + "" + NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Msg" + NamedTextColor.GRAY + "] " + NamedTextColor.GREEN + "Du " + NamedTextColor.GRAY + "-> "
                                + NamedTextColor.AQUA + target.getName() + ": " + NamedTextColor.GRAY + message);
                        player.sendMessage(TextDecoration.BOLD + "" + NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Msg" + NamedTextColor.GRAY + "] " + NamedTextColor.GRAY + "(Dieser Spieler ist AFK)");
                    }
                    target.sendMessage(TextDecoration.BOLD + "" + NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Msg" + NamedTextColor.GRAY + "] " + NamedTextColor.AQUA + player.getName() + NamedTextColor.GRAY + " -> "
                            + NamedTextColor.GREEN + "Dir: " + NamedTextColor.GRAY + message);

                    reply.put(player.getUniqueId(), sendtarget);
                    reply.put(sendtarget, player.getUniqueId());
// ---------------------------For OP---------------------------------
                    for (Player p1 : Bukkit.getOnlinePlayers()) {
                        if (p1.isOp() && p1 != target && p1 != player) {
                            p1.sendMessage(TextDecoration.BOLD + "" + NamedTextColor.GRAY + "[" + NamedTextColor.GRAY + "Msg Console" + NamedTextColor.GRAY + "] " + player.getName() + " " + "schreibt zu "
                                    + NamedTextColor.GRAY + target.getName() + ": " + message);
                        }
                    }
// ------------------------------------------------------------------
                    for (String arg : args)
                        message.append(arg).append(" ");
                } else {
                    player.sendMessage(NamedTextColor.RED + "/msg <Name> <Nachricht>");
                }
            } else {
                player.sendMessage(NamedTextColor.RED + "Du hast keine Berechtigung dafür!");
            }

        }
		// ------------------------------------------REPLY---------------------------------------
		if (label.equalsIgnoreCase("reply") || label.equalsIgnoreCase("r")) {
			Player player = (Player) sender;
			UUID lastplayer = reply.get(player.getUniqueId());
			Player target = Bukkit.getPlayer(lastplayer);
            if (player.hasPermission("Lilli.msg.reply") || player.isOp()) {
                if (args.length >= 1) {
                    final StringBuilder message = new StringBuilder();
                    for (String s : args) message.append(s).append(" ");
                    if (!AFKListener.afk.contains(target)) {
                        assert target != null;
                        player.sendMessage(TextDecoration.BOLD + "" + NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Msg" + NamedTextColor.GRAY + "] " + NamedTextColor.GREEN + "Du " + NamedTextColor.GRAY + "-> "
                                + NamedTextColor.AQUA + target.getName() + ": " + NamedTextColor.GRAY + message);
                    } else {
                        player.sendMessage(TextDecoration.BOLD + "" + NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Msg" + NamedTextColor.GRAY + "] " + NamedTextColor.GREEN + "Du " + NamedTextColor.GRAY + "-> "
                                + NamedTextColor.AQUA + target.getName() + ": " + NamedTextColor.GRAY + message);
                        player.sendMessage(TextDecoration.BOLD + "" + NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Msg" + NamedTextColor.GRAY + "] " + NamedTextColor.GRAY + "(Dieser Spieler ist AFK)");
                    }
                    target.sendMessage(TextDecoration.BOLD + "" + NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Msg" + NamedTextColor.GRAY + "] " + NamedTextColor.AQUA + player.getName() + NamedTextColor.GRAY + " -> "
                            + NamedTextColor.GREEN + "Dir: " + NamedTextColor.GRAY + message);
// ---------------------------For OP---------------------------------
                    for (Player onlineplayers : Bukkit.getOnlinePlayers()) {
                        if (onlineplayers.isOp() && onlineplayers != target && onlineplayers != player) {
                            onlineplayers.sendMessage(TextDecoration.BOLD + "" + NamedTextColor.GRAY + "[" + NamedTextColor.GRAY + "Msg Console" + NamedTextColor.GRAY + "] " + player.getName() + " "
                                    + "schreibt zu " + NamedTextColor.GRAY + target.getName() + ": " + message);
                        }
                    }
// ------------------------------------------------------------------
                    for (String arg : args)
                        message.append(arg).append(" ");
                } else {
                    player.sendMessage(NamedTextColor.RED + "/r <Nachricht>");
                }

            } else {
                player.sendMessage(NamedTextColor.RED + "Du hast keine Berechtigung dafür!");
            }
        }
		return false;
	}
}
