package com.goldkl.soymilk.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class ExampleSkillSwordItem extends SwordItem implements SkillWeaponItem{
    public ExampleSkillSwordItem() {
        super(new Tier() {
            public int getUses() {
                return 100;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return 0f;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -3f, new Item.Properties());
    }
    public double getEfficiency(){
        return 3.0;
    }
    public double getNeedenergy(){
        return 5.0;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand == InteractionHand.OFF_HAND) {
            //只能主手使用
            return InteractionResultHolder.fail(itemstack);
        }
        if(!this.canUseSkill(player)){
            return InteractionResultHolder.fail(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

}
