package com.goldkl.soymilk.communication;

import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergy;
import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergyProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerSpecialEnergyMessage {
    private CompoundTag capabilityNBT;

    public PlayerSpecialEnergyMessage(PlayerSpecialEnergy specialEnergy) {
        var tag = new CompoundTag();
        specialEnergy.saveNBTData(tag);
        this.capabilityNBT = tag;
    }

    public PlayerSpecialEnergyMessage(FriendlyByteBuf buffer) {
        this.capabilityNBT = buffer.readNbt();
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeNbt(capabilityNBT);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Handler.handle(this, context));
    }

    private static class Handler {
        static void handle(PlayerSpecialEnergyMessage message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                Player player = Minecraft.getInstance().player;
                assert player != null;

                player.getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).ifPresent(list -> {
                    list.loadNBTData(message.capabilityNBT);
                });
            });
            context.get().setPacketHandled(true);
        }
    }
}
