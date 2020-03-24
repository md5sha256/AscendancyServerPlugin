package com.gmail.andrewandy.ascendency.serverplugin.util.keybind;

import com.gmail.andrewandy.ascendency.lib.packet.keybind.AscendencyKey;
import com.gmail.andrewandy.ascendency.serverplugin.AscendencyServerPlugin;
import com.gmail.andrewandy.ascendency.serverplugin.util.Common;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Objects;
import java.util.logging.Level;

public enum ActiveKeyHandler implements KeyBindHandler {

    INSTANCE;

    private String scoreboard;
    private String team;

    ActiveKeyHandler() {
    }

    public void loadSettings() {
        ConfigurationNode node = AscendencyServerPlugin.getInstance().getSettings();
        node = node.getNode("KeyBinding");
        if (node == null) {
            Common.log(Level.INFO, "&b[Key Binds] Unable to find settings for ActiveKeyHandler.");
            return;
        }
        scoreboard = node.getNode("ScoreboardName").getString();
        team = node.getNode("ScoreboardTeam").getString();
    }

    @Override
    public AscendencyKey getTargetKey() {
        return AscendencyKey.ACTIVE_KEY;
    }

    @Override
    public void onKeyPress(Player player) {
        String command = "scoreboard " + scoreboard + "set " + Objects.requireNonNull(player).getName() + "to " + team;
        Sponge.getServer().getConsole().sendMessage(Text.of(command));
    }

    @Override
    public void onKeyRelease(Player player) {
        String command = "scoreboard " + scoreboard + "reset " + Objects.requireNonNull(player).getName();
        Sponge.getServer().getConsole().sendMessage(Text.of(command));
    }
}
