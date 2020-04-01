package com.gmail.andrewandy.ascendency.serverplugin.matchmaking.match.engine;

import com.gmail.andrewandy.ascendency.serverplugin.game.challenger.Challenger;
import com.gmail.andrewandy.ascendency.serverplugin.game.rune.Rune;

import java.util.Collection;
import java.util.UUID;

public interface GamePlayer {

    UUID getPlayerUUID();

    Collection<Rune> getRunes();

    Challenger getChallenger();

}
