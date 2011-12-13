// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package net.minecraft.src:
//            Container, Block, ItemStack, Item, 
//            ItemPotion, EntityPlayer, Slot, GuiContainerCreative, 
//            InventoryBasic

class ContainerCreative extends Container
{

    public List itemList;

    public ContainerCreative(EntityPlayer entityplayer)
    {
        itemList = new ArrayList();
        Block ablock[] = {
            Block.cobblestone, Block.stone, Block.oreDiamond, Block.oreGold, Block.oreIron, Block.oreCoal, Block.oreLapis, Block.oreRedstone, Block.stoneBrick, Block.stoneBrick, 
            Block.stoneBrick, Block.blockClay, Block.blockDiamond, Block.blockGold, Block.blockSteel, Block.bedrock, Block.blockLapis, Block.brick, Block.cobblestoneMossy, Block.stairSingle, 
            Block.stairSingle, Block.stairSingle, Block.stairSingle, Block.stairSingle, Block.stairSingle, Block.obsidian, Block.netherrack, Block.slowSand, Block.glowStone, Block.wood, 
            Block.wood, Block.wood, Block.leaves, Block.leaves, Block.leaves, Block.dirt, Block.grass, Block.sand, Block.sandStone, Block.gravel, 
            Block.web, Block.planks, Block.sapling, Block.sapling, Block.sapling, Block.deadBush, Block.sponge, Block.ice, Block.blockSnow, Block.plantYellow, 
            Block.plantRed, Block.mushroomBrown, Block.mushroomRed, Block.cactus, Block.melon, Block.pumpkin, Block.pumpkinLantern, Block.vine, Block.fenceIron, Block.thinGlass, 
            Block.netherBrick, Block.netherFence, Block.stairsNetherBrick, Block.whiteStone, Block.mycelium, Block.waterlily, Block.tallGrass, Block.tallGrass, Block.chest, Block.workbench, 
            Block.glass, Block.tnt, Block.bookShelf, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, 
            Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.dispenser, 
            Block.stoneOvenIdle, Block.music, Block.jukebox, Block.pistonStickyBase, Block.pistonBase, Block.fence, Block.fenceGate, Block.ladder, Block.rail, Block.railPowered, 
            Block.railDetector, Block.torchWood, Block.stairCompactPlanks, Block.stairCompactCobblestone, Block.stairsBrick, Block.stairsStoneBrickSmooth, Block.lever, Block.pressurePlateStone, Block.pressurePlatePlanks, Block.torchRedstoneActive, 
            Block.button, Block.trapdoor, Block.enchantmentTable
        };
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        int k1 = 1;
        for(int l1 = 0; l1 < ablock.length; l1++)
        {
            int k2 = 0;
            if(ablock[l1] == Block.cloth)
            {
                k2 = i++;
            } else
            if(ablock[l1] == Block.stairSingle)
            {
                k2 = j++;
            } else
            if(ablock[l1] == Block.wood)
            {
                k2 = k++;
            } else
            if(ablock[l1] == Block.sapling)
            {
                k2 = l++;
            } else
            if(ablock[l1] == Block.stoneBrick)
            {
                k2 = i1++;
            } else
            if(ablock[l1] == Block.tallGrass)
            {
                k2 = k1++;
            } else
            if(ablock[l1] == Block.leaves)
            {
                k2 = j1++;
            }
            itemList.add(new ItemStack(ablock[l1], 1, k2));
        }

        for(int i2 = 256; i2 < Item.itemsList.length; i2++)
        {
            if(Item.itemsList[i2] != null && Item.itemsList[i2].shiftedIndex != Item.potion.shiftedIndex)
            {
                itemList.add(new ItemStack(Item.itemsList[i2]));
            }
        }

        for(int j2 = 1; j2 < 16; j2++)
        {
            itemList.add(new ItemStack(Item.dyePowder.shiftedIndex, 1, j2));
        }

        InventoryPlayer inventoryplayer = entityplayer.inventory;
        for(int l2 = 0; l2 < 9; l2++)
        {
            for(int j3 = 0; j3 < 8; j3++)
            {
                addSlot(new Slot(GuiContainerCreative.getInventory(), j3 + l2 * 8, 8 + j3 * 18, 18 + l2 * 18));
            }

        }

        for(int i3 = 0; i3 < 9; i3++)
        {
            addSlot(new Slot(inventoryplayer, i3, 8 + i3 * 18, 184));
        }

        func_35374_a(0.0F);
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    public void func_35374_a(float f)
    {
        int i = (itemList.size() / 8 - 8) + 1;
        int j = (int)((double)(f * (float)i) + 0.5D);
        if(j < 0)
        {
            j = 0;
        }
        for(int k = 0; k < 9; k++)
        {
            for(int l = 0; l < 8; l++)
            {
                int i1 = l + (k + j) * 8;
                if(i1 >= 0 && i1 < itemList.size())
                {
                    GuiContainerCreative.getInventory().setInventorySlotContents(l + k * 8, (ItemStack)itemList.get(i1));
                } else
                {
                    GuiContainerCreative.getInventory().setInventorySlotContents(l + k * 8, null);
                }
            }

        }

    }

    protected void func_35373_b(int i, int j, boolean flag, EntityPlayer entityplayer)
    {
    }
}
