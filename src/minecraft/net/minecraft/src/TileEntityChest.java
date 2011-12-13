// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            TileEntity, IInventory, ItemStack, NBTTagCompound, 
//            NBTTagList, World, EntityPlayer, Block

public class TileEntityChest extends TileEntity
    implements IInventory
{

    private ItemStack chestContents[];
    public boolean field_35155_a;
    public TileEntityChest field_35152_b;
    public TileEntityChest field_35153_c;
    public TileEntityChest field_35150_d;
    public TileEntityChest field_35151_e;
    public float field_35148_f;
    public float field_35149_g;
    public int field_35156_h;
    private int field_35154_q;

    public TileEntityChest()
    {
        chestContents = new ItemStack[36];
        field_35155_a = false;
    }

    public int getSizeInventory()
    {
        return 27;
    }

    public ItemStack getStackInSlot(int i)
    {
        return chestContents[i];
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if(chestContents[i] != null)
        {
            if(chestContents[i].stackSize <= j)
            {
                ItemStack itemstack = chestContents[i];
                chestContents[i] = null;
                onInventoryChanged();
                return itemstack;
            }
            ItemStack itemstack1 = chestContents[i].splitStack(j);
            if(chestContents[i].stackSize == 0)
            {
                chestContents[i] = null;
            }
            onInventoryChanged();
            return itemstack1;
        } else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        chestContents[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
    }

    public String getInvName()
    {
        return "Chest";
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        chestContents = new ItemStack[getSizeInventory()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if(j >= 0 && j < chestContents.length)
            {
                chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < chestContents.length; i++)
        {
            if(chestContents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.setTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }
        return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }

    public void func_35144_b()
    {
        super.func_35144_b();
        field_35155_a = false;
    }

    public void func_35147_g()
    {
        if(field_35155_a)
        {
            return;
        }
        field_35155_a = true;
        field_35152_b = null;
        field_35153_c = null;
        field_35150_d = null;
        field_35151_e = null;
        if(worldObj.getBlockId(xCoord - 1, yCoord, zCoord) == Block.chest.blockID)
        {
            field_35150_d = (TileEntityChest)worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord);
        }
        if(worldObj.getBlockId(xCoord + 1, yCoord, zCoord) == Block.chest.blockID)
        {
            field_35153_c = (TileEntityChest)worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord);
        }
        if(worldObj.getBlockId(xCoord, yCoord, zCoord - 1) == Block.chest.blockID)
        {
            field_35152_b = (TileEntityChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1);
        }
        if(worldObj.getBlockId(xCoord, yCoord, zCoord + 1) == Block.chest.blockID)
        {
            field_35151_e = (TileEntityChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1);
        }
        if(field_35152_b != null)
        {
            field_35152_b.func_35144_b();
        }
        if(field_35151_e != null)
        {
            field_35151_e.func_35144_b();
        }
        if(field_35153_c != null)
        {
            field_35153_c.func_35144_b();
        }
        if(field_35150_d != null)
        {
            field_35150_d.func_35144_b();
        }
    }

    public void updateEntity()
    {
        super.updateEntity();
        func_35147_g();
        if((++field_35154_q % 20) * 4 == 0)
        {
            worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, field_35156_h);
        }
        field_35149_g = field_35148_f;
        float f = 0.1F;
        if(field_35156_h > 0 && field_35148_f == 0.0F && field_35152_b == null && field_35150_d == null)
        {
            double d = (double)xCoord + 0.5D;
            double d1 = (double)zCoord + 0.5D;
            if(field_35151_e != null)
            {
                d1 += 0.5D;
            }
            if(field_35153_c != null)
            {
                d += 0.5D;
            }
            worldObj.playSoundEffect(d, (double)yCoord + 0.5D, d1, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
        if(field_35156_h == 0 && field_35148_f > 0.0F || field_35156_h > 0 && field_35148_f < 1.0F)
        {
            float f1 = field_35148_f;
            if(field_35156_h > 0)
            {
                field_35148_f += f;
            } else
            {
                field_35148_f -= f;
            }
            if(field_35148_f > 1.0F)
            {
                field_35148_f = 1.0F;
            }
            float f2 = 0.5F;
            if(field_35148_f < f2 && f1 >= f2 && field_35152_b == null && field_35150_d == null)
            {
                double d2 = (double)xCoord + 0.5D;
                double d3 = (double)zCoord + 0.5D;
                if(field_35151_e != null)
                {
                    d3 += 0.5D;
                }
                if(field_35153_c != null)
                {
                    d2 += 0.5D;
                }
                worldObj.playSoundEffect(d2, (double)yCoord + 0.5D, d3, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
            if(field_35148_f < 0.0F)
            {
                field_35148_f = 0.0F;
            }
        }
    }

    public void func_35143_b(int i, int j)
    {
        if(i == 1)
        {
            field_35156_h = j;
        }
    }

    public void openChest()
    {
        field_35156_h++;
        worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, field_35156_h);
    }

    public void closeChest()
    {
        field_35156_h--;
        worldObj.playNoteAt(xCoord, yCoord, zCoord, 1, field_35156_h);
    }

    public void invalidate()
    {
        func_35144_b();
        func_35147_g();
        super.invalidate();
    }
}
