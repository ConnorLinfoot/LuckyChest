package com.connorlinfoot.luckychest.Handlers;

import com.connorlinfoot.luckychest.Main;
import com.google.common.collect.Lists;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;
import java.util.Random;

public class Chest {

    private static Chest chest;
    private static final List<ChestItem> chestItemList = Lists.newArrayList();
    private final Random random = new Random();

    public static void loadChestItems(){
        chestItemList.clear();
        File chestFile = new File(Main.getInstance().getDataFolder(), "chest.yml");

        if (!chestFile.exists()) {
            return;
        }

        if (chestFile.exists()) {
            FileConfiguration storage = YamlConfiguration.loadConfiguration(chestFile);

            if (storage.contains("items")) {
                for (String item : storage.getStringList("items")) {
                    String[] itemData = item.split(" ", 2);

                    int chance = Integer.parseInt(itemData[0]);
                    ItemStack itemStack = ItemUtils.parseItem(itemData[1].split(" "));

                    if (itemStack != null) {
                        chestItemList.add(new ChestItem(itemStack, chance));
                    } else {
                        //LogUtils.log(Level.WARNING, getClass(), "Invalid item in chest: " + item);
                    }
                }
            }
        }
    }

    public void populateChest(org.bukkit.block.Chest chest) {
        Inventory inventory = chest.getBlockInventory();
        int added = 0;

        chest.getInventory().clear();

        for (ChestItem chestItem : chestItemList) {
            if (random.nextInt(100) + 1 <= chestItem.getChance()) {
                inventory.addItem(chestItem.getItem());

                if (added++ > inventory.getSize()) {
                    break;
                }
            }
        }
    }

    public static class ChestItem {

        private ItemStack item;
        private int chance;

        public ChestItem(ItemStack item, int chance) {
            this.item = item;
            this.chance = chance;
        }

        public ItemStack getItem() {
            return item;
        }

        public int getChance() {
            return chance;
        }
    }

    public static Chest get() {
        if (chest == null) {
            chest = new Chest();
        }

        return chest;
    }

}
