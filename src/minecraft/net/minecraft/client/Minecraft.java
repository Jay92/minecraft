// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;
import java.io.PrintStream;
import java.text.DecimalFormat;
import net.minecraft.src.Achievement;
import net.minecraft.src.AchievementList;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockGrass;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderLoadOrGenerate;
import net.minecraft.src.ColorizerFoliage;
import net.minecraft.src.ColorizerGrass;
import net.minecraft.src.ColorizerWater;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumOS2;
import net.minecraft.src.EnumOSMappingHelper;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GameWindowListener;
import net.minecraft.src.GuiAchievement;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiConflictWarning;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiErrorScreen;
import net.minecraft.src.GuiGameOver;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiParticle;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSleepMP;
import net.minecraft.src.GuiUnused;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.LoadingScreenRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.MinecraftImpl;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.MovementInputFromOptions;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.OpenGlCapsChecker;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.PlayerController;
import net.minecraft.src.PlayerControllerCreative;
import net.minecraft.src.Profiler;
import net.minecraft.src.ProfilerResult;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.SaveConverterMcRegion;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.ScreenShotHelper;
import net.minecraft.src.Session;
import net.minecraft.src.SoundManager;
import net.minecraft.src.StatFileWriter;
import net.minecraft.src.StatList;
import net.minecraft.src.StatStringFormatKeyInv;
import net.minecraft.src.Teleporter;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureCompassFX;
import net.minecraft.src.TextureFlamesFX;
import net.minecraft.src.TextureLavaFX;
import net.minecraft.src.TextureLavaFlowFX;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.TexturePortalFX;
import net.minecraft.src.TextureWatchFX;
import net.minecraft.src.TextureWaterFX;
import net.minecraft.src.TextureWaterFlowFX;
import net.minecraft.src.ThreadCheckHasPaid;
import net.minecraft.src.ThreadDownloadResources;
import net.minecraft.src.ThreadSleepForever;
import net.minecraft.src.Timer;
import net.minecraft.src.UnexpectedThrowable;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.WorldSettings;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

// Referenced classes of package net.minecraft.client:
//            MinecraftApplet

public abstract class Minecraft
    implements Runnable
{

    public static byte field_28006_b[] = new byte[0xa00000];
    private static Minecraft theMinecraft;
    public PlayerController playerController;
    private boolean fullscreen;
    private boolean hasCrashed;
    public int displayWidth;
    public int displayHeight;
    private OpenGlCapsChecker glCapabilities;
    private Timer timer;
    public World theWorld;
    public RenderGlobal renderGlobal;
    public EntityPlayerSP thePlayer;
    public EntityLiving renderViewEntity;
    public EffectRenderer effectRenderer;
    public Session session;
    public String minecraftUri;
    public Canvas mcCanvas;
    public boolean hideQuitButton;
    public volatile boolean isGamePaused;
    public RenderEngine renderEngine;
    public FontRenderer fontRenderer;
    public FontRenderer standardGalacticFontRenderer;
    public GuiScreen currentScreen;
    public LoadingScreenRenderer loadingScreen;
    public EntityRenderer entityRenderer;
    private ThreadDownloadResources downloadResourcesThread;
    private int ticksRan;
    private int leftClickCounter;
    private int tempDisplayWidth;
    private int tempDisplayHeight;
    public GuiAchievement guiAchievement;
    public GuiIngame ingameGUI;
    public boolean skipRenderWorld;
    public ModelBiped playerModelBiped;
    public MovingObjectPosition objectMouseOver;
    public GameSettings gameSettings;
    protected MinecraftApplet mcApplet;
    public SoundManager sndManager;
    public MouseHelper mouseHelper;
    public TexturePackList texturePackList;
    public File mcDataDir;
    private ISaveFormat saveLoader;
    public static long frameTimes[] = new long[512];
    public static long tickTimes[] = new long[512];
    public static int numRecordedFrameTimes = 0;
    public static long hasPaidCheckTime = 0L;
    private int rightClickDelayTimer;
    public StatFileWriter statFileWriter;
    private String serverName;
    private int serverPort;
    private TextureWaterFX textureWaterFX;
    private TextureLavaFX textureLavaFX;
    private static File minecraftDir = null;
    public volatile boolean running;
    public String debug;
    long field_40004_N;
    int fpsCounter;
    boolean isTakingScreenshot;
    long prevFrameTime;
    private String field_40006_ak;
    public boolean inGameHasFocus;
    public boolean isRaining;
    long systemTime;
    private int joinPlayerCounter;

    public Minecraft(Component component, Canvas canvas, MinecraftApplet minecraftapplet, int i, int j, boolean flag)
    {
        fullscreen = false;
        hasCrashed = false;
        timer = new Timer(20F);
        session = null;
        hideQuitButton = false;
        isGamePaused = false;
        currentScreen = null;
        ticksRan = 0;
        leftClickCounter = 0;
        guiAchievement = new GuiAchievement(this);
        skipRenderWorld = false;
        playerModelBiped = new ModelBiped(0.0F);
        objectMouseOver = null;
        sndManager = new SoundManager();
        rightClickDelayTimer = 0;
        textureWaterFX = new TextureWaterFX();
        textureLavaFX = new TextureLavaFX();
        running = true;
        debug = "";
        field_40004_N = System.currentTimeMillis();
        fpsCounter = 0;
        isTakingScreenshot = false;
        prevFrameTime = -1L;
        field_40006_ak = "root";
        inGameHasFocus = false;
        isRaining = false;
        systemTime = System.currentTimeMillis();
        joinPlayerCounter = 0;
        StatList.func_27360_a();
        tempDisplayHeight = j;
        fullscreen = flag;
        mcApplet = minecraftapplet;
        new ThreadSleepForever(this, "Timer hack thread");
        mcCanvas = canvas;
        displayWidth = i;
        displayHeight = j;
        fullscreen = flag;
        if(minecraftapplet == null || "true".equals(minecraftapplet.getParameter("stand-alone")))
        {
            hideQuitButton = false;
        }
        theMinecraft = this;
    }

    public void onMinecraftCrash(UnexpectedThrowable unexpectedthrowable)
    {
        hasCrashed = true;
        displayUnexpectedThrowable(unexpectedthrowable);
    }

    public abstract void displayUnexpectedThrowable(UnexpectedThrowable unexpectedthrowable);

    public void setServer(String s, int i)
    {
        serverName = s;
        serverPort = i;
    }

    public void startGame()
        throws LWJGLException
    {
        if(mcCanvas != null)
        {
            Graphics g = mcCanvas.getGraphics();
            if(g != null)
            {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, displayWidth, displayHeight);
                g.dispose();
            }
            Display.setParent(mcCanvas);
        } else
        if(fullscreen)
        {
            Display.setFullscreen(true);
            displayWidth = Display.getDisplayMode().getWidth();
            displayHeight = Display.getDisplayMode().getHeight();
            if(displayWidth <= 0)
            {
                displayWidth = 1;
            }
            if(displayHeight <= 0)
            {
                displayHeight = 1;
            }
        } else
        {
            Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
        }
        Display.setTitle("Minecraft Minecraft 1.0.0");
        try
        {
            PixelFormat pixelformat = new PixelFormat();
            pixelformat = pixelformat.withDepthBits(24);
            Display.create(pixelformat);
        }
        catch(LWJGLException lwjglexception)
        {
            lwjglexception.printStackTrace();
            try
            {
                Thread.sleep(1000L);
            }
            catch(InterruptedException interruptedexception) { }
            Display.create();
        }
        OpenGlHelper.initializeTextures();
        mcDataDir = getMinecraftDir();
        saveLoader = new SaveConverterMcRegion(new File(mcDataDir, "saves"));
        gameSettings = new GameSettings(this, mcDataDir);
        texturePackList = new TexturePackList(this, mcDataDir);
        renderEngine = new RenderEngine(texturePackList, gameSettings);
        loadScreen();
        fontRenderer = new FontRenderer(gameSettings, "/font/default.png", renderEngine);
        standardGalacticFontRenderer = new FontRenderer(gameSettings, "/font/alternate.png", renderEngine);
        ColorizerWater.getWaterBiomeColorizer(renderEngine.getTextureContents("/misc/watercolor.png"));
        ColorizerGrass.setGrassBiomeColorizer(renderEngine.getTextureContents("/misc/grasscolor.png"));
        ColorizerFoliage.getFoilageBiomeColorizer(renderEngine.getTextureContents("/misc/foliagecolor.png"));
        entityRenderer = new EntityRenderer(this);
        RenderManager.instance.itemRenderer = new ItemRenderer(this);
        statFileWriter = new StatFileWriter(session, mcDataDir);
        AchievementList.openInventory.setStatStringFormatter(new StatStringFormatKeyInv(this));
        loadScreen();
        Keyboard.create();
        Mouse.create();
        mouseHelper = new MouseHelper(mcCanvas);
        try
        {
            Controllers.create();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        checkGLError("Pre startup");
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        GL11.glShadeModel(7425 /*GL_SMOOTH*/);
        GL11.glClearDepth(1.0D);
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        GL11.glDepthFunc(515);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glAlphaFunc(516, 0.1F);
        GL11.glCullFace(1029 /*GL_BACK*/);
        GL11.glMatrixMode(5889 /*GL_PROJECTION*/);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
        checkGLError("Startup");
        glCapabilities = new OpenGlCapsChecker();
        sndManager.loadSoundSettings(gameSettings);
        renderEngine.registerTextureFX(textureLavaFX);
        renderEngine.registerTextureFX(textureWaterFX);
        renderEngine.registerTextureFX(new TexturePortalFX());
        renderEngine.registerTextureFX(new TextureCompassFX(this));
        renderEngine.registerTextureFX(new TextureWatchFX(this));
        renderEngine.registerTextureFX(new TextureWaterFlowFX());
        renderEngine.registerTextureFX(new TextureLavaFlowFX());
        renderEngine.registerTextureFX(new TextureFlamesFX(0));
        renderEngine.registerTextureFX(new TextureFlamesFX(1));
        renderGlobal = new RenderGlobal(this, renderEngine);
        GL11.glViewport(0, 0, displayWidth, displayHeight);
        effectRenderer = new EffectRenderer(theWorld, renderEngine);
        try
        {
            downloadResourcesThread = new ThreadDownloadResources(mcDataDir, this);
            downloadResourcesThread.start();
        }
        catch(Exception exception1) { }
        checkGLError("Post startup");
        ingameGUI = new GuiIngame(this);
        if(serverName != null)
        {
            displayGuiScreen(new GuiConnecting(this, serverName, serverPort));
        } else
        {
            displayGuiScreen(new GuiMainMenu());
        }
        loadingScreen = new LoadingScreenRenderer(this);
    }

    private void loadScreen()
        throws LWJGLException
    {
        ScaledResolution scaledresolution = new ScaledResolution(gameSettings, displayWidth, displayHeight);
        GL11.glClear(16640);
        GL11.glMatrixMode(5889 /*GL_PROJECTION*/);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, scaledresolution.scaledWidthD, scaledresolution.scaledHeightD, 0.0D, 1000D, 3000D);
        GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000F);
        GL11.glViewport(0, 0, displayWidth, displayHeight);
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        GL11.glDisable(2912 /*GL_FOG*/);
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderEngine.getTexture("/title/mojang.png"));
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(0xffffff);
        tessellator.addVertexWithUV(0.0D, displayHeight, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV(displayWidth, displayHeight, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV(displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        char c = '\u0100';
        char c1 = '\u0100';
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        tessellator.setColorOpaque_I(0xffffff);
        scaledTessellator((scaledresolution.getScaledWidth() - c) / 2, (scaledresolution.getScaledHeight() - c1) / 2, 0, 0, c, c1);
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2912 /*GL_FOG*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glAlphaFunc(516, 0.1F);
        Display.swapBuffers();
    }

    public void scaledTessellator(int i, int j, int k, int l, int i1, int j1)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(i + 0, j + j1, 0.0D, (float)(k + 0) * f, (float)(l + j1) * f1);
        tessellator.addVertexWithUV(i + i1, j + j1, 0.0D, (float)(k + i1) * f, (float)(l + j1) * f1);
        tessellator.addVertexWithUV(i + i1, j + 0, 0.0D, (float)(k + i1) * f, (float)(l + 0) * f1);
        tessellator.addVertexWithUV(i + 0, j + 0, 0.0D, (float)(k + 0) * f, (float)(l + 0) * f1);
        tessellator.draw();
    }

    public static File getMinecraftDir()
    {
        if(minecraftDir == null)
        {
            minecraftDir = getAppDir("minecraft");
        }
        return minecraftDir;
    }

    public static File getAppDir(String s)
    {
        String s1 = System.getProperty("user.home", ".");
        File file;
        switch(EnumOSMappingHelper.enumOSMappingArray[getOs().ordinal()])
        {
        case 1: // '\001'
        case 2: // '\002'
            file = new File(s1, (new StringBuilder()).append('.').append(s).append('/').toString());
            break;

        case 3: // '\003'
            String s2 = System.getenv("APPDATA");
            if(s2 != null)
            {
                file = new File(s2, (new StringBuilder()).append(".").append(s).append('/').toString());
            } else
            {
                file = new File(s1, (new StringBuilder()).append('.').append(s).append('/').toString());
            }
            break;

        case 4: // '\004'
            file = new File(s1, (new StringBuilder()).append("Library/Application Support/").append(s).toString());
            break;

        default:
            file = new File(s1, (new StringBuilder()).append(s).append('/').toString());
            break;
        }
        if(!file.exists() && !file.mkdirs())
        {
            throw new RuntimeException((new StringBuilder()).append("The working directory could not be created: ").append(file).toString());
        } else
        {
            return file;
        }
    }

    private static EnumOS2 getOs()
    {
        String s = System.getProperty("os.name").toLowerCase();
        if(s.contains("win"))
        {
            return EnumOS2.windows;
        }
        if(s.contains("mac"))
        {
            return EnumOS2.macos;
        }
        if(s.contains("solaris"))
        {
            return EnumOS2.solaris;
        }
        if(s.contains("sunos"))
        {
            return EnumOS2.solaris;
        }
        if(s.contains("linux"))
        {
            return EnumOS2.linux;
        }
        if(s.contains("unix"))
        {
            return EnumOS2.linux;
        } else
        {
            return EnumOS2.unknown;
        }
    }

    public ISaveFormat getSaveLoader()
    {
        return saveLoader;
    }

    public void displayGuiScreen(GuiScreen guiscreen)
    {
        if(currentScreen instanceof GuiUnused)
        {
            return;
        }
        if(currentScreen != null)
        {
            currentScreen.onGuiClosed();
        }
        if(guiscreen instanceof GuiMainMenu)
        {
            statFileWriter.func_27175_b();
        }
        statFileWriter.syncStats();
        if(guiscreen == null && theWorld == null)
        {
            guiscreen = new GuiMainMenu();
        } else
        if(guiscreen == null && thePlayer.getEntityHealth() <= 0)
        {
            guiscreen = new GuiGameOver();
        }
        if(guiscreen instanceof GuiMainMenu)
        {
            gameSettings.showDebugInfo = false;
            ingameGUI.clearChatMessages();
        }
        currentScreen = guiscreen;
        if(guiscreen != null)
        {
            setIngameNotInFocus();
            ScaledResolution scaledresolution = new ScaledResolution(gameSettings, displayWidth, displayHeight);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            guiscreen.setWorldAndResolution(this, i, j);
            skipRenderWorld = false;
        } else
        {
            setIngameFocus();
        }
    }

    private void checkGLError(String s)
    {
        int i = GL11.glGetError();
        if(i != 0)
        {
            String s1 = GLU.gluErrorString(i);
            System.out.println("########## GL ERROR ##########");
            System.out.println((new StringBuilder()).append("@ ").append(s).toString());
            System.out.println((new StringBuilder()).append(i).append(": ").append(s1).toString());
        }
    }

    public void shutdownMinecraftApplet()
    {
        try
        {
            statFileWriter.func_27175_b();
            statFileWriter.syncStats();
            if(mcApplet != null)
            {
                mcApplet.clearApplet();
            }
            try
            {
                if(downloadResourcesThread != null)
                {
                    downloadResourcesThread.closeMinecraft();
                }
            }
            catch(Exception exception) { }
            System.out.println("Stopping!");
            try
            {
                changeWorld1(null);
            }
            catch(Throwable throwable) { }
            try
            {
                GLAllocation.deleteTexturesAndDisplayLists();
            }
            catch(Throwable throwable1) { }
            sndManager.closeMinecraft();
            Mouse.destroy();
            Keyboard.destroy();
        }
        finally
        {
            Display.destroy();
            if(!hasCrashed)
            {
                System.exit(0);
            }
        }
        System.gc();
    }

    public void run()
    {
        running = true;
        try
        {
            startGame();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            onMinecraftCrash(new UnexpectedThrowable("Failed to start game", exception));
            return;
        }
        try
        {
            while(running) 
            {
                try
                {
                    func_40001_x();
                }
                catch(MinecraftException minecraftexception)
                {
                    theWorld = null;
                    changeWorld1(null);
                    displayGuiScreen(new GuiConflictWarning());
                }
                catch(OutOfMemoryError outofmemoryerror)
                {
                    freeMemory();
                    displayGuiScreen(new GuiErrorScreen());
                    System.gc();
                }
            }
        }
        catch(MinecraftError minecrafterror) { }
        catch(Throwable throwable)
        {
            freeMemory();
            throwable.printStackTrace();
            onMinecraftCrash(new UnexpectedThrowable("Unexpected error", throwable));
        }
        finally
        {
            shutdownMinecraftApplet();
        }
    }

    private void func_40001_x()
    {
        if(mcApplet != null && !mcApplet.isActive())
        {
            running = false;
            return;
        }
        AxisAlignedBB.clearBoundingBoxPool();
        Vec3D.initialize();
        Profiler.startSection("root");
        if(mcCanvas == null && Display.isCloseRequested())
        {
            shutdown();
        }
        if(isGamePaused && theWorld != null)
        {
            float f = timer.renderPartialTicks;
            timer.updateTimer();
            timer.renderPartialTicks = f;
        } else
        {
            timer.updateTimer();
        }
        long l = System.nanoTime();
        Profiler.startSection("tick");
        for(int i = 0; i < timer.elapsedTicks; i++)
        {
            ticksRan++;
            try
            {
                runTick();
                continue;
            }
            catch(MinecraftException minecraftexception)
            {
                theWorld = null;
            }
            changeWorld1(null);
            displayGuiScreen(new GuiConflictWarning());
        }

        Profiler.endSection();
        long l1 = System.nanoTime() - l;
        checkGLError("Pre render");
        RenderBlocks.fancyGrass = gameSettings.fancyGraphics;
        Profiler.startSection("sound");
        sndManager.func_338_a(thePlayer, timer.renderPartialTicks);
        Profiler.endStartSection("updatelights");
        if(theWorld != null)
        {
            theWorld.updatingLighting();
        }
        Profiler.endSection();
        Profiler.startSection("render");
        Profiler.startSection("display");
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        if(!Keyboard.isKeyDown(65))
        {
            Display.update();
        }
        if(thePlayer != null && thePlayer.isEntityInsideOpaqueBlock())
        {
            gameSettings.thirdPersonView = 0;
        }
        Profiler.endSection();
        if(!skipRenderWorld)
        {
            Profiler.startSection("gameMode");
            if(playerController != null)
            {
                playerController.setPartialTime(timer.renderPartialTicks);
            }
            Profiler.endStartSection("gameRenderer");
            entityRenderer.updateCameraAndRender(timer.renderPartialTicks);
            Profiler.endSection();
        }
        GL11.glFlush();
        Profiler.endSection();
        if(!Display.isActive() && fullscreen)
        {
            toggleFullscreen();
        }
        Profiler.endSection();
        if(gameSettings.showDebugInfo)
        {
            if(!Profiler.profilingEnabled)
            {
                Profiler.clearProfiling();
            }
            Profiler.profilingEnabled = true;
            displayDebugInfo(l1);
        } else
        {
            Profiler.profilingEnabled = false;
            prevFrameTime = System.nanoTime();
        }
        guiAchievement.updateAchievementWindow();
        Profiler.startSection("root");
        Thread.yield();
        if(Keyboard.isKeyDown(65))
        {
            Display.update();
        }
        screenshotListener();
        if(mcCanvas != null && !fullscreen && (mcCanvas.getWidth() != displayWidth || mcCanvas.getHeight() != displayHeight))
        {
            displayWidth = mcCanvas.getWidth();
            displayHeight = mcCanvas.getHeight();
            if(displayWidth <= 0)
            {
                displayWidth = 1;
            }
            if(displayHeight <= 0)
            {
                displayHeight = 1;
            }
            resize(displayWidth, displayHeight);
        }
        checkGLError("Post render");
        fpsCounter++;
        isGamePaused = !isMultiplayerWorld() && currentScreen != null && currentScreen.doesGuiPauseGame();
        while(System.currentTimeMillis() >= field_40004_N + 1000L) 
        {
            debug = (new StringBuilder()).append(fpsCounter).append(" fps, ").append(WorldRenderer.chunksUpdated).append(" chunk updates").toString();
            WorldRenderer.chunksUpdated = 0;
            field_40004_N += 1000L;
            fpsCounter = 0;
        }
        Profiler.endSection();
    }

    public void freeMemory()
    {
        try
        {
            field_28006_b = new byte[0];
            renderGlobal.func_28137_f();
        }
        catch(Throwable throwable) { }
        try
        {
            System.gc();
            AxisAlignedBB.clearBoundingBoxes();
            Vec3D.clearVectorList();
        }
        catch(Throwable throwable1) { }
        try
        {
            System.gc();
            changeWorld1(null);
        }
        catch(Throwable throwable2) { }
        System.gc();
    }

    private void screenshotListener()
    {
        if(Keyboard.isKeyDown(60))
        {
            if(!isTakingScreenshot)
            {
                isTakingScreenshot = true;
                ingameGUI.addChatMessage(ScreenShotHelper.saveScreenshot(minecraftDir, displayWidth, displayHeight));
            }
        } else
        {
            isTakingScreenshot = false;
        }
    }

    private void func_40003_b(int i)
    {
        java.util.List list;
        ProfilerResult profilerresult;
        list = Profiler.getProfilingData(field_40006_ak);
        if(list == null || list.size() == 0)
        {
            return;
        }
        profilerresult = (ProfilerResult)list.remove(0);
        if(i == 0)
        {
            if(profilerresult.field_40703_c.length() > 0)
            {
                int j = field_40006_ak.lastIndexOf(".");
                if(j >= 0)
                {
                    field_40006_ak = field_40006_ak.substring(0, j);
                }
            }
        }
        else
        {
            if(--i < list.size() && !((ProfilerResult)list.get(i)).field_40703_c.equals("unspecified"))
            {
                if(field_40006_ak.length() > 0)
                {
                    field_40006_ak += ".";
                }
                field_40006_ak += ((ProfilerResult)list.get(i)).field_40703_c;
            }
        }
    }

    private void displayDebugInfo(long l)
    {
        java.util.List list = Profiler.getProfilingData(field_40006_ak);
        ProfilerResult profilerresult = (ProfilerResult)list.remove(0);
        long l1 = 0xfe502aL;
        if(prevFrameTime == -1L)
        {
            prevFrameTime = System.nanoTime();
        }
        long l2 = System.nanoTime();
        tickTimes[numRecordedFrameTimes & frameTimes.length - 1] = l;
        frameTimes[numRecordedFrameTimes++ & frameTimes.length - 1] = l2 - prevFrameTime;
        prevFrameTime = l2;
        GL11.glClear(256);
        GL11.glMatrixMode(5889 /*GL_PROJECTION*/);
        GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, displayWidth, displayHeight, 0.0D, 1000D, 3000D);
        GL11.glMatrixMode(5888 /*GL_MODELVIEW0_ARB*/);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000F);
        GL11.glLineWidth(1.0F);
        GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(7);
        int i = (int)(l1 / 0x30d40L);
        tessellator.setColorOpaque_I(0x20000000);
        tessellator.addVertex(0.0D, displayHeight - i, 0.0D);
        tessellator.addVertex(0.0D, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - i, 0.0D);
        tessellator.setColorOpaque_I(0x20200000);
        tessellator.addVertex(0.0D, displayHeight - i * 2, 0.0D);
        tessellator.addVertex(0.0D, displayHeight - i, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - i, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - i * 2, 0.0D);
        tessellator.draw();
        long l3 = 0L;
        for(int j = 0; j < frameTimes.length; j++)
        {
            l3 += frameTimes[j];
        }

        int k = (int)(l3 / 0x30d40L / (long)frameTimes.length);
        tessellator.startDrawing(7);
        tessellator.setColorOpaque_I(0x20400000);
        tessellator.addVertex(0.0D, displayHeight - k, 0.0D);
        tessellator.addVertex(0.0D, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight, 0.0D);
        tessellator.addVertex(frameTimes.length, displayHeight - k, 0.0D);
        tessellator.draw();
        tessellator.startDrawing(1);
        for(int i1 = 0; i1 < frameTimes.length; i1++)
        {
            int k1 = ((i1 - numRecordedFrameTimes & frameTimes.length - 1) * 255) / frameTimes.length;
            int j2 = (k1 * k1) / 255;
            j2 = (j2 * j2) / 255;
            int i3 = (j2 * j2) / 255;
            i3 = (i3 * i3) / 255;
            if(frameTimes[i1] > l1)
            {
                tessellator.setColorOpaque_I(0xff000000 + j2 * 0x10000);
            } else
            {
                tessellator.setColorOpaque_I(0xff000000 + j2 * 256);
            }
            long l4 = frameTimes[i1] / 0x30d40L;
            long l5 = tickTimes[i1] / 0x30d40L;
            tessellator.addVertex((float)i1 + 0.5F, (float)((long)displayHeight - l4) + 0.5F, 0.0D);
            tessellator.addVertex((float)i1 + 0.5F, (float)displayHeight + 0.5F, 0.0D);
            tessellator.setColorOpaque_I(0xff000000 + j2 * 0x10000 + j2 * 256 + j2 * 1);
            tessellator.addVertex((float)i1 + 0.5F, (float)((long)displayHeight - l4) + 0.5F, 0.0D);
            tessellator.addVertex((float)i1 + 0.5F, (float)((long)displayHeight - (l4 - l5)) + 0.5F, 0.0D);
        }

        tessellator.draw();
        int j1 = 160;
        int i2 = displayWidth - j1 - 10;
        int k2 = displayHeight - j1 * 2;
        GL11.glEnable(3042 /*GL_BLEND*/);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 200);
        tessellator.addVertex((float)i2 - (float)j1 * 1.1F, (float)k2 - (float)j1 * 0.6F - 16F, 0.0D);
        tessellator.addVertex((float)i2 - (float)j1 * 1.1F, k2 + j1 * 2, 0.0D);
        tessellator.addVertex((float)i2 + (float)j1 * 1.1F, k2 + j1 * 2, 0.0D);
        tessellator.addVertex((float)i2 + (float)j1 * 1.1F, (float)k2 - (float)j1 * 0.6F - 16F, 0.0D);
        tessellator.draw();
        GL11.glDisable(3042 /*GL_BLEND*/);
        double d = 0.0D;
        for(int j3 = 0; j3 < list.size(); j3++)
        {
            ProfilerResult profilerresult1 = (ProfilerResult)list.get(j3);
            int i4 = MathHelper.floor_double(profilerresult1.field_40704_a / 4D) + 1;
            tessellator.startDrawing(6);
            tessellator.setColorOpaque_I(profilerresult1.func_40700_a());
            tessellator.addVertex(i2, k2, 0.0D);
            for(int k4 = i4; k4 >= 0; k4--)
            {
                float f = (float)(((d + (profilerresult1.field_40704_a * (double)k4) / (double)i4) * 3.1415927410125732D * 2D) / 100D);
                float f2 = MathHelper.sin(f) * (float)j1;
                float f4 = MathHelper.cos(f) * (float)j1 * 0.5F;
                tessellator.addVertex((float)i2 + f2, (float)k2 - f4, 0.0D);
            }

            tessellator.draw();
            tessellator.startDrawing(5);
            tessellator.setColorOpaque_I((profilerresult1.func_40700_a() & 0xfefefe) >> 1);
            for(int i5 = i4; i5 >= 0; i5--)
            {
                float f1 = (float)(((d + (profilerresult1.field_40704_a * (double)i5) / (double)i4) * 3.1415927410125732D * 2D) / 100D);
                float f3 = MathHelper.sin(f1) * (float)j1;
                float f5 = MathHelper.cos(f1) * (float)j1 * 0.5F;
                tessellator.addVertex((float)i2 + f3, (float)k2 - f5, 0.0D);
                tessellator.addVertex((float)i2 + f3, ((float)k2 - f5) + 10F, 0.0D);
            }

            tessellator.draw();
            d += profilerresult1.field_40704_a;
        }

        DecimalFormat decimalformat = new DecimalFormat("##0.00");
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        String s = "";
        if(!profilerresult.field_40703_c.equals("unspecified"))
        {
            s = (new StringBuilder()).append(s).append("[0] ").toString();
        }
        if(profilerresult.field_40703_c.length() == 0)
        {
            s = (new StringBuilder()).append(s).append("ROOT ").toString();
        } else
        {
            s = (new StringBuilder()).append(s).append(profilerresult.field_40703_c).append(" ").toString();
        }
        int j4 = 0xffffff;
        fontRenderer.drawStringWithShadow(s, i2 - j1, k2 - j1 / 2 - 16, j4);
        fontRenderer.drawStringWithShadow(s = (new StringBuilder()).append(decimalformat.format(profilerresult.field_40702_b)).append("%").toString(), (i2 + j1) - fontRenderer.getStringWidth(s), k2 - j1 / 2 - 16, j4);
        for(int k3 = 0; k3 < list.size(); k3++)
        {
            ProfilerResult profilerresult2 = (ProfilerResult)list.get(k3);
            String s1 = "";
            if(!profilerresult2.field_40703_c.equals("unspecified"))
            {
                s1 = (new StringBuilder()).append(s1).append("[").append(k3 + 1).append("] ").toString();
            } else
            {
                s1 = (new StringBuilder()).append(s1).append("[?] ").toString();
            }
            s1 = (new StringBuilder()).append(s1).append(profilerresult2.field_40703_c).toString();
            fontRenderer.drawStringWithShadow(s1, i2 - j1, k2 + j1 / 2 + k3 * 8 + 20, profilerresult2.func_40700_a());
            fontRenderer.drawStringWithShadow(s1 = (new StringBuilder()).append(decimalformat.format(profilerresult2.field_40704_a)).append("%").toString(), (i2 + j1) - 50 - fontRenderer.getStringWidth(s1), k2 + j1 / 2 + k3 * 8 + 20, profilerresult2.func_40700_a());
            fontRenderer.drawStringWithShadow(s1 = (new StringBuilder()).append(decimalformat.format(profilerresult2.field_40702_b)).append("%").toString(), (i2 + j1) - fontRenderer.getStringWidth(s1), k2 + j1 / 2 + k3 * 8 + 20, profilerresult2.func_40700_a());
        }

    }

    public void shutdown()
    {
        running = false;
    }

    public void setIngameFocus()
    {
        if(!Display.isActive())
        {
            return;
        }
        if(inGameHasFocus)
        {
            return;
        } else
        {
            inGameHasFocus = true;
            mouseHelper.grabMouseCursor();
            displayGuiScreen(null);
            leftClickCounter = 10000;
            return;
        }
    }

    public void setIngameNotInFocus()
    {
        if(!inGameHasFocus)
        {
            return;
        } else
        {
            KeyBinding.unPressAllKeys();
            inGameHasFocus = false;
            mouseHelper.ungrabMouseCursor();
            return;
        }
    }

    public void displayInGameMenu()
    {
        if(currentScreen != null)
        {
            return;
        } else
        {
            displayGuiScreen(new GuiIngameMenu());
            return;
        }
    }

    private void sendClickBlockToController(int i, boolean flag)
    {
        if(!flag)
        {
            leftClickCounter = 0;
        }
        if(i == 0 && leftClickCounter > 0)
        {
            return;
        }
        if(flag && objectMouseOver != null && objectMouseOver.typeOfHit == EnumMovingObjectType.TILE && i == 0)
        {
            int j = objectMouseOver.blockX;
            int k = objectMouseOver.blockY;
            int l = objectMouseOver.blockZ;
            playerController.sendBlockRemoving(j, k, l, objectMouseOver.sideHit);
            if(thePlayer.func_35190_e(j, k, l))
            {
                effectRenderer.addBlockHitEffects(j, k, l, objectMouseOver.sideHit);
                thePlayer.swingItem();
            }
        } else
        {
            playerController.resetBlockRemoving();
        }
    }

    private void clickMouse(int i)
    {
        if(i == 0 && leftClickCounter > 0)
        {
            return;
        }
        if(i == 0)
        {
            thePlayer.swingItem();
        }
        if(i == 1)
        {
            rightClickDelayTimer = 4;
        }
        boolean flag = true;
        ItemStack itemstack = thePlayer.inventory.getCurrentItem();
        if(objectMouseOver == null)
        {
            if(i == 0 && playerController.func_35641_g())
            {
                leftClickCounter = 10;
            }
        } else
        if(objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY)
        {
            if(i == 0)
            {
                playerController.attackEntity(thePlayer, objectMouseOver.entityHit);
            }
            if(i == 1)
            {
                playerController.interactWithEntity(thePlayer, objectMouseOver.entityHit);
            }
        } else
        if(objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
        {
            int j = objectMouseOver.blockX;
            int k = objectMouseOver.blockY;
            int l = objectMouseOver.blockZ;
            int i1 = objectMouseOver.sideHit;
            if(i == 0)
            {
                playerController.clickBlock(j, k, l, objectMouseOver.sideHit);
            } else
            {
                ItemStack itemstack2 = itemstack;
                int j1 = itemstack2 == null ? 0 : itemstack2.stackSize;
                if(playerController.sendPlaceBlock(thePlayer, theWorld, itemstack2, j, k, l, i1))
                {
                    flag = false;
                    thePlayer.swingItem();
                }
                if(itemstack2 == null)
                {
                    return;
                }
                if(itemstack2.stackSize == 0)
                {
                    thePlayer.inventory.mainInventory[thePlayer.inventory.currentItem] = null;
                } else
                if(itemstack2.stackSize != j1 || playerController.isInCreativeMode())
                {
                    entityRenderer.itemRenderer.func_9449_b();
                }
            }
        }
        if(flag && i == 1)
        {
            ItemStack itemstack1 = thePlayer.inventory.getCurrentItem();
            if(itemstack1 != null && playerController.sendUseItem(thePlayer, theWorld, itemstack1))
            {
                entityRenderer.itemRenderer.func_9450_c();
            }
        }
    }

    public void toggleFullscreen()
    {
        try
        {
            fullscreen = !fullscreen;
            if(fullscreen)
            {
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                displayWidth = Display.getDisplayMode().getWidth();
                displayHeight = Display.getDisplayMode().getHeight();
                if(displayWidth <= 0)
                {
                    displayWidth = 1;
                }
                if(displayHeight <= 0)
                {
                    displayHeight = 1;
                }
            } else
            {
                if(mcCanvas != null)
                {
                    displayWidth = mcCanvas.getWidth();
                    displayHeight = mcCanvas.getHeight();
                } else
                {
                    displayWidth = tempDisplayWidth;
                    displayHeight = tempDisplayHeight;
                }
                if(displayWidth <= 0)
                {
                    displayWidth = 1;
                }
                if(displayHeight <= 0)
                {
                    displayHeight = 1;
                }
            }
            if(currentScreen != null)
            {
                resize(displayWidth, displayHeight);
            }
            Display.setFullscreen(fullscreen);
            Display.update();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void resize(int i, int j)
    {
        if(i <= 0)
        {
            i = 1;
        }
        if(j <= 0)
        {
            j = 1;
        }
        displayWidth = i;
        displayHeight = j;
        if(currentScreen != null)
        {
            ScaledResolution scaledresolution = new ScaledResolution(gameSettings, i, j);
            int k = scaledresolution.getScaledWidth();
            int l = scaledresolution.getScaledHeight();
            currentScreen.setWorldAndResolution(this, k, l);
        }
    }

    private void startThreadCheckHasPaid()
    {
        (new ThreadCheckHasPaid(this)).start();
    }

    public void runTick()
    {
        if(rightClickDelayTimer > 0)
        {
            rightClickDelayTimer--;
        }
        if(ticksRan == 6000)
        {
            startThreadCheckHasPaid();
        }
        Profiler.startSection("stats");
        statFileWriter.func_27178_d();
        Profiler.endStartSection("gui");
        if(!isGamePaused)
        {
            ingameGUI.updateTick();
        }
        Profiler.endStartSection("pick");
        entityRenderer.getMouseOver(1.0F);
        Profiler.endStartSection("centerChunkSource");
        if(thePlayer != null)
        {
            net.minecraft.src.IChunkProvider ichunkprovider = theWorld.getIChunkProvider();
            if(ichunkprovider instanceof ChunkProviderLoadOrGenerate)
            {
                ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate)ichunkprovider;
                int k = MathHelper.floor_float((int)thePlayer.posX) >> 4;
                int j1 = MathHelper.floor_float((int)thePlayer.posZ) >> 4;
                chunkproviderloadorgenerate.setCurrentChunkOver(k, j1);
            }
        }
        Profiler.endStartSection("gameMode");
        if(!isGamePaused && theWorld != null)
        {
            playerController.updateController();
        }
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, renderEngine.getTexture("/terrain.png"));
        Profiler.endStartSection("textures");
        if(!isGamePaused)
        {
            renderEngine.updateDynamicTextures();
        }
        if(currentScreen == null && thePlayer != null)
        {
            if(thePlayer.getEntityHealth() <= 0)
            {
                displayGuiScreen(null);
            } else
            if(thePlayer.isPlayerSleeping() && theWorld != null && theWorld.multiplayerWorld)
            {
                displayGuiScreen(new GuiSleepMP());
            }
        } else
        if(currentScreen != null && (currentScreen instanceof GuiSleepMP) && !thePlayer.isPlayerSleeping())
        {
            displayGuiScreen(null);
        }
        if(currentScreen != null)
        {
            leftClickCounter = 10000;
        }
        if(currentScreen != null)
        {
            currentScreen.handleInput();
            if(currentScreen != null)
            {
                currentScreen.guiParticles.update();
                currentScreen.updateScreen();
            }
        }
        if(currentScreen == null || currentScreen.allowUserInput)
        {
            Profiler.endStartSection("mouse");
            do
            {
                if(!Mouse.next())
                {
                    break;
                }
                KeyBinding.setKeyBindState(Mouse.getEventButton() - 100, Mouse.getEventButtonState());
                if(Mouse.getEventButtonState())
                {
                    KeyBinding.onTick(Mouse.getEventButton() - 100);
                }
                long l = System.currentTimeMillis() - systemTime;
                if(l <= 200L)
                {
                    int i1 = Mouse.getEventDWheel();
                    if(i1 != 0)
                    {
                        thePlayer.inventory.changeCurrentItem(i1);
                        if(gameSettings.noclip)
                        {
                            if(i1 > 0)
                            {
                                i1 = 1;
                            }
                            if(i1 < 0)
                            {
                                i1 = -1;
                            }
                            gameSettings.noclipRate += (float)i1 * 0.25F;
                        }
                    }
                    if(currentScreen == null)
                    {
                        if(!inGameHasFocus && Mouse.getEventButtonState())
                        {
                            setIngameFocus();
                        }
                    } else
                    if(currentScreen != null)
                    {
                        currentScreen.handleMouseInput();
                    }
                }
            } while(true);
            if(leftClickCounter > 0)
            {
                leftClickCounter--;
            }
            Profiler.endStartSection("keyboard");
            do
            {
                if(!Keyboard.next())
                {
                    break;
                }
                KeyBinding.setKeyBindState(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                if(Keyboard.getEventKeyState())
                {
                    KeyBinding.onTick(Keyboard.getEventKey());
                }
                if(Keyboard.getEventKeyState())
                {
                    if(Keyboard.getEventKey() == 87)
                    {
                        toggleFullscreen();
                    } else
                    {
                        if(currentScreen != null)
                        {
                            currentScreen.handleKeyboardInput();
                        } else
                        {
                            if(Keyboard.getEventKey() == 1)
                            {
                                displayInGameMenu();
                            }
                            if(Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61))
                            {
                                forceReload();
                            }
                            if(Keyboard.getEventKey() == 20 && Keyboard.isKeyDown(61))
                            {
                                renderEngine.refreshTextures();
                            }
                            if(Keyboard.getEventKey() == 33 && Keyboard.isKeyDown(61))
                            {
                                boolean flag = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
                                gameSettings.setOptionValue(EnumOptions.RENDER_DISTANCE, flag ? -1 : 1);
                            }
                            if(Keyboard.getEventKey() == 30 && Keyboard.isKeyDown(61))
                            {
                                renderGlobal.loadRenderers();
                            }
                            if(Keyboard.getEventKey() == 59)
                            {
                                gameSettings.hideGUI = !gameSettings.hideGUI;
                            }
                            if(Keyboard.getEventKey() == 61)
                            {
                                gameSettings.showDebugInfo = !gameSettings.showDebugInfo;
                            }
                            if(Keyboard.getEventKey() == 63)
                            {
                                gameSettings.thirdPersonView++;
                                if(gameSettings.thirdPersonView > 2)
                                {
                                    gameSettings.thirdPersonView = 0;
                                }
                            }
                            if(Keyboard.getEventKey() == 66)
                            {
                                gameSettings.smoothCamera = !gameSettings.smoothCamera;
                            }
                        }
                        for(int i = 0; i < 9; i++)
                        {
                            if(Keyboard.getEventKey() == 2 + i)
                            {
                                thePlayer.inventory.currentItem = i;
                            }
                        }

                        if(gameSettings.showDebugInfo)
                        {
                            if(Keyboard.getEventKey() == 11)
                            {
                                func_40003_b(0);
                            }
                            int j = 0;
                            while(j < 9) 
                            {
                                if(Keyboard.getEventKey() == 2 + j)
                                {
                                    func_40003_b(j + 1);
                                }
                                j++;
                            }
                        }
                    }
                }
            } while(true);
            for(; gameSettings.keyBindInventory.isPressed(); displayGuiScreen(new GuiInventory(thePlayer))) { }
            for(; gameSettings.keyBindDrop.isPressed(); thePlayer.dropCurrentItem()) { }
            for(; isMultiplayerWorld() && gameSettings.keyBindChat.isPressed(); displayGuiScreen(new GuiChat())) { }
            if(thePlayer.isUsingItem())
            {
                if(!gameSettings.keyBindUseItem.pressed)
                {
                    playerController.onStoppedUsingItem(thePlayer);
                }
                while(gameSettings.keyBindAttack.isPressed()) ;
                while(gameSettings.keyBindUseItem.isPressed()) ;
                while(gameSettings.keyBindPickBlock.isPressed()) ;
            } else
            {
                for(; gameSettings.keyBindAttack.isPressed(); clickMouse(0)) { }
                for(; gameSettings.keyBindUseItem.isPressed(); clickMouse(1)) { }
                for(; gameSettings.keyBindPickBlock.isPressed(); clickMiddleMouseButton()) { }
            }
            if(gameSettings.keyBindUseItem.pressed && rightClickDelayTimer == 0 && !thePlayer.isUsingItem())
            {
                clickMouse(1);
            }
            sendClickBlockToController(0, currentScreen == null && gameSettings.keyBindAttack.pressed && inGameHasFocus);
        }
        if(theWorld != null)
        {
            if(thePlayer != null)
            {
                joinPlayerCounter++;
                if(joinPlayerCounter == 30)
                {
                    joinPlayerCounter = 0;
                    theWorld.joinEntityInSurroundings(thePlayer);
                }
            }
            if(theWorld.getWorldInfo().isHardcoreModeEnabled())
            {
                theWorld.difficultySetting = 3;
            } else
            {
                theWorld.difficultySetting = gameSettings.difficulty;
            }
            if(theWorld.multiplayerWorld)
            {
                theWorld.difficultySetting = 1;
            }
            Profiler.endStartSection("gameRenderer");
            if(!isGamePaused)
            {
                entityRenderer.updateRenderer();
            }
            Profiler.endStartSection("levelRenderer");
            if(!isGamePaused)
            {
                renderGlobal.updateClouds();
            }
            Profiler.endStartSection("level");
            if(!isGamePaused)
            {
                if(theWorld.lightningFlash > 0)
                {
                    theWorld.lightningFlash--;
                }
                theWorld.updateEntities();
            }
            if(!isGamePaused || isMultiplayerWorld())
            {
                theWorld.setAllowedMobSpawns(theWorld.difficultySetting > 0, true);
                theWorld.tick();
            }
            Profiler.endStartSection("animateTick");
            if(!isGamePaused && theWorld != null)
            {
                theWorld.randomDisplayUpdates(MathHelper.floor_double(thePlayer.posX), MathHelper.floor_double(thePlayer.posY), MathHelper.floor_double(thePlayer.posZ));
            }
            Profiler.endStartSection("particles");
            if(!isGamePaused)
            {
                effectRenderer.updateEffects();
            }
        }
        Profiler.endSection();
        systemTime = System.currentTimeMillis();
    }

    private void forceReload()
    {
        System.out.println("FORCING RELOAD!");
        sndManager = new SoundManager();
        sndManager.loadSoundSettings(gameSettings);
        downloadResourcesThread.reloadResources();
    }

    public boolean isMultiplayerWorld()
    {
        return theWorld != null && theWorld.multiplayerWorld;
    }

    public void startWorld(String s, String s1, WorldSettings worldsettings)
    {
        changeWorld1(null);
        System.gc();
        if(saveLoader.isOldMapFormat(s))
        {
            convertMapFormat(s, s1);
        } else
        {
            if(loadingScreen != null)
            {
                loadingScreen.printText("Switching level");
                loadingScreen.displayLoadingString("");
            }
            net.minecraft.src.ISaveHandler isavehandler = saveLoader.getSaveLoader(s, false);
            World world = null;
            world = new World(isavehandler, s1, worldsettings);
            if(world.isNewWorld)
            {
                statFileWriter.readStat(StatList.createWorldStat, 1);
                statFileWriter.readStat(StatList.startGameStat, 1);
                changeWorld2(world, "Generating level");
            } else
            {
                statFileWriter.readStat(StatList.loadWorldStat, 1);
                statFileWriter.readStat(StatList.startGameStat, 1);
                changeWorld2(world, "Loading level");
            }
        }
    }

    public void usePortal(int i)
    {
        int j = thePlayer.dimension;
        thePlayer.dimension = i;
        theWorld.setEntityDead(thePlayer);
        thePlayer.isDead = false;
        double d = thePlayer.posX;
        double d1 = thePlayer.posZ;
        double d2 = 1.0D;
        if(j > -1 && thePlayer.dimension == -1)
        {
            d2 = 0.125D;
        } else
        if(j == -1 && thePlayer.dimension > -1)
        {
            d2 = 8D;
        }
        d *= d2;
        d1 *= d2;
        if(thePlayer.dimension == -1)
        {
            thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
            if(thePlayer.isEntityAlive())
            {
                theWorld.updateEntityWithOptionalForce(thePlayer, false);
            }
            World world = null;
            world = new World(theWorld, WorldProvider.getProviderForDimension(thePlayer.dimension));
            changeWorld(world, "Entering the Nether", thePlayer);
        } else
        if(thePlayer.dimension == 0)
        {
            if(thePlayer.isEntityAlive())
            {
                thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
                theWorld.updateEntityWithOptionalForce(thePlayer, false);
            }
            World world1 = null;
            world1 = new World(theWorld, WorldProvider.getProviderForDimension(thePlayer.dimension));
            if(j == -1)
            {
                changeWorld(world1, "Leaving the Nether", thePlayer);
            } else
            {
                changeWorld(world1, "Leaving the End", thePlayer);
            }
        } else
        {
            World world2 = null;
            world2 = new World(theWorld, WorldProvider.getProviderForDimension(thePlayer.dimension));
            ChunkCoordinates chunkcoordinates = world2.func_40472_j();
            d = chunkcoordinates.posX;
            thePlayer.posY = chunkcoordinates.posY;
            d1 = chunkcoordinates.posZ;
            thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, 90F, 0.0F);
            if(thePlayer.isEntityAlive())
            {
                world2.updateEntityWithOptionalForce(thePlayer, false);
            }
            changeWorld(world2, "Entering the End", thePlayer);
        }
        thePlayer.worldObj = theWorld;
        System.out.println((new StringBuilder()).append("Teleported to ").append(theWorld.worldProvider.worldType).toString());
        if(thePlayer.isEntityAlive() && j < 1)
        {
            thePlayer.setLocationAndAngles(d, thePlayer.posY, d1, thePlayer.rotationYaw, thePlayer.rotationPitch);
            theWorld.updateEntityWithOptionalForce(thePlayer, false);
            (new Teleporter()).placeInPortal(theWorld, thePlayer);
        }
    }

    public void func_40002_b(String s)
    {
        theWorld = null;
        changeWorld2(null, s);
    }

    public void changeWorld1(World world)
    {
        changeWorld2(world, "");
    }

    public void changeWorld2(World world, String s)
    {
        changeWorld(world, s, null);
    }

    public void changeWorld(World world, String s, EntityPlayer entityplayer)
    {
        statFileWriter.func_27175_b();
        statFileWriter.syncStats();
        renderViewEntity = null;
        if(loadingScreen != null)
        {
            loadingScreen.printText(s);
            loadingScreen.displayLoadingString("");
        }
        sndManager.playStreaming(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        if(theWorld != null)
        {
            theWorld.saveWorldIndirectly(loadingScreen);
        }
        theWorld = world;
        if(world != null)
        {
            if(playerController != null)
            {
                playerController.onWorldChange(world);
            }
            if(!isMultiplayerWorld())
            {
                if(entityplayer == null)
                {
                    thePlayer = (EntityPlayerSP)world.func_4085_a(net.minecraft.src.EntityPlayerSP.class);
                }
            } else
            if(thePlayer != null)
            {
                thePlayer.preparePlayerToSpawn();
                if(world != null)
                {
                    world.entityJoinedWorld(thePlayer);
                }
            }
            if(!world.multiplayerWorld)
            {
                preloadWorld(s);
            }
            if(thePlayer == null)
            {
                thePlayer = (EntityPlayerSP)playerController.createPlayer(world);
                thePlayer.preparePlayerToSpawn();
                playerController.flipPlayer(thePlayer);
            }
            thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
            if(renderGlobal != null)
            {
                renderGlobal.changeWorld(world);
            }
            if(effectRenderer != null)
            {
                effectRenderer.clearEffects(world);
            }
            if(entityplayer != null)
            {
                world.emptyMethod1();
            }
            net.minecraft.src.IChunkProvider ichunkprovider = world.getIChunkProvider();
            if(ichunkprovider instanceof ChunkProviderLoadOrGenerate)
            {
                ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate)ichunkprovider;
                int i = MathHelper.floor_float((int)thePlayer.posX) >> 4;
                int j = MathHelper.floor_float((int)thePlayer.posZ) >> 4;
                chunkproviderloadorgenerate.setCurrentChunkOver(i, j);
            }
            world.spawnPlayerWithLoadedChunks(thePlayer);
            playerController.func_6473_b(thePlayer);
            if(world.isNewWorld)
            {
                world.saveWorldIndirectly(loadingScreen);
            }
            renderViewEntity = thePlayer;
        } else
        {
            saveLoader.flushCache();
            thePlayer = null;
        }
        System.gc();
        systemTime = 0L;
    }

    private void convertMapFormat(String s, String s1)
    {
        loadingScreen.printText((new StringBuilder()).append("Converting World to ").append(saveLoader.func_22178_a()).toString());
        loadingScreen.displayLoadingString("This may take a while :)");
        saveLoader.convertMapFormat(s, loadingScreen);
        startWorld(s, s1, new WorldSettings(0L, 0, true, false));
    }

    private void preloadWorld(String s)
    {
        if(loadingScreen != null)
        {
            loadingScreen.printText(s);
            loadingScreen.displayLoadingString("Building terrain");
        }
        char c = '\200';
        if(playerController.func_35643_e())
        {
            c = '@';
        }
        int i = 0;
        int j = (c * 2) / 16 + 1;
        j *= j;
        net.minecraft.src.IChunkProvider ichunkprovider = theWorld.getIChunkProvider();
        ChunkCoordinates chunkcoordinates = theWorld.getSpawnPoint();
        if(thePlayer != null)
        {
            chunkcoordinates.posX = (int)thePlayer.posX;
            chunkcoordinates.posZ = (int)thePlayer.posZ;
        }
        if(ichunkprovider instanceof ChunkProviderLoadOrGenerate)
        {
            ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate)ichunkprovider;
            chunkproviderloadorgenerate.setCurrentChunkOver(chunkcoordinates.posX >> 4, chunkcoordinates.posZ >> 4);
        }
        for(int k = -c; k <= c; k += 16)
        {
            for(int l = -c; l <= c; l += 16)
            {
                if(loadingScreen != null)
                {
                    loadingScreen.setLoadingProgress((i++ * 100) / j);
                }
                theWorld.getBlockId(chunkcoordinates.posX + k, 64, chunkcoordinates.posZ + l);
                if(playerController.func_35643_e())
                {
                    continue;
                }
                while(theWorld.updatingLighting()) ;
            }

        }

        if(!playerController.func_35643_e())
        {
            if(loadingScreen != null)
            {
                loadingScreen.displayLoadingString("Simulating world for a bit");
            }
            char c1 = '\u07D0';
            theWorld.dropOldChunks();
        }
    }

    public void installResource(String s, File file)
    {
        int i = s.indexOf("/");
        String s1 = s.substring(0, i);
        s = s.substring(i + 1);
        if(s1.equalsIgnoreCase("sound"))
        {
            sndManager.addSound(s, file);
        } else
        if(s1.equalsIgnoreCase("newsound"))
        {
            sndManager.addSound(s, file);
        } else
        if(s1.equalsIgnoreCase("streaming"))
        {
            sndManager.addStreaming(s, file);
        } else
        if(s1.equalsIgnoreCase("music"))
        {
            sndManager.addMusic(s, file);
        } else
        if(s1.equalsIgnoreCase("newmusic"))
        {
            sndManager.addMusic(s, file);
        }
    }

    public String debugInfoRenders()
    {
        return renderGlobal.getDebugInfoRenders();
    }

    public String func_6262_n()
    {
        return renderGlobal.getDebugInfoEntities();
    }

    public String func_21002_o()
    {
        return theWorld.func_21119_g();
    }

    public String debugInfoEntities()
    {
        return (new StringBuilder()).append("P: ").append(effectRenderer.getStatistics()).append(". T: ").append(theWorld.getDebugLoadedEntities()).toString();
    }

    public void respawn(boolean flag, int i, boolean flag1)
    {
        if(!theWorld.multiplayerWorld && !theWorld.worldProvider.canRespawnHere())
        {
            usePortal(0);
        }
        ChunkCoordinates chunkcoordinates = null;
        ChunkCoordinates chunkcoordinates1 = null;
        boolean flag2 = true;
        if(thePlayer != null && !flag)
        {
            chunkcoordinates = thePlayer.getPlayerSpawnCoordinate();
            if(chunkcoordinates != null)
            {
                chunkcoordinates1 = EntityPlayer.verifyRespawnCoordinates(theWorld, chunkcoordinates);
                if(chunkcoordinates1 == null)
                {
                    thePlayer.addChatMessage("tile.bed.notValid");
                }
            }
        }
        if(chunkcoordinates1 == null)
        {
            chunkcoordinates1 = theWorld.getSpawnPoint();
            flag2 = false;
        }
        net.minecraft.src.IChunkProvider ichunkprovider = theWorld.getIChunkProvider();
        if(ichunkprovider instanceof ChunkProviderLoadOrGenerate)
        {
            ChunkProviderLoadOrGenerate chunkproviderloadorgenerate = (ChunkProviderLoadOrGenerate)ichunkprovider;
            chunkproviderloadorgenerate.setCurrentChunkOver(chunkcoordinates1.posX >> 4, chunkcoordinates1.posZ >> 4);
        }
        theWorld.setSpawnLocation();
        theWorld.updateEntityList();
        int j = 0;
        if(thePlayer != null)
        {
            j = thePlayer.entityId;
            theWorld.setEntityDead(thePlayer);
        }
        EntityPlayerSP entityplayersp = thePlayer;
        renderViewEntity = null;
        thePlayer = (EntityPlayerSP)playerController.createPlayer(theWorld);
        if(flag1)
        {
            thePlayer.func_41014_d(entityplayersp);
        }
        thePlayer.dimension = i;
        renderViewEntity = thePlayer;
        thePlayer.preparePlayerToSpawn();
        if(flag2)
        {
            thePlayer.setPlayerSpawnCoordinate(chunkcoordinates);
            thePlayer.setLocationAndAngles((float)chunkcoordinates1.posX + 0.5F, (float)chunkcoordinates1.posY + 0.1F, (float)chunkcoordinates1.posZ + 0.5F, 0.0F, 0.0F);
        }
        playerController.flipPlayer(thePlayer);
        theWorld.spawnPlayerWithLoadedChunks(thePlayer);
        thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
        thePlayer.entityId = j;
        thePlayer.func_6420_o();
        playerController.func_6473_b(thePlayer);
        preloadWorld("Respawning");
        if(currentScreen instanceof GuiGameOver)
        {
            displayGuiScreen(null);
        }
    }

    public static void startMainThread1(String s, String s1)
    {
        startMainThread(s, s1, null);
    }

    public static void startMainThread(String s, String s1, String s2)
    {
        boolean flag = false;
        String s3 = s;
        Frame frame = new Frame("Minecraft");
        Canvas canvas = new Canvas();
        frame.setLayout(new BorderLayout());
        frame.add(canvas, "Center");
        canvas.setPreferredSize(new Dimension(854, 480));
        frame.pack();
        frame.setLocationRelativeTo(null);
        MinecraftImpl minecraftimpl = new MinecraftImpl(frame, canvas, null, 854, 480, flag, frame);
        Thread thread = new Thread(minecraftimpl, "Minecraft main thread");
        thread.setPriority(10);
        minecraftimpl.minecraftUri = "www.minecraft.net";
        if(s3 != null && s1 != null)
        {
            minecraftimpl.session = new Session(s3, s1);
        } else
        {
            minecraftimpl.session = new Session((new StringBuilder()).append("Player").append(System.currentTimeMillis() % 1000L).toString(), "");
        }
        if(s2 != null)
        {
            String as[] = s2.split(":");
            minecraftimpl.setServer(as[0], Integer.parseInt(as[1]));
        }
        frame.setVisible(true);
        frame.addWindowListener(new GameWindowListener(minecraftimpl, thread));
        thread.start();
    }

    public NetClientHandler getSendQueue()
    {
        if(thePlayer instanceof EntityClientPlayerMP)
        {
            return ((EntityClientPlayerMP)thePlayer).sendQueue;
        } else
        {
            return null;
        }
    }

    public static void main(String args[])
    {
        String s = null;
        String s1 = null;
        s = (new StringBuilder()).append("Player").append(System.currentTimeMillis() % 1000L).toString();
        if(args.length > 0)
        {
            s = args[0];
        }
        s1 = "-";
        if(args.length > 1)
        {
            s1 = args[1];
        }
        startMainThread1(s, s1);
    }

    public static boolean isGuiEnabled()
    {
        return theMinecraft == null || !theMinecraft.gameSettings.hideGUI;
    }

    public static boolean isFancyGraphicsEnabled()
    {
        return theMinecraft != null && theMinecraft.gameSettings.fancyGraphics;
    }

    public static boolean isAmbientOcclusionEnabled()
    {
        return theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion;
    }

    public static boolean isDebugInfoEnabled()
    {
        return theMinecraft != null && theMinecraft.gameSettings.showDebugInfo;
    }

    public boolean lineIsCommand(String s)
    {
        if(!s.startsWith("/"));
        return false;
    }

    private void clickMiddleMouseButton()
    {
        if(objectMouseOver != null)
        {
            int i = theWorld.getBlockId(objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ);
            if(i == Block.grass.blockID)
            {
                i = Block.dirt.blockID;
            }
            if(i == Block.stairDouble.blockID)
            {
                i = Block.stairSingle.blockID;
            }
            if(i == Block.bedrock.blockID)
            {
                i = Block.stone.blockID;
            }
            int j = 0;
            boolean flag = false;
            if(Item.itemsList[i].getHasSubtypes())
            {
                j = theWorld.getBlockMetadata(objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ);
                flag = true;
            }
            thePlayer.inventory.setCurrentItem(i, j, flag, playerController instanceof PlayerControllerCreative);
        }
    }

}
