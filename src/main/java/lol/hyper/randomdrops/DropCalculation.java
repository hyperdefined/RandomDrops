/*
 * This file is part of RandomDrops.
 *
 * RandomDrops is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RandomDrops is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RandomDrops.  If not, see <https://www.gnu.org/licenses/>.
 */

package lol.hyper.randomdrops;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DropCalculation {

    private final RandomDrops randomDrops;
    public HashMap<Material, ArrayList<ItemStack>> itemDrops = new HashMap<>();
    public HashMap<EntityType, ArrayList<ItemStack>> mobDrops = new HashMap<>();

    public DropCalculation(RandomDrops randomDrops) {
        this.randomDrops = randomDrops;
    }

    /**
     * Start the process of calculation drops.
     *
     * @param material     The material being broken.
     * @param entityType   The mob being killed.
     * @param dropLocation The location of the block/mob.
     */
    public final void startDropCalculation(Material material, EntityType entityType, Location dropLocation) {
        // if we have the drop saved, don't calculate how many types
        if (itemDrops.containsKey(material)) {
            dropFromMemory(material, null, dropLocation);
            return;
        }

        if (mobDrops.containsKey(entityType)) {
            dropFromMemory(null, entityType, dropLocation);
            return;
        }

        int minTypes = randomDrops.config.getInt("drop-values.items-per-drop.min");
        int maxTypes = randomDrops.config.getInt("drop-values.items-per-drop.max");

        int randomTypes = new Random().nextInt(maxTypes - minTypes) + minTypes;

        ArrayList<ItemStack> itemsToDrop = new ArrayList<>();
        // this calculates how many types of items we want to drop
        for (int i = 0; i < randomTypes; i++) {
            // generate a random item
            ItemStack randomItem = new ItemStack(Material.values()[new Random().nextInt(Material.values().length)]);
            while (randomItem.getType() == Material.AIR && randomItem.getType() == Material.WATER && randomItem.getType() == Material.LAVA && !itemsToDrop.contains(randomItem)) {
                // if the material is illegal, reroll it
                // also checks for duplicate items
                randomItem = new ItemStack(Material.values()[new Random().nextInt(Material.values().length)]);
            }
            itemsToDrop.add(randomItem);
        }

        if (randomDrops.config.getBoolean("save-drops-in-memory")) {
            if (material == null) {
                mobDrops.put(entityType, itemsToDrop);
            }
            if (entityType == null) {
                itemDrops.put(material, itemsToDrop);
            }
        }
        dropItems(itemsToDrop, dropLocation);
    }

    /**
     * Sets the drops from memory.
     *
     * @param material   The material being broken.
     * @param entityType The mob being killed.
     * @param location   The location of the block/mob.
     */
    private void dropFromMemory(Material material, EntityType entityType, Location location) {
        // drops from a mob
        if (material == null) {
            dropItems(mobDrops.get(entityType), location);
        }
        // drops from a block
        if (entityType == null) {
            dropItems(itemDrops.get(material), location);
        }
    }

    /**
     * Drop the items.
     *
     * @param materials A list of materials to drop.
     * @param location  The location to drop.
     */
    private void dropItems(ArrayList<ItemStack> materials, Location location) {
        int minDrops = randomDrops.config.getInt("drop-values.item-quantity.min");
        int maxDrops = randomDrops.config.getInt("drop-values.item-quantity.max");
        for (ItemStack dropItem : materials) {
            int quantity = new Random().nextInt(maxDrops - minDrops) + minDrops;
            if (dropItem.getType() != Material.AIR) {
                dropItem.setAmount(1);
                for (int i = 0; i <= quantity; i++) {
                    // this is dirty, but it's a catch-all case for invalid drops
                    try {
                        location.getWorld().dropItemNaturally(location, dropItem);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }
}
