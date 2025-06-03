package com.goldkl.soymilk.client;

import com.goldkl.soymilk.SoymilkCore;
import com.goldkl.soymilk.registries.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = SoymilkCore.MODID)
public final class TooltipHandler {
    /*
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onItemTooltip(ItemTooltipEvent event) {
        Player player = event.getEntity();
        if (player == null) return;

        ItemStack food = event.getItemStack();
        if (food.getFoodProperties(player) == null) return;
        ResourceLocation key = net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(food.getItem());
        if (key != null && ItemRegistry.CURIOS_ITEMS_MAP.containsKey(key))
        {
            Item item = (new ItemStack( ItemRegistry.CURIOS_ITEMS_MAP.get(key).get()).copy()).getItem();
            var tooltip = event.getToolTip();
            if (item instanceof CurioBaseItem _curio)
            {
                tooltip.add(Math.max(0,tooltip.size()-1),Component.nullToEmpty("————————————————"));
                tooltip.addAll(Math.max(0,tooltip.size()-1),_curio.getCurioTip());
            }
        }
    }


    private TooltipHandler() {}
    */
}
