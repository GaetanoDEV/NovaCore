package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DelWarpCommand implements CommandExecutor, TabCompleter {
    private final Plugin plugin;
    private FileConfiguration warpsConfig;
    private File warpsFile;

    public DelWarpCommand(Plugin plugin) {
        this.plugin = plugin;
        createWarpsFile();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile); // Ricarica la configurazione
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("novacore.command.delwarp")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("warp-nospecific")));
            return true;
        }

        String warpName = args[0];

        if (!warpsConfig.contains(warpName)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("warp-notexist")));
            return true;
        }

        warpsConfig.set(warpName, null);
        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("delwarp-deleted")));

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile); // Ricarica la configurazione
        if (args.length == 1) {
            return new ArrayList<>(warpsConfig.getKeys(false));
        }

        return null;
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
