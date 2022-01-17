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

package lol.hyper.randomdrops.events;

import lol.hyper.randomdrops.RandomDrops;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    private final RandomDrops randomDrops;

    public BlockBreak(RandomDrops randomDrops) {
        this.randomDrops = randomDrops;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        // only check if it's enabled
        if (!randomDrops.config.getBoolean("enabled-drops.block-break")) {
            return;
        }

        Material material = event.getBlock().getType();
        Location location = event.getBlock().getLocation();
        event.setDropItems(false);
        randomDrops.dropCalculation.startDropCalculation(material, null, location);
    }
}
