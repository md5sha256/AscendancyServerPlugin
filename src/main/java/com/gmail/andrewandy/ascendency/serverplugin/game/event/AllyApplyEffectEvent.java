package com.gmail.andrewandy.ascendency.serverplugin.game.event;

import com.gmail.andrewandy.ascendency.serverplugin.matchmaking.Team;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.entity.living.player.Player;

public class AllyApplyEffectEvent extends GameEvent {

    private Player target;
    private PotionEffectType type;

    /**
     * Construct a new ally apply effect event/
     * @param player The ally.
     * @param target The player who now has new effects.
     * @param team The team of both players.
     */
    public AllyApplyEffectEvent(Player player, Player target, Team team) {
        super(player, team);
        this.target = target;
        type.getName();
    }

    /**
     * @return Returns the target of the effect application.
     */
    public Player getTarget() {
        return target;
    }
}
