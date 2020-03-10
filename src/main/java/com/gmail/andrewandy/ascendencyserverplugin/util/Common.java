package com.gmail.andrewandy.ascendencyserverplugin.util;

import com.gmail.andrewandy.ascendencyserverplugin.AscendencyServerPlugin;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;

public class Common {

    private static String prefix = "";
    private static ExecutorService executorService;

    public static void setup() {
        executorService = Sponge.getScheduler().createSyncExecutor(AscendencyServerPlugin.getInstance());
    }

    public static void setPrefix(String prefix) {
        Common.prefix = prefix;
    }

    public static ExecutorService getSyncExecutor() {
        return executorService;
    }

    public static void tell(MessageReceiver receiver, String... messages) {
        Objects.requireNonNull(receiver).sendMessage(Text.of((Object[]) messages));
    }

    public static void log(Level level, String... messages) {
        Logger logger = AscendencyServerPlugin.getInstance().getLogger();
        for (String message : messages) {
            message = message.concat(prefix + " " + message);
            if (level == Level.INFO) {
                logger.info(message);
            } else if (level == Level.WARNING) {
                logger.warn(message);
            } else if (level == Level.SEVERE) {
                logger.error(message);
            } else {
                logger.debug(message);
            }
        }
    }

    public static byte[] readFromStream(InputStream src) throws IOException {
        Objects.requireNonNull(src);
        byte[] data = new byte[src.available()];
        int index = 0;
        while (src.available() > 0) {
            data[index++] = (byte) src.read();
        }
        return data;
    }

}
