package ccameliek.lilli.commands;

import ccameliek.lilli.strings.prefixes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeS implements CommandExecutor {
    private GameMode getGameModeFromString(String gamemode) {
        switch (gamemode) {
            case "0" -> {
                return GameMode.SURVIVAL;
            }
            case "1" -> {
                return GameMode.CREATIVE;
            }
            case "2" -> {
                return GameMode.ADVENTURE;
            }
            case "3" -> {
                return GameMode.SPECTATOR;
            }
            default -> {
                return null;
            }
        }
    }
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        Player p = (Player) sender;

        if (label.equalsIgnoreCase("gm")) {
            if (!((p.hasPermission("Lilli.gm")) || (p.isOp()))) {
                sender.sendMessage(prefixes.adminprefix.append(Component.text("Du hast nicht die Berechtigung dies zu tun!").color(NamedTextColor.RED)));
                return true;
            } else if (args.length == 1) {
                GameMode gameMode = getGameModeFromString(args[0]);
                if (gameMode != null) {
                    p.setGameMode(gameMode);
                    p.sendMessage(prefixes.adminprefix.append(Component.text("Du bist jetzt im GameMode " + args[0]).color(NamedTextColor.GRAY)));
                    return true;
                }
            }
            sender.sendMessage(prefixes.adminprefix.append(Component.text("Verwende /gm [0 bis 3]").color(NamedTextColor.RED)));
        }
        return false;
    }
}