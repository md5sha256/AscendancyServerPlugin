package com.gmail.andrewandy.ascendencyserverplugin.matchmaking;

import com.gmail.andrewandy.ascendencyserverplugin.matchmaking.match.ManagedMatch;
import com.gmail.andrewandy.ascendencyserverplugin.matchmaking.match.SimplePlayerMatchManager;
import com.gmail.andrewandy.ascendencyserverplugin.matchmaking.match.event.MatchStartEvent;
import com.gmail.andrewandy.ascendencyserverplugin.matchmaking.match.event.PlayerJoinMatchEvent;
import com.gmail.andrewandy.ascendencyserverplugin.matchmaking.match.event.PlayerLeftMatchEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.util.*;
import java.util.function.Supplier;

/**
 * Represents a service which automatically tries so make matches based on current player queues.
 *
 * @param <M>
 */
public class MatchMakingService<M extends ManagedMatch> {

    private Queue<Player> playerQueue = new LinkedList<>();

    private int minPlayersPerGame;
    private int maxPlayersPerGame;

    private Supplier<M> matchMakingFactory;

    /**
     * Create a new match making service.
     *
     * @param minPlayers         The min players per game.
     * @param maxPlayers         The max players per game.
     * @param matchMakingFactory A supplier for creating matches. Matches should already
     *                           be created with the correct teams AND these should be empty.
     */
    public MatchMakingService(int minPlayers, int maxPlayers, Supplier<M> matchMakingFactory) {
        if (isInvalidPlayerCount(minPlayers, maxPlayers)) {
            throw new IllegalArgumentException("Invalid Player limits!");
        }
        this.matchMakingFactory = Objects.requireNonNull(matchMakingFactory);
        this.maxPlayersPerGame = maxPlayers;
        this.minPlayersPerGame = minPlayers;
    }

    private boolean isInvalidPlayerCount(int min, int max) {
        return min <= 0 || min >= max;
    }

    /**
     * Set the max players per game when making new matches.
     *
     * @param maxPlayers The max players to allocate.
     * @throws IllegalArgumentException Thrown if the player limits are invalid.
     */
    public MatchMakingService<M> setMaxPlayersPerGame(int maxPlayers) {
        if (isInvalidPlayerCount(minPlayersPerGame, maxPlayers)) {
            throw new IllegalArgumentException("Invalid Player limits!");
        }
        this.maxPlayersPerGame = maxPlayers;
        return this;
    }

    /**
     * Set the max players per game when making new matches.
     *
     * @param minPlayers The min players to allocate.
     * @throws IllegalArgumentException Thrown if the player limits are invalid.
     */
    public MatchMakingService<M> setMinPlayersPerGame(int minPlayers) {
        if (isInvalidPlayerCount(minPlayers, maxPlayersPerGame)) {
            throw new IllegalStateException("Invalid Player limits!");
        }
        this.minPlayersPerGame = minPlayers;
        return this;
    }

    public void registerListeners() {
        unregisterListeners();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void unregisterListeners() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    /**
     * Will try to start a new match, once it is unsuccessful,
     * this method will stop trying to start new matches.
     */
    private void tryMatch() {
        int creatableMatchCount = playerQueue.size() / minPlayersPerGame;
        int optimizedMatchCount = creatableMatchCount > 0 ? playerQueue.size() / maxPlayersPerGame : creatableMatchCount;
        while (optimizedMatchCount > 0) {
            M match = matchMakingFactory.get();
            Collection<UUID> players = new HashSet<>(minPlayersPerGame);
            int index = 0;
            for (Player player : playerQueue) {
                if (index == maxPlayersPerGame) {
                    break;
                }
                players.add(player.getUniqueId());
                index++;
            }
            players.removeIf((player) -> new PlayerJoinMatchEvent(player, match).callEvent());
            match.addAndAssignPlayersTeams(players);
            if (new MatchStartEvent(match).callEvent()) { //If event was not cancelled.
                playerQueue.removeIf((Player player) -> players.contains(player.getUniqueId()));
                SimplePlayerMatchManager.INSTANCE.startMatch(match);
                optimizedMatchCount--;
            } else {
                break;
            }
        }
    }

    @Listener(order = Order.LAST)
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        Optional<ManagedMatch> current = SimplePlayerMatchManager.INSTANCE.getMatchOf(event.getTargetEntity().getUniqueId());
        if (!current.isPresent()) {
            //If not in previous match, then try to load them into the matchmaking queue.
            playerQueue.add(event.getTargetEntity());
            tryMatch();
        }
    }

    @Listener(order = Order.LAST)
    public void onPlayerLeaveMatch(PlayerLeftMatchEvent event) {
        Optional<Player> optionalPlayer = Sponge.getServer().getPlayer(event.getPlayer());
        optionalPlayer.ifPresent(playerQueue::add); //Add the player to the queue.
    }


}
