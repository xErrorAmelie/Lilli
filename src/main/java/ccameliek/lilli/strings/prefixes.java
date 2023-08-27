package ccameliek.lilli.strings;

import ccameliek.lilli.Lilli;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.util.HashMap;

public class prefixes {

    public static HashMap<String, TextComponent> permissionPrefixes = new HashMap<>();
    static {
        // Rang prefixes, in Reihenfolge nach Priorität
        permissionPrefixes.put("lilli.admin", getComponentInBrackets(Component.text("Owner/in").color(NamedTextColor.LIGHT_PURPLE)));
        permissionPrefixes.put("lilli.supporter", getComponentInBrackets(Component.text("Supporter/in").color(NamedTextColor.GREEN)));
        permissionPrefixes.put("lilli.dev", getComponentInBrackets(Component.text("Developer/in").color(NamedTextColor.DARK_GREEN)));
        permissionPrefixes.put("lilli.moderator", getComponentInBrackets(Component.text("Moderator/in").color(NamedTextColor.DARK_GREEN)));
        permissionPrefixes.put("lilli.stammspieler", getComponentInBrackets(Component.text("Stammspieler/in").color(NamedTextColor.GOLD)));
        permissionPrefixes.put("lilli.spielerplus", getComponentInBrackets(Component.text("Spieler/in+").color(NamedTextColor.GOLD)));
        permissionPrefixes.put("lilli.elite", getComponentInBrackets(Component.text("Elite").color(NamedTextColor.DARK_AQUA)));
        permissionPrefixes.put("lilli.neko", getComponentInBrackets(Component.text("Neko").color(NamedTextColor.DARK_PURPLE)));
        permissionPrefixes.put("lilli.iwie", getComponentInBrackets(Component.text("dfdsfs").color(NamedTextColor.GREEN)));
    }


    public static TextComponent prefix = getComponentInBrackets(Component.text("Lilli").color(NamedTextColor.RED));
    public static TextComponent adminprefix = getComponentInBrackets(Component.text("AdminLilli").color(NamedTextColor.RED));
    public static TextComponent kowaiprefix = getComponentInBrackets(Component.text("Kowaineko").color(NamedTextColor.DARK_AQUA));
    public static TextComponent defaultNamePrefix = getComponentInBrackets(Component.text("Spieler/in").color(NamedTextColor.GREEN));
    public static TextComponent name = Component.text(NamedTextColor.AQUA + "");

    public static TextComponent getComponentInBrackets(TextComponent textComponent) {
        return Component.text("[").color(NamedTextColor.GRAY)
                .append(textComponent)
                .append(Component.text("] ").color(NamedTextColor.GRAY));
    }
}
