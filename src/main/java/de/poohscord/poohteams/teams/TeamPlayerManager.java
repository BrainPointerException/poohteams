package de.poohscord.poohteams.teams;

import java.util.UUID;

public interface TeamPlayerManager {

    void createTeamPlayer(UUID uuid, String name);

    int getTeamPlayerId(UUID uuid);

}
