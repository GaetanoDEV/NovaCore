package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DelHomeCommand implements CommandExecutor, TabCompleter {
    private final Plugin plugin;
    private FileConfiguration homesConfig;
    private File homesFile;

    public DelHomeCommand(Plugin plugin) {
        this.plugin = plugin;
        createHomesFile();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        homesConfig = YamlConfiguration.loadConfiguration(homesFile); // Ricarica il file di configurazione

        Player player = (Player) sender;

        if (!player.hasPermission("novacore.command.delhome")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("delhome-homename")));
            return true;
        }

        String homeName = args[0];

        if (!homesConfig.contains(player.getName() + "." + homeName)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("delhome-notexist")));
            return true;
        }

        homesConfig.set(player.getName() + "." + homeName, null);
        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("delhome-removed")));
        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private void createHomesFile() {
        File storageDir = new File(plugin.getDataFolder(), "storage");
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }

        homesFile = new File(storageDir, "Homes.yml");
        if (!homesFile.exists()) {
            try {
                homesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        homesConfig = YamlConfiguration.loadConfiguration(homesFile);
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        homesConfig = YamlConfiguration.loadConfiguration(homesFile); // Ricarica la configurazione
        if (!(sender instanceof Player)) {
            return null;
        }

        Player player = (Player) sender;
        if (args.length == 1) {
            List<String> homes = new ArrayList<>();
            ConfigurationSection section = homesConfig.getConfigurationSection(player.getName());
            if (section != null) {
                homes.addAll(section.getKeys(false));
            }
            return homes;
        }

        return null;
    }
}

