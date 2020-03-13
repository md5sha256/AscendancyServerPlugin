package com.gmail.andrewandy.ascendencyserverplugin.game;

import com.gmail.andrewandy.ascendencyserverplugin.game.gameclass.GameClass;
import com.gmail.andrewandy.ascendencyserverplugin.game.rune.PlayerSpecificRune;
import com.gmail.andrewandy.ascendencyserverplugin.util.Common;
import org.spongepowered.api.data.*;

/**
 * Reprsents the instances of all champions in "Season 1"
 */
public enum Season1Champions implements Champion, DataSerializable {

    ;

    private final int version = 0;
    private final DataContainer dataContainer = new MemoryDataContainer().set(DataQuery.of("ordinal"), ordinal());
    private final GameClass gameClass;
    private PlayerSpecificRune[] runes = new PlayerSpecificRune[0];

    Season1Champions(GameClass gameClass, PlayerSpecificRune... runes) {
        this.gameClass = gameClass;
        this.runes = runes;
    }

    @Override
    public String getName() {
        return Common.capitalise(name().toLowerCase());
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

    @Override
    public GameClass getGameClass() {
        return gameClass;
    }
}
