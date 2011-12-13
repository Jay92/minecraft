// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


public final class ProfilerResult
    implements Comparable
{

    public double field_40704_a;
    public double field_40702_b;
    public String field_40703_c;

    public ProfilerResult(String s, double d, double d1)
    {
        field_40703_c = s;
        field_40704_a = d;
        field_40702_b = d1;
    }

    public int func_40701_a(ProfilerResult profilerresult)
    {
        if(profilerresult.field_40704_a < field_40704_a)
        {
            return -1;
        }
        if(profilerresult.field_40704_a > field_40704_a)
        {
            return 1;
        } else
        {
            return profilerresult.field_40703_c.compareTo(field_40703_c);
        }
    }

    public int func_40700_a()
    {
        return (field_40703_c.hashCode() & 0xaaaaaa) + 0x444444;
    }

    public int compareTo(Object obj)
    {
        return func_40701_a((ProfilerResult)obj);
    }
}
