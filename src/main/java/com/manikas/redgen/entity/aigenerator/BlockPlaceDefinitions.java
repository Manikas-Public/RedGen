package com.manikas.redgen.entity.aigenerator;

import com.manikas.redgen.entity.AIGenPointer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;

public class BlockPlaceDefinitions {
    public void placeBlock(DirSet specialDir, BlockSet blockToPlace, AIGenPointer pointerToPlaceAt){
        switch (blockToPlace){
            case SLAB -> pointerToPlaceAt.level().setBlock(new BlockPos(pointerToPlaceAt.getBlockX(),pointerToPlaceAt.getBlockY(),pointerToPlaceAt.getBlockZ()), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(),1|2);
        }
    }
}
