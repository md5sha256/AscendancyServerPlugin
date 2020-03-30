package com.gmail.andrewandy.ascendency.serverplugin.game.rune;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;

public enum Season1Runes implements PlayerSpecificRune {
    ;

    @Override
    public void applyTo(Player player) {

    }

    @Override
    public void clearFrom(Player player) {

    }

    @Override
    public void clearFrom(Entity entity) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        return null;
    }
}
