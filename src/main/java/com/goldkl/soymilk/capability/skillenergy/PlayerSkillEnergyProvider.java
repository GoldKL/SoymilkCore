package com.goldkl.soymilk.capability.skillenergy;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerSkillEnergyProvider implements ICapabilitySerializable<CompoundTag> {
    private PlayerSkillEnergy playerSkillEnergy;
    public static final Capability<PlayerSkillEnergy> PLAYER_SKILL_ENERGY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final LazyOptional<PlayerSkillEnergy> PlayerSkillEnergyLazyOptional = LazyOptional.of(() -> playerSkillEnergy);
    public PlayerSkillEnergyProvider() {
        this.playerSkillEnergy = new PlayerSkillEnergy();
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_SKILL_ENERGY_CAPABILITY) {
            return PlayerSkillEnergyLazyOptional.cast();
        }
        else {
            return LazyOptional.empty();
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        playerSkillEnergy.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        playerSkillEnergy.loadNBTData(nbt);
    }
}

