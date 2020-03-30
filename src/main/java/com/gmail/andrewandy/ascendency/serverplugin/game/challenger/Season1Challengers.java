package com.gmail.andrewandy.ascendency.serverplugin.game.challenger;

import com.gmail.andrewandy.ascendency.serverplugin.AscendencyServerPlugin;
import com.gmail.andrewandy.ascendency.serverplugin.game.challenger.Challenger;
import ninja.leaping.configurate.ConfigurationNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Reprsents the instances of all champions in "Season 1"
 */
public enum Season1Challengers {

    KNAVIS(Knavis.getInstance()),
    ASTSRICTION(null),
    SOLACE(null),
    VENGLIS(null),
    BREEZY(null);

    private final int version = 0;
    private final Challenger challengerObject;

    public static final String LOAD = null; //Invoke to force classloader to load this class

    public static List<String> getLoreOf(String name) {
        ConfigurationNode node = AscendencyServerPlugin.getInstance().getSettings();
        node = node.getNode("Champions");
        List<? extends ConfigurationNode> nodes = node.getNode(name).getNode("lore").getChildrenList();
        return nodes.parallelStream().map(ConfigurationNode::getString).collect(Collectors.toList());
    }

    Season1Challengers(Challenger challengerObject) {
        this.challengerObject = challengerObject;
    }

    public Challenger asChallenger() {
        return challengerObject;
    }
}
