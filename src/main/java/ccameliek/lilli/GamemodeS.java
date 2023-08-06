package ccameliek.lilli3;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*    */ public class GamemodeS implements CommandExecutor
/*    */ {
	/*    */ public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	/*    */ {
		/* 15 */ Player p = (Player) sender;
		/*    */
		/* 17 */ if ((sender instanceof Player)) {
			/* 18 */ if (label.equalsIgnoreCase("gm")) {
				/* 19 */ if ((p.hasPermission("Lilli.gm")) || (p.isOp())) {
					/* 20 */ if (args.length == 1) {
						/* 21 */ if (args.length == 0) {
							/*    */ }
						/*    */
						/* 25 */ else if (args[0].equalsIgnoreCase("0")) {
							/* 26 */ p.setGameMode(GameMode.SURVIVAL);
							/* 27 */ p.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Lilli" + ChatColor.GRAY + "] " + "Du bist jetzt im GameMode 0");
							/* 28 */ return true;
							/*    */ }
						/* 30 */ else if (args[0].equalsIgnoreCase("1")) {
							/* 31 */ p.setGameMode(GameMode.CREATIVE);
							/* 32 */ p.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Lilli" + ChatColor.GRAY + "] " + "Du bist jetzt im GameMode 1");
							/* 33 */ return true;
						}

						/* 35 */ else if (args[0].equalsIgnoreCase("2")) {
							/* 36 */ p.setGameMode(GameMode.ADVENTURE);
							/* 37 */ p.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Lilli" + ChatColor.GRAY + "] " + "Du bist jetzt im GameMode 2");
							/* 38 */ return true;
							/*    */ }
						/* 40 */ else if (args[0].equalsIgnoreCase("3")) {
							/* 41 */ p.setGameMode(GameMode.SPECTATOR);
							/* 42 */ p.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Lilli" + ChatColor.GRAY + "] " + "Du bist jetzt im GameMode 3");
							/* 43 */ return true;
							/*    */ }
						/*    */ } else {
						/* 46 */ sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Lilli" + ChatColor.GRAY + "] " + "/gm [0 bis 3]");
						/* 47 */ return true;
						/*    */ }
					/*    */ } else {
					/* 50 */ sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Lilli" + ChatColor.GRAY + "] " + "Du hast nicht die Berechtigung dies zu tun!");
					/* 51 */ return true;
					/*    */ }
				/*    */ } else {
				/* 54 */ sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Lilli" + ChatColor.GRAY + "] " + "/gm [0 bis 3]");
				/* 55 */ return true;
				/*    */ }
			/*    */ } else {
			/* 58 */ sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Lilli" + ChatColor.GRAY + "] " + "Dieser Befehl ist nur für Spieler!");
			/* 59 */ return true;
			/*    */ }
		/* 61 */ return false;
		/*    */ }
	/*    */ }