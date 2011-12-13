// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, BiomeCache, WorldChunkManager

public class BiomeCacheBlock
{

    public float field_35659_a[];
    public float field_35657_b[];
    public BiomeGenBase field_35658_c[];
    public int field_35655_d;
    public int field_35656_e;
    public long field_35653_f;
    final BiomeCache field_35654_g; /* synthetic field */

    public BiomeCacheBlock(BiomeCache biomecache, int i, int j)
    {
        field_35654_g = biomecache;
//        super();
        field_35659_a = new float[256];
        field_35657_b = new float[256];
        field_35658_c = new BiomeGenBase[256];
        field_35655_d = i;
        field_35656_e = j;
        BiomeCache.getWorldChunkManager(biomecache).getTemperatures(field_35659_a, i << 4, j << 4, 16, 16);
        BiomeCache.getWorldChunkManager(biomecache).getRainfall(field_35657_b, i << 4, j << 4, 16, 16);
        BiomeCache.getWorldChunkManager(biomecache).func_35555_a(field_35658_c, i << 4, j << 4, 16, 16, false);
    }

    public BiomeGenBase func_35651_a(int i, int j)
    {
        return field_35658_c[i & 0xf | (j & 0xf) << 4];
    }

    public float func_35650_b(int i, int j)
    {
        return field_35659_a[i & 0xf | (j & 0xf) << 4];
    }

    public float func_35652_c(int i, int j)
    {
        return field_35657_b[i & 0xf | (j & 0xf) << 4];
    }
}
