package lol.hyper.randomdrops.events;

import lol.hyper.randomdrops.RandomDrops;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobDeath implements Listener {

    private final RandomDrops randomDrops;

    public MobDeath(RandomDrops randomDrops) {
        this.randomDrops = randomDrops;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        // only check if it's enabled
        if (!randomDrops.config.getBoolean("enabled-drops.mob-kill")) {
            return;
        }
        // ignore player deaths
        if (event.getEntity() instanceof Player) {
            return;
        }
        EntityType entityType = event.getEntity().getType();
        Location location = event.getEntity().getEyeLocation();
        event.getDrops().clear();
        randomDrops.dropCalculation.startDropCalculation(null, entityType, location);
    }
}
