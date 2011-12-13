// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.IOException;
import java.util.*;

// Referenced classes of package net.minecraft.src:
//            IChunkProvider, LongHashMap, EmptyChunk, World, 
//            ChunkCoordIntPair, ChunkCoordinates, Chunk, IChunkLoader, 
//            IProgressUpdate, EnumCreatureType, ChunkPosition

public class ChunkProvider
    implements IChunkProvider
{

    private Set droppedChunksSet;
    private Chunk emptyChunk;
    private IChunkProvider chunkProvider;
    private IChunkLoader chunkLoader;
    private LongHashMap chunkMap;
    private List chunkList;
    private World worldObj;
    private int field_35392_h;

    public ChunkProvider(World world, IChunkLoader ichunkloader, IChunkProvider ichunkprovider)
    {
        droppedChunksSet = new HashSet();
        chunkMap = new LongHashMap();
        chunkList = new ArrayList();
        emptyChunk = new EmptyChunk(world, new byte[256 * world.field_35472_c], 0, 0);
        worldObj = world;
        chunkLoader = ichunkloader;
        chunkProvider = ichunkprovider;
    }

    public boolean chunkExists(int i, int j)
    {
        return chunkMap.func_35575_b(ChunkCoordIntPair.chunkXZ2Int(i, j));
    }

    public void dropChunk(int i, int j)
    {
        ChunkCoordinates chunkcoordinates = worldObj.getSpawnPoint();
        int k = (i * 16 + 8) - chunkcoordinates.posX;
        int l = (j * 16 + 8) - chunkcoordinates.posZ;
        char c = '\200';
        if(k < -c || k > c || l < -c || l > c)
        {
            droppedChunksSet.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(i, j)));
        }
    }

    public Chunk loadChunk(int i, int j)
    {
        long l = ChunkCoordIntPair.chunkXZ2Int(i, j);
        droppedChunksSet.remove(Long.valueOf(l));
        Chunk chunk = (Chunk)chunkMap.getValueByKey(l);
        if(chunk == null)
        {
            int k = 0x1c9c3c;
            if(i < -k || j < -k || i >= k || j >= k)
            {
                return emptyChunk;
            }
            chunk = loadChunkFromFile(i, j);
            if(chunk == null)
            {
                if(chunkProvider == null)
                {
                    chunk = emptyChunk;
                } else
                {
                    chunk = chunkProvider.provideChunk(i, j);
                }
            }
            chunkMap.add(l, chunk);
            chunkList.add(chunk);
            if(chunk != null)
            {
                chunk.func_4143_d();
                chunk.onChunkLoad();
            }
            chunk.populateChunk(this, this, i, j);
        }
        return chunk;
    }

    public Chunk provideChunk(int i, int j)
    {
        Chunk chunk = (Chunk)chunkMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(i, j));
        if(chunk == null)
        {
            return loadChunk(i, j);
        } else
        {
            return chunk;
        }
    }

    private Chunk loadChunkFromFile(int i, int j)
    {
        if(chunkLoader == null)
        {
            return null;
        }
        try
        {
            Chunk chunk = chunkLoader.loadChunk(worldObj, i, j);
            if(chunk != null)
            {
                chunk.lastSaveTime = worldObj.getWorldTime();
            }
            return chunk;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }

    private void unloadAndSaveChunkData(Chunk chunk)
    {
        if(chunkLoader == null)
        {
            return;
        }
        try
        {
            chunkLoader.saveExtraChunkData(worldObj, chunk);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void unloadAndSaveChunk(Chunk chunk)
    {
        if(chunkLoader == null)
        {
            return;
        }
        try
        {
            chunk.lastSaveTime = worldObj.getWorldTime();
            chunkLoader.saveChunk(worldObj, chunk);
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j)
    {
        Chunk chunk = provideChunk(i, j);
        if(!chunk.isTerrainPopulated)
        {
            chunk.isTerrainPopulated = true;
            if(chunkProvider != null)
            {
                chunkProvider.populate(ichunkprovider, i, j);
                chunk.setChunkModified();
            }
        }
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate)
    {
        int i = 0;
        for(int j = 0; j < chunkList.size(); j++)
        {
            Chunk chunk = (Chunk)chunkList.get(j);
            if(flag && !chunk.neverSave)
            {
                unloadAndSaveChunkData(chunk);
            }
            if(!chunk.needsSaving(flag))
            {
                continue;
            }
            unloadAndSaveChunk(chunk);
            chunk.isModified = false;
            if(++i == 24 && !flag)
            {
                return false;
            }
        }

        if(flag)
        {
            if(chunkLoader == null)
            {
                return true;
            }
            chunkLoader.saveExtraData();
        }
        return true;
    }

    public boolean unload100OldestChunks()
    {
        for(int i = 0; i < 100; i++)
        {
            if(!droppedChunksSet.isEmpty())
            {
                Long long1 = (Long)droppedChunksSet.iterator().next();
                Chunk chunk1 = (Chunk)chunkMap.getValueByKey(long1.longValue());
                chunk1.onChunkUnload();
                unloadAndSaveChunk(chunk1);
                unloadAndSaveChunkData(chunk1);
                droppedChunksSet.remove(long1);
                chunkMap.remove(long1.longValue());
                chunkList.remove(chunk1);
            }
        }

        for(int j = 0; j < 10; j++)
        {
            if(field_35392_h >= chunkList.size())
            {
                field_35392_h = 0;
                break;
            }
            Chunk chunk = (Chunk)chunkList.get(field_35392_h++);
            EntityPlayer entityplayer = worldObj.getClosestPlayer((double)(chunk.xPosition << 4) + 8D, 64D, (double)(chunk.zPosition << 4) + 8D, 288D);
            if(entityplayer == null)
            {
                dropChunk(chunk.xPosition, chunk.zPosition);
            }
        }

        if(chunkLoader != null)
        {
            chunkLoader.func_814_a();
        }
        return chunkProvider.unload100OldestChunks();
    }

    public boolean canSave()
    {
        return true;
    }

    public String makeString()
    {
        return (new StringBuilder()).append("ServerChunkCache: ").append(chunkMap.getNumHashElements()).append(" Drop: ").append(droppedChunksSet.size()).toString();
    }

    public List func_40377_a(EnumCreatureType enumcreaturetype, int i, int j, int k)
    {
        return chunkProvider.func_40377_a(enumcreaturetype, i, j, k);
    }

    public ChunkPosition func_40376_a(World world, String s, int i, int j, int k)
    {
        return chunkProvider.func_40376_a(world, s, i, j, k);
    }
}
