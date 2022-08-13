package me.cloud.smpcore.events;

import me.cloud.smpcore.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.ArrayList;
import java.util.Random;


public class ServerListPing implements Listener {

    private final Plugin plugin;

    public ServerListPing(Plugin plugin) { this.plugin = plugin; }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        Random generator = new Random();
        ArrayList<String> motds = plugin.motds;
        int index = generator.nextInt(motds.size());
        String motd = motds.get(index);
        event.setMotd(ChatColor.translateAlternateColorCodes('&', motd.replace("%nl%", "\n")));
    }

}
