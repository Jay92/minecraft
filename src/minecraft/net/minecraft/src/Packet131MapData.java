// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, NetHandler

public class Packet131MapData extends Packet
{

    public short itemID;
    public short uniqueID;
    public byte itemData[];

    public Packet131MapData()
    {
        isChunkDataPacket = true;
    }

    public void readPacketData(DataInputStream datainputstream)
        throws IOException
    {
        itemID = datainputstream.readShort();
        uniqueID = datainputstream.readShort();
        itemData = new byte[datainputstream.readByte() & 0xff];
        datainputstream.readFully(itemData);
    }

    public void writePacketData(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeShort(itemID);
        dataoutputstream.writeShort(uniqueID);
        dataoutputstream.writeByte(itemData.length);
        dataoutputstream.write(itemData);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.processItemData(this);
    }

    public int getPacketSize()
    {
        return 4 + itemData.length;
    }
}
