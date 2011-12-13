// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            Block, MathHelper, IBlockAccess, BlockRail, 
//            BlockDragonEgg, BlockFence, BlockPane, BlockFenceGate, 
//            BlockCauldron, BlockBrewingStand, BlockEndPortalFrame, Tessellator, 
//            BlockBed, Direction, EntityRenderer, BlockRedstoneRepeater, 
//            BlockPistonBase, BlockPistonExtension, Vec3D, BlockFire, 
//            BlockRedstoneWire, BlockStem, BlockFluid, Material, 
//            BlockDoor, BlockGrass, ChestItemRenderHelper, World

public class RenderBlocks
{

    public IBlockAccess blockAccess;
    private int overrideBlockTexture;
    private boolean flipTexture;
    private boolean renderAllFaces;
    public static boolean fancyGrass = true;
    public boolean useInventoryTint;
    public boolean catchesLight[];
    private int uvRotateEast;
    private int uvRotateWest;
    private int uvRotateSouth;
    private int uvRotateNorth;
    private int uvRotateTop;
    private int uvRotateBottom;
    private boolean enableAO;
    private float lightValueOwn;
    private float aoLightValueXNeg;
    private float aoLightValueYNeg;
    private float aoLightValueZNeg;
    private float aoLightValueXPos;
    private float aoLightValueYPos;
    private float aoLightValueZPos;
    private float aoLightValueScratchXYZNNN;
    private float aoLightValueScratchXYNN;
    private float aoLightValueScratchXYZNNP;
    private float aoLightValueScratchYZNN;
    private float aoLightValueScratchYZNP;
    private float aoLightValueScratchXYZPNN;
    private float aoLightValueScratchXYPN;
    private float aoLightValueScratchXYZPNP;
    private float aoLightValueScratchXYZNPN;
    private float aoLightValueScratchXYNP;
    private float aoLightValueScratchXYZNPP;
    private float aoLightValueScratchYZPN;
    private float aoLightValueScratchXYZPPN;
    private float aoLightValueScratchXYPP;
    private float aoLightValueScratchYZPP;
    private float aoLightValueScratchXYZPPP;
    private float aoLightValueScratchXZNN;
    private float aoLightValueScratchXZPN;
    private float aoLightValueScratchXZNP;
    private float aoLightValueScratchXZPP;
    private int aoBrightnessXYZNNN;
    private int aoBrightnessXYNN;
    private int aoBrightnessXYZNNP;
    private int aoBrightnessYZNN;
    private int aoBrightnessYZNP;
    private int aoBrightnessXYZPNN;
    private int aoBrightnessXYPN;
    private int aoBrightnessXYZPNP;
    private int aoBrightnessXYZNPN;
    private int aoBrightnessXYNP;
    private int aoBrightnessXYZNPP;
    private int aoBrightnessYZPN;
    private int aoBrightnessXYZPPN;
    private int aoBrightnessXYPP;
    private int aoBrightnessYZPP;
    private int aoBrightnessXYZPPP;
    private int aoBrightnessXZNN;
    private int aoBrightnessXZPN;
    private int aoBrightnessXZNP;
    private int aoBrightnessXZPP;
    private int aoType;
    private int brightnessTopLeft;
    private int brightnessBottomLeft;
    private int brightnessBottomRight;
    private int brightnessTopRight;
    private float colorRedTopLeft;
    private float colorRedBottomLeft;
    private float colorRedBottomRight;
    private float colorRedTopRight;
    private float colorGreenTopLeft;
    private float colorGreenBottomLeft;
    private float colorGreenBottomRight;
    private float colorGreenTopRight;
    private float colorBlueTopLeft;
    private float colorBlueBottomLeft;
    private float colorBlueBottomRight;
    private float colorBlueTopRight;
    private boolean aoGrassXYZCPN;
    private boolean aoGrassXYZPPC;
    private boolean aoGrassXYZNPC;
    private boolean aoGrassXYZCPP;
    private boolean aoGrassXYZNCN;
    private boolean aoGrassXYZPCP;
    private boolean aoGrassXYZNCP;
    private boolean aoGrassXYZPCN;
    private boolean aoGrassXYZCNN;
    private boolean aoGrassXYZPNC;
    private boolean aoGrassXYZNNC;
    private boolean aoGrassXYZCNP;

    public RenderBlocks(IBlockAccess iblockaccess)
    {
        overrideBlockTexture = -1;
        flipTexture = false;
        renderAllFaces = false;
        useInventoryTint = true;
        catchesLight = new boolean[256];
        uvRotateEast = 0;
        uvRotateWest = 0;
        uvRotateSouth = 0;
        uvRotateNorth = 0;
        uvRotateTop = 0;
        uvRotateBottom = 0;
        aoType = 1;
        blockAccess = iblockaccess;
        for(int i = 0; i < 256; i++)
        {
            Block block = Block.blocksList[i];
            if(block == null)
            {
                continue;
            }
            if(block == Block.ice)
            {
                catchesLight[i] = true;
            }
            if(block.renderAsNormalBlock() && block.isOpaqueCube())
            {
                catchesLight[i] = true;
            }
        }

    }

    public RenderBlocks()
    {
        overrideBlockTexture = -1;
        flipTexture = false;
        renderAllFaces = false;
        useInventoryTint = true;
        catchesLight = new boolean[256];
        uvRotateEast = 0;
        uvRotateWest = 0;
        uvRotateSouth = 0;
        uvRotateNorth = 0;
        uvRotateTop = 0;
        uvRotateBottom = 0;
        aoType = 1;
    }

    public void clearOverrideBlockTexture()
    {
        overrideBlockTexture = -1;
    }

    public void renderBlockUsingTexture(Block block, int i, int j, int k, int l)
    {
        overrideBlockTexture = l;
        renderBlockByRenderType(block, i, j, k);
        overrideBlockTexture = -1;
    }

    public void renderBlockAllFaces(Block block, int i, int j, int k)
    {
        renderAllFaces = true;
        renderBlockByRenderType(block, i, j, k);
        renderAllFaces = false;
    }

    public boolean renderLightOnBlock(int i, int j, int k, int l)
    {
        if(this != null)
        {
            return false;
        }
        byte byte0 = 13;
        byte byte1 = 52;
        byte byte2 = 32;
        int i1 = MathHelper.abs_int(i - byte0);
        int j1 = MathHelper.abs_int(k - byte1);
        if(j1 <= byte2 && i1 <= byte2 && (i1 == byte2 || j1 == byte2) && catchesLight[blockAccess.getBlockId(i, j, k)])
        {
            int k1 = 5;
            if(i == byte0 - byte2)
            {
                k1--;
            }
            if(i == byte0 + byte2)
            {
                k1++;
            }
            if(k == byte1 - byte2)
            {
                k1 -= 3;
            }
            if(k == byte1 + byte2)
            {
                k1 += 3;
            }
            renderCorrectedLightOnBlock(k1, i, j, k);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean renderBlockByRenderType(Block block, int i, int j, int k)
    {
        int l = block.getRenderType();
        block.setBlockBoundsBasedOnState(blockAccess, i, j, k);
        if(l == 0)
        {
            return renderStandardBlock(block, i, j, k);
        }
        if(l == 4)
        {
            return renderBlockFluids(block, i, j, k);
        }
        if(l == 13)
        {
            return renderBlockCactus(block, i, j, k);
        }
        if(l == 1)
        {
            return renderBlockReed(block, i, j, k);
        }
        if(l == 19)
        {
            return renderBlockStem(block, i, j, k);
        }
        if(l == 23)
        {
            return renderBlockLilyPad(block, i, j, k);
        }
        if(l == 6)
        {
            return renderBlockCrops(block, i, j, k);
        }
        if(l == 2)
        {
            return renderBlockTorch(block, i, j, k);
        }
        if(l == 3)
        {
            return renderBlockFire(block, i, j, k);
        }
        if(l == 5)
        {
            return renderBlockRedstoneWire(block, i, j, k);
        }
        if(l == 8)
        {
            return renderBlockLadder(block, i, j, k);
        }
        if(l == 7)
        {
            return renderBlockDoor(block, i, j, k);
        }
        if(l == 9)
        {
            return renderBlockMinecartTrack((BlockRail)block, i, j, k);
        }
        if(l == 10)
        {
            return renderBlockStairs(block, i, j, k);
        }
        if(l == 27)
        {
            return func_41088_a((BlockDragonEgg)block, i, j, k);
        }
        if(l == 11)
        {
            return renderBlockFence((BlockFence)block, i, j, k);
        }
        if(l == 12)
        {
            return renderBlockLever(block, i, j, k);
        }
        if(l == 14)
        {
            return renderBlockBed(block, i, j, k);
        }
        if(l == 15)
        {
            return renderBlockRepeater(block, i, j, k);
        }
        if(l == 16)
        {
            return renderPistonBase(block, i, j, k, false);
        }
        if(l == 17)
        {
            return renderPistonExtension(block, i, j, k, true);
        }
        if(l == 18)
        {
            return renderBlockPane((BlockPane)block, i, j, k);
        }
        if(l == 20)
        {
            return renderBlockVine(block, i, j, k);
        }
        if(l == 21)
        {
            return renderBlockFenceGate((BlockFenceGate)block, i, j, k);
        }
        if(l == 24)
        {
            return renderBlockCauldron((BlockCauldron)block, i, j, k);
        }
        if(l == 25)
        {
            return renderBlockBrewingStand((BlockBrewingStand)block, i, j, k);
        }
        if(l == 26)
        {
            return renderBlockEndPortalFrame(block, i, j, k);
        } else
        {
            return false;
        }
    }

    private boolean renderBlockEndPortalFrame(Block block, int i, int j, int k)
    {
        int l = blockAccess.getBlockMetadata(i, j, k);
        int i1 = l & 3;
        if(i1 == 0)
        {
            uvRotateTop = 3;
        } else
        if(i1 == 3)
        {
            uvRotateTop = 1;
        } else
        if(i1 == 1)
        {
            uvRotateTop = 2;
        }
        if(!BlockEndPortalFrame.func_40212_d(l))
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
            renderStandardBlock(block, i, j, k);
            block.setBlockBoundsForItemRender();
            uvRotateTop = 0;
            return true;
        } else
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
            renderStandardBlock(block, i, j, k);
            overrideBlockTexture = 174;
            block.setBlockBounds(0.25F, 0.8125F, 0.25F, 0.75F, 1.0F, 0.75F);
            renderStandardBlock(block, i, j, k);
            clearOverrideBlockTexture();
            block.setBlockBoundsForItemRender();
            uvRotateTop = 0;
            return true;
        }
    }

    private boolean renderBlockBed(Block block, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        int l = blockAccess.getBlockMetadata(i, j, k);
        int i1 = BlockBed.getDirectionFromMetadata(l);
        boolean flag = BlockBed.isBlockFootOfBed(l);
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        float f4 = f1;
        float f5 = f1;
        float f6 = f1;
        float f7 = f;
        float f8 = f2;
        float f9 = f3;
        float f10 = f;
        float f11 = f2;
        float f12 = f3;
        float f13 = f;
        float f14 = f2;
        float f15 = f3;
        int j1 = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
        tessellator.setBrightness(j1);
        tessellator.setColorOpaque_F(f7, f10, f13);
        int k1 = block.getBlockTexture(blockAccess, i, j, k, 0);
        int l1 = (k1 & 0xf) << 4;
        int i2 = k1 & 0xf0;
        double d = (float)l1 / 256F;
        double d1 = ((double)(l1 + 16) - 0.01D) / 256D;
        double d2 = (float)i2 / 256F;
        double d3 = ((double)(i2 + 16) - 0.01D) / 256D;
        double d4 = (double)i + block.minX;
        double d5 = (double)i + block.maxX;
        double d6 = (double)j + block.minY + 0.1875D;
        double d7 = (double)k + block.minZ;
        double d8 = (double)k + block.maxZ;
        tessellator.addVertexWithUV(d4, d6, d8, d, d3);
        tessellator.addVertexWithUV(d4, d6, d7, d, d2);
        tessellator.addVertexWithUV(d5, d6, d7, d1, d2);
        tessellator.addVertexWithUV(d5, d6, d8, d1, d3);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k));
        tessellator.setColorOpaque_F(f4, f5, f6);
        k1 = block.getBlockTexture(blockAccess, i, j, k, 1);
        l1 = (k1 & 0xf) << 4;
        i2 = k1 & 0xf0;
        d = (float)l1 / 256F;
        d1 = ((double)(l1 + 16) - 0.01D) / 256D;
        d2 = (float)i2 / 256F;
        d3 = ((double)(i2 + 16) - 0.01D) / 256D;
        d4 = d;
        d5 = d1;
        d6 = d2;
        d7 = d2;
        d8 = d;
        double d9 = d1;
        double d10 = d3;
        double d11 = d3;
        if(i1 == 0)
        {
            d5 = d;
            d6 = d3;
            d8 = d1;
            d11 = d2;
        } else
        if(i1 == 2)
        {
            d4 = d1;
            d7 = d3;
            d9 = d;
            d10 = d2;
        } else
        if(i1 == 3)
        {
            d4 = d1;
            d7 = d3;
            d9 = d;
            d10 = d2;
            d5 = d;
            d6 = d3;
            d8 = d1;
            d11 = d2;
        }
        double d12 = (double)i + block.minX;
        double d13 = (double)i + block.maxX;
        double d14 = (double)j + block.maxY;
        double d15 = (double)k + block.minZ;
        double d16 = (double)k + block.maxZ;
        tessellator.addVertexWithUV(d13, d14, d16, d8, d10);
        tessellator.addVertexWithUV(d13, d14, d15, d4, d6);
        tessellator.addVertexWithUV(d12, d14, d15, d5, d7);
        tessellator.addVertexWithUV(d12, d14, d16, d9, d11);
        k1 = Direction.headInvisibleFace[i1];
        if(flag)
        {
            k1 = Direction.headInvisibleFace[Direction.footInvisibleFaceRemap[i1]];
        }
        l1 = 4;
        switch(i1)
        {
        case 0: // '\0'
            l1 = 5;
            break;

        case 3: // '\003'
            l1 = 2;
            break;

        case 1: // '\001'
            l1 = 3;
            break;
        }
        if(k1 != 2 && (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2)))
        {
            tessellator.setBrightness(block.minZ <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j, k - 1) : j1);
            tessellator.setColorOpaque_F(f8, f11, f14);
            flipTexture = l1 == 2;
            renderEastFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 2));
        }
        if(k1 != 3 && (renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3)))
        {
            tessellator.setBrightness(block.maxZ >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j, k + 1) : j1);
            tessellator.setColorOpaque_F(f8, f11, f14);
            flipTexture = l1 == 3;
            renderWestFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 3));
        }
        if(k1 != 4 && (renderAllFaces || block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4)))
        {
            tessellator.setBrightness(block.minZ <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k) : j1);
            tessellator.setColorOpaque_F(f9, f12, f15);
            flipTexture = l1 == 4;
            renderNorthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 4));
        }
        if(k1 != 5 && (renderAllFaces || block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5)))
        {
            tessellator.setBrightness(block.maxZ >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k) : j1);
            tessellator.setColorOpaque_F(f9, f12, f15);
            flipTexture = l1 == 5;
            renderSouthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 5));
        }
        flipTexture = false;
        return true;
    }

    private boolean renderBlockBrewingStand(BlockBrewingStand blockbrewingstand, int i, int j, int k)
    {
        blockbrewingstand.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
        renderStandardBlock(blockbrewingstand, i, j, k);
        overrideBlockTexture = 156;
        blockbrewingstand.setBlockBounds(0.5625F, 0.0F, 0.3125F, 0.9375F, 0.125F, 0.6875F);
        renderStandardBlock(blockbrewingstand, i, j, k);
        blockbrewingstand.setBlockBounds(0.125F, 0.0F, 0.0625F, 0.5F, 0.125F, 0.4375F);
        renderStandardBlock(blockbrewingstand, i, j, k);
        blockbrewingstand.setBlockBounds(0.125F, 0.0F, 0.5625F, 0.5F, 0.125F, 0.9375F);
        renderStandardBlock(blockbrewingstand, i, j, k);
        clearOverrideBlockTexture();
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(blockbrewingstand.getMixedBrightnessForBlock(blockAccess, i, j, k));
        float f = 1.0F;
        int l = blockbrewingstand.colorMultiplier(blockAccess, i, j, k);
        float f1 = (float)(l >> 16 & 0xff) / 255F;
        float f2 = (float)(l >> 8 & 0xff) / 255F;
        float f3 = (float)(l & 0xff) / 255F;
        if(EntityRenderer.anaglyphEnable)
        {
            float f4 = (f1 * 30F + f2 * 59F + f3 * 11F) / 100F;
            float f5 = (f1 * 30F + f2 * 70F) / 100F;
            float f6 = (f1 * 30F + f3 * 70F) / 100F;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }
        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        int i1 = blockbrewingstand.getBlockTextureFromSideAndMetadata(0, 0);
        if(overrideBlockTexture >= 0)
        {
            i1 = overrideBlockTexture;
        }
        int j1 = (i1 & 0xf) << 4;
        int k1 = i1 & 0xf0;
        double d = (float)k1 / 256F;
        double d1 = ((float)k1 + 15.99F) / 256F;
        int l1 = blockAccess.getBlockMetadata(i, j, k);
        for(int i2 = 0; i2 < 3; i2++)
        {
            double d2 = ((double)i2 * 3.1415926535897931D * 2D) / 3D + 1.5707963267948966D;
            double d3 = ((float)j1 + 8F) / 256F;
            double d4 = ((float)j1 + 15.99F) / 256F;
            if((l1 & 1 << i2) != 0)
            {
                d3 = ((float)j1 + 7.99F) / 256F;
                d4 = ((float)j1 + 0.0F) / 256F;
            }
            double d5 = (double)i + 0.5D;
            double d6 = (double)i + 0.5D + (Math.sin(d2) * 8D) / 16D;
            double d7 = (double)k + 0.5D;
            double d8 = (double)k + 0.5D + (Math.cos(d2) * 8D) / 16D;
            tessellator.addVertexWithUV(d5, j + 1, d7, d3, d);
            tessellator.addVertexWithUV(d5, j + 0, d7, d3, d1);
            tessellator.addVertexWithUV(d6, j + 0, d8, d4, d1);
            tessellator.addVertexWithUV(d6, j + 1, d8, d4, d);
            tessellator.addVertexWithUV(d6, j + 1, d8, d4, d);
            tessellator.addVertexWithUV(d6, j + 0, d8, d4, d1);
            tessellator.addVertexWithUV(d5, j + 0, d7, d3, d1);
            tessellator.addVertexWithUV(d5, j + 1, d7, d3, d);
        }

        blockbrewingstand.setBlockBoundsForItemRender();
        return true;
    }

    private boolean renderBlockCauldron(BlockCauldron blockcauldron, int i, int j, int k)
    {
        renderStandardBlock(blockcauldron, i, j, k);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(blockcauldron.getMixedBrightnessForBlock(blockAccess, i, j, k));
        float f = 1.0F;
        int l = blockcauldron.colorMultiplier(blockAccess, i, j, k);
        float f1 = (float)(l >> 16 & 0xff) / 255F;
        float f2 = (float)(l >> 8 & 0xff) / 255F;
        float f3 = (float)(l & 0xff) / 255F;
        if(EntityRenderer.anaglyphEnable)
        {
            float f4 = (f1 * 30F + f2 * 59F + f3 * 11F) / 100F;
            float f5 = (f1 * 30F + f2 * 70F) / 100F;
            float f7 = (f1 * 30F + f3 * 70F) / 100F;
            f1 = f4;
            f2 = f5;
            f3 = f7;
        }
        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        char c = '\232';
        float f6 = 0.125F;
        renderSouthFace(blockcauldron, ((float)i - 1.0F) + f6, j, k, c);
        renderNorthFace(blockcauldron, ((float)i + 1.0F) - f6, j, k, c);
        renderWestFace(blockcauldron, i, j, ((float)k - 1.0F) + f6, c);
        renderEastFace(blockcauldron, i, j, ((float)k + 1.0F) - f6, c);
        char c1 = '\213';
        renderTopFace(blockcauldron, i, ((float)j - 1.0F) + 0.25F, k, c1);
        renderBottomFace(blockcauldron, i, ((float)j + 1.0F) - 0.75F, k, c1);
        int i1 = blockAccess.getBlockMetadata(i, j, k);
        if(i1 > 0)
        {
            char c2 = '\315';
            if(i1 > 3)
            {
                i1 = 3;
            }
            renderTopFace(blockcauldron, i, ((float)j - 1.0F) + (6F + (float)i1 * 3F) / 16F, k, c2);
        }
        return true;
    }

    public boolean renderBlockTorch(Block block, int i, int j, int k)
    {
        int l = blockAccess.getBlockMetadata(i, j, k);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        double d = 0.40000000596046448D;
        double d1 = 0.5D - d;
        double d2 = 0.20000000298023224D;
        if(l == 1)
        {
            renderTorchAtAngle(block, (double)i - d1, (double)j + d2, k, -d, 0.0D);
        } else
        if(l == 2)
        {
            renderTorchAtAngle(block, (double)i + d1, (double)j + d2, k, d, 0.0D);
        } else
        if(l == 3)
        {
            renderTorchAtAngle(block, i, (double)j + d2, (double)k - d1, 0.0D, -d);
        } else
        if(l == 4)
        {
            renderTorchAtAngle(block, i, (double)j + d2, (double)k + d1, 0.0D, d);
        } else
        {
            renderTorchAtAngle(block, i, j, k, 0.0D, 0.0D);
        }
        return true;
    }

    private boolean renderBlockRepeater(Block block, int i, int j, int k)
    {
        int l = blockAccess.getBlockMetadata(i, j, k);
        int i1 = l & 3;
        int j1 = (l & 0xc) >> 2;
        renderStandardBlock(block, i, j, k);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        double d = -0.1875D;
        double d1 = 0.0D;
        double d2 = 0.0D;
        double d3 = 0.0D;
        double d4 = 0.0D;
        switch(i1)
        {
        case 0: // '\0'
            d4 = -0.3125D;
            d2 = BlockRedstoneRepeater.repeaterTorchOffset[j1];
            break;

        case 2: // '\002'
            d4 = 0.3125D;
            d2 = -BlockRedstoneRepeater.repeaterTorchOffset[j1];
            break;

        case 3: // '\003'
            d3 = -0.3125D;
            d1 = BlockRedstoneRepeater.repeaterTorchOffset[j1];
            break;

        case 1: // '\001'
            d3 = 0.3125D;
            d1 = -BlockRedstoneRepeater.repeaterTorchOffset[j1];
            break;
        }
        renderTorchAtAngle(block, (double)i + d1, (double)j + d, (double)k + d2, 0.0D, 0.0D);
        renderTorchAtAngle(block, (double)i + d3, (double)j + d, (double)k + d4, 0.0D, 0.0D);
        int k1 = block.getBlockTextureFromSide(1);
        int l1 = (k1 & 0xf) << 4;
        int i2 = k1 & 0xf0;
        double d5 = (float)l1 / 256F;
        double d6 = ((float)l1 + 15.99F) / 256F;
        double d7 = (float)i2 / 256F;
        double d8 = ((float)i2 + 15.99F) / 256F;
        double d9 = 0.125D;
        double d10 = i + 1;
        double d11 = i + 1;
        double d12 = i + 0;
        double d13 = i + 0;
        double d14 = k + 0;
        double d15 = k + 1;
        double d16 = k + 1;
        double d17 = k + 0;
        double d18 = (double)j + d9;
        if(i1 == 2)
        {
            d10 = d11 = i + 0;
            d12 = d13 = i + 1;
            d14 = d17 = k + 1;
            d15 = d16 = k + 0;
        } else
        if(i1 == 3)
        {
            d10 = d13 = i + 0;
            d11 = d12 = i + 1;
            d14 = d15 = k + 0;
            d16 = d17 = k + 1;
        } else
        if(i1 == 1)
        {
            d10 = d13 = i + 1;
            d11 = d12 = i + 0;
            d14 = d15 = k + 1;
            d16 = d17 = k + 0;
        }
        tessellator.addVertexWithUV(d13, d18, d17, d5, d7);
        tessellator.addVertexWithUV(d12, d18, d16, d5, d8);
        tessellator.addVertexWithUV(d11, d18, d15, d6, d8);
        tessellator.addVertexWithUV(d10, d18, d14, d6, d7);
        return true;
    }

    public void renderPistonBaseAllFaces(Block block, int i, int j, int k)
    {
        renderAllFaces = true;
        renderPistonBase(block, i, j, k, true);
        renderAllFaces = false;
    }

    private boolean renderPistonBase(Block block, int i, int j, int k, boolean flag)
    {
        int l = blockAccess.getBlockMetadata(i, j, k);
        boolean flag1 = flag || (l & 8) != 0;
        int i1 = BlockPistonBase.getOrientation(l);
        if(flag1)
        {
            switch(i1)
            {
            case 0: // '\0'
                uvRotateEast = 3;
                uvRotateWest = 3;
                uvRotateSouth = 3;
                uvRotateNorth = 3;
                block.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 1: // '\001'
                block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                break;

            case 2: // '\002'
                uvRotateSouth = 1;
                uvRotateNorth = 2;
                block.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                break;

            case 3: // '\003'
                uvRotateSouth = 2;
                uvRotateNorth = 1;
                uvRotateTop = 3;
                uvRotateBottom = 3;
                block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                break;

            case 4: // '\004'
                uvRotateEast = 1;
                uvRotateWest = 2;
                uvRotateTop = 2;
                uvRotateBottom = 1;
                block.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 5: // '\005'
                uvRotateEast = 2;
                uvRotateWest = 1;
                uvRotateTop = 1;
                uvRotateBottom = 2;
                block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
                break;
            }
            renderStandardBlock(block, i, j, k);
            uvRotateEast = 0;
            uvRotateWest = 0;
            uvRotateSouth = 0;
            uvRotateNorth = 0;
            uvRotateTop = 0;
            uvRotateBottom = 0;
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else
        {
            switch(i1)
            {
            case 0: // '\0'
                uvRotateEast = 3;
                uvRotateWest = 3;
                uvRotateSouth = 3;
                uvRotateNorth = 3;
                break;

            case 2: // '\002'
                uvRotateSouth = 1;
                uvRotateNorth = 2;
                break;

            case 3: // '\003'
                uvRotateSouth = 2;
                uvRotateNorth = 1;
                uvRotateTop = 3;
                uvRotateBottom = 3;
                break;

            case 4: // '\004'
                uvRotateEast = 1;
                uvRotateWest = 2;
                uvRotateTop = 2;
                uvRotateBottom = 1;
                break;

            case 5: // '\005'
                uvRotateEast = 2;
                uvRotateWest = 1;
                uvRotateTop = 1;
                uvRotateBottom = 2;
                break;
            }
            renderStandardBlock(block, i, j, k);
            uvRotateEast = 0;
            uvRotateWest = 0;
            uvRotateSouth = 0;
            uvRotateNorth = 0;
            uvRotateTop = 0;
            uvRotateBottom = 0;
        }
        return true;
    }

    private void renderPistonRodUD(double d, double d1, double d2, double d3, double d4, double d5, float f, double d6)
    {
        int i = 108;
        if(overrideBlockTexture >= 0)
        {
            i = overrideBlockTexture;
        }
        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        Tessellator tessellator = Tessellator.instance;
        double d7 = (float)(j + 0) / 256F;
        double d8 = (float)(k + 0) / 256F;
        double d9 = (((double)j + d6) - 0.01D) / 256D;
        double d10 = ((double)((float)k + 4F) - 0.01D) / 256D;
        tessellator.setColorOpaque_F(f, f, f);
        tessellator.addVertexWithUV(d, d3, d4, d9, d8);
        tessellator.addVertexWithUV(d, d2, d4, d7, d8);
        tessellator.addVertexWithUV(d1, d2, d5, d7, d10);
        tessellator.addVertexWithUV(d1, d3, d5, d9, d10);
    }

    private void renderPistonRodSN(double d, double d1, double d2, double d3, double d4, double d5, float f, double d6)
    {
        int i = 108;
        if(overrideBlockTexture >= 0)
        {
            i = overrideBlockTexture;
        }
        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        Tessellator tessellator = Tessellator.instance;
        double d7 = (float)(j + 0) / 256F;
        double d8 = (float)(k + 0) / 256F;
        double d9 = (((double)j + d6) - 0.01D) / 256D;
        double d10 = ((double)((float)k + 4F) - 0.01D) / 256D;
        tessellator.setColorOpaque_F(f, f, f);
        tessellator.addVertexWithUV(d, d2, d5, d9, d8);
        tessellator.addVertexWithUV(d, d2, d4, d7, d8);
        tessellator.addVertexWithUV(d1, d3, d4, d7, d10);
        tessellator.addVertexWithUV(d1, d3, d5, d9, d10);
    }

    private void renderPistonRodEW(double d, double d1, double d2, double d3, double d4, double d5, float f, double d6)
    {
        int i = 108;
        if(overrideBlockTexture >= 0)
        {
            i = overrideBlockTexture;
        }
        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        Tessellator tessellator = Tessellator.instance;
        double d7 = (float)(j + 0) / 256F;
        double d8 = (float)(k + 0) / 256F;
        double d9 = (((double)j + d6) - 0.01D) / 256D;
        double d10 = ((double)((float)k + 4F) - 0.01D) / 256D;
        tessellator.setColorOpaque_F(f, f, f);
        tessellator.addVertexWithUV(d1, d2, d4, d9, d8);
        tessellator.addVertexWithUV(d, d2, d4, d7, d8);
        tessellator.addVertexWithUV(d, d3, d5, d7, d10);
        tessellator.addVertexWithUV(d1, d3, d5, d9, d10);
    }

    public void renderPistonExtensionAllFaces(Block block, int i, int j, int k, boolean flag)
    {
        renderAllFaces = true;
        renderPistonExtension(block, i, j, k, flag);
        renderAllFaces = false;
    }

    private boolean renderPistonExtension(Block block, int i, int j, int k, boolean flag)
    {
        int l = blockAccess.getBlockMetadata(i, j, k);
        int i1 = BlockPistonExtension.func_31050_c(l);
        float f = block.getBlockBrightness(blockAccess, i, j, k);
        float f1 = flag ? 1.0F : 0.5F;
        double d = flag ? 16D : 8D;
        switch(i1)
        {
        case 0: // '\0'
            uvRotateEast = 3;
            uvRotateWest = 3;
            uvRotateSouth = 3;
            uvRotateNorth = 3;
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
            renderStandardBlock(block, i, j, k);
            renderPistonRodUD((float)i + 0.375F, (float)i + 0.625F, (float)j + 0.25F, (float)j + 0.25F + f1, (float)k + 0.625F, (float)k + 0.625F, f * 0.8F, d);
            renderPistonRodUD((float)i + 0.625F, (float)i + 0.375F, (float)j + 0.25F, (float)j + 0.25F + f1, (float)k + 0.375F, (float)k + 0.375F, f * 0.8F, d);
            renderPistonRodUD((float)i + 0.375F, (float)i + 0.375F, (float)j + 0.25F, (float)j + 0.25F + f1, (float)k + 0.375F, (float)k + 0.625F, f * 0.6F, d);
            renderPistonRodUD((float)i + 0.625F, (float)i + 0.625F, (float)j + 0.25F, (float)j + 0.25F + f1, (float)k + 0.625F, (float)k + 0.375F, f * 0.6F, d);
            break;

        case 1: // '\001'
            block.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderStandardBlock(block, i, j, k);
            renderPistonRodUD((float)i + 0.375F, (float)i + 0.625F, (((float)j - 0.25F) + 1.0F) - f1, ((float)j - 0.25F) + 1.0F, (float)k + 0.625F, (float)k + 0.625F, f * 0.8F, d);
            renderPistonRodUD((float)i + 0.625F, (float)i + 0.375F, (((float)j - 0.25F) + 1.0F) - f1, ((float)j - 0.25F) + 1.0F, (float)k + 0.375F, (float)k + 0.375F, f * 0.8F, d);
            renderPistonRodUD((float)i + 0.375F, (float)i + 0.375F, (((float)j - 0.25F) + 1.0F) - f1, ((float)j - 0.25F) + 1.0F, (float)k + 0.375F, (float)k + 0.625F, f * 0.6F, d);
            renderPistonRodUD((float)i + 0.625F, (float)i + 0.625F, (((float)j - 0.25F) + 1.0F) - f1, ((float)j - 0.25F) + 1.0F, (float)k + 0.625F, (float)k + 0.375F, f * 0.6F, d);
            break;

        case 2: // '\002'
            uvRotateSouth = 1;
            uvRotateNorth = 2;
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
            renderStandardBlock(block, i, j, k);
            renderPistonRodSN((float)i + 0.375F, (float)i + 0.375F, (float)j + 0.625F, (float)j + 0.375F, (float)k + 0.25F, (float)k + 0.25F + f1, f * 0.6F, d);
            renderPistonRodSN((float)i + 0.625F, (float)i + 0.625F, (float)j + 0.375F, (float)j + 0.625F, (float)k + 0.25F, (float)k + 0.25F + f1, f * 0.6F, d);
            renderPistonRodSN((float)i + 0.375F, (float)i + 0.625F, (float)j + 0.375F, (float)j + 0.375F, (float)k + 0.25F, (float)k + 0.25F + f1, f * 0.5F, d);
            renderPistonRodSN((float)i + 0.625F, (float)i + 0.375F, (float)j + 0.625F, (float)j + 0.625F, (float)k + 0.25F, (float)k + 0.25F + f1, f, d);
            break;

        case 3: // '\003'
            uvRotateSouth = 2;
            uvRotateNorth = 1;
            uvRotateTop = 3;
            uvRotateBottom = 3;
            block.setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
            renderStandardBlock(block, i, j, k);
            renderPistonRodSN((float)i + 0.375F, (float)i + 0.375F, (float)j + 0.625F, (float)j + 0.375F, (((float)k - 0.25F) + 1.0F) - f1, ((float)k - 0.25F) + 1.0F, f * 0.6F, d);
            renderPistonRodSN((float)i + 0.625F, (float)i + 0.625F, (float)j + 0.375F, (float)j + 0.625F, (((float)k - 0.25F) + 1.0F) - f1, ((float)k - 0.25F) + 1.0F, f * 0.6F, d);
            renderPistonRodSN((float)i + 0.375F, (float)i + 0.625F, (float)j + 0.375F, (float)j + 0.375F, (((float)k - 0.25F) + 1.0F) - f1, ((float)k - 0.25F) + 1.0F, f * 0.5F, d);
            renderPistonRodSN((float)i + 0.625F, (float)i + 0.375F, (float)j + 0.625F, (float)j + 0.625F, (((float)k - 0.25F) + 1.0F) - f1, ((float)k - 0.25F) + 1.0F, f, d);
            break;

        case 4: // '\004'
            uvRotateEast = 1;
            uvRotateWest = 2;
            uvRotateTop = 2;
            uvRotateBottom = 1;
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
            renderStandardBlock(block, i, j, k);
            renderPistonRodEW((float)i + 0.25F, (float)i + 0.25F + f1, (float)j + 0.375F, (float)j + 0.375F, (float)k + 0.625F, (float)k + 0.375F, f * 0.5F, d);
            renderPistonRodEW((float)i + 0.25F, (float)i + 0.25F + f1, (float)j + 0.625F, (float)j + 0.625F, (float)k + 0.375F, (float)k + 0.625F, f, d);
            renderPistonRodEW((float)i + 0.25F, (float)i + 0.25F + f1, (float)j + 0.375F, (float)j + 0.625F, (float)k + 0.375F, (float)k + 0.375F, f * 0.6F, d);
            renderPistonRodEW((float)i + 0.25F, (float)i + 0.25F + f1, (float)j + 0.625F, (float)j + 0.375F, (float)k + 0.625F, (float)k + 0.625F, f * 0.6F, d);
            break;

        case 5: // '\005'
            uvRotateEast = 2;
            uvRotateWest = 1;
            uvRotateTop = 1;
            uvRotateBottom = 2;
            block.setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderStandardBlock(block, i, j, k);
            renderPistonRodEW((((float)i - 0.25F) + 1.0F) - f1, ((float)i - 0.25F) + 1.0F, (float)j + 0.375F, (float)j + 0.375F, (float)k + 0.625F, (float)k + 0.375F, f * 0.5F, d);
            renderPistonRodEW((((float)i - 0.25F) + 1.0F) - f1, ((float)i - 0.25F) + 1.0F, (float)j + 0.625F, (float)j + 0.625F, (float)k + 0.375F, (float)k + 0.625F, f, d);
            renderPistonRodEW((((float)i - 0.25F) + 1.0F) - f1, ((float)i - 0.25F) + 1.0F, (float)j + 0.375F, (float)j + 0.625F, (float)k + 0.375F, (float)k + 0.375F, f * 0.6F, d);
            renderPistonRodEW((((float)i - 0.25F) + 1.0F) - f1, ((float)i - 0.25F) + 1.0F, (float)j + 0.625F, (float)j + 0.375F, (float)k + 0.625F, (float)k + 0.625F, f * 0.6F, d);
            break;
        }
        uvRotateEast = 0;
        uvRotateWest = 0;
        uvRotateSouth = 0;
        uvRotateNorth = 0;
        uvRotateTop = 0;
        uvRotateBottom = 0;
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return true;
    }

    public boolean renderBlockLever(Block block, int i, int j, int k)
    {
        int l = blockAccess.getBlockMetadata(i, j, k);
        int i1 = l & 7;
        boolean flag = (l & 8) > 0;
        Tessellator tessellator = Tessellator.instance;
        boolean flag1 = overrideBlockTexture >= 0;
        if(!flag1)
        {
            overrideBlockTexture = Block.cobblestone.blockIndexInTexture;
        }
        float f = 0.25F;
        float f1 = 0.1875F;
        float f2 = 0.1875F;
        if(i1 == 5)
        {
            block.setBlockBounds(0.5F - f1, 0.0F, 0.5F - f, 0.5F + f1, f2, 0.5F + f);
        } else
        if(i1 == 6)
        {
            block.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, f2, 0.5F + f1);
        } else
        if(i1 == 4)
        {
            block.setBlockBounds(0.5F - f1, 0.5F - f, 1.0F - f2, 0.5F + f1, 0.5F + f, 1.0F);
        } else
        if(i1 == 3)
        {
            block.setBlockBounds(0.5F - f1, 0.5F - f, 0.0F, 0.5F + f1, 0.5F + f, f2);
        } else
        if(i1 == 2)
        {
            block.setBlockBounds(1.0F - f2, 0.5F - f, 0.5F - f1, 1.0F, 0.5F + f, 0.5F + f1);
        } else
        if(i1 == 1)
        {
            block.setBlockBounds(0.0F, 0.5F - f, 0.5F - f1, f2, 0.5F + f, 0.5F + f1);
        }
        renderStandardBlock(block, i, j, k);
        if(!flag1)
        {
            overrideBlockTexture = -1;
        }
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
        float f3 = 1.0F;
        if(Block.lightValue[block.blockID] > 0)
        {
            f3 = 1.0F;
        }
        tessellator.setColorOpaque_F(f3, f3, f3);
        int j1 = block.getBlockTextureFromSide(0);
        if(overrideBlockTexture >= 0)
        {
            j1 = overrideBlockTexture;
        }
        int k1 = (j1 & 0xf) << 4;
        int l1 = j1 & 0xf0;
        float f4 = (float)k1 / 256F;
        float f5 = ((float)k1 + 15.99F) / 256F;
        float f6 = (float)l1 / 256F;
        float f7 = ((float)l1 + 15.99F) / 256F;
        Vec3D avec3d[] = new Vec3D[8];
        float f8 = 0.0625F;
        float f9 = 0.0625F;
        float f10 = 0.625F;
        avec3d[0] = Vec3D.createVector(-f8, 0.0D, -f9);
        avec3d[1] = Vec3D.createVector(f8, 0.0D, -f9);
        avec3d[2] = Vec3D.createVector(f8, 0.0D, f9);
        avec3d[3] = Vec3D.createVector(-f8, 0.0D, f9);
        avec3d[4] = Vec3D.createVector(-f8, f10, -f9);
        avec3d[5] = Vec3D.createVector(f8, f10, -f9);
        avec3d[6] = Vec3D.createVector(f8, f10, f9);
        avec3d[7] = Vec3D.createVector(-f8, f10, f9);
        for(int i2 = 0; i2 < 8; i2++)
        {
            if(flag)
            {
                avec3d[i2].zCoord -= 0.0625D;
                avec3d[i2].rotateAroundX(0.6981317F);
            } else
            {
                avec3d[i2].zCoord += 0.0625D;
                avec3d[i2].rotateAroundX(-0.6981317F);
            }
            if(i1 == 6)
            {
                avec3d[i2].rotateAroundY(1.570796F);
            }
            if(i1 < 5)
            {
                avec3d[i2].yCoord -= 0.375D;
                avec3d[i2].rotateAroundX(1.570796F);
                if(i1 == 4)
                {
                    avec3d[i2].rotateAroundY(0.0F);
                }
                if(i1 == 3)
                {
                    avec3d[i2].rotateAroundY(3.141593F);
                }
                if(i1 == 2)
                {
                    avec3d[i2].rotateAroundY(1.570796F);
                }
                if(i1 == 1)
                {
                    avec3d[i2].rotateAroundY(-1.570796F);
                }
                avec3d[i2].xCoord += (double)i + 0.5D;
                avec3d[i2].yCoord += (float)j + 0.5F;
                avec3d[i2].zCoord += (double)k + 0.5D;
            } else
            {
                avec3d[i2].xCoord += (double)i + 0.5D;
                avec3d[i2].yCoord += (float)j + 0.125F;
                avec3d[i2].zCoord += (double)k + 0.5D;
            }
        }

        Vec3D vec3d = null;
        Vec3D vec3d1 = null;
        Vec3D vec3d2 = null;
        Vec3D vec3d3 = null;
        for(int j2 = 0; j2 < 6; j2++)
        {
            if(j2 == 0)
            {
                f4 = (float)(k1 + 7) / 256F;
                f5 = ((float)(k1 + 9) - 0.01F) / 256F;
                f6 = (float)(l1 + 6) / 256F;
                f7 = ((float)(l1 + 8) - 0.01F) / 256F;
            } else
            if(j2 == 2)
            {
                f4 = (float)(k1 + 7) / 256F;
                f5 = ((float)(k1 + 9) - 0.01F) / 256F;
                f6 = (float)(l1 + 6) / 256F;
                f7 = ((float)(l1 + 16) - 0.01F) / 256F;
            }
            if(j2 == 0)
            {
                vec3d = avec3d[0];
                vec3d1 = avec3d[1];
                vec3d2 = avec3d[2];
                vec3d3 = avec3d[3];
            } else
            if(j2 == 1)
            {
                vec3d = avec3d[7];
                vec3d1 = avec3d[6];
                vec3d2 = avec3d[5];
                vec3d3 = avec3d[4];
            } else
            if(j2 == 2)
            {
                vec3d = avec3d[1];
                vec3d1 = avec3d[0];
                vec3d2 = avec3d[4];
                vec3d3 = avec3d[5];
            } else
            if(j2 == 3)
            {
                vec3d = avec3d[2];
                vec3d1 = avec3d[1];
                vec3d2 = avec3d[5];
                vec3d3 = avec3d[6];
            } else
            if(j2 == 4)
            {
                vec3d = avec3d[3];
                vec3d1 = avec3d[2];
                vec3d2 = avec3d[6];
                vec3d3 = avec3d[7];
            } else
            if(j2 == 5)
            {
                vec3d = avec3d[0];
                vec3d1 = avec3d[3];
                vec3d2 = avec3d[7];
                vec3d3 = avec3d[4];
            }
            tessellator.addVertexWithUV(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, f4, f7);
            tessellator.addVertexWithUV(vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, f5, f7);
            tessellator.addVertexWithUV(vec3d2.xCoord, vec3d2.yCoord, vec3d2.zCoord, f5, f6);
            tessellator.addVertexWithUV(vec3d3.xCoord, vec3d3.yCoord, vec3d3.zCoord, f4, f6);
        }

        return true;
    }

    public boolean renderCorrectedLightOnBlock(int i, int j, int k, int l)
    {
        Tessellator tessellator = Tessellator.instance;
        for(int i1 = 0; i1 < 6; i1++)
        {
            int j1 = j;
            int k1 = k;
            int l1 = l;
            if((i1 == 2 || i1 == 3) && (i - 1) / 3 == 1 || (i1 == 4 || i1 == 5) && (i - 1) % 3 == 1)
            {
                continue;
            }
            int i2 = 252;
            int j2 = 0;
            if(i == 2)
            {
                j2 = 0;
            }
            if(i == 4)
            {
                j2 = 1;
            }
            if(i == 8)
            {
                j2 = 2;
            }
            if(i == 6)
            {
                j2 = 3;
            }
            if(i == 1)
            {
                j2 = 0;
            }
            if(i == 3)
            {
                j2 = 3;
            }
            if(i == 7)
            {
                j2 = 1;
            }
            if(i == 9)
            {
                j2 = 2;
            }
            if(i == 2 || i == 4 || i == 6 || i == 8)
            {
                i2--;
            } else
            if(i1 >= 2)
            {
                if(i == 1)
                {
                    i2--;
                    if(i1 == 5 || i1 == 3)
                    {
                        continue;
                    }
                    if(i1 == 4)
                    {
                        j2++;
                    }
                } else
                if(i == 3)
                {
                    i2--;
                    if(i1 == 5 || i1 == 2)
                    {
                        continue;
                    }
                    if(i1 == 3)
                    {
                        j2++;
                    }
                } else
                if(i == 7)
                {
                    i2--;
                    if(i1 == 4 || i1 == 3)
                    {
                        continue;
                    }
                    if(i1 == 2)
                    {
                        j2++;
                    }
                } else
                if(i == 9)
                {
                    i2--;
                    if(i1 == 4 || i1 == 2)
                    {
                        continue;
                    }
                    if(i1 == 5)
                    {
                        j2++;
                    }
                }
            }
            if(i1 == 0)
            {
                k1++;
            }
            if(i1 == 1)
            {
                k1--;
            }
            if(i1 == 2)
            {
                j1++;
            }
            if(i1 == 3)
            {
                j1--;
            }
            if(i1 == 4)
            {
                l1++;
            }
            if(i1 == 5)
            {
                l1--;
            }
            if(catchesLight[blockAccess.getBlockId(j1, k1, l1)])
            {
                continue;
            }
            char c = '\u4000';
            tessellator.setColorOpaque_I(c);
            tessellator.setBrightness(blockAccess.getLightBrightnessForSkyBlocks(j1, k1, l1, 2));
            int k2 = (i2 & 0xf) << 4;
            int l2 = i2 & 0xf0;
            double d = ((double)k2 + 0.0D) / 256D;
            double d1 = ((double)k2 + 15.99D) / 256D;
            double d2 = (float)l2 / 256F;
            double d3 = ((float)l2 + 15.99F) / 256F;
            double d4 = d;
            double d5 = d1;
            double d6 = d1;
            double d7 = d;
            double d8 = d3;
            double d9 = d3;
            double d10 = d2;
            double d11 = d2;
            if(j2 != 0)
            {
                if(j2 == 1)
                {
                    d4 = d;
                    d5 = d;
                    d6 = d1;
                    d7 = d1;
                    d8 = d2;
                    d9 = d3;
                    d10 = d3;
                    d11 = d2;
                } else
                if(j2 == 2)
                {
                    d4 = d1;
                    d5 = d;
                    d6 = d;
                    d7 = d1;
                    d8 = d2;
                    d9 = d2;
                    d10 = d3;
                    d11 = d3;
                } else
                if(j2 == 3)
                {
                    d4 = d1;
                    d5 = d1;
                    d6 = d;
                    d7 = d;
                    d8 = d3;
                    d9 = d2;
                    d10 = d2;
                    d11 = d3;
                }
            }
            double d12 = 0.0031250000465661287D;
            double d13 = 0.0D - d12;
            double d14 = 1.0D + d12;
            if(i1 == 0)
            {
                tessellator.addVertexWithUV((double)j + d13, (double)(k + 1) + d12, (double)l + d13, d5, d9);
                tessellator.addVertexWithUV((double)j + d13, (double)(k + 1) + d12, (double)l + d14, d6, d10);
                tessellator.addVertexWithUV((double)j + d14, (double)(k + 1) + d12, (double)l + d14, d7, d11);
                tessellator.addVertexWithUV((double)j + d14, (double)(k + 1) + d12, (double)l + d13, d4, d8);
                continue;
            }
            if(i1 == 1)
            {
                tessellator.addVertexWithUV((double)j + d13, (double)(k + 0) - d12, (double)l + d14, d6, d10);
                tessellator.addVertexWithUV((double)j + d13, (double)(k + 0) - d12, (double)l + d13, d5, d9);
                tessellator.addVertexWithUV((double)j + d14, (double)(k + 0) - d12, (double)l + d13, d4, d8);
                tessellator.addVertexWithUV((double)j + d14, (double)(k + 0) - d12, (double)l + d14, d7, d11);
                continue;
            }
            if(i1 == 2)
            {
                tessellator.addVertexWithUV((double)(j + 1) + d12, (double)k + d13, (double)l + d14, d6, d10);
                tessellator.addVertexWithUV((double)(j + 1) + d12, (double)k + d13, (double)l + d13, d5, d9);
                tessellator.addVertexWithUV((double)(j + 1) + d12, (double)k + d14, (double)l + d13, d4, d8);
                tessellator.addVertexWithUV((double)(j + 1) + d12, (double)k + d14, (double)l + d14, d7, d11);
                continue;
            }
            if(i1 == 3)
            {
                tessellator.addVertexWithUV((double)(j + 0) - d12, (double)k + d13, (double)l + d13, d5, d9);
                tessellator.addVertexWithUV((double)(j + 0) - d12, (double)k + d13, (double)l + d14, d6, d10);
                tessellator.addVertexWithUV((double)(j + 0) - d12, (double)k + d14, (double)l + d14, d7, d11);
                tessellator.addVertexWithUV((double)(j + 0) - d12, (double)k + d14, (double)l + d13, d4, d8);
                continue;
            }
            if(i1 == 4)
            {
                tessellator.addVertexWithUV((double)j + d13, (double)k + d14, (double)(l + 1) + d12, d6, d10);
                tessellator.addVertexWithUV((double)j + d13, (double)k + d13, (double)(l + 1) + d12, d5, d9);
                tessellator.addVertexWithUV((double)j + d14, (double)k + d13, (double)(l + 1) + d12, d4, d8);
                tessellator.addVertexWithUV((double)j + d14, (double)k + d14, (double)(l + 1) + d12, d7, d11);
                continue;
            }
            if(i1 == 5)
            {
                tessellator.addVertexWithUV((double)j + d14, (double)k + d13, (double)(l + 0) - d12, d4, d8);
                tessellator.addVertexWithUV((double)j + d13, (double)k + d13, (double)(l + 0) - d12, d5, d9);
                tessellator.addVertexWithUV((double)j + d13, (double)k + d14, (double)(l + 0) - d12, d6, d10);
                tessellator.addVertexWithUV((double)j + d14, (double)k + d14, (double)(l + 0) - d12, d7, d11);
            }
        }

        return true;
    }

    public boolean renderBlockFire(Block block, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        int l = block.getBlockTextureFromSide(0);
        if(overrideBlockTexture >= 0)
        {
            l = overrideBlockTexture;
        }
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
        int i1 = (l & 0xf) << 4;
        int j1 = l & 0xf0;
        double d = (float)i1 / 256F;
        double d2 = ((float)i1 + 15.99F) / 256F;
        double d4 = (float)j1 / 256F;
        double d6 = ((float)j1 + 15.99F) / 256F;
        float f = 1.4F;
        if(blockAccess.isBlockNormalCube(i, j - 1, k) || Block.fire.canBlockCatchFire(blockAccess, i, j - 1, k))
        {
            double d8 = (double)i + 0.5D + 0.20000000000000001D;
            double d9 = ((double)i + 0.5D) - 0.20000000000000001D;
            double d12 = (double)k + 0.5D + 0.20000000000000001D;
            double d14 = ((double)k + 0.5D) - 0.20000000000000001D;
            double d16 = ((double)i + 0.5D) - 0.29999999999999999D;
            double d18 = (double)i + 0.5D + 0.29999999999999999D;
            double d20 = ((double)k + 0.5D) - 0.29999999999999999D;
            double d22 = (double)k + 0.5D + 0.29999999999999999D;
            tessellator.addVertexWithUV(d16, (float)j + f, k + 1, d2, d4);
            tessellator.addVertexWithUV(d8, j + 0, k + 1, d2, d6);
            tessellator.addVertexWithUV(d8, j + 0, k + 0, d, d6);
            tessellator.addVertexWithUV(d16, (float)j + f, k + 0, d, d4);
            tessellator.addVertexWithUV(d18, (float)j + f, k + 0, d2, d4);
            tessellator.addVertexWithUV(d9, j + 0, k + 0, d2, d6);
            tessellator.addVertexWithUV(d9, j + 0, k + 1, d, d6);
            tessellator.addVertexWithUV(d18, (float)j + f, k + 1, d, d4);
            d = (float)i1 / 256F;
            d2 = ((float)i1 + 15.99F) / 256F;
            d4 = (float)(j1 + 16) / 256F;
            d6 = ((float)j1 + 15.99F + 16F) / 256F;
            tessellator.addVertexWithUV(i + 1, (float)j + f, d22, d2, d4);
            tessellator.addVertexWithUV(i + 1, j + 0, d14, d2, d6);
            tessellator.addVertexWithUV(i + 0, j + 0, d14, d, d6);
            tessellator.addVertexWithUV(i + 0, (float)j + f, d22, d, d4);
            tessellator.addVertexWithUV(i + 0, (float)j + f, d20, d2, d4);
            tessellator.addVertexWithUV(i + 0, j + 0, d12, d2, d6);
            tessellator.addVertexWithUV(i + 1, j + 0, d12, d, d6);
            tessellator.addVertexWithUV(i + 1, (float)j + f, d20, d, d4);
            d8 = ((double)i + 0.5D) - 0.5D;
            d9 = (double)i + 0.5D + 0.5D;
            d12 = ((double)k + 0.5D) - 0.5D;
            d14 = (double)k + 0.5D + 0.5D;
            d16 = ((double)i + 0.5D) - 0.40000000000000002D;
            d18 = (double)i + 0.5D + 0.40000000000000002D;
            d20 = ((double)k + 0.5D) - 0.40000000000000002D;
            d22 = (double)k + 0.5D + 0.40000000000000002D;
            tessellator.addVertexWithUV(d16, (float)j + f, k + 0, d, d4);
            tessellator.addVertexWithUV(d8, j + 0, k + 0, d, d6);
            tessellator.addVertexWithUV(d8, j + 0, k + 1, d2, d6);
            tessellator.addVertexWithUV(d16, (float)j + f, k + 1, d2, d4);
            tessellator.addVertexWithUV(d18, (float)j + f, k + 1, d, d4);
            tessellator.addVertexWithUV(d9, j + 0, k + 1, d, d6);
            tessellator.addVertexWithUV(d9, j + 0, k + 0, d2, d6);
            tessellator.addVertexWithUV(d18, (float)j + f, k + 0, d2, d4);
            d = (float)i1 / 256F;
            d2 = ((float)i1 + 15.99F) / 256F;
            d4 = (float)j1 / 256F;
            d6 = ((float)j1 + 15.99F) / 256F;
            tessellator.addVertexWithUV(i + 0, (float)j + f, d22, d, d4);
            tessellator.addVertexWithUV(i + 0, j + 0, d14, d, d6);
            tessellator.addVertexWithUV(i + 1, j + 0, d14, d2, d6);
            tessellator.addVertexWithUV(i + 1, (float)j + f, d22, d2, d4);
            tessellator.addVertexWithUV(i + 1, (float)j + f, d20, d, d4);
            tessellator.addVertexWithUV(i + 1, j + 0, d12, d, d6);
            tessellator.addVertexWithUV(i + 0, j + 0, d12, d2, d6);
            tessellator.addVertexWithUV(i + 0, (float)j + f, d20, d2, d4);
        } else
        {
            float f2 = 0.2F;
            float f3 = 0.0625F;
            if((i + j + k & 1) == 1)
            {
                d = (float)i1 / 256F;
                d2 = ((float)i1 + 15.99F) / 256F;
                d4 = (float)(j1 + 16) / 256F;
                d6 = ((float)j1 + 15.99F + 16F) / 256F;
            }
            if((i / 2 + j / 2 + k / 2 & 1) == 1)
            {
                double d10 = d2;
                d2 = d;
                d = d10;
            }
            if(Block.fire.canBlockCatchFire(blockAccess, i - 1, j, k))
            {
                tessellator.addVertexWithUV((float)i + f2, (float)j + f + f3, k + 1, d2, d4);
                tessellator.addVertexWithUV(i + 0, (float)(j + 0) + f3, k + 1, d2, d6);
                tessellator.addVertexWithUV(i + 0, (float)(j + 0) + f3, k + 0, d, d6);
                tessellator.addVertexWithUV((float)i + f2, (float)j + f + f3, k + 0, d, d4);
                tessellator.addVertexWithUV((float)i + f2, (float)j + f + f3, k + 0, d, d4);
                tessellator.addVertexWithUV(i + 0, (float)(j + 0) + f3, k + 0, d, d6);
                tessellator.addVertexWithUV(i + 0, (float)(j + 0) + f3, k + 1, d2, d6);
                tessellator.addVertexWithUV((float)i + f2, (float)j + f + f3, k + 1, d2, d4);
            }
            if(Block.fire.canBlockCatchFire(blockAccess, i + 1, j, k))
            {
                tessellator.addVertexWithUV((float)(i + 1) - f2, (float)j + f + f3, k + 0, d, d4);
                tessellator.addVertexWithUV((i + 1) - 0, (float)(j + 0) + f3, k + 0, d, d6);
                tessellator.addVertexWithUV((i + 1) - 0, (float)(j + 0) + f3, k + 1, d2, d6);
                tessellator.addVertexWithUV((float)(i + 1) - f2, (float)j + f + f3, k + 1, d2, d4);
                tessellator.addVertexWithUV((float)(i + 1) - f2, (float)j + f + f3, k + 1, d2, d4);
                tessellator.addVertexWithUV((i + 1) - 0, (float)(j + 0) + f3, k + 1, d2, d6);
                tessellator.addVertexWithUV((i + 1) - 0, (float)(j + 0) + f3, k + 0, d, d6);
                tessellator.addVertexWithUV((float)(i + 1) - f2, (float)j + f + f3, k + 0, d, d4);
            }
            if(Block.fire.canBlockCatchFire(blockAccess, i, j, k - 1))
            {
                tessellator.addVertexWithUV(i + 0, (float)j + f + f3, (float)k + f2, d2, d4);
                tessellator.addVertexWithUV(i + 0, (float)(j + 0) + f3, k + 0, d2, d6);
                tessellator.addVertexWithUV(i + 1, (float)(j + 0) + f3, k + 0, d, d6);
                tessellator.addVertexWithUV(i + 1, (float)j + f + f3, (float)k + f2, d, d4);
                tessellator.addVertexWithUV(i + 1, (float)j + f + f3, (float)k + f2, d, d4);
                tessellator.addVertexWithUV(i + 1, (float)(j + 0) + f3, k + 0, d, d6);
                tessellator.addVertexWithUV(i + 0, (float)(j + 0) + f3, k + 0, d2, d6);
                tessellator.addVertexWithUV(i + 0, (float)j + f + f3, (float)k + f2, d2, d4);
            }
            if(Block.fire.canBlockCatchFire(blockAccess, i, j, k + 1))
            {
                tessellator.addVertexWithUV(i + 1, (float)j + f + f3, (float)(k + 1) - f2, d, d4);
                tessellator.addVertexWithUV(i + 1, (float)(j + 0) + f3, (k + 1) - 0, d, d6);
                tessellator.addVertexWithUV(i + 0, (float)(j + 0) + f3, (k + 1) - 0, d2, d6);
                tessellator.addVertexWithUV(i + 0, (float)j + f + f3, (float)(k + 1) - f2, d2, d4);
                tessellator.addVertexWithUV(i + 0, (float)j + f + f3, (float)(k + 1) - f2, d2, d4);
                tessellator.addVertexWithUV(i + 0, (float)(j + 0) + f3, (k + 1) - 0, d2, d6);
                tessellator.addVertexWithUV(i + 1, (float)(j + 0) + f3, (k + 1) - 0, d, d6);
                tessellator.addVertexWithUV(i + 1, (float)j + f + f3, (float)(k + 1) - f2, d, d4);
            }
            if(Block.fire.canBlockCatchFire(blockAccess, i, j + 1, k))
            {
                double d11 = (double)i + 0.5D + 0.5D;
                double d13 = ((double)i + 0.5D) - 0.5D;
                double d15 = (double)k + 0.5D + 0.5D;
                double d17 = ((double)k + 0.5D) - 0.5D;
                double d19 = ((double)i + 0.5D) - 0.5D;
                double d21 = (double)i + 0.5D + 0.5D;
                double d23 = ((double)k + 0.5D) - 0.5D;
                double d24 = (double)k + 0.5D + 0.5D;
                double d1 = (float)i1 / 256F;
                double d3 = ((float)i1 + 15.99F) / 256F;
                double d5 = (float)j1 / 256F;
                double d7 = ((float)j1 + 15.99F) / 256F;
                j++;
                float f1 = -0.2F;
                if((i + j + k & 1) == 0)
                {
                    tessellator.addVertexWithUV(d19, (float)j + f1, k + 0, d3, d5);
                    tessellator.addVertexWithUV(d11, j + 0, k + 0, d3, d7);
                    tessellator.addVertexWithUV(d11, j + 0, k + 1, d1, d7);
                    tessellator.addVertexWithUV(d19, (float)j + f1, k + 1, d1, d5);
                    d1 = (float)i1 / 256F;
                    d3 = ((float)i1 + 15.99F) / 256F;
                    d5 = (float)(j1 + 16) / 256F;
                    d7 = ((float)j1 + 15.99F + 16F) / 256F;
                    tessellator.addVertexWithUV(d21, (float)j + f1, k + 1, d3, d5);
                    tessellator.addVertexWithUV(d13, j + 0, k + 1, d3, d7);
                    tessellator.addVertexWithUV(d13, j + 0, k + 0, d1, d7);
                    tessellator.addVertexWithUV(d21, (float)j + f1, k + 0, d1, d5);
                } else
                {
                    tessellator.addVertexWithUV(i + 0, (float)j + f1, d24, d3, d5);
                    tessellator.addVertexWithUV(i + 0, j + 0, d17, d3, d7);
                    tessellator.addVertexWithUV(i + 1, j + 0, d17, d1, d7);
                    tessellator.addVertexWithUV(i + 1, (float)j + f1, d24, d1, d5);
                    d1 = (float)i1 / 256F;
                    d3 = ((float)i1 + 15.99F) / 256F;
                    d5 = (float)(j1 + 16) / 256F;
                    d7 = ((float)j1 + 15.99F + 16F) / 256F;
                    tessellator.addVertexWithUV(i + 1, (float)j + f1, d23, d3, d5);
                    tessellator.addVertexWithUV(i + 1, j + 0, d15, d3, d7);
                    tessellator.addVertexWithUV(i + 0, j + 0, d15, d1, d7);
                    tessellator.addVertexWithUV(i + 0, (float)j + f1, d23, d1, d5);
                }
            }
        }
        return true;
    }

    public boolean renderBlockRedstoneWire(Block block, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        int l = blockAccess.getBlockMetadata(i, j, k);
        int i1 = block.getBlockTextureFromSideAndMetadata(1, l);
        if(overrideBlockTexture >= 0)
        {
            i1 = overrideBlockTexture;
        }
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
        float f = 1.0F;
        float f1 = (float)l / 15F;
        float f2 = f1 * 0.6F + 0.4F;
        if(l == 0)
        {
            f2 = 0.3F;
        }
        float f3 = f1 * f1 * 0.7F - 0.5F;
        float f4 = f1 * f1 * 0.6F - 0.7F;
        if(f3 < 0.0F)
        {
            f3 = 0.0F;
        }
        if(f4 < 0.0F)
        {
            f4 = 0.0F;
        }
        tessellator.setColorOpaque_F(f2, f3, f4);
        int j1 = (i1 & 0xf) << 4;
        int k1 = i1 & 0xf0;
        double d = (float)j1 / 256F;
        double d2 = ((float)j1 + 15.99F) / 256F;
        double d4 = (float)k1 / 256F;
        double d6 = ((float)k1 + 15.99F) / 256F;
        boolean flag = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i - 1, j, k, 1) || !blockAccess.isBlockNormalCube(i - 1, j, k) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i - 1, j - 1, k, -1);
        boolean flag1 = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i + 1, j, k, 3) || !blockAccess.isBlockNormalCube(i + 1, j, k) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i + 1, j - 1, k, -1);
        boolean flag2 = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j, k - 1, 2) || !blockAccess.isBlockNormalCube(i, j, k - 1) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j - 1, k - 1, -1);
        boolean flag3 = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j, k + 1, 0) || !blockAccess.isBlockNormalCube(i, j, k + 1) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j - 1, k + 1, -1);
        if(!blockAccess.isBlockNormalCube(i, j + 1, k))
        {
            if(blockAccess.isBlockNormalCube(i - 1, j, k) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i - 1, j + 1, k, -1))
            {
                flag = true;
            }
            if(blockAccess.isBlockNormalCube(i + 1, j, k) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i + 1, j + 1, k, -1))
            {
                flag1 = true;
            }
            if(blockAccess.isBlockNormalCube(i, j, k - 1) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j + 1, k - 1, -1))
            {
                flag2 = true;
            }
            if(blockAccess.isBlockNormalCube(i, j, k + 1) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, i, j + 1, k + 1, -1))
            {
                flag3 = true;
            }
        }
        float f5 = i + 0;
        float f6 = i + 1;
        float f7 = k + 0;
        float f8 = k + 1;
        byte byte0 = 0;
        if((flag || flag1) && !flag2 && !flag3)
        {
            byte0 = 1;
        }
        if((flag2 || flag3) && !flag1 && !flag)
        {
            byte0 = 2;
        }
        if(byte0 != 0)
        {
            d = (float)(j1 + 16) / 256F;
            d2 = ((float)(j1 + 16) + 15.99F) / 256F;
            d4 = (float)k1 / 256F;
            d6 = ((float)k1 + 15.99F) / 256F;
        }
        if(byte0 == 0)
        {
            if(!flag)
            {
                f5 += 0.3125F;
            }
            if(!flag)
            {
                d += 0.01953125D;
            }
            if(!flag1)
            {
                f6 -= 0.3125F;
            }
            if(!flag1)
            {
                d2 -= 0.01953125D;
            }
            if(!flag2)
            {
                f7 += 0.3125F;
            }
            if(!flag2)
            {
                d4 += 0.01953125D;
            }
            if(!flag3)
            {
                f8 -= 0.3125F;
            }
            if(!flag3)
            {
                d6 -= 0.01953125D;
            }
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d2, d4);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d, d6);
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6 + 0.0625D);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d2, d4 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d, d6 + 0.0625D);
        } else
        if(byte0 == 1)
        {
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d2, d4);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d, d6);
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6 + 0.0625D);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d2, d4 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d, d6 + 0.0625D);
        } else
        if(byte0 == 2)
        {
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d, d6);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d2, d4);
            tessellator.setColorOpaque_F(f, f, f);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f8, d2, d6 + 0.0625D);
            tessellator.addVertexWithUV(f6, (double)j + 0.015625D, f7, d, d6 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f7, d, d4 + 0.0625D);
            tessellator.addVertexWithUV(f5, (double)j + 0.015625D, f8, d2, d4 + 0.0625D);
        }
        if(!blockAccess.isBlockNormalCube(i, j + 1, k))
        {
            double d1 = (float)(j1 + 16) / 256F;
            double d3 = ((float)(j1 + 16) + 15.99F) / 256F;
            double d5 = (float)k1 / 256F;
            double d7 = ((float)k1 + 15.99F) / 256F;
            if(blockAccess.isBlockNormalCube(i - 1, j, k) && blockAccess.getBlockId(i - 1, j + 1, k) == Block.redstoneWire.blockID)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)i + 0.015625D, (float)(j + 1) + 0.021875F, k + 1, d3, d5);
                tessellator.addVertexWithUV((double)i + 0.015625D, j + 0, k + 1, d1, d5);
                tessellator.addVertexWithUV((double)i + 0.015625D, j + 0, k + 0, d1, d7);
                tessellator.addVertexWithUV((double)i + 0.015625D, (float)(j + 1) + 0.021875F, k + 0, d3, d7);
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)i + 0.015625D, (float)(j + 1) + 0.021875F, k + 1, d3, d5 + 0.0625D);
                tessellator.addVertexWithUV((double)i + 0.015625D, j + 0, k + 1, d1, d5 + 0.0625D);
                tessellator.addVertexWithUV((double)i + 0.015625D, j + 0, k + 0, d1, d7 + 0.0625D);
                tessellator.addVertexWithUV((double)i + 0.015625D, (float)(j + 1) + 0.021875F, k + 0, d3, d7 + 0.0625D);
            }
            if(blockAccess.isBlockNormalCube(i + 1, j, k) && blockAccess.getBlockId(i + 1, j + 1, k) == Block.redstoneWire.blockID)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, j + 0, k + 1, d1, d7);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (float)(j + 1) + 0.021875F, k + 1, d3, d7);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (float)(j + 1) + 0.021875F, k + 0, d3, d5);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, j + 0, k + 0, d1, d5);
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, j + 0, k + 1, d1, d7 + 0.0625D);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (float)(j + 1) + 0.021875F, k + 1, d3, d7 + 0.0625D);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, (float)(j + 1) + 0.021875F, k + 0, d3, d5 + 0.0625D);
                tessellator.addVertexWithUV((double)(i + 1) - 0.015625D, j + 0, k + 0, d1, d5 + 0.0625D);
            }
            if(blockAccess.isBlockNormalCube(i, j, k - 1) && blockAccess.getBlockId(i, j + 1, k - 1) == Block.redstoneWire.blockID)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV(i + 1, j + 0, (double)k + 0.015625D, d1, d7);
                tessellator.addVertexWithUV(i + 1, (float)(j + 1) + 0.021875F, (double)k + 0.015625D, d3, d7);
                tessellator.addVertexWithUV(i + 0, (float)(j + 1) + 0.021875F, (double)k + 0.015625D, d3, d5);
                tessellator.addVertexWithUV(i + 0, j + 0, (double)k + 0.015625D, d1, d5);
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV(i + 1, j + 0, (double)k + 0.015625D, d1, d7 + 0.0625D);
                tessellator.addVertexWithUV(i + 1, (float)(j + 1) + 0.021875F, (double)k + 0.015625D, d3, d7 + 0.0625D);
                tessellator.addVertexWithUV(i + 0, (float)(j + 1) + 0.021875F, (double)k + 0.015625D, d3, d5 + 0.0625D);
                tessellator.addVertexWithUV(i + 0, j + 0, (double)k + 0.015625D, d1, d5 + 0.0625D);
            }
            if(blockAccess.isBlockNormalCube(i, j, k + 1) && blockAccess.getBlockId(i, j + 1, k + 1) == Block.redstoneWire.blockID)
            {
                tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
                tessellator.addVertexWithUV(i + 1, (float)(j + 1) + 0.021875F, (double)(k + 1) - 0.015625D, d3, d5);
                tessellator.addVertexWithUV(i + 1, j + 0, (double)(k + 1) - 0.015625D, d1, d5);
                tessellator.addVertexWithUV(i + 0, j + 0, (double)(k + 1) - 0.015625D, d1, d7);
                tessellator.addVertexWithUV(i + 0, (float)(j + 1) + 0.021875F, (double)(k + 1) - 0.015625D, d3, d7);
                tessellator.setColorOpaque_F(f, f, f);
                tessellator.addVertexWithUV(i + 1, (float)(j + 1) + 0.021875F, (double)(k + 1) - 0.015625D, d3, d5 + 0.0625D);
                tessellator.addVertexWithUV(i + 1, j + 0, (double)(k + 1) - 0.015625D, d1, d5 + 0.0625D);
                tessellator.addVertexWithUV(i + 0, j + 0, (double)(k + 1) - 0.015625D, d1, d7 + 0.0625D);
                tessellator.addVertexWithUV(i + 0, (float)(j + 1) + 0.021875F, (double)(k + 1) - 0.015625D, d3, d7 + 0.0625D);
            }
        }
        return true;
    }

    public boolean renderBlockMinecartTrack(BlockRail blockrail, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        int l = blockAccess.getBlockMetadata(i, j, k);
        int i1 = blockrail.getBlockTextureFromSideAndMetadata(0, l);
        if(overrideBlockTexture >= 0)
        {
            i1 = overrideBlockTexture;
        }
        if(blockrail.getIsPowered())
        {
            l &= 7;
        }
        tessellator.setBrightness(blockrail.getMixedBrightnessForBlock(blockAccess, i, j, k));
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        int j1 = (i1 & 0xf) << 4;
        int k1 = i1 & 0xf0;
        double d = (float)j1 / 256F;
        double d1 = ((float)j1 + 15.99F) / 256F;
        double d2 = (float)k1 / 256F;
        double d3 = ((float)k1 + 15.99F) / 256F;
        double d4 = 0.0625D;
        double d5 = i + 1;
        double d6 = i + 1;
        double d7 = i + 0;
        double d8 = i + 0;
        double d9 = k + 0;
        double d10 = k + 1;
        double d11 = k + 1;
        double d12 = k + 0;
        double d13 = (double)j + d4;
        double d14 = (double)j + d4;
        double d15 = (double)j + d4;
        double d16 = (double)j + d4;
        if(l == 1 || l == 2 || l == 3 || l == 7)
        {
            d5 = d8 = i + 1;
            d6 = d7 = i + 0;
            d9 = d10 = k + 1;
            d11 = d12 = k + 0;
        } else
        if(l == 8)
        {
            d5 = d6 = i + 0;
            d7 = d8 = i + 1;
            d9 = d12 = k + 1;
            d10 = d11 = k + 0;
        } else
        if(l == 9)
        {
            d5 = d8 = i + 0;
            d6 = d7 = i + 1;
            d9 = d10 = k + 0;
            d11 = d12 = k + 1;
        }
        if(l == 2 || l == 4)
        {
            d13++;
            d16++;
        } else
        if(l == 3 || l == 5)
        {
            d14++;
            d15++;
        }
        tessellator.addVertexWithUV(d5, d13, d9, d1, d2);
        tessellator.addVertexWithUV(d6, d14, d10, d1, d3);
        tessellator.addVertexWithUV(d7, d15, d11, d, d3);
        tessellator.addVertexWithUV(d8, d16, d12, d, d2);
        tessellator.addVertexWithUV(d8, d16, d12, d, d2);
        tessellator.addVertexWithUV(d7, d15, d11, d, d3);
        tessellator.addVertexWithUV(d6, d14, d10, d1, d3);
        tessellator.addVertexWithUV(d5, d13, d9, d1, d2);
        return true;
    }

    public boolean renderBlockLadder(Block block, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        int l = block.getBlockTextureFromSide(0);
        if(overrideBlockTexture >= 0)
        {
            l = overrideBlockTexture;
        }
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
        float f = 1.0F;
        tessellator.setColorOpaque_F(f, f, f);
        f = (l & 0xf) << 4;
        int i1 = l & 0xf0;
        double d = (float)f / 256F;
        double d1 = ((float)f + 15.99F) / 256F;
        double d2 = (float)i1 / 256F;
        double d3 = ((float)i1 + 15.99F) / 256F;
        int j1 = blockAccess.getBlockMetadata(i, j, k);
        double d4 = 0.0D;
        double d5 = 0.05000000074505806D;
        if(j1 == 5)
        {
            tessellator.addVertexWithUV((double)i + d5, (double)(j + 1) + d4, (double)(k + 1) + d4, d, d2);
            tessellator.addVertexWithUV((double)i + d5, (double)(j + 0) - d4, (double)(k + 1) + d4, d, d3);
            tessellator.addVertexWithUV((double)i + d5, (double)(j + 0) - d4, (double)(k + 0) - d4, d1, d3);
            tessellator.addVertexWithUV((double)i + d5, (double)(j + 1) + d4, (double)(k + 0) - d4, d1, d2);
        }
        if(j1 == 4)
        {
            tessellator.addVertexWithUV((double)(i + 1) - d5, (double)(j + 0) - d4, (double)(k + 1) + d4, d1, d3);
            tessellator.addVertexWithUV((double)(i + 1) - d5, (double)(j + 1) + d4, (double)(k + 1) + d4, d1, d2);
            tessellator.addVertexWithUV((double)(i + 1) - d5, (double)(j + 1) + d4, (double)(k + 0) - d4, d, d2);
            tessellator.addVertexWithUV((double)(i + 1) - d5, (double)(j + 0) - d4, (double)(k + 0) - d4, d, d3);
        }
        if(j1 == 3)
        {
            tessellator.addVertexWithUV((double)(i + 1) + d4, (double)(j + 0) - d4, (double)k + d5, d1, d3);
            tessellator.addVertexWithUV((double)(i + 1) + d4, (double)(j + 1) + d4, (double)k + d5, d1, d2);
            tessellator.addVertexWithUV((double)(i + 0) - d4, (double)(j + 1) + d4, (double)k + d5, d, d2);
            tessellator.addVertexWithUV((double)(i + 0) - d4, (double)(j + 0) - d4, (double)k + d5, d, d3);
        }
        if(j1 == 2)
        {
            tessellator.addVertexWithUV((double)(i + 1) + d4, (double)(j + 1) + d4, (double)(k + 1) - d5, d, d2);
            tessellator.addVertexWithUV((double)(i + 1) + d4, (double)(j + 0) - d4, (double)(k + 1) - d5, d, d3);
            tessellator.addVertexWithUV((double)(i + 0) - d4, (double)(j + 0) - d4, (double)(k + 1) - d5, d1, d3);
            tessellator.addVertexWithUV((double)(i + 0) - d4, (double)(j + 1) + d4, (double)(k + 1) - d5, d1, d2);
        }
        return true;
    }

    public boolean renderBlockVine(Block block, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        int l = block.getBlockTextureFromSide(0);
        if(overrideBlockTexture >= 0)
        {
            l = overrideBlockTexture;
        }
        float f = 1.0F;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
        int i1 = block.colorMultiplier(blockAccess, i, j, k);
        float f1 = (float)(i1 >> 16 & 0xff) / 255F;
        float da = (float)(i1 >> 8 & 0xff) / 255F;
        float f2 = (float)(i1 & 0xff) / 255F;
        tessellator.setColorOpaque_F(f * f1, f * da, f * f2);
        i1 = (l & 0xf) << 4;
        f1 = l & 0xf0;
        double d = (float)i1 / 256F;
        double d1 = ((float)i1 + 15.99F) / 256F;
        double d2 = (float)f1 / 256F;
        double d3 = ((float)f1 + 15.99F) / 256F;
        double d4 = 0.05000000074505806D;
        int j1 = blockAccess.getBlockMetadata(i, j, k);
        if((j1 & 2) != 0)
        {
            tessellator.addVertexWithUV((double)i + d4, j + 1, k + 1, d, d2);
            tessellator.addVertexWithUV((double)i + d4, j + 0, k + 1, d, d3);
            tessellator.addVertexWithUV((double)i + d4, j + 0, k + 0, d1, d3);
            tessellator.addVertexWithUV((double)i + d4, j + 1, k + 0, d1, d2);
            tessellator.addVertexWithUV((double)i + d4, j + 1, k + 0, d1, d2);
            tessellator.addVertexWithUV((double)i + d4, j + 0, k + 0, d1, d3);
            tessellator.addVertexWithUV((double)i + d4, j + 0, k + 1, d, d3);
            tessellator.addVertexWithUV((double)i + d4, j + 1, k + 1, d, d2);
        }
        if((j1 & 8) != 0)
        {
            tessellator.addVertexWithUV((double)(i + 1) - d4, j + 0, k + 1, d1, d3);
            tessellator.addVertexWithUV((double)(i + 1) - d4, j + 1, k + 1, d1, d2);
            tessellator.addVertexWithUV((double)(i + 1) - d4, j + 1, k + 0, d, d2);
            tessellator.addVertexWithUV((double)(i + 1) - d4, j + 0, k + 0, d, d3);
            tessellator.addVertexWithUV((double)(i + 1) - d4, j + 0, k + 0, d, d3);
            tessellator.addVertexWithUV((double)(i + 1) - d4, j + 1, k + 0, d, d2);
            tessellator.addVertexWithUV((double)(i + 1) - d4, j + 1, k + 1, d1, d2);
            tessellator.addVertexWithUV((double)(i + 1) - d4, j + 0, k + 1, d1, d3);
        }
        if((j1 & 4) != 0)
        {
            tessellator.addVertexWithUV(i + 1, j + 0, (double)k + d4, d1, d3);
            tessellator.addVertexWithUV(i + 1, j + 1, (double)k + d4, d1, d2);
            tessellator.addVertexWithUV(i + 0, j + 1, (double)k + d4, d, d2);
            tessellator.addVertexWithUV(i + 0, j + 0, (double)k + d4, d, d3);
            tessellator.addVertexWithUV(i + 0, j + 0, (double)k + d4, d, d3);
            tessellator.addVertexWithUV(i + 0, j + 1, (double)k + d4, d, d2);
            tessellator.addVertexWithUV(i + 1, j + 1, (double)k + d4, d1, d2);
            tessellator.addVertexWithUV(i + 1, j + 0, (double)k + d4, d1, d3);
        }
        if((j1 & 1) != 0)
        {
            tessellator.addVertexWithUV(i + 1, j + 1, (double)(k + 1) - d4, d, d2);
            tessellator.addVertexWithUV(i + 1, j + 0, (double)(k + 1) - d4, d, d3);
            tessellator.addVertexWithUV(i + 0, j + 0, (double)(k + 1) - d4, d1, d3);
            tessellator.addVertexWithUV(i + 0, j + 1, (double)(k + 1) - d4, d1, d2);
            tessellator.addVertexWithUV(i + 0, j + 1, (double)(k + 1) - d4, d1, d2);
            tessellator.addVertexWithUV(i + 0, j + 0, (double)(k + 1) - d4, d1, d3);
            tessellator.addVertexWithUV(i + 1, j + 0, (double)(k + 1) - d4, d, d3);
            tessellator.addVertexWithUV(i + 1, j + 1, (double)(k + 1) - d4, d, d2);
        }
        if(blockAccess.isBlockNormalCube(i, j + 1, k))
        {
            tessellator.addVertexWithUV(i + 1, (double)(j + 1) - d4, k + 0, d, d2);
            tessellator.addVertexWithUV(i + 1, (double)(j + 1) - d4, k + 1, d, d3);
            tessellator.addVertexWithUV(i + 0, (double)(j + 1) - d4, k + 1, d1, d3);
            tessellator.addVertexWithUV(i + 0, (double)(j + 1) - d4, k + 0, d1, d2);
        }
        return true;
    }

    public boolean renderBlockPane(BlockPane blockpane, int i, int j, int k)
    {
        int l = blockAccess.func_35452_b();
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(blockpane.getMixedBrightnessForBlock(blockAccess, i, j, k));
        float f = 1.0F;
        int i1 = blockpane.colorMultiplier(blockAccess, i, j, k);
        float f1 = (float)(i1 >> 16 & 0xff) / 255F;
        float f2 = (float)(i1 >> 8 & 0xff) / 255F;
        float f3 = (float)(i1 & 0xff) / 255F;
        if(EntityRenderer.anaglyphEnable)
        {
            float f4 = (f1 * 30F + f2 * 59F + f3 * 11F) / 100F;
            float f5 = (f1 * 30F + f2 * 70F) / 100F;
            float f6 = (f1 * 30F + f3 * 70F) / 100F;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }
        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        int j1 = 0;
        int k1 = 0;
        if(overrideBlockTexture >= 0)
        {
            j1 = overrideBlockTexture;
            k1 = overrideBlockTexture;
        } else
        {
            int l1 = blockAccess.getBlockMetadata(i, j, k);
            j1 = blockpane.getBlockTextureFromSideAndMetadata(0, l1);
            k1 = blockpane.func_35299_s();
        }
        int i2 = (j1 & 0xf) << 4;
        int j2 = j1 & 0xf0;
        double d = (float)i2 / 256F;
        double d1 = ((float)i2 + 7.99F) / 256F;
        double d2 = ((float)i2 + 15.99F) / 256F;
        double d3 = (float)j2 / 256F;
        double d4 = ((float)j2 + 15.99F) / 256F;
        int k2 = (k1 & 0xf) << 4;
        int l2 = k1 & 0xf0;
        double d5 = (float)(k2 + 7) / 256F;
        double d6 = ((float)k2 + 8.99F) / 256F;
        double d7 = (float)l2 / 256F;
        double d8 = (float)(l2 + 8) / 256F;
        double d9 = ((float)l2 + 15.99F) / 256F;
        double d10 = i;
        double d11 = (double)i + 0.5D;
        double d12 = i + 1;
        double d13 = k;
        double d14 = (double)k + 0.5D;
        double d15 = k + 1;
        double d16 = ((double)i + 0.5D) - 0.0625D;
        double d17 = (double)i + 0.5D + 0.0625D;
        double d18 = ((double)k + 0.5D) - 0.0625D;
        double d19 = (double)k + 0.5D + 0.0625D;
        boolean flag = blockpane.func_35298_d(blockAccess.getBlockId(i, j, k - 1));
        boolean flag1 = blockpane.func_35298_d(blockAccess.getBlockId(i, j, k + 1));
        boolean flag2 = blockpane.func_35298_d(blockAccess.getBlockId(i - 1, j, k));
        boolean flag3 = blockpane.func_35298_d(blockAccess.getBlockId(i + 1, j, k));
        boolean flag4 = blockpane.shouldSideBeRendered(blockAccess, i, j + 1, k, 1);
        boolean flag5 = blockpane.shouldSideBeRendered(blockAccess, i, j - 1, k, 0);
        if(flag2 && flag3 || !flag2 && !flag3 && !flag && !flag1)
        {
            tessellator.addVertexWithUV(d10, j + 1, d14, d, d3);
            tessellator.addVertexWithUV(d10, j + 0, d14, d, d4);
            tessellator.addVertexWithUV(d12, j + 0, d14, d2, d4);
            tessellator.addVertexWithUV(d12, j + 1, d14, d2, d3);
            tessellator.addVertexWithUV(d12, j + 1, d14, d, d3);
            tessellator.addVertexWithUV(d12, j + 0, d14, d, d4);
            tessellator.addVertexWithUV(d10, j + 0, d14, d2, d4);
            tessellator.addVertexWithUV(d10, j + 1, d14, d2, d3);
            if(flag4)
            {
                tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d19, d6, d9);
                tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d19, d6, d7);
                tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d18, d5, d7);
                tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d18, d5, d9);
                tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d19, d6, d9);
                tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d19, d6, d7);
                tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d18, d5, d7);
                tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d18, d5, d9);
            } else
            {
                if(j < l - 1 && blockAccess.isAirBlock(i - 1, j + 1, k))
                {
                    tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d19, d6, d8);
                    tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d19, d6, d9);
                    tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d18, d5, d9);
                    tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d19, d6, d8);
                    tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d19, d6, d9);
                    tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d18, d5, d9);
                    tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d18, d5, d8);
                }
                if(j < l - 1 && blockAccess.isAirBlock(i + 1, j + 1, k))
                {
                    tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d19, d6, d7);
                    tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d19, d6, d8);
                    tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d19, d6, d7);
                    tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d19, d6, d8);
                    tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d18, d5, d7);
                }
            }
            if(flag5)
            {
                tessellator.addVertexWithUV(d10, (double)j - 0.01D, d19, d6, d9);
                tessellator.addVertexWithUV(d12, (double)j - 0.01D, d19, d6, d7);
                tessellator.addVertexWithUV(d12, (double)j - 0.01D, d18, d5, d7);
                tessellator.addVertexWithUV(d10, (double)j - 0.01D, d18, d5, d9);
                tessellator.addVertexWithUV(d12, (double)j - 0.01D, d19, d6, d9);
                tessellator.addVertexWithUV(d10, (double)j - 0.01D, d19, d6, d7);
                tessellator.addVertexWithUV(d10, (double)j - 0.01D, d18, d5, d7);
                tessellator.addVertexWithUV(d12, (double)j - 0.01D, d18, d5, d9);
            } else
            {
                if(j > 1 && blockAccess.isAirBlock(i - 1, j - 1, k))
                {
                    tessellator.addVertexWithUV(d10, (double)j - 0.01D, d19, d6, d8);
                    tessellator.addVertexWithUV(d11, (double)j - 0.01D, d19, d6, d9);
                    tessellator.addVertexWithUV(d11, (double)j - 0.01D, d18, d5, d9);
                    tessellator.addVertexWithUV(d10, (double)j - 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d11, (double)j - 0.01D, d19, d6, d8);
                    tessellator.addVertexWithUV(d10, (double)j - 0.01D, d19, d6, d9);
                    tessellator.addVertexWithUV(d10, (double)j - 0.01D, d18, d5, d9);
                    tessellator.addVertexWithUV(d11, (double)j - 0.01D, d18, d5, d8);
                }
                if(j > 1 && blockAccess.isAirBlock(i + 1, j - 1, k))
                {
                    tessellator.addVertexWithUV(d11, (double)j - 0.01D, d19, d6, d7);
                    tessellator.addVertexWithUV(d12, (double)j - 0.01D, d19, d6, d8);
                    tessellator.addVertexWithUV(d12, (double)j - 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d11, (double)j - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d12, (double)j - 0.01D, d19, d6, d7);
                    tessellator.addVertexWithUV(d11, (double)j - 0.01D, d19, d6, d8);
                    tessellator.addVertexWithUV(d11, (double)j - 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d12, (double)j - 0.01D, d18, d5, d7);
                }
            }
        } else
        if(flag2 && !flag3)
        {
            tessellator.addVertexWithUV(d10, j + 1, d14, d, d3);
            tessellator.addVertexWithUV(d10, j + 0, d14, d, d4);
            tessellator.addVertexWithUV(d11, j + 0, d14, d1, d4);
            tessellator.addVertexWithUV(d11, j + 1, d14, d1, d3);
            tessellator.addVertexWithUV(d11, j + 1, d14, d, d3);
            tessellator.addVertexWithUV(d11, j + 0, d14, d, d4);
            tessellator.addVertexWithUV(d10, j + 0, d14, d1, d4);
            tessellator.addVertexWithUV(d10, j + 1, d14, d1, d3);
            if(!flag1 && !flag)
            {
                tessellator.addVertexWithUV(d11, j + 1, d19, d5, d7);
                tessellator.addVertexWithUV(d11, j + 0, d19, d5, d9);
                tessellator.addVertexWithUV(d11, j + 0, d18, d6, d9);
                tessellator.addVertexWithUV(d11, j + 1, d18, d6, d7);
                tessellator.addVertexWithUV(d11, j + 1, d18, d5, d7);
                tessellator.addVertexWithUV(d11, j + 0, d18, d5, d9);
                tessellator.addVertexWithUV(d11, j + 0, d19, d6, d9);
                tessellator.addVertexWithUV(d11, j + 1, d19, d6, d7);
            }
            if(flag4 || j < l - 1 && blockAccess.isAirBlock(i - 1, j + 1, k))
            {
                tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d19, d6, d8);
                tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d19, d6, d9);
                tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d18, d5, d9);
                tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d18, d5, d8);
                tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d19, d6, d8);
                tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d19, d6, d9);
                tessellator.addVertexWithUV(d10, (double)(j + 1) + 0.01D, d18, d5, d9);
                tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d18, d5, d8);
            }
            if(flag5 || j > 1 && blockAccess.isAirBlock(i - 1, j - 1, k))
            {
                tessellator.addVertexWithUV(d10, (double)j - 0.01D, d19, d6, d8);
                tessellator.addVertexWithUV(d11, (double)j - 0.01D, d19, d6, d9);
                tessellator.addVertexWithUV(d11, (double)j - 0.01D, d18, d5, d9);
                tessellator.addVertexWithUV(d10, (double)j - 0.01D, d18, d5, d8);
                tessellator.addVertexWithUV(d11, (double)j - 0.01D, d19, d6, d8);
                tessellator.addVertexWithUV(d10, (double)j - 0.01D, d19, d6, d9);
                tessellator.addVertexWithUV(d10, (double)j - 0.01D, d18, d5, d9);
                tessellator.addVertexWithUV(d11, (double)j - 0.01D, d18, d5, d8);
            }
        } else
        if(!flag2 && flag3)
        {
            tessellator.addVertexWithUV(d11, j + 1, d14, d1, d3);
            tessellator.addVertexWithUV(d11, j + 0, d14, d1, d4);
            tessellator.addVertexWithUV(d12, j + 0, d14, d2, d4);
            tessellator.addVertexWithUV(d12, j + 1, d14, d2, d3);
            tessellator.addVertexWithUV(d12, j + 1, d14, d1, d3);
            tessellator.addVertexWithUV(d12, j + 0, d14, d1, d4);
            tessellator.addVertexWithUV(d11, j + 0, d14, d2, d4);
            tessellator.addVertexWithUV(d11, j + 1, d14, d2, d3);
            if(!flag1 && !flag)
            {
                tessellator.addVertexWithUV(d11, j + 1, d18, d5, d7);
                tessellator.addVertexWithUV(d11, j + 0, d18, d5, d9);
                tessellator.addVertexWithUV(d11, j + 0, d19, d6, d9);
                tessellator.addVertexWithUV(d11, j + 1, d19, d6, d7);
                tessellator.addVertexWithUV(d11, j + 1, d19, d5, d7);
                tessellator.addVertexWithUV(d11, j + 0, d19, d5, d9);
                tessellator.addVertexWithUV(d11, j + 0, d18, d6, d9);
                tessellator.addVertexWithUV(d11, j + 1, d18, d6, d7);
            }
            if(flag4 || j < l - 1 && blockAccess.isAirBlock(i + 1, j + 1, k))
            {
                tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d19, d6, d7);
                tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d19, d6, d8);
                tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d18, d5, d8);
                tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d18, d5, d7);
                tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d19, d6, d7);
                tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d19, d6, d8);
                tessellator.addVertexWithUV(d11, (double)(j + 1) + 0.01D, d18, d5, d8);
                tessellator.addVertexWithUV(d12, (double)(j + 1) + 0.01D, d18, d5, d7);
            }
            if(flag5 || j > 1 && blockAccess.isAirBlock(i + 1, j - 1, k))
            {
                tessellator.addVertexWithUV(d11, (double)j - 0.01D, d19, d6, d7);
                tessellator.addVertexWithUV(d12, (double)j - 0.01D, d19, d6, d8);
                tessellator.addVertexWithUV(d12, (double)j - 0.01D, d18, d5, d8);
                tessellator.addVertexWithUV(d11, (double)j - 0.01D, d18, d5, d7);
                tessellator.addVertexWithUV(d12, (double)j - 0.01D, d19, d6, d7);
                tessellator.addVertexWithUV(d11, (double)j - 0.01D, d19, d6, d8);
                tessellator.addVertexWithUV(d11, (double)j - 0.01D, d18, d5, d8);
                tessellator.addVertexWithUV(d12, (double)j - 0.01D, d18, d5, d7);
            }
        }
        if(flag && flag1 || !flag2 && !flag3 && !flag && !flag1)
        {
            tessellator.addVertexWithUV(d11, j + 1, d15, d, d3);
            tessellator.addVertexWithUV(d11, j + 0, d15, d, d4);
            tessellator.addVertexWithUV(d11, j + 0, d13, d2, d4);
            tessellator.addVertexWithUV(d11, j + 1, d13, d2, d3);
            tessellator.addVertexWithUV(d11, j + 1, d13, d, d3);
            tessellator.addVertexWithUV(d11, j + 0, d13, d, d4);
            tessellator.addVertexWithUV(d11, j + 0, d15, d2, d4);
            tessellator.addVertexWithUV(d11, j + 1, d15, d2, d3);
            if(flag4)
            {
                tessellator.addVertexWithUV(d17, j + 1, d15, d6, d9);
                tessellator.addVertexWithUV(d17, j + 1, d13, d6, d7);
                tessellator.addVertexWithUV(d16, j + 1, d13, d5, d7);
                tessellator.addVertexWithUV(d16, j + 1, d15, d5, d9);
                tessellator.addVertexWithUV(d17, j + 1, d13, d6, d9);
                tessellator.addVertexWithUV(d17, j + 1, d15, d6, d7);
                tessellator.addVertexWithUV(d16, j + 1, d15, d5, d7);
                tessellator.addVertexWithUV(d16, j + 1, d13, d5, d9);
            } else
            {
                if(j < l - 1 && blockAccess.isAirBlock(i, j + 1, k - 1))
                {
                    tessellator.addVertexWithUV(d16, j + 1, d13, d6, d7);
                    tessellator.addVertexWithUV(d16, j + 1, d14, d6, d8);
                    tessellator.addVertexWithUV(d17, j + 1, d14, d5, d8);
                    tessellator.addVertexWithUV(d17, j + 1, d13, d5, d7);
                    tessellator.addVertexWithUV(d16, j + 1, d14, d6, d7);
                    tessellator.addVertexWithUV(d16, j + 1, d13, d6, d8);
                    tessellator.addVertexWithUV(d17, j + 1, d13, d5, d8);
                    tessellator.addVertexWithUV(d17, j + 1, d14, d5, d7);
                }
                if(j < l - 1 && blockAccess.isAirBlock(i, j + 1, k + 1))
                {
                    tessellator.addVertexWithUV(d16, j + 1, d14, d5, d8);
                    tessellator.addVertexWithUV(d16, j + 1, d15, d5, d9);
                    tessellator.addVertexWithUV(d17, j + 1, d15, d6, d9);
                    tessellator.addVertexWithUV(d17, j + 1, d14, d6, d8);
                    tessellator.addVertexWithUV(d16, j + 1, d15, d5, d8);
                    tessellator.addVertexWithUV(d16, j + 1, d14, d5, d9);
                    tessellator.addVertexWithUV(d17, j + 1, d14, d6, d9);
                    tessellator.addVertexWithUV(d17, j + 1, d15, d6, d8);
                }
            }
            if(flag5)
            {
                tessellator.addVertexWithUV(d17, j, d15, d6, d9);
                tessellator.addVertexWithUV(d17, j, d13, d6, d7);
                tessellator.addVertexWithUV(d16, j, d13, d5, d7);
                tessellator.addVertexWithUV(d16, j, d15, d5, d9);
                tessellator.addVertexWithUV(d17, j, d13, d6, d9);
                tessellator.addVertexWithUV(d17, j, d15, d6, d7);
                tessellator.addVertexWithUV(d16, j, d15, d5, d7);
                tessellator.addVertexWithUV(d16, j, d13, d5, d9);
            } else
            {
                if(j > 1 && blockAccess.isAirBlock(i, j - 1, k - 1))
                {
                    tessellator.addVertexWithUV(d16, j, d13, d6, d7);
                    tessellator.addVertexWithUV(d16, j, d14, d6, d8);
                    tessellator.addVertexWithUV(d17, j, d14, d5, d8);
                    tessellator.addVertexWithUV(d17, j, d13, d5, d7);
                    tessellator.addVertexWithUV(d16, j, d14, d6, d7);
                    tessellator.addVertexWithUV(d16, j, d13, d6, d8);
                    tessellator.addVertexWithUV(d17, j, d13, d5, d8);
                    tessellator.addVertexWithUV(d17, j, d14, d5, d7);
                }
                if(j > 1 && blockAccess.isAirBlock(i, j - 1, k + 1))
                {
                    tessellator.addVertexWithUV(d16, j, d14, d5, d8);
                    tessellator.addVertexWithUV(d16, j, d15, d5, d9);
                    tessellator.addVertexWithUV(d17, j, d15, d6, d9);
                    tessellator.addVertexWithUV(d17, j, d14, d6, d8);
                    tessellator.addVertexWithUV(d16, j, d15, d5, d8);
                    tessellator.addVertexWithUV(d16, j, d14, d5, d9);
                    tessellator.addVertexWithUV(d17, j, d14, d6, d9);
                    tessellator.addVertexWithUV(d17, j, d15, d6, d8);
                }
            }
        } else
        if(flag && !flag1)
        {
            tessellator.addVertexWithUV(d11, j + 1, d13, d, d3);
            tessellator.addVertexWithUV(d11, j + 0, d13, d, d4);
            tessellator.addVertexWithUV(d11, j + 0, d14, d1, d4);
            tessellator.addVertexWithUV(d11, j + 1, d14, d1, d3);
            tessellator.addVertexWithUV(d11, j + 1, d14, d, d3);
            tessellator.addVertexWithUV(d11, j + 0, d14, d, d4);
            tessellator.addVertexWithUV(d11, j + 0, d13, d1, d4);
            tessellator.addVertexWithUV(d11, j + 1, d13, d1, d3);
            if(!flag3 && !flag2)
            {
                tessellator.addVertexWithUV(d16, j + 1, d14, d5, d7);
                tessellator.addVertexWithUV(d16, j + 0, d14, d5, d9);
                tessellator.addVertexWithUV(d17, j + 0, d14, d6, d9);
                tessellator.addVertexWithUV(d17, j + 1, d14, d6, d7);
                tessellator.addVertexWithUV(d17, j + 1, d14, d5, d7);
                tessellator.addVertexWithUV(d17, j + 0, d14, d5, d9);
                tessellator.addVertexWithUV(d16, j + 0, d14, d6, d9);
                tessellator.addVertexWithUV(d16, j + 1, d14, d6, d7);
            }
            if(flag4 || j < l - 1 && blockAccess.isAirBlock(i, j + 1, k - 1))
            {
                tessellator.addVertexWithUV(d16, j + 1, d13, d6, d7);
                tessellator.addVertexWithUV(d16, j + 1, d14, d6, d8);
                tessellator.addVertexWithUV(d17, j + 1, d14, d5, d8);
                tessellator.addVertexWithUV(d17, j + 1, d13, d5, d7);
                tessellator.addVertexWithUV(d16, j + 1, d14, d6, d7);
                tessellator.addVertexWithUV(d16, j + 1, d13, d6, d8);
                tessellator.addVertexWithUV(d17, j + 1, d13, d5, d8);
                tessellator.addVertexWithUV(d17, j + 1, d14, d5, d7);
            }
            if(flag5 || j > 1 && blockAccess.isAirBlock(i, j - 1, k - 1))
            {
                tessellator.addVertexWithUV(d16, j, d13, d6, d7);
                tessellator.addVertexWithUV(d16, j, d14, d6, d8);
                tessellator.addVertexWithUV(d17, j, d14, d5, d8);
                tessellator.addVertexWithUV(d17, j, d13, d5, d7);
                tessellator.addVertexWithUV(d16, j, d14, d6, d7);
                tessellator.addVertexWithUV(d16, j, d13, d6, d8);
                tessellator.addVertexWithUV(d17, j, d13, d5, d8);
                tessellator.addVertexWithUV(d17, j, d14, d5, d7);
            }
        } else
        if(!flag && flag1)
        {
            tessellator.addVertexWithUV(d11, j + 1, d14, d1, d3);
            tessellator.addVertexWithUV(d11, j + 0, d14, d1, d4);
            tessellator.addVertexWithUV(d11, j + 0, d15, d2, d4);
            tessellator.addVertexWithUV(d11, j + 1, d15, d2, d3);
            tessellator.addVertexWithUV(d11, j + 1, d15, d1, d3);
            tessellator.addVertexWithUV(d11, j + 0, d15, d1, d4);
            tessellator.addVertexWithUV(d11, j + 0, d14, d2, d4);
            tessellator.addVertexWithUV(d11, j + 1, d14, d2, d3);
            if(!flag3 && !flag2)
            {
                tessellator.addVertexWithUV(d17, j + 1, d14, d5, d7);
                tessellator.addVertexWithUV(d17, j + 0, d14, d5, d9);
                tessellator.addVertexWithUV(d16, j + 0, d14, d6, d9);
                tessellator.addVertexWithUV(d16, j + 1, d14, d6, d7);
                tessellator.addVertexWithUV(d16, j + 1, d14, d5, d7);
                tessellator.addVertexWithUV(d16, j + 0, d14, d5, d9);
                tessellator.addVertexWithUV(d17, j + 0, d14, d6, d9);
                tessellator.addVertexWithUV(d17, j + 1, d14, d6, d7);
            }
            if(flag4 || j < l - 1 && blockAccess.isAirBlock(i, j + 1, k + 1))
            {
                tessellator.addVertexWithUV(d16, j + 1, d14, d5, d8);
                tessellator.addVertexWithUV(d16, j + 1, d15, d5, d9);
                tessellator.addVertexWithUV(d17, j + 1, d15, d6, d9);
                tessellator.addVertexWithUV(d17, j + 1, d14, d6, d8);
                tessellator.addVertexWithUV(d16, j + 1, d15, d5, d8);
                tessellator.addVertexWithUV(d16, j + 1, d14, d5, d9);
                tessellator.addVertexWithUV(d17, j + 1, d14, d6, d9);
                tessellator.addVertexWithUV(d17, j + 1, d15, d6, d8);
            }
            if(flag5 || j > 1 && blockAccess.isAirBlock(i, j - 1, k + 1))
            {
                tessellator.addVertexWithUV(d16, j, d14, d5, d8);
                tessellator.addVertexWithUV(d16, j, d15, d5, d9);
                tessellator.addVertexWithUV(d17, j, d15, d6, d9);
                tessellator.addVertexWithUV(d17, j, d14, d6, d8);
                tessellator.addVertexWithUV(d16, j, d15, d5, d8);
                tessellator.addVertexWithUV(d16, j, d14, d5, d9);
                tessellator.addVertexWithUV(d17, j, d14, d6, d9);
                tessellator.addVertexWithUV(d17, j, d15, d6, d8);
            }
        }
        return true;
    }

    public boolean renderBlockReed(Block block, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
        float f = 1.0F;
        int l = block.colorMultiplier(blockAccess, i, j, k);
        float f1 = (float)(l >> 16 & 0xff) / 255F;
        float f2 = (float)(l >> 8 & 0xff) / 255F;
        float f3 = (float)(l & 0xff) / 255F;
        if(EntityRenderer.anaglyphEnable)
        {
            float f4 = (f1 * 30F + f2 * 59F + f3 * 11F) / 100F;
            float f5 = (f1 * 30F + f2 * 70F) / 100F;
            float f6 = (f1 * 30F + f3 * 70F) / 100F;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }
        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        double d = i;
        double d1 = j;
        double d2 = k;
        if(block == Block.tallGrass)
        {
            long l1 = (long)(i * 0x2fc20f) ^ (long)k * 0x6ebfff5L ^ (long)j;
            l1 = l1 * l1 * 0x285b825L + l1 * 11L;
            d += ((double)((float)(l1 >> 16 & 15L) / 15F) - 0.5D) * 0.5D;
            d1 += ((double)((float)(l1 >> 20 & 15L) / 15F) - 1.0D) * 0.20000000000000001D;
            d2 += ((double)((float)(l1 >> 24 & 15L) / 15F) - 0.5D) * 0.5D;
        }
        renderCrossedSquares(block, blockAccess.getBlockMetadata(i, j, k), d, d1, d2);
        return true;
    }

    public boolean renderBlockStem(Block block, int i, int j, int k)
    {
        BlockStem blockstem = (BlockStem)block;
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(blockstem.getMixedBrightnessForBlock(blockAccess, i, j, k));
        float f = 1.0F;
        int l = blockstem.colorMultiplier(blockAccess, i, j, k);
        float f1 = (float)(l >> 16 & 0xff) / 255F;
        float f2 = (float)(l >> 8 & 0xff) / 255F;
        float f3 = (float)(l & 0xff) / 255F;
        if(EntityRenderer.anaglyphEnable)
        {
            float f4 = (f1 * 30F + f2 * 59F + f3 * 11F) / 100F;
            float f5 = (f1 * 30F + f2 * 70F) / 100F;
            float f6 = (f1 * 30F + f3 * 70F) / 100F;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }
        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        blockstem.setBlockBoundsBasedOnState(blockAccess, i, j, k);
        int i1 = blockstem.func_35296_f(blockAccess, i, j, k);
        if(i1 < 0)
        {
            renderBlockStemSmall(blockstem, blockAccess.getBlockMetadata(i, j, k), blockstem.maxY, i, j, k);
        } else
        {
            renderBlockStemSmall(blockstem, blockAccess.getBlockMetadata(i, j, k), 0.5D, i, j, k);
            renderBlockStemBig(blockstem, blockAccess.getBlockMetadata(i, j, k), i1, blockstem.maxY, i, j, k);
        }
        return true;
    }

    public boolean renderBlockCrops(Block block, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        renderBlockCropsImpl(block, blockAccess.getBlockMetadata(i, j, k), i, (float)j - 0.0625F, k);
        return true;
    }

    public void renderTorchAtAngle(Block block, double d, double d1, double d2, 
            double d3, double d4)
    {
        Tessellator tessellator = Tessellator.instance;
        int i = block.getBlockTextureFromSide(0);
        if(overrideBlockTexture >= 0)
        {
            i = overrideBlockTexture;
        }
        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        float f = (float)j / 256F;
        float f1 = ((float)j + 15.99F) / 256F;
        float f2 = (float)k / 256F;
        float f3 = ((float)k + 15.99F) / 256F;
        double d5 = (double)f + 0.02734375D;
        double d6 = (double)f2 + 0.0234375D;
        double d7 = (double)f + 0.03515625D;
        double d8 = (double)f2 + 0.03125D;
        d += 0.5D;
        d2 += 0.5D;
        double d9 = d - 0.5D;
        double d10 = d + 0.5D;
        double d11 = d2 - 0.5D;
        double d12 = d2 + 0.5D;
        double d13 = 0.0625D;
        double d14 = 0.625D;
        tessellator.addVertexWithUV((d + d3 * (1.0D - d14)) - d13, d1 + d14, (d2 + d4 * (1.0D - d14)) - d13, d5, d6);
        tessellator.addVertexWithUV((d + d3 * (1.0D - d14)) - d13, d1 + d14, d2 + d4 * (1.0D - d14) + d13, d5, d8);
        tessellator.addVertexWithUV(d + d3 * (1.0D - d14) + d13, d1 + d14, d2 + d4 * (1.0D - d14) + d13, d7, d8);
        tessellator.addVertexWithUV(d + d3 * (1.0D - d14) + d13, d1 + d14, (d2 + d4 * (1.0D - d14)) - d13, d7, d6);
        tessellator.addVertexWithUV(d - d13, d1 + 1.0D, d11, f, f2);
        tessellator.addVertexWithUV((d - d13) + d3, d1 + 0.0D, d11 + d4, f, f3);
        tessellator.addVertexWithUV((d - d13) + d3, d1 + 0.0D, d12 + d4, f1, f3);
        tessellator.addVertexWithUV(d - d13, d1 + 1.0D, d12, f1, f2);
        tessellator.addVertexWithUV(d + d13, d1 + 1.0D, d12, f, f2);
        tessellator.addVertexWithUV(d + d3 + d13, d1 + 0.0D, d12 + d4, f, f3);
        tessellator.addVertexWithUV(d + d3 + d13, d1 + 0.0D, d11 + d4, f1, f3);
        tessellator.addVertexWithUV(d + d13, d1 + 1.0D, d11, f1, f2);
        tessellator.addVertexWithUV(d9, d1 + 1.0D, d2 + d13, f, f2);
        tessellator.addVertexWithUV(d9 + d3, d1 + 0.0D, d2 + d13 + d4, f, f3);
        tessellator.addVertexWithUV(d10 + d3, d1 + 0.0D, d2 + d13 + d4, f1, f3);
        tessellator.addVertexWithUV(d10, d1 + 1.0D, d2 + d13, f1, f2);
        tessellator.addVertexWithUV(d10, d1 + 1.0D, d2 - d13, f, f2);
        tessellator.addVertexWithUV(d10 + d3, d1 + 0.0D, (d2 - d13) + d4, f, f3);
        tessellator.addVertexWithUV(d9 + d3, d1 + 0.0D, (d2 - d13) + d4, f1, f3);
        tessellator.addVertexWithUV(d9, d1 + 1.0D, d2 - d13, f1, f2);
    }

    public void renderCrossedSquares(Block block, int i, double d, double d1, double d2)
    {
        Tessellator tessellator = Tessellator.instance;
        int j = block.getBlockTextureFromSideAndMetadata(0, i);
        if(overrideBlockTexture >= 0)
        {
            j = overrideBlockTexture;
        }
        int k = (j & 0xf) << 4;
        int l = j & 0xf0;
        double d3 = (float)k / 256F;
        double d4 = ((float)k + 15.99F) / 256F;
        double d5 = (float)l / 256F;
        double d6 = ((float)l + 15.99F) / 256F;
        double d7 = (d + 0.5D) - 0.45000000000000001D;
        double d8 = d + 0.5D + 0.45000000000000001D;
        double d9 = (d2 + 0.5D) - 0.45000000000000001D;
        double d10 = d2 + 0.5D + 0.45000000000000001D;
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d3, d5);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d3, d6);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d4, d6);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d4, d5);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d3, d5);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d4, d6);
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d4, d5);
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d3, d5);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d4, d6);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d4, d5);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d3, d5);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d3, d6);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d4, d6);
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d4, d5);
    }

    public void renderBlockStemSmall(Block block, int i, double d, double d1, double d2, double d3)
    {
        Tessellator tessellator = Tessellator.instance;
        int j = block.getBlockTextureFromSideAndMetadata(0, i);
        if(overrideBlockTexture >= 0)
        {
            j = overrideBlockTexture;
        }
        int k = (j & 0xf) << 4;
        int l = j & 0xf0;
        double d4 = (float)k / 256F;
        double d5 = ((float)k + 15.99F) / 256F;
        double d6 = (float)l / 256F;
        double d7 = ((double)l + 15.989999771118164D * d) / 256D;
        double d8 = (d1 + 0.5D) - 0.44999998807907104D;
        double d9 = d1 + 0.5D + 0.44999998807907104D;
        double d10 = (d3 + 0.5D) - 0.44999998807907104D;
        double d11 = d3 + 0.5D + 0.44999998807907104D;
        tessellator.addVertexWithUV(d8, d2 + d, d10, d4, d6);
        tessellator.addVertexWithUV(d8, d2 + 0.0D, d10, d4, d7);
        tessellator.addVertexWithUV(d9, d2 + 0.0D, d11, d5, d7);
        tessellator.addVertexWithUV(d9, d2 + d, d11, d5, d6);
        tessellator.addVertexWithUV(d9, d2 + d, d11, d4, d6);
        tessellator.addVertexWithUV(d9, d2 + 0.0D, d11, d4, d7);
        tessellator.addVertexWithUV(d8, d2 + 0.0D, d10, d5, d7);
        tessellator.addVertexWithUV(d8, d2 + d, d10, d5, d6);
        tessellator.addVertexWithUV(d8, d2 + d, d11, d4, d6);
        tessellator.addVertexWithUV(d8, d2 + 0.0D, d11, d4, d7);
        tessellator.addVertexWithUV(d9, d2 + 0.0D, d10, d5, d7);
        tessellator.addVertexWithUV(d9, d2 + d, d10, d5, d6);
        tessellator.addVertexWithUV(d9, d2 + d, d10, d4, d6);
        tessellator.addVertexWithUV(d9, d2 + 0.0D, d10, d4, d7);
        tessellator.addVertexWithUV(d8, d2 + 0.0D, d11, d5, d7);
        tessellator.addVertexWithUV(d8, d2 + d, d11, d5, d6);
    }

    public boolean renderBlockLilyPad(Block block, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        int l = block.blockIndexInTexture;
        if(overrideBlockTexture >= 0)
        {
            l = overrideBlockTexture;
        }
        int i1 = (l & 0xf) << 4;
        int j1 = l & 0xf0;
        float f = 0.015625F;
        double d = (float)i1 / 256F;
        double d1 = ((float)i1 + 15.99F) / 256F;
        double d2 = (float)j1 / 256F;
        double d3 = ((float)j1 + 15.99F) / 256F;
        long l1 = (long)(i * 0x2fc20f) ^ (long)k * 0x6ebfff5L ^ (long)j;
        l1 = l1 * l1 * 0x285b825L + l1 * 11L;
        int k1 = (int)(l1 >> 16 & 3L);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
        float f1 = (float)i + 0.5F;
        float f2 = (float)k + 0.5F;
        float f3 = (float)(k1 & 1) * 0.5F * (float)(1 - ((k1 / 2) % 2) * 2);
        float f4 = (float)(k1 + 1 & 1) * 0.5F * (float)(1 - (((k1 + 1) / 2) % 2) * 2);
        tessellator.setColorOpaque_I(block.getBlockColor());
        tessellator.addVertexWithUV((f1 + f3) - f4, (float)j + f, f2 + f3 + f4, d, d2);
        tessellator.addVertexWithUV(f1 + f3 + f4, (float)j + f, (f2 - f3) + f4, d1, d2);
        tessellator.addVertexWithUV((f1 - f3) + f4, (float)j + f, f2 - f3 - f4, d1, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, (float)j + f, (f2 + f3) - f4, d, d3);
        tessellator.setColorOpaque_I((block.getBlockColor() & 0xfefefe) >> 1);
        tessellator.addVertexWithUV(f1 - f3 - f4, (float)j + f, (f2 + f3) - f4, d, d3);
        tessellator.addVertexWithUV((f1 - f3) + f4, (float)j + f, f2 - f3 - f4, d1, d3);
        tessellator.addVertexWithUV(f1 + f3 + f4, (float)j + f, (f2 - f3) + f4, d1, d2);
        tessellator.addVertexWithUV((f1 + f3) - f4, (float)j + f, f2 + f3 + f4, d, d2);
        return true;
    }

    public void renderBlockStemBig(Block block, int i, int j, double d, double d1, 
            double d2, double d3)
    {
        Tessellator tessellator = Tessellator.instance;
        int k = block.getBlockTextureFromSideAndMetadata(0, i) + 16;
        if(overrideBlockTexture >= 0)
        {
            k = overrideBlockTexture;
        }
        int l = (k & 0xf) << 4;
        int i1 = k & 0xf0;
        double d4 = (float)l / 256F;
        double d5 = ((float)l + 15.99F) / 256F;
        double d6 = (float)i1 / 256F;
        double d7 = ((double)i1 + 15.989999771118164D * d) / 256D;
        double d8 = (d1 + 0.5D) - 0.5D;
        double d9 = d1 + 0.5D + 0.5D;
        double d10 = (d3 + 0.5D) - 0.5D;
        double d11 = d3 + 0.5D + 0.5D;
        double d12 = d1 + 0.5D;
        double d13 = d3 + 0.5D;
        if(((j + 1) / 2) % 2 == 1)
        {
            double d14 = d5;
            d5 = d4;
            d4 = d14;
        }
        if(j < 2)
        {
            tessellator.addVertexWithUV(d8, d2 + d, d13, d4, d6);
            tessellator.addVertexWithUV(d8, d2 + 0.0D, d13, d4, d7);
            tessellator.addVertexWithUV(d9, d2 + 0.0D, d13, d5, d7);
            tessellator.addVertexWithUV(d9, d2 + d, d13, d5, d6);
            tessellator.addVertexWithUV(d9, d2 + d, d13, d5, d6);
            tessellator.addVertexWithUV(d9, d2 + 0.0D, d13, d5, d7);
            tessellator.addVertexWithUV(d8, d2 + 0.0D, d13, d4, d7);
            tessellator.addVertexWithUV(d8, d2 + d, d13, d4, d6);
        } else
        {
            tessellator.addVertexWithUV(d12, d2 + d, d11, d4, d6);
            tessellator.addVertexWithUV(d12, d2 + 0.0D, d11, d4, d7);
            tessellator.addVertexWithUV(d12, d2 + 0.0D, d10, d5, d7);
            tessellator.addVertexWithUV(d12, d2 + d, d10, d5, d6);
            tessellator.addVertexWithUV(d12, d2 + d, d10, d5, d6);
            tessellator.addVertexWithUV(d12, d2 + 0.0D, d10, d5, d7);
            tessellator.addVertexWithUV(d12, d2 + 0.0D, d11, d4, d7);
            tessellator.addVertexWithUV(d12, d2 + d, d11, d4, d6);
        }
    }

    public void renderBlockCropsImpl(Block block, int i, double d, double d1, double d2)
    {
        Tessellator tessellator = Tessellator.instance;
        int j = block.getBlockTextureFromSideAndMetadata(0, i);
        if(overrideBlockTexture >= 0)
        {
            j = overrideBlockTexture;
        }
        int k = (j & 0xf) << 4;
        int l = j & 0xf0;
        double d3 = (float)k / 256F;
        double d4 = ((float)k + 15.99F) / 256F;
        double d5 = (float)l / 256F;
        double d6 = ((float)l + 15.99F) / 256F;
        double d7 = (d + 0.5D) - 0.25D;
        double d8 = d + 0.5D + 0.25D;
        double d9 = (d2 + 0.5D) - 0.5D;
        double d10 = d2 + 0.5D + 0.5D;
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d3, d5);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d3, d6);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d4, d6);
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d4, d5);
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d3, d5);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d4, d6);
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d4, d5);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d3, d5);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d4, d6);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d4, d5);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d3, d5);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d3, d6);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d4, d6);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d4, d5);
        d7 = (d + 0.5D) - 0.5D;
        d8 = d + 0.5D + 0.5D;
        d9 = (d2 + 0.5D) - 0.25D;
        d10 = d2 + 0.5D + 0.25D;
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d3, d5);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d3, d6);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d4, d6);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d4, d5);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d9, d3, d5);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d9, d3, d6);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d9, d4, d6);
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d9, d4, d5);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d3, d5);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d4, d6);
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d4, d5);
        tessellator.addVertexWithUV(d7, d1 + 1.0D, d10, d3, d5);
        tessellator.addVertexWithUV(d7, d1 + 0.0D, d10, d3, d6);
        tessellator.addVertexWithUV(d8, d1 + 0.0D, d10, d4, d6);
        tessellator.addVertexWithUV(d8, d1 + 1.0D, d10, d4, d5);
    }

    public boolean renderBlockFluids(Block block, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        int l = block.colorMultiplier(blockAccess, i, j, k);
        float f = (float)(l >> 16 & 0xff) / 255F;
        float f1 = (float)(l >> 8 & 0xff) / 255F;
        float f2 = (float)(l & 0xff) / 255F;
        boolean flag = block.shouldSideBeRendered(blockAccess, i, j + 1, k, 1);
        boolean flag1 = block.shouldSideBeRendered(blockAccess, i, j - 1, k, 0);
        boolean aflag[] = new boolean[4];
        aflag[0] = block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2);
        aflag[1] = block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3);
        aflag[2] = block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4);
        aflag[3] = block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5);
        if(!flag && !flag1 && !aflag[0] && !aflag[1] && !aflag[2] && !aflag[3])
        {
            return false;
        }
        boolean flag2 = false;
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        double d = 0.0D;
        double d1 = 1.0D;
        Material material = block.blockMaterial;
        int i1 = blockAccess.getBlockMetadata(i, j, k);
        double d2 = getFluidHeight(i, j, k, material);
        double d3 = getFluidHeight(i, j, k + 1, material);
        double d4 = getFluidHeight(i + 1, j, k + 1, material);
        double d5 = getFluidHeight(i + 1, j, k, material);
        double d6 = 0.0010000000474974513D;
        if(renderAllFaces || flag)
        {
            flag2 = true;
            int j1 = block.getBlockTextureFromSideAndMetadata(1, i1);
            float f8 = (float)BlockFluid.func_293_a(blockAccess, i, j, k, material);
            if(f8 > -999F)
            {
                j1 = block.getBlockTextureFromSideAndMetadata(2, i1);
            }
            d2 -= d6;
            d3 -= d6;
            d4 -= d6;
            d5 -= d6;
            int i2 = (j1 & 0xf) << 4;
            int k2 = j1 & 0xf0;
            double d7 = ((double)i2 + 8D) / 256D;
            double d8 = ((double)k2 + 8D) / 256D;
            if(f8 < -999F)
            {
                f8 = 0.0F;
            } else
            {
                d7 = (float)(i2 + 16) / 256F;
                d8 = (float)(k2 + 16) / 256F;
            }
            double d10 = (double)(MathHelper.sin(f8) * 8F) / 256D;
            double d12 = (double)(MathHelper.cos(f8) * 8F) / 256D;
            tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
            float f9 = 1.0F;
            tessellator.setColorOpaque_F(f4 * f9 * f, f4 * f9 * f1, f4 * f9 * f2);
            tessellator.addVertexWithUV(i + 0, (double)j + d2, k + 0, d7 - d12 - d10, (d8 - d12) + d10);
            tessellator.addVertexWithUV(i + 0, (double)j + d3, k + 1, (d7 - d12) + d10, d8 + d12 + d10);
            tessellator.addVertexWithUV(i + 1, (double)j + d4, k + 1, d7 + d12 + d10, (d8 + d12) - d10);
            tessellator.addVertexWithUV(i + 1, (double)j + d5, k + 0, (d7 + d12) - d10, d8 - d12 - d10);
        }
        if(renderAllFaces || flag1)
        {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k));
            float f7 = 1.0F;
            tessellator.setColorOpaque_F(f3 * f7, f3 * f7, f3 * f7);
            renderBottomFace(block, i, (double)j + d6, k, block.getBlockTextureFromSide(0));
            flag2 = true;
        }
        for(int k1 = 0; k1 < 4; k1++)
        {
            int l1 = i;
            int j2 = j;
            int l2 = k;
            if(k1 == 0)
            {
                l2--;
            }
            if(k1 == 1)
            {
                l2++;
            }
            if(k1 == 2)
            {
                l1--;
            }
            if(k1 == 3)
            {
                l1++;
            }
            int i3 = block.getBlockTextureFromSideAndMetadata(k1 + 2, i1);
            int j3 = (i3 & 0xf) << 4;
            int k3 = i3 & 0xf0;
            if(!renderAllFaces && !aflag[k1])
            {
                continue;
            }
            double d9;
            double d11;
            double d13;
            double d14;
            double d15;
            double d16;
            if(k1 == 0)
            {
                d9 = d2;
                d11 = d5;
                d13 = i;
                d15 = i + 1;
                d14 = (double)k + d6;
                d16 = (double)k + d6;
            } else
            if(k1 == 1)
            {
                d9 = d4;
                d11 = d3;
                d13 = i + 1;
                d15 = i;
                d14 = (double)(k + 1) - d6;
                d16 = (double)(k + 1) - d6;
            } else
            if(k1 == 2)
            {
                d9 = d3;
                d11 = d2;
                d13 = (double)i + d6;
                d15 = (double)i + d6;
                d14 = k + 1;
                d16 = k;
            } else
            {
                d9 = d5;
                d11 = d4;
                d13 = (double)(i + 1) - d6;
                d15 = (double)(i + 1) - d6;
                d14 = k;
                d16 = k + 1;
            }
            flag2 = true;
            double d17 = (float)(j3 + 0) / 256F;
            double d18 = ((double)(j3 + 16) - 0.01D) / 256D;
            double d19 = ((double)k3 + (1.0D - d9) * 16D) / 256D;
            double d20 = ((double)k3 + (1.0D - d11) * 16D) / 256D;
            double d21 = ((double)(k3 + 16) - 0.01D) / 256D;
            tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, l1, j2, l2));
            float f10 = 1.0F;
            if(k1 < 2)
            {
                f10 *= f5;
            } else
            {
                f10 *= f6;
            }
            tessellator.setColorOpaque_F(f4 * f10 * f, f4 * f10 * f1, f4 * f10 * f2);
            tessellator.addVertexWithUV(d13, (double)j + d9, d14, d17, d19);
            tessellator.addVertexWithUV(d15, (double)j + d11, d16, d18, d20);
            tessellator.addVertexWithUV(d15, j + 0, d16, d18, d21);
            tessellator.addVertexWithUV(d13, j + 0, d14, d17, d21);
        }

        block.minY = d;
        block.maxY = d1;
        return flag2;
    }

    private float getFluidHeight(int i, int j, int k, Material material)
    {
        int l = 0;
        float f = 0.0F;
        for(int i1 = 0; i1 < 4; i1++)
        {
            int j1 = i - (i1 & 1);
            int k1 = j;
            int l1 = k - (i1 >> 1 & 1);
            if(blockAccess.getBlockMaterial(j1, k1 + 1, l1) == material)
            {
                return 1.0F;
            }
            Material material1 = blockAccess.getBlockMaterial(j1, k1, l1);
            if(material1 == material)
            {
                int i2 = blockAccess.getBlockMetadata(j1, k1, l1);
                if(i2 >= 8 || i2 == 0)
                {
                    f += BlockFluid.getFluidHeightPercent(i2) * 10F;
                    l += 10;
                }
                f += BlockFluid.getFluidHeightPercent(i2);
                l++;
                continue;
            }
            if(!material1.isSolid())
            {
                f++;
                l++;
            }
        }

        return 1.0F - f / (float)l;
    }

    public void renderBlockFallingSand(Block block, World world, int i, int j, int k)
    {
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
        float f4 = 1.0F;
        float f5 = 1.0F;
        if(f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f * f5, f * f5, f * f5);
        renderBottomFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(0));
        f5 = 1.0F;
        if(f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f1 * f5, f1 * f5, f1 * f5);
        renderTopFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(1));
        f5 = 1.0F;
        if(f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
        renderEastFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(2));
        f5 = 1.0F;
        if(f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
        renderWestFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(3));
        f5 = 1.0F;
        if(f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
        renderNorthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(4));
        f5 = 1.0F;
        if(f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
        renderSouthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSide(5));
        tessellator.draw();
    }

    public boolean renderStandardBlock(Block block, int i, int j, int k)
    {
        int l = block.colorMultiplier(blockAccess, i, j, k);
        float f = (float)(l >> 16 & 0xff) / 255F;
        float f1 = (float)(l >> 8 & 0xff) / 255F;
        float f2 = (float)(l & 0xff) / 255F;
        if(EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30F + f1 * 59F + f2 * 11F) / 100F;
            float f4 = (f * 30F + f1 * 70F) / 100F;
            float f5 = (f * 30F + f2 * 70F) / 100F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        if(Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[block.blockID] == 0)
        {
            return renderStandardBlockWithAmbientOcclusion(block, i, j, k, f, f1, f2);
        } else
        {
            return renderStandardBlockWithColorMultiplier(block, i, j, k, f, f1, f2);
        }
    }

    public boolean renderStandardBlockWithAmbientOcclusion(Block block, int i, int j, int k, float f, float f1, float f2)
    {
        enableAO = true;
        boolean flag = false;
        float f3 = lightValueOwn;
        float f10 = lightValueOwn;
        float f17 = lightValueOwn;
        float f24 = lightValueOwn;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        boolean flag5 = true;
        boolean flag6 = true;
        lightValueOwn = block.getAmbientOcclusionLightValue(blockAccess, i, j, k);
        aoLightValueXNeg = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j, k);
        aoLightValueYNeg = block.getAmbientOcclusionLightValue(blockAccess, i, j - 1, k);
        aoLightValueZNeg = block.getAmbientOcclusionLightValue(blockAccess, i, j, k - 1);
        aoLightValueXPos = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j, k);
        aoLightValueYPos = block.getAmbientOcclusionLightValue(blockAccess, i, j + 1, k);
        aoLightValueZPos = block.getAmbientOcclusionLightValue(blockAccess, i, j, k + 1);
        int l = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
        int i1 = l;
        int j1 = l;
        int k1 = l;
        int l1 = l;
        int i2 = l;
        int j2 = l;
        if(block.minY <= 0.0D)
        {
            j1 = block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k);
        }
        if(block.maxY >= 1.0D)
        {
            i2 = block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k);
        }
        if(block.minX <= 0.0D)
        {
            i1 = block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k);
        }
        if(block.maxX >= 1.0D)
        {
            l1 = block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k);
        }
        if(block.minZ <= 0.0D)
        {
            k1 = block.getMixedBrightnessForBlock(blockAccess, i, j, k - 1);
        }
        if(block.maxZ >= 1.0D)
        {
            j2 = block.getMixedBrightnessForBlock(blockAccess, i, j, k + 1);
        }
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(0xf000f);
        aoGrassXYZPPC = Block.canBlockGrass[blockAccess.getBlockId(i + 1, j + 1, k)];
        aoGrassXYZPNC = Block.canBlockGrass[blockAccess.getBlockId(i + 1, j - 1, k)];
        aoGrassXYZPCP = Block.canBlockGrass[blockAccess.getBlockId(i + 1, j, k + 1)];
        aoGrassXYZPCN = Block.canBlockGrass[blockAccess.getBlockId(i + 1, j, k - 1)];
        aoGrassXYZNPC = Block.canBlockGrass[blockAccess.getBlockId(i - 1, j + 1, k)];
        aoGrassXYZNNC = Block.canBlockGrass[blockAccess.getBlockId(i - 1, j - 1, k)];
        aoGrassXYZNCN = Block.canBlockGrass[blockAccess.getBlockId(i - 1, j, k - 1)];
        aoGrassXYZNCP = Block.canBlockGrass[blockAccess.getBlockId(i - 1, j, k + 1)];
        aoGrassXYZCPP = Block.canBlockGrass[blockAccess.getBlockId(i, j + 1, k + 1)];
        aoGrassXYZCPN = Block.canBlockGrass[blockAccess.getBlockId(i, j + 1, k - 1)];
        aoGrassXYZCNP = Block.canBlockGrass[blockAccess.getBlockId(i, j - 1, k + 1)];
        aoGrassXYZCNN = Block.canBlockGrass[blockAccess.getBlockId(i, j - 1, k - 1)];
        if(block.blockIndexInTexture == 3)
        {
            flag1 = flag3 = flag4 = flag5 = flag6 = false;
        }
        if(overrideBlockTexture >= 0)
        {
            flag1 = flag3 = flag4 = flag5 = flag6 = false;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j - 1, k, 0))
        {
            float f4;
            float f11;
            float f18;
            float f25;
            if(aoType > 0)
            {
                if(block.minY <= 0.0D)
                {
                    j--;
                }
                aoBrightnessXYNN = block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k);
                aoBrightnessYZNN = block.getMixedBrightnessForBlock(blockAccess, i, j, k - 1);
                aoBrightnessYZNP = block.getMixedBrightnessForBlock(blockAccess, i, j, k + 1);
                aoBrightnessXYPN = block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k);
                aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j, k);
                aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(blockAccess, i, j, k - 1);
                aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(blockAccess, i, j, k + 1);
                aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j, k);
                if(aoGrassXYZCNN || aoGrassXYZNNC)
                {
                    aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j, k - 1);
                    aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k - 1);
                } else
                {
                    aoLightValueScratchXYZNNN = aoLightValueScratchXYNN;
                    aoBrightnessXYZNNN = aoBrightnessXYNN;
                }
                if(aoGrassXYZCNP || aoGrassXYZNNC)
                {
                    aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j, k + 1);
                    aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k + 1);
                } else
                {
                    aoLightValueScratchXYZNNP = aoLightValueScratchXYNN;
                    aoBrightnessXYZNNP = aoBrightnessXYNN;
                }
                if(aoGrassXYZCNN || aoGrassXYZPNC)
                {
                    aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j, k - 1);
                    aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k - 1);
                } else
                {
                    aoLightValueScratchXYZPNN = aoLightValueScratchXYPN;
                    aoBrightnessXYZPNN = aoBrightnessXYPN;
                }
                if(aoGrassXYZCNP || aoGrassXYZPNC)
                {
                    aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j, k + 1);
                    aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k + 1);
                } else
                {
                    aoLightValueScratchXYZPNP = aoLightValueScratchXYPN;
                    aoBrightnessXYZPNP = aoBrightnessXYPN;
                }
                if(block.minY <= 0.0D)
                {
                    j++;
                }
                f4 = (aoLightValueScratchXYZNNP + aoLightValueScratchXYNN + aoLightValueScratchYZNP + aoLightValueYNeg) / 4F;
                f25 = (aoLightValueScratchYZNP + aoLightValueYNeg + aoLightValueScratchXYZPNP + aoLightValueScratchXYPN) / 4F;
                f18 = (aoLightValueYNeg + aoLightValueScratchYZNN + aoLightValueScratchXYPN + aoLightValueScratchXYZPNN) / 4F;
                f11 = (aoLightValueScratchXYNN + aoLightValueScratchXYZNNN + aoLightValueYNeg + aoLightValueScratchYZNN) / 4F;
                brightnessTopLeft = getAoBrightness(aoBrightnessXYZNNP, aoBrightnessXYNN, aoBrightnessYZNP, j1);
                brightnessTopRight = getAoBrightness(aoBrightnessYZNP, aoBrightnessXYZPNP, aoBrightnessXYPN, j1);
                brightnessBottomRight = getAoBrightness(aoBrightnessYZNN, aoBrightnessXYPN, aoBrightnessXYZPNN, j1);
                brightnessBottomLeft = getAoBrightness(aoBrightnessXYNN, aoBrightnessXYZNNN, aoBrightnessYZNN, j1);
            } else
            {
                f4 = f11 = f18 = f25 = aoLightValueYNeg;
                brightnessTopLeft = brightnessBottomLeft = brightnessBottomRight = brightnessTopRight = aoBrightnessXYNN;
            }
            colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = (flag1 ? f : 1.0F) * 0.5F;
            colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = (flag1 ? f1 : 1.0F) * 0.5F;
            colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = (flag1 ? f2 : 1.0F) * 0.5F;
            colorRedTopLeft *= f4;
            colorGreenTopLeft *= f4;
            colorBlueTopLeft *= f4;
            colorRedBottomLeft *= f11;
            colorGreenBottomLeft *= f11;
            colorBlueBottomLeft *= f11;
            colorRedBottomRight *= f18;
            colorGreenBottomRight *= f18;
            colorBlueBottomRight *= f18;
            colorRedTopRight *= f25;
            colorGreenTopRight *= f25;
            colorBlueTopRight *= f25;
            renderBottomFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 0));
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j + 1, k, 1))
        {
            float f5;
            float f12;
            float f19;
            float f26;
            if(aoType > 0)
            {
                if(block.maxY >= 1.0D)
                {
                    j++;
                }
                aoBrightnessXYNP = block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k);
                aoBrightnessXYPP = block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k);
                aoBrightnessYZPN = block.getMixedBrightnessForBlock(blockAccess, i, j, k - 1);
                aoBrightnessYZPP = block.getMixedBrightnessForBlock(blockAccess, i, j, k + 1);
                aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j, k);
                aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j, k);
                aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(blockAccess, i, j, k - 1);
                aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(blockAccess, i, j, k + 1);
                if(aoGrassXYZCPN || aoGrassXYZNPC)
                {
                    aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j, k - 1);
                    aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k - 1);
                } else
                {
                    aoLightValueScratchXYZNPN = aoLightValueScratchXYNP;
                    aoBrightnessXYZNPN = aoBrightnessXYNP;
                }
                if(aoGrassXYZCPN || aoGrassXYZPPC)
                {
                    aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j, k - 1);
                    aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k - 1);
                } else
                {
                    aoLightValueScratchXYZPPN = aoLightValueScratchXYPP;
                    aoBrightnessXYZPPN = aoBrightnessXYPP;
                }
                if(aoGrassXYZCPP || aoGrassXYZNPC)
                {
                    aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j, k + 1);
                    aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k + 1);
                } else
                {
                    aoLightValueScratchXYZNPP = aoLightValueScratchXYNP;
                    aoBrightnessXYZNPP = aoBrightnessXYNP;
                }
                if(aoGrassXYZCPP || aoGrassXYZPPC)
                {
                    aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j, k + 1);
                    aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k + 1);
                } else
                {
                    aoLightValueScratchXYZPPP = aoLightValueScratchXYPP;
                    aoBrightnessXYZPPP = aoBrightnessXYPP;
                }
                if(block.maxY >= 1.0D)
                {
                    j--;
                }
                f26 = (aoLightValueScratchXYZNPP + aoLightValueScratchXYNP + aoLightValueScratchYZPP + aoLightValueYPos) / 4F;
                f5 = (aoLightValueScratchYZPP + aoLightValueYPos + aoLightValueScratchXYZPPP + aoLightValueScratchXYPP) / 4F;
                f12 = (aoLightValueYPos + aoLightValueScratchYZPN + aoLightValueScratchXYPP + aoLightValueScratchXYZPPN) / 4F;
                f19 = (aoLightValueScratchXYNP + aoLightValueScratchXYZNPN + aoLightValueYPos + aoLightValueScratchYZPN) / 4F;
                brightnessTopRight = getAoBrightness(aoBrightnessXYZNPP, aoBrightnessXYNP, aoBrightnessYZPP, i2);
                brightnessTopLeft = getAoBrightness(aoBrightnessYZPP, aoBrightnessXYZPPP, aoBrightnessXYPP, i2);
                brightnessBottomLeft = getAoBrightness(aoBrightnessYZPN, aoBrightnessXYPP, aoBrightnessXYZPPN, i2);
                brightnessBottomRight = getAoBrightness(aoBrightnessXYNP, aoBrightnessXYZNPN, aoBrightnessYZPN, i2);
            } else
            {
                f5 = f12 = f19 = f26 = aoLightValueYPos;
                brightnessTopLeft = brightnessBottomLeft = brightnessBottomRight = brightnessTopRight = i2;
            }
            colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = flag2 ? f : 1.0F;
            colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = flag2 ? f1 : 1.0F;
            colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = flag2 ? f2 : 1.0F;
            colorRedTopLeft *= f5;
            colorGreenTopLeft *= f5;
            colorBlueTopLeft *= f5;
            colorRedBottomLeft *= f12;
            colorGreenBottomLeft *= f12;
            colorBlueBottomLeft *= f12;
            colorRedBottomRight *= f19;
            colorGreenBottomRight *= f19;
            colorBlueBottomRight *= f19;
            colorRedTopRight *= f26;
            colorGreenTopRight *= f26;
            colorBlueTopRight *= f26;
            renderTopFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 1));
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2))
        {
            float f6;
            float f13;
            float f20;
            float f27;
            if(aoType > 0)
            {
                if(block.minZ <= 0.0D)
                {
                    k--;
                }
                aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j, k);
                aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(blockAccess, i, j - 1, k);
                aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(blockAccess, i, j + 1, k);
                aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j, k);
                aoBrightnessXZNN = block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k);
                aoBrightnessYZNN = block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k);
                aoBrightnessYZPN = block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k);
                aoBrightnessXZPN = block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k);
                if(aoGrassXYZNCN || aoGrassXYZCNN)
                {
                    aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j - 1, k);
                    aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(blockAccess, i - 1, j - 1, k);
                } else
                {
                    aoLightValueScratchXYZNNN = aoLightValueScratchXZNN;
                    aoBrightnessXYZNNN = aoBrightnessXZNN;
                }
                if(aoGrassXYZNCN || aoGrassXYZCPN)
                {
                    aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j + 1, k);
                    aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockAccess, i - 1, j + 1, k);
                } else
                {
                    aoLightValueScratchXYZNPN = aoLightValueScratchXZNN;
                    aoBrightnessXYZNPN = aoBrightnessXZNN;
                }
                if(aoGrassXYZPCN || aoGrassXYZCNN)
                {
                    aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j - 1, k);
                    aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(blockAccess, i + 1, j - 1, k);
                } else
                {
                    aoLightValueScratchXYZPNN = aoLightValueScratchXZPN;
                    aoBrightnessXYZPNN = aoBrightnessXZPN;
                }
                if(aoGrassXYZPCN || aoGrassXYZCPN)
                {
                    aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j + 1, k);
                    aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockAccess, i + 1, j + 1, k);
                } else
                {
                    aoLightValueScratchXYZPPN = aoLightValueScratchXZPN;
                    aoBrightnessXYZPPN = aoBrightnessXZPN;
                }
                if(block.minZ <= 0.0D)
                {
                    k++;
                }
                f6 = (aoLightValueScratchXZNN + aoLightValueScratchXYZNPN + aoLightValueZNeg + aoLightValueScratchYZPN) / 4F;
                f13 = (aoLightValueZNeg + aoLightValueScratchYZPN + aoLightValueScratchXZPN + aoLightValueScratchXYZPPN) / 4F;
                f20 = (aoLightValueScratchYZNN + aoLightValueZNeg + aoLightValueScratchXYZPNN + aoLightValueScratchXZPN) / 4F;
                f27 = (aoLightValueScratchXYZNNN + aoLightValueScratchXZNN + aoLightValueScratchYZNN + aoLightValueZNeg) / 4F;
                brightnessTopLeft = getAoBrightness(aoBrightnessXZNN, aoBrightnessXYZNPN, aoBrightnessYZPN, k1);
                brightnessBottomLeft = getAoBrightness(aoBrightnessYZPN, aoBrightnessXZPN, aoBrightnessXYZPPN, k1);
                brightnessBottomRight = getAoBrightness(aoBrightnessYZNN, aoBrightnessXYZPNN, aoBrightnessXZPN, k1);
                brightnessTopRight = getAoBrightness(aoBrightnessXYZNNN, aoBrightnessXZNN, aoBrightnessYZNN, k1);
            } else
            {
                f6 = f13 = f20 = f27 = aoLightValueZNeg;
                brightnessTopLeft = brightnessBottomLeft = brightnessBottomRight = brightnessTopRight = k1;
            }
            colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = (flag3 ? f : 1.0F) * 0.8F;
            colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = (flag3 ? f1 : 1.0F) * 0.8F;
            colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = (flag3 ? f2 : 1.0F) * 0.8F;
            colorRedTopLeft *= f6;
            colorGreenTopLeft *= f6;
            colorBlueTopLeft *= f6;
            colorRedBottomLeft *= f13;
            colorGreenBottomLeft *= f13;
            colorBlueBottomLeft *= f13;
            colorRedBottomRight *= f20;
            colorGreenBottomRight *= f20;
            colorBlueBottomRight *= f20;
            colorRedTopRight *= f27;
            colorGreenTopRight *= f27;
            colorBlueTopRight *= f27;
            int k2 = block.getBlockTexture(blockAccess, i, j, k, 2);
            renderEastFace(block, i, j, k, k2);
            if(fancyGrass && k2 == 3 && overrideBlockTexture < 0)
            {
                colorRedTopLeft *= f;
                colorRedBottomLeft *= f;
                colorRedBottomRight *= f;
                colorRedTopRight *= f;
                colorGreenTopLeft *= f1;
                colorGreenBottomLeft *= f1;
                colorGreenBottomRight *= f1;
                colorGreenTopRight *= f1;
                colorBlueTopLeft *= f2;
                colorBlueBottomLeft *= f2;
                colorBlueBottomRight *= f2;
                colorBlueTopRight *= f2;
                renderEastFace(block, i, j, k, 38);
            }
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3))
        {
            float f7;
            float f14;
            float f21;
            float f28;
            if(aoType > 0)
            {
                if(block.maxZ >= 1.0D)
                {
                    k++;
                }
                aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j, k);
                aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j, k);
                aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(blockAccess, i, j - 1, k);
                aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(blockAccess, i, j + 1, k);
                aoBrightnessXZNP = block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k);
                aoBrightnessXZPP = block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k);
                aoBrightnessYZNP = block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k);
                aoBrightnessYZPP = block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k);
                if(aoGrassXYZNCP || aoGrassXYZCNP)
                {
                    aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j - 1, k);
                    aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(blockAccess, i - 1, j - 1, k);
                } else
                {
                    aoLightValueScratchXYZNNP = aoLightValueScratchXZNP;
                    aoBrightnessXYZNNP = aoBrightnessXZNP;
                }
                if(aoGrassXYZNCP || aoGrassXYZCPP)
                {
                    aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(blockAccess, i - 1, j + 1, k);
                    aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockAccess, i - 1, j + 1, k);
                } else
                {
                    aoLightValueScratchXYZNPP = aoLightValueScratchXZNP;
                    aoBrightnessXYZNPP = aoBrightnessXZNP;
                }
                if(aoGrassXYZPCP || aoGrassXYZCNP)
                {
                    aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j - 1, k);
                    aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(blockAccess, i + 1, j - 1, k);
                } else
                {
                    aoLightValueScratchXYZPNP = aoLightValueScratchXZPP;
                    aoBrightnessXYZPNP = aoBrightnessXZPP;
                }
                if(aoGrassXYZPCP || aoGrassXYZCPP)
                {
                    aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(blockAccess, i + 1, j + 1, k);
                    aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockAccess, i + 1, j + 1, k);
                } else
                {
                    aoLightValueScratchXYZPPP = aoLightValueScratchXZPP;
                    aoBrightnessXYZPPP = aoBrightnessXZPP;
                }
                if(block.maxZ >= 1.0D)
                {
                    k--;
                }
                f7 = (aoLightValueScratchXZNP + aoLightValueScratchXYZNPP + aoLightValueZPos + aoLightValueScratchYZPP) / 4F;
                f28 = (aoLightValueZPos + aoLightValueScratchYZPP + aoLightValueScratchXZPP + aoLightValueScratchXYZPPP) / 4F;
                f21 = (aoLightValueScratchYZNP + aoLightValueZPos + aoLightValueScratchXYZPNP + aoLightValueScratchXZPP) / 4F;
                f14 = (aoLightValueScratchXYZNNP + aoLightValueScratchXZNP + aoLightValueScratchYZNP + aoLightValueZPos) / 4F;
                brightnessTopLeft = getAoBrightness(aoBrightnessXZNP, aoBrightnessXYZNPP, aoBrightnessYZPP, j2);
                brightnessTopRight = getAoBrightness(aoBrightnessYZPP, aoBrightnessXZPP, aoBrightnessXYZPPP, j2);
                brightnessBottomRight = getAoBrightness(aoBrightnessYZNP, aoBrightnessXYZPNP, aoBrightnessXZPP, j2);
                brightnessBottomLeft = getAoBrightness(aoBrightnessXYZNNP, aoBrightnessXZNP, aoBrightnessYZNP, j2);
            } else
            {
                f7 = f14 = f21 = f28 = aoLightValueZPos;
                brightnessTopLeft = brightnessBottomLeft = brightnessBottomRight = brightnessTopRight = j2;
            }
            colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = (flag4 ? f : 1.0F) * 0.8F;
            colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = (flag4 ? f1 : 1.0F) * 0.8F;
            colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = (flag4 ? f2 : 1.0F) * 0.8F;
            colorRedTopLeft *= f7;
            colorGreenTopLeft *= f7;
            colorBlueTopLeft *= f7;
            colorRedBottomLeft *= f14;
            colorGreenBottomLeft *= f14;
            colorBlueBottomLeft *= f14;
            colorRedBottomRight *= f21;
            colorGreenBottomRight *= f21;
            colorBlueBottomRight *= f21;
            colorRedTopRight *= f28;
            colorGreenTopRight *= f28;
            colorBlueTopRight *= f28;
            int l2 = block.getBlockTexture(blockAccess, i, j, k, 3);
            renderWestFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 3));
            if(fancyGrass && l2 == 3 && overrideBlockTexture < 0)
            {
                colorRedTopLeft *= f;
                colorRedBottomLeft *= f;
                colorRedBottomRight *= f;
                colorRedTopRight *= f;
                colorGreenTopLeft *= f1;
                colorGreenBottomLeft *= f1;
                colorGreenBottomRight *= f1;
                colorGreenTopRight *= f1;
                colorBlueTopLeft *= f2;
                colorBlueBottomLeft *= f2;
                colorBlueBottomRight *= f2;
                colorBlueTopRight *= f2;
                renderWestFace(block, i, j, k, 38);
            }
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4))
        {
            float f8;
            float f15;
            float f22;
            float f29;
            if(aoType > 0)
            {
                if(block.minX <= 0.0D)
                {
                    i--;
                }
                aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(blockAccess, i, j - 1, k);
                aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(blockAccess, i, j, k - 1);
                aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(blockAccess, i, j, k + 1);
                aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(blockAccess, i, j + 1, k);
                aoBrightnessXYNN = block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k);
                aoBrightnessXZNN = block.getMixedBrightnessForBlock(blockAccess, i, j, k - 1);
                aoBrightnessXZNP = block.getMixedBrightnessForBlock(blockAccess, i, j, k + 1);
                aoBrightnessXYNP = block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k);
                if(aoGrassXYZNCN || aoGrassXYZNNC)
                {
                    aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(blockAccess, i, j - 1, k - 1);
                    aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k - 1);
                } else
                {
                    aoLightValueScratchXYZNNN = aoLightValueScratchXZNN;
                    aoBrightnessXYZNNN = aoBrightnessXZNN;
                }
                if(aoGrassXYZNCP || aoGrassXYZNNC)
                {
                    aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(blockAccess, i, j - 1, k + 1);
                    aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k + 1);
                } else
                {
                    aoLightValueScratchXYZNNP = aoLightValueScratchXZNP;
                    aoBrightnessXYZNNP = aoBrightnessXZNP;
                }
                if(aoGrassXYZNCN || aoGrassXYZNPC)
                {
                    aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(blockAccess, i, j + 1, k - 1);
                    aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k - 1);
                } else
                {
                    aoLightValueScratchXYZNPN = aoLightValueScratchXZNN;
                    aoBrightnessXYZNPN = aoBrightnessXZNN;
                }
                if(aoGrassXYZNCP || aoGrassXYZNPC)
                {
                    aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(blockAccess, i, j + 1, k + 1);
                    aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k + 1);
                } else
                {
                    aoLightValueScratchXYZNPP = aoLightValueScratchXZNP;
                    aoBrightnessXYZNPP = aoBrightnessXZNP;
                }
                if(block.minX <= 0.0D)
                {
                    i++;
                }
                f29 = (aoLightValueScratchXYNN + aoLightValueScratchXYZNNP + aoLightValueXNeg + aoLightValueScratchXZNP) / 4F;
                f8 = (aoLightValueXNeg + aoLightValueScratchXZNP + aoLightValueScratchXYNP + aoLightValueScratchXYZNPP) / 4F;
                f15 = (aoLightValueScratchXZNN + aoLightValueXNeg + aoLightValueScratchXYZNPN + aoLightValueScratchXYNP) / 4F;
                f22 = (aoLightValueScratchXYZNNN + aoLightValueScratchXYNN + aoLightValueScratchXZNN + aoLightValueXNeg) / 4F;
                brightnessTopRight = getAoBrightness(aoBrightnessXYNN, aoBrightnessXYZNNP, aoBrightnessXZNP, i1);
                brightnessTopLeft = getAoBrightness(aoBrightnessXZNP, aoBrightnessXYNP, aoBrightnessXYZNPP, i1);
                brightnessBottomLeft = getAoBrightness(aoBrightnessXZNN, aoBrightnessXYZNPN, aoBrightnessXYNP, i1);
                brightnessBottomRight = getAoBrightness(aoBrightnessXYZNNN, aoBrightnessXYNN, aoBrightnessXZNN, i1);
            } else
            {
                f8 = f15 = f22 = f29 = aoLightValueXNeg;
                brightnessTopLeft = brightnessBottomLeft = brightnessBottomRight = brightnessTopRight = i1;
            }
            colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = (flag5 ? f : 1.0F) * 0.6F;
            colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = (flag5 ? f1 : 1.0F) * 0.6F;
            colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = (flag5 ? f2 : 1.0F) * 0.6F;
            colorRedTopLeft *= f8;
            colorGreenTopLeft *= f8;
            colorBlueTopLeft *= f8;
            colorRedBottomLeft *= f15;
            colorGreenBottomLeft *= f15;
            colorBlueBottomLeft *= f15;
            colorRedBottomRight *= f22;
            colorGreenBottomRight *= f22;
            colorBlueBottomRight *= f22;
            colorRedTopRight *= f29;
            colorGreenTopRight *= f29;
            colorBlueTopRight *= f29;
            int i3 = block.getBlockTexture(blockAccess, i, j, k, 4);
            renderNorthFace(block, i, j, k, i3);
            if(fancyGrass && i3 == 3 && overrideBlockTexture < 0)
            {
                colorRedTopLeft *= f;
                colorRedBottomLeft *= f;
                colorRedBottomRight *= f;
                colorRedTopRight *= f;
                colorGreenTopLeft *= f1;
                colorGreenBottomLeft *= f1;
                colorGreenBottomRight *= f1;
                colorGreenTopRight *= f1;
                colorBlueTopLeft *= f2;
                colorBlueBottomLeft *= f2;
                colorBlueBottomRight *= f2;
                colorBlueTopRight *= f2;
                renderNorthFace(block, i, j, k, 38);
            }
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5))
        {
            float f9;
            float f16;
            float f23;
            float f30;
            if(aoType > 0)
            {
                if(block.maxX >= 1.0D)
                {
                    i++;
                }
                aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(blockAccess, i, j - 1, k);
                aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(blockAccess, i, j, k - 1);
                aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(blockAccess, i, j, k + 1);
                aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(blockAccess, i, j + 1, k);
                aoBrightnessXYPN = block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k);
                aoBrightnessXZPN = block.getMixedBrightnessForBlock(blockAccess, i, j, k - 1);
                aoBrightnessXZPP = block.getMixedBrightnessForBlock(blockAccess, i, j, k + 1);
                aoBrightnessXYPP = block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k);
                if(aoGrassXYZPNC || aoGrassXYZPCN)
                {
                    aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(blockAccess, i, j - 1, k - 1);
                    aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k - 1);
                } else
                {
                    aoLightValueScratchXYZPNN = aoLightValueScratchXZPN;
                    aoBrightnessXYZPNN = aoBrightnessXZPN;
                }
                if(aoGrassXYZPNC || aoGrassXYZPCP)
                {
                    aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(blockAccess, i, j - 1, k + 1);
                    aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k + 1);
                } else
                {
                    aoLightValueScratchXYZPNP = aoLightValueScratchXZPP;
                    aoBrightnessXYZPNP = aoBrightnessXZPP;
                }
                if(aoGrassXYZPPC || aoGrassXYZPCN)
                {
                    aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(blockAccess, i, j + 1, k - 1);
                    aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k - 1);
                } else
                {
                    aoLightValueScratchXYZPPN = aoLightValueScratchXZPN;
                    aoBrightnessXYZPPN = aoBrightnessXZPN;
                }
                if(aoGrassXYZPPC || aoGrassXYZPCP)
                {
                    aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(blockAccess, i, j + 1, k + 1);
                    aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k + 1);
                } else
                {
                    aoLightValueScratchXYZPPP = aoLightValueScratchXZPP;
                    aoBrightnessXYZPPP = aoBrightnessXZPP;
                }
                if(block.maxX >= 1.0D)
                {
                    i--;
                }
                f9 = (aoLightValueScratchXYPN + aoLightValueScratchXYZPNP + aoLightValueXPos + aoLightValueScratchXZPP) / 4F;
                f30 = (aoLightValueXPos + aoLightValueScratchXZPP + aoLightValueScratchXYPP + aoLightValueScratchXYZPPP) / 4F;
                f23 = (aoLightValueScratchXZPN + aoLightValueXPos + aoLightValueScratchXYZPPN + aoLightValueScratchXYPP) / 4F;
                f16 = (aoLightValueScratchXYZPNN + aoLightValueScratchXYPN + aoLightValueScratchXZPN + aoLightValueXPos) / 4F;
                brightnessTopLeft = getAoBrightness(aoBrightnessXYPN, aoBrightnessXYZPNP, aoBrightnessXZPP, l1);
                brightnessTopRight = getAoBrightness(aoBrightnessXZPP, aoBrightnessXYPP, aoBrightnessXYZPPP, l1);
                brightnessBottomRight = getAoBrightness(aoBrightnessXZPN, aoBrightnessXYZPPN, aoBrightnessXYPP, l1);
                brightnessBottomLeft = getAoBrightness(aoBrightnessXYZPNN, aoBrightnessXYPN, aoBrightnessXZPN, l1);
            } else
            {
                f9 = f16 = f23 = f30 = aoLightValueXPos;
                brightnessTopLeft = brightnessBottomLeft = brightnessBottomRight = brightnessTopRight = l1;
            }
            colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = (flag6 ? f : 1.0F) * 0.6F;
            colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = (flag6 ? f1 : 1.0F) * 0.6F;
            colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = (flag6 ? f2 : 1.0F) * 0.6F;
            colorRedTopLeft *= f9;
            colorGreenTopLeft *= f9;
            colorBlueTopLeft *= f9;
            colorRedBottomLeft *= f16;
            colorGreenBottomLeft *= f16;
            colorBlueBottomLeft *= f16;
            colorRedBottomRight *= f23;
            colorGreenBottomRight *= f23;
            colorBlueBottomRight *= f23;
            colorRedTopRight *= f30;
            colorGreenTopRight *= f30;
            colorBlueTopRight *= f30;
            int j3 = block.getBlockTexture(blockAccess, i, j, k, 5);
            renderSouthFace(block, i, j, k, j3);
            if(fancyGrass && j3 == 3 && overrideBlockTexture < 0)
            {
                colorRedTopLeft *= f;
                colorRedBottomLeft *= f;
                colorRedBottomRight *= f;
                colorRedTopRight *= f;
                colorGreenTopLeft *= f1;
                colorGreenBottomLeft *= f1;
                colorGreenBottomRight *= f1;
                colorGreenTopRight *= f1;
                colorBlueTopLeft *= f2;
                colorBlueBottomLeft *= f2;
                colorBlueBottomRight *= f2;
                colorBlueTopRight *= f2;
                renderSouthFace(block, i, j, k, 38);
            }
            flag = true;
        }
        enableAO = false;
        return flag;
    }

    private int getAoBrightness(int i, int j, int k, int l)
    {
        if(i == 0)
        {
            i = l;
        }
        if(j == 0)
        {
            j = l;
        }
        if(k == 0)
        {
            k = l;
        }
        return i + j + k + l >> 2 & 0xff00ff;
    }

    public boolean renderStandardBlockWithColorMultiplier(Block block, int i, int j, int k, float f, float f1, float f2)
    {
        enableAO = false;
        Tessellator tessellator = Tessellator.instance;
        boolean flag = false;
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        float f7 = f4 * f;
        float f8 = f4 * f1;
        float f9 = f4 * f2;
        float f10 = f3;
        float f11 = f5;
        float f12 = f6;
        float f13 = f3;
        float f14 = f5;
        float f15 = f6;
        float f16 = f3;
        float f17 = f5;
        float f18 = f6;
        if(block != Block.grass)
        {
            f10 *= f;
            f11 *= f;
            f12 *= f;
            f13 *= f1;
            f14 *= f1;
            f15 *= f1;
            f16 *= f2;
            f17 *= f2;
            f18 *= f2;
        }
        int l = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j - 1, k, 0))
        {
            tessellator.setBrightness(block.minY <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k) : l);
            tessellator.setColorOpaque_F(f10, f13, f16);
            renderBottomFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 0));
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j + 1, k, 1))
        {
            tessellator.setBrightness(block.maxY >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k) : l);
            tessellator.setColorOpaque_F(f7, f8, f9);
            renderTopFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 1));
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2))
        {
            tessellator.setBrightness(block.minZ <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j, k - 1) : l);
            tessellator.setColorOpaque_F(f11, f14, f17);
            int i1 = block.getBlockTexture(blockAccess, i, j, k, 2);
            renderEastFace(block, i, j, k, i1);
            if(fancyGrass && i1 == 3 && overrideBlockTexture < 0)
            {
                tessellator.setColorOpaque_F(f11 * f, f14 * f1, f17 * f2);
                renderEastFace(block, i, j, k, 38);
            }
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3))
        {
            tessellator.setBrightness(block.maxZ >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j, k + 1) : l);
            tessellator.setColorOpaque_F(f11, f14, f17);
            int j1 = block.getBlockTexture(blockAccess, i, j, k, 3);
            renderWestFace(block, i, j, k, j1);
            if(fancyGrass && j1 == 3 && overrideBlockTexture < 0)
            {
                tessellator.setColorOpaque_F(f11 * f, f14 * f1, f17 * f2);
                renderWestFace(block, i, j, k, 38);
            }
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4))
        {
            tessellator.setBrightness(block.minX <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k) : l);
            tessellator.setColorOpaque_F(f12, f15, f18);
            int k1 = block.getBlockTexture(blockAccess, i, j, k, 4);
            renderNorthFace(block, i, j, k, k1);
            if(fancyGrass && k1 == 3 && overrideBlockTexture < 0)
            {
                tessellator.setColorOpaque_F(f12 * f, f15 * f1, f18 * f2);
                renderNorthFace(block, i, j, k, 38);
            }
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5))
        {
            tessellator.setBrightness(block.maxX >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k) : l);
            tessellator.setColorOpaque_F(f12, f15, f18);
            int l1 = block.getBlockTexture(blockAccess, i, j, k, 5);
            renderSouthFace(block, i, j, k, l1);
            if(fancyGrass && l1 == 3 && overrideBlockTexture < 0)
            {
                tessellator.setColorOpaque_F(f12 * f, f15 * f1, f18 * f2);
                renderSouthFace(block, i, j, k, 38);
            }
            flag = true;
        }
        return flag;
    }

    public boolean renderBlockCactus(Block block, int i, int j, int k)
    {
        int l = block.colorMultiplier(blockAccess, i, j, k);
        float f = (float)(l >> 16 & 0xff) / 255F;
        float f1 = (float)(l >> 8 & 0xff) / 255F;
        float f2 = (float)(l & 0xff) / 255F;
        if(EntityRenderer.anaglyphEnable)
        {
            float f3 = (f * 30F + f1 * 59F + f2 * 11F) / 100F;
            float f4 = (f * 30F + f1 * 70F) / 100F;
            float f5 = (f * 30F + f2 * 70F) / 100F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        return renderBlockCactusImpl(block, i, j, k, f, f1, f2);
    }

    public boolean renderBlockCactusImpl(Block block, int i, int j, int k, float f, float f1, float f2)
    {
        Tessellator tessellator = Tessellator.instance;
        boolean flag = false;
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        float f7 = f3 * f;
        float f8 = f4 * f;
        float f9 = f5 * f;
        float f10 = f6 * f;
        float f11 = f3 * f1;
        float f12 = f4 * f1;
        float f13 = f5 * f1;
        float f14 = f6 * f1;
        float f15 = f3 * f2;
        float f16 = f4 * f2;
        float f17 = f5 * f2;
        float f18 = f6 * f2;
        float f19 = 0.0625F;
        int l = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j - 1, k, 0))
        {
            tessellator.setBrightness(block.minY <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k) : l);
            tessellator.setColorOpaque_F(f7, f11, f15);
            renderBottomFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 0));
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j + 1, k, 1))
        {
            tessellator.setBrightness(block.maxY >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k) : l);
            tessellator.setColorOpaque_F(f8, f12, f16);
            renderTopFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 1));
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k - 1, 2))
        {
            tessellator.setBrightness(block.minZ <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j, k - 1) : l);
            tessellator.setColorOpaque_F(f9, f13, f17);
            tessellator.setTranslationF(0.0F, 0.0F, f19);
            renderEastFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 2));
            tessellator.setTranslationF(0.0F, 0.0F, -f19);
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i, j, k + 1, 3))
        {
            tessellator.setBrightness(block.maxZ >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j, k + 1) : l);
            tessellator.setColorOpaque_F(f9, f13, f17);
            tessellator.setTranslationF(0.0F, 0.0F, -f19);
            renderWestFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 3));
            tessellator.setTranslationF(0.0F, 0.0F, f19);
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i - 1, j, k, 4))
        {
            tessellator.setBrightness(block.minX <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k) : l);
            tessellator.setColorOpaque_F(f10, f14, f18);
            tessellator.setTranslationF(f19, 0.0F, 0.0F);
            renderNorthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 4));
            tessellator.setTranslationF(-f19, 0.0F, 0.0F);
            flag = true;
        }
        if(renderAllFaces || block.shouldSideBeRendered(blockAccess, i + 1, j, k, 5))
        {
            tessellator.setBrightness(block.maxX >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k) : l);
            tessellator.setColorOpaque_F(f10, f14, f18);
            tessellator.setTranslationF(-f19, 0.0F, 0.0F);
            renderSouthFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 5));
            tessellator.setTranslationF(f19, 0.0F, 0.0F);
            flag = true;
        }
        return flag;
    }

    public boolean renderBlockFence(BlockFence blockfence, int i, int j, int k)
    {
        boolean flag = false;
        float f = 0.375F;
        float f1 = 0.625F;
        blockfence.setBlockBounds(f, 0.0F, f, f1, 1.0F, f1);
        renderStandardBlock(blockfence, i, j, k);
        flag = true;
        boolean flag1 = false;
        boolean flag2 = false;
        if(blockfence.isFenceAt(blockAccess, i - 1, j, k) || blockfence.isFenceAt(blockAccess, i + 1, j, k))
        {
            flag1 = true;
        }
        if(blockfence.isFenceAt(blockAccess, i, j, k - 1) || blockfence.isFenceAt(blockAccess, i, j, k + 1))
        {
            flag2 = true;
        }
        boolean flag3 = blockfence.isFenceAt(blockAccess, i - 1, j, k);
        boolean flag4 = blockfence.isFenceAt(blockAccess, i + 1, j, k);
        boolean flag5 = blockfence.isFenceAt(blockAccess, i, j, k - 1);
        boolean flag6 = blockfence.isFenceAt(blockAccess, i, j, k + 1);
        if(!flag1 && !flag2)
        {
            flag1 = true;
        }
        f = 0.4375F;
        f1 = 0.5625F;
        float f2 = 0.75F;
        float f3 = 0.9375F;
        float f4 = flag3 ? 0.0F : f;
        float f5 = flag4 ? 1.0F : f1;
        float f6 = flag5 ? 0.0F : f;
        float f7 = flag6 ? 1.0F : f1;
        if(flag1)
        {
            blockfence.setBlockBounds(f4, f2, f, f5, f3, f1);
            renderStandardBlock(blockfence, i, j, k);
            flag = true;
        }
        if(flag2)
        {
            blockfence.setBlockBounds(f, f2, f6, f1, f3, f7);
            renderStandardBlock(blockfence, i, j, k);
            flag = true;
        }
        f2 = 0.375F;
        f3 = 0.5625F;
        if(flag1)
        {
            blockfence.setBlockBounds(f4, f2, f, f5, f3, f1);
            renderStandardBlock(blockfence, i, j, k);
            flag = true;
        }
        if(flag2)
        {
            blockfence.setBlockBounds(f, f2, f6, f1, f3, f7);
            renderStandardBlock(blockfence, i, j, k);
            flag = true;
        }
        blockfence.setBlockBoundsBasedOnState(blockAccess, i, j, k);
        return flag;
    }

    public boolean func_41088_a(BlockDragonEgg blockdragonegg, int i, int j, int k)
    {
        boolean flag = false;
        int l = 0;
        for(int i1 = 0; i1 < 8; i1++)
        {
            int j1 = 0;
            byte byte0 = 1;
            if(i1 == 0)
            {
                j1 = 2;
            }
            if(i1 == 1)
            {
                j1 = 3;
            }
            if(i1 == 2)
            {
                j1 = 4;
            }
            if(i1 == 3)
            {
                j1 = 5;
                byte0 = 2;
            }
            if(i1 == 4)
            {
                j1 = 6;
                byte0 = 3;
            }
            if(i1 == 5)
            {
                j1 = 7;
                byte0 = 5;
            }
            if(i1 == 6)
            {
                j1 = 6;
                byte0 = 2;
            }
            if(i1 == 7)
            {
                j1 = 3;
            }
            float f = (float)j1 / 16F;
            float f1 = 1.0F - (float)l / 16F;
            float f2 = 1.0F - (float)(l + byte0) / 16F;
            l += byte0;
            blockdragonegg.setBlockBounds(0.5F - f, f2, 0.5F - f, 0.5F + f, f1, 0.5F + f);
            renderStandardBlock(blockdragonegg, i, j, k);
        }

        flag = true;
        blockdragonegg.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return flag;
    }

    public boolean renderBlockFenceGate(BlockFenceGate blockfencegate, int i, int j, int k)
    {
        boolean flag = true;
        int l = blockAccess.getBlockMetadata(i, j, k);
        boolean flag1 = BlockFenceGate.isFenceGateOpen(l);
        int i1 = BlockFenceGate.func_35290_f(l);
        if(i1 == 3 || i1 == 1)
        {
            float f = 0.4375F;
            float f4 = 0.5625F;
            float f8 = 0.0F;
            float f12 = 0.125F;
            blockfencegate.setBlockBounds(f, 0.3125F, f8, f4, 1.0F, f12);
            renderStandardBlock(blockfencegate, i, j, k);
            f8 = 0.875F;
            f12 = 1.0F;
            blockfencegate.setBlockBounds(f, 0.3125F, f8, f4, 1.0F, f12);
            renderStandardBlock(blockfencegate, i, j, k);
        } else
        {
            float f1 = 0.0F;
            float f5 = 0.125F;
            float f9 = 0.4375F;
            float f13 = 0.5625F;
            blockfencegate.setBlockBounds(f1, 0.3125F, f9, f5, 1.0F, f13);
            renderStandardBlock(blockfencegate, i, j, k);
            f1 = 0.875F;
            f5 = 1.0F;
            blockfencegate.setBlockBounds(f1, 0.3125F, f9, f5, 1.0F, f13);
            renderStandardBlock(blockfencegate, i, j, k);
        }
        if(!flag1)
        {
            if(i1 == 3 || i1 == 1)
            {
                float f2 = 0.4375F;
                float f6 = 0.5625F;
                float f10 = 0.375F;
                float f14 = 0.5F;
                blockfencegate.setBlockBounds(f2, 0.375F, f10, f6, 0.9375F, f14);
                renderStandardBlock(blockfencegate, i, j, k);
                f10 = 0.5F;
                f14 = 0.625F;
                blockfencegate.setBlockBounds(f2, 0.375F, f10, f6, 0.9375F, f14);
                renderStandardBlock(blockfencegate, i, j, k);
                f10 = 0.625F;
                f14 = 0.875F;
                blockfencegate.setBlockBounds(f2, 0.375F, f10, f6, 0.5625F, f14);
                renderStandardBlock(blockfencegate, i, j, k);
                blockfencegate.setBlockBounds(f2, 0.75F, f10, f6, 0.9375F, f14);
                renderStandardBlock(blockfencegate, i, j, k);
                f10 = 0.125F;
                f14 = 0.375F;
                blockfencegate.setBlockBounds(f2, 0.375F, f10, f6, 0.5625F, f14);
                renderStandardBlock(blockfencegate, i, j, k);
                blockfencegate.setBlockBounds(f2, 0.75F, f10, f6, 0.9375F, f14);
                renderStandardBlock(blockfencegate, i, j, k);
            } else
            {
                float f3 = 0.375F;
                float f7 = 0.5F;
                float f11 = 0.4375F;
                float f15 = 0.5625F;
                blockfencegate.setBlockBounds(f3, 0.375F, f11, f7, 0.9375F, f15);
                renderStandardBlock(blockfencegate, i, j, k);
                f3 = 0.5F;
                f7 = 0.625F;
                blockfencegate.setBlockBounds(f3, 0.375F, f11, f7, 0.9375F, f15);
                renderStandardBlock(blockfencegate, i, j, k);
                f3 = 0.625F;
                f7 = 0.875F;
                blockfencegate.setBlockBounds(f3, 0.375F, f11, f7, 0.5625F, f15);
                renderStandardBlock(blockfencegate, i, j, k);
                blockfencegate.setBlockBounds(f3, 0.75F, f11, f7, 0.9375F, f15);
                renderStandardBlock(blockfencegate, i, j, k);
                f3 = 0.125F;
                f7 = 0.375F;
                blockfencegate.setBlockBounds(f3, 0.375F, f11, f7, 0.5625F, f15);
                renderStandardBlock(blockfencegate, i, j, k);
                blockfencegate.setBlockBounds(f3, 0.75F, f11, f7, 0.9375F, f15);
                renderStandardBlock(blockfencegate, i, j, k);
            }
        } else
        if(i1 == 3)
        {
            blockfencegate.setBlockBounds(0.8125F, 0.375F, 0.0F, 0.9375F, 0.9375F, 0.125F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.8125F, 0.375F, 0.875F, 0.9375F, 0.9375F, 1.0F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.5625F, 0.375F, 0.0F, 0.8125F, 0.5625F, 0.125F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.5625F, 0.375F, 0.875F, 0.8125F, 0.5625F, 1.0F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.5625F, 0.75F, 0.0F, 0.8125F, 0.9375F, 0.125F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.5625F, 0.75F, 0.875F, 0.8125F, 0.9375F, 1.0F);
            renderStandardBlock(blockfencegate, i, j, k);
        } else
        if(i1 == 1)
        {
            blockfencegate.setBlockBounds(0.0625F, 0.375F, 0.0F, 0.1875F, 0.9375F, 0.125F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.0625F, 0.375F, 0.875F, 0.1875F, 0.9375F, 1.0F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.1875F, 0.375F, 0.0F, 0.4375F, 0.5625F, 0.125F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.1875F, 0.375F, 0.875F, 0.4375F, 0.5625F, 1.0F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.1875F, 0.75F, 0.0F, 0.4375F, 0.9375F, 0.125F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.1875F, 0.75F, 0.875F, 0.4375F, 0.9375F, 1.0F);
            renderStandardBlock(blockfencegate, i, j, k);
        } else
        if(i1 == 0)
        {
            blockfencegate.setBlockBounds(0.0F, 0.375F, 0.8125F, 0.125F, 0.9375F, 0.9375F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.875F, 0.375F, 0.8125F, 1.0F, 0.9375F, 0.9375F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.0F, 0.375F, 0.5625F, 0.125F, 0.5625F, 0.8125F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.875F, 0.375F, 0.5625F, 1.0F, 0.5625F, 0.8125F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.0F, 0.75F, 0.5625F, 0.125F, 0.9375F, 0.8125F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.875F, 0.75F, 0.5625F, 1.0F, 0.9375F, 0.8125F);
            renderStandardBlock(blockfencegate, i, j, k);
        } else
        if(i1 == 2)
        {
            blockfencegate.setBlockBounds(0.0F, 0.375F, 0.0625F, 0.125F, 0.9375F, 0.1875F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.875F, 0.375F, 0.0625F, 1.0F, 0.9375F, 0.1875F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.0F, 0.375F, 0.1875F, 0.125F, 0.5625F, 0.4375F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.875F, 0.375F, 0.1875F, 1.0F, 0.5625F, 0.4375F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.0F, 0.75F, 0.1875F, 0.125F, 0.9375F, 0.4375F);
            renderStandardBlock(blockfencegate, i, j, k);
            blockfencegate.setBlockBounds(0.875F, 0.75F, 0.1875F, 1.0F, 0.9375F, 0.4375F);
            renderStandardBlock(blockfencegate, i, j, k);
        }
        blockfencegate.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return flag;
    }

    public boolean renderBlockStairs(Block block, int i, int j, int k)
    {
        boolean flag = false;
        int l = blockAccess.getBlockMetadata(i, j, k);
        if(l == 0)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
            renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderStandardBlock(block, i, j, k);
            flag = true;
        } else
        if(l == 1)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
            renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            renderStandardBlock(block, i, j, k);
            flag = true;
        } else
        if(l == 2)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
            renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
            renderStandardBlock(block, i, j, k);
            flag = true;
        } else
        if(l == 3)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
            renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
            renderStandardBlock(block, i, j, k);
            flag = true;
        }
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return flag;
    }

    public boolean renderBlockDoor(Block block, int i, int j, int k)
    {
        Tessellator tessellator = Tessellator.instance;
        BlockDoor blockdoor = (BlockDoor)block;
        boolean flag = false;
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        int l = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
        tessellator.setBrightness(block.minY <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j - 1, k) : l);
        tessellator.setColorOpaque_F(f, f, f);
        renderBottomFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 0));
        flag = true;
        tessellator.setBrightness(block.maxY >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j + 1, k) : l);
        tessellator.setColorOpaque_F(f1, f1, f1);
        renderTopFace(block, i, j, k, block.getBlockTexture(blockAccess, i, j, k, 1));
        flag = true;
        tessellator.setBrightness(block.minZ <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j, k - 1) : l);
        tessellator.setColorOpaque_F(f2, f2, f2);
        int i1 = block.getBlockTexture(blockAccess, i, j, k, 2);
        if(i1 < 0)
        {
            flipTexture = true;
            i1 = -i1;
        }
        renderEastFace(block, i, j, k, i1);
        flag = true;
        flipTexture = false;
        tessellator.setBrightness(block.maxZ >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i, j, k + 1) : l);
        tessellator.setColorOpaque_F(f2, f2, f2);
        i1 = block.getBlockTexture(blockAccess, i, j, k, 3);
        if(i1 < 0)
        {
            flipTexture = true;
            i1 = -i1;
        }
        renderWestFace(block, i, j, k, i1);
        flag = true;
        flipTexture = false;
        tessellator.setBrightness(block.minX <= 0.0D ? block.getMixedBrightnessForBlock(blockAccess, i - 1, j, k) : l);
        tessellator.setColorOpaque_F(f3, f3, f3);
        i1 = block.getBlockTexture(blockAccess, i, j, k, 4);
        if(i1 < 0)
        {
            flipTexture = true;
            i1 = -i1;
        }
        renderNorthFace(block, i, j, k, i1);
        flag = true;
        flipTexture = false;
        tessellator.setBrightness(block.maxX >= 1.0D ? block.getMixedBrightnessForBlock(blockAccess, i + 1, j, k) : l);
        tessellator.setColorOpaque_F(f3, f3, f3);
        i1 = block.getBlockTexture(blockAccess, i, j, k, 5);
        if(i1 < 0)
        {
            flipTexture = true;
            i1 = -i1;
        }
        renderSouthFace(block, i, j, k, i1);
        flag = true;
        flipTexture = false;
        return flag;
    }

    public void renderBottomFace(Block block, double d, double d1, double d2, 
            int i)
    {
        Tessellator tessellator = Tessellator.instance;
        if(overrideBlockTexture >= 0)
        {
            i = overrideBlockTexture;
        }
        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minX * 16D) / 256D;
        double d4 = (((double)j + block.maxX * 16D) - 0.01D) / 256D;
        double d5 = ((double)k + block.minZ * 16D) / 256D;
        double d6 = (((double)k + block.maxZ * 16D) - 0.01D) / 256D;
        if(block.minX < 0.0D || block.maxX > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if(block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;
        if(uvRotateBottom == 2)
        {
            d3 = ((double)j + block.minZ * 16D) / 256D;
            d5 = ((double)(k + 16) - block.maxX * 16D) / 256D;
            d4 = ((double)j + block.maxZ * 16D) / 256D;
            d6 = ((double)(k + 16) - block.minX * 16D) / 256D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        } else
        if(uvRotateBottom == 1)
        {
            d3 = ((double)(j + 16) - block.maxZ * 16D) / 256D;
            d5 = ((double)k + block.minX * 16D) / 256D;
            d4 = ((double)(j + 16) - block.minZ * 16D) / 256D;
            d6 = ((double)k + block.maxX * 16D) / 256D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d3 = d7;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        } else
        if(uvRotateBottom == 3)
        {
            d3 = ((double)(j + 16) - block.minX * 16D) / 256D;
            d4 = ((double)(j + 16) - block.maxX * 16D - 0.01D) / 256D;
            d5 = ((double)(k + 16) - block.minZ * 16D) / 256D;
            d6 = ((double)(k + 16) - block.maxZ * 16D - 0.01D) / 256D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }
        double d11 = d + block.minX;
        double d12 = d + block.maxX;
        double d13 = d1 + block.minY;
        double d14 = d2 + block.minZ;
        double d15 = d2 + block.maxZ;
        if(enableAO)
        {
            tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
            tessellator.setBrightness(brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
            tessellator.setBrightness(brightnessBottomLeft);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
            tessellator.setBrightness(brightnessBottomRight);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
            tessellator.setBrightness(brightnessTopRight);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        } else
        {
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        }
    }

    public void renderTopFace(Block block, double d, double d1, double d2, 
            int i)
    {
        Tessellator tessellator = Tessellator.instance;
        if(overrideBlockTexture >= 0)
        {
            i = overrideBlockTexture;
        }
        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minX * 16D) / 256D;
        double d4 = (((double)j + block.maxX * 16D) - 0.01D) / 256D;
        double d5 = ((double)k + block.minZ * 16D) / 256D;
        double d6 = (((double)k + block.maxZ * 16D) - 0.01D) / 256D;
        if(block.minX < 0.0D || block.maxX > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if(block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;
        if(uvRotateTop == 1)
        {
            d3 = ((double)j + block.minZ * 16D) / 256D;
            d5 = ((double)(k + 16) - block.maxX * 16D) / 256D;
            d4 = ((double)j + block.maxZ * 16D) / 256D;
            d6 = ((double)(k + 16) - block.minX * 16D) / 256D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        } else
        if(uvRotateTop == 2)
        {
            d3 = ((double)(j + 16) - block.maxZ * 16D) / 256D;
            d5 = ((double)k + block.minX * 16D) / 256D;
            d4 = ((double)(j + 16) - block.minZ * 16D) / 256D;
            d6 = ((double)k + block.maxX * 16D) / 256D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d3 = d7;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        } else
        if(uvRotateTop == 3)
        {
            d3 = ((double)(j + 16) - block.minX * 16D) / 256D;
            d4 = ((double)(j + 16) - block.maxX * 16D - 0.01D) / 256D;
            d5 = ((double)(k + 16) - block.minZ * 16D) / 256D;
            d6 = ((double)(k + 16) - block.maxZ * 16D - 0.01D) / 256D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }
        double d11 = d + block.minX;
        double d12 = d + block.maxX;
        double d13 = d1 + block.maxY;
        double d14 = d2 + block.minZ;
        double d15 = d2 + block.maxZ;
        if(enableAO)
        {
            tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
            tessellator.setBrightness(brightnessTopLeft);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
            tessellator.setBrightness(brightnessBottomLeft);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
            tessellator.setBrightness(brightnessBottomRight);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
            tessellator.setBrightness(brightnessTopRight);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        } else
        {
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        }
    }

    public void renderEastFace(Block block, double d, double d1, double d2, 
            int i)
    {
        Tessellator tessellator = Tessellator.instance;
        if(overrideBlockTexture >= 0)
        {
            i = overrideBlockTexture;
        }
        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minX * 16D) / 256D;
        double d4 = (((double)j + block.maxX * 16D) - 0.01D) / 256D;
        double d5 = ((double)(k + 16) - block.maxY * 16D) / 256D;
        double d6 = ((double)(k + 16) - block.minY * 16D - 0.01D) / 256D;
        if(flipTexture)
        {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if(block.minX < 0.0D || block.maxX > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if(block.minY < 0.0D || block.maxY > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if(uvRotateEast == 2)
        {
            d3 = ((double)j + block.minY * 16D) / 256D;
            d5 = ((double)(k + 16) - block.minX * 16D) / 256D;
            d4 = ((double)j + block.maxY * 16D) / 256D;
            d6 = ((double)(k + 16) - block.maxX * 16D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else
        if(uvRotateEast == 1)
        {
            d3 = ((double)(j + 16) - block.maxY * 16D) / 256D;
            d5 = ((double)k + block.maxX * 16D) / 256D;
            d4 = ((double)(j + 16) - block.minY * 16D) / 256D;
            d6 = ((double)k + block.minX * 16D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else
        if(uvRotateEast == 3)
        {
            d3 = ((double)(j + 16) - block.minX * 16D) / 256D;
            d4 = ((double)(j + 16) - block.maxX * 16D - 0.01D) / 256D;
            d5 = ((double)k + block.maxY * 16D) / 256D;
            d6 = (((double)k + block.minY * 16D) - 0.01D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.minX;
        double d13 = d + block.maxX;
        double d14 = d1 + block.minY;
        double d15 = d1 + block.maxY;
        double d16 = d2 + block.minZ;
        if(enableAO)
        {
            tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
            tessellator.setBrightness(brightnessTopLeft);
            tessellator.addVertexWithUV(d12, d15, d16, d8, d10);
            tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
            tessellator.setBrightness(brightnessBottomLeft);
            tessellator.addVertexWithUV(d13, d15, d16, d3, d5);
            tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
            tessellator.setBrightness(brightnessBottomRight);
            tessellator.addVertexWithUV(d13, d14, d16, d9, d11);
            tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
            tessellator.setBrightness(brightnessTopRight);
            tessellator.addVertexWithUV(d12, d14, d16, d4, d6);
        } else
        {
            tessellator.addVertexWithUV(d12, d15, d16, d8, d10);
            tessellator.addVertexWithUV(d13, d15, d16, d3, d5);
            tessellator.addVertexWithUV(d13, d14, d16, d9, d11);
            tessellator.addVertexWithUV(d12, d14, d16, d4, d6);
        }
    }

    public void renderWestFace(Block block, double d, double d1, double d2, 
            int i)
    {
        Tessellator tessellator = Tessellator.instance;
        if(overrideBlockTexture >= 0)
        {
            i = overrideBlockTexture;
        }
        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minX * 16D) / 256D;
        double d4 = (((double)j + block.maxX * 16D) - 0.01D) / 256D;
        double d5 = ((double)(k + 16) - block.maxY * 16D) / 256D;
        double d6 = ((double)(k + 16) - block.minY * 16D - 0.01D) / 256D;
        if(flipTexture)
        {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if(block.minX < 0.0D || block.maxX > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if(block.minY < 0.0D || block.maxY > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if(uvRotateWest == 1)
        {
            d3 = ((double)j + block.minY * 16D) / 256D;
            d6 = ((double)(k + 16) - block.minX * 16D) / 256D;
            d4 = ((double)j + block.maxY * 16D) / 256D;
            d5 = ((double)(k + 16) - block.maxX * 16D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else
        if(uvRotateWest == 2)
        {
            d3 = ((double)(j + 16) - block.maxY * 16D) / 256D;
            d5 = ((double)k + block.minX * 16D) / 256D;
            d4 = ((double)(j + 16) - block.minY * 16D) / 256D;
            d6 = ((double)k + block.maxX * 16D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else
        if(uvRotateWest == 3)
        {
            d3 = ((double)(j + 16) - block.minX * 16D) / 256D;
            d4 = ((double)(j + 16) - block.maxX * 16D - 0.01D) / 256D;
            d5 = ((double)k + block.maxY * 16D) / 256D;
            d6 = (((double)k + block.minY * 16D) - 0.01D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.minX;
        double d13 = d + block.maxX;
        double d14 = d1 + block.minY;
        double d15 = d1 + block.maxY;
        double d16 = d2 + block.maxZ;
        if(enableAO)
        {
            tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
            tessellator.setBrightness(brightnessTopLeft);
            tessellator.addVertexWithUV(d12, d15, d16, d3, d5);
            tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
            tessellator.setBrightness(brightnessBottomLeft);
            tessellator.addVertexWithUV(d12, d14, d16, d9, d11);
            tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
            tessellator.setBrightness(brightnessBottomRight);
            tessellator.addVertexWithUV(d13, d14, d16, d4, d6);
            tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
            tessellator.setBrightness(brightnessTopRight);
            tessellator.addVertexWithUV(d13, d15, d16, d8, d10);
        } else
        {
            tessellator.addVertexWithUV(d12, d15, d16, d3, d5);
            tessellator.addVertexWithUV(d12, d14, d16, d9, d11);
            tessellator.addVertexWithUV(d13, d14, d16, d4, d6);
            tessellator.addVertexWithUV(d13, d15, d16, d8, d10);
        }
    }

    public void renderNorthFace(Block block, double d, double d1, double d2, 
            int i)
    {
        Tessellator tessellator = Tessellator.instance;
        if(overrideBlockTexture >= 0)
        {
            i = overrideBlockTexture;
        }
        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minZ * 16D) / 256D;
        double d4 = (((double)j + block.maxZ * 16D) - 0.01D) / 256D;
        double d5 = ((double)(k + 16) - block.maxY * 16D) / 256D;
        double d6 = ((double)(k + 16) - block.minY * 16D - 0.01D) / 256D;
        if(flipTexture)
        {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if(block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if(block.minY < 0.0D || block.maxY > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if(uvRotateNorth == 1)
        {
            d3 = ((double)j + block.minY * 16D) / 256D;
            d5 = ((double)(k + 16) - block.maxZ * 16D) / 256D;
            d4 = ((double)j + block.maxY * 16D) / 256D;
            d6 = ((double)(k + 16) - block.minZ * 16D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else
        if(uvRotateNorth == 2)
        {
            d3 = ((double)(j + 16) - block.maxY * 16D) / 256D;
            d5 = ((double)k + block.minZ * 16D) / 256D;
            d4 = ((double)(j + 16) - block.minY * 16D) / 256D;
            d6 = ((double)k + block.maxZ * 16D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else
        if(uvRotateNorth == 3)
        {
            d3 = ((double)(j + 16) - block.minZ * 16D) / 256D;
            d4 = ((double)(j + 16) - block.maxZ * 16D - 0.01D) / 256D;
            d5 = ((double)k + block.maxY * 16D) / 256D;
            d6 = (((double)k + block.minY * 16D) - 0.01D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.minX;
        double d13 = d1 + block.minY;
        double d14 = d1 + block.maxY;
        double d15 = d2 + block.minZ;
        double d16 = d2 + block.maxZ;
        if(enableAO)
        {
            tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
            tessellator.setBrightness(brightnessTopLeft);
            tessellator.addVertexWithUV(d12, d14, d16, d8, d10);
            tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
            tessellator.setBrightness(brightnessBottomLeft);
            tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
            tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
            tessellator.setBrightness(brightnessBottomRight);
            tessellator.addVertexWithUV(d12, d13, d15, d9, d11);
            tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
            tessellator.setBrightness(brightnessTopRight);
            tessellator.addVertexWithUV(d12, d13, d16, d4, d6);
        } else
        {
            tessellator.addVertexWithUV(d12, d14, d16, d8, d10);
            tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
            tessellator.addVertexWithUV(d12, d13, d15, d9, d11);
            tessellator.addVertexWithUV(d12, d13, d16, d4, d6);
        }
    }

    public void renderSouthFace(Block block, double d, double d1, double d2, 
            int i)
    {
        Tessellator tessellator = Tessellator.instance;
        if(overrideBlockTexture >= 0)
        {
            i = overrideBlockTexture;
        }
        int j = (i & 0xf) << 4;
        int k = i & 0xf0;
        double d3 = ((double)j + block.minZ * 16D) / 256D;
        double d4 = (((double)j + block.maxZ * 16D) - 0.01D) / 256D;
        double d5 = ((double)(k + 16) - block.maxY * 16D) / 256D;
        double d6 = ((double)(k + 16) - block.minY * 16D - 0.01D) / 256D;
        if(flipTexture)
        {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if(block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            d3 = ((float)j + 0.0F) / 256F;
            d4 = ((float)j + 15.99F) / 256F;
        }
        if(block.minY < 0.0D || block.maxY > 1.0D)
        {
            d5 = ((float)k + 0.0F) / 256F;
            d6 = ((float)k + 15.99F) / 256F;
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if(uvRotateSouth == 2)
        {
            d3 = ((double)j + block.minY * 16D) / 256D;
            d5 = ((double)(k + 16) - block.minZ * 16D) / 256D;
            d4 = ((double)j + block.maxY * 16D) / 256D;
            d6 = ((double)(k + 16) - block.maxZ * 16D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else
        if(uvRotateSouth == 1)
        {
            d3 = ((double)(j + 16) - block.maxY * 16D) / 256D;
            d5 = ((double)k + block.maxZ * 16D) / 256D;
            d4 = ((double)(j + 16) - block.minY * 16D) / 256D;
            d6 = ((double)k + block.minZ * 16D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else
        if(uvRotateSouth == 3)
        {
            d3 = ((double)(j + 16) - block.minZ * 16D) / 256D;
            d4 = ((double)(j + 16) - block.maxZ * 16D - 0.01D) / 256D;
            d5 = ((double)k + block.maxY * 16D) / 256D;
            d6 = (((double)k + block.minY * 16D) - 0.01D) / 256D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.maxX;
        double d13 = d1 + block.minY;
        double d14 = d1 + block.maxY;
        double d15 = d2 + block.minZ;
        double d16 = d2 + block.maxZ;
        if(enableAO)
        {
            tessellator.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
            tessellator.setBrightness(brightnessTopLeft);
            tessellator.addVertexWithUV(d12, d13, d16, d9, d11);
            tessellator.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
            tessellator.setBrightness(brightnessBottomLeft);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
            tessellator.setBrightness(brightnessBottomRight);
            tessellator.addVertexWithUV(d12, d14, d15, d8, d10);
            tessellator.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
            tessellator.setBrightness(brightnessTopRight);
            tessellator.addVertexWithUV(d12, d14, d16, d3, d5);
        } else
        {
            tessellator.addVertexWithUV(d12, d13, d16, d9, d11);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.addVertexWithUV(d12, d14, d15, d8, d10);
            tessellator.addVertexWithUV(d12, d14, d16, d3, d5);
        }
    }

    public void renderBlockOnInventory(Block block, int i, float f)
    {
        Tessellator tessellator = Tessellator.instance;
        boolean flag = block.blockID == Block.grass.blockID;
        if(useInventoryTint)
        {
            int j = block.getRenderColor(i);
            if(flag)
            {
                j = 0xffffff;
            }
            float f1 = (float)(j >> 16 & 0xff) / 255F;
            float f3 = (float)(j >> 8 & 0xff) / 255F;
            float f7 = (float)(j & 0xff) / 255F;
            GL11.glColor4f(f1 * f, f3 * f, f7 * f, 1.0F);
        }
        int k = block.getRenderType();
        if(k == 0 || k == 16)
        {
            if(k == 16)
            {
                i = 1;
            }
            block.setBlockBoundsForItemRender();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, i));
            tessellator.draw();
            if(flag && useInventoryTint)
            {
                int l = block.getRenderColor(i);
                float f4 = (float)(l >> 16 & 0xff) / 255F;
                float f8 = (float)(l >> 8 & 0xff) / 255F;
                float f9 = (float)(l & 0xff) / 255F;
                GL11.glColor4f(f4 * f, f8 * f, f9 * f, 1.0F);
            }
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, i));
            tessellator.draw();
            if(flag && useInventoryTint)
            {
                GL11.glColor4f(f, f, f, 1.0F);
            }
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1F);
            renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1F, 0.0F, 0.0F);
            renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, i));
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        } else
        if(k == 1)
        {
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            renderCrossedSquares(block, i, -0.5D, -0.5D, -0.5D);
            tessellator.draw();
        } else
        if(k == 19)
        {
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            block.setBlockBoundsForItemRender();
            renderBlockStemSmall(block, i, block.maxY, -0.5D, -0.5D, -0.5D);
            tessellator.draw();
        } else
        if(k == 23)
        {
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            block.setBlockBoundsForItemRender();
            tessellator.draw();
        } else
        if(k == 13)
        {
            block.setBlockBoundsForItemRender();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float f2 = 0.0625F;
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1F);
            tessellator.setTranslationF(0.0F, 0.0F, f2);
            renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
            tessellator.setTranslationF(0.0F, 0.0F, -f2);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            tessellator.setTranslationF(0.0F, 0.0F, -f2);
            renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
            tessellator.setTranslationF(0.0F, 0.0F, f2);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1F, 0.0F, 0.0F);
            tessellator.setTranslationF(f2, 0.0F, 0.0F);
            renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
            tessellator.setTranslationF(-f2, 0.0F, 0.0F);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.setTranslationF(-f2, 0.0F, 0.0F);
            renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
            tessellator.setTranslationF(f2, 0.0F, 0.0F);
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        } else
        if(k == 22)
        {
            ChestItemRenderHelper.instance.func_35609_a(block, i, f);
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        } else
        if(k == 6)
        {
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            renderBlockCropsImpl(block, i, -0.5D, -0.5D, -0.5D);
            tessellator.draw();
        } else
        if(k == 2)
        {
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            renderTorchAtAngle(block, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D);
            tessellator.draw();
        } else
        if(k == 10)
        {
            for(int i1 = 0; i1 < 2; i1++)
            {
                if(i1 == 0)
                {
                    block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                }
                if(i1 == 1)
                {
                    block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
                }
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1F, 0.0F);
                renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1F);
                renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1F, 0.0F, 0.0F);
                renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
                tessellator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }

        } else
        if(k == 27)
        {
            int j1 = 0;
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.startDrawingQuads();
            for(int i2 = 0; i2 < 8; i2++)
            {
                int j2 = 0;
                byte byte0 = 1;
                if(i2 == 0)
                {
                    j2 = 2;
                }
                if(i2 == 1)
                {
                    j2 = 3;
                }
                if(i2 == 2)
                {
                    j2 = 4;
                }
                if(i2 == 3)
                {
                    j2 = 5;
                    byte0 = 2;
                }
                if(i2 == 4)
                {
                    j2 = 6;
                    byte0 = 3;
                }
                if(i2 == 5)
                {
                    j2 = 7;
                    byte0 = 5;
                }
                if(i2 == 6)
                {
                    j2 = 6;
                    byte0 = 2;
                }
                if(i2 == 7)
                {
                    j2 = 3;
                }
                float f10 = (float)j2 / 16F;
                float f11 = 1.0F - (float)j1 / 16F;
                float f12 = 1.0F - (float)(j1 + byte0) / 16F;
                j1 += byte0;
                block.setBlockBounds(0.5F - f10, f12, 0.5F - f10, 0.5F + f10, f11, 0.5F + f10);
                tessellator.setNormal(0.0F, -1F, 0.0F);
                renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
                tessellator.setNormal(0.0F, 0.0F, -1F);
                renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
                tessellator.setNormal(-1F, 0.0F, 0.0F);
                renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
            }

            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else
        if(k == 11)
        {
            for(int k1 = 0; k1 < 4; k1++)
            {
                float f5 = 0.125F;
                if(k1 == 0)
                {
                    block.setBlockBounds(0.5F - f5, 0.0F, 0.0F, 0.5F + f5, 1.0F, f5 * 2.0F);
                }
                if(k1 == 1)
                {
                    block.setBlockBounds(0.5F - f5, 0.0F, 1.0F - f5 * 2.0F, 0.5F + f5, 1.0F, 1.0F);
                }
                f5 = 0.0625F;
                if(k1 == 2)
                {
                    block.setBlockBounds(0.5F - f5, 1.0F - f5 * 3F, -f5 * 2.0F, 0.5F + f5, 1.0F - f5, 1.0F + f5 * 2.0F);
                }
                if(k1 == 3)
                {
                    block.setBlockBounds(0.5F - f5, 0.5F - f5 * 3F, -f5 * 2.0F, 0.5F + f5, 0.5F - f5, 1.0F + f5 * 2.0F);
                }
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1F, 0.0F);
                renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1F);
                renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1F, 0.0F, 0.0F);
                renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
                tessellator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }

            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else
        if(k == 21)
        {
            for(int l1 = 0; l1 < 3; l1++)
            {
                float f6 = 0.0625F;
                if(l1 == 0)
                {
                    block.setBlockBounds(0.5F - f6, 0.3F, 0.0F, 0.5F + f6, 1.0F, f6 * 2.0F);
                }
                if(l1 == 1)
                {
                    block.setBlockBounds(0.5F - f6, 0.3F, 1.0F - f6 * 2.0F, 0.5F + f6, 1.0F, 1.0F);
                }
                f6 = 0.0625F;
                if(l1 == 2)
                {
                    block.setBlockBounds(0.5F - f6, 0.5F, 0.0F, 0.5F + f6, 1.0F - f6, 1.0F);
                }
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1F, 0.0F);
                renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1F);
                renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1F, 0.0F, 0.0F);
                renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
                tessellator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }

            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public static boolean renderItemIn3d(int i)
    {
        if(i == 0)
        {
            return true;
        }
        if(i == 13)
        {
            return true;
        }
        if(i == 10)
        {
            return true;
        }
        if(i == 11)
        {
            return true;
        }
        if(i == 27)
        {
            return true;
        }
        if(i == 22)
        {
            return true;
        }
        if(i == 21)
        {
            return true;
        }
        return i == 16;
    }

}
