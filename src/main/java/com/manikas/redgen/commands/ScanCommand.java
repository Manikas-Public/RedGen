package com.manikas.redgen.commands;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.AIGenPointer;
import com.manikas.redgen.entity.aigenerator.ActionDefinitions;
import com.manikas.redgen.entity.aigenerator.ActionSet;
import com.manikas.redgen.entity.aigenerator.BlockSet;
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

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = RedGen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ScanCommand {
    private static final ActionDefinitions actionDefinitions = new ActionDefinitions();
    public static AIGenPointer useablePointerEntity;

    @SubscribeEvent
    public static void getPointer(EntityJoinLevelEvent event){
        useablePointerEntity = ActionDefinitions.pointer;
    }

    public ScanCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("scan").then(Commands.argument("bounds1", BlockPosArgument.blockPos()).then(Commands.argument("bounds2", BlockPosArgument.blockPos()).then(Commands.argument("scan_name", StringArgumentType.string()).executes(this::execute)))));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (useablePointerEntity != null) {
            String pointerName = useablePointerEntity.getName().toString();

            Minecraft.getInstance().player.sendSystemMessage(Component.literal(StringArgumentType.getString(context, "scan_name")));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal((BlockPosArgument.getBlockPos(context, "bounds1")).toString()));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal((BlockPosArgument.getBlockPos(context, "bounds2")).toString()));

            int repeatsx = Math.abs(BlockPosArgument.getBlockPos(context, "bounds1").getX() - BlockPosArgument.getBlockPos(context, "bounds2").getX());
            int repeatsy = Math.abs(BlockPosArgument.getBlockPos(context, "bounds1").getY() - BlockPosArgument.getBlockPos(context, "bounds2").getY());
            int repeatsz = Math.abs(BlockPosArgument.getBlockPos(context, "bounds1").getZ() - BlockPosArgument.getBlockPos(context, "bounds2").getZ());

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

            List<BlockState> scannedBlocks = new ArrayList<BlockState>(repeatsx*repeatsy*repeatsz);

            for (int z = 0; z < repeatsz; z++) {
                useablePointerEntity.setPos(x1, y1, z1 + indexY*(islessz? -1 : 1));
                for (int y = 0; y < repeatsy; y++) {
                    useablePointerEntity.setPos(x1, y + Math.floorDiv(indexX,repeatsy)*(islessy? -1 : 1), useablePointerEntity.getZ() + indexY*(islessz? -1 : 1));
                    for (int x = indexX * repeatsx; x < repeatsx * indexX + repeatsx; x++) {
//                        context.getSource().getLevel().getBlockState(new BlockPos(useablePointerEntity.getBlockX(), useablePointerEntity.getBlockY(), useablePointerEntity.getBlockZ())).getBlock() != Blocks.AIR
                        Minecraft.getInstance().player.sendSystemMessage(Component.literal("block"));
//                        currentScan[x] = context.getSource().getLevel().getBlockState(new BlockPos(useablePointerEntity.getBlockX(), useablePointerEntity.getBlockY(), useablePointerEntity.getBlockZ()));

                        scannedBlocks.add(context.getSource().getLevel().getBlockState(new BlockPos(useablePointerEntity.getBlockX(), useablePointerEntity.getBlockY(), useablePointerEntity.getBlockZ())));

//                        if (true) {
//                            Minecraft.getInstance().player.sendSystemMessage(Component.literal("block"));
//                            currentScan[x] = context.getSource().getLevel().getBlockState(new BlockPos(useablePointerEntity.getBlockX(), useablePointerEntity.getBlockY(), useablePointerEntity.getBlockZ()));
//                        } else {
//                            Minecraft.getInstance().player.sendSystemMessage(Component.literal("air"));
//                            currentScan[x] = null;
//                        }
                        useablePointerEntity.setPos(new Vec3(useablePointerEntity.getBlockX() + (islessx? -1 : 1), useablePointerEntity.getBlockY(), useablePointerEntity.getBlockZ()));
                    }
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Full Loop X " + indexX + " at " + useablePointerEntity.getBlockX() + " " + useablePointerEntity.getBlockY() + " " + useablePointerEntity.getBlockZ()));
                    indexX++;
                }
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Full Loop Y " + indexY + " at " + useablePointerEntity.getBlockX() + " " + useablePointerEntity.getBlockY() + " " + useablePointerEntity.getBlockZ()));
                indexY++;
            }

//            Minecraft.getInstance().player.sendSystemMessage(Component.literal(currentScan[0].toString()));
            context.getSource().sendSuccess(() -> Component.literal("Finished scan " + "with first block as : " + scannedBlocks.get(0).getBlock() + "; at x " + useablePointerEntity.getBlockX() + " y " + useablePointerEntity.getBlockY() + " z " + useablePointerEntity.getBlockZ()), true);
            return 1;
        }else {
            context.getSource().sendFailure(Component.literal("Failed : no pointer entity found"));
            return -1;
        }
    }
}