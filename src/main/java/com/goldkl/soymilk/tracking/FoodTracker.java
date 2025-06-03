package com.goldkl.soymilk.tracking;


import com.goldkl.soymilk.SoymilkCore;
import com.mojang.logging.LogUtils;
import com.goldkl.soymilk.registries.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber
public class FoodTracker {
    //@SubscribeEvent
    //public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
        //ItemTooltipEvent 用来修改鼠标放上去的显示，只用于客户端
        /*
        if (key != null) {
            LOGGER.info(key.getPath());
            LOGGER.info(key.getNamespace());
            LOGGER.info(String.valueOf(key));
        }*/
    //}

}
