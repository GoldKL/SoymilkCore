package com.goldkl.soymilk.communication;

import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecial;
import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecialProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerOnSpecialMessage {
    private CompoundTag capabilityNBT;

    public PlayerOnSpecialMessage(PlayerOnSpecial onspecial) {
        var tag = new CompoundTag();
        onspecial.saveNBTData(tag);
        this.capabilityNBT = tag;
    }

    public PlayerOnSpecialMessage(FriendlyByteBuf buffer) {
        this.capabilityNBT = buffer.readNbt();
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeNbt(capabilityNBT);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Handler.handle(this, context));
    }

    private static class Handler {
        static void handle(PlayerOnSpecialMessage message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                Player player = Minecraft.getInstance().player;
                assert player != null;

                player.getCapability(PlayerOnSpecialProvider.PLAYER_ON_SPECIAL_CAPABILITY).ifPresent(list -> {
                    list.loadNBTData(message.capabilityNBT);
                });
            });
            context.get().setPacketHandled(true);
        }
    }
}
