package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SpawnCommand implements CommandExecutor {

    private JavaPlugin plugin;

    public SpawnCommand(NovaCore novaCore) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;

            if (!player.hasPermission("novacore.command.spawn")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("no-permission"))));
                return true;
            }


            String worldName = NovaCore.plugin.getConfig().getString("SpawnLocation.world");
            double x = NovaCore.plugin.getConfig().getDouble("SpawnLocation.x");
            double y = NovaCore.plugin.getConfig().getDouble("SpawnLocation.y");
            double z = NovaCore.plugin.getConfig().getDouble("SpawnLocation.z");

            if (worldName == null || worldName.isEmpty()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("spawn-notset"))));

            }
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("spawnworld-notexist"))));
                return true;
            }

            Location spawnLocation = new Location(world, x, y, z);
            player.teleportAsync(spawnLocation);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("spawn-teleport"))));
            return true;
        }
        return true;
    }
}
