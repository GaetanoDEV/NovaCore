package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MuteChatCommand implements CommandExecutor {
    private boolean chatMuted = false;

    public MuteChatCommand(NovaCore novaCore) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!player.hasPermission("novacore.lockchat.bypass")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("no-permission"))));
            return true;
        }

        chatMuted = !chatMuted;
        String message = chatMuted ? ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("chat-nowmuted"))) : ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("chat-unmuted")));
        for (Player reciver : Bukkit.getOnlinePlayers()) {
            reciver.sendMessage(message);
        }

        return true;
    }

    public boolean isChatMuted() {
        return chatMuted;
    }
}

