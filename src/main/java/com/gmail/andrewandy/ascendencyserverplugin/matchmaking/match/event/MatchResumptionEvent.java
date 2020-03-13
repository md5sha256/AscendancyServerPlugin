package com.gmail.andrewandy.ascendencyserverplugin.matchmaking.match.event;

import com.gmail.andrewandy.ascendencyserverplugin.matchmaking.match.Match;
import org.spongepowered.api.event.Cancellable;

public class MatchResumptionEvent extends MatchEvent implements Cancellable {

    private boolean cancel;

    public MatchResumptionEvent(Match match) {
        super(match);
    }

    public MatchResumptionEvent(Match match, String name, Object cause) {
        super(match, name, cause);
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }
}
