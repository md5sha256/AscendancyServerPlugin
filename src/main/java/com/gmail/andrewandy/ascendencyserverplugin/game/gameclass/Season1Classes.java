package com.gmail.andrewandy.ascendencyserverplugin.game.gameclass;

import com.gmail.andrewandy.ascendencyserverplugin.AscendencyServerPlugin;
import com.gmail.andrewandy.ascendencyserverplugin.game.rune.Rune;
import com.gmail.andrewandy.ascendencyserverplugin.util.Common;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.item.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public enum Season1Classes implements GameClass {
    ;

    private final int version = 0;
    private final int maxLevel;
    private final List<Inventory.Builder> levelInventory;
    private final DataContainer dataContainer = new MemoryDataContainer().set(DataQuery.of("ordinal"), ordinal());
    private List<String> levelPermission;

    Season1Classes(int maxLevel, List<Inventory.Builder> inventories, Rune... runes) {
        this(maxLevel, new ArrayList<>(), inventories, runes);
    }


    Season1Classes(int maxLevel, List<String> permissionsPerLevel, List<Inventory.Builder> inventories, Rune... runes) {
        assert permissionsPerLevel.size() <= maxLevel && inventories.size() <= maxLevel && maxLevel > 0;
        this.levelPermission = permissionsPerLevel;
        this.levelInventory = inventories;
        this.maxLevel = maxLevel;
    }

    public void setPermission(int level, String permission) {
        validateLevel(level);
        if (level > levelPermission.size()) {
            levelPermission.add(level, permission);
        } else {
            levelPermission.set(level, permission);
        }
    }

    public void setInventory(int level, Inventory.Builder inventory) {
        validateLevel(level);
        level = level - 1; //Lists start at index 0.
        if (level > levelInventory.size()) {
            levelInventory.add(level, inventory);
        } else {
            levelInventory.set(level, inventory);
        }
    }

    private void validateLevel(int level) {
        if (level > maxLevel || level < 0) {
            throw new IllegalArgumentException("Level out of bounds");
        }
    }

    @Override
    public String getName() {
        return Common.capitalise(name().toLowerCase());
    }

    @Override
    public Inventory getInventoryTemplate(int level) {
        return Inventory.builder().build(AscendencyServerPlugin.getInstance()); //Clone
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
