package com.gmail.andrewandy.ascendency.serverplugin.game;

import com.gmail.andrewandy.ascendency.serverplugin.game.rune.PlayerSpecificRune;

/**
 * Represents a champion which players can select.
 */
public interface Champion {

    String getName();

    PlayerSpecificRune[] getRunes();

}
