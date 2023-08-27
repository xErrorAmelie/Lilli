package ccameliek.lilli;

import ccameliek.lilli.strings.ranks;
import com.destroystokyo.paper.profile.PlayerProfile;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.ban.ProfileBanList;
import org.bukkit.block.data.type.Slab;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import javax.inject.Named;
import java.net.InetAddress;
import java.util.Objects;
import java.util.UUID;

public class BanListener implements CommandExecutor, Listener {

	public BanListener(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();

		this.plugin = plugin;
	}

	ConsoleCommandSender console;
	private final Lilli plugin;
	private String reason = "";

	@EventHandler
	public void onPlayerBanProzess(AsyncChatEvent e) {
		Player p = e.getPlayer();
		if (p.isBanned()) {
			e.setCancelled(true);
			p.sendMessage(NamedTextColor.RED + "Du kannst nicht schreiben während du gebannt wirst!");
		}
	}

	@EventHandler
	public void onPlayerBanProzessBlock(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.isBanned()) {
			p.setGameMode(GameMode.ADVENTURE);
		}
	}
	@Deprecated
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		String name = player.getName();
		OfflinePlayer tounbanned = Bukkit.getOfflinePlayer(name);
		// -----------------------------------------------UNBAN-----------------------------------
		if (label.equalsIgnoreCase("unban")) {
			if ((tounbanned.isBanned())) {
				player.sendMessage(Component.text("Der Spieler: " + NamedTextColor.AQUA + name + NamedTextColor.RED + " ist nicht gebannt!"));
			} else {
				if (!(player.isOp() || player.hasPermission("Lilli.admin.unban"))) {
					player.sendMessage(Component.text(NamedTextColor.RED + "Du hast keine Rechte dafür!"));
				} else {
					if (!(args.length == 1)) {
						player.sendMessage(Component.text(NamedTextColor.RED + "/unban <Name>"));
					} else {
						Bukkit.getBanList(BanList.Type.NAME).pardon(name);
						for (Player op : Bukkit.getOnlinePlayers()) {
							if (op.isOp()) {
								op.sendMessage(ranks.prefix.append(Component.text(name).color(NamedTextColor.AQUA).append(Component.text(" wurde erfolgreich gebannt!").color(NamedTextColor.GREEN))));
							}
						}
					}
				}
			}
		}

		// -----------------------------------------------MUSIK-BAN-----------------------------------------
		if (label.equalsIgnoreCase("rip")) {
				if (player.isOp() || player.hasPermission("Lilli.admin.rip")) {
					String banner = player.getDisplayName();
					// ------------------MIT GRUND-------------------
					if (args.length >= 2) {
						OfflinePlayer offlineplayer = Bukkit.getOfflinePlayer(name);
						String banned = Bukkit.getOfflinePlayer(name).getName();
						if (!Bukkit.getOfflinePlayer(name).isBanned()) {
							for (int i = 1; i < args.length; i++) {
								reason = reason + args[i] + " ";
							}
							for (Player p1 : Bukkit.getOnlinePlayers()) {
								p1.playSound(p1.getLocation(), Sound.MUSIC_DISC_CHIRP, 2.0F, 1.0F);
							}
                            assert banned != null;
                            Bukkit.getBanList(BanList.Type.NAME).addBan(banned, NamedTextColor.DARK_RED + reason, null, banner);
							if (offlineplayer.isOnline()) {
								String bannedname = Objects.requireNonNull(Bukkit.getPlayer(name)).getDisplayName();
								Bukkit.broadcastMessage(ranks.prefix.toString() + NamedTextColor.GRAY + "Ban-Prozess für " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " gestartet! 30 Sekunden verbleibend!");
								(new BukkitRunnable() {
									public void run() {
										for (Player p1 : Bukkit.getOnlinePlayers()) {
											p1.stopSound(Sound.MUSIC_DISC_CHIRP);
											p1.playSound(p1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
										}
										for (Player op : Bukkit.getOnlinePlayers()) {
											if (op.isOp()) {
												op.sendMessage(String.valueOf(ranks.prefix) + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner + NamedTextColor.GRAY
														+ " Grund: " + NamedTextColor.GRAY + reason);
											}
										}
										Bukkit.broadcastMessage(ranks.prefix.toString() + NamedTextColor.RED + "Der Spieler " + bannedname + NamedTextColor.RED + " wurde gebannt!");
										Player banned1 = Bukkit.getPlayer(name);
                                        assert banned1 != null;
                                        banned1.kickPlayer(plugin.serverpre + "\n\n" + NamedTextColor.RED + "Du wurdest gebannt. \nGrund: " + NamedTextColor.GRAY + reason);
										reason = "";
									}
								}).runTaskLater(this.plugin, 660L);

							} else {
								String bannedname = Bukkit.getOfflinePlayer(name).getName();
								Bukkit.broadcastMessage(ranks.prefix.toString() + NamedTextColor.GRAY + "Ban-Prozess für " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " gestartet! 30 Sekunden verbleibend!");
								(new BukkitRunnable() {
									public void run() {
										for (Player p1 : Bukkit.getOnlinePlayers()) {
											p1.stopSound(Sound.MUSIC_DISC_CHIRP);
											p1.playSound(p1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
										}
										for (Player op : Bukkit.getOnlinePlayers()) {
											if (op.isOp()) {
												op.sendMessage(String.valueOf(ranks.prefix) + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner + NamedTextColor.GRAY
														+ " Grund: " + NamedTextColor.GRAY + reason);
											}
										}
										Bukkit.broadcastMessage(ranks.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.RED + " wurde gebannt!");
										reason = "";
									}
								}).runTaskLater(this.plugin, 660L);
							}
						} else {
							player.sendMessage(ranks.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + name + NamedTextColor.RED + " ist bereits gebannt!");
						}

						// -------------OHNE GRUND-----------------
					} else if (args.length == 1) {
						OfflinePlayer offlineplayer = Bukkit.getOfflinePlayer(name);
						String banned = Bukkit.getOfflinePlayer(name).getName();
						if (!Bukkit.getOfflinePlayer(name).isBanned()) {
							for (Player p1 : Bukkit.getOnlinePlayers()) {
								p1.playSound(p1.getLocation(), Sound.MUSIC_DISC_CHIRP, 2.0F, 1.0F);
							}
							if (offlineplayer.isOnline()) {
								String bannedname = Bukkit.getPlayer(name).getDisplayName();
								Bukkit.broadcastMessage(ranks.prefix.toString() + NamedTextColor.GRAY + "Ban-Prozess für " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " gestartet! 30 Sekunden verbleibend!");
                                assert banned != null;
                                Bukkit.getBanList(BanList.Type.NAME).addBan(banned, NamedTextColor.DARK_RED + "Du wurdest gebannt.", null, banner);
								(new BukkitRunnable() {
									public void run() {
										for (Player p1 : Bukkit.getOnlinePlayers()) {
											p1.stopSound(Sound.MUSIC_DISC_CHIRP);
										}
										for (Player p1 : Bukkit.getOnlinePlayers()) {
											p1.playSound(p1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
										}
										Player banned1 = Bukkit.getPlayer(name);

                                        assert banned1 != null;
                                        banned1.kickPlayer(plugin.serverpre + "\n\n" + NamedTextColor.RED + "Du wurdest gebannt");

										for (Player op : Bukkit.getOnlinePlayers()) {
											if (op.isOp()) {
												op.sendMessage(ranks.adminprefix.toString() + NamedTextColor.GRAY + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner
														+ NamedTextColor.GRAY + "!");
											}
										}
										Bukkit.broadcastMessage(ranks.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.RED + " wurde gebannt!");
									}
								}).runTaskLater(this.plugin, 660L);
							} else {
								String bannedname = Bukkit.getOfflinePlayer(name).getName();
								Bukkit.broadcastMessage(ranks.prefix.toString() + NamedTextColor.GRAY + "Ban-Prozess für " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " gestartet! 30 Sekunden verbleibend!");
                                assert banned != null;
                                Bukkit.getBanList(BanList.Type.NAME).addBan(banned, NamedTextColor.DARK_RED + "Du wurdest gebannt.", null, banner);
								(new BukkitRunnable() {
									public void run() {
										for (Player p1 : Bukkit.getOnlinePlayers()) {
											p1.stopSound(Sound.MUSIC_DISC_CHIRP);
										}
										for (Player p1 : Bukkit.getOnlinePlayers()) {
											p1.playSound(p1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
										}
										for (Player op : Bukkit.getOnlinePlayers()) {
											if (op.isOp()) {
												op.sendMessage(ranks.adminprefix.toString() + NamedTextColor.GRAY + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner
														+ NamedTextColor.GRAY + "!");
											}
										}
										Bukkit.broadcastMessage(ranks.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.RED + " wurde gebannt!");
									}
								}).runTaskLater(this.plugin, 660L);
							}
						} else {
							player.sendMessage(ranks.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + name + NamedTextColor.RED + " ist bereits gebannt!");
						}
					} else {
						player.sendMessage(NamedTextColor.RED + "/ban <Name> <Grund>");
					}
				} else {
					player.sendMessage(NamedTextColor.RED + "Du hast keine Rechte dafür!");
				}
			}
		return false;
	}
}
