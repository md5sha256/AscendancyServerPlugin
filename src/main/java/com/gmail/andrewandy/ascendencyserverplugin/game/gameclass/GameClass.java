package com.gmail.andrewandy.ascendencyserverplugin.game.gameclass;

import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.item.inventory.Inventory;

/**
 * Represents a game class which players can pick.
 */
public interface GameClass extends DataSerializable {

    String getName();

    Inventory getInventoryTemplate(int level);
}
