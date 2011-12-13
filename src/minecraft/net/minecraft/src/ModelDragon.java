// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            ModelBase, ModelRenderer, EntityDragon, EntityLiving, 
//            Entity

public class ModelDragon extends ModelBase
{

    private ModelRenderer field_40314_a;
    private ModelRenderer field_40312_b;
    private ModelRenderer field_40313_c;
    private ModelRenderer field_40310_d;
    private ModelRenderer field_40311_e;
    private ModelRenderer field_40308_f;
    private ModelRenderer field_40309_g;
    private ModelRenderer field_40315_n;
    private ModelRenderer field_40316_o;
    private ModelRenderer field_40320_p;
    private ModelRenderer field_40319_q;
    private ModelRenderer field_40318_r;
    private float field_40317_s;

    public ModelDragon(float f)
    {
        textureWidth = 256;
        textureHeight = 256;
        setTextureOffset("body.body", 0, 0);
        setTextureOffset("wing.skin", -56, 88);
        setTextureOffset("wingtip.skin", -56, 144);
        setTextureOffset("rearleg.main", 0, 0);
        setTextureOffset("rearfoot.main", 112, 0);
        setTextureOffset("rearlegtip.main", 196, 0);
        setTextureOffset("head.upperhead", 112, 30);
        setTextureOffset("wing.bone", 112, 88);
        setTextureOffset("head.upperlip", 176, 44);
        setTextureOffset("jaw.jaw", 176, 65);
        setTextureOffset("frontleg.main", 112, 104);
        setTextureOffset("wingtip.bone", 112, 136);
        setTextureOffset("frontfoot.main", 144, 104);
        setTextureOffset("neck.box", 192, 104);
        setTextureOffset("frontlegtip.main", 226, 138);
        setTextureOffset("body.scale", 220, 53);
        setTextureOffset("head.scale", 0, 0);
        setTextureOffset("neck.scale", 48, 0);
        setTextureOffset("head.nostril", 112, 0);
        float f1 = -16F;
        field_40314_a = new ModelRenderer(this, "head");
        field_40314_a.addBox("upperlip", -6F, -1F, -8F + f1, 12, 5, 16);
        field_40314_a.addBox("upperhead", -8F, -8F, 6F + f1, 16, 16, 16);
        field_40314_a.mirror = true;
        field_40314_a.addBox("scale", -5F, -12F, 12F + f1, 2, 4, 6);
        field_40314_a.addBox("nostril", -5F, -3F, -6F + f1, 2, 2, 4);
        field_40314_a.mirror = false;
        field_40314_a.addBox("scale", 3F, -12F, 12F + f1, 2, 4, 6);
        field_40314_a.addBox("nostril", 3F, -3F, -6F + f1, 2, 2, 4);
        field_40313_c = new ModelRenderer(this, "jaw");
        field_40313_c.setRotationPoint(0.0F, 4F, 8F + f1);
        field_40313_c.addBox("jaw", -6F, 0.0F, -16F, 12, 4, 16);
        field_40314_a.addChild(field_40313_c);
        field_40312_b = new ModelRenderer(this, "neck");
        field_40312_b.addBox("box", -5F, -5F, -5F, 10, 10, 10);
        field_40312_b.addBox("scale", -1F, -9F, -3F, 2, 4, 6);
        field_40310_d = new ModelRenderer(this, "body");
        field_40310_d.setRotationPoint(0.0F, 4F, 8F);
        field_40310_d.addBox("body", -12F, 0.0F, -16F, 24, 24, 64);
        field_40310_d.addBox("scale", -1F, -6F, -10F, 2, 6, 12);
        field_40310_d.addBox("scale", -1F, -6F, 10F, 2, 6, 12);
        field_40310_d.addBox("scale", -1F, -6F, 30F, 2, 6, 12);
        field_40319_q = new ModelRenderer(this, "wing");
        field_40319_q.setRotationPoint(-12F, 5F, 2.0F);
        field_40319_q.addBox("bone", -56F, -4F, -4F, 56, 8, 8);
        field_40319_q.addBox("skin", -56F, 0.0F, 2.0F, 56, 0, 56);
        field_40318_r = new ModelRenderer(this, "wingtip");
        field_40318_r.setRotationPoint(-56F, 0.0F, 0.0F);
        field_40318_r.addBox("bone", -56F, -2F, -2F, 56, 4, 4);
        field_40318_r.addBox("skin", -56F, 0.0F, 2.0F, 56, 0, 56);
        field_40319_q.addChild(field_40318_r);
        field_40308_f = new ModelRenderer(this, "frontleg");
        field_40308_f.setRotationPoint(-12F, 20F, 2.0F);
        field_40308_f.addBox("main", -4F, -4F, -4F, 8, 24, 8);
        field_40315_n = new ModelRenderer(this, "frontlegtip");
        field_40315_n.setRotationPoint(0.0F, 20F, -1F);
        field_40315_n.addBox("main", -3F, -1F, -3F, 6, 24, 6);
        field_40308_f.addChild(field_40315_n);
        field_40320_p = new ModelRenderer(this, "frontfoot");
        field_40320_p.setRotationPoint(0.0F, 23F, 0.0F);
        field_40320_p.addBox("main", -4F, 0.0F, -12F, 8, 4, 16);
        field_40315_n.addChild(field_40320_p);
        field_40311_e = new ModelRenderer(this, "rearleg");
        field_40311_e.setRotationPoint(-16F, 16F, 42F);
        field_40311_e.addBox("main", -8F, -4F, -8F, 16, 32, 16);
        field_40309_g = new ModelRenderer(this, "rearlegtip");
        field_40309_g.setRotationPoint(0.0F, 32F, -4F);
        field_40309_g.addBox("main", -6F, -2F, 0.0F, 12, 32, 12);
        field_40311_e.addChild(field_40309_g);
        field_40316_o = new ModelRenderer(this, "rearfoot");
        field_40316_o.setRotationPoint(0.0F, 31F, 4F);
        field_40316_o.addBox("main", -9F, 0.0F, -20F, 18, 6, 24);
        field_40309_g.addChild(field_40316_o);
    }

    public void setLivingAnimations(EntityLiving entityliving, float f, float f1, float f2)
    {
        field_40317_s = f2;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        EntityDragon entitydragon = (EntityDragon)entity;
        float f6 = entitydragon.field_40173_aw + (entitydragon.field_40172_ax - entitydragon.field_40173_aw) * field_40317_s;
        field_40313_c.rotateAngleX = (float)(Math.sin(f6 * 3.141593F * 2.0F) + 1.0D) * 0.2F;
        float f7 = (float)(Math.sin(f6 * 3.141593F * 2.0F - 1.0F) + 1.0D);
        f7 = (f7 * f7 * 1.0F + f7 * 2.0F) * 0.05F;
        GL11.glTranslatef(0.0F, f7 - 2.0F, -3F);
        GL11.glRotatef(f7 * 2.0F, 1.0F, 0.0F, 0.0F);
        float f8 = -30F;
        float f9 = 22F;
        float f10 = 0.0F;
        float f11 = 1.5F;
        double ad[] = entitydragon.func_40160_a(6, field_40317_s);
        float f12 = func_40307_a(entitydragon.func_40160_a(5, field_40317_s)[0] - entitydragon.func_40160_a(10, field_40317_s)[0]);
        float f13 = func_40307_a(entitydragon.func_40160_a(5, field_40317_s)[0] + (double)(f12 / 2.0F));
        f8 += 2.0F;
        float f14 = 0.0F;
        float f15 = f6 * 3.141593F * 2.0F;
        f8 = 20F;
        f9 = -12F;
        for(int i = 0; i < 5; i++)
        {
            double ad3[] = entitydragon.func_40160_a(5 - i, field_40317_s);
            f14 = (float)Math.cos((float)i * 0.45F + f15) * 0.15F;
            field_40312_b.rotateAngleY = ((func_40307_a(ad3[0] - ad[0]) * 3.141593F) / 180F) * f11;
            field_40312_b.rotateAngleX = f14 + (((float)(ad3[1] - ad[1]) * 3.141593F) / 180F) * f11 * 5F;
            field_40312_b.rotateAngleZ = ((-func_40307_a(ad3[0] - (double)f13) * 3.141593F) / 180F) * f11;
            field_40312_b.rotationPointY = f8;
            field_40312_b.rotationPointZ = f9;
            field_40312_b.rotationPointX = f10;
            f8 = (float)((double)f8 + Math.sin(field_40312_b.rotateAngleX) * 10D);
            f9 = (float)((double)f9 - Math.cos(field_40312_b.rotateAngleY) * Math.cos(field_40312_b.rotateAngleX) * 10D);
            f10 = (float)((double)f10 - Math.sin(field_40312_b.rotateAngleY) * Math.cos(field_40312_b.rotateAngleX) * 10D);
            field_40312_b.render(f5);
        }

        field_40314_a.rotationPointY = f8;
        field_40314_a.rotationPointZ = f9;
        field_40314_a.rotationPointX = f10;
        double ad1[] = entitydragon.func_40160_a(0, field_40317_s);
        field_40314_a.rotateAngleY = ((func_40307_a(ad1[0] - ad[0]) * 3.141593F) / 180F) * 1.0F;
        field_40314_a.rotateAngleZ = ((-func_40307_a(ad1[0] - (double)f13) * 3.141593F) / 180F) * 1.0F;
        field_40314_a.render(f5);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-f12 * f11 * 1.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.0F, -1F, 0.0F);
        field_40310_d.rotateAngleZ = 0.0F;
        field_40310_d.render(f5);
        for(int j = 0; j < 2; j++)
        {
            GL11.glEnable(2884 /*GL_CULL_FACE*/);
            float f16 = f6 * 3.141593F * 2.0F;
            field_40319_q.rotateAngleX = 0.125F - (float)Math.cos(f16) * 0.2F;
            field_40319_q.rotateAngleY = 0.25F;
            field_40319_q.rotateAngleZ = (float)(Math.sin(f16) + 0.125D) * 0.8F;
            field_40318_r.rotateAngleZ = -(float)(Math.sin(f16 + 2.0F) + 0.5D) * 0.75F;
            field_40311_e.rotateAngleX = 1.0F + f7 * 0.1F;
            field_40309_g.rotateAngleX = 0.5F + f7 * 0.1F;
            field_40316_o.rotateAngleX = 0.75F + f7 * 0.1F;
            field_40308_f.rotateAngleX = 1.3F + f7 * 0.1F;
            field_40315_n.rotateAngleX = -0.5F - f7 * 0.1F;
            field_40320_p.rotateAngleX = 0.75F + f7 * 0.1F;
            field_40319_q.render(f5);
            field_40308_f.render(f5);
            field_40311_e.render(f5);
            GL11.glScalef(-1F, 1.0F, 1.0F);
            if(j == 0)
            {
                GL11.glCullFace(1028 /*GL_FRONT*/);
            }
        }

        GL11.glPopMatrix();
        GL11.glCullFace(1029 /*GL_BACK*/);
        GL11.glDisable(2884 /*GL_CULL_FACE*/);
        f14 = -(float)Math.sin(f6 * 3.141593F * 2.0F) * 0.0F;
        f15 = f6 * 3.141593F * 2.0F;
        f8 = 10F;
        f9 = 60F;
        f10 = 0.0F;
        ad = entitydragon.func_40160_a(11, field_40317_s);
        for(int k = 0; k < 12; k++)
        {
            double ad2[] = entitydragon.func_40160_a(12 + k, field_40317_s);
            f14 = (float)((double)f14 + Math.sin((float)k * 0.45F + f15) * 0.05000000074505806D);
            field_40312_b.rotateAngleY = ((func_40307_a(ad2[0] - ad[0]) * f11 + 180F) * 3.141593F) / 180F;
            field_40312_b.rotateAngleX = f14 + (((float)(ad2[1] - ad[1]) * 3.141593F) / 180F) * f11 * 5F;
            field_40312_b.rotateAngleZ = ((func_40307_a(ad2[0] - (double)f13) * 3.141593F) / 180F) * f11;
            field_40312_b.rotationPointY = f8;
            field_40312_b.rotationPointZ = f9;
            field_40312_b.rotationPointX = f10;
            f8 = (float)((double)f8 + Math.sin(field_40312_b.rotateAngleX) * 10D);
            f9 = (float)((double)f9 - Math.cos(field_40312_b.rotateAngleY) * Math.cos(field_40312_b.rotateAngleX) * 10D);
            f10 = (float)((double)f10 - Math.sin(field_40312_b.rotateAngleY) * Math.cos(field_40312_b.rotateAngleX) * 10D);
            field_40312_b.render(f5);
        }

        GL11.glPopMatrix();
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }

    private float func_40307_a(double d)
    {
        for(; d >= 180D; d -= 360D) { }
        for(; d < -180D; d += 360D) { }
        return (float)d;
    }
}
