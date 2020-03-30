package com.gmail.andrewandy.ascendency.serverplugin.test;

import com.gmail.andrewandy.ascendency.lib.testutils.MockPlayer;
import com.gmail.andrewandy.ascendency.serverplugin.matchmaking.Team;
import com.gmail.andrewandy.ascendency.serverplugin.matchmaking.draftpick.DraftPickMatch;

import java.util.Collection;
import java.util.HashSet;

public class MatchMakingServiceTest {

    private static final int maxPlayers = 5;
    private static final int minPlayers = 3;

    private DraftPickMatch match = new DraftPickMatch(maxPlayers, new HashSet<>()).setMinPlayersPerTeam(minPlayers);
    private Collection<MockPlayer> players = new HashSet<>();

    public MatchMakingServiceTest() {

    }

    public void populatePlayers() {
    }

    public void setupTeams() {
        //Make 2 teams;
        for (int index = 0; index < 2; index++) {
            Team team = new Team(String.valueOf(index), null);
        }
    }

}
