package me.cloud.smpcore.events;

import me.cloud.smpcore.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.UUID;

public class PlayerJoin implements Listener {

    private final Plugin plugin;

    public PlayerJoin(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.isVanished(player.getUniqueId())) {
            event.setJoinMessage(ChatColor.GRAY + "[" + plugin.accentColor + "+" + ChatColor.GRAY + "] " + plugin.accentColor + event.getPlayer().getDisplayName() + ChatColor.GRAY + " joined the game");
        } else {
            event.setJoinMessage("");
        }
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            plugin.joinMOTD.forEach(line -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            });
        }, 5);

        if (plugin.getVanished().size() > 0) {
            for (UUID uuid : plugin.getVanished()) {
                Player vanished = plugin.getServer().getPlayer(uuid);
                for (Player serverPlayer : plugin.getServer().getOnlinePlayers()) {
                    serverPlayer.hidePlayer(plugin, vanished);
                }
            }
        }

        if (plugin.isVanished(player.getUniqueId())) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[" + plugin.accentColor + "SMP" + "&7] You are still &avanished&7!"));
                player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1F, 1F);
            }, 10);
        }








    }
}
