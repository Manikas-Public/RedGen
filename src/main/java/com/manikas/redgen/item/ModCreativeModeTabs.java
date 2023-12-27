package com.manikas.redgen.item;

import com.manikas.redgen.RedGen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RedGen.MOD_ID);

    public static final RegistryObject<CreativeModeTab> GENCOMPONENTS = TABS.register("redgen_components", () -> CreativeModeTab.builder().icon(() -> new ItemStack(Blocks.REDSTONE_WIRE)).title(Component.translatable("creativetab.redgen_components"))
            .displayItems(((p_270258_, p_259752_) -> {
        p_259752_.accept(ItemRegister.GENPOINTER_SPAWN_EGG.get());
    })).build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
