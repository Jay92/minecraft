// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;

// Referenced classes of package net.minecraft.src:
//            WorldChunkManager, BiomeGenBase, ChunkPosition, ChunkCoordIntPair

public class WorldChunkManagerHell extends WorldChunkManager
{

    private BiomeGenBase field_4201_e;
    private float hellTemperature;
    private float field_4199_g;

    public WorldChunkManagerHell(BiomeGenBase biomegenbase, float f, float f1)
    {
        field_4201_e = biomegenbase;
        hellTemperature = f;
        field_4199_g = f1;
    }

    public BiomeGenBase getBiomeGenAtChunkCoord(ChunkCoordIntPair chunkcoordintpair)
    {
        return field_4201_e;
    }

    public BiomeGenBase getBiomeGenAt(int i, int j)
    {
        return field_4201_e;
    }

    public BiomeGenBase[] func_4069_a(int i, int j, int k, int l)
    {
        field_4195_d = loadBlockGeneratorData(field_4195_d, i, j, k, l);
        return field_4195_d;
    }

    public float[] getTemperatures(float af[], int i, int j, int k, int l)
    {
        if(af == null || af.length < k * l)
        {
            af = new float[k * l];
        }
        Arrays.fill(af, 0, k * l, hellTemperature);
        return af;
    }

    public float[] func_40539_b(int i, int j, int k, int l)
    {
        return getTemperatures(new float[k * l], i, j, k, l);
    }

    public float[] getRainfall(float af[], int i, int j, int k, int l)
    {
        if(af == null || af.length < k * l)
        {
            af = new float[k * l];
        }
        Arrays.fill(af, 0, k * l, field_4199_g);
        return af;
    }

    public float func_35558_c(int i, int j)
    {
        return field_4199_g;
    }

    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase abiomegenbase[], int i, int j, int k, int l)
    {
        if(abiomegenbase == null || abiomegenbase.length < k * l)
        {
            abiomegenbase = new BiomeGenBase[k * l];
        }
        Arrays.fill(abiomegenbase, 0, k * l, field_4201_e);
        return abiomegenbase;
    }

    public BiomeGenBase[] func_35555_a(BiomeGenBase abiomegenbase[], int i, int j, int k, int l, boolean flag)
    {
        return loadBlockGeneratorData(abiomegenbase, i, j, k, l);
    }

    public ChunkPosition func_35556_a(int i, int j, int k, List list, Random random)
    {
        if(list.contains(field_4201_e))
        {
            return new ChunkPosition((i - k) + random.nextInt(k * 2 + 1), 0, (j - k) + random.nextInt(k * 2 + 1));
        } else
        {
            return null;
        }
    }

    public boolean areBiomesViable(int i, int j, int k, List list)
    {
        return list.contains(field_4201_e);
    }
}
