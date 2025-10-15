package dev.andrewd1.mc2source.event;

import dev.andrewd1.mc2source.vmf.VMFEntity;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class OnEntitySpawn implements Listener {
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) return;
        if (event.getEntity() instanceof LivingEntity entity) {
            entity.setAI(false);
            entity.setSilent(true);

            boolean found = false;
            for (VMFEntity vmfEntity : VMFEntity.values()) {
                if (vmfEntity.type == entity.getType()) {
                    found = true;
                    entity.setHealth(1);
                    entity.setGravity(false);
                    entity.setCollidable(false);
                    entity.customName(Component.text(vmfEntity.name().toLowerCase()));
                    break;
                }
            }

            if (!found) event.setCancelled(true);
        }
    }
}
