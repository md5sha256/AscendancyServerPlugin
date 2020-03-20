package com.gmail.andrewandy.ascendency.serverplugin.game;

import com.gmail.andrewandy.ascendency.lib.packet.util.CommonUtils;
import com.gmail.andrewandy.ascendency.serverplugin.game.rune.PlayerSpecificRune;
import org.spongepowered.api.data.*;

/**
 * Reprsents the instances of all champions in "Season 1"
 */
public enum Season1Champions implements Champion, DataSerializable {

    ;

    private final int version = 0;
    private final DataContainer dataContainer = new MemoryDataContainer().set(DataQuery.of("ordinal"), ordinal());
    private PlayerSpecificRune[] runes;

    Season1Champions(PlayerSpecificRune... runes) {
        this.runes = runes;
    }

    @Override
    public String getName() {
        return CommonUtils.capitalise(name().toLowerCase());
    }

    @Override
    public PlayerSpecificRune[] getRunes() {
        return runes;
    }

    @Override
    public int getContentVersion() {
        return version;
    }

    @Override
    public DataContainer toContainer() {
        return dataContainer.copy(DataView.SafetyMode.ALL_DATA_CLONED);
    }
}
