package com.manikas.redgen.events;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.commands.SafeRemoveCommand;
import com.manikas.redgen.commands.ScanCommand;
import com.manikas.redgen.commands.StartGenCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = RedGen.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvenBusEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event){
        new StartGenCommand(event.getDispatcher());
        new SafeRemoveCommand(event.getDispatcher());
        new ScanCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
