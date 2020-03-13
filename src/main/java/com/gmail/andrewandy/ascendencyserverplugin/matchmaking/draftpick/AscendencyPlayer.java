package com.gmail.andrewandy.ascendencyserverplugin.matchmaking.draftpick;

import com.gmail.andrewandy.ascendencyserverplugin.game.Champion;
import com.gmail.andrewandy.ascendencyserverplugin.game.rune.Rune;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * Represents a player in an {@link DraftPickMatch}
 * This object holds data about the player which cannot
 * be directly attributed onto the player object for the {@link DraftPickMatchEngine}
 * to interpret and run the game accordingly.
 */
public class AscendencyPlayer {

    Collection<Rune> appliedRunes = new HashSet<>();
    Champion champion;
    private UUID player;

    AscendencyPlayer(UUID player) {
        this.player = player;
    }

    public Collection<Rune> getAppliedRunes() {
        return new HashSet<>(appliedRunes);
    }

    public Champion getChampion() {
        return champion;
    }

    public UUID getPlayer() {
        return player;
    }

    public boolean uuidMatches(UUID other) {
        return other.equals(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AscendencyPlayer that = (AscendencyPlayer) o;

        if (player != null ? !player.equals(that.player) : that.player != null) return false;
        if (appliedRunes != null ? !appliedRunes.equals(that.appliedRunes) : that.appliedRunes != null) return false;
        return champion != null ? champion.equals(that.champion) : that.champion == null;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (appliedRunes != null ? appliedRunes.hashCode() : 0);
        result = 31 * result + (champion != null ? champion.hashCode() : 0);
        return result;
    }
}
