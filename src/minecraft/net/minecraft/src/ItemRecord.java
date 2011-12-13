// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;

// Referenced classes of package net.minecraft.src:
//            Item, World, Block, BlockJukeBox, 
//            ItemStack, EnumRarity, EntityPlayer

public class ItemRecord extends Item
{

    public final String recordName;

    protected ItemRecord(int i, String s)
    {
        super(i);
        recordName = s;
        maxStackSize = 1;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(world.getBlockId(i, j, k) == Block.jukebox.blockID && world.getBlockMetadata(i, j, k) == 0)
        {
            if(world.multiplayerWorld)
            {
                return true;
            } else
            {
                ((BlockJukeBox)Block.jukebox).ejectRecord(world, i, j, k, shiftedIndex);
                world.playAuxSFXAtEntity(null, 1005, i, j, k, shiftedIndex);
                itemstack.stackSize--;
                return true;
            }
        } else
        {
            return false;
        }
    }

    public void func_40404_a(ItemStack itemstack, List list)
    {
        list.add((new StringBuilder()).append("C418 - ").append(recordName).toString());
    }

    public EnumRarity func_40398_f(ItemStack itemstack)
    {
        return EnumRarity.rare;
    }
}
