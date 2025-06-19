package com.goldkl.soymilk.tracking;

import com.goldkl.soymilk.SoymilkCore;
import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecial;
import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecialProvider;
import com.goldkl.soymilk.capability.skillenergy.PlayerSkillEnergy;
import com.goldkl.soymilk.capability.skillenergy.PlayerSkillEnergyProvider;
import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergy;
import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergyProvider;
import com.goldkl.soymilk.communication.PlayerOnSpecialMessage;
import com.goldkl.soymilk.communication.PlayerSkillEnergyMessage;
import com.goldkl.soymilk.communication.PlayerSpecialEnergyMessage;
import dev.kosmx.playerAnim.api.IPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = SoymilkCore.MODID)
public class ForgeEventTracker {
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(SoymilkCore.MODID, PlayerSkillEnergy.NBT_KEY_SKILL_ENERGY),new PlayerSkillEnergyProvider());
            event.addCapability(new ResourceLocation(SoymilkCore.MODID, PlayerSpecialEnergy.NBT_KEY_SPECIAL_ENERGY),new PlayerSpecialEnergyProvider());
            event.addCapability(new ResourceLocation(SoymilkCore.MODID, PlayerOnSpecial.NBT_KEY_ON_SPECIAL),new PlayerOnSpecialProvider());
        }
    }
    /*
    寄掉后，能量清空
    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        var originalPlayer = event.getOriginal();
        originalPlayer.reviveCaps();
        event.getOriginal().getCapability(PlayerSkillEnergyProvider.PLAYER_SKILL_ENERGY_CAPABILITY).ifPresent(old -> {
            event.getEntity().getCapability(PlayerSkillEnergyProvider.PLAYER_SKILL_ENERGY_CAPABILITY).ifPresent( cap ->{
                cap.setEnergy(event.getEntity(),old.getEnergy());
            });
        });
        event.getOriginal().getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).ifPresent(old -> {
            event.getEntity().getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).ifPresent( cap ->{
                cap.setspEnergy(event.getEntity(),old.getspEnergy());
            });
        });
        originalPlayer.invalidateCaps();
    }*/
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        syncPlayerSkillEnergy(event.getEntity());
        syncPlayerSpecialEnergy(event.getEntity());
        syncPlayerOnSpecial(event.getEntity());
    }
    @SubscribeEvent
    public static void onPlayerDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncPlayerSkillEnergy(event.getEntity());
        syncPlayerSpecialEnergy(event.getEntity());
        syncPlayerOnSpecial(event.getEntity());
    }
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        syncPlayerSkillEnergy(event.getEntity());
        syncPlayerSpecialEnergy(event.getEntity());
        syncPlayerOnSpecial(event.getEntity());
    }
    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if((!player.level().isClientSide)&&(!player.isCreative())&&(!player.isSpectator())) {
                boolean onspecial = false;
                PlayerOnSpecial onSpecial = player.getCapability(PlayerOnSpecialProvider.PLAYER_ON_SPECIAL_CAPABILITY).orElse(null);
                if(onSpecial != null)
                {
                    onspecial = onSpecial.getOnSpecial();
                }
                if(onspecial) {
                    if(player.tickCount%20 == 0)
                    {
                        addPlayerSpecialEnergy(player,-1);
                    }
                    player.getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).ifPresent(specialEnergy ->{
                        if(Double.compare(0.0,specialEnergy.getspEnergy()) == 0)
                        {
                            setPlayerOnSpecial(player,false);
                            setPlayerSkillEnergy(player,PlayerSkillEnergy.getMaxEnergy(player));
                        }
                    });
                }
            }
        }
    }
    public static void setPlayerOnSpecial(Player player,Boolean onspecial) {
        if (player.level().isClientSide) return;
        player.getCapability(PlayerOnSpecialProvider.PLAYER_ON_SPECIAL_CAPABILITY).ifPresent(onSpecial ->{
            onSpecial.setOnSpecial(onspecial);
        });
        syncPlayerOnSpecial(player);
    }
    public static void addPlayerSkillEnergy(Player player,double num) {
        if (player.level().isClientSide) return;
        player.getCapability(PlayerSkillEnergyProvider.PLAYER_SKILL_ENERGY_CAPABILITY).ifPresent(skillEnergy ->{
            skillEnergy.addEnergy(player,num);
        });
        syncPlayerSkillEnergy(player);
    }
    public static void setPlayerSkillEnergy(Player player,double num) {
        if (player.level().isClientSide) return;
        player.getCapability(PlayerSkillEnergyProvider.PLAYER_SKILL_ENERGY_CAPABILITY).ifPresent(skillEnergy ->{
            skillEnergy.setEnergy(player,num);
        });
        syncPlayerSkillEnergy(player);
    }
    public static void addPlayerSpecialEnergy(Player player,double num) {
        if (player.level().isClientSide) return;
        player.getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).ifPresent(specialEnergy ->{
            specialEnergy.addspEnergy(player,num);
        });
        syncPlayerSpecialEnergy(player);
    }
    public static void setPlayerSpecialEnergy(Player player,double num) {
        if (player.level().isClientSide) return;
        player.getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).ifPresent(specialEnergy ->{
            specialEnergy.setspEnergy(player,num);
        });
        syncPlayerSpecialEnergy(player);
    }
    public static void syncPlayerSkillEnergy(Player player) {
        if (player.level().isClientSide) return;
        var target = (ServerPlayer) player;
        SoymilkCore.channel.sendTo(
                new PlayerSkillEnergyMessage(target.getCapability(PlayerSkillEnergyProvider.PLAYER_SKILL_ENERGY_CAPABILITY).orElse(new PlayerSkillEnergy())),
                target.connection.connection,
                NetworkDirection.PLAY_TO_CLIENT
        );
    }
    public static void syncPlayerSpecialEnergy(Player player) {
        if (player.level().isClientSide) return;
        var target = (ServerPlayer) player;
        SoymilkCore.channel.sendTo(
                new PlayerSpecialEnergyMessage(target.getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).orElse(new PlayerSpecialEnergy())),
                target.connection.connection,
                NetworkDirection.PLAY_TO_CLIENT
        );
    }
    public static void syncPlayerOnSpecial(Player player) {
        if (player.level().isClientSide) return;
        var target = (ServerPlayer) player;
        SoymilkCore.channel.sendTo(
                new PlayerOnSpecialMessage(target.getCapability(PlayerOnSpecialProvider.PLAYER_ON_SPECIAL_CAPABILITY).orElse(new PlayerOnSpecial())),
                target.connection.connection,
                NetworkDirection.PLAY_TO_CLIENT
        );
    }

}
