package com.gmail.andrewandy.ascendency.serverplugin.game.challenger;

import com.gmail.andrewandy.ascendency.lib.game.data.IChampionData;
import com.gmail.andrewandy.ascendency.lib.game.data.game.ChampionDataImpl;
import com.gmail.andrewandy.ascendency.serverplugin.game.ability.Ability;
import com.gmail.andrewandy.ascendency.serverplugin.game.ability.AbstractAbility;
import com.gmail.andrewandy.ascendency.serverplugin.game.rune.PlayerSpecificRune;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Astricion extends AbstractChallenger {

    private static final Astricion instance = new Astricion();

    private Astricion() {
        super("Astricion",
                new Ability[0],
                new PlayerSpecificRune[0],
                Season1Challengers.getLoreOf("Astricion"));
    }

    public static Astricion getInstance() {
        return instance;
    }

    @Override
    public IChampionData toData() {
        try {
            return new ChampionDataImpl(getName(), new File("Path to data"), getLore());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static class Suppression extends AbstractAbility {

        private Collection<UUID> active = new HashSet<>();

        private Suppression() {
            super("Suppression", true);
        }

        public void activateAs(UUID player) {
            active.remove(player);
            active.add(player);
        }

        //TODO use entangle & give resistance
        @Listener
        public void onEntityDamage(DamageEntityEvent event) {
            Entity entity = event.getTargetEntity();
            if (!(entity instanceof Player) || !active.contains(entity.getUniqueId())) {
                return;
            }
            event.setBaseDamage(calculateIncomingDamage(event.getBaseDamage())); //Modifies the base damage directly
        }

        public double calculateIncomingDamage(double incoming) {
            return incoming * 0.6D; //Reduced incoming damage.
        }
    }
}
