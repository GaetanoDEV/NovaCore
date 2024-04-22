package it.gaetanodev.novacore.Events;

import it.gaetanodev.novacore.Commands.BackCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    private final BackCommand backCommand;

    public PlayerDeath(BackCommand backCommand) {
        this.backCommand = backCommand;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        backCommand.saveBackLocation(event.getEntity());
    }
}

