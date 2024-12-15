package de.poohscord.poohteams.teams;

public interface TeamManager {

    void createTeam(Team team);

    void deleteTeam(Team team);

    void changeTeamName(Team team);

    void changeTeamTag(Team team);

    void changeTeamColor(Team team);

    Team getTeamByTeamName(String name);

    Team getTeamByTeamTag(String tag);

    Team getTeamByPlayerName(String name);

}
