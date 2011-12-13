// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            RenderLiving, ModelSnowMan, ItemStack, Block, 
//            Item, ModelRenderer, RenderBlocks, RenderManager, 
//            ItemRenderer, EntitySnowman, EntityLiving

public class RenderSnowMan extends RenderLiving
{

    private ModelSnowMan field_40289_c;

    public RenderSnowMan()
    {
        super(new ModelSnowMan(), 0.5F);
        field_40289_c = (ModelSnowMan)super.mainModel;
        setRenderPassModel(field_40289_c);
    }

    protected void func_40288_a(EntitySnowman entitysnowman, float f)
    {
        super.renderEquippedItems(entitysnowman, f);
        ItemStack itemstack = new ItemStack(Block.pumpkin, 1);
        if(itemstack != null && itemstack.getItem().shiftedIndex < 256)
        {
            GL11.glPushMatrix();
            field_40289_c.field_40305_c.postRender(0.0625F);
            if(RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
            {
                float f1 = 0.625F;
                GL11.glTranslatef(0.0F, -0.34375F, 0.0F);
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1, -f1, f1);
            }
            renderManager.itemRenderer.renderItem(entitysnowman, itemstack, 0);
            GL11.glPopMatrix();
        }
    }

    protected void renderEquippedItems(EntityLiving entityliving, float f)
    {
        func_40288_a((EntitySnowman)entityliving, f);
    }
}
