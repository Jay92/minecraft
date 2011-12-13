// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


public class ColorizerFoliage
{

    private static int foliageBuffer[] = new int[0x10000];

    public ColorizerFoliage()
    {
    }

    public static void getFoilageBiomeColorizer(int ai[])
    {
        foliageBuffer = ai;
    }

    public static int getFoliageColor(double d, double d1)
    {
        d1 *= d;
        int i = (int)((1.0D - d) * 255D);
        int j = (int)((1.0D - d1) * 255D);
        return foliageBuffer[j << 8 | i];
    }

    public static int getFoliageColorPine()
    {
        return 0x619961;
    }

    public static int getFoliageColorBirch()
    {
        return 0x80a755;
    }

    public static int getFoliageColorBasic()
    {
        return 0x48b518;
    }

}
