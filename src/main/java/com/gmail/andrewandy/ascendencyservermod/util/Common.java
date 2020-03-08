package com.gmail.andrewandy.ascendencyservermod.util;

import com.gmail.andrewandy.ascendencyservermod.AscendencyServerPlugin;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class Common {

    private static String prefix = "";
    private static ExecutorService executorService;

    public static void setup() {
        executorService = Sponge.getScheduler().createSyncExecutor(AscendencyServerPlugin.getInstance());
    }

    public static void setPrefix(String prefix) {
        Common.prefix = prefix;
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static void tell(MessageReceiver receiver, String... messages) {
        Objects.requireNonNull(receiver).sendMessage(Text.of((Object[]) messages));
    }

}
