// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


public final class WorldSettings
{

    private final long worldSeed;
    private final int worldType;
    private final boolean mapFeaturesEnabled;
    private final boolean hardcoreEnabled;

    public WorldSettings(long l, int i, boolean flag, boolean flag1)
    {
        worldSeed = l;
        worldType = i;
        mapFeaturesEnabled = flag;
        hardcoreEnabled = flag1;
    }

    public long getSeed()
    {
        return worldSeed;
    }

    public int getGameType()
    {
        return worldType;
    }

    public boolean getHardcoreEnabled()
    {
        return hardcoreEnabled;
    }

    public boolean isMapFeaturesEnabled()
    {
        return mapFeaturesEnabled;
    }
}
