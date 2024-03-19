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
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.RotatedBlockProvider;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.level.PistonEvent;

public class BlockPlaceDefinitions {


    private static Direction pointerToDir(DirSet pointerEnum){
        switch (pointerEnum){
            case TURN_UP -> {
                return Direction.UP;
            }
            case TURN_DOWN -> {
                return Direction.DOWN;
            }
            case TURN_NORTH -> {
                return Direction.NORTH;
            }
            case TURN_EAST -> {
                return Direction.EAST;
            }
            case TURN_SOUTH -> {
                return Direction.SOUTH;
            }
            case TURN_WEST -> {
                return Direction.WEST;
            }
        }
        return null;
    }

    public static void placeBlock(DirSet specialDir, BlockSet blockToPlace, AIGenPointer pointerToPlaceAt, ServerLevel serverLevel){

        AIGenPointer.isActing = true;

        BlockPos placingPosition = pointerToPlaceAt.blockPosition();

//        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Placing "+blockToPlace+" in dimension "+serverLevel));

        switch (blockToPlace){
            case GREEN_WOOL -> serverLevel.setBlock(placingPosition, Blocks.LIME_WOOL.defaultBlockState(),1|2);
            case GLASS -> serverLevel.setBlock(placingPosition, Blocks.GLASS.defaultBlockState(),1|2);
            case SLAB -> {
                switch (specialDir) {
                    case TURN_UP, TURN_WEST, TURN_SOUTH, TURN_EAST ->
                            serverLevel.setBlock(placingPosition, Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM), 1 | 2);
                }
            }
            case PISTON -> {
                serverLevel.setBlock(placingPosition, Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, pointerToDir(specialDir)), 1 | 2);
            }
            case STICKY_PISTON -> {
                            serverLevel.setBlock(placingPosition, Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, pointerToDir(specialDir)), 1 | 2);
            }
            case SLIME_BLOCK -> serverLevel.setBlock(placingPosition, Blocks.SLIME_BLOCK.defaultBlockState(),1|2);
            case REDSTONE_DUST -> serverLevel.setBlock(placingPosition, Blocks.REDSTONE_WIRE.defaultBlockState(),1|2);
            case REDSTONE_TORCH -> {
                switch (specialDir) {
                    case TURN_UP, TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.REDSTONE_TORCH.defaultBlockState(), 1 | 2);
                    case TURN_NORTH ->
                            serverLevel.setBlock(placingPosition, Blocks.REDSTONE_WALL_TORCH.defaultBlockState().setValue(RedstoneWallTorchBlock.FACING, pointerToDir(specialDir)), 1 | 2);
                }
            }
            case OBSERVER -> {
                serverLevel.setBlock(placingPosition, Blocks.OBSERVER.defaultBlockState().setValue(ObserverBlock.FACING, pointerToDir(specialDir)), 1 | 2);
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
                    case TURN_NORTH, TURN_EAST, TURN_SOUTH, TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, pointerToDir(specialDir)),1|2);
                }
            }
            case DISPENSER -> {
                serverLevel.setBlock(placingPosition, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, pointerToDir(specialDir)), 1 | 2);
            }
            case DROPPER -> {
                serverLevel.setBlock(placingPosition, Blocks.DROPPER.defaultBlockState().setValue(DropperBlock.FACING, pointerToDir(specialDir)), 1 | 2);
            }
            case BARREL -> {
                serverLevel.setBlock(placingPosition, Blocks.BARREL.defaultBlockState().setValue(BarrelBlock.FACING, pointerToDir(specialDir)), 1 | 2);
            }
            case TARGET -> serverLevel.setBlock(placingPosition, Blocks.TARGET.defaultBlockState(),1|2);
            case STONE_BUTTON -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.CEILING), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.FLOOR), 1 | 2);
                    case TURN_NORTH, TURN_EAST, TURN_SOUTH, TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.STONE_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.WALL).setValue(ButtonBlock.FACING, pointerToDir(specialDir)), 1 | 2);
                }
            }
            case OAK_BUTTON -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.OAK_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.CEILING), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.OAK_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.FLOOR), 1 | 2);
                    case TURN_NORTH, TURN_EAST, TURN_SOUTH, TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.OAK_BUTTON.defaultBlockState().setValue(ButtonBlock.FACE, AttachFace.WALL).setValue(ButtonBlock.FACING, pointerToDir(specialDir)), 1 | 2);
                }
            }
            case LEVER -> {
                switch (specialDir) {
                    case TURN_UP ->
                            serverLevel.setBlock(placingPosition, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.CEILING), 1 | 2);
                    case TURN_DOWN ->
                            serverLevel.setBlock(placingPosition, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.FLOOR), 1 | 2);
                    case TURN_NORTH, TURN_EAST, TURN_SOUTH, TURN_WEST ->
                            serverLevel.setBlock(placingPosition, Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACE, AttachFace.WALL).setValue(LeverBlock.FACING, pointerToDir(specialDir)), 1 | 2);
                }
            }
            case HONEY_BLOCK -> {
                serverLevel.setBlock(placingPosition, Blocks.HONEY_BLOCK.defaultBlockState(), 1 | 2);
            }
            case OAK_DOOR -> {
                if (specialDir != DirSet.TURN_UP || specialDir != DirSet.TURN_DOWN) {
                    serverLevel.setBlock(placingPosition, Blocks.OAK_DOOR.defaultBlockState().setValue(DoorBlock.FACING, pointerToDir(specialDir)), 1 | 2);
                } else {
                    serverLevel.setBlock(placingPosition, Blocks.OAK_DOOR.defaultBlockState().setValue(DoorBlock.FACING, Direction.NORTH), 1 | 2);
                }
            }
            case IRON_DOOR -> {
                if (specialDir != DirSet.TURN_UP || specialDir != DirSet.TURN_DOWN) {
                    serverLevel.setBlock(placingPosition, Blocks.IRON_DOOR.defaultBlockState().setValue(DoorBlock.FACING, pointerToDir(specialDir)), 1 | 2);
                } else {
                    serverLevel.setBlock(placingPosition, Blocks.IRON_DOOR.defaultBlockState().setValue(DoorBlock.FACING, Direction.NORTH), 1 | 2);
                }
            }
            case NOTE_BLOCK -> {
                serverLevel.setBlock(placingPosition, Blocks.NOTE_BLOCK.defaultBlockState(), 1 | 2);
            }
            case DAYLIGHT_SENSOR -> {
                serverLevel.setBlock(placingPosition, Blocks.DAYLIGHT_DETECTOR.defaultBlockState(), 1 | 2);
            }
            case STONE_PRESSURE_PLATE -> {
                serverLevel.setBlock(placingPosition, Blocks.STONE_PRESSURE_PLATE.defaultBlockState(), 1 | 2);
            }
            case OAK_PRESSURE_PLATE -> {
                serverLevel.setBlock(placingPosition, Blocks.OAK_PRESSURE_PLATE.defaultBlockState(), 1 | 2);
            }
            case OAK_TRAPDOOR -> {
                if (specialDir == DirSet.TURN_UP) {
                    serverLevel.setBlock(placingPosition, Blocks.OAK_TRAPDOOR.defaultBlockState().setValue(TrapDoorBlock.HALF, Half.TOP), 1 | 2);
                } else if (specialDir == DirSet.TURN_DOWN) {
                    serverLevel.setBlock(placingPosition, Blocks.OAK_TRAPDOOR.defaultBlockState().setValue(TrapDoorBlock.HALF, Half.BOTTOM), 1 | 2);
                }
                serverLevel.setBlock(placingPosition, Blocks.OAK_TRAPDOOR.defaultBlockState().setValue(TrapDoorBlock.FACING, pointerToDir(specialDir)).setValue(TrapDoorBlock.HALF, Half.TOP), 1 | 2);
            }
            case IRON_TRAPDOOR -> {
                if (specialDir == DirSet.TURN_UP) {
                    serverLevel.setBlock(placingPosition, Blocks.IRON_TRAPDOOR.defaultBlockState().setValue(TrapDoorBlock.HALF, Half.TOP), 1 | 2);
                } else if (specialDir == DirSet.TURN_DOWN) {
                    serverLevel.setBlock(placingPosition, Blocks.IRON_TRAPDOOR.defaultBlockState().setValue(TrapDoorBlock.HALF, Half.BOTTOM), 1 | 2);
                }
                serverLevel.setBlock(placingPosition, Blocks.IRON_TRAPDOOR.defaultBlockState().setValue(TrapDoorBlock.FACING, pointerToDir(specialDir)).setValue(TrapDoorBlock.HALF, Half.TOP), 1 | 2);
            }
            case SAND -> {
                serverLevel.setBlock(placingPosition, Blocks.SAND.defaultBlockState(), 1 | 2);
            }
            // fallback
            case NULL -> Minecraft.getInstance().player.sendSystemMessage(Component.literal("Error : null provided as block to place"));
        }
        AIGenPointer.isActing = false;
    }

    public static BlockSet stringToBlock(String block) {
        switch (block) {
            case "GREEN_WOOL" -> {
                return BlockSet.GREEN_WOOL;
            }
            case "GLASS" -> {
                return BlockSet.GLASS;
            }
            case "SLAB" -> {
                return BlockSet.SLAB;
            }
            case "PISTON" -> {
                return BlockSet.PISTON;
            }
            case "STICKY_PISTON" -> {
                return BlockSet.STICKY_PISTON;
            }
            case "SLIME_BLOCK" -> {
                return BlockSet.SLIME_BLOCK;
            }
            case "REDSTONE_DUST" -> {
                return BlockSet.REDSTONE_DUST;
            }
            case "GREDSTONE_TORCH" -> {
                return BlockSet.REDSTONE_TORCH;
            }
            case "OBSERVER" -> {
                return BlockSet.OBSERVER;
            }
            case "REDSTONE_BLOCK" -> {
                return BlockSet.REDSTONE_BLOCK;
            }
            case "REDSTONE_REPEATER_1" -> {
                return BlockSet.REDSTONE_REPEATER_1;
            }
            case "REDSTONE_REPEATER_2" -> {
                return BlockSet.REDSTONE_REPEATER_2;
            }
            case "REDSTONE_REPEATER_3" -> {
                return BlockSet.REDSTONE_REPEATER_3;
            }
            case "REDSTONE_REPEATER_4" -> {
                return BlockSet.REDSTONE_REPEATER_4;
            }
            case "REDSTONE_COMPARATOR_SUBTRACT" -> {
                return BlockSet.REDSTONE_COMPARATOR_SUBTRACT;
            }
            case "REDSTONE_COMPARATOR_COMPARE" -> {
                return BlockSet.REDSTONE_COMPARATOR_COMPARE;
            }
            case "REDSTONE_LAMP" -> {
                return BlockSet.REDSTONE_LAMP;
            }
            case "HOPPER" -> {
                return BlockSet.HOPPER;
            }
            case "DISPENSER" -> {
                return BlockSet.DISPENSER;
            }
            case "DROPPER" -> {
                return BlockSet.DROPPER;
            }
            case "BARREL" -> {
                return BlockSet.BARREL;
            }
            case "TARGET" -> {
                return BlockSet.TARGET;
            }
            case "STONE_BUTTON" -> {
                return BlockSet.STONE_BUTTON;
            }
            case "OAK_BUTTON" -> {
                return BlockSet.OAK_BUTTON;
            }
            case "LEVER" -> {
                return BlockSet.LEVER;
            }
            case "HONEY_BLOCK" -> {
                return BlockSet.HONEY_BLOCK;
            }
            case "STONE_PRESSURE_PLATE" -> {
                return BlockSet.STONE_PRESSURE_PLATE;
            }
            case "OAK_PRESSURE_PLATE" -> {
                return BlockSet.OAK_PRESSURE_PLATE;
            }
            case "NOTE_BLOCK" -> {
                return BlockSet.NOTE_BLOCK;
            }
            case "OAK_TRAPDOOR" -> {
                return BlockSet.OAK_TRAPDOOR;
            }
            case "IRON_TRAPDOOR" -> {
                return BlockSet.IRON_TRAPDOOR;
            }
            case "IRON_DOOR" -> {
                return BlockSet.IRON_DOOR;
            }
            case "OAK_DOOR" -> {
                return BlockSet.OAK_DOOR;
            }
            case "DAYLIGHT_SENSOR" -> {
                return BlockSet.DAYLIGHT_SENSOR;
            }
            case "SAND" -> {
                return BlockSet.SAND;
            }
        }
        return BlockSet.NULL;
    }
}
