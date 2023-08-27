package ccameliek.lilli.commands;

import ccameliek.lilli.Lilli;
import ccameliek.lilli.strings.prefixes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class Spawn implements CommandExecutor {

    public Spawn(Lilli plugin) {
        this.console = Bukkit.getServer().getConsoleSender();

    }

    ConsoleCommandSender console;


    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!(sender instanceof Player p)) {
            System.out.println("???");
            return true;
        }

        if (!p.hasPermission("Lilli.setspawn")) {
            p.sendMessage("Du hast keine Permission");
            return true;
        }

        File ordner = new File("plugins//Lilli");
        File file = new File("plugins//Lilli//spawn.yml");

        if (!ordner.exists()) {
            ordner.mkdir();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                p.sendMessage(Component.text("Die Datei konnte auf dem Pfad nicht erstellt werden").color(NamedTextColor.RED));
            }
        }

        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        Location loc = p.getLocation();

        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        double yaw = loc.getYaw();
        double pitch = loc.getPitch();
        String worldname = loc.getWorld().getName();

        cfg.set("X", x);
        cfg.set("Y", y);
        cfg.set("Z", z);
        cfg.set("Yaw", yaw);
        cfg.set("Pitch", pitch);
        cfg.set("Worldname", worldname);

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.sendMessage(prefixes.adminprefix.append(Component.text("Du hast den globalen Spawn gesetzt").color(NamedTextColor.GREEN)));

        return true;
    }
}
