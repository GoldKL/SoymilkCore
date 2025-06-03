package com.goldkl.soymilk.item;


import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecial;
import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecialProvider;
import com.goldkl.soymilk.capability.skillenergy.PlayerSkillEnergyProvider;
import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergy;
import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergyProvider;
import com.goldkl.soymilk.tracking.ForgeEventTracker;
import com.goldkl.soymilk.tracking.ModEventTracker;
import net.minecraft.world.entity.player.Player;

public interface SkillWeaponItem {
    default boolean canUseSkill(Player player){
        final boolean[] canuse = {player.isCreative()};
        player.getCapability(PlayerOnSpecialProvider.PLAYER_ON_SPECIAL_CAPABILITY).ifPresent(onSpecial ->{
            canuse[0] = canuse[0]||onSpecial.getOnSpecial();
        });
        player.getCapability(PlayerSkillEnergyProvider.PLAYER_SKILL_ENERGY_CAPABILITY).ifPresent(skillEnergy ->{
            canuse[0] = canuse[0]||(Double.compare(skillEnergy.getEnergy(),getNeedenergy())>=0);
        });
        return canuse[0];
    }
    default void SpendEnergy(Player player){
        if(player.level().isClientSide)return;
        if(!player.isCreative()){
            player.getCapability(PlayerOnSpecialProvider.PLAYER_ON_SPECIAL_CAPABILITY).ifPresent(onSpecial ->{
                if(onSpecial.getOnSpecial()){
                    ForgeEventTracker.addPlayerSkillEnergy(player, -getNeedenergy());
                }
                else {
                    ForgeEventTracker.addPlayerSpecialEnergy(player, -getNeedenergy());
                }
            });
        }
    }
    default double getEfficiency(){
        return 0.0;
    }
    default double getNeedenergy(){
        return 0.0;
    }
}
