// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GuiContainer, EntityPlayer, AchievementList, PlayerController, 
//            GuiContainerCreative, EntityPlayerSP, FontRenderer, RenderEngine, 
//            RenderHelper, RenderManager, GuiButton, GuiAchievements, 
//            GuiStats, PotionEffect, Potion, StatCollector

public class GuiInventory extends GuiContainer
{

    private float xSize_lo;
    private float ySize_lo;

    public GuiInventory(EntityPlayer entityplayer)
    {
        super(entityplayer.inventorySlots);
        allowUserInput = true;
        entityplayer.addStat(AchievementList.openInventory, 1);
    }

    public void updateScreen()
    {
        if(mc.playerController.isInCreativeMode())
        {
            mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
        }
    }

    public void initGui()
    {
        controlList.clear();
        if(mc.playerController.isInCreativeMode())
        {
            mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
        } else
        {
            super.initGui();
            if(!mc.thePlayer.func_40118_aO().isEmpty())
            {
                field_40216_e = 160 + (width - xSize - 200) / 2;
            }
        }
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Crafting", 86, 16, 0x404040);
    }

    public void drawScreen(int i, int j, float f)
    {
        super.drawScreen(i, j, f);
        xSize_lo = i;
        ySize_lo = j;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        int k = mc.renderEngine.getTexture("/gui/inventory.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(k);
        int l = field_40216_e;
        int i1 = field_40215_f;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        func_40218_g();
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
        GL11.glPushMatrix();
        GL11.glTranslatef(l + 51, i1 + 75, 50F);
        float f1 = 30F;
        GL11.glScalef(-f1, f1, f1);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        float f2 = mc.thePlayer.renderYawOffset;
        float f3 = mc.thePlayer.rotationYaw;
        float f4 = mc.thePlayer.rotationPitch;
        float f5 = (float)(l + 51) - xSize_lo;
        float f6 = (float)((i1 + 75) - 50) - ySize_lo;
        GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-(float)Math.atan(f6 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
        mc.thePlayer.renderYawOffset = (float)Math.atan(f5 / 40F) * 20F;
        mc.thePlayer.rotationYaw = (float)Math.atan(f5 / 40F) * 40F;
        mc.thePlayer.rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
        GL11.glTranslatef(0.0F, mc.thePlayer.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180F;
        RenderManager.instance.renderEntityWithPosYaw(mc.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        mc.thePlayer.renderYawOffset = f2;
        mc.thePlayer.rotationYaw = f3;
        mc.thePlayer.rotationPitch = f4;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(guibutton.id == 0)
        {
            mc.displayGuiScreen(new GuiAchievements(mc.statFileWriter));
        }
        if(guibutton.id == 1)
        {
            mc.displayGuiScreen(new GuiStats(this, mc.statFileWriter));
        }
    }

    private void func_40218_g()
    {
        int i = field_40216_e - 124;
        int j = field_40215_f;
        int k = mc.renderEngine.getTexture("/gui/inventory.png");
        Collection collection = mc.thePlayer.func_40118_aO();
        if(collection.isEmpty())
        {
            return;
        }
        int l = 33;
        if(collection.size() > 5)
        {
            l = 132 / (collection.size() - 1);
        }
        for(Iterator iterator = mc.thePlayer.func_40118_aO().iterator(); iterator.hasNext();)
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.renderEngine.bindTexture(k);
            drawTexturedModalRect(i, j, 0, ySize, 140, 32);
            if(potion.func_40617_d())
            {
                int i1 = potion.func_40611_e();
                drawTexturedModalRect(i + 6, j + 7, 0 + (i1 % 8) * 18, ySize + 32 + (i1 / 8) * 18, 18, 18);
            }
            String s = StatCollector.translateToLocal(potion.func_40623_c());
            if(potioneffect.getAmplifier() > 0)
            {
                s = (new StringBuilder()).append(s).append(" ").append(StatCollector.translateToLocal((new StringBuilder()).append("potion.potency.").append(potioneffect.getAmplifier()).toString())).toString();
            }
            fontRenderer.drawStringWithShadow(s, i + 10 + 18, j + 6, 0xffffff);
            String s1 = Potion.func_40620_a(potioneffect);
            fontRenderer.drawStringWithShadow(s1, i + 10 + 18, j + 6 + 10, 0x7f7f7f);
            j += l;
        }

    }
}
