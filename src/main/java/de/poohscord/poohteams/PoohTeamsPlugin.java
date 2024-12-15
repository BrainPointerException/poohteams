package de.poohscord.poohteams;

import de.poohscord.poohteams.chunks.ChunkManager;
import de.poohscord.poohteams.chunks.manager.ChunkManagerImpl;
import de.poohscord.poohteams.teams.TeamManager;
import de.poohscord.poohteams.teams.DataStore;
import de.poohscord.poohteams.teams.TeamManagerCommand;
import de.poohscord.poohteams.teams.TeamPlayerManager;
import de.poohscord.poohteams.teams.data.PGDataConnector;
import de.poohscord.poohteams.teams.teammanager.TeamManagerCommandImpl;
import de.poohscord.poohteams.teams.teammanager.TeamManagerDataStoreImpl;
import de.poohscord.poohteams.teams.teammanager.TeamManagerImpl;
import de.poohscord.poohteams.teams.teamplayermanager.TeamPlayerManagerDataStoreImpl;
import de.poohscord.poohteams.teams.teamplayermanager.TeamPlayerManagerImpl;
import de.poohscord.poohteams.teams.teamplayermanager.TeamPlayerManagerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class PoohTeamsPlugin extends JavaPlugin {

    private PGDataConnector dataStore;
    private ChunkManager chunkManager;

    @Override
    public void onEnable() {
        this.chunkManager = new ChunkManagerImpl(this, Bukkit.getStructureManager());
        Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers()
                .forEach(this.chunkManager::enterVisualizationMode), 0, 20);
        //Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers().forEach(chunkManager::saveTeamChunk), 0, 20*10);
        //Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers().forEach(chunkManager::loadTeamChunk), 0, 20*10);
        this.dataStore = new PGDataConnector(this);
        this.dataStore.open();

        DataStore playerData = new TeamPlayerManagerDataStoreImpl(this.dataStore.getDataSource(), this.getLogger());
        playerData.open();

        DataStore teamData = new TeamManagerDataStoreImpl(this.dataStore.getDataSource(), this.getLogger());
        teamData.open();

        TeamPlayerManager teamPlayerManager = new TeamPlayerManagerImpl((TeamPlayerManager) playerData);

        TeamManager teamManager = new TeamManagerImpl((TeamManager) teamData);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new TeamPlayerManagerListener(teamPlayerManager), this);

        TeamManagerCommand tmc = new TeamManagerCommandImpl(teamManager, teamPlayerManager);

        Objects.requireNonNull(getCommand("tm")).setExecutor(tmc);

        getLogger().info("PoohTeams enabled!");
    }

    @Override
    public void onDisable() {
        this.dataStore.close();
        chunkManager.cancelTask();
        getLogger().info("PoohTeams disabled!");
    }

}
