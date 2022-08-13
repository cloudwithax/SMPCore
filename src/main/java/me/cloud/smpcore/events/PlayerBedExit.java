package me.cloud.smpcore.events;

import me.cloud.smpcore.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class PlayerBedExit implements Listener {
    private final Plugin plugin;

    public PlayerBedExit(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBedExit(PlayerBedLeaveEvent event) {
        if (plugin.isSleeping(event.getPlayer().getUniqueId())) {
            plugin.getServer().broadcastMessage(plugin.accentColor + event.getPlayer().getDisplayName() + ChatColor.GRAY + " left their bed, fast forward to day cancelled!");
            plugin.removeFromSleeping(event.getPlayer().getUniqueId());
        }
    }
}
