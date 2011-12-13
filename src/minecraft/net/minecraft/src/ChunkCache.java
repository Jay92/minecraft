// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            IBlockAccess, World, Chunk, WorldProvider, 
//            EnumSkyBlock, Block, Material, TileEntity, 
//            WorldChunkManager

public class ChunkCache
    implements IBlockAccess
{

    private int chunkX;
    private int chunkZ;
    private Chunk chunkArray[][];
    private World worldObj;

    public ChunkCache(World world, int i, int j, int k, int l, int i1, int j1)
    {
        worldObj = world;
        chunkX = i >> 4;
        chunkZ = k >> 4;
        int k1 = l >> 4;
        int l1 = j1 >> 4;
        chunkArray = new Chunk[(k1 - chunkX) + 1][(l1 - chunkZ) + 1];
        for(int i2 = chunkX; i2 <= k1; i2++)
        {
            for(int j2 = chunkZ; j2 <= l1; j2++)
            {
                chunkArray[i2 - chunkX][j2 - chunkZ] = world.getChunkFromChunkCoords(i2, j2);
            }

        }

    }

    public int getBlockId(int i, int j, int k)
    {
        if(j < 0)
        {
            return 0;
        }
        if(j >= worldObj.field_35472_c)
        {
            return 0;
        }
        int l = (i >> 4) - chunkX;
        int i1 = (k >> 4) - chunkZ;
        if(l < 0 || l >= chunkArray.length || i1 < 0 || i1 >= chunkArray[l].length)
        {
            return 0;
        }
        Chunk chunk = chunkArray[l][i1];
        if(chunk == null)
        {
            return 0;
        } else
        {
            return chunk.getBlockID(i & 0xf, j, k & 0xf);
        }
    }

    public TileEntity getBlockTileEntity(int i, int j, int k)
    {
        int l = (i >> 4) - chunkX;
        int i1 = (k >> 4) - chunkZ;
        return chunkArray[l][i1].getChunkBlockTileEntity(i & 0xf, j, k & 0xf);
    }

    public float getBrightness(int i, int j, int k, int l)
    {
        int i1 = getLightValue(i, j, k);
        if(i1 < l)
        {
            i1 = l;
        }
        return worldObj.worldProvider.lightBrightnessTable[i1];
    }

    public int getLightBrightnessForSkyBlocks(int i, int j, int k, int l)
    {
        int i1 = getSkyBlockTypeBrightness(EnumSkyBlock.Sky, i, j, k);
        int j1 = getSkyBlockTypeBrightness(EnumSkyBlock.Block, i, j, k);
        if(j1 < l)
        {
            j1 = l;
        }
        return i1 << 20 | j1 << 4;
    }

    public float getLightBrightness(int i, int j, int k)
    {
        return worldObj.worldProvider.lightBrightnessTable[getLightValue(i, j, k)];
    }

    public int getLightValue(int i, int j, int k)
    {
        return getLightValueExt(i, j, k, true);
    }

    public int getLightValueExt(int i, int j, int k, boolean flag)
    {
        if(i < 0xfe363c80 || k < 0xfe363c80 || i >= 0x1c9c380 || k > 0x1c9c380)
        {
            return 15;
        }
        if(flag)
        {
            int l = getBlockId(i, j, k);
            if(l == Block.stairSingle.blockID || l == Block.tilledField.blockID || l == Block.stairCompactPlanks.blockID || l == Block.stairCompactCobblestone.blockID)
            {
                int k1 = getLightValueExt(i, j + 1, k, false);
                int i2 = getLightValueExt(i + 1, j, k, false);
                int j2 = getLightValueExt(i - 1, j, k, false);
                int k2 = getLightValueExt(i, j, k + 1, false);
                int l2 = getLightValueExt(i, j, k - 1, false);
                if(i2 > k1)
                {
                    k1 = i2;
                }
                if(j2 > k1)
                {
                    k1 = j2;
                }
                if(k2 > k1)
                {
                    k1 = k2;
                }
                if(l2 > k1)
                {
                    k1 = l2;
                }
                return k1;
            }
        }
        if(j < 0)
        {
            return 0;
        }
        if(j >= worldObj.field_35472_c)
        {
            int i1 = 15 - worldObj.skylightSubtracted;
            if(i1 < 0)
            {
                i1 = 0;
            }
            return i1;
        } else
        {
            int j1 = (i >> 4) - chunkX;
            int l1 = (k >> 4) - chunkZ;
            return chunkArray[j1][l1].getBlockLightValue(i & 0xf, j, k & 0xf, worldObj.skylightSubtracted);
        }
    }

    public int getBlockMetadata(int i, int j, int k)
    {
        if(j < 0)
        {
            return 0;
        }
        if(j >= worldObj.field_35472_c)
        {
            return 0;
        } else
        {
            int l = (i >> 4) - chunkX;
            int i1 = (k >> 4) - chunkZ;
            return chunkArray[l][i1].getBlockMetadata(i & 0xf, j, k & 0xf);
        }
    }

    public Material getBlockMaterial(int i, int j, int k)
    {
        int l = getBlockId(i, j, k);
        if(l == 0)
        {
            return Material.air;
        } else
        {
            return Block.blocksList[l].blockMaterial;
        }
    }

    public WorldChunkManager getWorldChunkManager()
    {
        return worldObj.getWorldChunkManager();
    }

    public boolean isBlockOpaqueCube(int i, int j, int k)
    {
        Block block = Block.blocksList[getBlockId(i, j, k)];
        if(block == null)
        {
            return false;
        } else
        {
            return block.isOpaqueCube();
        }
    }

    public boolean isBlockNormalCube(int i, int j, int k)
    {
        Block block = Block.blocksList[getBlockId(i, j, k)];
        if(block == null)
        {
            return false;
        } else
        {
            return block.blockMaterial.getIsSolid() && block.renderAsNormalBlock();
        }
    }

    public boolean isAirBlock(int i, int j, int k)
    {
        Block block = Block.blocksList[getBlockId(i, j, k)];
        return block == null;
    }

    public int getSkyBlockTypeBrightness(EnumSkyBlock enumskyblock, int i, int j, int k)
    {
        if(j < 0)
        {
            j = 0;
        }
        if(j >= worldObj.field_35472_c)
        {
            j = worldObj.field_35472_c - 1;
        }
        if(j < 0 || j >= worldObj.field_35472_c || i < 0xfe363c80 || k < 0xfe363c80 || i >= 0x1c9c380 || k > 0x1c9c380)
        {
            return enumskyblock.defaultLightValue;
        }
        if(Block.useNeighborBrightness[getBlockId(i, j, k)])
        {
            int l = getSpecialBlockBrightness(enumskyblock, i, j + 1, k);
            int j1 = getSpecialBlockBrightness(enumskyblock, i + 1, j, k);
            int l1 = getSpecialBlockBrightness(enumskyblock, i - 1, j, k);
            int i2 = getSpecialBlockBrightness(enumskyblock, i, j, k + 1);
            int j2 = getSpecialBlockBrightness(enumskyblock, i, j, k - 1);
            if(j1 > l)
            {
                l = j1;
            }
            if(l1 > l)
            {
                l = l1;
            }
            if(i2 > l)
            {
                l = i2;
            }
            if(j2 > l)
            {
                l = j2;
            }
            return l;
        } else
        {
            int i1 = (i >> 4) - chunkX;
            int k1 = (k >> 4) - chunkZ;
            return chunkArray[i1][k1].getSavedLightValue(enumskyblock, i & 0xf, j, k & 0xf);
        }
    }

    public int getSpecialBlockBrightness(EnumSkyBlock enumskyblock, int i, int j, int k)
    {
        if(j < 0)
        {
            j = 0;
        }
        if(j >= worldObj.field_35472_c)
        {
            j = worldObj.field_35472_c - 1;
        }
        if(j < 0 || j >= worldObj.field_35472_c || i < 0xfe363c80 || k < 0xfe363c80 || i >= 0x1c9c380 || k > 0x1c9c380)
        {
            return enumskyblock.defaultLightValue;
        } else
        {
            int l = (i >> 4) - chunkX;
            int i1 = (k >> 4) - chunkZ;
            return chunkArray[l][i1].getSavedLightValue(enumskyblock, i & 0xf, j, k & 0xf);
        }
    }

    public int func_35452_b()
    {
        return worldObj.field_35472_c;
    }
}
