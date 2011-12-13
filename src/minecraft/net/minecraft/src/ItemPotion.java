// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;

// Referenced classes of package net.minecraft.src:
//            Item, ItemStack, PotionHelper, World, 
//            PotionEffect, EntityPlayer, InventoryPlayer, EnumAction, 
//            EntityPotion, Potion, StatCollector

public class ItemPotion extends Item
{

    private HashMap field_40435_a;

    public ItemPotion(int i)
    {
        super(i);
        field_40435_a = new HashMap();
        setMaxStackSize(1);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    public List func_40434_a_(ItemStack itemstack)
    {
        return func_40431_c_(itemstack.getItemDamage());
    }

    public List func_40431_c_(int i)
    {
        List list = (List)field_40435_a.get(Integer.valueOf(i));
        if(list == null)
        {
            list = PotionHelper.func_40360_b(i, false);
            field_40435_a.put(Integer.valueOf(i), list);
        }
        return list;
    }

    public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.stackSize--;
        if(!world.multiplayerWorld)
        {
            List list = func_40434_a_(itemstack);
            if(list != null)
            {
                PotionEffect potioneffect;
                for(Iterator iterator = list.iterator(); iterator.hasNext(); entityplayer.addPotionEffect(new PotionEffect(potioneffect)))
                {
                    potioneffect = (PotionEffect)iterator.next();
                }

            }
        }
        if(itemstack.stackSize <= 0)
        {
            return new ItemStack(Item.glassBottle);
        } else
        {
            entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            return itemstack;
        }
    }

    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 32;
    }

    public EnumAction getItemUseAction(ItemStack itemstack)
    {
        return EnumAction.drink;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if(func_40433_c(itemstack.getItemDamage()))
        {
            itemstack.stackSize--;
            world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if(!world.multiplayerWorld)
            {
                world.entityJoinedWorld(new EntityPotion(world, entityplayer, itemstack.getItemDamage()));
            }
            return itemstack;
        } else
        {
            entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
            return itemstack;
        }
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        return false;
    }

    public int getIconFromDamage(int i)
    {
        return !func_40433_c(i) ? 140 : 154;
    }

    public static boolean func_40433_c(int i)
    {
        return (i & 0x4000) != 0;
    }

    public int getColorFromDamage(int i)
    {
        return PotionHelper.func_40358_a(i, false);
    }

    public boolean func_40432_e(int i)
    {
        List list = func_40431_c_(i);
        if(list == null || list.isEmpty())
        {
            return false;
        }
        for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            if(Potion.potionTypes[potioneffect.getPotionID()].func_40622_b())
            {
                return true;
            }
        }

        return false;
    }

    public String func_40397_d(ItemStack itemstack)
    {
        if(itemstack.getItemDamage() == 0)
        {
            return StatCollector.translateToLocal("item.emptyPotion.name").trim();
        }
        String s = super.func_40397_d(itemstack);
        if(func_40433_c(itemstack.getItemDamage()))
        {
            s = (new StringBuilder()).append(StatCollector.translateToLocal("potion.prefix.grenade").trim()).append(" ").append(s).toString();
        }
        List list = Item.potion.func_40434_a_(itemstack);
        if(list != null && !list.isEmpty())
        {
            String s1 = ((PotionEffect)list.get(0)).func_40468_d();
            s1 = (new StringBuilder()).append(s1).append(".postfix").toString();
            return (new StringBuilder()).append(s).append(" ").append(StatCollector.translateToLocal(s1).trim()).toString();
        } else
        {
            String s2 = PotionHelper.func_40359_b(itemstack.getItemDamage());
            return (new StringBuilder()).append(StatCollector.translateToLocal(s2).trim()).append(" ").append(s).toString();
        }
    }

    public void func_40404_a(ItemStack itemstack, List list)
    {
        if(itemstack.getItemDamage() == 0)
        {
            return;
        }
        List list1 = Item.potion.func_40434_a_(itemstack);
        if(list1 != null && !list1.isEmpty())
        {
            for(Iterator iterator = list1.iterator(); iterator.hasNext();)
            {
                PotionEffect potioneffect = (PotionEffect)iterator.next();
                String s1 = StatCollector.translateToLocal(potioneffect.func_40468_d()).trim();
                if(potioneffect.getAmplifier() > 0)
                {
                    s1 = (new StringBuilder()).append(s1).append(" ").append(StatCollector.translateToLocal((new StringBuilder()).append("potion.potency.").append(potioneffect.getAmplifier()).toString()).trim()).toString();
                }
                if(potioneffect.getDuration() > 20)
                {
                    s1 = (new StringBuilder()).append(s1).append(" (").append(Potion.func_40620_a(potioneffect)).append(")").toString();
                }
                if(Potion.potionTypes[potioneffect.getPotionID()].func_40615_f())
                {
                    list.add((new StringBuilder()).append("\247c").append(s1).toString());
                } else
                {
                    list.add((new StringBuilder()).append("\2477").append(s1).toString());
                }
            }

        } else
        {
            String s = StatCollector.translateToLocal("potion.empty").trim();
            list.add((new StringBuilder()).append("\2477").append(s).toString());
        }
    }

    public boolean func_40403_e(ItemStack itemstack)
    {
        List list = func_40434_a_(itemstack);
        return list != null && !list.isEmpty();
    }
}
