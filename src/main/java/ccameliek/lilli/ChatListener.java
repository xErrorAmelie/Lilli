package ccameliek.lilli3;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;

public class ChatListener implements Listener {
	Scoreboard board;

	public ChatListener(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();
		this.plugin = plugin;
	}

	ConsoleCommandSender console;
	private Lilli plugin;

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String Msg = e.getMessage();
		if (Msg.contains("%")) {
			Msg.replace("%", "Prozent");
		}
		if (p.hasPermission("Lilli.admin")) {
			e.setFormat(String.valueOf(Lilli.admin) + ChatColor.AQUA + p.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + Msg);
		} else if (p.hasPermission("Lilli.supporter")) {
			e.setFormat(String.valueOf(Lilli.supporter) + ChatColor.AQUA + p.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + Msg);
		} else if (p.hasPermission("Lilli.mod")) {
			e.setFormat(String.valueOf(Lilli.moderator) + ChatColor.AQUA + p.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + Msg);
		} else if (p.hasPermission("Lilli.scientist")) {
			e.setFormat(String.valueOf(Lilli.developer) + ChatColor.AQUA + p.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + Msg);
		} else if (p.hasPermission("Lilli.stammspieler")) {
			e.setFormat(String.valueOf(Lilli.stammspieler) + ChatColor.AQUA + p.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + Msg);
		} else if (p.hasPermission("Lilli.stammspieler")) {

			e.setFormat(String.valueOf(Lilli.spielerplus) + ChatColor.AQUA + p.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + Msg);
		} else if (p.hasPermission("Lilli.stammspieler")) {
			e.setFormat(String.valueOf(Lilli.elite) + ChatColor.AQUA + p.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + Msg);
		} else if (p.hasPermission("Lilli.stammspieler")) {
			e.setFormat(String.valueOf(Lilli.neko) + ChatColor.AQUA + p.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + Msg);

		} else if (p.hasPermission("Lilli.iwie")) {
			e.setFormat(String.valueOf(Lilli.iwie) + ChatColor.AQUA + p.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + Msg);
		} else {
			e.setFormat(String.valueOf(Lilli.player) + ChatColor.AQUA + p.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + Msg);
		}
		File mute = new File("plugins//Lilli//Mute", p.getUniqueId() + ".yml");
		YamlConfiguration mutecfg = YamlConfiguration.loadConfiguration(mute);
		if (mutecfg.getBoolean(".Mute") == true) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + Lilli.kowaiprefix + "Du bist gemutet");
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		if (p.hasPermission("Lilli.admin")) {
			e.setJoinMessage(ChatColor.DARK_GRAY + ">> " + Lilli.admin + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat den Server betreten!");
			p.setPlayerListName(String.valueOf(Lilli.admin) + ChatColor.AQUA + p.getName());
			p.setDisplayName(String.valueOf(Lilli.admin) + ChatColor.AQUA + p.getName());
		} else if (p.hasPermission("Lilli.supporter")) {
			e.setJoinMessage(ChatColor.DARK_GRAY + ">> " + Lilli.supporter + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat den Server betreten!");
			p.setPlayerListName(String.valueOf(Lilli.supporter) + ChatColor.AQUA + p.getName());
			p.setDisplayName(String.valueOf(Lilli.supporter) + ChatColor.AQUA + p.getName());
		} else if (p.hasPermission("Lilli.mod")) {
			e.setJoinMessage(ChatColor.DARK_GRAY + ">> " + Lilli.moderator + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat den Server betreten!");
			p.setPlayerListName(String.valueOf(Lilli.moderator) + ChatColor.AQUA + p.getName());
			p.setDisplayName(String.valueOf(Lilli.moderator) + ChatColor.AQUA + p.getName());
		} else if (p.hasPermission("Lilli.scientist")) {
			e.setJoinMessage(ChatColor.DARK_GRAY + ">> " + Lilli.developer + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat den Server betreten!");
			p.setPlayerListName(String.valueOf(Lilli.developer) + ChatColor.AQUA + p.getName());
			p.setDisplayName(String.valueOf(Lilli.developer) + ChatColor.AQUA + p.getName());
		} else if (p.hasPermission("Lilli.stammspieler")) {
			e.setJoinMessage(ChatColor.DARK_GRAY + ">> " + Lilli.stammspieler + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat den Server betreten!");
			p.setPlayerListName(String.valueOf(Lilli.stammspieler) + ChatColor.AQUA + p.getName());
			p.setDisplayName(String.valueOf(Lilli.stammspieler) + ChatColor.AQUA + p.getName());
		} else if (p.hasPermission("Lilli.spielerplus")) {
			e.setJoinMessage(ChatColor.DARK_GRAY + ">> " + Lilli.spielerplus + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat den Server betreten!");
			p.setPlayerListName(String.valueOf(Lilli.spielerplus) + ChatColor.AQUA + p.getName());
			p.setDisplayName(String.valueOf(Lilli.spielerplus) + ChatColor.AQUA + p.getName());
		} else if (p.hasPermission("Lilli.elite")) {
			e.setJoinMessage(ChatColor.DARK_GRAY + ">> " + Lilli.elite + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat den Server betreten!");
			p.setPlayerListName(String.valueOf(Lilli.elite) + ChatColor.AQUA + p.getName());
			p.setDisplayName(String.valueOf(Lilli.elite) + ChatColor.AQUA + p.getName());
		} else if (p.hasPermission("Lilli.neko")) {
			e.setJoinMessage(ChatColor.DARK_GRAY + ">> " + Lilli.neko + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat den Server betreten!");
			p.setPlayerListName(String.valueOf(Lilli.neko) + ChatColor.AQUA + p.getName());
			p.setDisplayName(String.valueOf(Lilli.neko) + ChatColor.AQUA + p.getName());
		} else if (p.hasPermission("Lilli.iwie")) {
			e.setJoinMessage(ChatColor.DARK_GRAY + ">> " + Lilli.iwie + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat den Server betreten!");
			p.setPlayerListName(String.valueOf(Lilli.iwie) + ChatColor.AQUA + p.getName());
			p.setDisplayName(String.valueOf(Lilli.iwie) + ChatColor.AQUA + p.getName());
		} else {
			e.setJoinMessage(ChatColor.DARK_GRAY + ">> " + Lilli.player + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat den Server betreten!");
			p.setPlayerListName(String.valueOf(Lilli.player) + ChatColor.AQUA + p.getName());
			p.setDisplayName(String.valueOf(Lilli.player) + ChatColor.AQUA + p.getName());
		}
		if (!p.hasPlayedBefore()) {
			e.setJoinMessage(String.valueOf(Lilli.name) + p.getName() + ChatColor.GREEN + " ist neu hier!" + ChatColor.MAGIC + ".");
			for (Player op : Bukkit.getOnlinePlayers()) {
				if (op.isOp()) {
					op.playSound(op.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 1.0F);
					op.sendMessage("§aYour IP address: " + getIP(p) + ":" + getPort(p));
				}
			}
		}
		// String welcomemsg =
		// this.plugin.getConfig().getString(".WELCOMEMSG").replace("&",
		// "§").replace("%Player%", p.getDisplayName());
		(new BukkitRunnable() {
			public void run() {
				p.sendMessage(ChatColor.GOLD + "Willkommen, " + ChatColor.AQUA + p.getDisplayName() + ChatColor.GOLD + "!");
			}
		}).runTaskLater(this.plugin, 20L);
	}

	public static String getIP(Player player) {
		String playerIP = player.getAddress().getAddress().getHostAddress();
		return playerIP;
	}

	public static int getPort(Player player) {
		int playerPort = Lilli.getInstance().getServer().getPort();
		return playerPort;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (p.hasPermission("Lilli.admin")) {
			e.setQuitMessage(ChatColor.DARK_GRAY + "<< " + Lilli.admin + p.getName() + ChatColor.RED + " hat den Server verlassen!");
		} else if (p.hasPermission("Lilli.supporter")) {
			e.setQuitMessage(ChatColor.DARK_GRAY + "<< " + Lilli.supporter + p.getName() + ChatColor.RED + " hat den Server verlassen!");
		} else if (p.hasPermission("Lilli.mod")) {
			e.setQuitMessage(ChatColor.DARK_GRAY + "<< " + Lilli.moderator + p.getName() + ChatColor.RED + " hat den Server verlassen!");
		} else if (p.hasPermission("Lilli.stammspieler")) {
			e.setQuitMessage(ChatColor.DARK_GRAY + "<< " + Lilli.stammspieler + p.getName() + ChatColor.RED + " hat den Server verlassen!");
		} else if (p.hasPermission("Lilli.spielerplus")) {
			e.setQuitMessage(ChatColor.DARK_GRAY + "<< " + Lilli.spielerplus + p.getName() + ChatColor.RED + " hat den Server verlassen!");
		} else if (p.hasPermission("Lilli.elite")) {
			e.setQuitMessage(ChatColor.DARK_GRAY + "<< " + Lilli.elite + p.getName() + ChatColor.RED + " hat den Server verlassen!");
		} else if (p.hasPermission("Lilli.neko")) {
			e.setQuitMessage(ChatColor.DARK_GRAY + "<< " + Lilli.neko + p.getName() + ChatColor.RED + " hat den Server verlassen!");
		} else if (p.hasPermission("Lilli.scientist")) {
			e.setQuitMessage(ChatColor.DARK_GRAY + "<< " + Lilli.developer + p.getName() + ChatColor.RED + " hat den Server verlassen!");
		} else if (p.hasPermission("Lilli.iwie")) {
			e.setQuitMessage(ChatColor.DARK_GRAY + "<< " + Lilli.iwie + p.getName() + ChatColor.RED + " hat den Server verlassen!");
		} else {
			e.setQuitMessage(ChatColor.DARK_GRAY + "<< " + Lilli.player + ChatColor.AQUA + p.getName() + ChatColor.RED + " hat den Server verlassen!");
		}
	}

}