package me.cloud.smpcore.commands;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class GamemodeAdventure implements CommandExecutor {

    private final Plugin plugin;
    public GamemodeAdventure(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (sender != null) {
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage(plugin.chatPrefix + "Your gamemode has been changed to " + plugin.accentColor + "Adventure");
        }
        return true;
    }
}
