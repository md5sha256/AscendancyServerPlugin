package com.gmail.andrewandy.ascendency.serverplugin.game.challenger;

import com.gmail.andrewandy.ascendency.lib.game.data.IChampionData;
import com.gmail.andrewandy.ascendency.serverplugin.game.rune.PlayerSpecificRune;

import java.util.List;

/**
 * Represents a champion which players can select.
 */
public interface Challenger {

    String getName();

    PlayerSpecificRune[] getRunes();

    List<String> getLore();

    IChampionData toData();

}
