package com.goldkl.soymilk.communication;

import com.goldkl.soymilk.SoymilkCore;
import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecial;
import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecialProvider;
import com.goldkl.soymilk.client.Animation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.UUID;
import java.util.function.Supplier;

public class AnimationMessage {
    private final UUID playerId;
    private final ResourceLocation animation;

    public AnimationMessage(UUID player, ResourceLocation animation) {
        this.playerId = player;
        this.animation = animation;
    }

    public AnimationMessage(FriendlyByteBuf buffer) {
        this.playerId = buffer.readUUID();
        this.animation = buffer.readResourceLocation();
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeUUID(playerId);
        buffer.writeResourceLocation(animation);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        if(context.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            context.get().enqueueWork(() -> SoymilkCore.channel.send(PacketDistributor.ALL.noArg(), this));
        }
        else {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Handler.handle(this, context));
        }
        context.get().setPacketHandled(true);
    }

    private static class Handler {
        static void handle(AnimationMessage message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                AbstractClientPlayer player = (AbstractClientPlayer) Minecraft.getInstance().level.getPlayerByUUID(message.playerId);
                if(player != null) {
                    Animation.doAnimation(player,new ResourceLocation(message.animation.getNamespace(),message.animation.getPath()));
                }
            });
        }
    }
}
