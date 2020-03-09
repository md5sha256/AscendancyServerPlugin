package com.gmail.andrewandy.ascendencyserverplugin;

import com.gmail.andrewandy.ascendencyserverplugin.util.Common;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

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

    private static AscendencyServerPlugin instance;

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
        Common.log(Level.INFO, "Plugin enabled!");
    }

    @Listener(order = Order.DEFAULT)
    public void onServerStop(GameStoppedServerEvent event) {
        instance = null;
        Common.log(Level.INFO, "Goodbye! Plugin has been disabled.");
    }
}
