package com.gmail.andrewandy.ascendency.serverplugin.game.challenger;

import com.gmail.andrewandy.ascendency.lib.game.data.IChampionData;
import com.gmail.andrewandy.ascendency.serverplugin.AscendencyServerPlugin;
import com.gmail.andrewandy.ascendency.serverplugin.game.ability.Ability;
import com.gmail.andrewandy.ascendency.serverplugin.game.rune.PlayerSpecificRune;
import com.gmail.andrewandy.ascendency.serverplugin.matchmaking.AscendencyServerEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;

import java.util.*;

public class Knavis extends AbstractChallenger implements Challenger {

    private static final Knavis instance = new Knavis();

    public static Knavis getInstance() {
        return instance;
    }

    private Knavis() {
        super("Knavis", new PlayerSpecificRune[]{ChosenOTEarth.instance}, Collections.emptyList());
    }


    private static class LivingGift implements Ability {

        private Map<UUID, Integer> isActive = new HashMap<>();

        private class LivingGiftUseEvent extends AscendencyServerEvent {

            private Cause cause;

            public LivingGiftUseEvent(Player player) {
                this.cause = Cause.builder().named("Player", player).build();
            }

            @Override
            public Cause getCause() {
                return cause;
            }
        }

        @Override
        public boolean isPassive() {
            return true;
        }

        @Override
        public String getName() {
            return "LivingGift";
        }

        @Listener
        public void onDamage(DamageEntityEvent event) {
            Collection<Player> players = event.getCause().allOf(Player.class);
            for (Player player : players) {
                if (!isActive.containsKey(player.getUniqueId())) {
                    continue;
                }
                int hits = isActive.get(player.getUniqueId());
                if (hits++ == 3) {
                    HealthData data = player.getHealthData();
                    data.set(data.health().transform((Double val) -> val + 3.0)); //Add 3 health or 1.5 hearts.
                    player.offer(data); //Update the player object.
                    hits = 0;
                    new LivingGiftUseEvent(player).callEvent();
                }
                isActive.replace(player.getUniqueId(), hits); //Update hit count
            }
        }
    }

    /*
    Chosen of the Earth - (Passive Buff) - Every time Knavis heals with Living Gift, she gains a stack that can stack up to 4 times, healing from 3-7 health.

     */
    private static class ChosenOTEarth implements PlayerSpecificRune {

        private static final ChosenOTEarth instance = new ChosenOTEarth();
        private Collection<UUID> isActive = new HashSet<>();

        public static ChosenOTEarth getInstance() {
            return instance;
        }

        private ChosenOTEarth() {
            Sponge.getEventManager().registerListeners(AscendencyServerPlugin.getInstance(), this);
        }

        @Override
        public void applyTo(Player player) {
            isActive.add(player.getUniqueId());
        }

        @Override
        public void clearFrom(Player player) {
            isActive.remove(player.getUniqueId());
        }

        @Override
        public String getName() {
            return "Chosen of the Earth";
        }

        @Override
        public int getContentVersion() {
            return 0;
        }

        @Override
        public DataContainer toContainer() {
            return null;
        }

        /**
         * Handles when a player uses "LivingGift"
         */
        @Listener
        public void onItemUse(DamageEntityEvent event) {
            Collection<? extends Player> causes = event.getCause().allOf(Player.class);
            if (causes.size() < 1) {
                return;
            }
            for (Player player : causes) {
                if (!isActive.contains(player.getUniqueId())) {
                    continue;
                }
                //Do code
            }
        }

        @Listener
        public void onDeath(DestructEntityEvent.Death event) {
            Entity entity = event.getTargetEntity();
            if (!(entity instanceof Player)) {
                return;
            }
            Collection<Player> players = event.getCause().allOf(Player.class);
            for (Player player : players) {
                if (!isActive.contains(player.getUniqueId())) {
                    continue;
                }
            }

        }

        @Listener
        public void onGiftUse(LivingGift.LivingGiftUseEvent event) {
            Optional<Player> optionalPlayer = (event.getCause().get("Player", Player.class).isPresent();
            assert optionalPlayer.isPresent();
            if (!isActive.contains(optionalPlayer.get().getUniqueId()) {
                return;
            }

        }
    }

    @Override
    public IChampionData toData() {
        return null;
    }
}
