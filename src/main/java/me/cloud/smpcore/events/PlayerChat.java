package me.cloud.smpcore.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.getPlayer().hasPermission("smp.owner") || event.getPlayer().isOp()) {
            event.setFormat(ChatColor.translateAlternateColorCodes('&', "&8[&cOwner&8] ") + ChatColor.RED + event.getPlayer().getDisplayName() + ChatColor.WHITE + ": " + event.getMessage());
        } else {
            event.setFormat(ChatColor.GRAY + event.getPlayer().getDisplayName()  + ": " + ChatColor.WHITE + event.getMessage());
        }
    }
}
