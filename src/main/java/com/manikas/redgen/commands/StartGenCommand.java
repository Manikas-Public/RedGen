package com.manikas.redgen.commands;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.AIGenPointer;
import com.manikas.redgen.entity.aigenerator.ActionDefinitions;
import com.manikas.redgen.entity.aigenerator.ActionSet;
import com.manikas.redgen.entity.aigenerator.BlockSet;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.sun.jdi.connect.Connector;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
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
        dispatcher.register(Commands.literal("redgen").then(Commands.literal("generate").then(Commands.argument("prompt", StringArgumentType.string()))
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String pointerName = useablePointerEntity.getName().toString();

        // TEST CASES FOR POINTER
        ActionDefinitions.performAction(ActionSet.TURN_DOWN, useablePointerEntity, BlockSet.NULL, source.getLevel());
        ActionDefinitions.performAction(ActionSet.PLACE, useablePointerEntity, BlockSet.DISPENSER, source.getLevel());
        //ActionDefinitions.performAction(ActionSet.TURN_NORTH, useablePointerEntity, BlockSet.NULL, source.getLevel());
        ActionDefinitions.performAction(ActionSet.FORWARD, useablePointerEntity, BlockSet.NULL, source.getLevel());
        ActionDefinitions.performAction(ActionSet.PLACE, useablePointerEntity, BlockSet.BARREL, source.getLevel());
        ActionDefinitions.performAction(ActionSet.TURN_NORTH, useablePointerEntity, BlockSet.NULL, source.getLevel());
        ActionDefinitions.performAction(ActionSet.FORWARD, useablePointerEntity, BlockSet.NULL, source.getLevel());
        ActionDefinitions.performAction(ActionSet.TURN_UP, useablePointerEntity, BlockSet.NULL, source.getLevel());
        ActionDefinitions.performAction(ActionSet.FORWARD, useablePointerEntity, BlockSet.NULL, source.getLevel());
        ActionDefinitions.performAction(ActionSet.TURN_NORTH, useablePointerEntity, BlockSet.NULL, source.getLevel());
        ActionDefinitions.performAction(ActionSet.PLACE, useablePointerEntity, BlockSet.STONE_BUTTON, source.getLevel());
        // END OF TEST CASES

        context.getSource().sendSuccess(() -> Component.literal("Current entity " + pointerName + " at " + useablePointerEntity.getBlockX() + " " + useablePointerEntity.getBlockY() + " " + useablePointerEntity.getBlockZ()), true);
        return 1;
    }
}