package com.gmail.andrewandy.ascendency.serverplugin.game;

public class Mechanics {

    public enum DamageType {

        ATTACK_DAMAGE, ABILITY_POWER;


        DamageType() {}

        double calculateDamage(Challenger challenger, double damage) {
            //return challenger.getDamageType() == this ? damage : damage * modifier;
            throw new UnsupportedOperationException("Unimplemented");
        }

    }

}
