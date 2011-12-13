// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            RenderLiving, EntityPig, ModelBase, EntityLiving, 
//            Entity

public class RenderPig extends RenderLiving
{

    public RenderPig(ModelBase modelbase, ModelBase modelbase1, float f)
    {
        super(modelbase, f);
        setRenderPassModel(modelbase1);
    }

    protected int renderSaddledPig(EntityPig entitypig, int i, float f)
    {
        loadTexture("/mob/saddle.png");
        return i != 0 || !entitypig.getSaddled() ? -1 : 1;
    }

    public void func_40286_a(EntityPig entitypig, double d, double d1, double d2, 
            float f, float f1)
    {
        super.doRenderLiving(entitypig, d, d1, d2, f, f1);
    }

    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return renderSaddledPig((EntityPig)entityliving, i, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        func_40286_a((EntityPig)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        func_40286_a((EntityPig)entity, d, d1, d2, f, f1);
    }
}
