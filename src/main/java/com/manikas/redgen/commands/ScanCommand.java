package com.manikas.redgen.commands;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.AIGenPointer;
import com.manikas.redgen.entity.aigenerator.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = RedGen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ScanCommand {
    private static final ActionDefinitions actionDefinitions = new ActionDefinitions();
    public static AIGenPointer useablePointerEntity;

    public static String scanResult = null;

    @SubscribeEvent
    public static void getPointer(EntityJoinLevelEvent event){
        useablePointerEntity = ActionDefinitions.pointer;
    }

    public ScanCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("redgen.scan").then(Commands.argument("bounds1", BlockPosArgument.blockPos()).then(Commands.argument("bounds2", BlockPosArgument.blockPos()).then(Commands.argument("scan_name", StringArgumentType.string()).executes(this::execute)))));
    }

    public static int toDirMultiplier_bool(boolean bool){
        if (bool){
            return -1;
        } else {
            return 1;
        }
    }

    public static void appendAction(StringBuilder output, DirSet direction, int repeats) {
        output.append(direction).append(",");
        if (repeats != 0) {
            for (int a = 0; a < repeats; a++) {
                output.append(ActionSet.FORWARD).append(",");
            }
        }
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (useablePointerEntity != null) {
            String pointerName = useablePointerEntity.getName().toString();

            Minecraft.getInstance().player.sendSystemMessage(Component.literal(StringArgumentType.getString(context, "scan_name")));

            int repeatsx = Math.abs(BlockPosArgument.getBlockPos(context, "bounds1").getX() - BlockPosArgument.getBlockPos(context, "bounds2").getX()) + 1;
            int repeatsy = Math.abs(BlockPosArgument.getBlockPos(context, "bounds1").getY() - BlockPosArgument.getBlockPos(context, "bounds2").getY()) + 1;
            int repeatsz = Math.abs(BlockPosArgument.getBlockPos(context, "bounds1").getZ() - BlockPosArgument.getBlockPos(context, "bounds2").getZ()) + 1;

            int x1 = BlockPosArgument.getBlockPos(context, "bounds1").getX();
            int y1 = BlockPosArgument.getBlockPos(context, "bounds1").getY();
            int z1 = BlockPosArgument.getBlockPos(context, "bounds1").getZ();

            int x2 = BlockPosArgument.getBlockPos(context, "bounds2").getX();
            int y2 = BlockPosArgument.getBlockPos(context, "bounds2").getY();
            int z2 = BlockPosArgument.getBlockPos(context, "bounds2").getZ();

            boolean islessx = x1 > x2;
            boolean islessy = y1 > y2;
            boolean islessz = z1 > z2;

            int indexX = 0;
            int indexY = 0;
//            int indexZ = 0;

//            BlockState[] currentScan = new BlockState[repeatsx*repeatsy*repeatsz];

            List<BlockArgs> scannedBlocks = new ArrayList<BlockArgs>(repeatsx*repeatsy*repeatsz);


            // Main scan loop
            for (int z = 0; z < repeatsz; z++) {
                useablePointerEntity.setPos(x1, y1, z1 + Math.floorDiv(indexX, repeatsy)*toDirMultiplier_bool(islessz));
                for (int y = 0; y < repeatsy; y++) {
                    useablePointerEntity.setPos(x1, y1 + (indexX - Math.floorDiv(indexX, repeatsy)*repeatsy)*toDirMultiplier_bool(islessy), useablePointerEntity.getZ());
                    for (int x = indexX * repeatsx; x < repeatsx * indexX + repeatsx; x++) {
                        BlockState scannedBlockState = (context.getSource().getLevel().getBlockState(new BlockPos(useablePointerEntity.getBlockX(), useablePointerEntity.getBlockY(), useablePointerEntity.getBlockZ())));
                        BlockArgs scannedBlockProperties = ScanConverter.toBlockArgs(scannedBlockState);

                        scannedBlocks.add(new BlockArgs(scannedBlockProperties.getDefinition(), scannedBlockProperties.getRotation(), new BlockPos(useablePointerEntity.getBlockX(), useablePointerEntity.getBlockY(), useablePointerEntity.getBlockZ())));

                        //debug
                        ActionDefinitions.performAction(ActionSet.PLACE, useablePointerEntity, BlockSet.GLASS, source.getLevel());

                        useablePointerEntity.setPos(new Vec3(useablePointerEntity.getBlockX() + toDirMultiplier_bool(islessx), useablePointerEntity.getBlockY(), useablePointerEntity.getBlockZ()));
                    }
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Full Loop X " + indexX + " at " + useablePointerEntity.getBlockX() + " " + useablePointerEntity.getBlockY() + " " + useablePointerEntity.getBlockZ()));
                    indexX++;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                indexY++;
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Full Loop Y " + indexY + " at " + useablePointerEntity.getBlockX() + " " + useablePointerEntity.getBlockY() + " " + useablePointerEntity.getBlockZ()));
            }

            // Consolidation process loop
            scanResult = StringArgumentType.getString(context, "scan_name") + ",";

            StringBuilder scanBuilder = new StringBuilder(scanResult);

            int resultIndex = 0;
            int lastBlock = 0;

            for (BlockArgs currentBlock : scannedBlocks) {
                if (currentBlock != null) {
                    if (currentBlock.getDefinition() != BlockSet.NULL) {
                        if (lastBlock != resultIndex) {
                            int adjustedx = currentBlock.getRelativePos().getX();
                            int adjustedy = currentBlock.getRelativePos().getY();
                            int adjustedz = currentBlock.getRelativePos().getZ();

                            int adjustedlastx;
                            int adjustedlasty;
                            int adjustedlastz;

                            if (scannedBlocks.get(lastBlock).getRelativePos() != null) {
                                adjustedlastx = scannedBlocks.get(lastBlock).getRelativePos().getX();
                                adjustedlasty = scannedBlocks.get(lastBlock).getRelativePos().getY();
                                adjustedlastz = scannedBlocks.get(lastBlock).getRelativePos().getZ();
                            } else {
                                adjustedlastx = x1;
                                adjustedlasty = y1;
                                adjustedlastz = z1;
                            }

                            int moveX = adjustedx - adjustedlastx;
                            int moveY = adjustedy - adjustedlasty;
                            int moveZ = adjustedz - adjustedlastz;

                            lastBlock = resultIndex;

                            // adding X coordinate movements
                            if (moveX > 0) {
                                    //east
                                ScanCommand.appendAction(scanBuilder, DirSet.TURN_EAST, moveX);
                            } else if (moveX < 0) {
                                    //west
                                ScanCommand.appendAction(scanBuilder, DirSet.TURN_WEST, Math.abs(moveX));
                            }

                            // adding Y coordinate movements
                            if (moveY != 0) {
                                if (moveY > 0) {
                                    //up
                                    ScanCommand.appendAction(scanBuilder, DirSet.TURN_UP, moveY);
                                } else {
                                    //down
                                    ScanCommand.appendAction(scanBuilder, DirSet.TURN_DOWN, Math.abs(moveY));
                                }
                            }

                            // adding Z coordinate movements
                            if (moveZ != 0) {
                                if (moveZ > 0) {
                                    //south
                                    ScanCommand.appendAction(scanBuilder, DirSet.TURN_SOUTH, moveZ);
                                } else {
                                    //north
                                    ScanCommand.appendAction(scanBuilder, DirSet.TURN_NORTH, Math.abs(moveZ));
                                }
                            }
                        }
                        scanBuilder.append(currentBlock.getRotation()).append(",").append(currentBlock.getDefinition()).append(",");
                    }
                }
                resultIndex++;
            }

            scanResult = scanBuilder.toString();

            // Saving scanResult file

            File scanOut  = new File("C:\\Redgen_Data\\" + StringArgumentType.getString(context, "scan_name") + ".txt");

            try {
                if (scanOut.createNewFile()) {
                    System.out.println("Created new DataSet : " + scanOut.getName());
                } else {
                    System.out.println("DataSet already exists");
                }
            } catch (IOException e) {
                System.out.println("Error : failed creating DataSet - " + scanOut.getName() + "; " + e);
            }

            // Writing to scanResult

            try {
                FileWriter scanWriter = new FileWriter("C:\\Redgen_Data\\" + StringArgumentType.getString(context, "scan_name") + ".txt");
                scanWriter.write(scanResult);
                scanWriter.close();
                System.out.println("Successfully written scanResults to - " + scanWriter);
            } catch (IOException e) {
                System.out.println("Error : failed writing to DataSet - " + scanOut.getName() + "; " + e);
            }

            // Finishing function

            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Finished scan (length - " + scannedBlocks.size() + ")" + "with resulting string : " + scanResult));

            context.getSource().sendSuccess(() -> Component.literal("Last Pointer position" + " at : x " + useablePointerEntity.getBlockX() + " y " + useablePointerEntity.getBlockY() + " z " + useablePointerEntity.getBlockZ()), true);
            return 1;
        }else {
            context.getSource().sendFailure(Component.translatable("cmd.redgen.nopointer_error"));
            return -1;
        }
    }
}