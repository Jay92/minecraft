// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet108EnchantItem extends Packet
{

    public int windowId;
    public int enchantment;

    public Packet108EnchantItem()
    {
    }

    public Packet108EnchantItem(int i, int j)
    {
        windowId = i;
        enchantment = j;
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_40599_a(this);
    }

    public void readPacketData(DataInputStream datainputstream)
        throws IOException
    {
        windowId = datainputstream.readByte();
        enchantment = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeByte(windowId);
        dataoutputstream.writeByte(enchantment);
    }

    public int getPacketSize()
    {
        return 2;
    }
}
