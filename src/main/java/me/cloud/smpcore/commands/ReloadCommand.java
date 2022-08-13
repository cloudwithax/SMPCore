package me.cloud.smpcore.commands;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class ReloadCommand implements CommandExecutor {

    private final Plugin plugin;
    public ReloadCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (sender != null) {
            try {
                plugin.reloadConfig();
                player.sendMessage(ChatColor.GREEN + "Plugin config reloaded!");
            } catch (Exception e){
                player.sendMessage(ChatColor.RED + "Hey stupid! Check the console, you fucked up.");
            }
            return true;
        }
        return false;
    }
}
