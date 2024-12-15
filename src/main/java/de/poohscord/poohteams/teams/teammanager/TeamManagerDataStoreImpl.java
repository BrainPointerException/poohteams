package de.poohscord.poohteams.teams.teammanager;

import com.zaxxer.hikari.HikariDataSource;
import de.poohscord.poohteams.teams.Team;
import de.poohscord.poohteams.teams.DataStore;
import de.poohscord.poohteams.teams.TeamManager;
import de.poohscord.poohteams.teams.team.TeamImpl;

import java.sql.*;
import java.util.logging.Logger;

public class TeamManagerDataStoreImpl implements DataStore, TeamManager {

    private static final String TEAMS_INSERT = "INSERT INTO teams (owner_id, team_name, team_tag, level, honey, " +
            "gradient_from_hex, gradient_to_hex) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String TEAM_PLAYERS_TEAM_UPDATE = "UPDATE team_players SET team_id = NULL WHERE team_id = ?;";
    private static final String TEAM_DELETE = "DELETE FROM teams WHERE team_id = ?;";
    private static final String TEAM_NAME_UPDATE = "UPDATE teams SET team_name = ? WHERE team_id = ?;";
    private static final String TEAM_TAG_UPDATE = "UPDATE teams SET team_tag = ? WHERE team_id = ?;";
    private static final String TEAM_COLOR_UPDATE = "UPDATE teams SET gradient_from_hex = ?, gradient_to_hex = ? WHERE team_id = ?;";
    private static final String GET_TEAM_BY_TEAM_NAME = "SELECT team_name, team_tag, gradient_from_hex, gradient_to_hex, owner_id" +
            " FROM teams WHERE team_name = ?;";
    private static final String GET_TEAM_BY_TEAM_TAG = "SELECT team_name, team_tag, gradient_from_hex, gradient_to_hex, owner_id" +
            " FROM teams WHERE team_tag = ?;";
    private static final String GET_TEAM_BY_PLAYER_NAME = "SELECT t.team_name, t.team_tag, t.gradient_from_hex, t.gradient_to_hex, t.owner_id" +
            " FROM teams t INNER JOIN team_players tp ON t.team_id = tp.team_id WHERE tp.player_name = ?;";
    private static final String GET_TEAM_ID_BY_TEAM_NAME = "SELECT team_id FROM teams WHERE team_name = ?;";
    private static final String UPDATE_PLAYER_TEAM_BY_ID = "UPDATE team_players SET team_id = ? WHERE player_id = ?;";

    private final HikariDataSource ds;

    private final Logger logger;

    public TeamManagerDataStoreImpl(HikariDataSource ds, Logger logger) {
        this.ds = ds;
        this.logger = logger;
    }

    @Override
    public void open() {
        createTables();
    }

    @Override
    public void close() {
        this.ds.close();
    }

    @Override
    public void changeTeamName(Team team) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(TEAM_NAME_UPDATE)) {
            ps.setString(1, team.getName());
            ps.setInt(2, team.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeTeamTag(Team team) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(TEAM_TAG_UPDATE)) {
            ps.setString(1, team.getTag());
            ps.setInt(2, team.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeTeamColor(Team team) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(TEAM_COLOR_UPDATE)) {
            ps.setString(1, team.getGradientFromColor());
            ps.setString(2, team.getGradientToColor());
            ps.setInt(3, team.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Team getTeamByTeamName(String name) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(GET_TEAM_BY_TEAM_NAME)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return getTeamFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Team getTeamByTeamTag(String tag) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(GET_TEAM_BY_TEAM_TAG)) {
            ps.setString(1, tag);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return getTeamFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Team getTeamByPlayerName(String name) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(GET_TEAM_BY_PLAYER_NAME)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return getTeamFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createTeam(Team team) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(TEAMS_INSERT)) {
            ps.setInt(1, team.getOwnerId());
            ps.setString(2, team.getName());
            ps.setString(3, team.getTag());
            ps.setInt(4, team.getLevel());
            ps.setInt(5, team.getHoney());
            ps.setString(6, team.getGradientFromColor());
            ps.setString(7, team.getGradientToColor());
            ps.executeUpdate();
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_PLAYER_TEAM_BY_ID)) {
            try (PreparedStatement ps2 = con.prepareStatement(GET_TEAM_ID_BY_TEAM_NAME)) {
                ps2.setString(1, team.getName());
                try (ResultSet rs = ps2.executeQuery()) {
                    if (rs.next()) {
                        ps.setInt(1, rs.getInt("team_id"));
                        ps.setInt(2, team.getOwnerId());
                        ps.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTeam(Team team) {
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(TEAM_PLAYERS_TEAM_UPDATE)) {
            ps.setInt(1, team.getId());
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(TEAM_DELETE)) {
            ps.setInt(1, team.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Team getTeamFromResultSet(ResultSet rs) throws SQLException {
        return new TeamImpl(rs.getString("team_name"), rs.getString("team_tag"),
                rs.getString("gradient_from_hex"), rs.getString("gradient_to_hex"),
                rs.getInt("owner_id"));
    }

    private void createTables() {

        String teamsQuery = "CREATE TABLE IF NOT EXISTS teams (" +
                "team_id SERIAL NOT NULL PRIMARY KEY, " +
                "owner_id INT NOT NULL, " +
                "team_name VARCHAR(12) NOT NULL, " +
                "team_tag VARCHAR(4) NOT NULL, " +
                "level INT NOT NULL, " +
                "honey INT NOT NULL, " +
                "gradient_from_hex VARCHAR(7), " +
                "gradient_to_hex VARCHAR(7)" +
                ");";

        String teamPlayersQuery = "CREATE TABLE IF NOT EXISTS team_players ("
                + "player_id SERIAL NOT NULL PRIMARY KEY, "
                + "player_uuid VARCHAR(36) NOT NULL, "
                + "player_name VARCHAR(16) NOT NULL, "
                + "team_id INT REFERENCES teams(team_id)"
                + ");";

        String teamChunksQuery = "CREATE TABLE IF NOT EXISTS team_chunks (" +
                "team_chunk_id SERIAL NOT NULL PRIMARY KEY, " +
                "team_id INT NOT NULL REFERENCES teams(team_id), " +
                "chunk_x INT NOT NULL, " +
                "chunk_z INT NOT NULL" +
                ");";

        String playerChunksQuery = "CREATE TABLE IF NOT EXISTS player_chunks (" +
                "player_chunk_id SERIAL NOT NULL PRIMARY KEY, " +
                "player_id INT NOT NULL REFERENCES team_players(player_id), " +
                "chunk_x INT NOT NULL, " +
                "chunk_z INT NOT NULL" +
                ");";

        String alterTeamsTable = "ALTER TABLE teams ADD CONSTRAINT owner_id FOREIGN KEY (owner_id) REFERENCES team_players(player_id);";

        try (Connection connection = ds.getConnection(); Statement statement = connection.createStatement()) {

            boolean update = statement.execute(teamsQuery);
            statement.execute(teamPlayersQuery);
            statement.execute(teamChunksQuery);
            statement.execute(playerChunksQuery);
            if (update) statement.execute(alterTeamsTable);

        } catch (SQLException e) {
            this.logger.severe("SQL State: %s\n%s".formatted(e.getSQLState(), e.getMessage()));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
