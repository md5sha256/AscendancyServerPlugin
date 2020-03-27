package com.gmail.andrewandy.ascendency.serverplugin.matchmaking;

import com.gmail.andrewandy.ascendency.lib.packet.util.CommonUtils;

public enum Teams {

    ARCTIC(1, 5),
    BLASIC(6, 10);

    private Team team = new Team(CommonUtils.capitalise(name().toLowerCase()), 3);

    Teams(int min, int max) {
        team.setIDs(min, max);
    }

    Team asTeamObject() {
        return team.clone();
    }
}
