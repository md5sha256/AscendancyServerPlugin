package com.gmail.andrewandy.ascendancy.serverplugin.game.util;

import com.gmail.andrewandy.ascendancy.serverplugin.api.challenger.Challenger;
import org.spongepowered.api.event.cause.EventContextKey;

public class ChallengerUtils {

    public static <T extends Challenger> EventContextKey<T> newKey(final Class<T> clazz, final String name) {
        return EventContextKey.builder(clazz)
                .id("ascendancyserverplugin:challenger")
                .name(name)
                .build();
    }

    public static <T extends Challenger> EventContextKey<T> newRootKey(final Class<T> clazz) {
        return newKey(clazz, "SOURCE");
    }


}
