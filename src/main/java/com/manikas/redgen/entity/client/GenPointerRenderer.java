package com.manikas.redgen.entity.client;

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.AIGenPointer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GenPointerRenderer extends MobRenderer<AIGenPointer,AIGenPointerModel<AIGenPointer>> {
    public GenPointerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AIGenPointerModel<>(pContext.bakeLayer(ModModelLayers.GENPOINTER_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(AIGenPointer p_114482_) {
        return new ResourceLocation(RedGen.MOD_ID, "textures/ai_genpointer.png");
    }
}
