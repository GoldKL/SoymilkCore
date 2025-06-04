package com.goldkl.soymilk.client;

import com.goldkl.soymilk.SoymilkCore;
import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecial;
import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecialProvider;
import com.goldkl.soymilk.capability.skillenergy.PlayerSkillEnergyProvider;
import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergyProvider;
import com.goldkl.soymilk.item.SkillWeaponItem;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SoymilkCore.MODID,value = Dist.CLIENT)
public class EnergyHUD {
    private static final ResourceLocation texture = new ResourceLocation("soymilk_core:textures/gui/hud_energy.png");
    private static class PlayerData {
        public double skillenergy = 0.0;
        public double maxskillenergy = 20.0;
        public double specialenergy = 0.0;
        public double maxspecialenergy = 100.0;
        public boolean onspecial = false;
    }
    private static final PlayerData playerData = new PlayerData();
    @SubscribeEvent
    public static void onOverlayRender(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.HOTBAR.type()) {
            return;
        }
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        GuiGraphics guiGraphics = event.getGuiGraphics();
        Font font = minecraft.font;
        PoseStack poseStack = guiGraphics.pose();
        int guitick = minecraft.gui.getGuiTicks();
        if(!minecraft.options.hideGui)
        {
            minecraft.getProfiler().push("EnergyHUD");//性能分析
            player.getCapability(PlayerSkillEnergyProvider.PLAYER_SKILL_ENERGY_CAPABILITY).ifPresent(
                    (cap) -> {
                        playerData.skillenergy = cap.getEnergy();
                        playerData.maxskillenergy = cap.getMaxEnergy(player);
                    }
            );
            player.getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).ifPresent(
                    (cap) -> {
                        playerData.specialenergy = cap.getspEnergy();
                        playerData.maxskillenergy = cap.getMaxspEnergy(player);
                    }
            );
            player.getCapability(PlayerOnSpecialProvider.PLAYER_ON_SPECIAL_CAPABILITY).ifPresent(
                    (cap) -> {
                        playerData.onspecial = cap.getOnSpecial();
                    }
            );
            int x=10;
            int y=10;
            double specialpercent= playerData.specialenergy/playerData.maxspecialenergy;
            // 获取屏幕尺寸（已适配GUI缩放）
            int screenWidth = event.getWindow().getGuiScaledWidth();
            int screenHeight = event.getWindow().getGuiScaledHeight();
            //开始绘制文字(在图片下面）
            guiGraphics.drawString(minecraft.font,playerData.skillenergy+" "+playerData.specialenergy+" "+playerData.onspecial,x, y+50, 0xFFFFFF);
            //开始绘制图片
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            guiGraphics.blit(texture, x, y, 0, 0, 36, 36, 256, 256);
            guiGraphics.blit(texture, x+23, y+16, 0, 45, 84, 16, 256, 256);
            //武器技能条
            if(!playerData.onspecial)
            {
                for(int i = 0; i < 5; ++i)
                {
                    guiGraphics.blit(texture, x+33+i*10, y+7 , 9, 36, 9, 9, 256, 256);
                    if(player.getMainHandItem().getItem() instanceof SkillWeaponItem switem)
                    {
                        int num = guitick%40;
                        float f = num<20?(num/20F):(1.0F-(num-20)/20F);
                        if(Double.compare(switem.getNeedenergy(), playerData.skillenergy) <= 0)
                        {
                            double newenergy = playerData.skillenergy - switem.getNeedenergy();
                            if (newenergy > i * 2) {
                                if (newenergy >= i * 2 + 2) {
                                    guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 27, 36, 9, 9, 256, 256);
                                } else if (newenergy >= i * 2 + 1) {
                                    guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 45, 36, 9, 9, 256, 256);
                                }
                            }
                            if (newenergy > i * 2 + 10) {
                                if (newenergy >= i * 2 + 12) {
                                    guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 18, 36, 9, 9, 256, 256);
                                } else if (newenergy >= i * 2 + 11) {
                                    guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 36, 36, 9, 9, 256, 256);
                                }
                            }
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f);
                            if (playerData.skillenergy > i * 2) {
                                if (playerData.skillenergy >= i * 2 + 2) {
                                    guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 27, 36, 9, 9, 256, 256);
                                } else if (playerData.skillenergy >= i * 2 + 1) {
                                    guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 45, 36, 9, 9, 256, 256);
                                }
                            }
                            if (playerData.skillenergy > i * 2 + 10) {
                                if (playerData.skillenergy >= i * 2 + 12) {
                                    guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 18, 36, 9, 9, 256, 256);
                                } else if (playerData.skillenergy >= i * 2 + 11) {
                                    guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 36, 36, 9, 9, 256, 256);
                                }
                            }
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            continue;
                        }
                    }
                    if (playerData.skillenergy > i * 2) {
                        if (playerData.skillenergy >= i * 2 + 2) {
                            guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 27, 36, 9, 9, 256, 256);
                        } else if (playerData.skillenergy >= i * 2 + 1) {
                            guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 45, 36, 9, 9, 256, 256);
                        }
                    }
                    if (playerData.skillenergy > i * 2 + 10) {
                        if (playerData.skillenergy >= i * 2 + 12) {
                            guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 18, 36, 9, 9, 256, 256);
                        } else if (playerData.skillenergy >= i * 2 + 11) {
                            guiGraphics.blit(texture, x + 33 + i * 10, y + 7, 36, 36, 9, 9, 256, 256);
                        }
                    }
                }
            }
            else
            {
                for(int i = 0; i < 5; ++i)
                {
                    guiGraphics.blit(texture, x+33+i*10, y+7 , 0, 36, 9, 9, 256, 256);
                }
            }
            //必杀技能条
            if(!playerData.onspecial)
            {
                guiGraphics.blit(texture, x+27, y+20, 1, 62, (int)(72*specialpercent), 8, 256, 256);
                if(Double.compare(1.0f, specialpercent)<=0)
                {
                    int num = guitick%20;
                    float f = num<10?(num/10F):(1.0F-(num-10)/10F);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f);
                    guiGraphics.blit(texture, x+27, y+20, 1, 72, (int)(72*specialpercent), 8, 256, 256);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                guiGraphics.blit(texture, x+27, y+20, 1, 72, (int)(72*specialpercent), 8, 256, 256);
            }
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1, 1, 1, 1);
            //开始绘制文字（在图片上面）
            guiGraphics.renderItem(player.getMainHandItem(),x+10,y+10);
            minecraft.getProfiler().pop();
        }
    }
}
