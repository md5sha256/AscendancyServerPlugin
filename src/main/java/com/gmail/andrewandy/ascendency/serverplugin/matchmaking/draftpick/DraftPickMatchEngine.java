package com.gmail.andrewandy.ascendency.serverplugin.matchmaking.draftpick;

import com.gmail.andrewandy.ascendency.serverplugin.AscendencyServerPlugin;
import com.gmail.andrewandy.ascendency.serverplugin.game.gameclass.GameClass;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * Represents the engine in which will execute logic for the main sequence of the
 * {@link DraftPickMatch}s. This object will hold {@link AscendencyPlayer} objects
 * in order to keep track of data which cannot be directly attributed to the player,
 * or would be inefficient to do so.
 */
public class DraftPickMatchEngine {

    private static Scoreboard scoreboard;
    private static Objective damager, victim;
    private static String relativeIDName;
    private WeakReference<DraftPickMatch> matchReference;
    private Collection<AscendencyPlayer> ascendencyPlayers;


    DraftPickMatchEngine(DraftPickMatch match) {
        this.matchReference = new WeakReference<>(match);
        int index = 0;
        Collection<UUID> collection = match.getPlayers();
        this.ascendencyPlayers = new HashSet<>(collection.size());
        for (UUID uuid : collection) {
            this.ascendencyPlayers.add(new AscendencyPlayer(uuid, index++));
        }
        initScoreBoard();
    }

    public void initScoreBoard() {
        if (scoreboard != null) {
            return;
        }
        ConfigurationNode node = AscendencyServerPlugin.getInstance().getSettings();
        node = node.getNode("DamageScoreboard");
        Objects.requireNonNull(node, "Invalid Config! DamageScoreboard is missing!");
        String rawName, rawDamager, rawVictim;
        rawName = node.getNode("ScoreboardPlayerID").getString();
        rawDamager = node.getNode("ScoreboardDamager").getString();
        rawVictim = node.getNode("ScoreboardVictim").getString();
        relativeIDName = rawName;
        damager = Objective.builder().name(rawDamager).build();
        victim = Objective.builder().name(rawVictim).build();
        scoreboard = Scoreboard.builder()
                .objectives(Arrays.asList(damager, victim))
                .build();
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
        init();
    }

    private void init() {
        Sponge.getEventManager().registerListeners(AscendencyServerPlugin.getInstance(), this);
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
            String command = "scoreboard players set " + playerObj.getName() + gameClass.getName() + "1";
            Sponge.getServer().getConsole().sendMessage(Text.of(command)); //Send the command through to console.
        });
    }

    public void pause() {

    }

    public void resume() {

    }

    public void end() {

    }

    private void disable() {
        Sponge.getEventManager().unregisterListeners(this);
        ascendencyPlayers.clear();
    }

    //Register listeners for game logic.

    /**
     * Scoreboard updater for the command-block implementation
     * of this game engine.
     */
    @Listener(order = Order.LAST)
    public void onDamage(DamageEntityEvent event) {
        if (matchReference.isEnqueued()) {
            disable();
            return;
        }
        DraftPickMatch match = matchReference.get();
        assert match != null;
        Cause cause = event.getCause();
        Entity victim = event.getTargetEntity();
        if (!(victim instanceof Player)) {
            return;
        }
        Optional<AscendencyPlayer> optionalVictimObject = wrapPlayer(victim.getUniqueId());
        if (!optionalVictimObject.isPresent()) {
            return;
        }
        Player actual = null;
        List<Player> objects = cause.allOf(Player.class); //Get all players which were damaged.
        for (Player player : objects) {
            if (player == victim) {
                return;
            }
            Optional<AscendencyPlayer> optional = wrapPlayer(player.getUniqueId()); //Player object
            if (!optional.isPresent()) {
                continue;
            }
            if (!match.isEngaged()) { //Cancels this event if the match is not engaged.
                event.setCancelled(true);
                return;
            }
            Optional<Score> optionalDamager = damager.getScore(Text.of(relativeIDName));
            assert optionalDamager.isPresent(); //Scoreboard should have been set on initalisation.
            Score score = optionalDamager.get();
            score.setScore(optional.get().relativeID);
            actual = player;
            break; //Only take the first player in the list as a damager.
        }
        if (actual == null) {
            return;
        }
        Optional<AscendencyPlayer> optional = wrapPlayer(victim.getUniqueId()); //Victim AP object.
        assert optional.isPresent();
        Optional<Score> optionalVictim = damager.getScore(Text.of(relativeIDName));
        assert optionalVictim.isPresent();
        optionalVictim.get().setScore(optional.get().relativeID);
    }
}
