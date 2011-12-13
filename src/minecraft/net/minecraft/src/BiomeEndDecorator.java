// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BiomeDecorator, WorldGenSpikes, Block, World, 
//            WorldGenerator, EntityDragon, BiomeGenBase

public class BiomeEndDecorator extends BiomeDecorator
{

    protected WorldGenerator spikeGen;

    public BiomeEndDecorator(BiomeGenBase biomegenbase)
    {
        super(biomegenbase);
        spikeGen = new WorldGenSpikes(Block.whiteStone.blockID);
    }

    protected void decorate_do()
    {
        generateOres();
        if(decoRNG.nextInt(5) == 0)
        {
            int i = chunk_X + decoRNG.nextInt(16) + 8;
            int j = chunk_Z + decoRNG.nextInt(16) + 8;
            int k = currentWorld.getTopSolidOrLiquidBlock(i, j);
            if(k <= 0);
            spikeGen.generate(currentWorld, decoRNG, i, k, j);
        }
        if(chunk_X == 0 && chunk_Z == 0)
        {
            EntityDragon entitydragon = new EntityDragon(currentWorld);
            entitydragon.setLocationAndAngles(0.0D, 128D, 0.0D, decoRNG.nextFloat() * 360F, 0.0F);
            currentWorld.entityJoinedWorld(entitydragon);
        }
    }
}
