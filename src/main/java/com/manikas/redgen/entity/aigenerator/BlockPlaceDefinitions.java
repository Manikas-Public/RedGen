package com.manikas.redgen.entity.aigenerator;

import com.manikas.redgen.entity.AIGenPointer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;

public class BlockPlaceDefinitions {
    public static void placeBlock(DirSet specialDir, BlockSet blockToPlace, AIGenPointer pointerToPlaceAt, ServerLevel serverLevel){
        BlockPos placingPosition = pointerToPlaceAt.blockPosition();

        Level pointerLevel = pointerToPlaceAt.level();

        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Placing "+blockToPlace+" in dimension "+serverLevel));

        switch (blockToPlace){
            case GREEN_WOOL -> serverLevel.setBlock(placingPosition, Blocks.LIME_WOOL.defaultBlockState(),1|2);
            case GLASS -> serverLevel.setBlock(placingPosition, Blocks.GLASS.defaultBlockState(),1|2);
            case SLAB -> serverLevel.setBlock(placingPosition, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(),1|2);
            case PISTON -> serverLevel.setBlock(placingPosition, Blocks.PISTON.defaultBlockState(),1|2);
            case STICKY_PISTON -> serverLevel.setBlock(placingPosition, Blocks.STICKY_PISTON.defaultBlockState(),1|2);
            case SLIME_BLOCK -> serverLevel.setBlock(placingPosition, Blocks.SLIME_BLOCK.defaultBlockState(),1|2);
            case REDSTONE_DUST -> serverLevel.setBlock(placingPosition, Blocks.REDSTONE_WIRE.defaultBlockState(),1|2);
            case REDSTONE_TORCH -> serverLevel.setBlock(placingPosition, Blocks.REDSTONE_TORCH.defaultBlockState(),1|2);
            case REDSTONE_BLOCK -> serverLevel.setBlock(placingPosition, Blocks.REDSTONE_BLOCK.defaultBlockState(),1|2);
            case OBSERVER -> serverLevel.setBlock(placingPosition, Blocks.OBSERVER.defaultBlockState(),1|2);
            case REDSTONE_REPEATER_1 -> serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState(),1|2);
            case REDSTONE_REPEATER_2 -> serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState(),1|2);
            case REDSTONE_REPEATER_3 -> serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState(),1|2);
            case REDSTONE_REPEATER_4 -> serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState(),1|2);
            case REDSTONE_COMPARATOR_SUBTRACT -> serverLevel.setBlock(placingPosition, Blocks.COMPARATOR.defaultBlockState(),1|2);
            case REDSTONE_COMPARATOR_COMPARE -> serverLevel.setBlock(placingPosition, Blocks.COMPARATOR.defaultBlockState(),1|2);
            case REDSTONE_LAMP -> serverLevel.setBlock(placingPosition, Blocks.REDSTONE_LAMP.defaultBlockState(),1|2);
            case HOPPER -> serverLevel.setBlock(placingPosition, Blocks.HOPPER.defaultBlockState(),1|2);
            case DISPENSER -> serverLevel.setBlock(placingPosition, Blocks.DISPENSER.defaultBlockState(),1|2);
            case DROPPER -> serverLevel.setBlock(placingPosition, Blocks.DROPPER.defaultBlockState(),1|2);
            case BARREL -> serverLevel.setBlock(placingPosition, Blocks.BARREL.defaultBlockState(),1|2);
            case TARGET -> serverLevel.setBlock(placingPosition, Blocks.TARGET.defaultBlockState(),1|2);
            case STONE_BUTTON -> serverLevel.setBlock(placingPosition, Blocks.STONE_BUTTON.defaultBlockState(),1|2);
            case OAK_BUTTON -> serverLevel.setBlock(placingPosition, Blocks.OAK_BUTTON.defaultBlockState(),1|2);
            case LEVER -> serverLevel.setBlock(placingPosition, Blocks.LEVER.defaultBlockState(),1|2);
        }
    }
}
