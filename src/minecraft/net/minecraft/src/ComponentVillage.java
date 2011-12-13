// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            StructureComponent, StructureBoundingBox, StructureVillagePieces, World, 
//            EntityVillager, ComponentVillageStartPiece

abstract class ComponentVillage extends StructureComponent
{

    private int field_39009_a;

    protected ComponentVillage(int i)
    {
        super(i);
    }

    protected StructureComponent func_35077_a(ComponentVillageStartPiece componentvillagestartpiece, List list, Random random, int i, int j)
    {
        switch(coordBaseMode)
        {
        case 2: // '\002'
            return StructureVillagePieces.getNextStructureComponent(componentvillagestartpiece, list, random, boundingBox.minX - 1, boundingBox.minY + i, boundingBox.minZ + j, 1, func_35012_c());

        case 0: // '\0'
            return StructureVillagePieces.getNextStructureComponent(componentvillagestartpiece, list, random, boundingBox.minX - 1, boundingBox.minY + i, boundingBox.minZ + j, 1, func_35012_c());

        case 1: // '\001'
            return StructureVillagePieces.getNextStructureComponent(componentvillagestartpiece, list, random, boundingBox.minX + j, boundingBox.minY + i, boundingBox.minZ - 1, 2, func_35012_c());

        case 3: // '\003'
            return StructureVillagePieces.getNextStructureComponent(componentvillagestartpiece, list, random, boundingBox.minX + j, boundingBox.minY + i, boundingBox.minZ - 1, 2, func_35012_c());
        }
        return null;
    }

    protected StructureComponent func_35076_b(ComponentVillageStartPiece componentvillagestartpiece, List list, Random random, int i, int j)
    {
        switch(coordBaseMode)
        {
        case 2: // '\002'
            return StructureVillagePieces.getNextStructureComponent(componentvillagestartpiece, list, random, boundingBox.maxX + 1, boundingBox.minY + i, boundingBox.minZ + j, 3, func_35012_c());

        case 0: // '\0'
            return StructureVillagePieces.getNextStructureComponent(componentvillagestartpiece, list, random, boundingBox.maxX + 1, boundingBox.minY + i, boundingBox.minZ + j, 3, func_35012_c());

        case 1: // '\001'
            return StructureVillagePieces.getNextStructureComponent(componentvillagestartpiece, list, random, boundingBox.minX + j, boundingBox.minY + i, boundingBox.maxZ + 1, 0, func_35012_c());

        case 3: // '\003'
            return StructureVillagePieces.getNextStructureComponent(componentvillagestartpiece, list, random, boundingBox.minX + j, boundingBox.minY + i, boundingBox.maxZ + 1, 0, func_35012_c());
        }
        return null;
    }

    protected int getAverageGroundLevel(World world, StructureBoundingBox structureboundingbox)
    {
        int i = 0;
        int j = 0;
        for(int k = boundingBox.minZ; k <= boundingBox.maxZ; k++)
        {
            for(int l = boundingBox.minX; l <= boundingBox.maxX; l++)
            {
                if(structureboundingbox.isVecInside(l, 64, k))
                {
                    i += Math.max(world.getTopSolidOrLiquidBlock(l, k), world.field_35470_e);
                    j++;
                }
            }

        }

        if(j == 0)
        {
            return -1;
        } else
        {
            return i / j;
        }
    }

    protected static boolean canVillageGoDeeper(StructureBoundingBox structureboundingbox)
    {
        return structureboundingbox != null && structureboundingbox.minY > 10;
    }

    protected void func_40044_a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l)
    {
        if(field_39009_a >= l)
        {
            return;
        }
        int i1 = field_39009_a;
        do
        {
            if(i1 >= l)
            {
                break;
            }
            int j1 = getXWithOffset(i + i1, k);
            int k1 = getYWithOffset(j);
            int l1 = getZWithOffset(i + i1, k);
            if(!structureboundingbox.isVecInside(j1, k1, l1))
            {
                break;
            }
            field_39009_a++;
            EntityVillager entityvillager = new EntityVillager(world, func_40043_a(i1));
            entityvillager.setLocationAndAngles((double)j1 + 0.5D, k1, (double)l1 + 0.5D, 0.0F, 0.0F);
            world.entityJoinedWorld(entityvillager);
            i1++;
        } while(true);
    }

    protected int func_40043_a(int i)
    {
        return 0;
    }
}
