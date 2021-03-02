package com.gmail.andrewandy.ascendancy.serverplugin.matchmaking.match.event;

import com.gmail.andrewandy.ascendancy.serverplugin.matchmaking.match.Match;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;

import java.util.Objects;
import java.util.UUID;

public class PlayerJoinMatchEvent extends MatchEvent implements Cancellable {

    private final UUID player;
    private boolean cancel;

    public PlayerJoinMatchEvent(@NotNull UUID player, @NotNull Match joined) {
        super(joined);
        this.player = Objects.requireNonNull(player);
    }

    public PlayerJoinMatchEvent(final @NotNull Match match, final @NotNull Cause cause, final @NotNull UUID player) {
        super(match, cause);
        this.player = player;
    }

    public @NotNull UUID getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

}
