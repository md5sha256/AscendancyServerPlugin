package com.gmail.andrewandy.ascendency.serverplugin.game.challenger;

import com.gmail.andrewandy.ascendency.lib.game.data.IChampionData;
import com.gmail.andrewandy.ascendency.lib.game.data.game.ChampionDataImpl;
import com.gmail.andrewandy.ascendency.serverplugin.AscendencyServerPlugin;
import com.gmail.andrewandy.ascendency.serverplugin.game.ability.Ability;
import com.gmail.andrewandy.ascendency.serverplugin.game.rune.AbstractRune;
import com.gmail.andrewandy.ascendency.serverplugin.game.rune.PlayerSpecificRune;
import com.gmail.andrewandy.ascendency.serverplugin.matchmaking.AscendencyServerEvent;
import com.gmail.andrewandy.ascendency.serverplugin.util.Common;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Knavis extends AbstractChallenger implements Challenger {

    private static final Knavis instance = new Knavis();

    private Knavis() {
        super("Knavis",
                new Ability[]{LivingGift.instance}, //Abilities
                new PlayerSpecificRune[]{ChosenOTEarth.instance}, //Runes
                Season1Challengers.getLoreOf("Knavis")); //Lore
    }

    public static Knavis getInstance() {
        return instance;
    }

    @Override
    public IChampionData toData() {
        try {
            return new ChampionDataImpl(getName(), new File("Path to file on server"), getLore());
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to create ChampionData", ex);
        }
    }

    private static class LivingGift implements Ability {

        private static LivingGift instance = new LivingGift();
        private Map<UUID, Integer> hitHistory = new HashMap<>();

        private LivingGift() {

        }

        public static LivingGift getInstance() {
            return instance;
        }

        @Override
        public boolean isPassive() {
            return true;
        }

        @Override
        public boolean isActive()  {
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
                if (!hitHistory.containsKey(player.getUniqueId())) {
                    continue;
                }
                int hits = hitHistory.get(player.getUniqueId());
                if (hits++ == 3) {
                    HealthData data = player.getHealthData();
                    data.set(data.health().transform((Double val) -> val + 3.0)); //Add 3 health or 1.5 hearts.
                    player.offer(data); //Update the player object.
                    hits = 0;
                    new LivingGiftUseEvent(player).callEvent();
                }
                hitHistory.replace(player.getUniqueId(), hits); //Update hit count
            }
        }

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
    }

    /**
     * Represents Knavis' rune named "Chosen of the Earth"
     */
    private static class ChosenOTEarth extends AbstractRune {

        private static final ChosenOTEarth instance = new ChosenOTEarth();
        private UUID uuid = UUID.randomUUID();
        private Map<UUID, Integer> stacks = new HashMap<>();
        private Map<UUID, Long> tickHistory = new HashMap<>();

        private ChosenOTEarth() {
            Sponge.getEventManager().registerListeners(AscendencyServerPlugin.getInstance(), this);
        }

        public static ChosenOTEarth getInstance() {
            return instance;
        }

        @Override
        public void applyTo(Player player) {
            tickHistory.put(player.getUniqueId(), 0L);
        }

        @Override
        public void clearFrom(Player player) {
            tickHistory.remove(player.getUniqueId());
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
         * Handles when a player uses {@link LivingGift}
         */
        @Listener
        public void onGiftUse(LivingGift.LivingGiftUseEvent event) {
            Optional<Player> optionalPlayer = (event.getCause().get("Player", Player.class));
            assert optionalPlayer.isPresent();
            if (!tickHistory.containsKey(optionalPlayer.get().getUniqueId())) {
                return;
            }
            Player playerObj = optionalPlayer.get();
            long val = tickHistory.get(playerObj.getUniqueId());
            val = val == 0 ? val : val - 1;
            tickHistory.replace(playerObj.getUniqueId(), val);
            stacks.compute(uuid, ((player, stack) -> {
                stack = stack == null ? 1 : stack; //Unboxing here may throw nullpointer.
                double health = 3;
                for (int index = 1; index < stack; ) {
                    health += index++;
                }
                Common.addHealth(playerObj, health); //Set the health of the player based on stacks.
                return stack == 4 ? stack : stack + 1; //If stack = 4, then max has been reached, therefore its 4 or stack + 1;
            }));
        }

        @Listener
        public void onDeath(DestructEntityEvent.Death event) {
            Entity entity = event.getTargetEntity();
            if (!(entity instanceof Player)) {
                return;
            }
            Collection<Player> players = event.getCause().allOf(Player.class);
            for (Player player : players) {
                if (!tickHistory.containsKey(player.getUniqueId())) {
                    continue;
                }
            }
            //TODO
        }

        @Override
        public UUID getUniqueID() {
            return uuid;
        }

        /**
         * Updates the stack history.
         */
        @Override
        public void tick() {
            tickHistory.entrySet().removeIf((entry -> {
                if (entry.getValue() >= Common.toTicks(6, TimeUnit.SECONDS)) {
                    stacks.remove(entry.getKey()); //Remove from stack history
                    return true;
                }
                return false;
            })); //Clear if greater than the number of ticks in 6 seconds.
        }
    }
}
