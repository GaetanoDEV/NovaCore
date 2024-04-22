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

public class SetHomeCommand implements CommandExecutor {
    private final Plugin plugin;
    private FileConfiguration homesConfig;
    private File homesFile;

    public SetHomeCommand(Plugin plugin) {
        this.plugin = plugin;
        createHomesFile();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        homesConfig = YamlConfiguration.loadConfiguration(homesFile); // Ricarica il file di configurazione

        Player player = (Player) sender;

        if (!player.hasPermission("novacore.command.sethome")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("sethome-specificname")));
            return true;
        }

        String homeName = args[0];
        int maxHomes = player.hasPermission("novacore.command.sethome.vip") ? plugin.getConfig().getInt("VipPlayerHomes") : plugin.getConfig().getInt("DefaultPlayerHomes");

        if (homesConfig.getConfigurationSection(player.getName()) != null && homesConfig.getConfigurationSection(player.getName()).getKeys(false).size() >= maxHomes) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("sethome-maxreached")));
            return true;
        }

        if (homesConfig.contains(player.getName() + "." + homeName)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("sethome-alreadyexist")));
            return true;
        }

        homesConfig.set(player.getName() + "." + homeName + ".world", player.getLocation().getWorld().getName());
        homesConfig.set(player.getName() + "." + homeName + ".x", player.getLocation().getX());
        homesConfig.set(player.getName() + "." + homeName + ".y", player.getLocation().getY());
        homesConfig.set(player.getName() + "." + homeName + ".z", player.getLocation().getZ());
        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("sethome-set")));

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
}
