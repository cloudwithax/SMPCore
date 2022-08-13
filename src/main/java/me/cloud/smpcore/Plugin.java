package me.cloud.smpcore;

import me.cloud.smpcore.commands.*;
import me.cloud.smpcore.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Plugin extends JavaPlugin {

    private HashMap<UUID, Boolean> sleeping = new HashMap<>();
    private HashMap<UUID, UUID> messages = new HashMap<>();
    private ArrayList<UUID> vanished = new ArrayList<>();
    public FileConfiguration config = getConfig();

    public ChatColor accentColor = ChatColor.valueOf(config.getString("accent_color"));

    public ArrayList<String> motds = (ArrayList<String>) config.getStringList("motds");

    public ArrayList<String> joinMOTD = (ArrayList<String>) config.getStringList("join_motd");

    public String chatPrefix = ChatColor.GRAY + "[" + accentColor + "SMP" + ChatColor.GRAY + "] ";

    public String serverName = config.getString("server_name");

    public boolean isSleeping(UUID uuid) { return sleeping.containsKey(uuid); }

    public void addToMessages(UUID sender, UUID receiver ) { messages.put(sender, receiver); }

    public void addToVanish(UUID uuid) { vanished.add(uuid); }

    public void removeFromVanish(UUID uuid) { vanished.remove(uuid); }

    public ArrayList<UUID> getVanished() { return vanished; }

    public boolean isVanished(UUID uuid) { return vanished.contains(uuid); }

    public void removeFromMessages(UUID sender) { messages.remove(sender); }

    public boolean hasRecipient(UUID sender) {return messages.containsKey(sender); }

    public UUID getRecipient(UUID sender) { return messages.get(sender);}

    public void addToSleeping(UUID uuid, Boolean bool) {
        sleeping.put(uuid, bool);
    }

    public void removeFromSleeping(UUID uuid) {
        sleeping.remove(uuid);
    }

    public boolean playersSleeping() {
        return sleeping.size() > 0;
    }

    private static String plural(long num, String unit) {
        return num + " " + unit + (num == 1 ? "" : "s");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        accentColor = ChatColor.valueOf(getConfig().getString("accent_color"));
        motds = (ArrayList<String>) getConfig().getStringList("motds");
        joinMOTD = (ArrayList<String>) getConfig().getStringList("join_motd");
        serverName = getConfig().getString("server_name");
        saveConfig();


    }

    public String formatDuration(Duration duration) {
        List<String> parts = new ArrayList<>();
        long days = duration.toDaysPart();
        if (days > 0) {
            parts.add(plural(days, "day"));
        }
        int hours = duration.toHoursPart();
        if (hours > 0 || !parts.isEmpty()) {
            parts.add(plural(hours, "hour"));
        }
        int minutes = duration.toMinutesPart();
        if (minutes > 0 || !parts.isEmpty()) {
            parts.add(plural(minutes, "minute"));
        }
        int seconds = duration.toSecondsPart();
        parts.add(plural(seconds, "second"));
        return String.join(", ", parts);
    }

    public long startTime = System.currentTimeMillis();

    @Override
    public void onEnable() {
        getLogger().info("Plugin has been enabled!");
        getServer().getPluginManager().registerEvents(new PlayerLeave(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(), this);
        getServer().getPluginManager().registerEvents(new PlayerBedEnter(this), this);
        getServer().getPluginManager().registerEvents(new PlayerBedExit(this), this);
        getServer().getPluginManager().registerEvents(new ServerListPing(this), this);
        getCommand("playtime").setExecutor(new PlaytimeCommand(this));
        getCommand("ping").setExecutor(new PingCommand(this));
        getCommand("alert").setExecutor(new AlertCommand(this));
        getCommand("gms").setExecutor(new GamemodeSurvival(this));
        getCommand("gma").setExecutor(new GamemodeAdventure(this));
        getCommand("gmsp").setExecutor(new GamemodeSpectator(this));
        getCommand("gmc").setExecutor(new GamemodeCreative(this));
        getCommand("msg").setExecutor(new MsgCommand(this));
        getCommand("r").setExecutor(new ReplyCommand(this));
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("usage").setExecutor(new UsageCommand(this));
        getCommand("smpreload").setExecutor(new ReloadCommand(this));
        getCommand("heal").setExecutor(new HealCommand(this));


        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            getServer().dispatchCommand(getServer().getConsoleSender(), "save-all");
        }, 0L, 2000L);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            int online = 0;
            if (vanished.size() > 0) {
                online = Bukkit.getOnlinePlayers().size() - vanished.size();
            } else {
                online = Bukkit.getOnlinePlayers().size();
            }

            DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a");
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Bukkit.getOnlinePlayers().size() == 0) return;
                if (player.hasPermission("smp.owner") || player.isOp()) {
                    player.setPlayerListName("§8[§cOwner§8] §c" + player.getDisplayName() + "§7 ❘§f ☠ " + player.getStatistic(Statistic.DEATHS));

                } else {
                    player.setPlayerListName(player.getDisplayName() + "§7 ❘§f ☠ " + player.getStatistic(Statistic.DEATHS));
                }

                player.setPlayerListHeaderFooter(
                        ChatColor.translateAlternateColorCodes('&',
                                "\n &7-- " + accentColor + serverName + " &7-- \n"),
                        ChatColor.translateAlternateColorCodes('&', "\n &7Players online: " + accentColor + online +
                                "\n &7Time: " + accentColor + java.time.LocalTime.now().format(format) + "\n"));
            }
        }, 0L, 20L);


    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled, goodbye!");
    }

}
