package com.gmail.andrewandy.ascendencyserverplugin.io.packet.data;

import com.gmail.andrewandy.ascendencyserverplugin.io.packet.AscendencyPacket;

public abstract class DataRequestPacket extends AscendencyPacket {

    private byte[] requestMessage;

    public DataRequestPacket() {
    }

    public DataRequestPacket(byte[] requestMessage) {
        this.requestMessage = requestMessage;
    }

    public byte[] getRequestMessage() {
        return requestMessage;
    }

    protected void setRequestMessage(byte[] message) {
        this.requestMessage = message;
    }

}
