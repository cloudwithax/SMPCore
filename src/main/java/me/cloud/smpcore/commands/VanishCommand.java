package me.cloud.smpcore.commands;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    private final Plugin plugin;

    public VanishCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (plugin.isVanished(player.getUniqueId())) {
            plugin.removeFromVanish(player.getUniqueId());
            player.sendMessage(plugin.chatPrefix + "You have been" + ChatColor.GREEN + " unvanished");
            plugin.getServer().broadcastMessage(ChatColor.GRAY + "[" + plugin.accentColor + "+" + ChatColor.GRAY + "] " + plugin.accentColor + player.getDisplayName() + ChatColor.GRAY + " joined the game");

            for (Player serverPlayer : plugin.getServer().getOnlinePlayers()) {
                serverPlayer.showPlayer(plugin, player);
            }
        } else {
            plugin.addToVanish(player.getUniqueId());
            player.sendMessage(plugin.chatPrefix + "You have been" + ChatColor.GREEN + " vanished");
            plugin.getServer().broadcastMessage(ChatColor.GRAY + "[" + plugin.accentColor + "-" + ChatColor.GRAY + "] " + plugin.accentColor + player.getDisplayName() + ChatColor.GRAY + " left the game");

            for (Player serverPlayer : plugin.getServer().getOnlinePlayers()) {
                serverPlayer.hidePlayer(plugin, player);
            }

        }
        return true;
    }
}
