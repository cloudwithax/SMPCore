package me.cloud.smpcore.commands;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;

public class PlaytimeCommand implements CommandExecutor {

    private final Plugin plugin;

    public PlaytimeCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (sender != null) {
            long minutesPlayed = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
            Duration msPlayed = Duration.ofMillis(minutesPlayed * 50);
            String humanized = plugin.formatDuration(msPlayed);
            player.sendMessage(plugin.chatPrefix + ChatColor.GRAY + "You have played on the server for " + plugin.accentColor + humanized);
        }
        return true;
    }
}
