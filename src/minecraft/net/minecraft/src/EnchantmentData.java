// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            WeightedRandomChoice, Enchantment

public class EnchantmentData extends WeightedRandomChoice
{

    public final Enchantment field_40264_a;
    public final int field_40263_b;

    public EnchantmentData(Enchantment enchantment, int i)
    {
        super(enchantment.getWeight());
        field_40264_a = enchantment;
        field_40263_b = i;
    }
}
