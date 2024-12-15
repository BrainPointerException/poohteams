package de.poohscord.poohteams.teams.teamplayermanager;

import com.zaxxer.hikari.HikariDataSource;
import de.poohscord.poohteams.teams.DataStore;
import de.poohscord.poohteams.teams.TeamPlayerManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

public class TeamPlayerManagerDataStoreImpl implements DataStore, TeamPlayerManager {

    private static final String CREATE_TEAM_PLAYER = "INSERT INTO team_players (player_uuid, player_name) VALUES (?, ?);";
    private static final String GET_TEAM_PLAYER_ID = "SELECT player_id FROM team_players WHERE player_uuid = ?;";

    private final HikariDataSource ds;
    private final Logger logger;

    public TeamPlayerManagerDataStoreImpl(HikariDataSource ds, Logger logger) {
        this.ds = ds;
        this.logger = logger;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {
        this.ds.close();
    }

    @Override
    public void createTeamPlayer(UUID uuid, String name) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(CREATE_TEAM_PLAYER)) {
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getTeamPlayerId(UUID uuid) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(GET_TEAM_PLAYER_ID)) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("player_id");
                }
            }
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
