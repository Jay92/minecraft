// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


public class ColorizerWater
{

    private static int waterBuffer[] = new int[0x10000];

    public ColorizerWater()
    {
    }

    public static void getWaterBiomeColorizer(int ai[])
    {
        waterBuffer = ai;
    }

}
