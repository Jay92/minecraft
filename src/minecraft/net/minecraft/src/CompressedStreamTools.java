// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

// Referenced classes of package net.minecraft.src:
//            NBTBase, NBTTagCompound

public class CompressedStreamTools
{

    public CompressedStreamTools()
    {
    }

    public static NBTTagCompound loadGzippedCompoundFromOutputStream(InputStream inputstream)
        throws IOException
    {
        DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputstream)));
        try
        {
            NBTTagCompound nbttagcompound = func_1141_a(datainputstream);
            return nbttagcompound;
        }
        finally
        {
            datainputstream.close();
        }
    }

    public static void writeGzippedCompoundToOutputStream(NBTTagCompound nbttagcompound, OutputStream outputstream)
        throws IOException
    {
        DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(outputstream));
        try
        {
            writeTo(nbttagcompound, dataoutputstream);
        }
        finally
        {
            dataoutputstream.close();
        }
    }

    public static NBTTagCompound func_40592_a(byte abyte0[])
        throws IOException
    {
        DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte0))));
        try
        {
            NBTTagCompound nbttagcompound = func_1141_a(datainputstream);
            return nbttagcompound;
        }
        finally
        {
            datainputstream.close();
        }
    }

    public static byte[] func_40591_a(NBTTagCompound nbttagcompound)
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));
        try
        {
            writeTo(nbttagcompound, dataoutputstream);
        }
        finally
        {
            dataoutputstream.close();
        }
        return bytearrayoutputstream.toByteArray();
    }

    public static void func_35621_a(NBTTagCompound nbttagcompound, File file)
        throws IOException
    {
        File file1 = new File((new StringBuilder()).append(file.getAbsolutePath()).append("_tmp").toString());
        if(file1.exists())
        {
            file1.delete();
        }
        func_35620_b(nbttagcompound, file1);
        if(file.exists())
        {
            file.delete();
        }
        if(file.exists())
        {
            throw new IOException((new StringBuilder()).append("Failed to delete ").append(file).toString());
        } else
        {
            file1.renameTo(file);
            return;
        }
    }

    public static void func_35620_b(NBTTagCompound nbttagcompound, File file)
        throws IOException
    {
        DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file));
        try
        {
            writeTo(nbttagcompound, dataoutputstream);
        }
        finally
        {
            dataoutputstream.close();
        }
    }

    public static NBTTagCompound func_35622_a(File file)
        throws IOException
    {
        if(!file.exists())
        {
            return null;
        }
        DataInputStream datainputstream = new DataInputStream(new FileInputStream(file));
        try
        {
            NBTTagCompound nbttagcompound = func_1141_a(datainputstream);
            return nbttagcompound;
        }
        finally
        {
            datainputstream.close();
        }
    }

    public static NBTTagCompound func_1141_a(DataInput datainput)
        throws IOException
    {
        NBTBase nbtbase = NBTBase.readTag(datainput);
        if(nbtbase instanceof NBTTagCompound)
        {
            return (NBTTagCompound)nbtbase;
        } else
        {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    public static void writeTo(NBTTagCompound nbttagcompound, DataOutput dataoutput)
        throws IOException
    {
        NBTBase.writeTag(nbttagcompound, dataoutput);
    }
}
