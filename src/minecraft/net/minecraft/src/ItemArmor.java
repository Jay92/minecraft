// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            Item, EnumArmorMaterial

public class ItemArmor extends Item
{

    private static final int maxDamageArray[] = {
        11, 16, 15, 13
    };
    public final int armorType;
    public final int damageReduceAmount;
    public final int renderIndex;
    private final EnumArmorMaterial material;

    public ItemArmor(int i, EnumArmorMaterial enumarmormaterial, int j, int k)
    {
        super(i);
        material = enumarmormaterial;
        armorType = k;
        renderIndex = j;
        damageReduceAmount = enumarmormaterial.func_40574_b(k);
        setMaxDamage(enumarmormaterial.func_40576_a(k));
        maxStackSize = 1;
    }

    public int getItemEnchantability()
    {
        return material.getEnchantability();
    }

    static int[] func_40436_c()
    {
        return maxDamageArray;
    }

}
