package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GMSpecificCommands implements CommandExecutor {

    public GMSpecificCommands(NovaCore novaCore) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        GameMode gameMode = null;
        String permission = null;

        switch (command.getName().toLowerCase()) {
            case "gms":
                gameMode = GameMode.SURVIVAL;
                permission = "novacore.gm.survival";
                break;
            case "gmc":
                gameMode = GameMode.CREATIVE;
                permission = "novacore.gm.creative";
                break;
            case "gmsp":
                gameMode = GameMode.SPECTATOR;
                permission = "novacore.gm.spectator";
                break;
            case "gma":
                gameMode = GameMode.ADVENTURE;
                permission = "novacore.gm.adventure";
                break;
        }

        if (gameMode != null && permission != null) {
            if (!player.hasPermission(permission)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("no-permission")));
                return true;
            }
            player.setGameMode(gameMode);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', NovaCore.messages.getMessage("gm-specific")) + " " + gameMode.name());
        }

        return true;
    }
}
