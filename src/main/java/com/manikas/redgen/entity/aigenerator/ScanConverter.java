package com.manikas.redgen.entity.aigenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Half;

import java.awt.*;
import java.util.Objects;

public class ScanConverter {
    private static DirSet dirToPointer(BlockState directionalBlock, String type){
        if (Objects.equals(type, "Normal")) {
            switch (directionalBlock.getValue(DirectionalBlock.FACING)){
                case UP -> {
                    return DirSet.TURN_UP;
                }
                case DOWN -> {
                    return DirSet.TURN_DOWN;
                }
                case NORTH -> {
                    return DirSet.TURN_NORTH;
                }
                case EAST -> {
                    return DirSet.TURN_EAST;
                }
                case SOUTH -> {
                    return DirSet.TURN_SOUTH;
                }
                case WEST -> {
                    return DirSet.TURN_WEST;
                }
            }
        } else if (Objects.equals(type, "Horizontal")){
            switch (directionalBlock.getValue(HorizontalDirectionalBlock.FACING)){
                case NORTH -> {
                    return DirSet.TURN_NORTH;
                }
                case EAST -> {
                    return DirSet.TURN_EAST;
                }
                case SOUTH -> {
                    return DirSet.TURN_SOUTH;
                }
                case WEST -> {
                    return DirSet.TURN_WEST;
                }
            }
        } else if (Objects.equals(type, "Button")){
                switch (directionalBlock.getValue(ButtonBlock.FACE)) {
                    case FLOOR -> {
                        return DirSet.TURN_DOWN;
                    }
                    case CEILING -> {
                        return DirSet.TURN_UP;
                    }
                    case WALL -> {
                        switch (directionalBlock.getValue(ButtonBlock.FACING)) {
                            case DOWN, NORTH, UP -> {
                                return DirSet.TURN_NORTH;
                            }
                            case SOUTH -> {
                                return DirSet.TURN_SOUTH;
                            }
                            case WEST -> {
                                return DirSet.TURN_WEST;
                            }
                            case EAST -> {
                                return DirSet.TURN_EAST;
                            }
                        };
                    }
                }
        } else if (Objects.equals(type, "Lever")) {
            switch (directionalBlock.getValue(LeverBlock.FACE)) {
                case FLOOR -> {
                    return DirSet.TURN_DOWN;
                }
                case CEILING -> {
                    return DirSet.TURN_UP;
                }
                case WALL -> {
                    switch (directionalBlock.getValue(LeverBlock.FACING)) {
                        case DOWN, NORTH, UP -> {
                            return DirSet.TURN_NORTH;
                        }
                        case SOUTH -> {
                            return DirSet.TURN_SOUTH;
                        }
                        case WEST -> {
                            return DirSet.TURN_WEST;
                        }
                        case EAST -> {
                            return DirSet.TURN_EAST;
                        }
                    };
                }
            }
        } else if (Objects.equals(type, "Trap")) {
            if (directionalBlock.getValue(TrapDoorBlock.HALF) == Half.TOP) {
                switch (directionalBlock.getValue(TrapDoorBlock.FACING)) {
                    case NORTH -> {
                        return DirSet.TURN_NORTH;
                    }
                    case EAST -> {
                        return DirSet.TURN_EAST;
                    }
                    case SOUTH -> {
                        return DirSet.TURN_SOUTH;
                    }
                    case WEST -> {
                        return DirSet.TURN_WEST;
                    }
                }
                return DirSet.TURN_UP;
            } else {
                return DirSet.TURN_DOWN;
            }
        } else if (Objects.equals(type, "Door")) {
            switch (directionalBlock.getValue(DoorBlock.FACING)) {
                case NORTH -> {
                    return DirSet.TURN_NORTH;
                }
                case EAST -> {
                    return DirSet.TURN_EAST;
                }
                case SOUTH -> {
                    return DirSet.TURN_SOUTH;
                }
                case WEST -> {
                    return DirSet.TURN_WEST;
                }
            }
        }
        return null;
    }

    public static BlockArgs toBlockArgs(BlockState block){
        BlockPos nullPosition = new BlockPos(0, 0, 0);
        if (block.getBlock() == Blocks.AIR) {
            return new BlockArgs(BlockSet.NULL, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.LIME_WOOL || block.getBlock() == Blocks.GREEN_WOOL) {
            return new BlockArgs(BlockSet.GREEN_WOOL, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.GLASS) {
            return new BlockArgs(BlockSet.GLASS, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.STONE_SLAB || block.getBlock() == Blocks.SMOOTH_STONE_SLAB) {
            return new BlockArgs(BlockSet.SLAB, switch (block.getValue(SlabBlock.TYPE)){
                case TOP -> DirSet.TURN_UP;
                case BOTTOM -> DirSet.TURN_DOWN;
                case DOUBLE -> DirSet.TURN_NORTH;
            }, nullPosition);
        } else if (block.getBlock() == Blocks.PISTON) {
            return new BlockArgs(BlockSet.PISTON, dirToPointer(block, "Normal"), nullPosition);
        } else if (block.getBlock() == Blocks.STICKY_PISTON) {
            return new BlockArgs(BlockSet.STICKY_PISTON, dirToPointer(block, "Normal"), nullPosition);
        } else if (block.getBlock() == Blocks.REDSTONE_WIRE) {
            return new BlockArgs (BlockSet.REDSTONE_DUST, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.REDSTONE_BLOCK) {
            return new BlockArgs (BlockSet.REDSTONE_BLOCK, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.REDSTONE_TORCH) {
            return new BlockArgs(BlockSet.REDSTONE_TORCH, DirSet.TURN_DOWN, nullPosition);
        } else if (block.getBlock() == Blocks.REDSTONE_WALL_TORCH) {
            return new BlockArgs(BlockSet.REDSTONE_TORCH, dirToPointer(block, "Horizontal"), nullPosition);
        } else if (block.getBlock() == Blocks.REPEATER) {
            switch (block.getValue(RepeaterBlock.DELAY)){
                case 1 :
                    return new BlockArgs(BlockSet.REDSTONE_REPEATER_1, dirToPointer(block, "Horizontal"), nullPosition);
                case 2 :
                    return new BlockArgs(BlockSet.REDSTONE_REPEATER_2, dirToPointer(block, "Horizontal"), nullPosition);
                case 3 :
                    return new BlockArgs(BlockSet.REDSTONE_REPEATER_3, dirToPointer(block, "Horizontal"), nullPosition);
                case 4 :
                    return new BlockArgs(BlockSet.REDSTONE_REPEATER_4, dirToPointer(block, "Horizontal"), nullPosition);
            }
        } else if (block.getBlock() == Blocks.SLIME_BLOCK) {
            return new BlockArgs(BlockSet.SLIME_BLOCK, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.COMPARATOR) {
            return switch (block.getValue(ComparatorBlock.MODE)) {
                case COMPARE -> new BlockArgs(BlockSet.REDSTONE_COMPARATOR_COMPARE, dirToPointer(block, "Horizontal"), nullPosition);
                case SUBTRACT -> new BlockArgs(BlockSet.REDSTONE_COMPARATOR_SUBTRACT, dirToPointer(block, "Horizontal"), nullPosition);
            };
        } else if (block.getBlock() == Blocks.OBSERVER) {
            return new BlockArgs(BlockSet.OBSERVER, dirToPointer(block, "Normal"), nullPosition);
        } else if (block.getBlock() == Blocks.REDSTONE_LAMP) {
            return new BlockArgs(BlockSet.REDSTONE_LAMP, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.HOPPER) {
            return new BlockArgs(BlockSet.HOPPER, switch (block.getValue(HopperBlock.FACING)){
                case DOWN, UP -> DirSet.TURN_DOWN;
                case NORTH -> DirSet.TURN_NORTH;
                case SOUTH -> DirSet.TURN_SOUTH;
                case WEST -> DirSet.TURN_WEST;
                case EAST -> DirSet.TURN_EAST;
            }, nullPosition);
        } else if (block.getBlock() == Blocks.DISPENSER) {
            return new BlockArgs(BlockSet.DISPENSER, dirToPointer(block, "Normal"), nullPosition);
        } else if (block.getBlock() == Blocks.DROPPER) {
            return new BlockArgs(BlockSet.DROPPER, dirToPointer(block, "Normal"), nullPosition);
        } else if (block.getBlock() == Blocks.BARREL) {
            return new BlockArgs(BlockSet.BARREL, dirToPointer(block, "Normal"), nullPosition);
        } else if (block.getBlock() == Blocks.TARGET) {
            return new BlockArgs(BlockSet.TARGET, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.STONE_BUTTON || block.getBlock() == Blocks.POLISHED_BLACKSTONE_BUTTON){
            return new BlockArgs(BlockSet.STONE_BUTTON, dirToPointer(block, "Button"), nullPosition);
        } else if (block.getBlock() == Blocks.OAK_BUTTON || block.getBlock() == Blocks.DARK_OAK_BUTTON || block.getBlock() == Blocks.ACACIA_BUTTON || block.getBlock() == Blocks.BIRCH_BUTTON || block.getBlock() == Blocks.SPRUCE_BUTTON || block.getBlock() == Blocks.JUNGLE_BUTTON){
            return new BlockArgs(BlockSet.OAK_BUTTON, dirToPointer(block, "Button"), nullPosition);
        } else if (block.getBlock() == Blocks.LEVER) {
            return new BlockArgs(BlockSet.LEVER, dirToPointer(block, "Lever"), nullPosition);
        } else if (block.getBlock() == Blocks.HONEY_BLOCK) {
            return  new BlockArgs(BlockSet.HONEY_BLOCK, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.STONE_PRESSURE_PLATE) {
            return new BlockArgs(BlockSet.STONE_PRESSURE_PLATE, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.OAK_PRESSURE_PLATE) {
            return new BlockArgs(BlockSet.OAK_PRESSURE_PLATE, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.NOTE_BLOCK) {
            return new BlockArgs(BlockSet.NOTE_BLOCK, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.OAK_TRAPDOOR) {
            return new BlockArgs(BlockSet.OAK_TRAPDOOR, dirToPointer(block, "Trap"), nullPosition);
        } else if (block.getBlock() == Blocks.IRON_TRAPDOOR) {
            return new BlockArgs(BlockSet.IRON_TRAPDOOR, dirToPointer(block, "Trap"), nullPosition);
        } else if (block.getBlock() == Blocks.IRON_DOOR) {
            if (block.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
                return new BlockArgs(BlockSet.IRON_DOOR, dirToPointer(block, "Door"), nullPosition);
            } else {
                return new BlockArgs(BlockSet.NULL, DirSet.NULL, nullPosition);
            }
        } else if (block.getBlock() == Blocks.OAK_DOOR) {
            if (block.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
                return new BlockArgs(BlockSet.OAK_DOOR, dirToPointer(block, "Door"), nullPosition);
            } else {
                return new BlockArgs(BlockSet.NULL, DirSet.NULL, nullPosition);
            }
        } else if (block.getBlock() == Blocks.DAYLIGHT_DETECTOR) {
            return new BlockArgs(BlockSet.DAYLIGHT_SENSOR, DirSet.NULL, nullPosition);
        } else if (block.getBlock() == Blocks.SAND) {
            return new BlockArgs(BlockSet.SAND, DirSet.NULL, nullPosition);
        }

        // returns null if not recognizing block
        return new BlockArgs(BlockSet.NULL, DirSet.NULL, nullPosition);
    }
}
