package com.goldkl.soymilk.capability.skillenergy;

import com.goldkl.soymilk.registries.AttributeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class PlayerSkillEnergy {
    private double energy;
    public static final String NBT_KEY_SKILL_ENERGY = "soymilkskillenergy";
    public PlayerSkillEnergy(double energy) {
        this.energy = energy;
    }
    public PlayerSkillEnergy() {
        this.energy = 0;
    }
    public double getEnergy() {
        return energy;
    }
    public double getMaxEnergy(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(AttributeRegistry.MAX_SKILL_ENERGY.get());
        return attributeInstance!=null?attributeInstance.getValue():0.0;
    }
    public void setEnergy(Player player,double energy) {
        this.energy = Math.max(0.0,Math.min(getMaxEnergy(player),energy));
    }
    public void addEnergy(Player player,double energyadd) {
        this.energy = Math.max(0.0,Math.min(getMaxEnergy(player),energy+energyadd));
    }
    public void saveNBTData(CompoundTag tag) {;
        tag.put(NBT_KEY_SKILL_ENERGY, DoubleTag.valueOf(energy));
    }
    public void loadNBTData(CompoundTag tag) {
        energy = tag.getDouble(NBT_KEY_SKILL_ENERGY);
    }
}
