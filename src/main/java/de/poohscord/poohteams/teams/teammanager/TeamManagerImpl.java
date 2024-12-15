package de.poohscord.poohteams.teams.teammanager;

import de.poohscord.poohteams.teams.Team;
import de.poohscord.poohteams.teams.TeamManager;

public class TeamManagerImpl implements TeamManager {

    private final TeamManager ds;

    public TeamManagerImpl(TeamManager ds) {
        this.ds = ds;
    }

    @Override
    public void createTeam(Team team) {
        ds.createTeam(team);
    }

    @Override
    public void deleteTeam(Team team) {
        ds.deleteTeam(team);
    }

    @Override
    public void changeTeamName(Team team) {
        ds.changeTeamName(team);
    }

    @Override
    public void changeTeamTag(Team team) {
        ds.changeTeamTag(team);
    }

    @Override
    public void changeTeamColor(Team team) {
        ds.changeTeamColor(team);
    }

    @Override
    public Team getTeamByTeamName(String name) {
        return ds.getTeamByTeamName(name);
    }

    @Override
    public Team getTeamByTeamTag(String tag) {
        return ds.getTeamByTeamTag(tag);
    }

    @Override
    public Team getTeamByPlayerName(String name) {
        return ds.getTeamByPlayerName(name);
    }

}
