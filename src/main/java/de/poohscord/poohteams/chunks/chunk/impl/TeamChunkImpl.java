package de.poohscord.poohteams.chunks.chunk.impl;

import de.poohscord.poohteams.chunks.chunk.TeamChunk;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TeamChunkImpl implements TeamChunk {

    private final JavaPlugin plugin;
    private final StructureManager structureManager;
    private final Chunk chunk;

    public TeamChunkImpl(JavaPlugin plugin, StructureManager structureManager, Chunk chunk) {
        this.plugin = plugin;
        this.structureManager = structureManager;
        this.chunk = chunk;
    }

    @Override
    public boolean save() {
        Structure structure = this.structureManager.createStructure();

        int x = chunk.getX() * 16;
        int z = chunk.getZ() * 16;
        World world = chunk.getWorld();

        structure.fill(new Vector(x, -64, z).toLocation(world),
                new Vector(x + 16, 319, z + 16).toLocation(world), true);

        File directory = new File(plugin.getDataFolder().getAbsolutePath() + "/teamchunks/");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/teamchunks/1");
        try {
            this.structureManager.saveStructure(file, structure);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean load() {
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/teamchunks/1");
        if (!file.exists()) {
            return false;
        }
        Structure structure = null;
        try {
            structure = structureManager.loadStructure(file);

            int x = chunk.getX() * 16;
            int z = chunk.getZ() * 16;
            World world = chunk.getWorld();

            structure.place(new Vector(x, -64, z).toLocation(world), true, StructureRotation.NONE, Mirror.NONE,
                    0, 1f, new Random());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
