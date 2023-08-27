package ccameliek.lilli;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import net.megavex.scoreboardlibrary.api.sidebar.component.ComponentSidebarLayout;
import net.megavex.scoreboardlibrary.api.sidebar.component.SidebarComponent;
import net.megavex.scoreboardlibrary.api.sidebar.component.animation.CollectionSidebarAnimation;
import net.megavex.scoreboardlibrary.api.sidebar.component.animation.SidebarAnimation;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;


public class Scoreboard implements Listener {
    ConsoleCommandSender console;
    private final Sidebar sidebar;
    private final ComponentSidebarLayout componentSidebar;
    private final SidebarAnimation<Component> titleAnimation;

    public Scoreboard(Lilli plugin, Sidebar sidebar) {
        this.sidebar = sidebar;
        this.console = Bukkit.getServer().getConsoleSender();
        this.titleAnimation = createGradientAnimation(Component.text("SaMe.de", Style.style(TextDecoration.BOLD)));
        var title = SidebarComponent.animatedLine(titleAnimation);

        SimpleDateFormat dtf = new SimpleDateFormat("HH:mm:ss");

        // Custom SidebarComponent, see below for how an implementation might look like

        SidebarComponent lines = SidebarComponent.builder()
                .addBlankLine()
                .addDynamicLine(() -> {
                    var time = dtf.format(new Date());
                    return Component.text("Testupdates: " + time, NamedTextColor.WHITE);
                })
                .addBlankLine()
                .addStaticLine(Component.text("oh miau!"))
                .addBlankLine()
                .addStaticLine(Component.text("Rang:", NamedTextColor.DARK_AQUA))
                .addDynamicLine(() -> {
                    if(PermissionCheck(sidebar.players()) !=  null) {
                        return Component.text(PermissionCheck(sidebar.players())); //Achtung
                    }else{
                        return Component.text("PermissionCheck empty", NamedTextColor.DARK_AQUA);
                    }
                })
                .addBlankLine()
                .addStaticLine(Component.text("Geld:", NamedTextColor.DARK_AQUA))
                .addStaticLine(Component.text("... Coins", NamedTextColor.WHITE))
                .build();
        this.componentSidebar = new ComponentSidebarLayout(title, lines);
        Bukkit.broadcast(Component.text(sidebar.players().toString()));
        (new BukkitRunnable () {
            public void run() {
                tick();
            }
        }).runTaskTimer(plugin, 20L, 20L);
    }

    public void tick() {
        titleAnimation.nextFrame();
        // Update sidebar title & lines
        componentSidebar.apply(sidebar);
    }

    String PermissionCheck(@NotNull Collection<Player> players) {
        HashMap<String, String> teams = Rangs.teams;
        String currentTeamName = null;
        for (String perm : teams.keySet()) {
            for (Player player : players) {
                if (perm == null || player.hasPermission(perm)) {
                    currentTeamName = teams.get(perm);
                }
            }
        }
        return currentTeamName;
    }
    private @NotNull SidebarAnimation<Component> createGradientAnimation(@NotNull Component text) {
        float step = 1f / 8f;

        TagResolver.Single textPlaceholder = Placeholder.component("text", text);
        List<Component> frames = new ArrayList<>((int) (2f / step));

        float phase = -1f;
        while (phase < 1) {
            frames.add(MiniMessage.miniMessage().deserialize("<gradient:aqua:dark_aqua:" + phase + "><text>", textPlaceholder));
            phase += step;
        }

        return new CollectionSidebarAnimation<>(frames);
    }
}





// On plugin shutdown:

/*

	private static DecimalFormat df2 = new DecimalFormat("#.##");
	static double[] tps = org.bukkit.Bukkit.getTPS();
	@SuppressWarnings("deprecation")
	public static void sendScoreboard(Player player) {
		Scoreboard board = (Scoreboard) Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = ((org.bukkit.scoreboard.Scoreboard) board).getObjective("aaa") != null ? ((org.bukkit.scoreboard.Scoreboard) board).getObjective("aaa")
				: ((org.bukkit.scoreboard.Scoreboard) board).registerNewObjective("aaa", "bbb");

        assert obj != null;
        obj.setDisplayName("Test");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		obj.getScore("Coins").setScore(50);

		player.setScoreboard((org.bukkit.scoreboard.Scoreboard) board);
	}


	public static void updateScoreboard(Player player) {
        player.getScoreboard();
        Objective obj = player.getScoreboard().getObjective("aaa") != null ? player.getScoreboard().getObjective("aaa") : player.getScoreboard().registerNewObjective("aaa", "bbb");
        assert obj != null;
        obj.getScore(ChatColor.GREEN + "TPS: " + ChatColor.GOLD + df2.format(tps[0])).setScore(0);
        ;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player p = (Player) sender;
		if (label.equalsIgnoreCase("showtps")) {
			if (p.isOp() || p.hasPermission("Lilli.admin.showtps")) {
				sendScoreboard(p);
				if (!Bukkit.getScheduler().isCurrentlyRunning(sched)) {
					Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
						@Override
						public void run() {
							updateScoreboard(p);
						}
					}, 20, 20);
				}
			}
		} else {
			p.sendMessage(ChatColor.RED + "Du hast keine Rechte dafür!");
		}
		return false;
	}*/

