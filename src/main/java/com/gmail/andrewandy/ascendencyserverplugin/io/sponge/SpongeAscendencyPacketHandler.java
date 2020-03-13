package com.gmail.andrewandy.ascendencyserverplugin.io.sponge;

import com.gmail.andrewandy.ascendencyserverplugin.io.packet.AscendencyPacket;
import com.gmail.andrewandy.ascendencyserverplugin.io.packet.AscendencyPacketHandler;
import org.spongepowered.api.Platform;
import org.spongepowered.api.network.ChannelBuf;
import org.spongepowered.api.network.RawDataListener;
import org.spongepowered.api.network.RemoteConnection;

/**
 * Server Side ONLY! DO not shade into the client.
 * This handles the raw client packets on the sponge server.
 */
public class SpongeAscendencyPacketHandler implements RawDataListener {

    @Override
    public void handlePayload(ChannelBuf data, RemoteConnection connection, Platform.Type side) {
        try {
            int len = data.readInteger();
            String rawClazz = new String(data.readBytes(data.readerIndex(), len));
            Class<?> clazz = Class.forName(rawClazz);
            if (!AscendencyPacket.class.isAssignableFrom(clazz)) {
                return;
            }
            Class<? extends AscendencyPacket> casted = clazz.asSubclass(AscendencyPacket.class);
            AscendencyPacket packet = casted.newInstance();
            packet.fromBytes(data.readByteArray());
            AscendencyPacketHandler.getInstance().onMessage(packet);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new IllegalStateException("Unable to interact with packet!", e);
        }
    }
}
