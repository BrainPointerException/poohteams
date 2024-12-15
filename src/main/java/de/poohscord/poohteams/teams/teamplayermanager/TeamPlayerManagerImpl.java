package de.poohscord.poohteams.teams.teamplayermanager;

import de.poohscord.poohteams.teams.TeamPlayerManager;

import java.util.UUID;

public class TeamPlayerManagerImpl implements TeamPlayerManager {

    private final TeamPlayerManager ds;

    public TeamPlayerManagerImpl(TeamPlayerManager ds) {
        this.ds = ds;
    }

    @Override
    public void createTeamPlayer(UUID uuid, String name) {
        this.ds.createTeamPlayer(uuid, name);
    }

    @Override
    public int getTeamPlayerId(UUID uuid) {
        return this.ds.getTeamPlayerId(uuid);
    }
}
