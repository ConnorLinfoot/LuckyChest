package com.connorlinfoot.luckychest;

import com.connorlinfoot.luckychest.Handlers.Chest;
import com.connorlinfoot.luckychest.Listeners.BlockBreak;
import com.connorlinfoot.luckychest.Listeners.ChestOpen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Main extends JavaPlugin implements Listener {
    private static Plugin instance;

    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        Server server = getServer();
        ConsoleCommandSender console = server.getConsoleSender();

        console.sendMessage(ChatColor.GREEN + "============ LuckyChest ============");
        console.sendMessage(ChatColor.GREEN + "========== VERSION: 1.2.1 ==========");
        console.sendMessage(ChatColor.GREEN + "======== BY CONNOR LINFOOT! ========");

        if( getConfig().getInt("Config Version") == 0 ){
            // Run setup
            console.sendMessage("Running setup");
            setup();
            console.sendMessage("Setup complete");
        }

        Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new ChestOpen(), this);

        Chest.loadChestItems();


    }

    public void onDisable() {
        getLogger().info(getDescription().getName() + " has been disabled!");
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private void setup(){
        File chestFile = new File(getDataFolder(), "chest.yml");

        if( !chestFile.exists() ){
            try {
                chestFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        getConfig().set("Config Version",1);
        saveConfig();

    }
}
