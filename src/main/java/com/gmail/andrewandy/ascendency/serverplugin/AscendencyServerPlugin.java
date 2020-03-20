package com.gmail.andrewandy.ascendency.serverplugin;

import com.gmail.andrewandy.ascendency.lib.packet.util.CommonUtils;
import com.gmail.andrewandy.ascendency.serverplugin.io.SpongeAscendencyPacketHandler;
import com.gmail.andrewandy.ascendency.serverplugin.matchmaking.MatchMakingService;
import com.gmail.andrewandy.ascendency.serverplugin.matchmaking.draftpick.DraftPickMatch;
import com.gmail.andrewandy.ascendency.serverplugin.matchmaking.match.SimplePlayerMatchManager;
import com.gmail.andrewandy.ascendency.serverplugin.util.Common;
import com.gmail.andrewandy.ascendency.serverplugin.util.ForceLoadChunks;
import com.gmail.andrewandy.ascendency.serverplugin.util.YamlLoader;
import com.google.inject.Inject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.network.ChannelBinding;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@Plugin(
        id = "ascendencyserverplugin",
        name = "AscendencyServerPlugin",
        version = "${project.version}",
        description = "Ascendency Server Plugin",
        authors = {
                "andrewandy"
        }
)
public class AscendencyServerPlugin {

    private static final String DEFAULT_NETWORK_CHANNEL_NAME = "ASCENDENCY_DEFAULT_CHANNEL";
    private static AscendencyServerPlugin instance;
    private final SpongeAscendencyPacketHandler DEFAULT_HANDLER = new SpongeAscendencyPacketHandler();
    private MatchMakingService<DraftPickMatch> matchMatchMakingService;


    @Inject
    @ConfigDir(sharedRoot = true)
    private File dataFolder;
    private YAMLConfigurationLoader configurationLoader;

    private ChannelBinding.RawDataChannel DEFAULT_NETWORK_CHANNEL;

    @Inject
    private Logger logger;

    @Inject
    public AscendencyServerPlugin() {
    }

    public static AscendencyServerPlugin getInstance() {
        return instance;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public ConfigurationNode getSettings() {
        try {
            return configurationLoader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public Logger getLogger() {
        return logger;
    }

    @Listener(order = Order.DEFAULT)
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        Common.setup();
        Common.setPrefix("[CustomServerMod]");
        setupIO();
        loadSettings();
        ForceLoadChunks.getInstance().loadSettings(); //Register the force event handler.
        SimplePlayerMatchManager.enableManager();
        loadMatchMaking(); //Load after the player match manager.
        Common.log(Level.INFO, "Plugin enabled!");
    }

    @Listener(order = Order.DEFAULT)
    public void onServerStop(GameStoppedServerEvent event) {
        SimplePlayerMatchManager.disableManager();
        unregisterIO();
        Common.log(Level.INFO, "Goodbye! Plugin has been disabled.");
        if (matchMatchMakingService != null) {
            matchMatchMakingService.clearQueue();
        }
        instance = null;
    }

    @Listener(order = Order.DEFAULT)
    public void onServerReload(GameReloadEvent event) {
        loadSettings();
    }

    private void setupIO() {
        Common.log(Level.INFO, "Loading Network Channel...");
        if (!Sponge.getChannelRegistrar().isChannelAvailable(DEFAULT_NETWORK_CHANNEL_NAME) && DEFAULT_NETWORK_CHANNEL == null) {
            throw new IllegalStateException("Unable to obtain a default channel! The default name is occupied!");
        }
        ChannelBinding.RawDataChannel channel = Sponge.getChannelRegistrar().getOrCreateRaw(instance, DEFAULT_NETWORK_CHANNEL_NAME);
        channel.removeListener(DEFAULT_HANDLER);
        channel.addListener(DEFAULT_HANDLER);
        if (channel != DEFAULT_NETWORK_CHANNEL) {
            if (DEFAULT_NETWORK_CHANNEL != null) {
                Common.log(Level.INFO, "Unbinding old channel...");
                Sponge.getChannelRegistrar().unbindChannel(DEFAULT_NETWORK_CHANNEL);
            }
            DEFAULT_NETWORK_CHANNEL = channel;
            Common.log(Level.INFO, "Default channel changed.");
            //If they aren't the same, then unbind the old one.
        }
        Common.log(Level.INFO, "Loading complete.");
    }

    public void loadSettings() {
        Common.log(Level.INFO, "&bLoading settings from disk...");
        long time = System.currentTimeMillis();
        configurationLoader = new YamlLoader("settings.yml").getLoader();
        Common.log(Level.INFO, "&aLoad complete! Took " + (System.currentTimeMillis() - time) + "ms.");
    }

    private void unregisterIO() {
        Common.log(Level.INFO, "Disabling Network Channel...");
        if (DEFAULT_NETWORK_CHANNEL != null) {
            DEFAULT_NETWORK_CHANNEL.removeListener(DEFAULT_HANDLER);
            Sponge.getChannelRegistrar().unbindChannel(DEFAULT_NETWORK_CHANNEL);
        }
        DEFAULT_NETWORK_CHANNEL = null;
    }

    private void loadMatchMaking() throws IllegalArgumentException {
        ConfigurationNode node = getSettings();
        node = node.getNode("MatchMaking");
        if (node == null) {
            throw new IllegalArgumentException("Invalid settings detected! Missing MatchMaking section!");
        }
        int min = node.getNode("Min-Players").getInt();
        int max = node.getNode("Max-Players").getInt();
        String modeEnumName = node.getNode("Mode").getString().toUpperCase();
        MatchMakingService.MatchMakingMode mode = MatchMakingService.MatchMakingMode.valueOf(modeEnumName);
        MatchMakingService<DraftPickMatch> service = new MatchMakingService<>(min, max, () -> new DraftPickMatch(Math.round(max / 2f))).setMatchMakingMode(mode);
        if (matchMatchMakingService != null) {
            for (Player player : matchMatchMakingService.clearQueue()) {
                service.addToQueue(player);
            }
        }
        Common.log(Level.INFO, "7a[Matchmaking] Loaded: Max-Players = " + max + ", Min-Players = " + min + ", Mode = " + CommonUtils.capitalise(mode.name().toLowerCase()));
        matchMatchMakingService = service;
    }
}
