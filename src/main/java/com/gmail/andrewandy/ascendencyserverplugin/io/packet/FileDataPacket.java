package com.gmail.andrewandy.ascendencyserverplugin.io.packet;

import io.netty.buffer.ByteBuf;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a packet which holds data from a given file on the disk.
 */
public class FileDataPacket extends DataPacket {

    private static final String PROTOCOL_VERSION = "1";
    private static final String SPLITTER = "::";

    private String fileName;
    private long targetFileSize;


    public FileDataPacket() {
    }

    public FileDataPacket(File file) throws IOException {
        this(new FileInputStream(file), file.getName(), file.length());
    }

    public FileDataPacket(InputStream src, String fileName, long targetFileSize) throws IOException {
        super(src);
        this.fileName = fileName;
        if (targetFileSize < 0) {
            throw new IllegalArgumentException("Invalid file size!");
        }
        this.targetFileSize = targetFileSize;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int idLength = Objects.requireNonNull(buf).readInt();
        String identifier = new String(buf.readSlice(idLength).array());
        String[] arr = identifier.split(SPLITTER);
        if (arr.length < 2) {
            throw new IllegalArgumentException("Invalid identifier!");
        }
        try {
            Class<?> clazz = Class.forName(arr[0]);
            if (!FileDataPacket.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Packet identifier not type of FileDataPacket!");
            }
            String otherVersion = arr[1];
            if (!otherVersion.equals(PROTOCOL_VERSION)) {
                //Conversion
            }
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException("Packet identifier not type of FileDataPacket!");
        }
        buf.setIndex(idLength + 1, idLength + 1);
        int nameLength = buf.readInt();
        assert nameLength > 0;
        byte[] nameBytes = buf.readBytes(nameLength).slice().array();
        fileName = new String(nameBytes);
        targetFileSize = buf.readLong();
        int dataLen = buf.readInt();
        byte[] data = dataLen > 0 ? buf.readBytes(dataLen).slice().array() : new byte[0];
        super.setData(data);
        if (!fileSizeIsCorrect()) {
            throw new IllegalStateException("File size != target file size!");
        }
    }

    private boolean fileSizeIsCorrect() {
        File temp = null;
        try {
            temp = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
            try (FileOutputStream os = new FileOutputStream(temp)) {
                os.write(getData());
                return temp.length() == targetFileSize;
            }
        } catch (IOException ex) {
            return false;
        } finally {
            if (temp != null) {
                temp.delete();
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (fileName == null) {
            return;
        }
        byte[] nameBytes = fileName.getBytes();
        String identifier = this.getClass().getCanonicalName() + SPLITTER + PROTOCOL_VERSION;
        byte[] identifierBytes = identifier.getBytes();
        byte[] data = getData();
        buf.writeInt(identifierBytes.length)
                .writeBytes(identifierBytes)
                .writeInt(nameBytes.length)
                .writeBytes(nameBytes)
                .writeLong(targetFileSize)
                .writeInt(data.length)
                .writeBytes(data);
    }

    public void writeToFile(File file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("null file!");
        }
        try (OutputStream stream = new FileOutputStream(file)) {
            writeToStream(stream, false);
        }
    }
}
