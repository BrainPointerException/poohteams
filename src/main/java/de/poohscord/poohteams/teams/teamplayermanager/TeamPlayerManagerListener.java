package de.poohscord.poohteams.teams.teamplayermanager;

import de.poohscord.poohteams.teams.TeamPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class TeamPlayerManagerListener implements Listener {

    private final TeamPlayerManager tpm;

    public TeamPlayerManagerListener(TeamPlayerManager tpm) {
        this.tpm = tpm;
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        final UUID uuid = e.getUniqueId();
        final String name = e.getName();

        int id;

        id = this.tpm.getTeamPlayerId(uuid);

        if (id == -1) {
            this.tpm.createTeamPlayer(uuid, name);
        }
    }

}
