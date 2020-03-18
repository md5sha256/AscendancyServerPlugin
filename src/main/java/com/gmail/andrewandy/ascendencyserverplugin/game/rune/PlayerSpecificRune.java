package com.gmail.andrewandy.ascendencyserverplugin.game.rune;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

public interface PlayerSpecificRune extends EntityRune {

    void applyTo(Player player);

    void clearFrom(Player player);

    @Override
    default void clearFrom(Object object) {
        if (!canApplyTo(object)) {
            return;
        }
        clearFrom((Player) object);
    }

    @Override
    default void applyTo(Entity entity) {
        if (!canApplyTo(entity)) {
            throw new UnsupportedOperationException();
        }
        applyTo((Player) entity);
    }

    @Override
    default boolean canApplyTo(Object object) {
        return object instanceof Player;
    }
}