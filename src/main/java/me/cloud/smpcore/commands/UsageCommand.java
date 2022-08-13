package me.cloud.smpcore.commands;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;

public class UsageCommand implements CommandExecutor {

    private final Plugin plugin;

    public UsageCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            float usedMem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            float totalMem = Runtime.getRuntime().maxMemory();
            String fmtUsedMem = Math.round(usedMem / 1048576) + "MB";
            String fmtTotalMem = Math.round(totalMem / 1048576) + "MB";
            long currentMs = System.currentTimeMillis();
            Duration elapsedDuration = Duration.ofMillis(currentMs - plugin.startTime);
            String fmtElapsed = plugin.formatDuration(elapsedDuration);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.chatPrefix + "Used Memory: " + plugin.accentColor + fmtUsedMem));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.chatPrefix +  "Total Memory: " + plugin.accentColor + fmtTotalMem));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.chatPrefix +  "Uptime: " + plugin.accentColor + fmtElapsed));


            return true;
        }
        return false;
    }
}
