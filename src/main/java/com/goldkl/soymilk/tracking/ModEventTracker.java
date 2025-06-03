package com.goldkl.soymilk.tracking;

import com.goldkl.soymilk.SoymilkCore;
import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecialProvider;
import com.goldkl.soymilk.capability.skillenergy.PlayerSkillEnergy;
import com.goldkl.soymilk.capability.skillenergy.PlayerSkillEnergyProvider;
import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergy;
import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergyProvider;
import com.goldkl.soymilk.communication.PlayerOnSpecialMessage;
import com.goldkl.soymilk.communication.PlayerSkillEnergyMessage;
import com.goldkl.soymilk.communication.PlayerSpecialEnergyMessage;
import com.goldkl.soymilk.registries.AttributeRegistry;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = SoymilkCore.MODID)
public class ModEventTracker {
    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AttributeRegistry.SKILL_CHARGE_EFFICIENCY.get());
        event.add(EntityType.PLAYER, AttributeRegistry.SPECIAL_CHARGE_EFFICIENCY.get());
        event.add(EntityType.PLAYER, AttributeRegistry.MAX_SKILL_ENERGY.get());
        event.add(EntityType.PLAYER, AttributeRegistry.MAX_SPECIAL_ENERGY.get());
    }
    @SubscribeEvent
    public static void RegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerSkillEnergyProvider.class);
        event.register(PlayerSpecialEnergyProvider.class);
        event.register(PlayerOnSpecialProvider.class);
    }
    @SubscribeEvent
    public static void setUp(FMLCommonSetupEvent event) {
        SoymilkCore.channel.messageBuilder(PlayerSkillEnergyMessage.class, 0)
                .encoder(PlayerSkillEnergyMessage::write)
                .decoder(PlayerSkillEnergyMessage::new)
                .consumerMainThread(PlayerSkillEnergyMessage::handle)
                .add();
        SoymilkCore.channel.messageBuilder(PlayerSpecialEnergyMessage.class, 1)
                .encoder(PlayerSpecialEnergyMessage::write)
                .decoder(PlayerSpecialEnergyMessage::new)
                .consumerMainThread(PlayerSpecialEnergyMessage::handle)
                .add();
        SoymilkCore.channel.messageBuilder(PlayerOnSpecialMessage.class, 2)
                .encoder(PlayerOnSpecialMessage::write)
                .decoder(PlayerOnSpecialMessage::new)
                .consumerMainThread(PlayerOnSpecialMessage::handle)
                .add();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        //Set the player construct callback. It can be a lambda function.
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                new ResourceLocation(SoymilkCore.MODID, "first_animation"),
                42,
                ModEventTracker::registerPlayerAnimation);
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                new ResourceLocation(SoymilkCore.MODID, "third_animation"),
                43,
                ModEventTracker::registerPlayerAnimation);
    }
    //This method will set your mods animation into the library.
    private static IAnimation registerPlayerAnimation(AbstractClientPlayer player) {
        //This will be invoked for every new player
        return new ModifierLayer<>();
    }

}
