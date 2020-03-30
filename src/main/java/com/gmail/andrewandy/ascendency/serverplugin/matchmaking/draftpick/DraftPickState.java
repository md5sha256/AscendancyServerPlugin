package com.gmail.andrewandy.ascendency.serverplugin.matchmaking.draftpick;

import com.gmail.andrewandy.ascendency.serverplugin.game.Champion;

import java.util.*;

public class DraftPickState {

    private Collection<Champion> banned = new HashSet<>();
    private Map<UUID, Champion> selected = new HashMap<>();

    public DraftPickState(byte[] data) {
    }

    public byte[] toData() {
        return new byte[0];
    }

    public enum PickPhase {

        PICKING, BANNING, FINALISE;

    }

}
