package com.gmail.andrewandy.ascendency.serverplugin.matchmaking.match.engine;

import com.gmail.andrewandy.ascendency.serverplugin.game.challenger.Challenger;
import org.spongepowered.api.effect.potion.PotionEffect;

import java.util.Collection;
import java.util.UUID;

public interface GamePlayer {

    UUID getPlayerUUID();

    Collection<PotionEffect> getBuffs();

    Collection<PotionEffect> getDebuffs();

    Challenger getChallenger();

}
