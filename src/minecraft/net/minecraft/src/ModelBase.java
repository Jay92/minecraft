// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;

// Referenced classes of package net.minecraft.src:
//            TextureOffset, Entity, EntityLiving

public abstract class ModelBase
{

    public float onGround;
    public boolean isRiding;
    public List boxList;
    public boolean field_40301_k;
    private Map field_39000_a;
    public int textureWidth;
    public int textureHeight;

    public ModelBase()
    {
        isRiding = false;
        boxList = new ArrayList();
        field_40301_k = true;
        field_39000_a = new HashMap();
        textureWidth = 64;
        textureHeight = 32;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
    }

    public void setLivingAnimations(EntityLiving entityliving, float f, float f1, float f2)
    {
    }

    protected void setTextureOffset(String s, int i, int j)
    {
        field_39000_a.put(s, new TextureOffset(i, j));
    }

    public TextureOffset func_40297_a(String s)
    {
        return (TextureOffset)field_39000_a.get(s);
    }
}
