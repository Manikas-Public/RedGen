package com.manikas.redgen.commands;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.AIGenPointer;
import com.manikas.redgen.entity.aigenerator.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.logging.LogUtils;
import com.sun.jdi.connect.Connector;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

@Mod.EventBusSubscriber(modid = RedGen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StartGenCommand {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ActionDefinitions actionDefinitions = new ActionDefinitions();

    public static AIGenPointer useablePointerEntity;

    @SubscribeEvent
    public static void getPointer(EntityJoinLevelEvent event){
        useablePointerEntity = ActionDefinitions.pointer;
    }

    public StartGenCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("redgen.generate").then(Commands.argument("gen_prompt", StringArgumentType.string()).executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();

        String genPrompt = StringArgumentType.getString(context, "gen_prompt");

        if (useablePointerEntity != null) {
            String pointerName = useablePointerEntity.getName().toString();

            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("cmd.redgen.query_in_progress").append(Component.literal(genPrompt)));
            LOGGER.info("Generating " + genPrompt);

            // calls Llama-Index and gets response in a string, then splits by "," symbol
            String outputString = GPTQueryLauncher.queryAI(genPrompt);
            String[] outputStringArray = outputString.split(",");

            // pointer carries out actions
            int actionIndex = 0;

            for (String c : outputStringArray) {
                // skips prompt
                if (actionIndex > 0) {
                    if (ActionDefinitions.stringToAction(c) == ActionSet.PLACE) {
                        ActionDefinitions.performAction(ActionSet.PLACE, useablePointerEntity, BlockPlaceDefinitions.stringToBlock(c), source.getLevel());
                    } else if (ActionDefinitions.stringToAction(c) != null) {
                        ActionDefinitions.performAction(ActionDefinitions.stringToAction(c), useablePointerEntity, BlockSet.NULL, source.getLevel());
                    }
                    System.out.println(c);
                }
                actionIndex++;
            }

            context.getSource().sendSuccess(() -> Component.translatable("cmd.redgen.gen_success").append(Component.literal(" X : " + useablePointerEntity.getBlockX()+ " Y : " + useablePointerEntity.getBlockY()+ " Z : " + useablePointerEntity.getBlockZ())), true);
            LOGGER.info("Finished generating " + genPrompt);
            return 1;
        }else {
            context.getSource().sendFailure(Component.translatable("cmd.redgen.nopointer_error"));
            return -1;
        }
    }
}