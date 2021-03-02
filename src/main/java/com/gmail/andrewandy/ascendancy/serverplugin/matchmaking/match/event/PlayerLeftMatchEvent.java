package com.gmail.andrewandy.ascendancy.serverplugin.matchmaking.match.event;

import com.gmail.andrewandy.ascendancy.serverplugin.matchmaking.match.Match;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;

import java.util.Objects;
import java.util.UUID;

public class PlayerLeftMatchEvent extends MatchEvent {

    private final UUID player;

    public PlayerLeftMatchEvent(@NotNull final UUID player, @NotNull final Match match) {
        super(match);
        this.player = Objects.requireNonNull(player);
    }

    public PlayerLeftMatchEvent(final @NotNull Match match, final @NotNull Cause cause, final @NotNull UUID player) {
        super(match, cause);
        this.player = Objects.requireNonNull(player);
    }

    public @NotNull UUID getPlayer() {
        return player;
    }

}
