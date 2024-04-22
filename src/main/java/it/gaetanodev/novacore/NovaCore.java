package it.gaetanodev.novacore;

import it.gaetanodev.novacore.Commands.*;
import it.gaetanodev.novacore.Events.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;


public final class NovaCore extends JavaPlugin {
    public static NovaCore instance;
    public static NovaCore plugin;
    public static Messages messages;


    @Override
    public void onEnable() {
        plugin = this;
        // COMANDI VARI
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("novacore").setExecutor(new NovaCoreCommand(this));
        getCommand("broadcast").setExecutor(new BroadcastCommand(this));
        getCommand("sethome").setExecutor(new SetHomeCommand(this));
        getCommand("delhome").setExecutor(new DelHomeCommand(this));
        getCommand("fly").setExecutor(new FlyCommand(this));
        getCommand("setwarp").setExecutor(new SetWarpCommand(this));
        // GM SPECIFIC COMMANDS
        getCommand("gms").setExecutor(new GMSpecificCommands(this));
        getCommand("gmc").setExecutor(new GMSpecificCommands(this));
        getCommand("gmsp").setExecutor(new GMSpecificCommands(this));
        getCommand("gma").setExecutor(new GMSpecificCommands(this));

        // MUTECOMMAND - ISTANZA
        MuteChatCommand muteChatCommand = new MuteChatCommand(this);
        this.getCommand("mutechat").setExecutor(muteChatCommand);
        // BACK - ISTANZA
        BackCommand backCommand = new BackCommand(this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(backCommand, this), this); // BACK - QUIT EVENT
        this.getCommand("back").setExecutor(backCommand);
        // HOME - ISTANZA
        HomeCommand homeCommand = new HomeCommand(this);
        this.getCommand("home").setExecutor(homeCommand);
        this.getCommand("home").setTabCompleter(homeCommand);
        // DELHOME - ISTANZA
        DelHomeCommand delhomeCommand = new DelHomeCommand(this);
        this.getCommand("delhome").setExecutor(delhomeCommand);
        this.getCommand("delhome").setTabCompleter(delhomeCommand);
        // GM - ISTANZA
        GMCommand gmCommand = new GMCommand(this);
        this.getCommand("gm").setExecutor(gmCommand);
        this.getCommand("gm").setTabCompleter(gmCommand);
        // WARP - ISTANZA
        WarpCommand warpCommand = new WarpCommand(this);
        this.getCommand("warp").setExecutor(warpCommand);
        this.getCommand("warp").setTabCompleter(warpCommand);
        // DELWARP - ISTANZA
        DelWarpCommand delWarpCommand = new DelWarpCommand(this);
        this.getCommand("delwarp").setExecutor(delWarpCommand);
        this.getCommand("delwarp").setTabCompleter(delWarpCommand);


        // LISTENER
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(muteChatCommand), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(backCommand), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(this), this);

        // MESSAGGIO DI AVVIO
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "----------------------------");
        getServer().getConsoleSender().sendMessage(" ");
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "      " + "Nova" + ChatColor.WHITE + "Core");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "abilitato correttamente.");
        FileConfiguration config = getConfig(); // CONFIGURAZIONE
        String PluginVersion = config.getString("version");
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + " " + "v" + PluginVersion + " " + ChatColor.GRAY + "-" + " " + ChatColor.DARK_AQUA + "by Gaethanos__");
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA +"----------------------------");

        // CREA IL CONFIG.YML SE NON ESISTE
        saveDefaultConfig();
        // REGISTRA IL MESSAGES
        messages = new Messages(this);

    }


    @Override
    public void onDisable() {
        // MESSAGGIO DI SPEGNIMENTO
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "----------------------------");
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "      " + "Nova" + ChatColor.WHITE + "Core");
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "disabilitato correttamente.");
        getServer().getConsoleSender().sendMessage("");
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "----------------------------");
    }

}
