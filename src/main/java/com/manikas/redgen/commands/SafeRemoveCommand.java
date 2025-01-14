package com.manikas.redgen.commands;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.AIGenPointer;
import com.manikas.redgen.entity.aigenerator.ActionDefinitions;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RedGen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SafeRemoveCommand {
    public SafeRemoveCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("redgen.remove")
                .executes(this::execute));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        if(ActionDefinitions.pointer != null) {
            ActionDefinitions.pointer.kill();
            ActionDefinitions.pointer = null;

            context.getSource().sendSuccess(() -> Component.translatable("cmd.redgen.remove.success"), true);
            return 1;
        }else {
            context.getSource().sendFailure(Component.translatable("cmd.redgen.nopointer_error"));
            return -1;
        }
    }
}