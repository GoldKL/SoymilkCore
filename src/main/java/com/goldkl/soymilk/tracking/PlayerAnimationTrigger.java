package com.goldkl.soymilk.tracking;

import com.goldkl.soymilk.SoymilkCore;
import com.goldkl.soymilk.client.Animation;
import com.goldkl.soymilk.communication.AnimationMessage;
import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractModifier;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.UUID;

/**
 * Example, how to trigger animations on specific players
 * Always trigger animation on client-side.  Maybe as a response to a network packet or event
 */
@Mod.EventBusSubscriber(modid = SoymilkCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerAnimationTrigger {

    //We need to know when to play an animation
    //This can be anything depending on your ideas (see Emotecraft, BetterCombat ...)
    @SubscribeEvent
    public static void onChatReceived(ClientChatReceivedEvent event) {
        //Test if it is a player (main or other) and the message
        if (event.getMessage().contains(Component.literal("waving"))) {
            //UUID player = event.getSender();
            //SoymilkCore.channel.sendToServer(new AnimationMessage(player,new ResourceLocation(SoymilkCore.MODID,"waving")));
            Animation.playAnimation(Minecraft.getInstance().level.getPlayerByUUID(event.getSender()),new ResourceLocation(SoymilkCore.MODID,"waving"));
            //Get the player from Minecraft, using the chat profile ID. From network packets, you'll receive entity IDs instead of UUIDs
            //AbstractClientPlayer player = (AbstractClientPlayer)Minecraft.getInstance().level.getPlayerByUUID(event.getSender());
/*
            if (player == null) return; //The player can be null because it was a system message or because it is not loaded by this player.
            //Get the animation for that player
            var animation1 = (ModifierLayer<IAnimation>)PlayerAnimationAccess.getPlayerAssociatedData( player).get(new ResourceLocation(SoymilkCore.MODID, "first_animation"));
            var animation2 = (ModifierLayer<IAnimation>)PlayerAnimationAccess.getPlayerAssociatedData( player).get(new ResourceLocation(SoymilkCore.MODID, "third_animation"));
            if (animation1 != null) {
                //You can set an animation from anywhere ON THE CLIENT
                //Do not attempt to do this on a server, that will only fail
                //SoymilkCore.LOGGER.info("{}",tem1.bodyParts);
                animation1.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(SoymilkCore.MODID, "first_person.waving"))).setFirstPersonConfiguration(new FirstPersonConfiguration(true,true,true,true)).setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL));
                //You might use  animation.replaceAnimationWithFade(); to create fade effect instead of sudden change
                //See javadoc for details
            }
            if (animation2 != null) {
                //You can set an animation from anywhere ON THE CLIENT
                //Do not attempt to do this on a server, that will only fail
               // SoymilkCore.LOGGER.info("{}",tem1.bodyParts);
                animation2.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(SoymilkCore.MODID, "third_person.waving"))));
                //You might use  animation.replaceAnimationWithFade(); to create fade effect instead of sudden change
                //See javadoc for details
            }*/
        }
    }

    //For server-side animation playing, see Emotecraft API
}