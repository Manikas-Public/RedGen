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

    public static AIGenPointer pointer = null;

    @SubscribeEvent
    public static void getPointer(EntityJoinLevelEvent event){
        if (pointer == null) {
            pointer = ActionDefinitions.getEntity(event);
            if (pointer != null) {
                pointer.setYBodyRot(0);
                pointer.setYRot(0);
                pointer.setXRot(0);
                pointer.setYHeadRot(0);
            }
        }else if (ActionDefinitions.getEntity(event) != null){
            ActionDefinitions.getEntity(event).kill();
        }
    }

    private static AIGenPointer getEntity(EntityJoinLevelEvent event){
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

    public static ActionSet stringToAction(String stringAction) {
        switch (stringAction) {
            case "FORWARD" -> {
                return ActionSet.FORWARD;
            }
            case "TURN_UP" -> {
                return ActionSet.TURN_UP;
            }
            case "TURN_DOWN" -> {
                return ActionSet.TURN_DOWN;
            }
            case "TURN_NORTH" -> {
                return ActionSet.TURN_NORTH;
            }
            case "TURN_EAST" -> {
                return ActionSet.TURN_EAST;
            }
            case "TURN_SOUTH" -> {
                return ActionSet.TURN_SOUTH;
            }
            case "TURN_WEST" -> {
                return ActionSet.TURN_WEST;
            }
        }
        return ActionSet.PLACE;
    }
}
