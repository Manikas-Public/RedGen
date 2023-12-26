package com.manikas.redgen.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;

public class AIGenPointer extends Mob {
    public AIGenPointer(EntityType<? extends Mob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder genpointeratt(){
        return Mob.createLivingAttributes()
                .add(Attributes.KNOCKBACK_RESISTANCE, 100)
                .add(Attributes.MAX_HEALTH, 5000)
                .add(Attributes.MOVEMENT_SPEED, 1);
    }


}
