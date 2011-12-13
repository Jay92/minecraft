// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

// Referenced classes of package net.minecraft.src:
//            MouseFilter, GLAllocation, ItemRenderer, RenderEngine, 
//            GameSettings, EntityLiving, MathHelper, World, 
//            PlayerController, MovingObjectPosition, Vec3D, AxisAlignedBB, 
//            Entity, EntityPlayerSP, EntityPlayer, ActiveRenderInfo, 
//            Block, Material, RenderGlobal, Potion, 
//            OpenGlHelper, WorldProvider, Profiler, MouseHelper, 
//            ScaledResolution, GuiIngame, GuiScreen, GuiParticle, 
//            ChunkProviderLoadOrGenerate, ClippingHelperImpl, Frustrum, ICamera, 
//            RenderHelper, EffectRenderer, InventoryPlayer, WorldChunkManager, 
//            BiomeGenBase, EntitySmokeFX, EntityRainFX, Tessellator, 
//            PotionEffect

public class EntityRenderer
{

    public static boolean anaglyphEnable = false;
    public static int anaglyphField;
    private Minecraft mc;
    private float farPlaneDistance;
    public ItemRenderer itemRenderer;
    private int rendererUpdateCount;
    private Entity pointedEntity;
    private MouseFilter mouseFilterXAxis;
    private MouseFilter mouseFilterYAxis;
    private MouseFilter mouseFilterDummy1;
    private MouseFilter mouseFilterDummy2;
    private MouseFilter mouseFilterDummy3;
    private MouseFilter mouseFilterDummy4;
    private float thirdPersonDistance;
    private float thirdPersonDistanceTemp;
    private float debugCamYaw;
    private float prevDebugCamYaw;
    private float debugCamPitch;
    private float prevDebugCamPitch;
    private float smoothCamYaw;
    private float smoothCamPitch;
    private float smoothCamFilterX;
    private float smoothCamFilterY;
    private float smoothCamPartialTicks;
    private float debugCamFOV;
    private float prevDebugCamFOV;
    private float camRoll;
    private float prevCamRoll;
    public int emptyTexture;
    private int lightmapColors[];
    private float fovModifierHand;
    private float fovModifierHandPrev;
    private float fovMultiplierTemp;
    private boolean cloudFog;
    private double cameraZoom;
    private double cameraYaw;
    private double cameraPitch;
    private long prevFrameTime;
    private long renderEndNanoTime;
    private boolean lightmapUpdateNeeded;
    float torchFlickerX;
    float torchFlickerDX;
    float torchFlickerY;
    float torchFlickerDY;
    private Random random;
    private int rainSoundCounter;
    float rainXCoords[];
    float rainYCoords[];
    volatile int unusedVolatile0;
    volatile int unusedVolatile1;
    FloatBuffer fogColorBuffer;
    float fogColorRed;
    float fogColorGreen;
    float fogColorBlue;
    private float fogColor2;
    private float fogColor1;
    public int debugViewDirection;

    public EntityRenderer(Minecraft minecraft)
    {
        farPlaneDistance = 0.0F;
        pointedEntity = null;
        mouseFilterXAxis = new MouseFilter();
        mouseFilterYAxis = new MouseFilter();
        mouseFilterDummy1 = new MouseFilter();
        mouseFilterDummy2 = new MouseFilter();
        mouseFilterDummy3 = new MouseFilter();
        mouseFilterDummy4 = new MouseFilter();
        thirdPersonDistance = 4F;
        thirdPersonDistanceTemp = 4F;
        debugCamYaw = 0.0F;
        prevDebugCamYaw = 0.0F;
        debugCamPitch = 0.0F;
        prevDebugCamPitch = 0.0F;
        debugCamFOV = 0.0F;
        prevDebugCamFOV = 0.0F;
        camRoll = 0.0F;
        prevCamRoll = 0.0F;
        cloudFog = false;
        cameraZoom = 1.0D;
        cameraYaw = 0.0D;
        cameraPitch = 0.0D;
        prevFrameTime = System.currentTimeMillis();
        renderEndNanoTime = 0L;
        lightmapUpdateNeeded = false;
        torchFlickerX = 0.0F;
        torchFlickerDX = 0.0F;
        torchFlickerY = 0.0F;
        torchFlickerDY = 0.0F;
        random = new Random();
        rainSoundCounter = 0;
        unusedVolatile0 = 0;
        unusedVolatile1 = 0;
        fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
        mc = minecraft;
        itemRenderer = new ItemRenderer(minecraft);
        emptyTexture = minecraft.renderEngine.allocateAndSetupTexture(new BufferedImage(16, 16, 1));
        lightmapColors = new int[256];
    }

    public void updateRenderer()
    {
        updateFovModifierHand();
        updateTorchFlicker();
        fogColor2 = fogColor1;
        thirdPersonDistanceTemp = thirdPersonDistance;
        prevDebugCamYaw = debugCamYaw;
        prevDebugCamPitch = debugCamPitch;
        prevDebugCamFOV = debugCamFOV;
        prevCamRoll = camRoll;
        if(mc.gameSettings.smoothCamera)
        {
            float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f2 = f * f * f * 8F;
            smoothCamFilterX = mouseFilterXAxis.func_22386_a(smoothCamYaw, 0.05F * f2);
            smoothCamFilterY = mouseFilterYAxis.func_22386_a(smoothCamPitch, 0.05F * f2);
            smoothCamPartialTicks = 0.0F;
            smoothCamYaw = 0.0F;
            smoothCamPitch = 0.0F;
        }
        if(mc.renderViewEntity == null)
        {
            mc.renderViewEntity = mc.thePlayer;
        }
        float f1 = mc.theWorld.getLightBrightness(MathHelper.floor_double(mc.renderViewEntity.posX), MathHelper.floor_double(mc.renderViewEntity.posY), MathHelper.floor_double(mc.renderViewEntity.posZ));
        float f3 = (float)(3 - mc.gameSettings.renderDistance) / 3F;
        float f4 = f1 * (1.0F - f3) + f3;
        fogColor1 += (f4 - fogColor1) * 0.1F;
        rendererUpdateCount++;
        itemRenderer.updateEquippedItem();
        addRainParticles();
    }

    public void getMouseOver(float f)
    {
        if(mc.renderViewEntity == null)
        {
            return;
        }
        if(mc.theWorld == null)
        {
            return;
        }
        double d = mc.playerController.getBlockReachDistance();
        mc.objectMouseOver = mc.renderViewEntity.rayTrace(d, f);
        double d1 = d;
        Vec3D vec3d = mc.renderViewEntity.getPosition(f);
        if(mc.objectMouseOver != null)
        {
            d1 = mc.objectMouseOver.hitVec.distanceTo(vec3d);
        }
        if(mc.playerController.extendedReach())
        {
            d1 = d = 6D;
        } else
        {
            if(d1 > 3D)
            {
                d1 = 3D;
            }
            d = d1;
        }
        Vec3D vec3d1 = mc.renderViewEntity.getLook(f);
        Vec3D vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
        pointedEntity = null;
        float f1 = 1.0F;
        java.util.List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d).expand(f1, f1, f1));
        double d2 = 0.0D;
        for(int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity)list.get(i);
            if(!entity.canBeCollidedWith())
            {
                continue;
            }
            float f2 = entity.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f2, f2, f2);
            MovingObjectPosition movingobjectposition = axisalignedbb.func_1169_a(vec3d, vec3d2);
            if(axisalignedbb.isVecInside(vec3d))
            {
                if(0.0D < d2 || d2 == 0.0D)
                {
                    pointedEntity = entity;
                    d2 = 0.0D;
                }
                continue;
            }
            if(movingobjectposition == null)
            {
                continue;
            }
            double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
            if(d3 < d2 || d2 == 0.0D)
            {
                pointedEntity = entity;
                d2 = d3;
            }
        }

        if(pointedEntity != null)
        {
            mc.objectMouseOver = new MovingObjectPosition(pointedEntity);
        }
    }

    private void updateFovModifierHand()
    {
        EntityPlayerSP entityplayersp = (EntityPlayerSP)mc.renderViewEntity;
        fovMultiplierTemp = entityplayersp.getFOVMultiplier();
        fovModifierHandPrev = fovModifierHand;
        fovModifierHand += (fovMultiplierTemp - fovModifierHand) * 0.5F;
    }

    private float getFOVModifier(float f, boolean flag)
    {
        if(debugViewDirection > 0)
        {
            return 90F;
        }
        EntityPlayer entityplayer = (EntityPlayer)mc.renderViewEntity;
        float f1 = 70F;
        if(flag)
        {
            f1 += mc.gameSettings.fovSetting * 40F;
            f1 *= fovModifierHandPrev + (fovModifierHand - fovModifierHandPrev) * f;
        }
        if(entityplayer.getEntityHealth() <= 0)
        {
            float f2 = (float)entityplayer.deathTime + f;
            f1 /= (1.0F - 500F / (f2 + 500F)) * 2.0F + 1.0F;
        }
        int i = ActiveRenderInfo.func_41066_a(mc.theWorld, entityplayer, f);
        if(i != 0 && Block.blocksList[i].blockMaterial == Material.water)
        {
            f1 = (f1 * 60F) / 70F;
        }
        return f1 + prevDebugCamFOV + (debugCamFOV - prevDebugCamFOV) * f;
    }

    private void hurtCameraEffect(float f)
    {
        EntityLiving entityliving = mc.renderViewEntity;
        float f1 = (float)entityliving.hurtTime - f;
        if(entityliving.getEntityHealth() <= 0)
        {
            float f2 = (float)entityliving.deathTime + f;
            GL11.glRotatef(40F - 8000F / (f2 + 200F), 0.0F, 0.0F, 1.0F);
        }
        if(f1 < 0.0F)
        {
            return;
        } else
        {
            f1 /= entityliving.maxHurtTime;
            f1 = MathHelper.sin(f1 * f1 * f1 * f1 * 3.141593F);
            float f3 = entityliving.attackedAtYaw;
            GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f1 * 14F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
            return;
        }
    }

    private void setupViewBobbing(float f)
    {
        if(!(mc.renderViewEntity instanceof EntityPlayer))
        {
            return;
        } else
        {
            EntityPlayer entityplayer = (EntityPlayer)mc.renderViewEntity;
            float f1 = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
            float f2 = -(entityplayer.distanceWalkedModified + f1 * f);
            float f3 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * f;
            float f4 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * f;
            GL11.glTranslatef(MathHelper.sin(f2 * 3.141593F) * f3 * 0.5F, -Math.abs(MathHelper.cos(f2 * 3.141593F) * f3), 0.0F);
            GL11.glRotatef(MathHelper.sin(f2 * 3.141593F) * f3 * 3F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(Math.abs(MathHelper.cos(f2 * 3.141593F - 0.2F) * f3) * 5F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
            return;
        }
    }

    private void orientCamera(float f)
    {
        EntityLiving entityliving = mc.renderViewEntity;
        float f1 = entityliving.yOffset - 1.62F;
        double d = entityliving.prevPosX + (entityliving.posX - entityliving.prevPosX) * (double)f;
        double d1 = (entityliving.prevPosY + (entityliving.posY - entityliving.prevPosY) * (double)f) - (double)f1;
        double d2 = entityliving.prevPosZ + (entityliving.posZ - entityliving.prevPosZ) * (double)f;
        GL11.glRotatef(prevCamRoll + (camRoll - prevCamRoll) * f, 0.0F, 0.0F, 1.0F);
        if(entityliving.isPlayerSleeping())
        {
            f1 = (float)((double)f1 + 1.0D);
            GL11.glTranslatef(0.0F, 0.3F, 0.0F);
            if(!mc.gameSettings.debugCamEnable)
            {
                int i = mc.theWorld.getBlockId(MathHelper.floor_double(entityliving.posX), MathHelper.floor_double(entityliving.posY), MathHelper.floor_double(entityliving.posZ));
                if(i == Block.bed.blockID)
                {
                    int j = mc.theWorld.getBlockMetadata(MathHelper.floor_double(entityliving.posX), MathHelper.floor_double(entityliving.posY), MathHelper.floor_double(entityliving.posZ));
                    int k = j & 3;
                    GL11.glRotatef(k * 90, 0.0F, 1.0F, 0.0F);
                }
                GL11.glRotatef(entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f + 180F, 0.0F, -1F, 0.0F);
                GL11.glRotatef(entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f, -1F, 0.0F, 0.0F);
            }
        } else
        if(mc.gameSettings.thirdPersonView > 0)
        {
            double d3 = thirdPersonDistanceTemp + (thirdPersonDistance - thirdPersonDistanceTemp) * f;
            if(mc.gameSettings.debugCamEnable)
            {
                float f2 = prevDebugCamYaw + (debugCamYaw - prevDebugCamYaw) * f;
                float f4 = prevDebugCamPitch + (debugCamPitch - prevDebugCamPitch) * f;
                GL11.glTranslatef(0.0F, 0.0F, (float)(-d3));
                GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
            } else
            {
                float f3 = entityliving.rotationYaw;
                float f5 = entityliving.rotationPitch;
                if(mc.gameSettings.thirdPersonView == 2)
                {
                    f5 += 180F;
                }
                double d4 = (double)(-MathHelper.sin((f3 / 180F) * 3.141593F) * MathHelper.cos((f5 / 180F) * 3.141593F)) * d3;
                double d5 = (double)(MathHelper.cos((f3 / 180F) * 3.141593F) * MathHelper.cos((f5 / 180F) * 3.141593F)) * d3;
                double d6 = (double)(-MathHelper.sin((f5 / 180F) * 3.141593F)) * d3;
                for(int l = 0; l < 8; l++)
                {
                    float f6 = (l & 1) * 2 - 1;
                    float f7 = (l >> 1 & 1) * 2 - 1;
                    float f8 = (l >> 2 & 1) * 2 - 1;
                    f6 *= 0.1F;
                    f7 *= 0.1F;
                    f8 *= 0.1F;
                    MovingObjectPosition movingobjectposition = mc.theWorld.rayTraceBlocks(Vec3D.createVector(d + (double)f6, d1 + (double)f7, d2 + (double)f8), Vec3D.createVector((d - d4) + (double)f6 + (double)f8, (d1 - d6) + (double)f7, (d2 - d5) + (double)f8));
                    if(movingobjectposition == null)
                    {
                        continue;
                    }
                    double d7 = movingobjectposition.hitVec.distanceTo(Vec3D.createVector(d, d1, d2));
                    if(d7 < d3)
                    {
                        d3 = d7;
                    }
                }

                if(mc.gameSettings.thirdPersonView == 2)
                {
                    GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                }
                GL11.glRotatef(entityliving.rotationPitch - f5, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(entityliving.rotationYaw - f3, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.0F, (float)(-d3));
                GL11.glRotatef(f3 - entityliving.rotationYaw, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(f5 - entityliving.rotationPitch, 1.0F, 0.0F, 0.0F);
            }
        } else
        {
            GL11.glTranslatef(0.0F, 0.0F, -0.1F);
        }
        if(!mc.gameSettings.debugCamEnable)
        {
            GL11.glRotatef(entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f + 180F, 0.0F, 1.0F, 0.0F);
        }
        GL11.glTranslatef(0.0F, f1, 0.0F);
        d = entityliving.prevPosX + (entityliving.posX - entityliving.prevPosX) * (double)f;
        d1 = (entityliving.prevPosY + (entityliving.posY - entityliving.prevPosY) * (double)f) - (double)f1;
        d2 = entityliving.prevPosZ + (entityliving.posZ - entityliving.prevPosZ) * (double)f;
        cloudFog = mc.renderGlobal.func_27307_a(d, d1, d2, f);
    }

    private void setupCameraTransform(float f, int i)
    {
        farPlaneDistance = 256 >> mc.gameSettings.renderDistance;
        GL11.glMatrixMode(5889 /*GL_PROJECTION*/);
        GL11.glLoadIdentity();
        float f1 = 0.07F;
        if(mc.gameSettings.anaglyph)
        {
            GL11.glTranslatef((float)(-(i * 2 - 1)) * f1, 0.0F, 0.0F);
        }
        if(cameraZoom != 1.0D)
        {
            GL11.glTranslatef((float)cameraYaw, (float)(-cameraPitch), 0.0F);
            GL11.glScaled(cameraZoom, cameraZoom, 1.0D);
            GLU.gluPerspective(getFOVModifier(f, true), (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
        } else
        {
            GLU.gluPerspective(getFOVModifier(f, true), (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
        }
        if(mc.playerController.func_35643_e())
        {
            float f2 = 0.6666667F;
            GL11.glScalef(1.0F, f2, 1.0F);
        }
        GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
        GL11.glLoadIdentity();
        if(mc.gameSettings.anaglyph)
        {
            GL11.glTranslatef((float)(i * 2 - 1) * 0.1F, 0.0F, 0.0F);
        }
        hurtCameraEffect(f);
        if(mc.gameSettings.viewBobbing)
        {
            setupViewBobbing(f);
        }
        float f3 = mc.thePlayer.prevTimeInPortal + (mc.thePlayer.timeInPortal - mc.thePlayer.prevTimeInPortal) * f;
        if(f3 > 0.0F)
        {
            int j = 20;
            if(mc.thePlayer.isPotionActive(Potion.potionConfusion))
            {
                j = 7;
            }
            float f4 = 5F / (f3 * f3 + 5F) - f3 * 0.04F;
            f4 *= f4;
            GL11.glRotatef(((float)rendererUpdateCount + f) * (float)j, 0.0F, 1.0F, 1.0F);
            GL11.glScalef(1.0F / f4, 1.0F, 1.0F);
            GL11.glRotatef(-((float)rendererUpdateCount + f) * (float)j, 0.0F, 1.0F, 1.0F);
        }
        orientCamera(f);
        if(debugViewDirection > 0)
        {
            int k = debugViewDirection - 1;
            if(k == 1)
            {
                GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            }
            if(k == 2)
            {
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            }
            if(k == 3)
            {
                GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
            }
            if(k == 4)
            {
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            }
            if(k == 5)
            {
                GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
            }
        }
    }

    private void renderHand(float f, int i)
    {
        if(debugViewDirection > 0)
        {
            return;
        }
        GL11.glMatrixMode(5889 /*GL_PROJECTION*/);
        GL11.glLoadIdentity();
        float f1 = 0.07F;
        if(mc.gameSettings.anaglyph)
        {
            GL11.glTranslatef((float)(-(i * 2 - 1)) * f1, 0.0F, 0.0F);
        }
        if(cameraZoom != 1.0D)
        {
            GL11.glTranslatef((float)cameraYaw, (float)(-cameraPitch), 0.0F);
            GL11.glScaled(cameraZoom, cameraZoom, 1.0D);
            GLU.gluPerspective(getFOVModifier(f, false), (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
        } else
        {
            GLU.gluPerspective(getFOVModifier(f, false), (float)mc.displayWidth / (float)mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
        }
        if(mc.playerController.func_35643_e())
        {
            float f2 = 0.6666667F;
            GL11.glScalef(1.0F, f2, 1.0F);
        }
        GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
        GL11.glLoadIdentity();
        if(mc.gameSettings.anaglyph)
        {
            GL11.glTranslatef((float)(i * 2 - 1) * 0.1F, 0.0F, 0.0F);
        }
        GL11.glPushMatrix();
        hurtCameraEffect(f);
        if(mc.gameSettings.viewBobbing)
        {
            setupViewBobbing(f);
        }
        if(mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping() && !mc.gameSettings.hideGUI && !mc.playerController.func_35643_e())
        {
            enableLightmap(f);
            itemRenderer.renderItemInFirstPerson(f);
            disableLightmap(f);
        }
        GL11.glPopMatrix();
        if(mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping())
        {
            itemRenderer.renderOverlays(f);
            hurtCameraEffect(f);
        }
        if(mc.gameSettings.viewBobbing)
        {
            setupViewBobbing(f);
        }
    }

    public void disableLightmap(double d)
    {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapEnabled);
        GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapDisabled);
    }

    public void enableLightmap(double d)
    {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapEnabled);
        GL11.glMatrixMode(5890 /*GL_TEXTURE*/);
        GL11.glLoadIdentity();
        float f = 0.00390625F;
        GL11.glScalef(f, f, f);
        GL11.glTranslatef(8F, 8F, 8F);
        GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
        mc.renderEngine.bindTexture(emptyTexture);
        GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10241 /*GL_TEXTURE_MIN_FILTER*/, 9729 /*GL_LINEAR*/);
        GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10240 /*GL_TEXTURE_MAG_FILTER*/, 9729 /*GL_LINEAR*/);
        GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10241 /*GL_TEXTURE_MIN_FILTER*/, 9729 /*GL_LINEAR*/);
        GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10240 /*GL_TEXTURE_MAG_FILTER*/, 9729 /*GL_LINEAR*/);
        GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10242 /*GL_TEXTURE_WRAP_S*/, 10496 /*GL_CLAMP*/);
        GL11.glTexParameteri(3553 /*GL_TEXTURE_2D*/, 10243 /*GL_TEXTURE_WRAP_T*/, 10496 /*GL_CLAMP*/);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapDisabled);
    }

    private void updateTorchFlicker()
    {
        torchFlickerDX += (Math.random() - Math.random()) * Math.random() * Math.random();
        torchFlickerDY += (Math.random() - Math.random()) * Math.random() * Math.random();
        torchFlickerDX *= 0.90000000000000002D;
        torchFlickerDY *= 0.90000000000000002D;
        torchFlickerX += (torchFlickerDX - torchFlickerX) * 1.0F;
        torchFlickerY += (torchFlickerDY - torchFlickerY) * 1.0F;
        lightmapUpdateNeeded = true;
    }

    private void updateLightmap()
    {
        World world = mc.theWorld;
        if(world == null)
        {
            return;
        }
        for(int i = 0; i < 256; i++)
        {
            float f = world.func_35464_b(1.0F) * 0.95F + 0.05F;
            float f1 = world.worldProvider.lightBrightnessTable[i / 16] * f;
            float f2 = world.worldProvider.lightBrightnessTable[i % 16] * (torchFlickerX * 0.1F + 1.5F);
            if(world.lightningFlash > 0)
            {
                f1 = world.worldProvider.lightBrightnessTable[i / 16];
            }
            float f3 = f1 * (world.func_35464_b(1.0F) * 0.65F + 0.35F);
            float f4 = f1 * (world.func_35464_b(1.0F) * 0.65F + 0.35F);
            float f5 = f1;
            float f6 = f2;
            float f7 = f2 * ((f2 * 0.6F + 0.4F) * 0.6F + 0.4F);
            float f8 = f2 * (f2 * f2 * 0.6F + 0.4F);
            float f9 = f3 + f6;
            float f10 = f4 + f7;
            float f11 = f5 + f8;
            f9 = f9 * 0.96F + 0.03F;
            f10 = f10 * 0.96F + 0.03F;
            f11 = f11 * 0.96F + 0.03F;
            if(world.worldProvider.worldType == 1)
            {
                f9 = 0.22F + f6 * 0.75F;
                f10 = 0.28F + f7 * 0.75F;
                f11 = 0.25F + f8 * 0.75F;
            }
            float f12 = mc.gameSettings.gammaSetting;
            if(f9 > 1.0F)
            {
                f9 = 1.0F;
            }
            if(f10 > 1.0F)
            {
                f10 = 1.0F;
            }
            if(f11 > 1.0F)
            {
                f11 = 1.0F;
            }
            float f13 = 1.0F - f9;
            float f14 = 1.0F - f10;
            float f15 = 1.0F - f11;
            f13 = 1.0F - f13 * f13 * f13 * f13;
            f14 = 1.0F - f14 * f14 * f14 * f14;
            f15 = 1.0F - f15 * f15 * f15 * f15;
            f9 = f9 * (1.0F - f12) + f13 * f12;
            f10 = f10 * (1.0F - f12) + f14 * f12;
            f11 = f11 * (1.0F - f12) + f15 * f12;
            f9 = f9 * 0.96F + 0.03F;
            f10 = f10 * 0.96F + 0.03F;
            f11 = f11 * 0.96F + 0.03F;
            if(f9 > 1.0F)
            {
                f9 = 1.0F;
            }
            if(f10 > 1.0F)
            {
                f10 = 1.0F;
            }
            if(f11 > 1.0F)
            {
                f11 = 1.0F;
            }
            if(f9 < 0.0F)
            {
                f9 = 0.0F;
            }
            if(f10 < 0.0F)
            {
                f10 = 0.0F;
            }
            if(f11 < 0.0F)
            {
                f11 = 0.0F;
            }
            char c = '\377';
            int j = (int)(f9 * 255F);
            int k = (int)(f10 * 255F);
            int l = (int)(f11 * 255F);
            lightmapColors[i] = c << 24 | j << 16 | k << 8 | l;
        }

        mc.renderEngine.createTextureFromBytes(lightmapColors, 16, 16, emptyTexture);
    }

    public void updateCameraAndRender(float f)
    {
        Profiler.startSection("lightTex");
        if(lightmapUpdateNeeded)
        {
            updateLightmap();
        }
        Profiler.endSection();
        if(!Display.isActive())
        {
            if(System.currentTimeMillis() - prevFrameTime > 500L)
            {
                mc.displayInGameMenu();
            }
        } else
        {
            prevFrameTime = System.currentTimeMillis();
        }
        Profiler.startSection("mouse");
        if(mc.inGameHasFocus)
        {
            mc.mouseHelper.mouseXYChange();
            float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f2 = f1 * f1 * f1 * 8F;
            float f3 = (float)mc.mouseHelper.deltaX * f2;
            float f4 = (float)mc.mouseHelper.deltaY * f2;
            int l = 1;
            if(mc.gameSettings.invertMouse)
            {
                l = -1;
            }
            if(mc.gameSettings.smoothCamera)
            {
                smoothCamYaw += f3;
                smoothCamPitch += f4;
                float f5 = f - smoothCamPartialTicks;
                smoothCamPartialTicks = f;
                f3 = smoothCamFilterX * f5;
                f4 = smoothCamFilterY * f5;
                mc.thePlayer.setAngles(f3, f4 * (float)l);
            } else
            {
                mc.thePlayer.setAngles(f3, f4 * (float)l);
            }
        }
        Profiler.endSection();
        if(mc.skipRenderWorld)
        {
            return;
        }
        anaglyphEnable = mc.gameSettings.anaglyph;
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        int k = (Mouse.getX() * i) / mc.displayWidth;
        int i1 = j - (Mouse.getY() * j) / mc.displayHeight - 1;
        char c = '\310';
        if(mc.gameSettings.limitFramerate == 1)
        {
            c = 'x';
        }
        if(mc.gameSettings.limitFramerate == 2)
        {
            c = '(';
        }
        if(mc.theWorld != null)
        {
            Profiler.startSection("level");
            if(mc.gameSettings.limitFramerate == 0)
            {
                renderWorld(f, 0L);
            } else
            {
                renderWorld(f, renderEndNanoTime + (long)(0x3b9aca00 / c));
            }
            Profiler.endStartSection("sleep");
            if(mc.gameSettings.limitFramerate == 2)
            {
                long l1 = ((renderEndNanoTime + (long)(0x3b9aca00 / c)) - System.nanoTime()) / 0xf4240L;
                if(l1 > 0L && l1 < 500L)
                {
                    try
                    {
                        Thread.sleep(l1);
                    }
                    catch(InterruptedException interruptedexception)
                    {
                        interruptedexception.printStackTrace();
                    }
                }
            }
            renderEndNanoTime = System.nanoTime();
            Profiler.endStartSection("gui");
            if(!mc.gameSettings.hideGUI || mc.currentScreen != null)
            {
                mc.ingameGUI.renderGameOverlay(f, mc.currentScreen != null, k, i1);
            }
            Profiler.endSection();
        } else
        {
            GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
            GL11.glMatrixMode(5889 /*GL_PROJECTION*/);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
            GL11.glLoadIdentity();
            setupOverlayRendering();
            if(mc.gameSettings.limitFramerate == 2)
            {
                long l2 = ((renderEndNanoTime + (long)(0x3b9aca00 / c)) - System.nanoTime()) / 0xf4240L;
                if(l2 < 0L)
                {
                    l2 += 10L;
                }
                if(l2 > 0L && l2 < 500L)
                {
                    try
                    {
                        Thread.sleep(l2);
                    }
                    catch(InterruptedException interruptedexception1)
                    {
                        interruptedexception1.printStackTrace();
                    }
                }
            }
            renderEndNanoTime = System.nanoTime();
        }
        if(mc.currentScreen != null)
        {
            GL11.glClear(256);
            mc.currentScreen.drawScreen(k, i1, f);
            if(mc.currentScreen != null && mc.currentScreen.guiParticles != null)
            {
                mc.currentScreen.guiParticles.draw(f);
            }
        }
    }

    public void renderWorld(float f, long l)
    {
        Profiler.startSection("lightTex");
        if(lightmapUpdateNeeded)
        {
            updateLightmap();
        }
        GL11.glEnable(2884 /*GL_CULL_FACE*/);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        if(mc.renderViewEntity == null)
        {
            mc.renderViewEntity = mc.thePlayer;
        }
        Profiler.endStartSection("pick");
        getMouseOver(f);
        EntityLiving entityliving = mc.renderViewEntity;
        RenderGlobal renderglobal = mc.renderGlobal;
        EffectRenderer effectrenderer = mc.effectRenderer;
        double d = entityliving.lastTickPosX + (entityliving.posX - entityliving.lastTickPosX) * (double)f;
        double d1 = entityliving.lastTickPosY + (entityliving.posY - entityliving.lastTickPosY) * (double)f;
        double d2 = entityliving.lastTickPosZ + (entityliving.posZ - entityliving.lastTickPosZ) * (double)f;
        Profiler.endStartSection("center");
        IChunkProvider ichunkprovider = mc.theWorld.getIChunkProvider();
        if(ichunkprovider instanceof ChunkProviderLoadOrGenerate)
        {
            ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate)ichunkprovider;
            int j = MathHelper.floor_float((int)d) >> 4;
            int k = MathHelper.floor_float((int)d2) >> 4;
            chunkproviderloadorgenerate.setCurrentChunkOver(j, k);
        }
        for(int i = 0; i < 2; i++)
        {
            if(mc.gameSettings.anaglyph)
            {
                anaglyphField = i;
                if(anaglyphField == 0)
                {
                    GL11.glColorMask(false, true, true, false);
                } else
                {
                    GL11.glColorMask(true, false, false, false);
                }
            }
            Profiler.endStartSection("clear");
            GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
            updateFogColor(f);
            GL11.glClear(16640);
            GL11.glEnable(2884 /*GL_CULL_FACE*/);
            Profiler.endStartSection("camera");
            setupCameraTransform(f, i);
            ActiveRenderInfo.func_41067_a(mc.thePlayer, mc.gameSettings.thirdPersonView == 2);
            Profiler.endStartSection("frustrum");
            ClippingHelperImpl.getInstance();
            if(mc.gameSettings.renderDistance < 2)
            {
                setupFog(-1, f);
                Profiler.endStartSection("sky");
                renderglobal.renderSky(f);
            }
            GL11.glEnable(2912 /*GL_FOG*/);
            setupFog(1, f);
            if(mc.gameSettings.ambientOcclusion)
            {
                GL11.glShadeModel(7425 /*GL_SMOOTH*/);
            }
            Profiler.endStartSection("culling");
            Frustrum frustrum = new Frustrum();
            frustrum.setPosition(d, d1, d2);
            mc.renderGlobal.clipRenderersByFrustrum(frustrum, f);
            if(i == 0)
            {
                Profiler.endStartSection("updatechunks");
                long l1;
                do
                {
                    if(mc.renderGlobal.updateRenderers(entityliving, false) || l == 0L)
                    {
                        break;
                    }
                    l1 = l - System.nanoTime();
                } while(l1 >= 0L && l1 <= 0x3b9aca00L);
            }
            setupFog(0, f);
            GL11.glEnable(2912 /*GL_FOG*/);
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/terrain.png"));
            RenderHelper.disableStandardItemLighting();
            Profiler.endStartSection("terrain");
            renderglobal.sortAndRender(entityliving, 0, f);
            GL11.glShadeModel(7424 /*GL_FLAT*/);
            if(debugViewDirection == 0)
            {
                RenderHelper.enableStandardItemLighting();
                Profiler.endStartSection("entities");
                renderglobal.renderEntities(entityliving.getPosition(f), frustrum, f);
                enableLightmap(f);
                Profiler.endStartSection("litParticles");
                effectrenderer.func_1187_b(entityliving, f);
                RenderHelper.disableStandardItemLighting();
                setupFog(0, f);
                Profiler.endStartSection("particles");
                effectrenderer.renderParticles(entityliving, f);
                disableLightmap(f);
                if(mc.objectMouseOver != null && entityliving.isInsideOfMaterial(Material.water) && (entityliving instanceof EntityPlayer))
                {
                    EntityPlayer entityplayer = (EntityPlayer)entityliving;
                    GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
                    Profiler.endStartSection("outline");
                    renderglobal.drawBlockBreaking(entityplayer, mc.objectMouseOver, 0, entityplayer.inventory.getCurrentItem(), f);
                    renderglobal.drawSelectionBox(entityplayer, mc.objectMouseOver, 0, entityplayer.inventory.getCurrentItem(), f);
                    GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
                }
            }
            GL11.glDisable(3042 /*GL_BLEND*/);
            GL11.glEnable(2884 /*GL_CULL_FACE*/);
            GL11.glBlendFunc(770, 771);
            GL11.glDepthMask(true);
            setupFog(0, f);
            GL11.glEnable(3042 /*GL_BLEND*/);
            GL11.glDisable(2884 /*GL_CULL_FACE*/);
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/terrain.png"));
            if(mc.gameSettings.fancyGraphics)
            {
                Profiler.endStartSection("water");
                if(mc.gameSettings.ambientOcclusion)
                {
                    GL11.glShadeModel(7425 /*GL_SMOOTH*/);
                }
                GL11.glColorMask(false, false, false, false);
                int i1 = renderglobal.sortAndRender(entityliving, 1, f);
                if(mc.gameSettings.anaglyph)
                {
                    if(anaglyphField == 0)
                    {
                        GL11.glColorMask(false, true, true, true);
                    } else
                    {
                        GL11.glColorMask(true, false, false, true);
                    }
                } else
                {
                    GL11.glColorMask(true, true, true, true);
                }
                if(i1 > 0)
                {
                    renderglobal.renderAllRenderLists(1, f);
                }
                GL11.glShadeModel(7424 /*GL_FLAT*/);
            } else
            {
                Profiler.endStartSection("water");
                renderglobal.sortAndRender(entityliving, 1, f);
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2884 /*GL_CULL_FACE*/);
            GL11.glDisable(3042 /*GL_BLEND*/);
            if(cameraZoom == 1.0D && (entityliving instanceof EntityPlayer) && mc.objectMouseOver != null && !entityliving.isInsideOfMaterial(Material.water))
            {
                EntityPlayer entityplayer1 = (EntityPlayer)entityliving;
                GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
                Profiler.endStartSection("outline");
                renderglobal.drawBlockBreaking(entityplayer1, mc.objectMouseOver, 0, entityplayer1.inventory.getCurrentItem(), f);
                renderglobal.drawSelectionBox(entityplayer1, mc.objectMouseOver, 0, entityplayer1.inventory.getCurrentItem(), f);
                GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
            }
            Profiler.endStartSection("weather");
            renderRainSnow(f);
            GL11.glDisable(2912 /*GL_FOG*/);
            if(pointedEntity == null);
            if(mc.gameSettings.shouldRenderClouds())
            {
                Profiler.endStartSection("clouds");
                GL11.glPushMatrix();
                setupFog(0, f);
                GL11.glEnable(2912 /*GL_FOG*/);
                renderglobal.renderClouds(f);
                GL11.glDisable(2912 /*GL_FOG*/);
                setupFog(1, f);
                GL11.glPopMatrix();
            }
            Profiler.endStartSection("hand");
            if(cameraZoom == 1.0D)
            {
                GL11.glClear(256);
                renderHand(f, i);
            }
            if(!mc.gameSettings.anaglyph)
            {
                Profiler.endSection();
                return;
            }
        }

        GL11.glColorMask(true, true, true, false);
        Profiler.endSection();
    }

    private void addRainParticles()
    {
        float f = mc.theWorld.getRainStrength(1.0F);
        if(!mc.gameSettings.fancyGraphics)
        {
            f /= 2.0F;
        }
        if(f == 0.0F)
        {
            return;
        }
        random.setSeed((long)rendererUpdateCount * 0x12a7ce5fL);
        EntityLiving entityliving = mc.renderViewEntity;
        World world = mc.theWorld;
        int i = MathHelper.floor_double(entityliving.posX);
        int j = MathHelper.floor_double(entityliving.posY);
        int k = MathHelper.floor_double(entityliving.posZ);
        byte byte0 = 10;
        double d = 0.0D;
        double d1 = 0.0D;
        double d2 = 0.0D;
        int l = 0;
        int i1 = (int)(100F * f * f);
        if(mc.gameSettings.field_41087_P == 1)
        {
            i1 >>= 1;
        } else
        if(mc.gameSettings.field_41087_P == 2)
        {
            i1 = 0;
        }
        for(int j1 = 0; j1 < i1; j1++)
        {
            int k1 = (i + random.nextInt(byte0)) - random.nextInt(byte0);
            int l1 = (k + random.nextInt(byte0)) - random.nextInt(byte0);
            int i2 = world.func_35461_e(k1, l1);
            int j2 = world.getBlockId(k1, i2 - 1, l1);
            if(i2 > j + byte0 || i2 < j - byte0 || !world.getWorldChunkManager().getBiomeGenAt(k1, l1).canSpawnLightningBolt() || world.getWorldChunkManager().func_35554_b(k1, i2, l1) <= 0.2F)
            {
                continue;
            }
            float f1 = random.nextFloat();
            float f2 = random.nextFloat();
            if(j2 <= 0)
            {
                continue;
            }
            if(Block.blocksList[j2].blockMaterial == Material.lava)
            {
                mc.effectRenderer.addEffect(new EntitySmokeFX(world, (float)k1 + f1, (double)((float)i2 + 0.1F) - Block.blocksList[j2].minY, (float)l1 + f2, 0.0D, 0.0D, 0.0D));
                continue;
            }
            if(random.nextInt(++l) == 0)
            {
                d = (float)k1 + f1;
                d1 = (double)((float)i2 + 0.1F) - Block.blocksList[j2].minY;
                d2 = (float)l1 + f2;
            }
            mc.effectRenderer.addEffect(new EntityRainFX(world, (float)k1 + f1, (double)((float)i2 + 0.1F) - Block.blocksList[j2].minY, (float)l1 + f2));
        }

        if(l > 0 && random.nextInt(3) < rainSoundCounter++)
        {
            rainSoundCounter = 0;
            if(d1 > entityliving.posY + 1.0D && world.func_35461_e(MathHelper.floor_double(entityliving.posX), MathHelper.floor_double(entityliving.posZ)) > MathHelper.floor_double(entityliving.posY))
            {
                mc.theWorld.playSoundEffect(d, d1, d2, "ambient.weather.rain", 0.1F, 0.5F);
            } else
            {
                mc.theWorld.playSoundEffect(d, d1, d2, "ambient.weather.rain", 0.2F, 1.0F);
            }
        }
    }

    protected void renderRainSnow(float f)
    {
        float f1 = mc.theWorld.getRainStrength(f);
        if(f1 <= 0.0F)
        {
            return;
        }
        enableLightmap(f);
        if(rainXCoords == null)
        {
            rainXCoords = new float[1024 /*GL_FRONT_LEFT*/];
            rainYCoords = new float[1024 /*GL_FRONT_LEFT*/];
            for(int i = 0; i < 32; i++)
            {
                for(int j = 0; j < 32; j++)
                {
                    float f2 = j - 16;
                    float f3 = i - 16;
                    float f4 = MathHelper.sqrt_float(f2 * f2 + f3 * f3);
                    rainXCoords[i << 5 | j] = -f3 / f4;
                    rainYCoords[i << 5 | j] = f2 / f4;
                }

            }

        }
        EntityLiving entityliving = mc.renderViewEntity;
        World world = mc.theWorld;
        int k = MathHelper.floor_double(entityliving.posX);
        int l = MathHelper.floor_double(entityliving.posY);
        int i1 = MathHelper.floor_double(entityliving.posZ);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(2884 /*GL_CULL_FACE*/);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        GL11.glAlphaFunc(516, 0.01F);
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/environment/snow.png"));
        double d = entityliving.lastTickPosX + (entityliving.posX - entityliving.lastTickPosX) * (double)f;
        double d1 = entityliving.lastTickPosY + (entityliving.posY - entityliving.lastTickPosY) * (double)f;
        double d2 = entityliving.lastTickPosZ + (entityliving.posZ - entityliving.lastTickPosZ) * (double)f;
        int j1 = MathHelper.floor_double(d1);
        int k1 = 5;
        if(mc.gameSettings.fancyGraphics)
        {
            k1 = 10;
        }
        BiomeGenBase abiomegenbase[] = world.getWorldChunkManager().func_4069_a(k - k1, i1 - k1, k1 * 2 + 1, k1 * 2 + 1);
        float af[] = world.getWorldChunkManager().func_40539_b(k - k1, i1 - k1, k1 * 2 + 1, k1 * 2 + 1);
        int l1 = 0;
        byte byte0 = -1;
        float f5 = (float)rendererUpdateCount + f;
        if(mc.gameSettings.fancyGraphics)
        {
            k1 = 10;
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        l1 = 0;
        for(int i2 = i1 - k1; i2 <= i1 + k1; i2++)
        {
            for(int j2 = k - k1; j2 <= k + k1; j2++)
            {
                int k2 = ((i2 - i1) + 16) * 32 + ((j2 - k) + 16);
                float f6 = rainXCoords[k2] * 0.5F;
                float f7 = rainYCoords[k2] * 0.5F;
                BiomeGenBase biomegenbase = abiomegenbase[l1++];
                if(!biomegenbase.canSpawnLightningBolt() && !biomegenbase.getEnableSnow())
                {
                    continue;
                }
                int l2 = world.func_35461_e(j2, i2);
                int i3 = l - k1;
                int j3 = l + k1;
                if(i3 < l2)
                {
                    i3 = l2;
                }
                if(j3 < l2)
                {
                    j3 = l2;
                }
                float f8 = 1.0F;
                int k3 = l2;
                if(k3 < j1)
                {
                    k3 = j1;
                }
                if(i3 == j3)
                {
                    continue;
                }
                random.setSeed(j2 * j2 * 3121 /*GL_RGBA_MODE*/ + j2 * 0x2b24abb ^ i2 * i2 * 0x66397 + i2 * 13761);
                float f9 = af[l1 - 1];
                if(world.getWorldChunkManager().func_40540_a(f9, l2) >= 0.15F)
                {
                    if(byte0 != 0)
                    {
                        if(byte0 >= 0)
                        {
                            tessellator.draw();
                        }
                        byte0 = 0;
                        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/environment/rain.png"));
                        tessellator.startDrawingQuads();
                    }
                    float f10 = (((float)(rendererUpdateCount + j2 * j2 * 3121 /*GL_RGBA_MODE*/ + j2 * 0x2b24abb + i2 * i2 * 0x66397 + i2 * 13761 & 0x1f) + f) / 32F) * (3F + random.nextFloat());
                    double d3 = (double)((float)j2 + 0.5F) - entityliving.posX;
                    double d4 = (double)((float)i2 + 0.5F) - entityliving.posZ;
                    float f14 = MathHelper.sqrt_double(d3 * d3 + d4 * d4) / (float)k1;
                    float f15 = 1.0F;
                    tessellator.setBrightness(world.getLightBrightnessForSkyBlocks(j2, k3, i2, 0));
                    tessellator.setColorRGBA_F(f15, f15, f15, ((1.0F - f14 * f14) * 0.5F + 0.5F) * f1);
                    tessellator.setTranslationD(-d * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                    tessellator.addVertexWithUV((double)((float)j2 - f6) + 0.5D, i3, (double)((float)i2 - f7) + 0.5D, 0.0F * f8, ((float)i3 * f8) / 4F + f10 * f8);
                    tessellator.addVertexWithUV((double)((float)j2 + f6) + 0.5D, i3, (double)((float)i2 + f7) + 0.5D, 1.0F * f8, ((float)i3 * f8) / 4F + f10 * f8);
                    tessellator.addVertexWithUV((double)((float)j2 + f6) + 0.5D, j3, (double)((float)i2 + f7) + 0.5D, 1.0F * f8, ((float)j3 * f8) / 4F + f10 * f8);
                    tessellator.addVertexWithUV((double)((float)j2 - f6) + 0.5D, j3, (double)((float)i2 - f7) + 0.5D, 0.0F * f8, ((float)j3 * f8) / 4F + f10 * f8);
                    tessellator.setTranslationD(0.0D, 0.0D, 0.0D);
                    continue;
                }
                if(byte0 != 1)
                {
                    if(byte0 >= 0)
                    {
                        tessellator.draw();
                    }
                    byte0 = 1;
                    GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/environment/snow.png"));
                    tessellator.startDrawingQuads();
                }
                float f11 = ((float)(rendererUpdateCount & 0x1ff) + f) / 512F;
                float f12 = random.nextFloat() + f5 * 0.01F * (float)random.nextGaussian();
                float f13 = random.nextFloat() + f5 * (float)random.nextGaussian() * 0.001F;
                double d5 = (double)((float)j2 + 0.5F) - entityliving.posX;
                double d6 = (double)((float)i2 + 0.5F) - entityliving.posZ;
                float f16 = MathHelper.sqrt_double(d5 * d5 + d6 * d6) / (float)k1;
                float f17 = 1.0F;
                tessellator.setBrightness((world.getLightBrightnessForSkyBlocks(j2, k3, i2, 0) * 3 + 0xf000f0) / 4);
                tessellator.setColorRGBA_F(f17, f17, f17, ((1.0F - f16 * f16) * 0.3F + 0.5F) * f1);
                tessellator.setTranslationD(-d * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                tessellator.addVertexWithUV((double)((float)j2 - f6) + 0.5D, i3, (double)((float)i2 - f7) + 0.5D, 0.0F * f8 + f12, ((float)i3 * f8) / 4F + f11 * f8 + f13);
                tessellator.addVertexWithUV((double)((float)j2 + f6) + 0.5D, i3, (double)((float)i2 + f7) + 0.5D, 1.0F * f8 + f12, ((float)i3 * f8) / 4F + f11 * f8 + f13);
                tessellator.addVertexWithUV((double)((float)j2 + f6) + 0.5D, j3, (double)((float)i2 + f7) + 0.5D, 1.0F * f8 + f12, ((float)j3 * f8) / 4F + f11 * f8 + f13);
                tessellator.addVertexWithUV((double)((float)j2 - f6) + 0.5D, j3, (double)((float)i2 - f7) + 0.5D, 0.0F * f8 + f12, ((float)j3 * f8) / 4F + f11 * f8 + f13);
                tessellator.setTranslationD(0.0D, 0.0D, 0.0D);
            }

        }

        if(byte0 >= 0)
        {
            tessellator.draw();
        }
        GL11.glEnable(2884 /*GL_CULL_FACE*/);
        GL11.glDisable(3042 /*GL_BLEND*/);
        GL11.glAlphaFunc(516, 0.1F);
        disableLightmap(f);
    }

    public void setupOverlayRendering()
    {
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        GL11.glClear(256);
        GL11.glMatrixMode(5889 /*GL_PROJECTION*/);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, scaledresolution.scaledWidthD, scaledresolution.scaledHeightD, 0.0D, 1000D, 3000D);
        GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000F);
    }

    private void updateFogColor(float f)
    {
        World world = mc.theWorld;
        EntityLiving entityliving = mc.renderViewEntity;
        float f1 = 1.0F / (float)(4 - mc.gameSettings.renderDistance);
        f1 = 1.0F - (float)Math.pow(f1, 0.25D);
        Vec3D vec3d = world.getSkyColor(mc.renderViewEntity, f);
        float f2 = (float)vec3d.xCoord;
        float f3 = (float)vec3d.yCoord;
        float f4 = (float)vec3d.zCoord;
        Vec3D vec3d1 = world.getFogColor(f);
        fogColorRed = (float)vec3d1.xCoord;
        fogColorGreen = (float)vec3d1.yCoord;
        fogColorBlue = (float)vec3d1.zCoord;
        if(mc.gameSettings.renderDistance < 2)
        {
            Vec3D vec3d2 = MathHelper.sin(world.getCelestialAngleRadians(f)) <= 0.0F ? Vec3D.createVector(1.0D, 0.0D, 0.0D) : Vec3D.createVector(-1D, 0.0D, 0.0D);
            float f6 = (float)entityliving.getLook(f).dotProduct(vec3d2);
            if(f6 < 0.0F)
            {
                f6 = 0.0F;
            }
            if(f6 > 0.0F)
            {
                float af[] = world.worldProvider.calcSunriseSunsetColors(world.getCelestialAngle(f), f);
                if(af != null)
                {
                    f6 *= af[3];
                    fogColorRed = fogColorRed * (1.0F - f6) + af[0] * f6;
                    fogColorGreen = fogColorGreen * (1.0F - f6) + af[1] * f6;
                    fogColorBlue = fogColorBlue * (1.0F - f6) + af[2] * f6;
                }
            }
        }
        fogColorRed += (f2 - fogColorRed) * f1;
        fogColorGreen += (f3 - fogColorGreen) * f1;
        fogColorBlue += (f4 - fogColorBlue) * f1;
        float f5 = world.getRainStrength(f);
        if(f5 > 0.0F)
        {
            float f7 = 1.0F - f5 * 0.5F;
            float f9 = 1.0F - f5 * 0.4F;
            fogColorRed *= f7;
            fogColorGreen *= f7;
            fogColorBlue *= f9;
        }
        float f8 = world.getWeightedThunderStrength(f);
        if(f8 > 0.0F)
        {
            float f10 = 1.0F - f8 * 0.5F;
            fogColorRed *= f10;
            fogColorGreen *= f10;
            fogColorBlue *= f10;
        }
        int i = ActiveRenderInfo.func_41066_a(mc.theWorld, entityliving, f);
        if(cloudFog)
        {
            Vec3D vec3d3 = world.drawClouds(f);
            fogColorRed = (float)vec3d3.xCoord;
            fogColorGreen = (float)vec3d3.yCoord;
            fogColorBlue = (float)vec3d3.zCoord;
        } else
        if(i != 0 && Block.blocksList[i].blockMaterial == Material.water)
        {
            fogColorRed = 0.02F;
            fogColorGreen = 0.02F;
            fogColorBlue = 0.2F;
        } else
        if(i != 0 && Block.blocksList[i].blockMaterial == Material.lava)
        {
            fogColorRed = 0.6F;
            fogColorGreen = 0.1F;
            fogColorBlue = 0.0F;
        }
        float f11 = fogColor2 + (fogColor1 - fogColor2) * f;
        fogColorRed *= f11;
        fogColorGreen *= f11;
        fogColorBlue *= f11;
        double d = (entityliving.lastTickPosY + (entityliving.posY - entityliving.lastTickPosY) * (double)f) / 32D;
        if(entityliving.isPotionActive(Potion.potionBlindness))
        {
            int j = entityliving.getActivePotionEffect(Potion.potionBlindness).getDuration();
            if(j < 20)
            {
                d *= 1.0F - (float)j / 20F;
            } else
            {
                d = 0.0D;
            }
        }
        if(d < 1.0D)
        {
            if(d < 0.0D)
            {
                d = 0.0D;
            }
            d *= d;
            fogColorRed *= d;
            fogColorGreen *= d;
            fogColorBlue *= d;
        }
        if(mc.gameSettings.anaglyph)
        {
            float f12 = (fogColorRed * 30F + fogColorGreen * 59F + fogColorBlue * 11F) / 100F;
            float f13 = (fogColorRed * 30F + fogColorGreen * 70F) / 100F;
            float f14 = (fogColorRed * 30F + fogColorBlue * 70F) / 100F;
            fogColorRed = f12;
            fogColorGreen = f13;
            fogColorBlue = f14;
        }
        GL11.glClearColor(fogColorRed, fogColorGreen, fogColorBlue, 0.0F);
    }

    private void setupFog(int i, float f)
    {
        EntityLiving entityliving = mc.renderViewEntity;
        if(i == 999)
        {
            GL11.glFog(2918 /*GL_FOG_COLOR*/, setFogColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
            GL11.glFogi(2917 /*GL_FOG_MODE*/, 9729 /*GL_LINEAR*/);
            GL11.glFogf(2915 /*GL_FOG_START*/, 0.0F);
            GL11.glFogf(2916 /*GL_FOG_END*/, 8F);
            if(GLContext.getCapabilities().GL_NV_fog_distance)
            {
                GL11.glFogi(34138, 34139);
            }
            GL11.glFogf(2915 /*GL_FOG_START*/, 0.0F);
            return;
        }
        GL11.glFog(2918 /*GL_FOG_COLOR*/, setFogColorBuffer(fogColorRed, fogColorGreen, fogColorBlue, 1.0F));
        GL11.glNormal3f(0.0F, -1F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int j = ActiveRenderInfo.func_41066_a(mc.theWorld, entityliving, f);
        if(entityliving.isPotionActive(Potion.potionBlindness))
        {
            float f1 = 5F;
            int k = entityliving.getActivePotionEffect(Potion.potionBlindness).getDuration();
            if(k < 20)
            {
                f1 = 5F + (farPlaneDistance - 5F) * (1.0F - (float)k / 20F);
            }
            GL11.glFogi(2917 /*GL_FOG_MODE*/, 9729 /*GL_LINEAR*/);
            if(i < 0)
            {
                GL11.glFogf(2915 /*GL_FOG_START*/, 0.0F);
                GL11.glFogf(2916 /*GL_FOG_END*/, f1 * 0.8F);
            } else
            {
                GL11.glFogf(2915 /*GL_FOG_START*/, f1 * 0.25F);
                GL11.glFogf(2916 /*GL_FOG_END*/, f1);
            }
            if(GLContext.getCapabilities().GL_NV_fog_distance)
            {
                GL11.glFogi(34138, 34139);
            }
        } else
        if(cloudFog)
        {
            GL11.glFogi(2917 /*GL_FOG_MODE*/, 2048 /*GL_EXP*/);
            GL11.glFogf(2914 /*GL_FOG_DENSITY*/, 0.1F);
            float f2 = 1.0F;
            float f6 = 1.0F;
            float f9 = 1.0F;
            if(mc.gameSettings.anaglyph)
            {
                float f12 = (f2 * 30F + f6 * 59F + f9 * 11F) / 100F;
                float f16 = (f2 * 30F + f6 * 70F) / 100F;
                float f19 = (f2 * 30F + f9 * 70F) / 100F;
                f2 = f12;
                f6 = f16;
                f9 = f19;
            }
        } else
        if(j > 0 && Block.blocksList[j].blockMaterial == Material.water)
        {
            GL11.glFogi(2917 /*GL_FOG_MODE*/, 2048 /*GL_EXP*/);
            if(!entityliving.isPotionActive(Potion.potionWaterBreathing))
            {
                GL11.glFogf(2914 /*GL_FOG_DENSITY*/, 0.1F);
            } else
            {
                GL11.glFogf(2914 /*GL_FOG_DENSITY*/, 0.05F);
            }
            float f3 = 0.4F;
            float f7 = 0.4F;
            float f10 = 0.9F;
            if(mc.gameSettings.anaglyph)
            {
                float f13 = (f3 * 30F + f7 * 59F + f10 * 11F) / 100F;
                float f17 = (f3 * 30F + f7 * 70F) / 100F;
                float f20 = (f3 * 30F + f10 * 70F) / 100F;
                f3 = f13;
                f7 = f17;
                f10 = f20;
            }
        } else
        if(j > 0 && Block.blocksList[j].blockMaterial == Material.lava)
        {
            GL11.glFogi(2917 /*GL_FOG_MODE*/, 2048 /*GL_EXP*/);
            GL11.glFogf(2914 /*GL_FOG_DENSITY*/, 2.0F);
            float f4 = 0.4F;
            float f8 = 0.3F;
            float f11 = 0.3F;
            if(mc.gameSettings.anaglyph)
            {
                float f14 = (f4 * 30F + f8 * 59F + f11 * 11F) / 100F;
                float f18 = (f4 * 30F + f8 * 70F) / 100F;
                float f21 = (f4 * 30F + f11 * 70F) / 100F;
                f4 = f14;
                f8 = f18;
                f11 = f21;
            }
        } else
        {
            float f5 = farPlaneDistance;
            if(!mc.theWorld.worldProvider.hasNoSky)
            {
                double d = (double)((entityliving.getEntityBrightnessForRender(f) & 0xf00000) >> 20) / 16D + (entityliving.lastTickPosY + (entityliving.posY - entityliving.lastTickPosY) * (double)f + 4D) / 32D;
                if(d < 1.0D)
                {
                    if(d < 0.0D)
                    {
                        d = 0.0D;
                    }
                    d *= d;
                    float f15 = 100F * (float)d;
                    if(f15 < 5F)
                    {
                        f15 = 5F;
                    }
                    if(f5 > f15)
                    {
                        f5 = f15;
                    }
                }
            }
            GL11.glFogi(2917 /*GL_FOG_MODE*/, 9729 /*GL_LINEAR*/);
            if(i < 0)
            {
                GL11.glFogf(2915 /*GL_FOG_START*/, 0.0F);
                GL11.glFogf(2916 /*GL_FOG_END*/, f5 * 0.8F);
            } else
            {
                GL11.glFogf(2915 /*GL_FOG_START*/, f5 * 0.25F);
                GL11.glFogf(2916 /*GL_FOG_END*/, f5);
            }
            if(GLContext.getCapabilities().GL_NV_fog_distance)
            {
                GL11.glFogi(34138, 34139);
            }
            if(mc.theWorld.worldProvider.isNether)
            {
                GL11.glFogf(2915 /*GL_FOG_START*/, 0.0F);
            }
        }
        GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
        GL11.glColorMaterial(1028 /*GL_FRONT*/, 4608 /*GL_AMBIENT*/);
    }

    private FloatBuffer setFogColorBuffer(float f, float f1, float f2, float f3)
    {
        fogColorBuffer.clear();
        fogColorBuffer.put(f).put(f1).put(f2).put(f3);
        fogColorBuffer.flip();
        return fogColorBuffer;
    }

}
