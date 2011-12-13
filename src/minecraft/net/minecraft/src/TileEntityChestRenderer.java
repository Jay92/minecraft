// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            TileEntitySpecialRenderer, ModelChest, ModelLargeChest, TileEntityChest, 
//            BlockChest, ModelRenderer, TileEntity

public class TileEntityChestRenderer extends TileEntitySpecialRenderer
{

    private ModelChest field_35377_b;
    private ModelChest field_35378_c;

    public TileEntityChestRenderer()
    {
        field_35377_b = new ModelChest();
        field_35378_c = new ModelLargeChest();
    }

    public void func_35376_a(TileEntityChest tileentitychest, double d, double d1, double d2, 
            float f)
    {
        int i;
        if(tileentitychest.worldObj == null)
        {
            i = 0;
        } else
        {
            Block block = tileentitychest.getBlockType();
            i = tileentitychest.getBlockMetadata();
            if(block != null && i == 0)
            {
                ((BlockChest)block).unifyAdjacentChests(tileentitychest.worldObj, tileentitychest.xCoord, tileentitychest.yCoord, tileentitychest.zCoord);
                i = tileentitychest.getBlockMetadata();
            }
            tileentitychest.func_35147_g();
        }
        if(tileentitychest.field_35152_b != null || tileentitychest.field_35150_d != null)
        {
            return;
        }
        ModelChest modelchest;
        if(tileentitychest.field_35153_c != null || tileentitychest.field_35151_e != null)
        {
            modelchest = field_35378_c;
            bindTextureByName("/item/largechest.png");
        } else
        {
            modelchest = field_35377_b;
            bindTextureByName("/item/chest.png");
        }
        GL11.glPushMatrix();
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)d, (float)d1 + 1.0F, (float)d2 + 1.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        int j = 0;
        if(i == 2)
        {
            j = 180;
        }
        if(i == 3)
        {
            j = 0;
        }
        if(i == 4)
        {
            j = 90;
        }
        if(i == 5)
        {
            j = -90;
        }
        if(i == 2 && tileentitychest.field_35153_c != null)
        {
            GL11.glTranslatef(1.0F, 0.0F, 0.0F);
        }
        if(i == 5 && tileentitychest.field_35151_e != null)
        {
            GL11.glTranslatef(0.0F, 0.0F, -1F);
        }
        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        float f1 = tileentitychest.field_35149_g + (tileentitychest.field_35148_f - tileentitychest.field_35149_g) * f;
        if(tileentitychest.field_35152_b != null)
        {
            float f2 = tileentitychest.field_35152_b.field_35149_g + (tileentitychest.field_35152_b.field_35148_f - tileentitychest.field_35152_b.field_35149_g) * f;
            if(f2 > f1)
            {
                f1 = f2;
            }
        }
        if(tileentitychest.field_35150_d != null)
        {
            float f3 = tileentitychest.field_35150_d.field_35149_g + (tileentitychest.field_35150_d.field_35148_f - tileentitychest.field_35150_d.field_35149_g) * f;
            if(f3 > f1)
            {
                f1 = f3;
            }
        }
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1;
        modelchest.chestLid.rotateAngleX = -((f1 * 3.141593F) / 2.0F);
        modelchest.func_35402_a();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, 
            float f)
    {
        func_35376_a((TileEntityChest)tileentity, d, d1, d2, f);
    }
}
