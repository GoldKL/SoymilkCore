package com.goldkl.soymilk.registries;

import com.goldkl.soymilk.SoymilkCore;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AttributeRegistry {
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, SoymilkCore.MODID);
    public static final RegistryObject<Attribute> SKILL_CHARGE_EFFICIENCY =
            ATTRIBUTES.register("generic.skill_charge_efficiency",
                    () -> new RangedAttribute(
                            "attribute.name.skill_charge_efficiency",
                            1.0,
                            0.0,
                            1024.0
                    )// 默认值, 最小值, 最大值
            );
    public static final RegistryObject<Attribute> SPECIAL_CHARGE_EFFICIENCY =
            ATTRIBUTES.register("generic.special_charge_efficiency",
                    () -> new RangedAttribute(
                            "attribute.name.special_charge_efficiency",
                            1.0,
                            0.0,
                            1024.0
                    )// 默认值, 最小值, 最大值
            );
    public static final RegistryObject<Attribute> MAX_SKILL_ENERGY =
            ATTRIBUTES.register("generic.max_skill_energy",
                    () -> new RangedAttribute(
                            "attribute.name.max_skill_energy",
                            20.0,
                            0.0,
                            1024.0
                    )// 默认值, 最小值, 最大值
            );
    public static final RegistryObject<Attribute> MAX_SPECIAL_ENERGY =
            ATTRIBUTES.register("generic.max_special_energy",
                    () -> new RangedAttribute(
                            "attribute.name.max_special_energy",
                            100.0,
                            0.0,
                            1024.0
                    )// 默认值, 最小值, 最大值
            );
    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}