package com.gmail.andrewandy.ascendency.serverplugin.matchmaking.draftpick;

import com.gmail.andrewandy.ascendency.serverplugin.game.gameclass.GameClass;
import com.gmail.andrewandy.ascendency.serverplugin.game.Champion;
import com.gmail.andrewandy.ascendency.serverplugin.game.rune.Rune;

import java.util.*;

/**
 * Represents a player in an {@link DraftPickMatch}
 * This object holds data about the player which cannot
 * be directly attributed onto the player object for the {@link DraftPickMatchEngine}
 * to interpret and run the game accordingly.
 */
public class AscendencyPlayer {

    Collection<Rune> appliedRunes = new ArrayList<>(); //Allow duplicate runes?
    Champion champion;
    GameClass gameClass;
    private UUID player;

    AscendencyPlayer(UUID player) {
        this.player = player;
    }

    /**
     * Get all runes which are currently applied to the player.
     * @return Returns a cloned collection of runes which are
     * currently active on the player.
     */
    public Collection<Rune> getAppliedRunes() {
        return new HashSet<>(appliedRunes);
    }

    /**
     * Get the champion the player has selected.
     * @return Returns the Champion object which the player has selected.
     */
    public Champion getChampion() {
        return champion;
    }

    public UUID getPlayer() {
        return player;
    }

    public boolean uuidMatches(UUID other) {
        return other.equals(player);
    }

    /**
     * Get the GameClass which the player selected.
     * @return Returns the GameClass object which the player has selected.
     */
    public GameClass getGameClass() {
        return gameClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AscendencyPlayer that = (AscendencyPlayer) o;

        if (!Objects.equals(appliedRunes, that.appliedRunes)) return false;
        if (!Objects.equals(champion, that.champion)) return false;
        if (!Objects.equals(gameClass, that.gameClass)) return false;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        int result = appliedRunes != null ? appliedRunes.hashCode() : 0;
        result = 31 * result + (champion != null ? champion.hashCode() : 0);
        result = 31 * result + (gameClass != null ? gameClass.hashCode() : 0);
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}
