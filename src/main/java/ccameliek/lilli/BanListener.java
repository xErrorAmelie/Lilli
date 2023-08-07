package ccameliek.lilli;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
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

public class BanListener implements CommandExecutor, Listener {

	public BanListener(Lilli plugin) {
		this.console = Bukkit.getServer().getConsoleSender();

		this.plugin = plugin;
	}

	ConsoleCommandSender console;
	private final Lilli plugin;
	private String reason = "";

	@EventHandler
	public void onPlayerBanProzess(AsyncPlayerChatEvent e) {
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

	@SuppressWarnings({ "deprecation" })
	// -----------------------------------------------UNBAN-----------------------------------
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("unban")) {
			if (!(sender instanceof Player)) {
				Player p = (Player) sender;
				String name = args[0];
				OfflinePlayer tounbanned = Bukkit.getOfflinePlayer(name);
				if (tounbanned.isBanned()) {
					if (p.isOp() || p.hasPermission("Lilli.admin.unban")) {
						if (args.length == 1) {
							Bukkit.getBanList(BanList.Type.NAME).pardon(name);
							for (Player op : Bukkit.getOnlinePlayers()) {
								if (op.isOp()) {
									op.sendMessage(Lilli.prefix.toString() + NamedTextColor.AQUA + name + NamedTextColor.GREEN + " wurde erfolgreich entbannt!");
								}
							}
						} else {
							p.sendMessage(NamedTextColor.RED + "/unban <Name>");
						}
					} else {
						p.sendMessage(NamedTextColor.RED + "Du hast keine Rechte dafür!");
					}
				} else {
					p.sendMessage(NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + name + NamedTextColor.RED + " ist nicht gebannt!");
				}
			}
		}
		// ------------------------------------------BAN---------------------------------------------
		if (label.equalsIgnoreCase("ban")) {
			if ((sender instanceof Player)) {
				Player p = (Player) sender;
				if (p.isOp() || p.hasPermission("Lilli.admin.ban")) {
					String banner = p.getDisplayName();
					// -----------------------------------MIT GRUND------------------------------
					if (args.length >= 2) {
						String name = args[0];
						OfflinePlayer player = Bukkit.getOfflinePlayer(name);
						String banned = Bukkit.getOfflinePlayer(name).getName();
						for (int i = 1; i < args.length; i++) {
							reason = reason + args[i] + " ";
						}
						if (!Bukkit.getOfflinePlayer(name).isBanned()) {
							if (player.isOnline()) {
								String bannedname = Bukkit.getPlayer(name).getDisplayName();
								Bukkit.getBanList(BanList.Type.NAME).addBan(banned, NamedTextColor.DARK_RED + reason, null, banner);
								for (Player op : Bukkit.getOnlinePlayers()) {
									if (op.isOp()) {
										op.sendMessage(String.valueOf(Lilli.prefix) + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner + NamedTextColor.GRAY + " Grund: "
												+ NamedTextColor.GRAY + reason);
									}
								}
								Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + bannedname + NamedTextColor.RED + " wurde gebannt!");
								Player banned1 = Bukkit.getPlayer(name);
                                assert banned1 != null;
                                banned1.kickPlayer(plugin.serverpre + "\n\n" + NamedTextColor.RED + "Du wurdest gebannt. \nGrund: " + NamedTextColor.GRAY + reason);

							} else {
								String bannedname = Bukkit.getOfflinePlayer(name).getName();
                                assert banned != null;
                                Bukkit.getBanList(BanList.Type.NAME).addBan(banned, NamedTextColor.DARK_RED + reason, null, banner);
								for (Player op : Bukkit.getOnlinePlayers()) {
									if (op.isOp()) {
										op.sendMessage(String.valueOf(Lilli.prefix) + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner + NamedTextColor.GRAY + " Grund: "
												+ NamedTextColor.GRAY + reason);
									}
								}
								Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.RED + " wurde gebannt!");
							}
						} else {
							p.sendMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + name + NamedTextColor.RED + " ist bereits gebannt!");
						}
						reason = "";
						// ------------------------------OHNE GRUND-----------------------------
					} else if (args.length == 1) {
						String name = args[0];
						OfflinePlayer player = Bukkit.getOfflinePlayer(name);
						String banned = Bukkit.getOfflinePlayer(name).getName();
						if (!Bukkit.getOfflinePlayer(name).isBanned()) {
							if (player.isOnline()) {
								String bannedname = Bukkit.getPlayer(name).getDisplayName();
								Bukkit.getBanList(BanList.Type.NAME).addBan(banned, NamedTextColor.DARK_RED + "Du wurdest gebannt.", null, banner);
								for (Player op : Bukkit.getOnlinePlayers()) {
									if (op.isOp()) {
										op.sendMessage(Lilli.adminprefix.toString() + NamedTextColor.GRAY + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner
												+ NamedTextColor.GRAY + "!");
									}
								}
								Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + bannedname + NamedTextColor.RED + " wurde gebannt!");
								Player banned1 = Bukkit.getPlayer(name);
                                assert banned1 != null;
                                banned1.kickPlayer(plugin.serverpre + "\n\n" + NamedTextColor.RED + "Du wurdest gebannt");

							} else {
								String bannedname = Bukkit.getOfflinePlayer(name).getName();
								Bukkit.getBanList(BanList.Type.NAME).addBan(banned, NamedTextColor.DARK_RED + "Du wurdest gebannt.", null, banner);
								for (Player op : Bukkit.getOnlinePlayers()) {
									if (op.isOp()) {
										op.sendMessage(Lilli.adminprefix.toString() + NamedTextColor.GRAY + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner
												+ NamedTextColor.GRAY + "!");
									}
								}
								Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.RED + " wurde gebannt!");
							}
						} else {
							p.sendMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + name + NamedTextColor.RED + " ist bereits gebannt!");
						}
					} else {
						p.sendMessage(NamedTextColor.RED + "/ban <Name> <Grund>");
					}

				} else {
					p.sendMessage(NamedTextColor.RED + "Du hast keine Rechte dafür!");
				}
			} else {
				sender.sendMessage("§cDieser Befehl ist nur für Spieler!");
			}
		}
		// -----------------------------------------------MUSIK-BAN-----------------------------------------
		if (label.equalsIgnoreCase("rip")) {
			if ((sender instanceof Player)) {
				Player p = (Player) sender;
				if (p.isOp() || p.hasPermission("Lilli.admin.rip")) {
					String banner = p.getDisplayName();
					// ------------------MIT GRUND-------------------
					if (args.length >= 2) {
						String name = args[0];
						OfflinePlayer player = Bukkit.getOfflinePlayer(name);
						String banned = Bukkit.getOfflinePlayer(name).getName();
						if (!Bukkit.getOfflinePlayer(name).isBanned()) {
							for (int i = 1; i < args.length; i++) {
								reason = reason + args[i] + " ";
							}
							for (Player p1 : Bukkit.getOnlinePlayers()) {
								p1.playSound(p1.getLocation(), Sound.MUSIC_DISC_CHIRP, 2.0F, 1.0F);
							}
							Bukkit.getBanList(BanList.Type.NAME).addBan(banned, NamedTextColor.DARK_RED + reason, null, banner);
							if (player.isOnline()) {
								String bannedname = Bukkit.getPlayer(name).getDisplayName();
								Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.GRAY + "Ban-Prozess für " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " gestartet! 30 Sekunden verbleibend!");
								(new BukkitRunnable() {
									public void run() {
										for (Player p1 : Bukkit.getOnlinePlayers()) {
											p1.stopSound(Sound.MUSIC_DISC_CHIRP);
											p1.playSound(p1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
										}
										for (Player op : Bukkit.getOnlinePlayers()) {
											if (op.isOp()) {
												op.sendMessage(String.valueOf(Lilli.prefix) + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner + NamedTextColor.GRAY
														+ " Grund: " + NamedTextColor.GRAY + reason);
											}
										}
										Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + bannedname + NamedTextColor.RED + " wurde gebannt!");
										Player banned1 = Bukkit.getPlayer(name);
                                        assert banned1 != null;
                                        banned1.kickPlayer(plugin.serverpre + "\n\n" + NamedTextColor.RED + "Du wurdest gebannt. \nGrund: " + NamedTextColor.GRAY + reason);
										reason = "";
									}
								}).runTaskLater(this.plugin, 660L);

							} else {
								String bannedname = Bukkit.getOfflinePlayer(name).getName();
								Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.GRAY + "Ban-Prozess für " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " gestartet! 30 Sekunden verbleibend!");
								(new BukkitRunnable() {
									public void run() {
										for (Player p1 : Bukkit.getOnlinePlayers()) {
											p1.stopSound(Sound.MUSIC_DISC_CHIRP);
											p1.playSound(p1.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
										}
										for (Player op : Bukkit.getOnlinePlayers()) {
											if (op.isOp()) {
												op.sendMessage(String.valueOf(Lilli.prefix) + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner + NamedTextColor.GRAY
														+ " Grund: " + NamedTextColor.GRAY + reason);
											}
										}
										Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.RED + " wurde gebannt!");
										reason = "";
									}
								}).runTaskLater(this.plugin, 660L);
							}
						} else {
							p.sendMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + name + NamedTextColor.RED + " ist bereits gebannt!");
						}

						// -------------OHNE GRUND-----------------
					} else if (args.length == 1) {
						String name = args[0];
						OfflinePlayer player = Bukkit.getOfflinePlayer(name);
						String banned = Bukkit.getOfflinePlayer(name).getName();
						if (!Bukkit.getOfflinePlayer(name).isBanned()) {
							for (Player p1 : Bukkit.getOnlinePlayers()) {
								p1.playSound(p1.getLocation(), Sound.MUSIC_DISC_CHIRP, 2.0F, 1.0F);
							}
							if (player.isOnline()) {
								String bannedname = Bukkit.getPlayer(name).getDisplayName();
								Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.GRAY + "Ban-Prozess für " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " gestartet! 30 Sekunden verbleibend!");
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
												op.sendMessage(Lilli.adminprefix.toString() + NamedTextColor.GRAY + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner
														+ NamedTextColor.GRAY + "!");
											}
										}
										Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.RED + " wurde gebannt!");
									}
								}).runTaskLater(this.plugin, 660L);
							} else {
								String bannedname = Bukkit.getOfflinePlayer(name).getName();
								Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.GRAY + "Ban-Prozess für " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " gestartet! 30 Sekunden verbleibend!");
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
												op.sendMessage(Lilli.adminprefix.toString() + NamedTextColor.GRAY + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.GRAY + " wurde gebannt von " + banner
														+ NamedTextColor.GRAY + "!");
											}
										}
										Bukkit.broadcastMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + bannedname + NamedTextColor.RED + " wurde gebannt!");
									}
								}).runTaskLater(this.plugin, 660L);
							}
						} else {
							p.sendMessage(Lilli.prefix.toString() + NamedTextColor.RED + "Der Spieler " + NamedTextColor.AQUA + name + NamedTextColor.RED + " ist bereits gebannt!");
						}
					} else {
						p.sendMessage(NamedTextColor.RED + "/ban <Name> <Grund>");
					}
				} else {
					p.sendMessage(NamedTextColor.RED + "Du hast keine Rechte dafür!");
				}
			} else {
				sender.sendMessage("§cDieser Befehl ist nur für Spieler!");
			}

		}
		return false;
	}
}
