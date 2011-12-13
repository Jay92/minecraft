// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            TileEntityChest, TileEntityRenderer, Block

public class ChestItemRenderHelper
{

    public static ChestItemRenderHelper instance = new ChestItemRenderHelper();
    private TileEntityChest field_35610_b;

    public ChestItemRenderHelper()
    {
        field_35610_b = new TileEntityChest();
    }

    public void func_35609_a(Block block, int i, float f)
    {
        TileEntityRenderer.instance.renderTileEntityAt(field_35610_b, 0.0D, 0.0D, 0.0D, 0.0F);
    }

}
