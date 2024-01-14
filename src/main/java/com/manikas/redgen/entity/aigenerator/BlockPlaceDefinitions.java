package com.manikas.redgen.entity.aigenerator;

import com.manikas.redgen.entity.AIGenPointer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.feature.stateproviders.RotatedBlockProvider;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.level.PistonEvent;

public class BlockPlaceDefinitions {
    public static void placeBlock(DirSet specialDir, BlockSet blockToPlace, AIGenPointer pointerToPlaceAt, ServerLevel serverLevel){

        AIGenPointer.isActing = true;

        BlockPos placingPosition = pointerToPlaceAt.blockPosition();

        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Placing "+blockToPlace+" in dimension "+serverLevel));

        switch (blockToPlace){
            case GREEN_WOOL -> serverLevel.setBlock(placingPosition, Blocks.LIME_WOOL.defaultBlockState(),1|2);
            case GLASS -> serverLevel.setBlock(placingPosition, Blocks.GLASS.defaultBlockState(),1|2);
            case SLAB -> {
                switch (specialDir) {
                    case TURN_UP, TURN_WEST, TURN_SOUTH, TURN_EAST, TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM), 1 | 2);
                }
            }
            case PISTON -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.UP), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.DOWN), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case STICKY_PISTON -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.UP), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.DOWN), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case SLIME_BLOCK -> serverLevel.setBlock(placingPosition, Blocks.SLIME_BLOCK.defaultBlockState(),1|2);
            case REDSTONE_DUST -> serverLevel.setBlock(placingPosition, Blocks.REDSTONE_WIRE.defaultBlockState(),1|2);
            case REDSTONE_TORCH -> {
                switch (specialDir) {
                    case TURN_UP, TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.REDSTONE_TORCH.defaultBlockState(), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REDSTONE_WALL_TORCH.defaultBlockState().setValue(RedstoneWallTorchBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.REDSTONE_WALL_TORCH.defaultBlockState().setValue(RedstoneWallTorchBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REDSTONE_WALL_TORCH.defaultBlockState().setValue(RedstoneWallTorchBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.REDSTONE_WALL_TORCH.defaultBlockState().setValue(RedstoneWallTorchBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case OBSERVER -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.OBSERVER.defaultBlockState().setValue(ObserverBlock.FACING, Direction.UP), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.OBSERVER.defaultBlockState().setValue(ObserverBlock.FACING, Direction.DOWN), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.OBSERVER.defaultBlockState().setValue(ObserverBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.OBSERVER.defaultBlockState().setValue(ObserverBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.OBSERVER.defaultBlockState().setValue(ObserverBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.OBSERVER.defaultBlockState().setValue(ObserverBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case REDSTONE_BLOCK -> serverLevel.setBlock(placingPosition, Blocks.REDSTONE_BLOCK.defaultBlockState(),1|2);
            case REDSTONE_REPEATER_1 -> {
                switch (specialDir) {
                    case TURN_UP, TURN_DOWN, TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 1).setValue(RepeaterBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 1).setValue(RepeaterBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 1).setValue(RepeaterBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 1).setValue(RepeaterBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case REDSTONE_REPEATER_2 -> {
                switch (specialDir) {
                    case TURN_UP, TURN_DOWN, TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 2).setValue(RepeaterBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 2).setValue(RepeaterBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 2).setValue(RepeaterBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 2).setValue(RepeaterBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case REDSTONE_REPEATER_3 -> {
                switch (specialDir) {
                    case TURN_UP, TURN_DOWN, TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 3).setValue(RepeaterBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 3).setValue(RepeaterBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 3).setValue(RepeaterBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 3).setValue(RepeaterBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case REDSTONE_REPEATER_4 -> {
                switch (specialDir) {
                    case TURN_UP, TURN_DOWN, TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 4).setValue(RepeaterBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 4).setValue(RepeaterBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 4).setValue(RepeaterBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.DELAY, 4).setValue(RepeaterBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case REDSTONE_COMPARATOR_SUBTRACT -> {
                switch (specialDir) {
                    case TURN_UP, TURN_DOWN, TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.MODE, ComparatorMode.SUBTRACT).setValue(ComparatorBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.MODE, ComparatorMode.SUBTRACT).setValue(ComparatorBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.MODE, ComparatorMode.SUBTRACT).setValue(ComparatorBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.MODE, ComparatorMode.SUBTRACT).setValue(ComparatorBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case REDSTONE_COMPARATOR_COMPARE -> {
                switch (specialDir) {
                    case TURN_UP, TURN_DOWN, TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.MODE, ComparatorMode.COMPARE).setValue(ComparatorBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.MODE, ComparatorMode.COMPARE).setValue(ComparatorBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.MODE, ComparatorMode.COMPARE).setValue(ComparatorBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.COMPARATOR.defaultBlockState().setValue(ComparatorBlock.MODE, ComparatorMode.COMPARE).setValue(ComparatorBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case REDSTONE_LAMP -> serverLevel.setBlock(placingPosition, Blocks.REDSTONE_LAMP.defaultBlockState(),1|2);
            case HOPPER -> {
                switch (specialDir) {
                    case TURN_UP, TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.DOWN), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.NORTH),1|2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.EAST),1|2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.SOUTH),1|2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, Direction.WEST),1|2);
                }
            }
            case DISPENSER -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.UP), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.DOWN), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case DROPPER -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.DROPPER.defaultBlockState().setValue(DropperBlock.FACING, Direction.UP), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.DROPPER.defaultBlockState().setValue(DropperBlock.FACING, Direction.DOWN), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.DROPPER.defaultBlockState().setValue(DropperBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.DROPPER.defaultBlockState().setValue(DropperBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.DROPPER.defaultBlockState().setValue(DropperBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.DROPPER.defaultBlockState().setValue(DropperBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case BARREL -> {
                switch (specialDir) {
                    case TURN_UP -> serverLevel.setBlock(placingPosition, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.UP), 1 | 2);
                    case TURN_DOWN -> serverLevel.setBlock(placingPosition, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.DOWN), 1 | 2);
                    case TURN_NORTH -> serverLevel.setBlock(placingPosition, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST -> serverLevel.setBlock(placingPosition, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH -> serverLevel.setBlock(placingPosition, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST -> serverLevel.setBlock(placingPosition, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case TARGET -> serverLevel.setBlock(placingPosition, Blocks.TARGET.defaultBlockState(),1|2);
            case STONE_BUTTON -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.CEILING), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.FLOOR), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.WALL).setValue(ButtonBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.WALL).setValue(ButtonBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.WALL).setValue(ButtonBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.WALL).setValue(ButtonBlock.FACING,Direction.WEST), 1 | 2);
                }
            }
            case OAK_BUTTON -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.OAK_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.CEILING), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.OAK_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.FLOOR), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.OAK_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.WALL).setValue(ButtonBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.OAK_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.WALL).setValue(ButtonBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.OAK_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.WALL).setValue(ButtonBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.OAK_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.WALL).setValue(ButtonBlock.FACING,Direction.WEST), 1 | 2);
                }
            }
            case LEVER -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.CEILING), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.FLOOR), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.WALL).setValue(LeverBlock.FACING, Direction.NORTH), 1 | 2);
                    case TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.WALL).setValue(LeverBlock.FACING, Direction.EAST), 1 | 2);
                    case TURN_SOUTH ->
                            serverLevel.setBlock(placingPosition, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.WALL).setValue(LeverBlock.FACING, Direction.SOUTH), 1 | 2);
                    case TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.WALL).setValue(LeverBlock.FACING, Direction.WEST), 1 | 2);
                }
            }
            case NULL -> Minecraft.getInstance().player.sendSystemMessage(Component.literal("Error : null provided as block to place"));
        }
        AIGenPointer.isActing = false;
    }
}
