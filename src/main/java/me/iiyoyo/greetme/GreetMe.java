package me.iiyoyo.greetme;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

public class GreetMe extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN + "GreetMe is enabled");
        getLogger().info(ChatColor.GREEN + "Free Palestine");
        getLogger().info(ChatColor.YELLOW + "Free ukraine");

        // Save default config values (generates the config file :_) )
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        // register the greetme command with the tabcompleter
        getCommand("greetme").setExecutor(new commands(this));
        getCommand("greetme").setTabCompleter(new TabCompleter());

        // Register event listener
        GreetMe plugin = this;
        getServer().getPluginManager().registerEvents(new eventshandler(plugin), this);
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "GreetMe is disabled!");
        getLogger().info(ChatColor.GREEN + "Free Palestine");
        getLogger().info(ChatColor.YELLOW + "Free ukraine");

    }
}