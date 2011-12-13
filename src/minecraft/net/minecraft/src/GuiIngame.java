// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            Gui, ScaledResolution, EntityRenderer, EntityPlayerSP, 
//            InventoryPlayer, GameSettings, ItemStack, Block, 
//            Potion, PlayerController, RenderEngine, FoodStats, 
//            World, WorldInfo, Material, RenderHelper, 
//            FontRenderer, MathHelper, GuiChat, ChatLine, 
//            EntityClientPlayerMP, KeyBinding, NetClientHandler, GuiSavingLevelString, 
//            RenderDragon, EntityDragon, Tessellator, BlockPortal, 
//            RenderItem, StringTranslate

public class GuiIngame extends Gui
{

    private static RenderItem itemRenderer = new RenderItem();
    private java.util.List chatMessageList;
    private Random rand;
    private Minecraft mc;
    public String field_933_a;
    private int updateCounter;
    private String recordPlaying;
    private int recordPlayingUpFor;
    private boolean recordIsPlaying;
    public float damageGuiPartialTime;
    float prevVignetteBrightness;

    public GuiIngame(Minecraft minecraft)
    {
        chatMessageList = new ArrayList();
        rand = new Random();
        field_933_a = null;
        updateCounter = 0;
        recordPlaying = "";
        recordPlayingUpFor = 0;
        recordIsPlaying = false;
        prevVignetteBrightness = 1.0F;
        mc = minecraft;
    }

    public void renderGameOverlay(float f, boolean flag, int i, int j)
    {
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int k = scaledresolution.getScaledWidth();
        int l = scaledresolution.getScaledHeight();
        FontRenderer fontrenderer = mc.fontRenderer;
        mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(3042 /*GL_BLEND*/);
        if(Minecraft.isFancyGraphicsEnabled())
        {
            renderVignette(mc.thePlayer.getEntityBrightness(f), k, l);
        } else
        {
            GL11.glBlendFunc(770, 771);
        }
        ItemStack itemstack = mc.thePlayer.inventory.armorItemInSlot(3);
        if(mc.gameSettings.thirdPersonView == 0 && itemstack != null && itemstack.itemID == Block.pumpkin.blockID)
        {
            renderPumpkinBlur(k, l);
        }
        if(!mc.thePlayer.isPotionActive(Potion.potionConfusion))
        {
            float f1 = mc.thePlayer.prevTimeInPortal + (mc.thePlayer.timeInPortal - mc.thePlayer.prevTimeInPortal) * f;
            if(f1 > 0.0F)
            {
                renderPortalOverlay(f1, k, l);
            }
        }
        if(!mc.playerController.func_35643_e())
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/gui.png"));
            InventoryPlayer inventoryplayer = mc.thePlayer.inventory;
            zLevel = -90F;
            drawTexturedModalRect(k / 2 - 91, l - 22, 0, 0, 182, 22);
            drawTexturedModalRect((k / 2 - 91 - 1) + inventoryplayer.currentItem * 20, l - 22 - 1, 0, 22, 24, 22);
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/icons.png"));
            GL11.glEnable(3042 /*GL_BLEND*/);
            GL11.glBlendFunc(775, 769);
            drawTexturedModalRect(k / 2 - 7, l / 2 - 7, 0, 0, 16, 16);
            GL11.glDisable(3042 /*GL_BLEND*/);
            boolean flag2 = (mc.thePlayer.heartsLife / 3) % 2 == 1;
            if(mc.thePlayer.heartsLife < 10)
            {
                flag2 = false;
            }
            int i2 = mc.thePlayer.getEntityHealth();
            int j3 = mc.thePlayer.prevHealth;
            rand.setSeed(updateCounter * 0x4c627);
            boolean flag4 = false;
            FoodStats foodstats = mc.thePlayer.getFoodStats();
            int i5 = foodstats.getFoodLevel();
            int k5 = foodstats.getPrevFoodLevel();
            func_41039_c();
            if(mc.playerController.shouldDrawHUD())
            {
                int k6 = k / 2 - 91;
                int j7 = k / 2 + 91;
                int l7 = mc.thePlayer.xpBarCap();
                if(l7 > 0)
                {
                    char c = '\266';
                    int i9 = (int)(mc.thePlayer.currentXP * (float)(c + 1));
                    int l9 = (l - 32) + 3;
                    drawTexturedModalRect(k6, l9, 0, 64, c, 5);
                    if(i9 > 0)
                    {
                        drawTexturedModalRect(k6, l9, 0, 69, i9, 5);
                    }
                }
                int k8 = l - 39;
                int j9 = k8 - 10;
                int i10 = mc.thePlayer.getPlayerArmorValue();
                int j10 = -1;
                if(mc.thePlayer.isPotionActive(Potion.potionRegeneration))
                {
                    j10 = updateCounter % 25;
                }
                for(int l10 = 0; l10 < 10; l10++)
                {
                    if(i10 > 0)
                    {
                        int k11 = k6 + l10 * 8;
                        if(l10 * 2 + 1 < i10)
                        {
                            drawTexturedModalRect(k11, j9, 34, 9, 9, 9);
                        }
                        if(l10 * 2 + 1 == i10)
                        {
                            drawTexturedModalRect(k11, j9, 25, 9, 9, 9);
                        }
                        if(l10 * 2 + 1 > i10)
                        {
                            drawTexturedModalRect(k11, j9, 16, 9, 9, 9);
                        }
                    }
                    int l11 = 16;
                    if(mc.thePlayer.isPotionActive(Potion.potionPoison))
                    {
                        l11 += 36;
                    }
                    int k12 = 0;
                    if(flag2)
                    {
                        k12 = 1;
                    }
                    int j13 = k6 + l10 * 8;
                    int k13 = k8;
                    if(i2 <= 4)
                    {
                        k13 += rand.nextInt(2);
                    }
                    if(l10 == j10)
                    {
                        k13 -= 2;
                    }
                    byte byte5 = 0;
                    if(mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
                    {
                        byte5 = 5;
                    }
                    drawTexturedModalRect(j13, k13, 16 + k12 * 9, 9 * byte5, 9, 9);
                    if(flag2)
                    {
                        if(l10 * 2 + 1 < j3)
                        {
                            drawTexturedModalRect(j13, k13, l11 + 54, 9 * byte5, 9, 9);
                        }
                        if(l10 * 2 + 1 == j3)
                        {
                            drawTexturedModalRect(j13, k13, l11 + 63, 9 * byte5, 9, 9);
                        }
                    }
                    if(l10 * 2 + 1 < i2)
                    {
                        drawTexturedModalRect(j13, k13, l11 + 36, 9 * byte5, 9, 9);
                    }
                    if(l10 * 2 + 1 == i2)
                    {
                        drawTexturedModalRect(j13, k13, l11 + 45, 9 * byte5, 9, 9);
                    }
                }

                for(int i11 = 0; i11 < 10; i11++)
                {
                    int i12 = k8;
                    int l12 = 16;
                    byte byte4 = 0;
                    if(mc.thePlayer.isPotionActive(Potion.potionHunger))
                    {
                        l12 += 36;
                        byte4 = 13;
                    }
                    if(mc.thePlayer.getFoodStats().getFoodSaturationLevel() <= 0.0F && updateCounter % (i5 * 3 + 1) == 0)
                    {
                        i12 += rand.nextInt(3) - 1;
                    }
                    if(flag4)
                    {
                        byte4 = 1;
                    }
                    int l13 = j7 - i11 * 8 - 9;
                    drawTexturedModalRect(l13, i12, 16 + byte4 * 9, 27, 9, 9);
                    if(flag4)
                    {
                        if(i11 * 2 + 1 < k5)
                        {
                            drawTexturedModalRect(l13, i12, l12 + 54, 27, 9, 9);
                        }
                        if(i11 * 2 + 1 == k5)
                        {
                            drawTexturedModalRect(l13, i12, l12 + 63, 27, 9, 9);
                        }
                    }
                    if(i11 * 2 + 1 < i5)
                    {
                        drawTexturedModalRect(l13, i12, l12 + 36, 27, 9, 9);
                    }
                    if(i11 * 2 + 1 == i5)
                    {
                        drawTexturedModalRect(l13, i12, l12 + 45, 27, 9, 9);
                    }
                }

                if(mc.thePlayer.isInsideOfMaterial(Material.water))
                {
                    int j11 = (int)Math.ceil(((double)(mc.thePlayer.func_41001_Z() - 2) * 10D) / 300D);
                    int j12 = (int)Math.ceil(((double)mc.thePlayer.func_41001_Z() * 10D) / 300D) - j11;
                    for(int i13 = 0; i13 < j11 + j12; i13++)
                    {
                        if(i13 < j11)
                        {
                            drawTexturedModalRect(j7 - i13 * 8 - 9, j9, 16, 18, 9, 9);
                        } else
                        {
                            drawTexturedModalRect(j7 - i13 * 8 - 9, j9, 25, 18, 9, 9);
                        }
                    }

                }
            }
            GL11.glDisable(3042 /*GL_BLEND*/);
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            RenderHelper.func_41089_c();
            for(int l6 = 0; l6 < 9; l6++)
            {
                int k7 = (k / 2 - 90) + l6 * 20 + 2;
                int i8 = l - 16 - 3;
                renderInventorySlot(l6, k7, i8, f);
            }

            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        }
        if(mc.thePlayer.getSleepTimer() > 0)
        {
            GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
            GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
            int i1 = mc.thePlayer.getSleepTimer();
            float f3 = (float)i1 / 100F;
            if(f3 > 1.0F)
            {
                f3 = 1.0F - (float)(i1 - 100) / 10F;
            }
            int j2 = (int)(220F * f3) << 24 | 0x101020;
            drawRect(0, 0, k, l, j2);
            GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
            GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        }
        if(mc.playerController.func_35642_f() && mc.thePlayer.playerLevel > 0)
        {
            boolean flag1 = false;
            int j1 = flag1 ? 0xffffff : 0x80ff20;
            String s = (new StringBuilder()).append("").append(mc.thePlayer.playerLevel).toString();
            int k3 = (k - fontrenderer.getStringWidth(s)) / 2;
            int l3 = l - 31 - 4;
            fontrenderer.drawString(s, k3 + 1, l3, 0);
            fontrenderer.drawString(s, k3 - 1, l3, 0);
            fontrenderer.drawString(s, k3, l3 + 1, 0);
            fontrenderer.drawString(s, k3, l3 - 1, 0);
            fontrenderer.drawString(s, k3, l3, j1);
        }
        if(mc.gameSettings.showDebugInfo)
        {
            GL11.glPushMatrix();
            if(Minecraft.hasPaidCheckTime > 0L)
            {
                GL11.glTranslatef(0.0F, 32F, 0.0F);
            }
            fontrenderer.drawStringWithShadow((new StringBuilder()).append("Minecraft 1.0.0 (").append(mc.debug).append(")").toString(), 2, 2, 0xffffff);
            fontrenderer.drawStringWithShadow(mc.debugInfoRenders(), 2, 12, 0xffffff);
            fontrenderer.drawStringWithShadow(mc.func_6262_n(), 2, 22, 0xffffff);
            fontrenderer.drawStringWithShadow(mc.debugInfoEntities(), 2, 32, 0xffffff);
            fontrenderer.drawStringWithShadow(mc.func_21002_o(), 2, 42, 0xffffff);
            long l1 = Runtime.getRuntime().maxMemory();
            long l2 = Runtime.getRuntime().totalMemory();
            long l4 = Runtime.getRuntime().freeMemory();
            long l5 = l2 - l4;
            String s1 = (new StringBuilder()).append("Used memory: ").append((l5 * 100L) / l1).append("% (").append(l5 / 1024L / 1024L).append("MB) of ").append(l1 / 1024L / 1024L).append("MB").toString();
            drawString(fontrenderer, s1, k - fontrenderer.getStringWidth(s1) - 2, 2, 0xe0e0e0);
            s1 = (new StringBuilder()).append("Allocated memory: ").append((l2 * 100L) / l1).append("% (").append(l2 / 1024L / 1024L).append("MB)").toString();
            drawString(fontrenderer, s1, k - fontrenderer.getStringWidth(s1) - 2, 12, 0xe0e0e0);
            drawString(fontrenderer, (new StringBuilder()).append("x: ").append(mc.thePlayer.posX).toString(), 2, 64, 0xe0e0e0);
            drawString(fontrenderer, (new StringBuilder()).append("y: ").append(mc.thePlayer.posY).toString(), 2, 72, 0xe0e0e0);
            drawString(fontrenderer, (new StringBuilder()).append("z: ").append(mc.thePlayer.posZ).toString(), 2, 80, 0xe0e0e0);
            drawString(fontrenderer, (new StringBuilder()).append("f: ").append(MathHelper.floor_double((double)((mc.thePlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3).toString(), 2, 88, 0xe0e0e0);
            drawString(fontrenderer, (new StringBuilder()).append("Seed: ").append(mc.theWorld.getWorldSeed()).toString(), 2, 104, 0xe0e0e0);
            GL11.glPopMatrix();
        }
        if(recordPlayingUpFor > 0)
        {
            float f2 = (float)recordPlayingUpFor - f;
            int k1 = (int)((f2 * 256F) / 20F);
            if(k1 > 255)
            {
                k1 = 255;
            }
            if(k1 > 0)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef(k / 2, l - 48, 0.0F);
                GL11.glEnable(3042 /*GL_BLEND*/);
                GL11.glBlendFunc(770, 771);
                int k2 = 0xffffff;
                if(recordIsPlaying)
                {
                    k2 = Color.HSBtoRGB(f2 / 50F, 0.7F, 0.6F) & 0xffffff;
                }
                fontrenderer.drawString(recordPlaying, -fontrenderer.getStringWidth(recordPlaying) / 2, -4, k2 + (k1 << 24));
                GL11.glDisable(3042 /*GL_BLEND*/);
                GL11.glPopMatrix();
            }
        }
        byte byte0 = 10;
        boolean flag3 = false;
        if(mc.currentScreen instanceof GuiChat)
        {
            byte0 = 20;
            flag3 = true;
        }
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, l - 48, 0.0F);
        for(int i3 = 0; i3 < chatMessageList.size() && i3 < byte0; i3++)
        {
            if(((ChatLine)chatMessageList.get(i3)).updateCounter >= 200 && !flag3)
            {
                continue;
            }
            double d = (double)((ChatLine)chatMessageList.get(i3)).updateCounter / 200D;
            d = 1.0D - d;
            d *= 10D;
            if(d < 0.0D)
            {
                d = 0.0D;
            }
            if(d > 1.0D)
            {
                d = 1.0D;
            }
            d *= d;
            int j4 = (int)(255D * d);
            if(flag3)
            {
                j4 = 255;
            }
            if(j4 > 0)
            {
                byte byte1 = 2;
                int i6 = -i3 * 9;
                String s2 = ((ChatLine)chatMessageList.get(i3)).message;
                drawRect(byte1, i6 - 1, byte1 + 320, i6 + 8, j4 / 2 << 24);
                GL11.glEnable(3042 /*GL_BLEND*/);
                fontrenderer.drawStringWithShadow(s2, byte1, i6, 0xffffff + (j4 << 24));
            }
        }

        GL11.glPopMatrix();
        if((mc.thePlayer instanceof EntityClientPlayerMP) && mc.gameSettings.keyBindPlayerList.pressed)
        {
            NetClientHandler netclienthandler = ((EntityClientPlayerMP)mc.thePlayer).sendQueue;
            java.util.List list = netclienthandler.field_35786_c;
            int i4 = netclienthandler.field_35785_d;
            int k4 = i4;
            int j5 = 1;
            for(; k4 > 20; k4 = ((i4 + j5) - 1) / j5)
            {
                j5++;
            }

            int j6 = 300 / j5;
            if(j6 > 150)
            {
                j6 = 150;
            }
            int i7 = (k - j5 * j6) / 2;
            byte byte2 = 10;
            drawRect(i7 - 1, byte2 - 1, i7 + j6 * j5, byte2 + 9 * k4, 0x80000000);
            for(int j8 = 0; j8 < i4; j8++)
            {
                int l8 = i7 + (j8 % j5) * j6;
                int k9 = byte2 + (j8 / j5) * 9;
                drawRect(l8, k9, (l8 + j6) - 1, k9 + 8, 0x20ffffff);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
                if(j8 >= list.size())
                {
                    continue;
                }
                GuiSavingLevelString guisavinglevelstring = (GuiSavingLevelString)list.get(j8);
                fontrenderer.drawStringWithShadow(guisavinglevelstring.field_35624_a, l8, k9, 0xffffff);
                mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/icons.png"));
                int k10 = 0;
                byte byte3 = 0;
                k10 = 0;
                byte3 = 0;
                if(guisavinglevelstring.field_35623_b < 0)
                {
                    byte3 = 5;
                } else
                if(guisavinglevelstring.field_35623_b < 150)
                {
                    byte3 = 0;
                } else
                if(guisavinglevelstring.field_35623_b < 300)
                {
                    byte3 = 1;
                } else
                if(guisavinglevelstring.field_35623_b < 600)
                {
                    byte3 = 2;
                } else
                if(guisavinglevelstring.field_35623_b < 1000)
                {
                    byte3 = 3;
                } else
                {
                    byte3 = 4;
                }
                zLevel += 100F;
                drawTexturedModalRect((l8 + j6) - 12, k9, 0 + k10 * 10, 176 + byte3 * 8, 10, 8);
                zLevel -= 100F;
            }

        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
    }

    private void func_41039_c()
    {
        if(RenderDragon.field_41038_a == null)
        {
            return;
        }
        EntityDragon entitydragon = RenderDragon.field_41038_a;
        RenderDragon.field_41038_a = null;
        FontRenderer fontrenderer = mc.fontRenderer;
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int i = scaledresolution.getScaledWidth();
        char c = '\266';
        int j = i / 2 - c / 2;
        int k = (int)(((float)entitydragon.func_41010_ax() / (float)entitydragon.getMaxHealth()) * (float)(c + 1));
        byte byte0 = 12;
        drawTexturedModalRect(j, byte0, 0, 74, c, 5);
        drawTexturedModalRect(j, byte0, 0, 74, c, 5);
        if(k > 0)
        {
            drawTexturedModalRect(j, byte0, 0, 79, k, 5);
        }
        String s = "Boss health";
        fontrenderer.drawStringWithShadow(s, i / 2 - fontrenderer.getStringWidth(s) / 2, byte0 - 10, 0xff00ff);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/icons.png"));
    }

    private void renderPumpkinBlur(int i, int j)
    {
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("%blur%/misc/pumpkinblur.png"));
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, j, -90D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderVignette(float f, int i, int j)
    {
        f = 1.0F - f;
        if(f < 0.0F)
        {
            f = 0.0F;
        }
        if(f > 1.0F)
        {
            f = 1.0F;
        }
        prevVignetteBrightness += (double)(f - prevVignetteBrightness) * 0.01D;
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(0, 769);
        GL11.glColor4f(prevVignetteBrightness, prevVignetteBrightness, prevVignetteBrightness, 1.0F);
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("%blur%/misc/vignette.png"));
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, j, -90D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(770, 771);
    }

    private void renderPortalOverlay(float f, int i, int j)
    {
        if(f < 1.0F)
        {
            f *= f;
            f *= f;
            f = f * 0.8F + 0.2F;
        }
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/terrain.png"));
        float f1 = (float)(Block.portal.blockIndexInTexture % 16) / 16F;
        float f2 = (float)(Block.portal.blockIndexInTexture / 16) / 16F;
        float f3 = (float)(Block.portal.blockIndexInTexture % 16 + 1) / 16F;
        float f4 = (float)(Block.portal.blockIndexInTexture / 16 + 1) / 16F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, j, -90D, f1, f4);
        tessellator.addVertexWithUV(i, j, -90D, f3, f4);
        tessellator.addVertexWithUV(i, 0.0D, -90D, f3, f2);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90D, f1, f2);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderInventorySlot(int i, int j, int k, float f)
    {
        ItemStack itemstack = mc.thePlayer.inventory.mainInventory[i];
        if(itemstack == null)
        {
            return;
        }
        float f1 = (float)itemstack.animationsToGo - f;
        if(f1 > 0.0F)
        {
            GL11.glPushMatrix();
            float f2 = 1.0F + f1 / 5F;
            GL11.glTranslatef(j + 8, k + 12, 0.0F);
            GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
            GL11.glTranslatef(-(j + 8), -(k + 12), 0.0F);
        }
        itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, j, k);
        if(f1 > 0.0F)
        {
            GL11.glPopMatrix();
        }
        itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, j, k);
    }

    public void updateTick()
    {
        if(recordPlayingUpFor > 0)
        {
            recordPlayingUpFor--;
        }
        updateCounter++;
        for(int i = 0; i < chatMessageList.size(); i++)
        {
            ((ChatLine)chatMessageList.get(i)).updateCounter++;
        }

    }

    public void clearChatMessages()
    {
        chatMessageList.clear();
    }

    public void addChatMessage(String s)
    {
        int i;
        for(; mc.fontRenderer.getStringWidth(s) > 320; s = s.substring(i))
        {
            for(i = 1; i < s.length() && mc.fontRenderer.getStringWidth(s.substring(0, i + 1)) <= 320; i++) { }
            addChatMessage(s.substring(0, i));
        }

        chatMessageList.add(0, new ChatLine(s));
        for(; chatMessageList.size() > 50; chatMessageList.remove(chatMessageList.size() - 1)) { }
    }

    public void setRecordPlayingMessage(String s)
    {
        recordPlaying = (new StringBuilder()).append("Now playing: ").append(s).toString();
        recordPlayingUpFor = 60;
        recordIsPlaying = true;
    }

    public void addChatMessageTranslate(String s)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        String s1 = stringtranslate.translateKey(s);
        addChatMessage(s1);
    }

}
