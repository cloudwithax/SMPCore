package me.cloud.smpcore.commands;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ReplyCommand implements CommandExecutor {

    private final Plugin plugin;

    public ReplyCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (sender != null) {
            Player target = plugin.getServer().getPlayer(plugin.getRecipient(player.getUniqueId()));

            if (!plugin.hasRecipient(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You have no one to reply to!");
                return true;
            }

            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player isn't online!");
                return true;
            }

            if (args.length > 0) {
                String message = String.join(" ", args);
                player.sendMessage(
                        ChatColor.GRAY + "[" + plugin.accentColor + "you" +ChatColor.GRAY + "-> " + plugin.accentColor + target.getDisplayName() + ChatColor.GRAY + "] " + message
                );
                target.sendMessage(
                        ChatColor.GRAY + "[" + plugin.accentColor + player.getDisplayName() + ChatColor.GRAY + "-> " + plugin.accentColor + "you" + ChatColor.GRAY + "] " + message
                );
                plugin.addToMessages(player.getUniqueId(), target.getUniqueId());
                plugin.addToMessages(target.getUniqueId(), player.getUniqueId());
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 0.5F);
                target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 2F);
            } else {
                return false;
            }
        }
        return true;
    }
}
