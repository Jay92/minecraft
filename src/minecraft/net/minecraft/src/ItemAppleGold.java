// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            ItemFood, EnumRarity, ItemStack

public class ItemAppleGold extends ItemFood
{

    public ItemAppleGold(int i, int j, float f, boolean flag)
    {
        super(i, j, f, flag);
    }

    public boolean func_40403_e(ItemStack itemstack)
    {
        return true;
    }

    public EnumRarity func_40398_f(ItemStack itemstack)
    {
        return EnumRarity.epic;
    }
}
