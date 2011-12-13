// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, Material, IBlockAccess, WorldChunkManager, 
//            BiomeGenBase, World, Vec3D, AxisAlignedBB, 
//            Entity

public abstract class BlockFluid extends Block
{

    protected BlockFluid(int i, Material material)
    {
        super(i, (material != Material.lava ? 12 : 14) * 16 + 13, material);
        float f = 0.0F;
        float f1 = 0.0F;
        setBlockBounds(0.0F + f1, 0.0F + f, 0.0F + f1, 1.0F + f1, 1.0F + f, 1.0F + f1);
        setTickOnLoad(true);
    }

    public int getBlockColor()
    {
        return 0xffffff;
    }

    public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k)
    {
        if(blockMaterial == Material.water)
        {
            BiomeGenBase biomegenbase = iblockaccess.getWorldChunkManager().getBiomeGenAt(i, k);
            return biomegenbase.field_40256_A;
        } else
        {
            return 0xffffff;
        }
    }

    public static float getFluidHeightPercent(int i)
    {
        if(i >= 8)
        {
            i = 0;
        }
        float f = (float)(i + 1) / 9F;
        return f;
    }

    public int getBlockTextureFromSide(int i)
    {
        if(i == 0 || i == 1)
        {
            return blockIndexInTexture;
        } else
        {
            return blockIndexInTexture + 1;
        }
    }

    protected int getFlowDecay(World world, int i, int j, int k)
    {
        if(world.getBlockMaterial(i, j, k) != blockMaterial)
        {
            return -1;
        } else
        {
            return world.getBlockMetadata(i, j, k);
        }
    }

    protected int getEffectiveFlowDecay(IBlockAccess iblockaccess, int i, int j, int k)
    {
        if(iblockaccess.getBlockMaterial(i, j, k) != blockMaterial)
        {
            return -1;
        }
        int l = iblockaccess.getBlockMetadata(i, j, k);
        if(l >= 8)
        {
            l = 0;
        }
        return l;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean canCollideCheck(int i, boolean flag)
    {
        return flag && i == 0;
    }

    public boolean getIsBlockSolid(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        Material material = iblockaccess.getBlockMaterial(i, j, k);
        if(material == blockMaterial)
        {
            return false;
        }
        if(l == 1)
        {
            return true;
        }
        if(material == Material.ice)
        {
            return false;
        } else
        {
            return super.getIsBlockSolid(iblockaccess, i, j, k, l);
        }
    }

    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        Material material = iblockaccess.getBlockMaterial(i, j, k);
        if(material == blockMaterial)
        {
            return false;
        }
        if(l == 1)
        {
            return true;
        }
        if(material == Material.ice)
        {
            return false;
        } else
        {
            return super.shouldSideBeRendered(iblockaccess, i, j, k, l);
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public int getRenderType()
    {
        return 4;
    }

    public int idDropped(int i, Random random, int j)
    {
        return 0;
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    private Vec3D getFlowVector(IBlockAccess iblockaccess, int i, int j, int k)
    {
        Vec3D vec3d = Vec3D.createVector(0.0D, 0.0D, 0.0D);
        int l = getEffectiveFlowDecay(iblockaccess, i, j, k);
        for(int i1 = 0; i1 < 4; i1++)
        {
            int j1 = i;
            int k1 = j;
            int l1 = k;
            if(i1 == 0)
            {
                j1--;
            }
            if(i1 == 1)
            {
                l1--;
            }
            if(i1 == 2)
            {
                j1++;
            }
            if(i1 == 3)
            {
                l1++;
            }
            int i2 = getEffectiveFlowDecay(iblockaccess, j1, k1, l1);
            if(i2 < 0)
            {
                if(iblockaccess.getBlockMaterial(j1, k1, l1).getIsSolid())
                {
                    continue;
                }
                i2 = getEffectiveFlowDecay(iblockaccess, j1, k1 - 1, l1);
                if(i2 >= 0)
                {
                    int j2 = i2 - (l - 8);
                    vec3d = vec3d.addVector((j1 - i) * j2, (k1 - j) * j2, (l1 - k) * j2);
                }
                continue;
            }
            if(i2 >= 0)
            {
                int k2 = i2 - l;
                vec3d = vec3d.addVector((j1 - i) * k2, (k1 - j) * k2, (l1 - k) * k2);
            }
        }

        if(iblockaccess.getBlockMetadata(i, j, k) >= 8)
        {
            boolean flag = false;
            if(flag || getIsBlockSolid(iblockaccess, i, j, k - 1, 2))
            {
                flag = true;
            }
            if(flag || getIsBlockSolid(iblockaccess, i, j, k + 1, 3))
            {
                flag = true;
            }
            if(flag || getIsBlockSolid(iblockaccess, i - 1, j, k, 4))
            {
                flag = true;
            }
            if(flag || getIsBlockSolid(iblockaccess, i + 1, j, k, 5))
            {
                flag = true;
            }
            if(flag || getIsBlockSolid(iblockaccess, i, j + 1, k - 1, 2))
            {
                flag = true;
            }
            if(flag || getIsBlockSolid(iblockaccess, i, j + 1, k + 1, 3))
            {
                flag = true;
            }
            if(flag || getIsBlockSolid(iblockaccess, i - 1, j + 1, k, 4))
            {
                flag = true;
            }
            if(flag || getIsBlockSolid(iblockaccess, i + 1, j + 1, k, 5))
            {
                flag = true;
            }
            if(flag)
            {
                vec3d = vec3d.normalize().addVector(0.0D, -6D, 0.0D);
            }
        }
        vec3d = vec3d.normalize();
        return vec3d;
    }

    public void velocityToAddToEntity(World world, int i, int j, int k, Entity entity, Vec3D vec3d)
    {
        Vec3D vec3d1 = getFlowVector(world, i, j, k);
        vec3d.xCoord += vec3d1.xCoord;
        vec3d.yCoord += vec3d1.yCoord;
        vec3d.zCoord += vec3d1.zCoord;
    }

    public int tickRate()
    {
        if(blockMaterial == Material.water)
        {
            return 5;
        }
        return blockMaterial != Material.lava ? 0 : 30;
    }

    public int getMixedBrightnessForBlock(IBlockAccess iblockaccess, int i, int j, int k)
    {
        int l = iblockaccess.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int i1 = iblockaccess.getLightBrightnessForSkyBlocks(i, j + 1, k, 0);
        int j1 = l & 0xff;
        int k1 = i1 & 0xff;
        int l1 = l >> 16 & 0xff;
        int i2 = i1 >> 16 & 0xff;
        return (j1 <= k1 ? k1 : j1) | (l1 <= i2 ? i2 : l1) << 16;
    }

    public float getBlockBrightness(IBlockAccess iblockaccess, int i, int j, int k)
    {
        float f = iblockaccess.getLightBrightness(i, j, k);
        float f1 = iblockaccess.getLightBrightness(i, j + 1, k);
        return f <= f1 ? f1 : f;
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        super.updateTick(world, i, j, k, random);
    }

    public int getRenderBlockPass()
    {
        return blockMaterial != Material.water ? 0 : 1;
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if(blockMaterial == Material.water)
        {
            if(random.nextInt(10) == 0)
            {
                int l = world.getBlockMetadata(i, j, k);
                if(l <= 0 || l >= 8)
                {
                    world.spawnParticle("suspended", (float)i + random.nextFloat(), (float)j + random.nextFloat(), (float)k + random.nextFloat(), 0.0D, 0.0D, 0.0D);
                }
            }
            for(int i1 = 0; i1 < 0; i1++)
            {
                int k1 = random.nextInt(4);
                int l1 = i;
                int i2 = k;
                if(k1 == 0)
                {
                    l1--;
                }
                if(k1 == 1)
                {
                    l1++;
                }
                if(k1 == 2)
                {
                    i2--;
                }
                if(k1 == 3)
                {
                    i2++;
                }
                if(world.getBlockMaterial(l1, j, i2) != Material.air || !world.getBlockMaterial(l1, j - 1, i2).getIsSolid() && !world.getBlockMaterial(l1, j - 1, i2).getIsLiquid())
                {
                    continue;
                }
                float f = 0.0625F;
                double d6 = (float)i + random.nextFloat();
                double d7 = (float)j + random.nextFloat();
                double d8 = (float)k + random.nextFloat();
                if(k1 == 0)
                {
                    d6 = (float)i - f;
                }
                if(k1 == 1)
                {
                    d6 = (float)(i + 1) + f;
                }
                if(k1 == 2)
                {
                    d8 = (float)k - f;
                }
                if(k1 == 3)
                {
                    d8 = (float)(k + 1) + f;
                }
                double d9 = 0.0D;
                double d10 = 0.0D;
                if(k1 == 0)
                {
                    d9 = -f;
                }
                if(k1 == 1)
                {
                    d9 = f;
                }
                if(k1 == 2)
                {
                    d10 = -f;
                }
                if(k1 == 3)
                {
                    d10 = f;
                }
                world.spawnParticle("splash", d6, d7, d8, d9, 0.0D, d10);
            }

        }
        if(blockMaterial == Material.water && random.nextInt(64) == 0)
        {
            int j1 = world.getBlockMetadata(i, j, k);
            if(j1 > 0 && j1 < 8)
            {
                world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "liquid.water", random.nextFloat() * 0.25F + 0.75F, random.nextFloat() * 1.0F + 0.5F);
            }
        }
        if(blockMaterial == Material.lava && world.getBlockMaterial(i, j + 1, k) == Material.air && !world.isBlockOpaqueCube(i, j + 1, k) && random.nextInt(100) == 0)
        {
            double d = (float)i + random.nextFloat();
            double d2 = (double)j + maxY;
            double d4 = (float)k + random.nextFloat();
            world.spawnParticle("lava", d, d2, d4, 0.0D, 0.0D, 0.0D);
        }
        if(random.nextInt(10) == 0 && world.isBlockNormalCube(i, j - 1, k) && !world.getBlockMaterial(i, j - 2, k).getIsSolid())
        {
            double d1 = (float)i + random.nextFloat();
            double d3 = (double)j - 1.05D;
            double d5 = (float)k + random.nextFloat();
            if(blockMaterial == Material.water)
            {
                world.spawnParticle("dripWater", d1, d3, d5, 0.0D, 0.0D, 0.0D);
            } else
            {
                world.spawnParticle("dripLava", d1, d3, d5, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public static double func_293_a(IBlockAccess iblockaccess, int i, int j, int k, Material material)
    {
        Vec3D vec3d = null;
        if(material == Material.water)
        {
            vec3d = ((BlockFluid)Block.waterMoving).getFlowVector(iblockaccess, i, j, k);
        }
        if(material == Material.lava)
        {
            vec3d = ((BlockFluid)Block.lavaMoving).getFlowVector(iblockaccess, i, j, k);
        }
        if(vec3d.xCoord == 0.0D && vec3d.zCoord == 0.0D)
        {
            return -1000D;
        } else
        {
            return Math.atan2(vec3d.zCoord, vec3d.xCoord) - 1.5707963267948966D;
        }
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        checkForHarden(world, i, j, k);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        checkForHarden(world, i, j, k);
    }

    private void checkForHarden(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j, k) != blockID)
        {
            return;
        }
        if(blockMaterial == Material.lava)
        {
            boolean flag = false;
            if(flag || world.getBlockMaterial(i, j, k - 1) == Material.water)
            {
                flag = true;
            }
            if(flag || world.getBlockMaterial(i, j, k + 1) == Material.water)
            {
                flag = true;
            }
            if(flag || world.getBlockMaterial(i - 1, j, k) == Material.water)
            {
                flag = true;
            }
            if(flag || world.getBlockMaterial(i + 1, j, k) == Material.water)
            {
                flag = true;
            }
            if(flag || world.getBlockMaterial(i, j + 1, k) == Material.water)
            {
                flag = true;
            }
            if(flag)
            {
                int l = world.getBlockMetadata(i, j, k);
                if(l == 0)
                {
                    world.setBlockWithNotify(i, j, k, Block.obsidian.blockID);
                } else
                if(l <= 4)
                {
                    world.setBlockWithNotify(i, j, k, Block.cobblestone.blockID);
                }
                triggerLavaMixEffects(world, i, j, k);
            }
        }
    }

    protected void triggerLavaMixEffects(World world, int i, int j, int k)
    {
        world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
        for(int l = 0; l < 8; l++)
        {
            world.spawnParticle("largesmoke", (double)i + Math.random(), (double)j + 1.2D, (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
        }

    }
}
