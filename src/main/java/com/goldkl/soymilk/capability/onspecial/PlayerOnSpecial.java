package com.goldkl.soymilk.capability.onspecial;

import com.goldkl.soymilk.registries.AttributeRegistry;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

public class PlayerOnSpecial {
    private boolean onspecial;
    public static final String NBT_KEY_ON_SPECIAL = "soymilkonspecial";
    public PlayerOnSpecial(boolean onspecial) {
        this.onspecial = onspecial;
    }
    public PlayerOnSpecial() {
        this.onspecial = false;
    }
    public boolean getOnSpecial() {
        return onspecial;
    }
    public void setOnSpecial(boolean onspecial) {
        this.onspecial = onspecial;
    }
    public void saveNBTData(CompoundTag tag) {;
        tag.put(NBT_KEY_ON_SPECIAL, ByteTag.valueOf(onspecial));
    }
    public void loadNBTData(CompoundTag tag) {
        onspecial = tag.getBoolean(NBT_KEY_ON_SPECIAL);
    }
}
