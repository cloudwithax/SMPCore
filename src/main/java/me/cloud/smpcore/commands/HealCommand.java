package me.cloud.smpcore.commands;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    private Plugin plugin;
    public HealCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (sender != null) {
            if (args.length > 0) {
                if (args[0].equals("*")) {
                    plugin.getServer().getOnlinePlayers().forEach(target -> {
                        target.setHealth(20);
                        target.setFoodLevel(20);
                        target.sendMessage(plugin.accentColor + "You have been healed.");
                        player.sendMessage(ChatColor.GRAY + "Healed " + plugin.accentColor + target.getDisplayName());
                    });

                } else {
                    Player target = plugin.getServer().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "That player isn't online!");
                        return true;
                    }
                    target.setHealth(20);
                    target.setFoodLevel(20);
                    target.sendMessage(plugin.accentColor + "You have been healed.");
                    player.sendMessage(ChatColor.GRAY + "Healed " + plugin.accentColor + target.getDisplayName());
                }

            }


        }
        return false;
    }
}
