package ccameliek.lilli;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeS implements CommandExecutor
     {
	     public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args)
	     {
		   Player p = (Player) sender;

             if (label.equalsIgnoreCase("gm")) {
                 if ((p.hasPermission("Lilli.gm")) || (p.isOp())) {
                     if (args.length == 1) {
                         if (args[0].equalsIgnoreCase("0")) {
                             p.setGameMode(GameMode.SURVIVAL);
                             p.sendMessage(Lilli.adminprefix.append(Component.text("Du bist jetzt im GameMode 0").color(NamedTextColor.GRAY)));
                             return true;
                         } else if (args[0].equalsIgnoreCase("1")) {
                             p.setGameMode(GameMode.CREATIVE);
                             p.sendMessage(Lilli.adminprefix.append(Component.text("Du bist jetzt im GameMode 1").color(NamedTextColor.GRAY)));;
                             return true;
                         } else if (args[0].equalsIgnoreCase("2")) {
                             p.setGameMode(GameMode.ADVENTURE);
                             p.sendMessage(Lilli.adminprefix.append(Component.text("Du bist jetzt im GameMode 2").color(NamedTextColor.GRAY)));
                             return true;
                         } else if (args[0].equalsIgnoreCase("3")) {
                             p.setGameMode(GameMode.SPECTATOR);
                             p.sendMessage(Lilli.adminprefix.append(Component.text("Du bist jetzt im GameMode 3").color(NamedTextColor.GRAY)));
                             return true;
                         }
                     } else {
                         sender.sendMessage(Lilli.adminprefix.append(Component.text("/gm [0 bis 3]").color(NamedTextColor.GRAY)));
                         return true;
                     }
                 } else {
                     sender.sendMessage(Lilli.adminprefix.append(Component.text("Du hast nicht die Berechtigung dies zu tun!").color(NamedTextColor.GRAY)));
                     return true;
                 }
             } else {
                 sender.sendMessage(Lilli.adminprefix.append(Component.text("/gm [0 bis 3]").color(NamedTextColor.GRAY)));
                 return true;
             }
             return false;
		     }
	     }