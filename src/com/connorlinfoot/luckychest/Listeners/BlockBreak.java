package com.connorlinfoot.luckychest.Listeners;

import com.connorlinfoot.luckychest.Handlers.BlockCheck;
import com.connorlinfoot.luckychest.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.ContainerBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak( BlockBreakEvent e ) throws IOException {
        if( !e.getBlock().getWorld().getName().equalsIgnoreCase(Main.getInstance().getConfig().getString("World")) && !Main.getInstance().getConfig().getString("World").equalsIgnoreCase("all") ) return;
        if( e.getBlock().getType() == Material.CHEST ){
            ContainerBlock cb = (ContainerBlock) e.getBlock().getState();
            if( cb.getInventory().getItem(0) == null || cb.getInventory().getItem(0).getType() == Material.AIR ) return;
            if( cb.getInventory().getItem(0).getItemMeta().getDisplayName().equalsIgnoreCase("LuckyChest") ){
                cb.getInventory().clear();
                com.connorlinfoot.luckychest.Handlers.Chest.get().populateChest((org.bukkit.block.Chest) e.getBlock());
            }
            return;
        }

        if(BlockCheck.checkBlock(e.getBlock(), Material.CHEST)) return;

        boolean allow = false;
        if( Main.getInstance().getConfig().getBoolean("Use Perms") ) {
            if( e.getPlayer().hasPermission("luckychest.use") ) {
                allow = true;
            }
        } else allow = true;

        if( allow ) {
            Integer rand = Main.randInt(0, 100);

            if (rand <= Main.getInstance().getConfig().getInt("Chance of chest")) {
                // Spawn chest
                e.setCancelled(true);
                Block block = e.getBlock();
                block.setType(Material.CHEST);

                ItemStack is = new ItemStack(Material.SPONGE);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("LuckyChest");
                is.setItemMeta(im);
                ContainerBlock cb = (ContainerBlock) block.getState();
                cb.getInventory().addItem(is);
            }
        }
    }
}
