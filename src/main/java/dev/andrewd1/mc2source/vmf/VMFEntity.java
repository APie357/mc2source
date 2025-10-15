package dev.andrewd1.mc2source.vmf;

import org.bukkit.entity.EntityType;

public enum VMFEntity {
    INFO_PLAYER_DEATHMATCH(EntityType.VILLAGER),

    ;

    public final EntityType type;

    VMFEntity(EntityType type) {
        this.type = type;
    }
}
