package it.gaetanodev.novacore.Commands;

import it.gaetanodev.novacore.NovaCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

import static it.gaetanodev.novacore.NovaCore.messages;

public class NovaCoreCommand implements CommandExecutor {
    private final Plugin plugin;

    public NovaCoreCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (!player.hasPermission("novacore.admin")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messages.getMessage("no-permission"))));
            return true;
        }

            if (args.length == 0) {
                sender.sendMessage(ChatColor.AQUA + "-----------------------------------");
                sender.sendMessage(" ");
                sender.sendMessage("      " + ChatColor.AQUA + "Nova" + ChatColor.WHITE + "Core" + " " + ChatColor.GREEN + "v" + NovaCore.plugin.getConfig().getString("version"));
                sender.sendMessage("     " + ChatColor.GRAY + "by" + ChatColor.GREEN + " " + "Gaethanos__");
                sender.sendMessage(" ");
                sender.sendMessage(ChatColor.GREEN + "/novacore reload" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messages.getMessage("help-reload"))));
                sender.sendMessage(ChatColor.GREEN + "/setspawn" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messages.getMessage("help-setspawn"))));
                sender.sendMessage(ChatColor.GREEN + "/spawn" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messages.getMessage("help-spawn"))));
                sender.sendMessage(ChatColor.GREEN + "/mutechat" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messages.getMessage("help-mutechat"))));
                sender.sendMessage(ChatColor.GREEN + "/broadcast" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messages.getMessage("help-broadcast"))));
                sender.sendMessage(ChatColor.GREEN + "/back" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', messages.getMessage("help-back")));
                sender.sendMessage(ChatColor.GREEN + "/sethome" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', messages.getMessage("help-sethome")));
                sender.sendMessage(ChatColor.GREEN + "/delhome" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', messages.getMessage("help-delhome")));
                sender.sendMessage(ChatColor.GREEN + "/home" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', messages.getMessage("help-home")));
                sender.sendMessage(ChatColor.GREEN + "/gm" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', messages.getMessage("help-gm")));
                sender.sendMessage(ChatColor.GREEN + "/fly" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', messages.getMessage("help-fly")));
                sender.sendMessage(ChatColor.GREEN + "/warp" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', messages.getMessage("help-warp")));
                sender.sendMessage(ChatColor.GREEN + "/setwarp" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', messages.getMessage("help-setwarp")));
                sender.sendMessage(ChatColor.GREEN + "/delwarp" + ChatColor.GRAY + " - " + ChatColor.translateAlternateColorCodes('&', messages.getMessage("help-delwarp")));
                sender.sendMessage("");
                sender.sendMessage(ChatColor.AQUA + "-----------------------------------");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                messages.reloadMessages();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messages.getMessage("reload"))));
                return true;
            }

            // ALTRI SOTTO-COMANDI

            return false;
        }

    }

