package ccameliek.lilli;

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
                             p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du bist jetzt im GameMode 0");
                             return true;
                         } else if (args[0].equalsIgnoreCase("1")) {
                             p.setGameMode(GameMode.CREATIVE);
                             p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du bist jetzt im GameMode 1");
                             return true;
                         } else if (args[0].equalsIgnoreCase("2")) {
                             p.setGameMode(GameMode.ADVENTURE);
                             p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du bist jetzt im GameMode 2");
                             return true;
                         } else if (args[0].equalsIgnoreCase("3")) {
                             p.setGameMode(GameMode.SPECTATOR);
                             p.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du bist jetzt im GameMode 3");
                             return true;
                         }
                     } else {
                         sender.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "/gm [0 bis 3]");
                         return true;
                     }
                 } else {
                     sender.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "Du hast nicht die Berechtigung dies zu tun!");
                     return true;
                 }
             } else {
                 sender.sendMessage(NamedTextColor.GRAY + "[" + NamedTextColor.RED + "Lilli" + NamedTextColor.GRAY + "] " + "/gm [0 bis 3]");
                 return true;
             }
             return false;
		     }
	     }