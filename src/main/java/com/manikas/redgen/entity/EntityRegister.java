package com.manikas.redgen.entity;

import com.manikas.redgen.RedGen;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RedGen.MOD_ID);

    public static final RegistryObject<EntityType<AIGenPointer>> GENPOINTER =
            ENTITY_TYPES.register("ai_genpointer", () -> EntityType.Builder.of(AIGenPointer::new, MobCategory.MISC).sized(1f, 1f).build("ai_genpointer"));
    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
