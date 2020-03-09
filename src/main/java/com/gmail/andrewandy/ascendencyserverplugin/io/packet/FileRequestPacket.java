package com.gmail.andrewandy.ascendencyserverplugin.io.packet;

import io.netty.buffer.ByteBuf;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Represents a packet to request for a file.
 */
public class FileRequestPacket extends AscendencyPacket {

    public static final String PROTOCOL_VERSION = "1";
    private static final String SPLITTER = "::";
    private Path filePath;

    public FileRequestPacket() {

    }

    public FileRequestPacket(FileRequestPacket other) {
        if (other == null) {
            return;
        }
        if (other.filePath != null) {
            this.filePath = Paths.get(other.filePath.toString());
        }
    }

    public FileRequestPacket(String filePath) {
        this(Paths.get(filePath));
    }

    public FileRequestPacket(Path path) {
        this.filePath = Objects.requireNonNull(path);
    }

    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        byte[] rawString = Objects.requireNonNull(buf).array();
        String str = new String(rawString);
        String[] split = str.split(SPLITTER);
        if (split.length < 3) {
            throw new IllegalArgumentException("Invalid buffer parsed!");
        }
        int index = 0;
        String classAsString = split[index++];
        String protocolVersion = split[index++];
        String filePath = split[index];
        try {
            Class<?> clazz = Class.forName(classAsString);
            if (!FileRequestPacket.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Invalid packet, not type of FileRequestPacket!");
            }
            if (!protocolVersion.equals(PROTOCOL_VERSION)) {
                //Convert.
            }
            this.filePath = Paths.get(filePath);
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException("Invalid packet, not type of FileRequestPacket!", ex);
        }

    }

    @Override
    public void toBytes(ByteBuf buf) {
        String str = getClass().getCanonicalName() + SPLITTER + PROTOCOL_VERSION + SPLITTER + filePath.toString();
        Objects.requireNonNull(buf).writeBytes(str.getBytes());
    }
}
