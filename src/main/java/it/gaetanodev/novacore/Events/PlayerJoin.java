package it.gaetanodev.novacore.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoin implements Listener {
    private final Plugin plugin;

    public PlayerJoin(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FileConfiguration config = plugin.getConfig();
        boolean spawnOnJoin = config.getBoolean("SpawnOnJoin");

        if (spawnOnJoin) {
            String worldName = config.getString("SpawnLocation.world");
            double x = config.getDouble("SpawnLocation.x");
            double y = config.getDouble("SpawnLocation.y");
            double z = config.getDouble("SpawnLocation.z");

            if (worldName == null || worldName.isEmpty()) {
                return;
            }

            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                return;
            }

            Location spawnLocation = new Location(world, x, y, z);
            Player player = event.getPlayer();
            player.teleportAsync(spawnLocation);
        }
    }
}