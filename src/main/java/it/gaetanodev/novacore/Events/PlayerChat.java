package it.gaetanodev.novacore.Events;

import it.gaetanodev.novacore.Commands.MuteChatCommand;
import it.gaetanodev.novacore.NovaCore;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class PlayerChat implements Listener {
    private final MuteChatCommand muteChatCommand;

    public PlayerChat(MuteChatCommand muteChatCommand) {
        this.muteChatCommand = muteChatCommand;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (muteChatCommand.isChatMuted() && !event.getPlayer().hasPermission("novacore.lockchat.bypass")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.plugin.getConfig().getString("chat-muted"))));
        }
    }
}
