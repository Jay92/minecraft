// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            EntityDamageSource, Entity

public class EntityDamageSourceIndirect extends EntityDamageSource
{

    private Entity damageSourceProjectile;

    public EntityDamageSourceIndirect(String s, Entity entity, Entity entity1)
    {
        super(s, entity);
        damageSourceProjectile = entity1;
    }

    public Entity getEntity()
    {
        return damageSourceProjectile;
    }
}
