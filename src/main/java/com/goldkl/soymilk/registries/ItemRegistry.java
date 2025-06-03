package com.goldkl.soymilk.registries;

import com.goldkl.soymilk.SoymilkCore;
import com.goldkl.soymilk.item.ExampleSkillSwordItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

public class ItemRegistry {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SoymilkCore.MODID);
    public static final RegistryObject<Item> EXAMPLE_SKILL_SWORD_ITEM = ITEMS.register("exampleskillsworditem", () -> new ExampleSkillSwordItem());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}