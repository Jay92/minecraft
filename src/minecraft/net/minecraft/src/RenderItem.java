// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            Render, RenderBlocks, EntityItem, MathHelper, 
//            ItemStack, Block, Item, ItemPotion, 
//            Tessellator, RenderManager, RenderEngine, FontRenderer, 
//            Entity

public class RenderItem extends Render
{

    private RenderBlocks renderBlocks;
    private Random random;
    public boolean field_27004_a;
    public float field_40268_b;

    public RenderItem()
    {
        renderBlocks = new RenderBlocks();
        random = new Random();
        field_27004_a = true;
        field_40268_b = 0.0F;
        shadowSize = 0.15F;
        field_194_c = 0.75F;
    }

    public void doRenderItem(EntityItem entityitem, double d, double d1, double d2, 
            float f, float f1)
    {
        random.setSeed(187L);
        ItemStack itemstack = entityitem.item;
        GL11.glPushMatrix();
        float f2 = MathHelper.sin(((float)entityitem.age + f1) / 10F + entityitem.field_804_d) * 0.1F + 0.1F;
        float f3 = (((float)entityitem.age + f1) / 20F + entityitem.field_804_d) * 57.29578F;
        byte byte0 = 1;
        if(entityitem.item.stackSize > 1)
        {
            byte0 = 2;
        }
        if(entityitem.item.stackSize > 5)
        {
            byte0 = 3;
        }
        if(entityitem.item.stackSize > 20)
        {
            byte0 = 4;
        }
        GL11.glTranslatef((float)d, (float)d1 + f2, (float)d2);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        if(itemstack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
        {
            GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
            loadTexture("/terrain.png");
            float f4 = 0.25F;
            int j = Block.blocksList[itemstack.itemID].getRenderType();
            if(j == 1 || j == 19 || j == 12 || j == 2)
            {
                f4 = 0.5F;
            }
            GL11.glScalef(f4, f4, f4);
            for(int l = 0; l < byte0; l++)
            {
                GL11.glPushMatrix();
                if(l > 0)
                {
                    float f7 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
                    float f11 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
                    float f14 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
                    GL11.glTranslatef(f7, f11, f14);
                }
                float f8 = 1.0F;
                renderBlocks.renderBlockOnInventory(Block.blocksList[itemstack.itemID], itemstack.getItemDamage(), f8);
                GL11.glPopMatrix();
            }

        } else
        if(itemstack.itemID == Item.potion.shiftedIndex)
        {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            char c = '\215';
            loadTexture("/gui/items.png");
            float f5 = 1.0F;
            if(field_27004_a)
            {
                int i1 = Item.itemsList[itemstack.itemID].getColorFromDamage(itemstack.getItemDamage());
                float f9 = (float)(i1 >> 16 & 0xff) / 255F;
                float f12 = (float)(i1 >> 8 & 0xff) / 255F;
                float f15 = (float)(i1 & 0xff) / 255F;
                GL11.glColor4f(f9 * f5, f12 * f5, f15 * f5, 1.0F);
            }
            func_40267_a(c, byte0);
            if(field_27004_a)
            {
                GL11.glColor4f(f5, f5, f5, 1.0F);
                func_40267_a(itemstack.getIconIndex(), byte0);
            }
        } else
        {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            int i = itemstack.getIconIndex();
            if(itemstack.itemID < 256)
            {
                loadTexture("/terrain.png");
            } else
            {
                loadTexture("/gui/items.png");
            }
            if(field_27004_a)
            {
                int k = Item.itemsList[itemstack.itemID].getColorFromDamage(itemstack.getItemDamage());
                float f6 = (float)(k >> 16 & 0xff) / 255F;
                float f10 = (float)(k >> 8 & 0xff) / 255F;
                float f13 = (float)(k & 0xff) / 255F;
                float f16 = 1.0F;
                GL11.glColor4f(f6 * f16, f10 * f16, f13 * f16, 1.0F);
            }
            func_40267_a(i, byte0);
        }
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
    }

    private void func_40267_a(int i, int j)
    {
        Tessellator tessellator = Tessellator.instance;
        float f = (float)((i % 16) * 16 + 0) / 256F;
        float f1 = (float)((i % 16) * 16 + 16) / 256F;
        float f2 = (float)((i / 16) * 16 + 0) / 256F;
        float f3 = (float)((i / 16) * 16 + 16) / 256F;
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        for(int k = 0; k < j; k++)
        {
            GL11.glPushMatrix();
            if(k > 0)
            {
                float f7 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                float f8 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                float f9 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                GL11.glTranslatef(f7, f8, f9);
            }
            GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            tessellator.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
            tessellator.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
            tessellator.addVertexWithUV(f4 - f5, 1.0F - f6, 0.0D, f1, f2);
            tessellator.addVertexWithUV(0.0F - f5, 1.0F - f6, 0.0D, f, f2);
            tessellator.draw();
            GL11.glPopMatrix();
        }

    }

    public void drawItemIntoGui(FontRenderer fontrenderer, RenderEngine renderengine, int i, int j, int k, int l, int i1)
    {
        if(i < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[i].getRenderType()))
        {
            int j1 = i;
            renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
            Block block = Block.blocksList[j1];
            GL11.glPushMatrix();
            GL11.glTranslatef(l - 2, i1 + 3, -3F + field_40268_b);
            GL11.glScalef(10F, 10F, 10F);
            GL11.glTranslatef(1.0F, 0.5F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, -1F);
            GL11.glRotatef(210F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            int i2 = Item.itemsList[i].getColorFromDamage(j);
            float f3 = (float)(i2 >> 16 & 0xff) / 255F;
            float f6 = (float)(i2 >> 8 & 0xff) / 255F;
            float f8 = (float)(i2 & 0xff) / 255F;
            if(field_27004_a)
            {
                GL11.glColor4f(f3, f6, f8, 1.0F);
            }
            GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
            renderBlocks.useInventoryTint = field_27004_a;
            renderBlocks.renderBlockOnInventory(block, j, 1.0F);
            renderBlocks.useInventoryTint = true;
            GL11.glPopMatrix();
        } else
        if(i == Item.potion.shiftedIndex)
        {
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            renderengine.bindTexture(renderengine.getTexture("/gui/items.png"));
            char c = '\215';
            int l1 = Item.itemsList[i].getColorFromDamage(j);
            float f1 = (float)(l1 >> 16 & 0xff) / 255F;
            float f4 = (float)(l1 >> 8 & 0xff) / 255F;
            float f7 = (float)(l1 & 0xff) / 255F;
            if(field_27004_a)
            {
                GL11.glColor4f(f1, f4, f7, 1.0F);
            }
            renderTexturedQuad(l, i1, (c % 16) * 16, (c / 16) * 16, 16, 16);
            if(field_27004_a)
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
            renderTexturedQuad(l, i1, (k % 16) * 16, (k / 16) * 16, 16, 16);
            GL11.glEnable(2896 /*GL_LIGHTING*/);
        } else
        if(k >= 0)
        {
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            if(i < 256)
            {
                renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
            } else
            {
                renderengine.bindTexture(renderengine.getTexture("/gui/items.png"));
            }
            int k1 = Item.itemsList[i].getColorFromDamage(j);
            float f = (float)(k1 >> 16 & 0xff) / 255F;
            float f2 = (float)(k1 >> 8 & 0xff) / 255F;
            float f5 = (float)(k1 & 0xff) / 255F;
            if(field_27004_a)
            {
                GL11.glColor4f(f, f2, f5, 1.0F);
            }
            renderTexturedQuad(l, i1, (k % 16) * 16, (k / 16) * 16, 16, 16);
            GL11.glEnable(2896 /*GL_LIGHTING*/);
        }
        GL11.glEnable(2884 /*GL_CULL_FACE*/);
    }

    public void renderItemIntoGUI(FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemstack, int i, int j)
    {
        if(itemstack == null)
        {
            return;
        }
        drawItemIntoGui(fontrenderer, renderengine, itemstack.itemID, itemstack.getItemDamage(), itemstack.getIconIndex(), i, j);
        if(itemstack != null && itemstack.func_40713_r())
        {
            GL11.glDepthFunc(516);
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            GL11.glDepthMask(false);
            renderengine.bindTexture(renderengine.getTexture("%blur%/misc/glint.png"));
            field_40268_b -= 50F;
            GL11.glEnable(3042 /*GL_BLEND*/);
            GL11.glBlendFunc(774, 774);
            GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
            func_40266_a(i * 0x19b4ca14 + j * 0x1eafff1, i - 2, j - 2, 20, 20);
            GL11.glDisable(3042 /*GL_BLEND*/);
            GL11.glDepthMask(true);
            field_40268_b += 50F;
            GL11.glEnable(2896 /*GL_LIGHTING*/);
            GL11.glDepthFunc(515);
        }
    }

    private void func_40266_a(int i, int j, int k, int l, int i1)
    {
        for(int j1 = 0; j1 < 2; j1++)
        {
            if(j1 == 0)
            {
                GL11.glBlendFunc(768, 1);
            }
            if(j1 == 1)
            {
                GL11.glBlendFunc(768, 1);
            }
            float f = 0.00390625F;
            float f1 = 0.00390625F;
            float f2 = ((float)(System.currentTimeMillis() % (long)(3000 + j1 * 1873)) / (3000F + (float)(j1 * 1873))) * 256F;
            float f3 = 0.0F;
            Tessellator tessellator = Tessellator.instance;
            float f4 = 4F;
            if(j1 == 1)
            {
                f4 = -1F;
            }
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(j + 0, k + i1, field_40268_b, (f2 + (float)i1 * f4) * f, (f3 + (float)i1) * f1);
            tessellator.addVertexWithUV(j + l, k + i1, field_40268_b, (f2 + (float)l + (float)i1 * f4) * f, (f3 + (float)i1) * f1);
            tessellator.addVertexWithUV(j + l, k + 0, field_40268_b, (f2 + (float)l) * f, (f3 + 0.0F) * f1);
            tessellator.addVertexWithUV(j + 0, k + 0, field_40268_b, (f2 + 0.0F) * f, (f3 + 0.0F) * f1);
            tessellator.draw();
        }

    }

    public void renderItemOverlayIntoGUI(FontRenderer fontrenderer, RenderEngine renderengine, ItemStack itemstack, int i, int j)
    {
        if(itemstack == null)
        {
            return;
        }
        if(itemstack.stackSize > 1)
        {
            String s = (new StringBuilder()).append("").append(itemstack.stackSize).toString();
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
            fontrenderer.drawStringWithShadow(s, (i + 19) - 2 - fontrenderer.getStringWidth(s), j + 6 + 3, 0xffffff);
            GL11.glEnable(2896 /*GL_LIGHTING*/);
            GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        }
        if(itemstack.isItemDamaged())
        {
            int k = (int)Math.round(13D - ((double)itemstack.getItemDamageForDisplay() * 13D) / (double)itemstack.getMaxDamage());
            int l = (int)Math.round(255D - ((double)itemstack.getItemDamageForDisplay() * 255D) / (double)itemstack.getMaxDamage());
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
            GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
            Tessellator tessellator = Tessellator.instance;
            int i1 = 255 - l << 16 | l << 8;
            int j1 = (255 - l) / 4 << 16 | 0x3f00;
            renderQuad(tessellator, i + 2, j + 13, 13, 2, 0);
            renderQuad(tessellator, i + 2, j + 13, 12, 1, j1);
            renderQuad(tessellator, i + 2, j + 13, k, 1, i1);
            GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
            GL11.glEnable(2896 /*GL_LIGHTING*/);
            GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void renderQuad(Tessellator tessellator, int i, int j, int k, int l, int i1)
    {
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(i1);
        tessellator.addVertex(i + 0, j + 0, 0.0D);
        tessellator.addVertex(i + 0, j + l, 0.0D);
        tessellator.addVertex(i + k, j + l, 0.0D);
        tessellator.addVertex(i + k, j + 0, 0.0D);
        tessellator.draw();
    }

    public void renderTexturedQuad(int i, int j, int k, int l, int i1, int j1)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(i + 0, j + j1, field_40268_b, (float)(k + 0) * f, (float)(l + j1) * f1);
        tessellator.addVertexWithUV(i + i1, j + j1, field_40268_b, (float)(k + i1) * f, (float)(l + j1) * f1);
        tessellator.addVertexWithUV(i + i1, j + 0, field_40268_b, (float)(k + i1) * f, (float)(l + 0) * f1);
        tessellator.addVertexWithUV(i + 0, j + 0, field_40268_b, (float)(k + 0) * f, (float)(l + 0) * f1);
        tessellator.draw();
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        doRenderItem((EntityItem)entity, d, d1, d2, f, f1);
    }
}
