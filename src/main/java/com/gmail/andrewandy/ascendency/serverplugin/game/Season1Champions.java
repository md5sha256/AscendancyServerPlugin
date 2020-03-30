package com.gmail.andrewandy.ascendency.serverplugin.game;

import com.gmail.andrewandy.ascendency.lib.game.AscendencyChampions;
import com.gmail.andrewandy.ascendency.lib.game.data.IChampionData;
import com.gmail.andrewandy.ascendency.lib.game.data.game.ChampionDataImpl;
import com.gmail.andrewandy.ascendency.lib.packet.util.CommonUtils;
import com.gmail.andrewandy.ascendency.serverplugin.AscendencyServerPlugin;
import com.gmail.andrewandy.ascendency.serverplugin.GameRegistry;
import com.gmail.andrewandy.ascendency.serverplugin.game.rune.PlayerSpecificRune;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.data.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reprsents the instances of all champions in "Season 1"
 */
public enum Season1Champions implements Champion, DataSerializable {

    KNAVIS(),
    ASTSRICTION(),
    SOLACE(),
    VENGLIS(),
    BREEZY(),
    STYX(),
    CORSAIR(),
    CRYSTAL(),
    BELLA(),
    KYE(),
    HILDA(),
    BERTON(),
    NIKOLAS(),
    VULTURE(),
    TWIST();

    private final int version = 0;
    private final DataContainer dataContainer = new MemoryDataContainer().set(DataQuery.of("ordinal"), ordinal());
    private final PlayerSpecificRune[] runes;
    private final List<String> lore;

    public static final String LOAD = null; //Invoke to force classloader to load this class

    public static List<String> getLoreOf(String name) {
        ConfigurationNode node = AscendencyServerPlugin.getInstance().getSettings();
        node = node.getNode("Champions");
        List<? extends ConfigurationNode> nodes = node.getNode(name).getNode("lore").getChildrenList();
        return nodes.parallelStream().map(ConfigurationNode::getString).collect(Collectors.toList());
    }

    Season1Champions(PlayerSpecificRune... runes) {
        this.runes = runes;
        this.lore = getLoreOf(CommonUtils.capitalise(name().toLowerCase()));
        GameRegistry.INSTANCE.mapChampion(AscendencyChampions.valueOf(name()), this, false);
    }

    @Override
    public String getName() {
        return CommonUtils.capitalise("S1::" + name().toLowerCase());
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
    public IChampionData toData() {
        try {
            return new ChampionDataImpl(getName(), new File(""), lore);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public List<String> getLore() {
        return new ArrayList<>(lore);
    }
}
