// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            EntityLiving, DamageSource, EntityPlayer, PotionEffect, 
//            PotionHealth

public class Potion
{

    public static final Potion potionTypes[] = new Potion[32];
    public static final Potion field_35676_b = null;
    public static final Potion potionSpeed = (new Potion(1, false, 0x7cafc6)).setPotionName("potion.moveSpeed").func_40618_a(0, 0);
    public static final Potion potionSlowdown = (new Potion(2, true, 0x5a6c81)).setPotionName("potion.moveSlowdown").func_40618_a(1, 0);
    public static final Potion potionDigSpeed = (new Potion(3, false, 0xd9c043)).setPotionName("potion.digSpeed").func_40618_a(2, 0).func_40614_a(1.5D);
    public static final Potion potionDigSlow = (new Potion(4, true, 0x4a4217)).setPotionName("potion.digSlowDown").func_40618_a(3, 0);
    public static final Potion potionDamageBoost = (new Potion(5, false, 0x932423)).setPotionName("potion.damageBoost").func_40618_a(4, 0);
    public static final Potion potionHeal = (new PotionHealth(6, false, 0xf82423)).setPotionName("potion.heal");
    public static final Potion potionHealDamage = (new PotionHealth(7, true, 0x430a09)).setPotionName("potion.harm");
    public static final Potion potionJump = (new Potion(8, false, 0x786297)).setPotionName("potion.jump").func_40618_a(2, 1);
    public static final Potion potionConfusion = (new Potion(9, true, 0x551d4a)).setPotionName("potion.confusion").func_40618_a(3, 1).func_40614_a(0.25D);
    public static final Potion potionRegeneration = (new Potion(10, false, 0xcd5cab)).setPotionName("potion.regeneration").func_40618_a(7, 0).func_40614_a(0.25D);
    public static final Potion potionResistance = (new Potion(11, false, 0x99453a)).setPotionName("potion.resistance").func_40618_a(6, 1);
    public static final Potion potionFireReistance = (new Potion(12, false, 0xe49a3a)).setPotionName("potion.fireResistance").func_40618_a(7, 1);
    public static final Potion potionWaterBreathing = (new Potion(13, false, 0x2e5299)).setPotionName("potion.waterBreathing").func_40618_a(0, 2);
    public static final Potion potionInvisibility = (new Potion(14, false, 0x7f8392)).setPotionName("potion.invisibility").func_40618_a(0, 1).func_40616_h();
    public static final Potion potionBlindness = (new Potion(15, true, 0x1f1f23)).setPotionName("potion.blindness").func_40618_a(5, 1).func_40614_a(0.25D);
    public static final Potion potionNightVision = (new Potion(16, false, 0x1f1fa1)).setPotionName("potion.nightVision").func_40618_a(4, 1).func_40616_h();
    public static final Potion potionHunger = (new Potion(17, true, 0x587653)).setPotionName("potion.hunger").func_40618_a(1, 1);
    public static final Potion potionWeakness = (new Potion(18, true, 0x484d48)).setPotionName("potion.weakness").func_40618_a(5, 0);
    public static final Potion potionPoison = (new Potion(19, true, 0x4e9331)).setPotionName("potion.poison").func_40618_a(6, 0).func_40614_a(0.25D);
    public static final Potion field_35688_v = null;
    public static final Potion field_35687_w = null;
    public static final Potion field_35697_x = null;
    public static final Potion field_35696_y = null;
    public static final Potion field_35695_z = null;
    public static final Potion field_35667_A = null;
    public static final Potion field_35668_B = null;
    public static final Potion field_35669_C = null;
    public static final Potion field_35663_D = null;
    public static final Potion field_35664_E = null;
    public static final Potion field_35665_F = null;
    public static final Potion field_35666_G = null;
    public final int id;
    private String name;
    private int field_40627_J;
    private final boolean field_40628_K;
    private double field_40624_L;
    private boolean field_40625_M;
    private final int field_40626_N;

    protected Potion(int i, boolean flag, int j)
    {
        name = "";
        field_40627_J = -1;
        id = i;
        potionTypes[i] = this;
        field_40628_K = flag;
        if(flag)
        {
            field_40624_L = 0.5D;
        } else
        {
            field_40624_L = 1.0D;
        }
        field_40626_N = j;
    }

    protected Potion func_40618_a(int i, int j)
    {
        field_40627_J = i + j * 8;
        return this;
    }

    public int func_40619_a()
    {
        return id;
    }

    public void performEffect(EntityLiving entityliving, int i)
    {
        if(id == potionRegeneration.id)
        {
            if(entityliving.getEntityHealth() < entityliving.getMaxHealth())
            {
                entityliving.heal(1);
            }
        } else
        if(id == potionPoison.id)
        {
            if(entityliving.getEntityHealth() > 1)
            {
                entityliving.attackEntityFrom(DamageSource.magic, 1);
            }
        } else
        if(id == potionHunger.id && (entityliving instanceof EntityPlayer))
        {
            ((EntityPlayer)entityliving).addExhaustion(0.025F * (float)(i + 1));
        } else
        if(id == potionHeal.id && !entityliving.func_40122_aP() || id == potionHealDamage.id && entityliving.func_40122_aP())
        {
            entityliving.heal(6 << i);
        } else
        if(id == potionHealDamage.id && !entityliving.func_40122_aP() || id == potionHeal.id && entityliving.func_40122_aP())
        {
            entityliving.attackEntityFrom(DamageSource.magic, 6 << i);
        }
    }

    public void func_40613_a(EntityLiving entityliving, EntityLiving entityliving1, int i, double d)
    {
        if(id == potionHeal.id && !entityliving1.func_40122_aP() || id == potionHealDamage.id && entityliving1.func_40122_aP())
        {
            int j = (int)(d * (double)(6 << i) + 0.5D);
            entityliving1.heal(j);
        } else
        if(id == potionHealDamage.id && !entityliving1.func_40122_aP() || id == potionHeal.id && entityliving1.func_40122_aP())
        {
            int k = (int)(d * (double)(6 << i) + 0.5D);
            if(entityliving == null)
            {
                entityliving1.attackEntityFrom(DamageSource.magic, k);
            } else
            {
                entityliving1.attackEntityFrom(DamageSource.func_40542_b(entityliving1, entityliving), k);
            }
        }
    }

    public boolean func_40622_b()
    {
        return false;
    }

    public boolean isReady(int i, int j)
    {
        if(id == potionRegeneration.id || id == potionPoison.id)
        {
            int k = 25 >> j;
            if(k > 0)
            {
                return i % k == 0;
            } else
            {
                return true;
            }
        }
        return id == potionHunger.id;
    }

    public Potion setPotionName(String s)
    {
        name = s;
        return this;
    }

    public String func_40623_c()
    {
        return name;
    }

    public boolean func_40617_d()
    {
        return field_40627_J >= 0;
    }

    public int func_40611_e()
    {
        return field_40627_J;
    }

    public boolean func_40615_f()
    {
        return field_40628_K;
    }

    public static String func_40620_a(PotionEffect potioneffect)
    {
        int i = potioneffect.getDuration();
        int j = i / 20;
        int k = j / 60;
        j %= 60;
        if(j < 10)
        {
            return (new StringBuilder()).append(k).append(":0").append(j).toString();
        } else
        {
            return (new StringBuilder()).append(k).append(":").append(j).toString();
        }
    }

    protected Potion func_40614_a(double d)
    {
        field_40624_L = d;
        return this;
    }

    public double func_40610_g()
    {
        return field_40624_L;
    }

    public Potion func_40616_h()
    {
        field_40625_M = true;
        return this;
    }

    public boolean func_40612_i()
    {
        return field_40625_M;
    }

    public int func_40621_j()
    {
        return field_40626_N;
    }

}
