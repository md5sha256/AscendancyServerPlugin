package com.gmail.andrewandy.ascendancy.serverplugin.matchmaking.match.event;

import com.gmail.andrewandy.ascendancy.serverplugin.matchmaking.match.Match;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;

public class MatchEndedEvent extends MatchEvent {

    public MatchEndedEvent(final Match match) {
        super(match);
    }

    public MatchEndedEvent(final @NotNull Match match, final @NotNull Cause cause) {
        super(match, cause);
    }

}
