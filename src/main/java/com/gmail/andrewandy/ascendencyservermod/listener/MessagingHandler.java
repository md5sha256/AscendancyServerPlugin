package com.gmail.andrewandy.ascendencyservermod.listener;

import com.gmail.andrewandy.ascendencyservermod.AscendencyServerPlugin;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.io.IOException;

public class MessagingHandler  {

    @Listener(order = Order.LAST)
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        Player player = event.getTargetEntity();
        AscendencyServerPlugin.getInstance().getMessageBroker().assignPortFor(player.getUniqueId());
    }

    @Listener(order = Order.LAST)
    public void onPlayerLeave(ClientConnectionEvent.Disconnect event) {
        Player player = event.getTargetEntity();
        try {
            AscendencyServerPlugin.getInstance().getMessageBroker().unregister(player.getUniqueId());
        } catch (IOException e) {
            AscendencyServerPlugin.getInstance().getLogger().error("Unable to unregister player: " + player.getName());
            e.printStackTrace();
        }
    }
}
