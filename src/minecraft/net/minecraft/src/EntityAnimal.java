// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            EntityCreature, DataWatcher, World, EntityPlayer, 
//            Entity, Block, BlockGrass, NBTTagCompound, 
//            AxisAlignedBB, MathHelper, ItemStack, Item, 
//            InventoryPlayer, DamageSource

public abstract class EntityAnimal extends EntityCreature
{

    private int field_39004_a;
    private int field_39005_b;

    public EntityAnimal(World world)
    {
        super(world);
        field_39005_b = 0;
    }

    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(12, new Integer(0));
    }

    public int func_40146_g()
    {
        return dataWatcher.getWatchableObjectInt(12);
    }

    public void func_40142_a_(int i)
    {
        dataWatcher.updateObject(12, Integer.valueOf(i));
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        int i = func_40146_g();
        if(i < 0)
        {
            i++;
            func_40142_a_(i);
        } else
        if(i > 0)
        {
            i--;
            func_40142_a_(i);
        }
        if(field_39004_a > 0)
        {
            field_39004_a--;
            String s = "heart";
            if(field_39004_a % 10 == 0)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(s, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }
        } else
        {
            field_39005_b = 0;
        }
    }

    protected void attackEntity(Entity entity, float f)
    {
        if(entity instanceof EntityPlayer)
        {
            if(f < 3F)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                rotationYaw = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F;
                hasAttacked = true;
            }
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if(entityplayer.getCurrentEquippedItem() == null || !func_40143_a(entityplayer.getCurrentEquippedItem()))
            {
                entityToAttack = null;
            }
        } else
        if(entity instanceof EntityAnimal)
        {
            EntityAnimal entityanimal = (EntityAnimal)entity;
            if(func_40146_g() > 0 && entityanimal.func_40146_g() < 0)
            {
                if((double)f < 2.5D)
                {
                    hasAttacked = true;
                }
            } else
            if(field_39004_a > 0 && entityanimal.field_39004_a > 0)
            {
                if(entityanimal.entityToAttack == null)
                {
                    entityanimal.entityToAttack = this;
                }
                if(entityanimal.entityToAttack == this && (double)f < 3.5D)
                {
                    entityanimal.field_39004_a++;
                    field_39004_a++;
                    field_39005_b++;
                    if(field_39005_b % 4 == 0)
                    {
                        worldObj.spawnParticle("heart", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, 0.0D, 0.0D, 0.0D);
                    }
                    if(field_39005_b == 60)
                    {
                        func_40144_b((EntityAnimal)entity);
                    }
                } else
                {
                    field_39005_b = 0;
                }
            } else
            {
                field_39005_b = 0;
            }
        }
    }

    private void func_40144_b(EntityAnimal entityanimal)
    {
        EntityAnimal entityanimal1 = func_40145_a(entityanimal);
        if(entityanimal1 != null)
        {
            func_40142_a_(6000);
            entityanimal.func_40142_a_(6000);
            field_39004_a = 0;
            field_39005_b = 0;
            entityToAttack = null;
            entityanimal.entityToAttack = null;
            entityanimal.field_39005_b = 0;
            entityanimal.field_39004_a = 0;
            entityanimal1.func_40142_a_(-24000);
            entityanimal1.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            for(int i = 0; i < 7; i++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle("heart", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }

            worldObj.entityJoinedWorld(entityanimal1);
        }
    }

    protected abstract EntityAnimal func_40145_a(EntityAnimal entityanimal);

    protected void attackBlockedEntity(Entity entity, float f)
    {
    }

    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        fleeingTick = 60;
        entityToAttack = null;
        field_39004_a = 0;
        return super.attackEntityFrom(damagesource, i);
    }

    protected float getBlockPathWeight(int i, int j, int k)
    {
        if(worldObj.getBlockId(i, j - 1, k) == Block.grass.blockID)
        {
            return 10F;
        } else
        {
            return worldObj.getLightBrightness(i, j, k) - 0.5F;
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("Age", func_40146_g());
        nbttagcompound.setInteger("InLove", field_39004_a);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        func_40142_a_(nbttagcompound.getInteger("Age"));
        field_39004_a = nbttagcompound.getInteger("InLove");
    }

    protected Entity findPlayerToAttack()
    {
        if(fleeingTick > 0)
        {
            return null;
        }
        float f = 8F;
        if(field_39004_a > 0)
        {
            List list = worldObj.getEntitiesWithinAABB(getClass(), boundingBox.addCoord(f, f, f));
            for(int i = 0; i < list.size(); i++)
            {
                EntityAnimal entityanimal = (EntityAnimal)list.get(i);
                if(entityanimal != this && entityanimal.field_39004_a > 0)
                {
                    return entityanimal;
                }
            }

        } else
        if(func_40146_g() == 0)
        {
            List list1 = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityPlayer.class, boundingBox.addCoord(f, f, f));
            for(int j = 0; j < list1.size(); j++)
            {
                EntityPlayer entityplayer = (EntityPlayer)list1.get(j);
                if(entityplayer.getCurrentEquippedItem() != null && func_40143_a(entityplayer.getCurrentEquippedItem()))
                {
                    return entityplayer;
                }
            }

        } else
        if(func_40146_g() > 0)
        {
            List list2 = worldObj.getEntitiesWithinAABB(getClass(), boundingBox.addCoord(f, f, f));
            for(int k = 0; k < list2.size(); k++)
            {
                EntityAnimal entityanimal1 = (EntityAnimal)list2.get(k);
                if(entityanimal1 != this && entityanimal1.func_40146_g() < 0)
                {
                    return entityanimal1;
                }
            }

        }
        return null;
    }

    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        return worldObj.getBlockId(i, j - 1, k) == Block.grass.blockID && worldObj.getFullBlockLightValue(i, j, k) > 8 && super.getCanSpawnHere();
    }

    public int getTalkInterval()
    {
        return 120;
    }

    protected boolean canDespawn()
    {
        return false;
    }

    protected int func_36001_a(EntityPlayer entityplayer)
    {
        return 1 + worldObj.rand.nextInt(3);
    }

    protected boolean func_40143_a(ItemStack itemstack)
    {
        return itemstack.itemID == Item.wheat.shiftedIndex;
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if(itemstack != null && func_40143_a(itemstack) && func_40146_g() == 0)
        {
            itemstack.stackSize--;
            if(itemstack.stackSize <= 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            field_39004_a = 600;
            entityToAttack = null;
            for(int i = 0; i < 7; i++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle("heart", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }

            return true;
        } else
        {
            return super.interact(entityplayer);
        }
    }

    public boolean func_40127_l()
    {
        return func_40146_g() < 0;
    }
}
