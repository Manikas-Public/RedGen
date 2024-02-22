package com.manikas.redgen.entity.aigenerator;

import net.minecraft.core.BlockPos;

public class BlockArgs {
    private BlockSet definition;
    public DirSet rotation;
    public BlockPos relativePos;

    public BlockArgs(BlockSet definition, DirSet rotation, BlockPos relativePos) {
        this.definition = definition;
        this.rotation = rotation;
        this.relativePos = relativePos;
    }

    public BlockSet getDefinition(){
        return definition;
    }

    public DirSet getRotation(){
        return rotation;
    }

    public BlockPos getRelativePos(){
        return relativePos;
    }
}
