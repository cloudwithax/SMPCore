package me.cloud.smpcore.commands;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MsgCommand implements CommandExecutor {

    private final Plugin plugin;

    public MsgCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (sender != null) {
            if (args.length > 0) {
                Player target = plugin.getServer().getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "That player isn't online!");
                    return true;
                }
                String[] formattedMessage = Arrays.copyOfRange(args, 1, args.length);
                String message = String.join(" ", formattedMessage);
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
