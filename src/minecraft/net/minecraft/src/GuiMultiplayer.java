// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, GuiSlotServer, CompressedStreamTools, NBTTagCompound, 
//            NBTTagList, ServerNBTStorage, StringTranslate, GuiButton, 
//            GuiYesNo, GuiScreenServerList, StatCollector, GuiScreenAddServer, 
//            GuiConnecting, Packet, ChatAllowedCharacters, FontRenderer

public class GuiMultiplayer extends GuiScreen
{

    private static int threadsPending = 0;
    private static Object lock = new Object();
    private GuiScreen parentScreen;
    private GuiSlotServer serverSlotContainer;
    private List serverList;
    private int selectedServer;
    private GuiButton buttonEdit;
    private GuiButton buttonSelect;
    private GuiButton buttonDelete;
    private boolean deleteClicked;
    private boolean addClicked;
    private boolean editClicked;
    private boolean directClicked;
    private String field_35350_v;
    private ServerNBTStorage tempServer;

    public GuiMultiplayer(GuiScreen guiscreen)
    {
        serverList = new ArrayList();
        selectedServer = -1;
        deleteClicked = false;
        addClicked = false;
        editClicked = false;
        directClicked = false;
        field_35350_v = null;
        tempServer = null;
        parentScreen = guiscreen;
    }

    public void updateScreen()
    {
    }

    public void initGui()
    {
        loadServerList();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        serverSlotContainer = new GuiSlotServer(this);
        initGuiControls();
    }

    private void loadServerList()
    {
        try
        {
            NBTTagCompound nbttagcompound = CompressedStreamTools.func_35622_a(new File(mc.mcDataDir, "servers.dat"));
            NBTTagList nbttaglist = nbttagcompound.getTagList("servers");
            serverList.clear();
            for(int i = 0; i < nbttaglist.tagCount(); i++)
            {
                serverList.add(ServerNBTStorage.createServerNBTStorage((NBTTagCompound)nbttaglist.tagAt(i)));
            }

        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void saveServerList()
    {
        try
        {
            NBTTagList nbttaglist = new NBTTagList();
            for(int i = 0; i < serverList.size(); i++)
            {
                nbttaglist.setTag(((ServerNBTStorage)serverList.get(i)).getCompoundTag());
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setTag("servers", nbttaglist);
            CompressedStreamTools.func_35621_a(nbttagcompound, new File(mc.mcDataDir, "servers.dat"));
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void initGuiControls()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.add(buttonEdit = new GuiButton(7, width / 2 - 154, height - 28, 70, 20, stringtranslate.translateKey("selectServer.edit")));
        controlList.add(buttonDelete = new GuiButton(2, width / 2 - 74, height - 28, 70, 20, stringtranslate.translateKey("selectServer.delete")));
        controlList.add(buttonSelect = new GuiButton(1, width / 2 - 154, height - 52, 100, 20, stringtranslate.translateKey("selectServer.select")));
        controlList.add(new GuiButton(4, width / 2 - 50, height - 52, 100, 20, stringtranslate.translateKey("selectServer.direct")));
        controlList.add(new GuiButton(3, width / 2 + 4 + 50, height - 52, 100, 20, stringtranslate.translateKey("selectServer.add")));
        controlList.add(new GuiButton(8, width / 2 + 4, height - 28, 70, 20, stringtranslate.translateKey("selectServer.refresh")));
        controlList.add(new GuiButton(0, width / 2 + 4 + 76, height - 28, 75, 20, stringtranslate.translateKey("gui.cancel")));
        boolean flag = selectedServer >= 0 && selectedServer < serverSlotContainer.getSize();
        buttonSelect.enabled = flag;
        buttonEdit.enabled = flag;
        buttonDelete.enabled = flag;
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 2)
        {
            String s = ((ServerNBTStorage)serverList.get(selectedServer)).name;
            if(s != null)
            {
                deleteClicked = true;
                StringTranslate stringtranslate = StringTranslate.getInstance();
                String s1 = stringtranslate.translateKey("selectServer.deleteQuestion");
                String s2 = (new StringBuilder()).append("'").append(s).append("' ").append(stringtranslate.translateKey("selectServer.deleteWarning")).toString();
                String s3 = stringtranslate.translateKey("selectServer.deleteButton");
                String s4 = stringtranslate.translateKey("gui.cancel");
                GuiYesNo guiyesno = new GuiYesNo(this, s1, s2, s3, s4, selectedServer);
                mc.displayGuiScreen(guiyesno);
            }
        } else
        if(guibutton.id == 1)
        {
            joinServer(selectedServer);
        } else
        if(guibutton.id == 4)
        {
            directClicked = true;
            mc.displayGuiScreen(new GuiScreenServerList(this, tempServer = new ServerNBTStorage(StatCollector.translateToLocal("selectServer.defaultName"), "")));
        } else
        if(guibutton.id == 3)
        {
            addClicked = true;
            mc.displayGuiScreen(new GuiScreenAddServer(this, tempServer = new ServerNBTStorage(StatCollector.translateToLocal("selectServer.defaultName"), "")));
        } else
        if(guibutton.id == 7)
        {
            editClicked = true;
            ServerNBTStorage servernbtstorage = (ServerNBTStorage)serverList.get(selectedServer);
            mc.displayGuiScreen(new GuiScreenAddServer(this, tempServer = new ServerNBTStorage(servernbtstorage.name, servernbtstorage.host)));
        } else
        if(guibutton.id == 0)
        {
            mc.displayGuiScreen(parentScreen);
        } else
        if(guibutton.id == 8)
        {
            mc.displayGuiScreen(new GuiMultiplayer(parentScreen));
        } else
        {
            serverSlotContainer.actionPerformed(guibutton);
        }
    }

    public void deleteWorld(boolean flag, int i)
    {
        if(deleteClicked)
        {
            deleteClicked = false;
            if(flag)
            {
                serverList.remove(i);
                saveServerList();
            }
            mc.displayGuiScreen(this);
        } else
        if(directClicked)
        {
            directClicked = false;
            if(flag)
            {
                joinServer(tempServer);
            } else
            {
                mc.displayGuiScreen(this);
            }
        } else
        if(addClicked)
        {
            addClicked = false;
            if(flag)
            {
                serverList.add(tempServer);
                saveServerList();
            }
            mc.displayGuiScreen(this);
        } else
        if(editClicked)
        {
            editClicked = false;
            if(flag)
            {
                ServerNBTStorage servernbtstorage = (ServerNBTStorage)serverList.get(selectedServer);
                servernbtstorage.name = tempServer.name;
                servernbtstorage.host = tempServer.host;
                saveServerList();
            }
            mc.displayGuiScreen(this);
        }
    }

    private int parseIntWithDefault(String s, int i)
    {
        try
        {
            return Integer.parseInt(s.trim());
        }
        catch(Exception exception)
        {
            return i;
        }
    }

    protected void keyTyped(char c, int i)
    {
        if(c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(2));
        }
    }

    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
    }

    public void drawScreen(int i, int j, float f)
    {
        field_35350_v = null;
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        serverSlotContainer.drawScreen(i, j, f);
        drawCenteredString(fontRenderer, stringtranslate.translateKey("multiplayer.title"), width / 2, 20, 0xffffff);
        super.drawScreen(i, j, f);
        if(field_35350_v != null)
        {
            func_35325_a(field_35350_v, i, j);
        }
    }

    private void joinServer(int i)
    {
        joinServer((ServerNBTStorage)serverList.get(i));
    }

    private void joinServer(ServerNBTStorage servernbtstorage)
    {
        String s = servernbtstorage.host;
        String as[] = s.split(":");
        if(s.startsWith("["))
        {
            int i = s.indexOf("]");
            if(i > 0)
            {
                String s1 = s.substring(1, i);
                String s2 = s.substring(i + 1).trim();
                if(s2.startsWith(":") && s2.length() > 0)
                {
                    s2 = s2.substring(1);
                    as = new String[2];
                    as[0] = s1;
                    as[1] = s2;
                } else
                {
                    as = new String[1];
                    as[0] = s1;
                }
            }
        }
        if(as.length > 2)
        {
            as = new String[1];
            as[0] = s;
        }
        mc.displayGuiScreen(new GuiConnecting(mc, as[0], as.length <= 1 ? 25565 : parseIntWithDefault(as[1], 25565)));
    }

    private void pollServer(ServerNBTStorage servernbtstorage)
        throws IOException
    {
        String s = servernbtstorage.host;
        String as[] = s.split(":");
        if(s.startsWith("["))
        {
            int i = s.indexOf("]");
            if(i > 0)
            {
                String s2 = s.substring(1, i);
                String s3 = s.substring(i + 1).trim();
                if(s3.startsWith(":") && s3.length() > 0)
                {
                    s3 = s3.substring(1);
                    as = new String[2];
                    as[0] = s2;
                    as[1] = s3;
                } else
                {
                    as = new String[1];
                    as[0] = s2;
                }
            }
        }
        if(as.length > 2)
        {
            as = new String[1];
            as[0] = s;
        }
        String s1 = as[0];
        int j = as.length <= 1 ? 25565 : parseIntWithDefault(as[1], 25565);
        Socket socket = null;
        DataInputStream datainputstream = null;
        DataOutputStream dataoutputstream = null;
        try
        {
            socket = new Socket();
            socket.setSoTimeout(3000);
            socket.setTcpNoDelay(true);
            socket.setTrafficClass(18);
            socket.connect(new InetSocketAddress(s1, j), 3000);
            datainputstream = new DataInputStream(socket.getInputStream());
            dataoutputstream = new DataOutputStream(socket.getOutputStream());
            dataoutputstream.write(254);
            if(datainputstream.read() != 255)
            {
                throw new IOException("Bad message");
            }
            String s4 = Packet.readString(datainputstream, 64);
            char ac[] = s4.toCharArray();
            for(int k = 0; k < ac.length; k++)
            {
                if(ac[k] != '\247' && ChatAllowedCharacters.allowedCharacters.indexOf(ac[k]) < 0)
                {
                    ac[k] = '?';
                }
            }

            s4 = new String(ac);
            String as1[] = s4.split("\247");
            s4 = as1[0];
            int l = -1;
            int i1 = -1;
            try
            {
                l = Integer.parseInt(as1[1]);
                i1 = Integer.parseInt(as1[2]);
            }
            catch(Exception exception) { }
            servernbtstorage.motd = (new StringBuilder()).append("\2477").append(s4).toString();
            if(l >= 0 && i1 > 0)
            {
                servernbtstorage.playerCount = (new StringBuilder()).append("\2477").append(l).append("\2478/\2477").append(i1).toString();
            } else
            {
                servernbtstorage.playerCount = "\2478???";
            }
        }
        finally
        {
            try
            {
                if(datainputstream != null)
                {
                    datainputstream.close();
                }
            }
            catch(Throwable throwable) { }
            try
            {
                if(dataoutputstream != null)
                {
                    dataoutputstream.close();
                }
            }
            catch(Throwable throwable1) { }
            try
            {
                if(socket != null)
                {
                    socket.close();
                }
            }
            catch(Throwable throwable2) { }
        }
    }

    protected void func_35325_a(String s, int i, int j)
    {
        if(s == null)
        {
            return;
        } else
        {
            int k = i + 12;
            int l = j - 12;
            int i1 = fontRenderer.getStringWidth(s);
            drawGradientRect(k - 3, l - 3, k + i1 + 3, l + 8 + 3, 0xc0000000, 0xc0000000);
            fontRenderer.drawStringWithShadow(s, k, l, -1);
            return;
        }
    }

    static List getServerList(GuiMultiplayer guimultiplayer)
    {
        return guimultiplayer.serverList;
    }

    static int setSelectedServer(GuiMultiplayer guimultiplayer, int i)
    {
        return guimultiplayer.selectedServer = i;
    }

    static int getSelectedServer(GuiMultiplayer guimultiplayer)
    {
        return guimultiplayer.selectedServer;
    }

    static GuiButton getButtonSelect(GuiMultiplayer guimultiplayer)
    {
        return guimultiplayer.buttonSelect;
    }

    static GuiButton getButtonEdit(GuiMultiplayer guimultiplayer)
    {
        return guimultiplayer.buttonEdit;
    }

    static GuiButton getButtonDelete(GuiMultiplayer guimultiplayer)
    {
        return guimultiplayer.buttonDelete;
    }

    static void joinServer(GuiMultiplayer guimultiplayer, int i)
    {
        guimultiplayer.joinServer(i);
    }

    static Object getLock()
    {
        return lock;
    }

    static int getThreadsPending()
    {
        return threadsPending;
    }

    static int incrementThreadsPending()
    {
        return threadsPending++;
    }

    static void pollServer(GuiMultiplayer guimultiplayer, ServerNBTStorage servernbtstorage)
        throws IOException
    {
        guimultiplayer.pollServer(servernbtstorage);
    }

    static int decrementThreadsPending()
    {
        return threadsPending--;
    }

    static String func_35327_a(GuiMultiplayer guimultiplayer, String s)
    {
        return guimultiplayer.field_35350_v = s;
    }

}
