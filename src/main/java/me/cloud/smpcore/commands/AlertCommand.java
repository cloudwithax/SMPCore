package me.cloud.smpcore.commands;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertCommand implements CommandExecutor {

    private final Plugin plugin;
    public AlertCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (sender != null) {
            if (args.length > 0) {
                String message = String.join(" ", args);
                player.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cAlert&8] &r" + message));
                for (Player serverPlayer : player.getWorld().getPlayers()) {
                    serverPlayer.playSound(serverPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 0.5F);
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
