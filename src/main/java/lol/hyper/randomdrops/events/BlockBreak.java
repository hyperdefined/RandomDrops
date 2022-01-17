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
