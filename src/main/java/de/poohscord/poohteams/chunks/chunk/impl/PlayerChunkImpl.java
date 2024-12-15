package de.poohscord.poohteams.chunks.chunk.impl;

import de.poohscord.poohteams.chunks.chunk.PlayerChunk;
import org.bukkit.structure.StructureManager;

public class PlayerChunkImpl implements PlayerChunk {

    private final StructureManager structureManager;

    public PlayerChunkImpl(StructureManager structureManager) {
        this.structureManager = structureManager;
    }

    @Override
    public boolean save() {
        return false;
    }

}
