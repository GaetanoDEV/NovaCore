package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class BackCommand implements CommandExecutor {
    private final Plugin plugin;
    private FileConfiguration backConfig;
    private File backFile;

    public BackCommand(Plugin plugin) {
        this.plugin = plugin;
        createBackFile();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!player.hasPermission("novacore.command.back")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
            return true;
        }

        if (backConfig.getConfigurationSection(player.getName()) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("back-nolocation")));
            return true;
        }

        double x = backConfig.getDouble(player.getName() + ".x");
        double y = backConfig.getDouble(player.getName() + ".y");
        double z = backConfig.getDouble(player.getName() + ".z");
        String worldName = backConfig.getString(player.getName() + ".world");

        if (worldName == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("back-noworld")));
            return true;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("back-noworld")));
            return true;
        }

        Location backLocation = new Location(world, x, y, z);
        player.teleportAsync(backLocation);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("back-teleport")));

        return true;
    }

    public void createBackFile() {
        File storageDir = new File(plugin.getDataFolder(), "storage");
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }

        backFile = new File(storageDir, "BackLocations.yml");
        if (!backFile.exists()) {
            try {
                backFile.createNewFile();
            } catch (IOException e) {
                // Handle the exception
            }
        }
        backConfig = YamlConfiguration.loadConfiguration(backFile);
    }

    public void saveBackLocation(Player player) {
        backConfig.set(player.getName() + ".x", player.getLocation().getX());
        backConfig.set(player.getName() + ".y", player.getLocation().getY());
        backConfig.set(player.getName() + ".z", player.getLocation().getZ());
        backConfig.set(player.getName() + ".world", player.getLocation().getWorld().getName());
        try {
            backConfig.save(backFile);
        } catch (IOException e) {
            // Handle the exception
        }
    }
}

