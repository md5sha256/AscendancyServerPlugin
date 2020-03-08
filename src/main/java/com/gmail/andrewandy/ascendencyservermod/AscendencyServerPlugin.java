package com.gmail.andrewandy.ascendencyservermod;

import com.gmail.andrewandy.ascendencyservermod.io.MessageBroker;
import com.gmail.andrewandy.ascendencyservermod.util.Common;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

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

    private static AscendencyServerPlugin instance;
    private MessageBroker messageBroker;

    public static AscendencyServerPlugin getInstance() {
        return instance;
    }

    @Inject
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    @Listener(order = Order.DEFAULT)
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        Common.setup();
        Common.setPrefix("[CustomServerMod] ");
        loadMessageBroker();
    }

    @Listener(order = Order.DEFAULT)
    public void onServerStop(GameStoppedServerEvent event) {
        instance = null;
        logger.info("[Custom Server Mod] Disabling message broker & terminating connections.");
        messageBroker.stop();
        logger.info("[Custom Server Mod] Goodbye! Plugin has been disabled.");
    }

    public void loadMessageBroker() {
        messageBroker = new MessageBroker();
    }

    public MessageBroker getMessageBroker() {
        return messageBroker;
    }
}
