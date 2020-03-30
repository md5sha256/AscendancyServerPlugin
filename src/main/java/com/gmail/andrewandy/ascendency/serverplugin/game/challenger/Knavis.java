package com.gmail.andrewandy.ascendency.serverplugin.game.challenger;

import com.gmail.andrewandy.ascendency.lib.game.data.IChampionData;
import com.gmail.andrewandy.ascendency.serverplugin.game.rune.PlayerSpecificRune;

import java.util.Collections;

public class Knavis extends AbstractChallenger implements Challenger {

    private static final Knavis instance = new Knavis();

    public static Knavis getInstance() {
        return instance;
    }

    private Knavis() {
        super("Knavis", new PlayerSpecificRune[0], Collections.emptyList());
    }

    @Override
    public IChampionData toData() {
        return null;
    }
}
