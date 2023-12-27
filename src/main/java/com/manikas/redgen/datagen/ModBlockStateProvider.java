package com.manikas.redgen.datagen;

import com.manikas.redgen.RedGen;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider  extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RedGen.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //use blockWithItem to generate for registered blocks
    }
    private void blockWithItem(RegistryObject<Block> registeredBlock){
        simpleBlockWithItem(registeredBlock.get(),cubeAll(registeredBlock.get()));
    }
}
