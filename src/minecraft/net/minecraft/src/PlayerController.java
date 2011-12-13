// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            Block, World, ItemStack, EntityPlayer, 
//            InventoryPlayer, PlayerControllerCreative, EntityPlayerSP, WorldProvider, 
//            Container, Entity

public abstract class PlayerController
{

    protected final Minecraft mc;
    public boolean isInTestMode;

    public PlayerController(Minecraft minecraft)
    {
        isInTestMode = false;
        mc = minecraft;
    }

    public void onWorldChange(World world)
    {
    }

    public abstract void clickBlock(int i, int j, int k, int l);

    public boolean sendBlockRemoved(int i, int j, int k, int l)
    {
        World world = mc.theWorld;
        Block block = Block.blocksList[world.getBlockId(i, j, k)];
        if(block == null)
        {
            return false;
        }
        world.playAuxSFX(2001, i, j, k, block.blockID + world.getBlockMetadata(i, j, k) * 256);
        int i1 = world.getBlockMetadata(i, j, k);
        boolean flag = world.setBlockWithNotify(i, j, k, 0);
        if(block != null && flag)
        {
            block.onBlockDestroyedByPlayer(world, i, j, k, i1);
        }
        return flag;
    }

    public abstract void sendBlockRemoving(int i, int j, int k, int l);

    public abstract void resetBlockRemoving();

    public void setPartialTime(float f)
    {
    }

    public abstract float getBlockReachDistance();

    public boolean sendUseItem(EntityPlayer entityplayer, World world, ItemStack itemstack)
    {
        int i = itemstack.stackSize;
        ItemStack itemstack1 = itemstack.useItemRightClick(world, entityplayer);
        if(itemstack1 != itemstack || itemstack1 != null && itemstack1.stackSize != i)
        {
            entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = itemstack1;
            if(itemstack1.stackSize == 0)
            {
                entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
            }
            return true;
        } else
        {
            return false;
        }
    }

    public void flipPlayer(EntityPlayer entityplayer)
    {
    }

    public void updateController()
    {
    }

    public abstract boolean shouldDrawHUD();

    public void func_6473_b(EntityPlayer entityplayer)
    {
        PlayerControllerCreative.func_35645_e(entityplayer);
    }

    public abstract boolean sendPlaceBlock(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l);

    public EntityPlayer createPlayer(World world)
    {
        return new EntityPlayerSP(mc, world, mc.session, world.worldProvider.worldType);
    }

    public void interactWithEntity(EntityPlayer entityplayer, Entity entity)
    {
        entityplayer.useCurrentItemOnEntity(entity);
    }

    public void attackEntity(EntityPlayer entityplayer, Entity entity)
    {
        entityplayer.attackTargetEntityWithCurrentItem(entity);
    }

    public ItemStack windowClick(int i, int j, int k, boolean flag, EntityPlayer entityplayer)
    {
        return entityplayer.craftingInventory.slotClick(j, k, flag, entityplayer);
    }

    public void func_20086_a(int i, EntityPlayer entityplayer)
    {
        entityplayer.craftingInventory.onCraftGuiClosed(entityplayer);
        entityplayer.craftingInventory = entityplayer.inventorySlots;
    }

    public void func_40593_a(int i, int j)
    {
    }

    public boolean func_35643_e()
    {
        return false;
    }

    public void onStoppedUsingItem(EntityPlayer entityplayer)
    {
        entityplayer.stopUsingItem();
    }

    public boolean func_35642_f()
    {
        return false;
    }

    public boolean func_35641_g()
    {
        return true;
    }

    public boolean isInCreativeMode()
    {
        return false;
    }

    public boolean extendedReach()
    {
        return false;
    }

    public void func_35637_a(ItemStack itemstack, int i)
    {
    }

    public void func_35639_a(ItemStack itemstack)
    {
    }
}
