// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet254ServerPing extends Packet
{

    public Packet254ServerPing()
    {
    }

    public void readPacketData(DataInputStream datainputstream)
        throws IOException
    {
    }

    public void writePacketData(DataOutputStream dataoutputstream)
        throws IOException
    {
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleServerPing(this);
    }

    public int getPacketSize()
    {
        return 0;
    }
}
