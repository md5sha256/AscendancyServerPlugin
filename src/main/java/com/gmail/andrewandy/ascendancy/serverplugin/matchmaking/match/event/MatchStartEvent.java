package com.gmail.andrewandy.ascendancy.serverplugin.matchmaking.match.event;

import com.gmail.andrewandy.ascendancy.serverplugin.matchmaking.match.Match;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;

public class MatchStartEvent extends MatchEvent implements Cancellable {

    private boolean cancel;

    public MatchStartEvent(final Match match) {
        super(match);
    }

    public MatchStartEvent(final @NotNull Match match, final @NotNull Cause cause) {
        super(match, cause);
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }

}
