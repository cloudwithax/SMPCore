package me.cloud.smpcore.events;

import me.cloud.smpcore.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerBedEnter implements Listener {

    private final Plugin plugin;

    public PlayerBedEnter(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        if (!event.getBedEnterResult().toString().equals("OK")) return;
        plugin.getServer().broadcastMessage(plugin.accentColor + event.getPlayer().getDisplayName() + ChatColor.GRAY + " is sleeping! Shh...");
        if (plugin.playersSleeping()) return;
        plugin.addToSleeping(event.getPlayer().getUniqueId(), true);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (plugin.isSleeping(event.getPlayer().getUniqueId())) {
                event.getPlayer().getWorld().setTime(1000);
                if (event.getPlayer().getWorld().hasStorm()) {
                    event.getPlayer().getWorld().setThundering(false);
                    event.getPlayer().getWorld().setStorm(false);
                }
                plugin.removeFromSleeping(event.getPlayer().getUniqueId());

            }
        }, 80);


    }
}
