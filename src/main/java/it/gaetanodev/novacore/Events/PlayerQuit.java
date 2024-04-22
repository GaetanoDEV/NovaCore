package it.gaetanodev.novacore.Events;

import it.gaetanodev.novacore.Commands.BackCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerQuit implements Listener {
    private final BackCommand backCommand;
    private final Plugin plugin;

    public PlayerQuit(BackCommand backCommand, Plugin plugin) {
        this.backCommand = backCommand;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        FileConfiguration config = plugin.getConfig();
        boolean saveBackLocationOnQuit = config.getBoolean("SaveBackLocationOnQuit");

        if (saveBackLocationOnQuit) {
            backCommand.saveBackLocation(event.getPlayer());
        }
    }
}
