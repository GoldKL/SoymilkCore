package com.goldkl.soymilk.capability.specialenergy;

import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergy;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerSpecialEnergyProvider implements ICapabilitySerializable<CompoundTag> {
    private PlayerSpecialEnergy PlayerSpecialEnergy;
    public static final Capability<PlayerSpecialEnergy> PLAYER_SPECIAL_ENERGY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final LazyOptional<PlayerSpecialEnergy> PlayerSpecialEnergyLazyOptional = LazyOptional.of(() -> PlayerSpecialEnergy);
    public PlayerSpecialEnergyProvider() {
        this.PlayerSpecialEnergy = new PlayerSpecialEnergy();
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_SPECIAL_ENERGY_CAPABILITY) {
            return PlayerSpecialEnergyLazyOptional.cast();
        }
        else {
            return LazyOptional.empty();
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        PlayerSpecialEnergy.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        PlayerSpecialEnergy.loadNBTData(nbt);
    }
}

