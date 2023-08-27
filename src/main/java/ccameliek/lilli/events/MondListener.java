package ccameliek.lilli.events;

import ccameliek.lilli.Lilli;
import ccameliek.lilli.strings.prefixes;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class MondListener implements Listener {

	public MondListener(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();

		this.plugin = plugin;
	}

	ConsoleCommandSender console;
	private final Lilli plugin;
	int Helmet = 0;
	private static final Map<String, Boolean> message = new HashMap<>();

	@EventHandler
	public void onMoonPort(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.getWorld().getName().contains("Mond")) {
			message.put("Helmetmessage", false);
			message.put("NoHelmetmessage", false);

			(new BukkitRunnable() {
				public void run() {
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 3));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100000, 0));
					for (Player p1 : Bukkit.getOnlinePlayers()) {
						if (p1.isOp()) {
							p1.sendMessage(prefixes.prefix + e.getPlayer().getDisplayName() + ChatColor.GRAY + " hat den Mond betreten!");
						}
					}
					Helmet = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

                        if (p.isDead()) {
                            Bukkit.getServer().getScheduler().cancelTask(Helmet);
                        }
                        if (!p.getWorld().getName().contains("Mond")) {
                            Bukkit.getServer().getScheduler().cancelTask(Helmet);
                        }
                        if (p.getInventory().getHelmet() != null) {
                            // p.sendMessage(": "+
                            // p.getInventory().getHelmet().getItemMeta().getDisplayName());
                            if (p.getInventory().getHelmet().getType().toString().contains("IRON_HELMET")
                                    && (p.getInventory().getHelmet().getItemMeta().getDisplayName().contains(ChatColor.LIGHT_PURPLE + "Weltraumhelm"))) {
                                if (!message.get("Helmetmessage")) {
                                    p.sendMessage(ChatColor.GREEN + "Du hast einen Weltraumhelm auf, du verlierst keine Luft!");
                                    message.put("Helmetmessage", true);
                                    message.put("NoHelmetmessage", false);
                                }
                            } else {
                                if (!message.get("NoHelmetmessage")) {
                                    p.sendMessage(ChatColor.RED + "Du hast keinen Weltraumhelm auf!");
                                    message.put("NoHelmetmessage", true);
                                    message.put("Helmetmessage", false);
                                }
                                p.setRemainingAir(-1);
                                if (p.getRemainingAir() <= 1) {
                                    p.damage(0.01);
                                }
                            }
                        } else {
                            if (!message.get("NoHelmetmessage")) {
                                p.sendMessage(ChatColor.RED + "Du hast keinen Weltraumhelm auf!");
                                message.put("NoHelmetmessage", true);
                                message.put("Helmetmessage", false);
                            }
                            p.setRemainingAir(-1);
                            if (p.getRemainingAir() <= 1) {
                                p.damage(0.01);
                            }
                        }
                    }, 10, 0);
				}
			}).runTaskLater(this.plugin, 40L);
		} else {
			p.removePotionEffect(PotionEffectType.JUMP);
			p.removePotionEffect(PotionEffectType.SLOW_FALLING);
		}
	}
}
