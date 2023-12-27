package com.manikas.redgen.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

public class AIGenPointer extends Mob {
    public AIGenPointer(EntityType<? extends Mob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public final AnimationState placingBlock = new AnimationState();
    private int placingAnimTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()){

        }
    }

    @Override
    public void setInvulnerable(boolean p_20332_) {
        super.setInvulnerable(true);
    }



    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Override
    public boolean isEffectiveAi() {
        return false;
    }

    @Override
    public void setNoGravity(boolean p_20243_) {
        super.setNoGravity(true);
    }

    @Override
    public boolean canBeSeenByAnyone() {
        return false;
    }

    private void setupAnimationStates(){

    }

    @Override
    protected void registerGoals(){
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1,new LookAtPlayerGoal(this, Player.class, 2f));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createLivingAttributes()
                .add(Attributes.KNOCKBACK_RESISTANCE, 100)
                .add(Attributes.MAX_HEALTH, 5000)
                .add(Attributes.MOVEMENT_SPEED, 1)
                .add(Attributes.ARMOR_TOUGHNESS, 100)
                .add(Attributes.FOLLOW_RANGE, 5D);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEvents.BEACON_DEACTIVATE;
    }
}
