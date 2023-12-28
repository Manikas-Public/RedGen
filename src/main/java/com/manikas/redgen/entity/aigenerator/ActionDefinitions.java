package com.manikas.redgen.entity.aigenerator;

//Definitions

import com.manikas.redgen.RedGen;
import com.manikas.redgen.entity.AIGenPointer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RedGen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ActionDefinitions extends AIGenPointer {
    public static AIGenPointer pointerEntity;
    public DirSet selectedDir = DirSet.TURN_NORTH;
    public BlockSet selectedBlock = BlockSet.REDSTONE_BLOCK;
    private final BlockPlaceDefinitions blockDefinitions = new BlockPlaceDefinitions();

    public ActionDefinitions(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @SubscribeEvent
    public void getEntity(EntityJoinLevelEvent event){
        if (event.getEntity() instanceof AIGenPointer) {
            pointerEntity = (AIGenPointer) event.getEntity();
        }
    }

    public void performAction(ActionSet selectedAction){
        switch (selectedAction){
            case FORWARD -> {
                switch (selectedDir) {
                    case TURN_UP -> pointerEntity.position().add(0, 1, 0);
                    case TURN_DOWN -> pointerEntity.position().add(0, -1, 0);
                    case TURN_NORTH -> pointerEntity.position().add(0, 0, -1);
                    case TURN_EAST -> pointerEntity.position().add(1,0,0);
                    case TURN_SOUTH -> pointerEntity.position().add(0,0,1);
                    case TURN_WEST -> pointerEntity.position().add(-1,0,0);
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
                blockDefinitions.placeBlock(selectedDir,selectedBlock, pointerEntity);
        }
    }
}
