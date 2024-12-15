package de.poohscord.poohteams.teams.teammanager;

import de.poohscord.poohteams.teams.Team;
import de.poohscord.poohteams.teams.TeamManager;
import de.poohscord.poohteams.teams.TeamManagerCommand;
import de.poohscord.poohteams.teams.TeamPlayerManager;
import de.poohscord.poohteams.teams.team.TeamImpl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TeamManagerCommandImpl implements TeamManagerCommand {

    private final TeamManager tm;
    private final TeamPlayerManager tpm;

    public TeamManagerCommandImpl(TeamManager tm, TeamPlayerManager tpm) {
        this.tm = tm;
        this.tpm = tpm;
    }

    @Override
    public void createTeam(Team team) {
        this.tm.createTeam(team);
    }

    private void createTeam(Player owner, String name, String tag, String gradientColor) {
        if (name == null || tag == null || gradientColor == null) {
            owner.sendMessage("Name, tag or gradient color is null");
            return;
        }
        Team team = new TeamImpl(name, tag, gradientColor, gradientColor, this.tpm.getTeamPlayerId(owner.getUniqueId()));
        createTeam(team);
    }

    @Override
    public void deleteTeam(Team team) {

    }

    @Override
    public void changeTeamName(Team team) {

    }

    @Override
    public void changeTeamTag(Team team) {

    }

    @Override
    public void changeTeamColor(Team team) {

    }

    @Override
    public Team getTeamByTeamName(String name) {
        return null;
    }

    @Override
    public Team getTeamByTeamTag(String tag) {
        return null;
    }

    @Override
    public Team getTeamByPlayerName(String name) {
        return this.tm.getTeamByPlayerName(name);
    }

    private void getTeamByPlayerName(Player player, String name) {
        if (name == null) {
            player.sendMessage("Name is null");
            return;
        }
        Team team = getTeamByPlayerName(name);
        if (team == null) {
            player.sendMessage("You are not in a team");
            return;
        }
        player.sendMessage("Team: %s".formatted(team.getName()));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("You must be a player to execute this command");
            return true;
        }

        /*
        (String name, String tag, String gradientFromColor, String gradientToColor, int ownerId,
                    List<Integer> memberIds
         */

        switch (args.length) {
            case 0:
                getTeamByPlayerName(p, p.getName());
                break;
            case 3:
               createTeam(p, args[0], args[1], args[2]);
               break;
        }

        return true;
    }
}
