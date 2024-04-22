package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class GMCommand implements CommandExecutor, TabCompleter {

    public GMCommand(NovaCore novaCore) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("novacore.command.gm")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("gm-nospecific")));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "survival":
            case "s":
                if (!player.hasPermission("novacore.gm.survival")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
                    return true;
                }
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("gm-specific")) + " " + "SURVIVAL");
                break;
            case "creative":
            case "c":
                if (!player.hasPermission("novacore.gm.creative")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
                    return true;
                }
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("gm-specific")) + " " + "CREATIVE");
                break;
            case "spectator":
            case "sp":
                if (!player.hasPermission("novacore.gm.spectator")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
                    return true;
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("gm-specific")) + " " + "SPECTATATOR");
                break;
            case "adventure":
            case "a":
                if (!player.hasPermission("novacore.gm.adventure")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
                    return true;
                }
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("gm-specific")) + " " + "ADVENTURE");
                break;
            default:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("gm-nospecific")));
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("survival", "creative", "spectator", "adventure");
        }

        return null;
    }
}
