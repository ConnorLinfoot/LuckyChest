package com.connorlinfoot.luckychest.Listeners;

import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import java.io.IOException;

public class ChestOpen implements Listener {

    @EventHandler
    public void onInventory( InventoryOpenEvent e ) throws IOException {
        if( e.getInventory().getType() == InventoryType.CHEST ){
            Chest chest = (Chest) e.getInventory().getHolder();
            if( e.getInventory().getItem(0).getItemMeta().getDisplayName().equalsIgnoreCase("LuckyChest") ){
                e.getInventory().clear();
                com.connorlinfoot.luckychest.Handlers.Chest.get().populateChest(chest);
            }
        }
    }

}
