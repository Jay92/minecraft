// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            RenderBlocks, MapItemRenderer, ItemStack, Block, 
//            RenderEngine, Tessellator, EntityLiving, EntityPlayer, 
//            RenderHelper, EntityPlayerSP, MathHelper, World, 
//            OpenGlHelper, Item, ItemMap, RenderManager, 
//            RenderPlayer, EnumAction, ItemPotion, Material, 
//            BlockFire, InventoryPlayer

public class ItemRenderer
{

    private Minecraft mc;
    private ItemStack itemToRender;
    private float equippedProgress;
    private float prevEquippedProgress;
    private RenderBlocks renderBlocksInstance;
    private MapItemRenderer mapItemRenderer;
    private int field_20099_f;

    public ItemRenderer(Minecraft minecraft)
    {
        itemToRender = null;
        equippedProgress = 0.0F;
        prevEquippedProgress = 0.0F;
        renderBlocksInstance = new RenderBlocks();
        field_20099_f = -1;
        mc = minecraft;
        mapItemRenderer = new MapItemRenderer(minecraft.fontRenderer, minecraft.gameSettings, minecraft.renderEngine);
    }

    public void renderItem(EntityLiving entityliving, ItemStack itemstack, int i)
    {
        GL11.glPushMatrix();
        if(itemstack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
        {
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/terrain.png"));
            renderBlocksInstance.renderBlockOnInventory(Block.blocksList[itemstack.itemID], itemstack.getItemDamage(), 1.0F);
        } else
        {
            if(itemstack.itemID < 256)
            {
                GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/terrain.png"));
            } else
            {
                GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/items.png"));
            }
            Tessellator tessellator = Tessellator.instance;
            int j = entityliving.getItemIcon(itemstack, i);
            float f = ((float)((j % 16) * 16) + 0.0F) / 256F;
            float f1 = ((float)((j % 16) * 16) + 15.99F) / 256F;
            float f2 = ((float)((j / 16) * 16) + 0.0F) / 256F;
            float f3 = ((float)((j / 16) * 16) + 15.99F) / 256F;
            float f4 = 0.0F;
            float f5 = 0.3F;
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            GL11.glTranslatef(-f4, -f5, 0.0F);
            float f6 = 1.5F;
            GL11.glScalef(f6, f6, f6);
            GL11.glRotatef(50F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(335F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            func_40686_a(tessellator, f1, f2, f, f3);
            if(itemstack != null && itemstack.func_40713_r() && i == 0)
            {
                GL11.glDepthFunc(514);
                GL11.glDisable(2896 /*GL_LIGHTING*/);
                mc.renderEngine.bindTexture(mc.renderEngine.getTexture("%blur%/misc/glint.png"));
                GL11.glEnable(3042 /*GL_BLEND*/);
                GL11.glBlendFunc(768, 1);
                float f7 = 0.76F;
                GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
                GL11.glMatrixMode(5890 /*GL_TEXTURE*/);
                GL11.glPushMatrix();
                float f8 = 0.125F;
                GL11.glScalef(f8, f8, f8);
                float f9 = ((float)(System.currentTimeMillis() % 3000L) / 3000F) * 8F;
                GL11.glTranslatef(f9, 0.0F, 0.0F);
                GL11.glRotatef(-50F, 0.0F, 0.0F, 1.0F);
                func_40686_a(tessellator, 0.0F, 0.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(f8, f8, f8);
                f9 = ((float)(System.currentTimeMillis() % 4873L) / 4873F) * 8F;
                GL11.glTranslatef(-f9, 0.0F, 0.0F);
                GL11.glRotatef(10F, 0.0F, 0.0F, 1.0F);
                func_40686_a(tessellator, 0.0F, 0.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
                GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
                GL11.glDisable(3042 /*GL_BLEND*/);
                GL11.glEnable(2896 /*GL_LIGHTING*/);
                GL11.glDepthFunc(515);
            }
            GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        }
        GL11.glPopMatrix();
    }

    private void func_40686_a(Tessellator tessellator, float f, float f1, float f2, float f3)
    {
        float f4 = 1.0F;
        float f5 = 0.0625F;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, f, f3);
        tessellator.addVertexWithUV(f4, 0.0D, 0.0D, f2, f3);
        tessellator.addVertexWithUV(f4, 1.0D, 0.0D, f2, f1);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, f, f1);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - f5, f, f1);
        tessellator.addVertexWithUV(f4, 1.0D, 0.0F - f5, f2, f1);
        tessellator.addVertexWithUV(f4, 0.0D, 0.0F - f5, f2, f3);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - f5, f, f3);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        for(int i = 0; i < 16; i++)
        {
            float f6 = (float)i / 16F;
            float f10 = (f + (f2 - f) * f6) - 0.001953125F;
            float f14 = f4 * f6;
            tessellator.addVertexWithUV(f14, 0.0D, 0.0F - f5, f10, f3);
            tessellator.addVertexWithUV(f14, 0.0D, 0.0D, f10, f3);
            tessellator.addVertexWithUV(f14, 1.0D, 0.0D, f10, f1);
            tessellator.addVertexWithUV(f14, 1.0D, 0.0F - f5, f10, f1);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        for(int j = 0; j < 16; j++)
        {
            float f7 = (float)j / 16F;
            float f11 = (f + (f2 - f) * f7) - 0.001953125F;
            float f15 = f4 * f7 + 0.0625F;
            tessellator.addVertexWithUV(f15, 1.0D, 0.0F - f5, f11, f1);
            tessellator.addVertexWithUV(f15, 1.0D, 0.0D, f11, f1);
            tessellator.addVertexWithUV(f15, 0.0D, 0.0D, f11, f3);
            tessellator.addVertexWithUV(f15, 0.0D, 0.0F - f5, f11, f3);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        for(int k = 0; k < 16; k++)
        {
            float f8 = (float)k / 16F;
            float f12 = (f3 + (f1 - f3) * f8) - 0.001953125F;
            float f16 = f4 * f8 + 0.0625F;
            tessellator.addVertexWithUV(0.0D, f16, 0.0D, f, f12);
            tessellator.addVertexWithUV(f4, f16, 0.0D, f2, f12);
            tessellator.addVertexWithUV(f4, f16, 0.0F - f5, f2, f12);
            tessellator.addVertexWithUV(0.0D, f16, 0.0F - f5, f, f12);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        for(int l = 0; l < 16; l++)
        {
            float f9 = (float)l / 16F;
            float f13 = (f3 + (f1 - f3) * f9) - 0.001953125F;
            float f17 = f4 * f9;
            tessellator.addVertexWithUV(f4, f17, 0.0D, f2, f13);
            tessellator.addVertexWithUV(0.0D, f17, 0.0D, f, f13);
            tessellator.addVertexWithUV(0.0D, f17, 0.0F - f5, f, f13);
            tessellator.addVertexWithUV(f4, f17, 0.0F - f5, f2, f13);
        }

        tessellator.draw();
    }

    public void renderItemInFirstPerson(float f)
    {
        float f1 = prevEquippedProgress + (equippedProgress - prevEquippedProgress) * f;
        EntityPlayerSP entityplayersp = mc.thePlayer;
        float f2 = ((EntityPlayer) (entityplayersp)).prevRotationPitch + (((EntityPlayer) (entityplayersp)).rotationPitch - ((EntityPlayer) (entityplayersp)).prevRotationPitch) * f;
        GL11.glPushMatrix();
        GL11.glRotatef(f2, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(((EntityPlayer) (entityplayersp)).prevRotationYaw + (((EntityPlayer) (entityplayersp)).rotationYaw - ((EntityPlayer) (entityplayersp)).prevRotationYaw) * f, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        if(entityplayersp instanceof EntityPlayerSP)
        {
            EntityPlayerSP entityplayersp1 = (EntityPlayerSP)entityplayersp;
            float f3 = entityplayersp1.prevRenderArmPitch + (entityplayersp1.renderArmPitch - entityplayersp1.prevRenderArmPitch) * f;
            float f5 = entityplayersp1.prevRenderArmYaw + (entityplayersp1.renderArmYaw - entityplayersp1.prevRenderArmYaw) * f;
            GL11.glRotatef((((EntityPlayer) (entityplayersp)).rotationPitch - f3) * 0.1F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef((((EntityPlayer) (entityplayersp)).rotationYaw - f5) * 0.1F, 0.0F, 1.0F, 0.0F);
        }
        ItemStack itemstack = itemToRender;
        float f4 = mc.theWorld.getLightBrightness(MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posX), MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posY), MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posZ));
        f4 = 1.0F;
        int i = mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posX), MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posY), MathHelper.floor_double(((EntityPlayer) (entityplayersp)).posZ), 0);
        int k = i % 0x10000;
        int l = i / 0x10000;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapEnabled, (float)k / 1.0F, (float)l / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(itemstack != null)
        {
            int j = Item.itemsList[itemstack.itemID].getColorFromDamage(itemstack.getItemDamage());
            float f9 = (float)(j >> 16 & 0xff) / 255F;
            float f14 = (float)(j >> 8 & 0xff) / 255F;
            float f20 = (float)(j & 0xff) / 255F;
            GL11.glColor4f(f4 * f9, f4 * f14, f4 * f20, 1.0F);
        } else
        {
            GL11.glColor4f(f4, f4, f4, 1.0F);
        }
        if(itemstack != null && itemstack.itemID == Item.map.shiftedIndex)
        {
            GL11.glPushMatrix();
            float f6 = 0.8F;
            float f10 = entityplayersp.getSwingProgress(f);
            float f15 = MathHelper.sin(f10 * 3.141593F);
            float f21 = MathHelper.sin(MathHelper.sqrt_float(f10) * 3.141593F);
            GL11.glTranslatef(-f21 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(f10) * 3.141593F * 2.0F) * 0.2F, -f15 * 0.2F);
            f10 = (1.0F - f2 / 45F) + 0.1F;
            if(f10 < 0.0F)
            {
                f10 = 0.0F;
            }
            if(f10 > 1.0F)
            {
                f10 = 1.0F;
            }
            f10 = -MathHelper.cos(f10 * 3.141593F) * 0.5F + 0.5F;
            GL11.glTranslatef(0.0F, (0.0F * f6 - (1.0F - f1) * 1.2F - f10 * 0.5F) + 0.04F, -0.9F * f6);
            GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(f10 * -85F, 0.0F, 0.0F, 1.0F);
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTextureForDownloadableImage(mc.thePlayer.skinUrl, mc.thePlayer.getEntityTexture()));
            for(f15 = 0; f15 < 2; f15++)
            {
                f21 = f15 * 2 - 1;
                GL11.glPushMatrix();
                GL11.glTranslatef(-0F, -0.6F, 1.1F * (float)f21);
                GL11.glRotatef(-45 * f21, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(59F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-65 * f21, 0.0F, 1.0F, 0.0F);
                Render render1 = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
                RenderPlayer renderplayer1 = (RenderPlayer)render1;
                float f32 = 1.0F;
                GL11.glScalef(f32, f32, f32);
                renderplayer1.drawFirstPersonHand();
                GL11.glPopMatrix();
            }

            f15 = entityplayersp.getSwingProgress(f);
            f21 = MathHelper.sin(f15 * f15 * 3.141593F);
            float f27 = MathHelper.sin(MathHelper.sqrt_float(f15) * 3.141593F);
            GL11.glRotatef(-f21 * 20F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f27 * 20F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f27 * 80F, 1.0F, 0.0F, 0.0F);
            f15 = 0.38F;
            GL11.glScalef(f15, f15, f15);
            GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-1F, -1F, 0.0F);
            f21 = 0.015625F;
            GL11.glScalef(f21, f21, f21);
            mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/misc/mapbg.png"));
            Tessellator tessellator = Tessellator.instance;
            GL11.glNormal3f(0.0F, 0.0F, -1F);
            tessellator.startDrawingQuads();
            byte byte0 = 7;
            tessellator.addVertexWithUV(0 - byte0, 128 + byte0, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV(128 + byte0, 128 + byte0, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV(128 + byte0, 0 - byte0, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(0 - byte0, 0 - byte0, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
            MapData mapdata = Item.map.getMapData(itemstack, mc.theWorld);
            mapItemRenderer.renderMap(mc.thePlayer, mc.renderEngine, mapdata);
            GL11.glPopMatrix();
        } else
        if(itemstack != null)
        {
            GL11.glPushMatrix();
            float f7 = 0.8F;
            if(entityplayersp.func_35205_Y() > 0)
            {
                EnumAction enumaction = itemstack.getItemUseAction();
                if(enumaction == EnumAction.eat || enumaction == EnumAction.drink)
                {
                    float f16 = ((float)entityplayersp.func_35205_Y() - f) + 1.0F;
                    float f22 = 1.0F - f16 / (float)itemstack.getMaxItemUseDuration();
                    float f28 = f22;
                    float f30 = 1.0F - f28;
                    f30 = f30 * f30 * f30;
                    f30 = f30 * f30 * f30;
                    f30 = f30 * f30 * f30;
                    float f33 = 1.0F - f30;
                    GL11.glTranslatef(0.0F, MathHelper.abs(MathHelper.cos((f16 / 4F) * 3.141593F) * 0.1F) * (float)((double)f28 <= 0.20000000000000001D ? 0 : 1), 0.0F);
                    GL11.glTranslatef(f33 * 0.6F, -f33 * 0.5F, 0.0F);
                    GL11.glRotatef(f33 * 90F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(f33 * 10F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(f33 * 30F, 0.0F, 0.0F, 1.0F);
                }
            } else
            {
                float f11 = entityplayersp.getSwingProgress(f);
                float f17 = MathHelper.sin(f11 * 3.141593F);
                float f23 = MathHelper.sin(MathHelper.sqrt_float(f11) * 3.141593F);
                GL11.glTranslatef(-f23 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(f11) * 3.141593F * 2.0F) * 0.2F, -f17 * 0.2F);
            }
            GL11.glTranslatef(0.7F * f7, -0.65F * f7 - (1.0F - f1) * 0.6F, -0.9F * f7);
            GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            float f12 = entityplayersp.getSwingProgress(f);
            float f18 = MathHelper.sin(f12 * f12 * 3.141593F);
            float f24 = MathHelper.sin(MathHelper.sqrt_float(f12) * 3.141593F);
            GL11.glRotatef(-f18 * 20F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f24 * 20F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f24 * 80F, 1.0F, 0.0F, 0.0F);
            f12 = 0.4F;
            GL11.glScalef(f12, f12, f12);
            if(entityplayersp.func_35205_Y() > 0)
            {
                EnumAction enumaction1 = itemstack.getItemUseAction();
                if(enumaction1 == EnumAction.block)
                {
                    GL11.glTranslatef(-0.5F, 0.2F, 0.0F);
                    GL11.glRotatef(30F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-80F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(60F, 0.0F, 1.0F, 0.0F);
                } else
                if(enumaction1 == EnumAction.bow)
                {
                    GL11.glRotatef(-18F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-12F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-8F, 1.0F, 0.0F, 0.0F);
                    GL11.glTranslatef(-0.9F, 0.2F, 0.0F);
                    float f25 = (float)itemstack.getMaxItemUseDuration() - (((float)entityplayersp.func_35205_Y() - f) + 1.0F);
                    float f29 = f25 / 20F;
                    f29 = (f29 * f29 + f29 * 2.0F) / 3F;
                    if(f29 > 1.0F)
                    {
                        f29 = 1.0F;
                    }
                    if(f29 > 0.1F)
                    {
                        GL11.glTranslatef(0.0F, MathHelper.sin((f25 - 0.1F) * 1.3F) * 0.01F * (f29 - 0.1F), 0.0F);
                    }
                    GL11.glTranslatef(0.0F, 0.0F, f29 * 0.1F);
                    GL11.glRotatef(-335F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-50F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(0.0F, 0.5F, 0.0F);
                    float f31 = 1.0F + f29 * 0.2F;
                    GL11.glScalef(1.0F, 1.0F, f31);
                    GL11.glTranslatef(0.0F, -0.5F, 0.0F);
                    GL11.glRotatef(50F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(335F, 0.0F, 0.0F, 1.0F);
                }
            }
            if(itemstack.getItem().shouldRotateAroundWhenRendering())
            {
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            }
            if(itemstack.itemID == Item.potion.shiftedIndex)
            {
                renderItem(entityplayersp, itemstack, 0);
                GL11.glColor4f(f4, f4, f4, 1.0F);
                renderItem(entityplayersp, itemstack, 1);
            } else
            {
                renderItem(entityplayersp, itemstack, 0);
            }
            GL11.glPopMatrix();
        } else
        {
            GL11.glPushMatrix();
            float f8 = 0.8F;
            float f13 = entityplayersp.getSwingProgress(f);
            float f19 = MathHelper.sin(f13 * 3.141593F);
            float f26 = MathHelper.sin(MathHelper.sqrt_float(f13) * 3.141593F);
            GL11.glTranslatef(-f26 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(f13) * 3.141593F * 2.0F) * 0.4F, -f19 * 0.4F);
            GL11.glTranslatef(0.8F * f8, -0.75F * f8 - (1.0F - f1) * 0.6F, -0.9F * f8);
            GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            f13 = entityplayersp.getSwingProgress(f);
            f19 = MathHelper.sin(f13 * f13 * 3.141593F);
            f26 = MathHelper.sin(MathHelper.sqrt_float(f13) * 3.141593F);
            GL11.glRotatef(f26 * 70F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f19 * 20F, 0.0F, 0.0F, 1.0F);
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTextureForDownloadableImage(mc.thePlayer.skinUrl, mc.thePlayer.getEntityTexture()));
            GL11.glTranslatef(-1F, 3.6F, 3.5F);
            GL11.glRotatef(120F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(200F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef(5.6F, 0.0F, 0.0F);
            Render render = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
            RenderPlayer renderplayer = (RenderPlayer)render;
            f26 = 1.0F;
            GL11.glScalef(f26, f26, f26);
            renderplayer.drawFirstPersonHand();
            GL11.glPopMatrix();
        }
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        RenderHelper.disableStandardItemLighting();
    }

    public void renderOverlays(float f)
    {
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        if(mc.thePlayer.isBurning())
        {
            int i = mc.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, i);
            renderFireInFirstPerson(f);
        }
        if(mc.thePlayer.isEntityInsideOpaqueBlock())
        {
            int j = MathHelper.floor_double(mc.thePlayer.posX);
            int l = MathHelper.floor_double(mc.thePlayer.posY);
            int i1 = MathHelper.floor_double(mc.thePlayer.posZ);
            int j1 = mc.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, j1);
            int k1 = mc.theWorld.getBlockId(j, l, i1);
            if(mc.theWorld.isBlockNormalCube(j, l, i1))
            {
                renderInsideOfBlock(f, Block.blocksList[k1].getBlockTextureFromSide(2));
            } else
            {
                for(int l1 = 0; l1 < 8; l1++)
                {
                    float f1 = ((float)((l1 >> 0) % 2) - 0.5F) * mc.thePlayer.width * 0.9F;
                    float f2 = ((float)((l1 >> 1) % 2) - 0.5F) * mc.thePlayer.height * 0.2F;
                    float f3 = ((float)((l1 >> 2) % 2) - 0.5F) * mc.thePlayer.width * 0.9F;
                    int i2 = MathHelper.floor_float((float)j + f1);
                    int j2 = MathHelper.floor_float((float)l + f2);
                    int k2 = MathHelper.floor_float((float)i1 + f3);
                    if(mc.theWorld.isBlockNormalCube(i2, j2, k2))
                    {
                        k1 = mc.theWorld.getBlockId(i2, j2, k2);
                    }
                }

            }
            if(Block.blocksList[k1] != null)
            {
                renderInsideOfBlock(f, Block.blocksList[k1].getBlockTextureFromSide(2));
            }
        }
        if(mc.thePlayer.isInsideOfMaterial(Material.water))
        {
            int k = mc.renderEngine.getTexture("/misc/water.png");
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, k);
            renderWarpedTextureOverlay(f);
        }
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
    }

    private void renderInsideOfBlock(float f, int i)
    {
        Tessellator tessellator = Tessellator.instance;
        float f1 = mc.thePlayer.getEntityBrightness(f);
        f1 = 0.1F;
        GL11.glColor4f(f1, f1, f1, 0.5F);
        GL11.glPushMatrix();
        float f2 = -1F;
        float f3 = 1.0F;
        float f4 = -1F;
        float f5 = 1.0F;
        float f6 = -0.5F;
        float f7 = 0.0078125F;
        float f8 = (float)(i % 16) / 256F - f7;
        float f9 = ((float)(i % 16) + 15.99F) / 256F + f7;
        float f10 = (float)(i / 16) / 256F - f7;
        float f11 = ((float)(i / 16) + 15.99F) / 256F + f7;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(f2, f4, f6, f9, f11);
        tessellator.addVertexWithUV(f3, f4, f6, f8, f11);
        tessellator.addVertexWithUV(f3, f5, f6, f8, f10);
        tessellator.addVertexWithUV(f2, f5, f6, f9, f10);
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderWarpedTextureOverlay(float f)
    {
        Tessellator tessellator = Tessellator.instance;
        float f1 = mc.thePlayer.getEntityBrightness(f);
        GL11.glColor4f(f1, f1, f1, 0.5F);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        float f2 = 4F;
        float f3 = -1F;
        float f4 = 1.0F;
        float f5 = -1F;
        float f6 = 1.0F;
        float f7 = -0.5F;
        float f8 = -mc.thePlayer.rotationYaw / 64F;
        float f9 = mc.thePlayer.rotationPitch / 64F;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(f3, f5, f7, f2 + f8, f2 + f9);
        tessellator.addVertexWithUV(f4, f5, f7, 0.0F + f8, f2 + f9);
        tessellator.addVertexWithUV(f4, f6, f7, 0.0F + f8, 0.0F + f9);
        tessellator.addVertexWithUV(f3, f6, f7, f2 + f8, 0.0F + f9);
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042 /*GL_BLEND*/);
    }

    private void renderFireInFirstPerson(float f)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        float f1 = 1.0F;
        for(int i = 0; i < 2; i++)
        {
            GL11.glPushMatrix();
            int j = Block.fire.blockIndexInTexture + i * 16;
            int k = (j & 0xf) << 4;
            int l = j & 0xf0;
            float f2 = (float)k / 256F;
            float f3 = ((float)k + 15.99F) / 256F;
            float f4 = (float)l / 256F;
            float f5 = ((float)l + 15.99F) / 256F;
            float f6 = (0.0F - f1) / 2.0F;
            float f7 = f6 + f1;
            float f8 = 0.0F - f1 / 2.0F;
            float f9 = f8 + f1;
            float f10 = -0.5F;
            GL11.glTranslatef((float)(-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            GL11.glRotatef((float)(i * 2 - 1) * 10F, 0.0F, 1.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(f6, f8, f10, f3, f5);
            tessellator.addVertexWithUV(f7, f8, f10, f2, f5);
            tessellator.addVertexWithUV(f7, f9, f10, f2, f4);
            tessellator.addVertexWithUV(f6, f9, f10, f3, f4);
            tessellator.draw();
            GL11.glPopMatrix();
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042 /*GL_BLEND*/);
    }

    public void updateEquippedItem()
    {
        prevEquippedProgress = equippedProgress;
        EntityPlayerSP entityplayersp = mc.thePlayer;
        ItemStack itemstack = ((EntityPlayer) (entityplayersp)).inventory.getCurrentItem();
        ItemStack itemstack1 = itemstack;
        boolean flag = field_20099_f == ((EntityPlayer) (entityplayersp)).inventory.currentItem && itemstack1 == itemToRender;
        if(itemToRender == null && itemstack1 == null)
        {
            flag = true;
        }
        if(itemstack1 != null && itemToRender != null && itemstack1 != itemToRender && itemstack1.itemID == itemToRender.itemID && itemstack1.getItemDamage() == itemToRender.getItemDamage())
        {
            itemToRender = itemstack1;
            flag = true;
        }
        float f = 0.4F;
        float f1 = flag ? 1.0F : 0.0F;
        float f2 = f1 - equippedProgress;
        if(f2 < -f)
        {
            f2 = -f;
        }
        if(f2 > f)
        {
            f2 = f;
        }
        equippedProgress += f2;
        if(equippedProgress < 0.1F)
        {
            itemToRender = itemstack1;
            field_20099_f = ((EntityPlayer) (entityplayersp)).inventory.currentItem;
        }
    }

    public void func_9449_b()
    {
        equippedProgress = 0.0F;
    }

    public void func_9450_c()
    {
        equippedProgress = 0.0F;
    }
}
