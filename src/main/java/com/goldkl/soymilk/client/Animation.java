package com.goldkl.soymilk.client;

import com.goldkl.soymilk.SoymilkCore;
/*
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;*/
import com.goldkl.soymilk.communication.AnimationMessage;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;

public class Animation {
    static public final int FIRST_PERSON = 0b1;
    static public final int THIRD_PERSON = 0b10;
    static private final ResourceLocation FIRST_ANIMA = new ResourceLocation(SoymilkCore.MODID, "first_animation");
    static private final ResourceLocation THIRD_ANIMA = new ResourceLocation(SoymilkCore.MODID, "third_animation");
    static public void doAnimation(AbstractClientPlayer player, ResourceLocation animation, int which_person) {
        if (player == null) return;
        var ianimationf = (ModifierLayer<IAnimation>)PlayerAnimationAccess
                .getPlayerAssociatedData(player)
                .get(FIRST_ANIMA);
        var ianimationt = (ModifierLayer<IAnimation>)PlayerAnimationAccess
                .getPlayerAssociatedData(player)
                .get(THIRD_ANIMA);
        if(ianimationf != null &&(which_person & FIRST_PERSON) > 0) {
            ianimationf.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(animation.getNamespace(), "first_person."+ animation.getPath())))
                    .setFirstPersonConfiguration(new FirstPersonConfiguration(true,true,true,true))
                    .setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL));
        }
        if(ianimationt != null && (which_person & THIRD_PERSON) > 0) {
            ianimationt.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(animation.getNamespace(), "third_person." + animation.getPath()))));
        }
    }
    static public void doAnimation(AbstractClientPlayer player, ResourceLocation animation) {
        doAnimation(player, animation, FIRST_PERSON|THIRD_PERSON);
    }
    //主要用下面这个函数来播放动画，让全局都能看见，双端可用
    static public void playAnimation(Player player, ResourceLocation animation) {
        if (player == null) return;
        if (player.level().isClientSide) {
            SoymilkCore.channel.sendToServer(new AnimationMessage(player.getUUID(), animation));
        }
        else{
            SoymilkCore.channel.send(PacketDistributor.ALL.noArg(), new AnimationMessage(player.getUUID(), animation));
        }
    }
}
