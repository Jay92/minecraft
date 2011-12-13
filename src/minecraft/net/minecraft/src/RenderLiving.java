// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            Render, ModelBase, EntityLiving, OpenGlHelper, 
//            MathHelper, RenderManager, Tessellator, FontRenderer, 
//            Entity

public class RenderLiving extends Render
{

    protected ModelBase mainModel;
    protected ModelBase renderPassModel;

    public RenderLiving(ModelBase modelbase, float f)
    {
        mainModel = modelbase;
        shadowSize = f;
    }

    public void setRenderPassModel(ModelBase modelbase)
    {
        renderPassModel = modelbase;
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        GL11.glPushMatrix();
        GL11.glDisable(2884 /*GL_CULL_FACE*/);
        mainModel.onGround = renderSwingProgress(entityliving, f1);
        if(renderPassModel != null)
        {
            renderPassModel.onGround = mainModel.onGround;
        }
        mainModel.isRiding = entityliving.isRiding();
        if(renderPassModel != null)
        {
            renderPassModel.isRiding = mainModel.isRiding;
        }
        mainModel.field_40301_k = entityliving.func_40127_l();
        if(renderPassModel != null)
        {
            renderPassModel.field_40301_k = mainModel.field_40301_k;
        }
        try
        {
            float f2 = entityliving.prevRenderYawOffset + (entityliving.renderYawOffset - entityliving.prevRenderYawOffset) * f1;
            float f3 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f1;
            float f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
            renderLivingAt(entityliving, d, d1, d2);
            float f5 = handleRotationFloat(entityliving, f1);
            rotateCorpse(entityliving, f5, f2, f1);
            float f6 = 0.0625F;
            GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
            GL11.glScalef(-1F, -1F, 1.0F);
            preRenderCallback(entityliving, f1);
            GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
            float f7 = entityliving.field_705_Q + (entityliving.field_704_R - entityliving.field_705_Q) * f1;
            float f8 = entityliving.field_703_S - entityliving.field_704_R * (1.0F - f1);
            if(entityliving.func_40127_l())
            {
                f8 *= 3F;
            }
            if(f7 > 1.0F)
            {
                f7 = 1.0F;
            }
            GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
            mainModel.setLivingAnimations(entityliving, f8, f7, f1);
            func_40270_a(entityliving, f8, f7, f5, f3 - f2, f4, f6);
            for(int i = 0; i < 4; i++)
            {
                int j = shouldRenderPass(entityliving, i, f1);
                if(j <= 0)
                {
                    continue;
                }
                renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
                if(j == 15)
                {
                    float f10 = (float)entityliving.ticksExisted + f1;
                    loadTexture("%blur%/misc/glint.png");
                    GL11.glEnable(3042 /*GL_BLEND*/);
                    float f12 = 0.5F;
                    GL11.glColor4f(f12, f12, f12, 1.0F);
                    GL11.glDepthFunc(514);
                    GL11.glDepthMask(false);
                    for(int i1 = 0; i1 < 2; i1++)
                    {
                        GL11.glDisable(2896 /*GL_LIGHTING*/);
                        float f15 = 0.76F;
                        GL11.glColor4f(0.5F * f15, 0.25F * f15, 0.8F * f15, 1.0F);
                        GL11.glBlendFunc(768, 1);
                        GL11.glMatrixMode(5890 /*GL_TEXTURE*/);
                        GL11.glLoadIdentity();
                        float f17 = f10 * (0.001F + (float)i1 * 0.003F) * 20F;
                        float f18 = 0.3333333F;
                        GL11.glScalef(f18, f18, f18);
                        GL11.glRotatef(30F - (float)i1 * 60F, 0.0F, 0.0F, 1.0F);
                        GL11.glTranslatef(0.0F, f17, 0.0F);
                        GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
                        renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glMatrixMode(5890 /*GL_TEXTURE*/);
                    GL11.glDepthMask(true);
                    GL11.glLoadIdentity();
                    GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
                    GL11.glEnable(2896 /*GL_LIGHTING*/);
                    GL11.glDisable(3042 /*GL_BLEND*/);
                    GL11.glDepthFunc(515);
                }
                GL11.glDisable(3042 /*GL_BLEND*/);
                GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
            }

            renderEquippedItems(entityliving, f1);
            float f9 = entityliving.getEntityBrightness(f1);
            int k = getColorMultiplier(entityliving, f9, f1);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapEnabled);
            GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapDisabled);
            if((k >> 24 & 0xff) > 0 || entityliving.hurtTime > 0 || entityliving.deathTime > 0)
            {
                GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
                GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
                GL11.glEnable(3042 /*GL_BLEND*/);
                GL11.glBlendFunc(770, 771);
                GL11.glDepthFunc(514);
                if(entityliving.hurtTime > 0 || entityliving.deathTime > 0)
                {
                    GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
                    mainModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
                    for(int l = 0; l < 4; l++)
                    {
                        if(inheritRenderPass(entityliving, l, f1) >= 0)
                        {
                            GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
                            renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
                        }
                    }

                }
                if((k >> 24 & 0xff) > 0)
                {
                    float f11 = (float)(k >> 16 & 0xff) / 255F;
                    float f13 = (float)(k >> 8 & 0xff) / 255F;
                    float f14 = (float)(k & 0xff) / 255F;
                    float f16 = (float)(k >> 24 & 0xff) / 255F;
                    GL11.glColor4f(f11, f13, f14, f16);
                    mainModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
                    for(int j1 = 0; j1 < 4; j1++)
                    {
                        if(inheritRenderPass(entityliving, j1, f1) >= 0)
                        {
                            GL11.glColor4f(f11, f13, f14, f16);
                            renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
                        }
                    }

                }
                GL11.glDepthFunc(515);
                GL11.glDisable(3042 /*GL_BLEND*/);
                GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
                GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
            }
            GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapEnabled);
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapDisabled);
        GL11.glEnable(2884 /*GL_CULL_FACE*/);
        GL11.glPopMatrix();
        passSpecialRender(entityliving, d, d1, d2);
    }

    protected void func_40270_a(EntityLiving entityliving, float f, float f1, float f2, float f3, float f4, float f5)
    {
        loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getEntityTexture());
        mainModel.render(entityliving, f, f1, f2, f3, f4, f5);
    }

    protected void renderLivingAt(EntityLiving entityliving, double d, double d1, double d2)
    {
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
    }

    protected void rotateCorpse(EntityLiving entityliving, float f, float f1, float f2)
    {
        GL11.glRotatef(180F - f1, 0.0F, 1.0F, 0.0F);
        if(entityliving.deathTime > 0)
        {
            float f3 = ((((float)entityliving.deathTime + f2) - 1.0F) / 20F) * 1.6F;
            f3 = MathHelper.sqrt_float(f3);
            if(f3 > 1.0F)
            {
                f3 = 1.0F;
            }
            GL11.glRotatef(f3 * getDeathMaxRotation(entityliving), 0.0F, 0.0F, 1.0F);
        }
    }

    protected float renderSwingProgress(EntityLiving entityliving, float f)
    {
        return entityliving.getSwingProgress(f);
    }

    protected float handleRotationFloat(EntityLiving entityliving, float f)
    {
        return (float)entityliving.ticksExisted + f;
    }

    protected void renderEquippedItems(EntityLiving entityliving, float f)
    {
    }

    protected int inheritRenderPass(EntityLiving entityliving, int i, float f)
    {
        return shouldRenderPass(entityliving, i, f);
    }

    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return -1;
    }

    protected float getDeathMaxRotation(EntityLiving entityliving)
    {
        return 90F;
    }

    protected int getColorMultiplier(EntityLiving entityliving, float f, float f1)
    {
        return 0;
    }

    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
    }

    protected void passSpecialRender(EntityLiving entityliving, double d, double d1, double d2)
    {
        if(!Minecraft.isDebugInfoEnabled());
    }

    protected void renderLivingLabel(EntityLiving entityliving, String s, double d, double d1, double d2, int i)
    {
        float f = entityliving.getDistanceToEntity(renderManager.livingPlayer);
        if(f > (float)i)
        {
            return;
        }
        FontRenderer fontrenderer = getFontRendererFromRenderManager();
        float f1 = 1.6F;
        float f2 = 0.01666667F * f1;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d + 0.0F, (float)d1 + 2.3F, (float)d2);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-f2, -f2, f2);
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDepthMask(false);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        Tessellator tessellator = Tessellator.instance;
        byte byte0 = 0;
        if(s.equals("deadmau5"))
        {
            byte0 = -10;
        }
        GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
        tessellator.startDrawingQuads();
        int j = fontrenderer.getStringWidth(s) / 2;
        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
        tessellator.addVertex(-j - 1, -1 + byte0, 0.0D);
        tessellator.addVertex(-j - 1, 8 + byte0, 0.0D);
        tessellator.addVertex(j + 1, 8 + byte0, 0.0D);
        tessellator.addVertex(j + 1, -1 + byte0, 0.0D);
        tessellator.draw();
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        GL11.glDepthMask(true);
        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(3042 /*GL_BLEND*/);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        doRenderLiving((EntityLiving)entity, d, d1, d2, f, f1);
    }
}
