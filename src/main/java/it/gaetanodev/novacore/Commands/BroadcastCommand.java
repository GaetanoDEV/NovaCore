package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BroadcastCommand implements CommandExecutor {

    public BroadcastCommand(NovaCore novaCore) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!player.hasPermission("novacore.command.broadcast")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("no-permission"))));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("broadcast-nomessage"))));
            return true;
        }

        String message = String.join(" ", args);
        for (Player receiver : Bukkit.getOnlinePlayers()) {
            receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(NovaCore.messages.getMessage("broadcast-prefix"))) + ChatColor.translateAlternateColorCodes('&', message));

        }
        return true;
    }
}
