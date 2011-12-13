// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            Slot, ItemStack, Item, ItemPotion, 
//            AchievementList, EntityPlayer, ContainerBrewingStand, IInventory

class SlotBrewingStandPotion extends Slot
{

    private EntityPlayer field_40440_f;
    final ContainerBrewingStand field_40441_a; /* synthetic field */

    public SlotBrewingStandPotion(ContainerBrewingStand containerbrewingstand, EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
        field_40441_a = containerbrewingstand;
        field_40440_f = entityplayer;
    }

    public boolean isItemValid(ItemStack itemstack)
    {
        return itemstack != null && (itemstack.itemID == Item.potion.shiftedIndex || itemstack.itemID == Item.glassBottle.shiftedIndex);
    }

    public int getSlotStackLimit()
    {
        return 1;
    }

    public void onPickupFromSlot(ItemStack itemstack)
    {
        if(itemstack.itemID == Item.potion.shiftedIndex && itemstack.getItemDamage() > 0)
        {
            field_40440_f.addStat(AchievementList.potion, 1);
        }
        super.onPickupFromSlot(itemstack);
    }
}
