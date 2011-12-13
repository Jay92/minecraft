// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.awt.Canvas;
import net.minecraft.client.MinecraftApplet;

public class CanvasMinecraftApplet extends Canvas
{

    final MinecraftApplet mcApplet; /* synthetic field */

    public CanvasMinecraftApplet(MinecraftApplet minecraftapplet)
    {
        mcApplet = minecraftapplet;
//        super();
    }

    public synchronized void addNotify()
    {
        super.addNotify();
        mcApplet.startMainThread();
    }

    public synchronized void removeNotify()
    {
        mcApplet.shutdown();
        super.removeNotify();
    }
}
