package com.gmail.andrewandy.ascendencyserverplugin;

import com.gmail.andrewandy.ascendencyserverplugin.io.sponge.SpongeAscendencyPacketHandler;
import com.gmail.andrewandy.ascendencyserverplugin.util.Common;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.network.ChannelBinding;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
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
    @Inject
    @ConfigDir(sharedRoot = true)
    private File dataFolder;

    private ChannelBinding.RawDataChannel DEFAULT_NETWORK_CHANNEL;

    @Inject
    private Logger logger;

    public static AscendencyServerPlugin getInstance() {
        return instance;
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
        Common.log(Level.INFO, "Plugin enabled!");
    }

    @Listener(order = Order.DEFAULT)
    public void onServerStop(GameStoppedServerEvent event) {
        unregisterIO();
        Common.log(Level.INFO, "Goodbye! Plugin has been disabled.");
        instance = null;
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

    private void unregisterIO() {
        Common.log(Level.INFO, "Disabling Network Channel...");
        if (DEFAULT_NETWORK_CHANNEL != null) {
            DEFAULT_NETWORK_CHANNEL.removeListener(DEFAULT_HANDLER);
            Sponge.getChannelRegistrar().unbindChannel(DEFAULT_NETWORK_CHANNEL);
        }
        DEFAULT_NETWORK_CHANNEL = null;
    }
}
