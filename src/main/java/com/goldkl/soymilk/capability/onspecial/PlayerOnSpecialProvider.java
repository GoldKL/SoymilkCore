package com.goldkl.soymilk.capability.onspecial;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerOnSpecialProvider implements ICapabilitySerializable<CompoundTag> {
    private PlayerOnSpecial PlayerOnSpecial;
    public static final Capability<PlayerOnSpecial> PLAYER_ON_SPECIAL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final LazyOptional<PlayerOnSpecial> PlayerOnSpecialLazyOptional = LazyOptional.of(() -> PlayerOnSpecial);
    public PlayerOnSpecialProvider() {
        this.PlayerOnSpecial = new PlayerOnSpecial();
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == PLAYER_ON_SPECIAL_CAPABILITY) {
            return PlayerOnSpecialLazyOptional.cast();
        }
        else {
            return LazyOptional.empty();
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        PlayerOnSpecial.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        PlayerOnSpecial.loadNBTData(nbt);
    }
}

