package de.poohscord.poohteams.teams.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.poohscord.poohteams.teams.DataStore;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PGDataConnector implements DataStore {

    private HikariDataSource ds;

    private final JavaPlugin plugin;

    public PGDataConnector(JavaPlugin plugin) {
        this.plugin = plugin;
        final File file = new File(plugin.getDataFolder() + "/hikari.properties");
        if (!file.exists()) {
            plugin.saveResource("hikari.properties", false);
        }
    }

    @Override
    public void open() {
        HikariConfig config = new HikariConfig(plugin.getDataFolder() + "/hikari.properties");
        this.ds = new HikariDataSource(config);
    }

    @Override
    public void close() {
        this.ds.close();
    }

    public HikariDataSource getDataSource() {
        return ds;
    }
}
