package me.cloud.smpcore.events;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    private final Plugin plugin;

    public PlayerLeave(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!plugin.isVanished(event.getPlayer().getUniqueId())) {
            event.setQuitMessage(ChatColor.GRAY + "[" + plugin.accentColor + "-" + ChatColor.GRAY + "] " + plugin.accentColor + event.getPlayer().getDisplayName() + ChatColor.GRAY + " left the game");
        } else {
            event.setQuitMessage("");
        }
        if (plugin.hasRecipient(event.getPlayer().getUniqueId())) {
            plugin.removeFromMessages(event.getPlayer().getUniqueId());
        }
    }
}
