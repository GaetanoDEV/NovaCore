package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
import java.util.Objects;

public class HomeCommand implements CommandExecutor, TabCompleter {
    private final Plugin plugin;
    private FileConfiguration homesConfig;
    private File homesFile;

    public HomeCommand(Plugin plugin) {
        this.plugin = plugin;
        createHomesFile();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        homesConfig = YamlConfiguration.loadConfiguration(homesFile); // Ricarica il file di configurazione
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("novacore.command.home")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("home-homename")));
            return true;
        }

        String homeName = args[0];

        if (!homesConfig.contains(player.getName() + "." + homeName)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("home-notexist")));
            return true;
        }

        World world = plugin.getServer().getWorld(Objects.requireNonNull(homesConfig.getString(player.getName() + "." + homeName + ".world")));
        double x = homesConfig.getDouble(player.getName() + "." + homeName + ".x");
        double y = homesConfig.getDouble(player.getName() + "." + homeName + ".y");
        double z = homesConfig.getDouble(player.getName() + "." + homeName + ".z");

        Location homeLocation = new Location(world, x, y, z);
        player.teleportAsync(homeLocation);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("home-teleported")));

        return true;
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
            if (homesConfig.getConfigurationSection(player.getName()) != null) {
                homes.addAll(homesConfig.getConfigurationSection(player.getName()).getKeys(false));
            }
            return homes;
        }

        return null;
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
