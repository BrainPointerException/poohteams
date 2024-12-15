package de.poohscord.poohteams.chunks;

import org.bukkit.entity.Player;

public interface ChunkManager {

    void enterVisualizationMode(Player player);

    void leaveVisualizationMode(Player player);

    boolean savePlayerChunk(Player player);

    boolean saveTeamChunk(Player player);

    boolean loadTeamChunk(Player player);

    void cancelTask();

}
