package com.gmail.andrewandy.ascendencyserverplugin.game;

import com.gmail.andrewandy.ascendencyserverplugin.game.gameclass.GameClass;
import com.gmail.andrewandy.ascendencyserverplugin.game.rune.PlayerSpecificRune;

/**
 * Represents a champion which players can select.
 */
public interface Champion {

    String getName();

    GameClass getGameClass();

    PlayerSpecificRune[] getRunes();

}
