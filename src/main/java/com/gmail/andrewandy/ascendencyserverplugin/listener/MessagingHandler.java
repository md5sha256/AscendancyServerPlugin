package com.gmail.andrewandy.ascendencyserverplugin.listener;

import com.gmail.andrewandy.ascendencyserverplugin.util.Common;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.util.logging.Level;

/**
 * Represents the listener which integrates with Ascendency packets.
 */
public class MessagingHandler {

    @Listener(order = Order.LAST)
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        Player player = event.getTargetEntity();
    }

    @Listener(order = Order.LAST)
    public void onPlayerLeave(ClientConnectionEvent.Disconnect event) {
        Player player = event.getTargetEntity();
    }
}
