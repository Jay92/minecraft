// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            TileEntity, IInventory, ItemStack, World, 
//            Item, ItemPotion, PotionHelper, NBTTagCompound, 
//            NBTTagList, EntityPlayer

public class TileEntityBrewingStand extends TileEntity
    implements IInventory
{

    private ItemStack field_40058_a[];
    private int field_40056_b;
    private int field_40057_c;
    private int field_40055_d;

    public TileEntityBrewingStand()
    {
        field_40058_a = new ItemStack[4];
    }

    public String getInvName()
    {
        return "Brewing Stand";
    }

    public int getSizeInventory()
    {
        return field_40058_a.length;
    }

    public void updateEntity()
    {
        if(field_40056_b > 0)
        {
            field_40056_b--;
            if(field_40056_b == 0)
            {
                func_40052_p();
                onInventoryChanged();
            } else
            if(!func_40050_o())
            {
                field_40056_b = 0;
                onInventoryChanged();
            } else
            if(field_40055_d != field_40058_a[3].itemID)
            {
                field_40056_b = 0;
                onInventoryChanged();
            }
        } else
        if(func_40050_o())
        {
            field_40056_b = 600;
            field_40055_d = field_40058_a[3].itemID;
        }
        int i = func_40054_n();
        if(i != field_40057_c)
        {
            field_40057_c = i;
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i);
        }
        super.updateEntity();
    }

    public int func_40053_g()
    {
        return field_40056_b;
    }

    private boolean func_40050_o()
    {
        if(field_40058_a[3] == null || field_40058_a[3].stackSize <= 0)
        {
            return false;
        }
        ItemStack itemstack = field_40058_a[3];
        if(!Item.itemsList[itemstack.itemID].func_40406_n())
        {
            return false;
        }
        boolean flag = false;
        for(int i = 0; i < 3; i++)
        {
            if(field_40058_a[i] == null || field_40058_a[i].itemID != Item.potion.shiftedIndex)
            {
                continue;
            }
            int j = field_40058_a[i].getItemDamage();
            int k = func_40051_b(j, itemstack);
            if(!ItemPotion.func_40433_c(j) && ItemPotion.func_40433_c(k))
            {
                flag = true;
                break;
            }
            java.util.List list = Item.potion.func_40431_c_(j);
            java.util.List list1 = Item.potion.func_40431_c_(k);
            if(j > 0 && list == list1 || list != null && (list.equals(list1) || list1 == null) || j == k)
            {
                continue;
            }
            flag = true;
            break;
        }

        return flag;
    }

    private void func_40052_p()
    {
        if(!func_40050_o())
        {
            return;
        }
        ItemStack itemstack = field_40058_a[3];
        for(int i = 0; i < 3; i++)
        {
            if(field_40058_a[i] == null || field_40058_a[i].itemID != Item.potion.shiftedIndex)
            {
                continue;
            }
            int j = field_40058_a[i].getItemDamage();
            int k = func_40051_b(j, itemstack);
            java.util.List list = Item.potion.func_40431_c_(j);
            java.util.List list1 = Item.potion.func_40431_c_(k);
            if(j > 0 && list == list1 || list != null && (list.equals(list1) || list1 == null))
            {
                if(!ItemPotion.func_40433_c(j) && ItemPotion.func_40433_c(k))
                {
                    field_40058_a[i].setItemDamage(k);
                }
                continue;
            }
            if(j != k)
            {
                field_40058_a[i].setItemDamage(k);
            }
        }

        if(Item.itemsList[itemstack.itemID].hasContainerItem())
        {
            field_40058_a[3] = new ItemStack(Item.itemsList[itemstack.itemID].getContainerItem());
        } else
        {
            field_40058_a[3].stackSize--;
            if(field_40058_a[3].stackSize <= 0)
            {
                field_40058_a[3] = null;
            }
        }
    }

    private int func_40051_b(int i, ItemStack itemstack)
    {
        if(itemstack == null)
        {
            return i;
        }
        if(Item.itemsList[itemstack.itemID].func_40406_n())
        {
            return PotionHelper.func_40356_a(i, Item.itemsList[itemstack.itemID].func_40405_m());
        } else
        {
            return i;
        }
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        field_40058_a = new ItemStack[getSizeInventory()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < field_40058_a.length)
            {
                field_40058_a[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        field_40056_b = nbttagcompound.getShort("BrewTime");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("BrewTime", (short)field_40056_b);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < field_40058_a.length; i++)
        {
            if(field_40058_a[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                field_40058_a[i].writeToNBT(nbttagcompound1);
                nbttaglist.setTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public ItemStack getStackInSlot(int i)
    {
        if(i >= 0 && i < field_40058_a.length)
        {
            return field_40058_a[i];
        } else
        {
            return null;
        }
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if(i >= 0 && i < field_40058_a.length)
        {
            ItemStack itemstack = field_40058_a[i];
            field_40058_a[i] = null;
            return itemstack;
        } else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        if(i >= 0 && i < field_40058_a.length)
        {
            field_40058_a[i] = itemstack;
        }
    }

    public int getInventoryStackLimit()
    {
        return 1;
    }

    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }
        return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }

    public void openChest()
    {
    }

    public void closeChest()
    {
    }

    public void func_40049_b(int i)
    {
        field_40056_b = i;
    }

    public int func_40054_n()
    {
        int i = 0;
        for(int j = 0; j < 3; j++)
        {
            if(field_40058_a[j] != null)
            {
                i |= 1 << j;
            }
        }

        return i;
    }
}
