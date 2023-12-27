package com.manikas.redgen.item;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.EntityRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RedGen.MOD_ID);

    public static final RegistryObject<Item> GENPOINTER_SPAWN_EGG = ITEMS.register("ai_genpointer_spawn_egg", () ->
            new ForgeSpawnEggItem(EntityRegister.GENPOINTER,0x8F00FF,0x550096,new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
