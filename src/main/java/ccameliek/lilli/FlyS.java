package ccameliek.lilli;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyS implements CommandExecutor {
	// macht überall [HOP]
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		Player p = (Player) sender;
		float flyspeed = p.getFlySpeed();

        if (command.getName().equalsIgnoreCase("fly")) {
            if ((sender.hasPermission("Lilli.flyspeed")) || (sender.isOp())) {
                if (args.length == 1) {
                    if (args.length == 0) {
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast die Fluggeschwindigkeit: " + flyspeed);
                    }

                    if (args[0].equalsIgnoreCase("0")) {
                        p.setFlySpeed(0.0F);
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast deine Fluggeschwindigkeit auf 0 gesetzt!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("0.05")) {
                        p.setFlySpeed(0.004F);
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast deine Fluggeschwindigkeit auf 0.05 gesetzt!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("0.1")) {
                        p.setFlySpeed(0.02F);
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast deine Fluggeschwindigkeit auf 0.1 gesetzt!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("0.3")) {
                        p.setFlySpeed(0.06F);
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast deine Fluggeschwindigkeit auf 0.3 gesetzt!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("0.5")) {
                        p.setFlySpeed(0.1F);
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast deine Fluggeschwindigkeit auf 0.5 gesetzt!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("1")) {
                        p.setFlySpeed(0.2F);
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast deine Fluggeschwindigkeit auf 1 gesetzt!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("2")) {
                        p.setFlySpeed(0.4F);
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast deine Fluggeschwindigkeit auf 2 gesetzt!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("3")) {
                        p.setFlySpeed(0.6F);
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast deine Fluggeschwindigkeit auf 3 gesetzt!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("4")) {
                        p.setFlySpeed(0.8F);
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast deine Fluggeschwindigkeit auf 4 gesetzt!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("5")) {
                        p.setFlySpeed(1F);
                        p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast deine Fluggeschwindigkeit auf 5 gesetzt!");
                        return true;
                    }
                } else {
                    sender.sendMessage(NamedTextColor.RED + "/fly [0 bis 5]");
                    return true;
                }
            } else {
                sender.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast nicht die Berechtigung dies zu tun!");
                return true;
            }
        }
        return false;
	}
}
