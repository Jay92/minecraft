// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


public class ColorizerGrass
{

    private static int grassBuffer[] = new int[0x10000];

    public ColorizerGrass()
    {
    }

    public static void setGrassBiomeColorizer(int ai[])
    {
        grassBuffer = ai;
    }

    public static int getGrassColor(double d, double d1)
    {
        d1 *= d;
        int i = (int)((1.0D - d) * 255D);
        int j = (int)((1.0D - d1) * 255D);
        return grassBuffer[j << 8 | i];
    }

}
