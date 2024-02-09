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
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

            int indexX = 0;
            int indexY = 0;
//            int indexZ = 0;

            Block[] currentScan = new Block[repeatsx*repeatsy*repeatsz];

            for (int z = 0; z < repeatsz; z++) {
                for (int y = 0; y < repeatsy; y++) {
                    useablePointerEntity.setPos(BlockPosArgument.getBlockPos(context, "bounds1").getX(), BlockPosArgument.getBlockPos(context, "bounds1").getY() + Math.floorDiv(indexX,repeatsy), useablePointerEntity.getZ() + indexY);
                    for (int x = indexX * repeatsx; x < repeatsx; x++) {
                        if (context.getSource().getLevel().getBlockState(new BlockPos(useablePointerEntity.getBlockX(), useablePointerEntity.getBlockY(), useablePointerEntity.getBlockZ())).getBlock() != Blocks.AIR) {
                            currentScan[x] = context.getSource().getLevel().getBlockState(new BlockPos(useablePointerEntity.getBlockX(), useablePointerEntity.getBlockY(), useablePointerEntity.getBlockZ())).getBlock();
                        } else {
                            currentScan[x] = null;
                        }
                    }
                    indexX++;
                }
                indexY++;
            }

            Minecraft.getInstance().player.sendSystemMessage(Component.literal(currentScan[0].toString()));
            context.getSource().sendSuccess(() -> Component.literal("Current entity " + pointerName + " at " + useablePointerEntity.getBlockX() + " " + useablePointerEntity.getBlockY() + " " + useablePointerEntity.getBlockZ()), true);
            return 1;
        }else {
            context.getSource().sendFailure(Component.literal("Failed : no pointer entity found"));
            return -1;
        }
    }
}