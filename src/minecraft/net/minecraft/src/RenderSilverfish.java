// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.PrintStream;

// Referenced classes of package net.minecraft.src:
//            RenderLiving, ModelSilverfish, EntitySilverfish, EntityLiving, 
//            Entity

public class RenderSilverfish extends RenderLiving
{

    private int field_35450_c;

    public RenderSilverfish()
    {
        super(new ModelSilverfish(), 0.3F);
    }

    protected float getSilverfishDeathRotation(EntitySilverfish entitysilverfish)
    {
        return 180F;
    }

    public void renderSilverfish(EntitySilverfish entitysilverfish, double d, double d1, double d2, 
            float f, float f1)
    {
        int i = ((ModelSilverfish)mainModel).func_35395_a();
        if(i != field_35450_c)
        {
            field_35450_c = i;
            mainModel = new ModelSilverfish();
            System.out.println("new silverfish model");
        }
        super.doRenderLiving(entitysilverfish, d, d1, d2, f, f1);
    }

    protected int shouldSilverfishRenderPass(EntitySilverfish entitysilverfish, int i, float f)
    {
        return -1;
    }

    protected float getDeathMaxRotation(EntityLiving entityliving)
    {
        return getSilverfishDeathRotation((EntitySilverfish)entityliving);
    }

    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return shouldSilverfishRenderPass((EntitySilverfish)entityliving, i, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        renderSilverfish((EntitySilverfish)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        renderSilverfish((EntitySilverfish)entity, d, d1, d2, f, f1);
    }
}
