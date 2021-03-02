package com.gmail.andrewandy.ascendancy.serverplugin.util.keybind;

import com.gmail.andrewandy.ascendancy.serverplugin.api.event.AscendancyServerEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;

public class ActiveKeyReleasedEvent extends AscendancyServerEvent {

    @NotNull
    private final Player player;
    @NotNull
    private final Cause cause;

    ActiveKeyReleasedEvent(@NotNull final Player player) {
        this.player = player;
        this.cause = Cause.builder().build(EventContext.builder().add(EventContextKeys.PLAYER, player).build());
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    @NotNull
    public Cause getCause() {
        return cause;
    }

}
