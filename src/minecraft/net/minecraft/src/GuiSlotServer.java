// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GuiSlot, GuiMultiplayer, GuiButton, ServerNBTStorage, 
//            ThreadPollServers, FontRenderer, RenderEngine, Tessellator

class GuiSlotServer extends GuiSlot
{

    final GuiMultiplayer field_35410_a; /* synthetic field */

    public GuiSlotServer(GuiMultiplayer guimultiplayer)
    {
        super(guimultiplayer.mc, guimultiplayer.width, guimultiplayer.height, 32, guimultiplayer.height - 64, 36);
        field_35410_a = guimultiplayer;
    }

    protected int getSize()
    {
        return GuiMultiplayer.getServerList(field_35410_a).size();
    }

    protected void elementClicked(int i, boolean flag)
    {
        GuiMultiplayer.setSelectedServer(field_35410_a, i);
        boolean flag1 = GuiMultiplayer.getSelectedServer(field_35410_a) >= 0 && GuiMultiplayer.getSelectedServer(field_35410_a) < getSize();
        GuiMultiplayer.getButtonSelect(field_35410_a).enabled = flag1;
        GuiMultiplayer.getButtonEdit(field_35410_a).enabled = flag1;
        GuiMultiplayer.getButtonDelete(field_35410_a).enabled = flag1;
        if(flag && flag1)
        {
            GuiMultiplayer.joinServer(field_35410_a, i);
        }
    }

    protected boolean isSelected(int i)
    {
        return i == GuiMultiplayer.getSelectedServer(field_35410_a);
    }

    protected int getContentHeight()
    {
        return GuiMultiplayer.getServerList(field_35410_a).size() * 36;
    }

    protected void drawBackground()
    {
        field_35410_a.drawDefaultBackground();
    }

    protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator)
    {
        ServerNBTStorage servernbtstorage = (ServerNBTStorage)GuiMultiplayer.getServerList(field_35410_a).get(i);
        synchronized(GuiMultiplayer.getLock())
        {
            if(GuiMultiplayer.getThreadsPending() < 5 && !servernbtstorage.polled)
            {
                servernbtstorage.polled = true;
                servernbtstorage.lag = -2L;
                servernbtstorage.motd = "";
                servernbtstorage.playerCount = "";
                GuiMultiplayer.incrementThreadsPending();
                (new ThreadPollServers(this, servernbtstorage)).start();
            }
        }
        field_35410_a.drawString(field_35410_a.fontRenderer, servernbtstorage.name, j + 2, k + 1, 0xffffff);
        field_35410_a.drawString(field_35410_a.fontRenderer, servernbtstorage.motd, j + 2, k + 12, 0x808080);
        field_35410_a.drawString(field_35410_a.fontRenderer, servernbtstorage.playerCount, (j + 215) - field_35410_a.fontRenderer.getStringWidth(servernbtstorage.playerCount), k + 12, 0x808080);
        field_35410_a.drawString(field_35410_a.fontRenderer, servernbtstorage.host, j + 2, k + 12 + 11, 0x303030);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        field_35410_a.mc.renderEngine.bindTexture(field_35410_a.mc.renderEngine.getTexture("/gui/icons.png"));
        int i1 = 0;
        int j1 = 0;
        String s = "";
        if(servernbtstorage.polled && servernbtstorage.lag != -2L)
        {
            i1 = 0;
            j1 = 0;
            if(servernbtstorage.lag < 0L)
            {
                j1 = 5;
            } else
            if(servernbtstorage.lag < 150L)
            {
                j1 = 0;
            } else
            if(servernbtstorage.lag < 300L)
            {
                j1 = 1;
            } else
            if(servernbtstorage.lag < 600L)
            {
                j1 = 2;
            } else
            if(servernbtstorage.lag < 1000L)
            {
                j1 = 3;
            } else
            {
                j1 = 4;
            }
            if(servernbtstorage.lag < 0L)
            {
                s = "(no connection)";
            } else
            {
                s = (new StringBuilder()).append(servernbtstorage.lag).append("ms").toString();
            }
        } else
        {
            i1 = 1;
            j1 = (int)(System.currentTimeMillis() / 100L + (long)(i * 2) & 7L);
            if(j1 > 4)
            {
                j1 = 8 - j1;
            }
            s = "Polling..";
        }
        field_35410_a.drawTexturedModalRect(j + 205, k, 0 + i1 * 10, 176 + j1 * 8, 10, 8);
        byte byte0 = 4;
        if(field_35409_k >= (j + 205) - byte0 && field_35408_l >= k - byte0 && field_35409_k <= j + 205 + 10 + byte0 && field_35408_l <= k + 8 + byte0)
        {
            GuiMultiplayer.func_35327_a(field_35410_a, s);
        }
    }
}
