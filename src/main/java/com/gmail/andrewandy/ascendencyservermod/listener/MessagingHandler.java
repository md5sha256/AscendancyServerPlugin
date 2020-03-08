package com.gmail.andrewandy.ascendencyservermod.listener;

import com.gmail.andrewandy.ascendencyservermod.AscendencyServerPlugin;
import com.gmail.andrewandy.ascendencyservermod.util.Common;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Represents the listener which integrates with {@link com.gmail.andrewandy.ascendencyservermod.io.MessageBroker}
 */
public class MessagingHandler {

    @Listener(order = Order.LAST)
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        Player player = event.getTargetEntity();
        Common.log(Level.FINE, "Player joined! Assigning a port now...");
        int port = AscendencyServerPlugin.getInstance().getMessageBroker().assignPortFor(player.getUniqueId());
        Common.log(Level.FINE, "Assigned port " + port + " to player " + player.getName());
    }

    @Listener(order = Order.LAST)
    public void onPlayerLeave(ClientConnectionEvent.Disconnect event) {
        Player player = event.getTargetEntity();
        try {
            AscendencyServerPlugin.getInstance().getMessageBroker().unregister(player.getUniqueId());
            Common.log(Level.FINE, "Player left. Unregistering...");
        } catch (IOException e) {
            Common.log(Level.SEVERE, "Unable to unregister player: " + player.getName());
            e.printStackTrace();
        }
    }
}
