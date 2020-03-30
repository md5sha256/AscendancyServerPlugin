package com.gmail.andrewandy.ascendency.serverplugin.game.challenger;

import com.gmail.andrewandy.ascendency.serverplugin.game.rune.PlayerSpecificRune;

import java.util.List;

public abstract class AbstractChallenger implements Challenger {

    private final String name;
    private final PlayerSpecificRune[] runes;
    private final List<String> lore;

    public AbstractChallenger(String name, PlayerSpecificRune[] runes, List<String> lore) {
        this.name = name;
        this.runes = runes;
        this.lore = lore;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayerSpecificRune[] getRunes() {
        return runes;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }


}
