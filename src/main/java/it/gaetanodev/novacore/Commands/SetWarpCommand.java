package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SetWarpCommand implements CommandExecutor {
    private final Plugin plugin;
    private FileConfiguration warpsConfig;
    private File warpsFile;

    public SetWarpCommand(Plugin plugin) {
        this.plugin = plugin;
        createWarpsFile();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("novacore.command.setwarp")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("warp-nospecific")));
            return true;
        }

        String warpName = args[0];

        warpsConfig.set(warpName + ".world", player.getLocation().getWorld().getName());
        warpsConfig.set(warpName + ".x", player.getLocation().getX());
        warpsConfig.set(warpName + ".y", player.getLocation().getY());
        warpsConfig.set(warpName + ".z", player.getLocation().getZ());
        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile); // Ricarica la configurazione

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("setwarp-created")));
        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private void createWarpsFile() {
        File storageDir = new File(plugin.getDataFolder(), "storage");
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }

        warpsFile = new File(storageDir, "warps.yml");
        if (!warpsFile.exists()) {
            try {
                warpsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);
    }
}
