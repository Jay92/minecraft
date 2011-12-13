// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            DamageSource, Entity

public class EntityDamageSource extends DamageSource
{

    private Entity damageSourceEntity;

    public EntityDamageSource(String s, Entity entity)
    {
        super(s);
        damageSourceEntity = entity;
    }

    public Entity getEntity()
    {
        return damageSourceEntity;
    }
}
