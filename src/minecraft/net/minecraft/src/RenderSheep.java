// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            RenderLiving, EntitySheep, ModelBase, EntityLiving, 
//            Entity

public class RenderSheep extends RenderLiving
{

    public RenderSheep(ModelBase modelbase, ModelBase modelbase1, float f)
    {
        super(modelbase, f);
        setRenderPassModel(modelbase1);
    }

    protected int setWoolColorAndRender(EntitySheep entitysheep, int i, float f)
    {
        if(i == 0 && !entitysheep.getSheared())
        {
            loadTexture("/mob/sheep_fur.png");
            float f1 = 1.0F;
            int j = entitysheep.getFleeceColor();
            GL11.glColor3f(f1 * EntitySheep.fleeceColorTable[j][0], f1 * EntitySheep.fleeceColorTable[j][1], f1 * EntitySheep.fleeceColorTable[j][2]);
            return 1;
        } else
        {
            return -1;
        }
    }

    public void func_40271_a(EntitySheep entitysheep, double d, double d1, double d2, 
            float f, float f1)
    {
        super.doRenderLiving(entitysheep, d, d1, d2, f, f1);
    }

    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return setWoolColorAndRender((EntitySheep)entityliving, i, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        func_40271_a((EntitySheep)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        func_40271_a((EntitySheep)entity, d, d1, d2, f, f1);
    }
}
