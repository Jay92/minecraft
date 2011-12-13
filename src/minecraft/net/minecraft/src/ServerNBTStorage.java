// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            NBTTagCompound

public class ServerNBTStorage
{

    public String name;
    public String host;
    public String playerCount;
    public String motd;
    public long lag;
    public boolean polled;

    public ServerNBTStorage(String s, String s1)
    {
        polled = false;
        name = s;
        host = s1;
    }

    public NBTTagCompound getCompoundTag()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("name", name);
        nbttagcompound.setString("ip", host);
        return nbttagcompound;
    }

    public static ServerNBTStorage createServerNBTStorage(NBTTagCompound nbttagcompound)
    {
        return new ServerNBTStorage(nbttagcompound.getString("name"), nbttagcompound.getString("ip"));
    }
}
