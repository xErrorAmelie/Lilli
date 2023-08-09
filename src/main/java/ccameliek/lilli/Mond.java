package ccameliek.lilli;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
import java.util.Objects;

public class Mond implements Listener{

	public Mond(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();

		this.plugin = plugin;
	}

	ConsoleCommandSender console;
	private final Lilli plugin;
	int Helmet = 0;

	private static final Map<String, Boolean> message = new HashMap<String, Boolean>();

	@EventHandler
	public void onMoonPort(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.getWorld().getName().contains("Mond")) {
			message.put("Helmetmessage", false);
			message.put("NoHelmetmessage", false);

			(new BukkitRunnable() {
				public void run() {
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 3));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100000, 0));
					for (Player p1 : Bukkit.getOnlinePlayers()) {
						if (p1.isOp()) {
							p1.sendMessage(Lilli.prefix.append(Component.text(player.displayName() + " hat den Mond betreten!").color(NamedTextColor.GRAY)));
						}
					}
					Helmet = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

						@Override
						public void run() {

							if (player.isDead()) {
								Bukkit.getServer().getScheduler().cancelTask(Helmet);
							}
							if (!player.getWorld().getName().contains("Mond")) {
								Bukkit.getServer().getScheduler().cancelTask(Helmet);
							}
							if (player.getInventory().getHelmet() != null) {
								// p.sendMessage(": "+
								// p.getInventory().getHelmet().getItemMeta().getDisplayName());
								if (player.getInventory().getHelmet().getType().toString().contains("IRON_HELMET")
										&& (Objects.requireNonNull(player.getInventory().getHelmet().getItemMeta().displayName()).contains(Component.text("Weltraumhelm").color(NamedTextColor.LIGHT_PURPLE)))) {
									if (!message.get("Helmetmessage")) {
										player.sendMessage(Component.text("Du hast einen Weltraumhelm auf, du verlierst keine Luft!").color(NamedTextColor.GREEN));
										message.put("Helmetmessage", true);
										message.put("NoHelmetmessage", false);
									}
								} else {
									if (!message.get("NoHelmetmessage")) {
										player.sendMessage(Component.text("Du hast keinen Weltraumhelm auf!").color(NamedTextColor.RED));
										message.put("NoHelmetmessage", true);
										message.put("Helmetmessage", false);
									}
									player.setRemainingAir(-1);
									if (player.getRemainingAir() <= 1) {
										player.damage(0.01);
									}
								}
							} else {
								if (!message.get("NoHelmetmessage")) {
									player.sendMessage(Component.text("Du hast keinen Weltraumhelm auf!").color(NamedTextColor.RED));
									message.put("NoHelmetmessage", true);
									message.put("Helmetmessage", false);
								}
								player.setRemainingAir(-1);
								if (player.getRemainingAir() <= 1) {
									player.damage(0.01);
								}
							}
						}
					}, 10, 0);
				}
			}).runTaskLater(this.plugin, 40L);
		} else {
			player.removePotionEffect(PotionEffectType.JUMP);
			player.removePotionEffect(PotionEffectType.SLOW_FALLING);
		}
	}
}
