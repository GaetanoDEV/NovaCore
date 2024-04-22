package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SetSpawnCommand implements CommandExecutor {
    private JavaPlugin plugin;

    public SetSpawnCommand(NovaCore novaCore) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (!player.hasPermission("novacore.admin")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("no-permission"))));
                return true;
            }
            // Salva le coordinate del giocatore nel file di configurazione
            NovaCore.plugin.reloadConfig();
            NovaCore.plugin.getConfig().set("SpawnLocation.world", player.getWorld().getName());
            NovaCore.plugin.getConfig().set("SpawnLocation.x", player.getLocation().getX());
            NovaCore.plugin.getConfig().set("SpawnLocation.y", player.getLocation().getY());
            NovaCore.plugin.getConfig().set("SpawnLocation.z", player.getLocation().getZ());
            NovaCore.plugin.saveConfig();


            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("setspawn-message"))));
            return true;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("setspawn-message"))));
            return false;
        }
    }
}
