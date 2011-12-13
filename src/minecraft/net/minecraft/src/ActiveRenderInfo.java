// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

// Referenced classes of package net.minecraft.src:
//            EntityPlayer, MathHelper, EntityLiving, Vec3D, 
//            ChunkPosition, World, Block, Material, 
//            BlockFluid, GLAllocation

public class ActiveRenderInfo
{

    public static float field_41074_a = 0.0F;
    public static float field_41072_b = 0.0F;
    public static float field_41073_c = 0.0F;
    private static IntBuffer field_41079_i = GLAllocation.createDirectIntBuffer(16);
    private static FloatBuffer field_41076_j = GLAllocation.createDirectFloatBuffer(16);
    private static FloatBuffer field_41077_k = GLAllocation.createDirectFloatBuffer(16);
    private static FloatBuffer field_41075_l = GLAllocation.createDirectFloatBuffer(3);
    public static float field_41070_d;
    public static float field_41071_e;
    public static float field_41068_f;
    public static float field_41069_g;
    public static float field_41078_h;

    public ActiveRenderInfo()
    {
    }

    public static void func_41067_a(EntityPlayer entityplayer, boolean flag)
    {
        GL11.glGetFloat(2982 /*GL_MODELVIEW_MATRIX*/, field_41076_j);
        GL11.glGetFloat(2983 /*GL_PROJECTION_MATRIX*/, field_41077_k);
        GL11.glGetInteger(2978 /*GL_VIEWPORT*/, field_41079_i);
        float f = (field_41079_i.get(0) + field_41079_i.get(2)) / 2;
        float f1 = (field_41079_i.get(1) + field_41079_i.get(3)) / 2;
        GLU.gluUnProject(f, f1, 0.0F, field_41076_j, field_41077_k, field_41079_i, field_41075_l);
        field_41074_a = field_41075_l.get(0);
        field_41072_b = field_41075_l.get(1);
        field_41073_c = field_41075_l.get(2);
        int i = flag ? 1 : 0;
        float f2 = entityplayer.rotationPitch;
        float f3 = entityplayer.rotationYaw;
        field_41070_d = MathHelper.cos((f3 * 3.141593F) / 180F) * (float)(1 - i * 2);
        field_41068_f = MathHelper.sin((f3 * 3.141593F) / 180F) * (float)(1 - i * 2);
        field_41069_g = -field_41068_f * MathHelper.sin((f2 * 3.141593F) / 180F) * (float)(1 - i * 2);
        field_41078_h = field_41070_d * MathHelper.sin((f2 * 3.141593F) / 180F) * (float)(1 - i * 2);
        field_41071_e = MathHelper.cos((f2 * 3.141593F) / 180F);
    }

    public static Vec3D func_41065_a(EntityLiving entityliving, double d)
    {
        double d1 = entityliving.prevPosX + (entityliving.posX - entityliving.prevPosX) * d;
        double d2 = entityliving.prevPosY + (entityliving.posY - entityliving.prevPosY) * d + (double)entityliving.getEyeHeight();
        double d3 = entityliving.prevPosZ + (entityliving.posZ - entityliving.prevPosZ) * d;
        double d4 = d1 + (double)(field_41074_a * 1.0F);
        double d5 = d2 + (double)(field_41072_b * 1.0F);
        double d6 = d3 + (double)(field_41073_c * 1.0F);
        return Vec3D.createVector(d4, d5, d6);
    }

    public static int func_41066_a(World world, EntityLiving entityliving, float f)
    {
        Vec3D vec3d = func_41065_a(entityliving, f);
        ChunkPosition chunkposition = new ChunkPosition(vec3d);
        int i = world.getBlockId(chunkposition.x, chunkposition.y, chunkposition.z);
        if(i != 0 && Block.blocksList[i].blockMaterial.getIsLiquid())
        {
            float f1 = BlockFluid.getFluidHeightPercent(world.getBlockMetadata(chunkposition.x, chunkposition.y, chunkposition.z)) - 0.1111111F;
            float f2 = (float)(chunkposition.y + 1) - f1;
            if(vec3d.yCoord >= (double)f2)
            {
                i = world.getBlockId(chunkposition.x, chunkposition.y + 1, chunkposition.z);
            }
        }
        return i;
    }

}
