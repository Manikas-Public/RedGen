package com.manikas.redgen.commands;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.AIGenPointer;
import com.manikas.redgen.entity.aigenerator.ActionDefinitions;
import com.manikas.redgen.entity.aigenerator.ActionSet;
import com.manikas.redgen.entity.aigenerator.BlockSet;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RedGen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StartGenCommand {
    private static final ActionDefinitions actionDefinitions = new ActionDefinitions();
    public static AIGenPointer useablePointerEntity = null;
    @SubscribeEvent
    public static void pointerEntitySpawn(EntityJoinLevelEvent event){
        if (useablePointerEntity == null) {
            useablePointerEntity = ActionDefinitions.getEntity(event);
        }else if (ActionDefinitions.getEntity(event) != null){
            ActionDefinitions.getEntity(event).kill();
        }
    }

    public StartGenCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("redgen").then(Commands.literal("generate")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String pointerName = useablePointerEntity.getName().toString();
        ActionDefinitions.performAction(ActionSet.FORWARD, useablePointerEntity,BlockSet.GLASS, source.getLevel());
        ActionDefinitions.performAction(ActionSet.PLACE,useablePointerEntity, BlockSet.REDSTONE_BLOCK, source.getLevel());

        context.getSource().sendSuccess(() -> Component.literal("Current entity " + pointerName + " at " + useablePointerEntity.getBlockX() + " " + useablePointerEntity.getBlockY() + " " + useablePointerEntity.getBlockZ()), true);
        return 1;
    }
}