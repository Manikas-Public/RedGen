package com.manikas.redgen.commands;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.AIGenPointer;
import com.manikas.redgen.entity.aigenerator.ActionDefinitions;
import com.manikas.redgen.entity.aigenerator.ActionSet;
import com.manikas.redgen.entity.aigenerator.BlockPlaceDefinitions;
import com.manikas.redgen.entity.aigenerator.BlockSet;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
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

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

@Mod.EventBusSubscriber(modid = RedGen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StartGenCommand {
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
        if (useablePointerEntity != null) {
            String pointerName = useablePointerEntity.getName().toString();

            Minecraft.getInstance().player.sendSystemMessage(Component.literal(StringArgumentType.getString(context, "gen_prompt")));

            StringBuilder outputStringBuilder = new StringBuilder();
            String currentData = null;

            try {
                File selectedData = new File("C:\\Redgen_Data\\" + StringArgumentType.getString(context, "gen_prompt") + ".txt");
                Scanner outputReader = new Scanner(selectedData);
                while (outputReader.hasNextLine()) {
                    currentData = outputReader.nextLine();
                    outputStringBuilder.append(currentData);
                }
                outputReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error : DataSet with name : " + StringArgumentType.getString(context, "gen_prompt") + " not found; " + e);
            }

            String outputString = outputStringBuilder.toString();

            String[] outputStringArray = outputString.split(",");

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

//            // TEST CASES FOR POINTER
//            ActionDefinitions.performAction(ActionSet.TURN_DOWN, useablePointerEntity, BlockSet.NULL, source.getLevel());
//            ActionDefinitions.performAction(ActionSet.PLACE, useablePointerEntity, BlockSet.DISPENSER, source.getLevel());
//            //ActionDefinitions.performAction(ActionSet.TURN_NORTH, useablePointerEntity, BlockSet.NULL, source.getLevel());
//            ActionDefinitions.performAction(ActionSet.FORWARD, useablePointerEntity, BlockSet.NULL, source.getLevel());
//            ActionDefinitions.performAction(ActionSet.PLACE, useablePointerEntity, BlockSet.BARREL, source.getLevel());
//            ActionDefinitions.performAction(ActionSet.TURN_NORTH, useablePointerEntity, BlockSet.NULL, source.getLevel());
//            ActionDefinitions.performAction(ActionSet.FORWARD, useablePointerEntity, BlockSet.NULL, source.getLevel());
//            ActionDefinitions.performAction(ActionSet.TURN_UP, useablePointerEntity, BlockSet.NULL, source.getLevel());
//            ActionDefinitions.performAction(ActionSet.FORWARD, useablePointerEntity, BlockSet.NULL, source.getLevel());
//            ActionDefinitions.performAction(ActionSet.TURN_NORTH, useablePointerEntity, BlockSet.NULL, source.getLevel());
//            ActionDefinitions.performAction(ActionSet.PLACE, useablePointerEntity, BlockSet.STONE_BUTTON, source.getLevel());
//            // END OF TEST CASES

            context.getSource().sendSuccess(() -> Component.literal("Current entity " + pointerName + " at " + useablePointerEntity.getBlockX() + " " + useablePointerEntity.getBlockY() + " " + useablePointerEntity.getBlockZ()), true);
            return 1;
        }else {
            context.getSource().sendFailure(Component.translatable("cmd.redgen.nopointer_error"));
            return -1;
        }
    }
}