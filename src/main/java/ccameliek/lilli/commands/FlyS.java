package ccameliek.lilli.commands;

import ccameliek.lilli.strings.prefixes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyS implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("fly")) {
            if (!((sender.hasPermission("Lilli.flyspeed")) || (sender.isOp()))) {
                sender.sendMessage(prefixes.adminprefix.append(Component.text("Du hast nicht die Berechtigung dies zu tun!").color(NamedTextColor.RED)));
            } else {
                if (args.length == 1) {
                    float flyargs = Float.parseFloat(args[0]) * 0.20F;
                    player.setFlySpeed(flyargs);
                    player.sendMessage(prefixes.adminprefix.append(Component.text("Du hast deine Fluggeschwindigkeit auf " + args[0] + " gesetzt!").color(NamedTextColor.GREEN)));
                } else {
                    sender.sendMessage(prefixes.adminprefix.append(Component.text("Verwende /fly [Zahl]").color(NamedTextColor.RED)));
                }
            }
        }
        return false;
    }
}
