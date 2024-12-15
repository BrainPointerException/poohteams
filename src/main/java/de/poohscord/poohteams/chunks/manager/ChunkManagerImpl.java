package de.poohscord.poohteams.chunks.manager;

import de.poohscord.poohteams.chunks.ChunkManager;
import de.poohscord.poohteams.chunks.chunk.PoohChunk;
import de.poohscord.poohteams.chunks.chunk.impl.TeamChunkImpl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.structure.StructureManager;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class ChunkManagerImpl implements ChunkManager {

    private final JavaPlugin plugin;
    private final BukkitTask task;
    private final StructureManager structureManager;

    private final Set<Player> playersVisualizationMode = new HashSet<>();

    public ChunkManagerImpl(final JavaPlugin plugin, final StructureManager structureManager) {
        this.plugin = plugin;
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::visualize, 0, 20);
        this.structureManager = structureManager;
    }

    @Override
    public void enterVisualizationMode(Player player) {
        playersVisualizationMode.add(player);
    }

    @Override
    public void leaveVisualizationMode(Player player) {
        playersVisualizationMode.remove(player);
    }

    @Override
    public boolean savePlayerChunk(Player player) {
        //saveChunk(player);
        return false;
    }

    @Override
    public boolean saveTeamChunk(Player player) {
        return saveChunk(player, new TeamChunkImpl(plugin, structureManager, player.getChunk()));
    }

    @Override
    public boolean loadTeamChunk(Player player) {
        return new TeamChunkImpl(plugin, structureManager, player.getChunk()).load();
    }

    private boolean saveChunk(Player player, PoohChunk chunk) {
        if (!playersVisualizationMode.contains(player)) {
            return false;
        }
        return chunk.save();
    }

    @Override
    public void cancelTask() {
        playersVisualizationMode.clear();
        task.cancel();
    }

    private void visualize() {
        for (Player player : playersVisualizationMode) {
            Chunk chunk = player.getChunk();
            int x = chunk.getX() * 16;
            int z = chunk.getZ() * 16;
            for (int y = 0; y < 256; y++) {
                Vector point1 = new Vector(x, y, z);
                Vector point2 = new Vector(x + 16, y, z);
                Vector point3 = new Vector(x + 16, y, z + 16);
                Vector point4 = new Vector(x, y, z + 16);
                player.spawnParticle(Particle.ANGRY_VILLAGER, point1.getX(), y, point1.getZ(), 1);
                player.spawnParticle(Particle.ANGRY_VILLAGER, point2.getX(), y, point2.getZ(), 1);
                player.spawnParticle(Particle.ANGRY_VILLAGER, point3.getX(), y, point3.getZ(), 1);
                player.spawnParticle(Particle.ANGRY_VILLAGER, point4.getX(), y, point4.getZ(), 1);
            }
        }
    }
}
