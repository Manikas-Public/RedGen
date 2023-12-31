package com.manikas.redgen.entity.aigenerator;

//Definitions

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.AIGenPointer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RedGen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ActionDefinitions {
    public static DirSet selectedDir = DirSet.TURN_UP;
    public static BlockSet selectedBlock = BlockSet.REDSTONE_BLOCK;

    @SubscribeEvent
    public static AIGenPointer getEntity(EntityJoinLevelEvent event){
        if (event.getEntity() instanceof AIGenPointer) {
            return (AIGenPointer) event.getEntity();
        }else {
            return null;
        }
    }

    public static void performAction(ActionSet selectedAction, AIGenPointer pointerEntity, BlockSet selectedPlaceable, ServerLevel levelForPlacing){
        double pointerX = pointerEntity.getX();
        double pointerY = pointerEntity.getY();
        double pointerZ = pointerEntity.getZ();

        switch (selectedAction){
            case FORWARD -> {
                switch (selectedDir) {
                    case TURN_UP -> pointerEntity.setPos(pointerX,pointerY + 1, pointerZ);
                    case TURN_DOWN -> pointerEntity.setPos(pointerX, pointerY - 1, pointerZ);
                    case TURN_NORTH -> pointerEntity.setPos(pointerX, pointerY, pointerZ - 1);
                    case TURN_EAST -> pointerEntity.setPos(pointerX + 1,pointerY, pointerZ);
                    case TURN_SOUTH -> pointerEntity.setPos(pointerX,pointerY, pointerZ + 1);
                    case TURN_WEST -> pointerEntity.setPos(pointerX - 1, pointerY, pointerZ);
                }
            }
            case TURN_UP ->
                selectedDir = DirSet.TURN_UP;
            case TURN_DOWN ->
                selectedDir = DirSet.TURN_DOWN;
            case TURN_NORTH ->
                selectedDir = DirSet.TURN_NORTH;
            case TURN_EAST ->
                selectedDir = DirSet.TURN_EAST;
            case TURN_SOUTH ->
                selectedDir = DirSet.TURN_SOUTH;
            case TURN_WEST ->
                selectedDir = DirSet.TURN_WEST;
            case PLACE ->
                BlockPlaceDefinitions.placeBlock(selectedDir,selectedPlaceable, pointerEntity, levelForPlacing);
        }
    }
}
