// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.File;
import java.util.List;

// Referenced classes of package net.minecraft.src:
//            ISaveHandler, WorldInfo, WorldProvider, IChunkLoader

public class SaveHandlerMP
    implements ISaveHandler
{

    public SaveHandlerMP()
    {
    }

    public WorldInfo loadWorldInfo()
    {
        return null;
    }

    public void checkSessionLock()
    {
    }

    public IChunkLoader getChunkLoader(WorldProvider worldprovider)
    {
        return null;
    }

    public void saveWorldInfoAndPlayer(WorldInfo worldinfo, List list)
    {
    }

    public void saveWorldInfo(WorldInfo worldinfo)
    {
    }

    public File getMapFile(String s)
    {
        return null;
    }

    public String func_40530_d()
    {
        return "none";
    }
}
