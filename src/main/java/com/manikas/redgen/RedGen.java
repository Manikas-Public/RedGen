package com.manikas.redgen;

import com.manikas.redgen.entity.EntityRegister;
import com.manikas.redgen.entity.client.GenPointerRenderer;
import com.manikas.redgen.item.ItemRegister;
import com.manikas.redgen.item.ModCreativeModeTabs;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RedGen.MOD_ID)
public class RedGen
{
    public static final String MOD_ID = "redgen";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static File aiData = new File("C:\\Redgen_Data");

    public RedGen()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        EntityRegister.register(modEventBus);
        ItemRegister.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("Loading RedGen...");

        String queryScript = """
                import sys
                import os

                from llama_index.core import VectorStoreIndex, SimpleDirectoryReader
                from llama_index.core import StorageContext, load_index_from_storage

                os.environ['OPENAI_API_KEY'] = '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'

                PERSIST_DIR = "C:/Redgen_Data/llamaindex"

                prompt = sys.argv[1]

                if not os.path.exists(PERSIST_DIR):
                    redgendata = SimpleDirectoryReader("C:/Redgen_Data").load_data()
                    index = VectorStoreIndex.from_documents(redgendata)
                    index.storage_context.persist(persist_dir=PERSIST_DIR)
                else:
                    storedindex = StorageContext.from_defaults(persist_dir=PERSIST_DIR)
                    index = load_index_from_storage(storedindex)

                queryer = index.as_query_engine()
                response = queryer.query("Give sequence of actions, each separated by a comma, that has a name similar to : " + prompt)

                print(response)
                """;

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));

        File querySetup = new File("queryGPT.py");
        // Setting up initial data directory

        if (aiData.mkdir()) {
            LOGGER.info("Successfully made Redgen Directory");
        } else {
            LOGGER.warn("Error : failed Redgen Directory creation OR Directory already exists");
        }

        try {
            if (querySetup.createNewFile()) {
                try {
                    FileWriter queryScriptW = new FileWriter(querySetup);
                    queryScriptW.write(queryScript);
                    queryScriptW.close();
                    LOGGER.info("Successfully set up query script");
                } catch (IOException e) {
                    LOGGER.error("Error : failed writing query script");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event){
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.warn("RedGen : this is a singleplayer only tested mod, errors may occur when running on a server");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(EntityRegister.GENPOINTER.get(), GenPointerRenderer::new);
        }
    }
}
