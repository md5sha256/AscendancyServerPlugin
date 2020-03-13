package com.gmail.andrewandy.ascendencyserverplugin.matchmaking.match.event;

import com.gmail.andrewandy.ascendencyserverplugin.matchmaking.match.Match;
import org.spongepowered.api.event.cause.Cause;

public class MatchEndedEvent extends MatchEvent {
    public MatchEndedEvent(Match match) {
        super(match);
    }

    public MatchEndedEvent(Match match, String name, Object cause) {
        super(match, name, cause);
    }

    @Override
    public Cause getCause() {
        return null;
    }
}
