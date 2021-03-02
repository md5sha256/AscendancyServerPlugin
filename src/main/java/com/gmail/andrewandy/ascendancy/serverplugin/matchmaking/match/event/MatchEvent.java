package com.gmail.andrewandy.ascendancy.serverplugin.matchmaking.match.event;

import com.gmail.andrewandy.ascendancy.serverplugin.api.event.AscendancyServerEvent;
import com.gmail.andrewandy.ascendancy.serverplugin.matchmaking.match.Match;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKey;

import java.util.Objects;

/**
 * Represents a event relating to a {@link Match}
 */
public abstract class MatchEvent extends AscendancyServerEvent {

    public static final EventContextKey<Match> MATCH_KEY = EventContextKey.builder(Match.class)
            .id("ascendancyserverplugin:matchmaking")
            .name("SOURCE")
            .build();

    private final Match match;
    private Cause cause;

    public MatchEvent(@NotNull final Match match) {
        this.match = Objects.requireNonNull(match);
        this.cause = Cause.builder().build(EventContext.builder().add(MATCH_KEY, match).build());
    }

    public MatchEvent(@NotNull final Match match, @NotNull final Cause cause) {
        this(match);
        this.cause = cause;
    }

    public @NotNull Match getMatch() {
        return match;
    }

    @Override
    public @NotNull Cause getCause() {
        return cause;
    }

}
