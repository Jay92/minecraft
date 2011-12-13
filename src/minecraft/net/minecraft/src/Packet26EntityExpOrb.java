// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;

// Referenced classes of package net.minecraft.src:
//            Packet, EntityXPOrb, MathHelper, NetHandler

public class Packet26EntityExpOrb extends Packet
{

    public int entityId;
    public int posX;
    public int posY;
    public int posZ;
    public int count;

    public Packet26EntityExpOrb()
    {
    }

    public Packet26EntityExpOrb(EntityXPOrb entityxporb)
    {
        entityId = entityxporb.entityId;
        posX = MathHelper.floor_double(entityxporb.posX * 32D);
        posY = MathHelper.floor_double(entityxporb.posY * 32D);
        posZ = MathHelper.floor_double(entityxporb.posZ * 32D);
        count = entityxporb.getXpValue();
    }

    public void readPacketData(DataInputStream datainputstream)
        throws IOException
    {
        entityId = datainputstream.readInt();
        posX = datainputstream.readInt();
        posY = datainputstream.readInt();
        posZ = datainputstream.readInt();
        count = datainputstream.readShort();
    }

    public void writePacketData(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeInt(entityId);
        dataoutputstream.writeInt(posX);
        dataoutputstream.writeInt(posY);
        dataoutputstream.writeInt(posZ);
        dataoutputstream.writeShort(count);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleXPOrb(this);
    }

    public int getPacketSize()
    {
        return 18;
    }
}
