package com.goldkl.soymilk.capability.specialenergy;

import com.goldkl.soymilk.registries.AttributeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

public class  PlayerSpecialEnergy {
    private double spEnergy;
    public static final String NBT_KEY_SPECIAL_ENERGY = "soymilkspecialenergy";
    public PlayerSpecialEnergy(double energy) {
        this.spEnergy = energy;
    }
    public PlayerSpecialEnergy() {
        this.spEnergy = 0;
    }
    public double getspEnergy() {
        return spEnergy;
    }
    public double getMaxspEnergy(Player player) {
        AttributeInstance attributeInstance = player.getAttribute(AttributeRegistry.MAX_SPECIAL_ENERGY.get());
        return attributeInstance!=null?attributeInstance.getValue():0.0;
    }
    public boolean isCanSpecial(Player player)
    {
        return Double.compare(getMaxspEnergy(player),getspEnergy())<=0;
    }
    public void setspEnergy(Player player,double energy) {
        this.spEnergy = Math.max(0.0,Math.min(getMaxspEnergy(player),energy));
    }
    public void addspEnergy(Player player,double energyadd) {
        this.spEnergy = Math.max(0.0,Math.min(getMaxspEnergy(player),spEnergy+energyadd));
    }
    public void saveNBTData(CompoundTag tag) {;
        tag.put(NBT_KEY_SPECIAL_ENERGY, DoubleTag.valueOf(spEnergy));
    }
    public void loadNBTData(CompoundTag tag) {
        spEnergy = tag.getDouble(NBT_KEY_SPECIAL_ENERGY);
    }
}
