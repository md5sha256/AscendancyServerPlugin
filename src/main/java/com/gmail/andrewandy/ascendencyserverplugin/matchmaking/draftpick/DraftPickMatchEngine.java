package com.gmail.andrewandy.ascendencyserverplugin.matchmaking.draftpick;

import com.gmail.andrewandy.ascendencyserverplugin.game.gameclass.GameClass;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.text.Text;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents the engine in which will execute logic for the main sequence of the
 * {@link DraftPickMatch}s. This object will hold {@link AscendencyPlayer} objects
 * in order to keep track of data which cannot be directly attributed to the player,
 * or would be inefficient to do so.
 */
public class DraftPickMatchEngine {

    private Collection<AscendencyPlayer> ascendencyPlayers;

    DraftPickMatchEngine(DraftPickMatch match) {
        this.ascendencyPlayers = match.getPlayers().stream().map(AscendencyPlayer::new).collect(Collectors.toCollection(HashSet::new));
    }

    Collection<AscendencyPlayer> getAscendencyPlayers() {
        return new HashSet<>(ascendencyPlayers);
    }

    Optional<AscendencyPlayer> wrapPlayer(UUID player) {
        for (AscendencyPlayer ap : ascendencyPlayers) {
            if (ap.uuidMatches(player)) {
                return Optional.of(ap);
            }
        }
        return Optional.empty();
    }

    public void tick() {
        //TODO Update the runes, etc
    }

    public void start() {

    }

    private void init() {
        ascendencyPlayers.forEach(this::initPlayer);
    }

    public void rejoin(UUID player) throws IllegalArgumentException {
        AscendencyPlayer ascendencyPlayer = wrapPlayer(player).orElseThrow(() -> new IllegalArgumentException("Player is not in this match!"));
        initPlayer(ascendencyPlayer);
    }

    private void initPlayer(AscendencyPlayer player) {
        GameClass gameClass = player.gameClass;
        UUID playerUID = player.getPlayer();
        Optional<Player> optionalPlayer = Sponge.getServer().getPlayer(playerUID);
        optionalPlayer.ifPresent((playerObj) -> {
            String command = "scoreboard players set " + playerObj.getName() + gameClass.getName();
            Sponge.getServer().getConsole().sendMessage(Text.of(command)); //Send the command through to console.
        });
    }

    public void pause() {

    }

    public void resume() {

    }

    public void end() {

    }

    //Register listeners for game logic.

}
