// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package net.minecraft.src:
//            LongHashMap, BiomeCacheBlock, WorldChunkManager, BiomeGenBase

public class BiomeCache
{

    private final WorldChunkManager chunkmanager;
    private long field_35729_b;
    private LongHashMap field_35730_c;
    private List field_35728_d;

    public BiomeCache(WorldChunkManager worldchunkmanager)
    {
        field_35729_b = 0L;
        field_35730_c = new LongHashMap();
        field_35728_d = new ArrayList();
        chunkmanager = worldchunkmanager;
    }

    public BiomeCacheBlock getBiomeCacheBlock(int i, int j)
    {
        i >>= 4;
        j >>= 4;
        long l = (long)i & 0xffffffffL | ((long)j & 0xffffffffL) << 32;
        BiomeCacheBlock biomecacheblock = (BiomeCacheBlock)field_35730_c.getValueByKey(l);
        if(biomecacheblock == null)
        {
            biomecacheblock = new BiomeCacheBlock(this, i, j);
            field_35730_c.add(l, biomecacheblock);
            field_35728_d.add(biomecacheblock);
        }
        biomecacheblock.field_35653_f = System.currentTimeMillis();
        return biomecacheblock;
    }

    public BiomeGenBase func_35725_a(int i, int j)
    {
        return getBiomeCacheBlock(i, j).func_35651_a(i, j);
    }

    public float func_35722_b(int i, int j)
    {
        return getBiomeCacheBlock(i, j).func_35650_b(i, j);
    }

    public float func_35727_c(int i, int j)
    {
        return getBiomeCacheBlock(i, j).func_35652_c(i, j);
    }

    public void func_35724_a()
    {
        long l = System.currentTimeMillis();
        long l1 = l - field_35729_b;
        if(l1 > 7500L || l1 < 0L)
        {
            field_35729_b = l;
            for(int i = 0; i < field_35728_d.size(); i++)
            {
                BiomeCacheBlock biomecacheblock = (BiomeCacheBlock)field_35728_d.get(i);
                long l2 = l - biomecacheblock.field_35653_f;
                if(l2 > 30000L || l2 < 0L)
                {
                    field_35728_d.remove(i--);
                    long l3 = (long)biomecacheblock.field_35655_d & 0xffffffffL | ((long)biomecacheblock.field_35656_e & 0xffffffffL) << 32;
                    field_35730_c.remove(l3);
                }
            }

        }
    }

    public BiomeGenBase[] func_35723_d(int i, int j)
    {
        return getBiomeCacheBlock(i, j).field_35658_c;
    }

    static WorldChunkManager getWorldChunkManager(BiomeCache biomecache)
    {
        return biomecache.chunkmanager;
    }
}
