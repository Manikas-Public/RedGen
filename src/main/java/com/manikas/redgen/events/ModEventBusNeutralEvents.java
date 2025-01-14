package com.manikas.redgen.events;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.commands.StartGenCommand;
import com.manikas.redgen.entity.AIGenPointer;
import com.manikas.redgen.entity.EntityRegister;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = RedGen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusNeutralEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(EntityRegister.GENPOINTER.get(), AIGenPointer.createAttributes().build());
    }
}
