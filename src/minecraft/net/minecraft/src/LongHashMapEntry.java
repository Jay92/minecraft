// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            LongHashMap

class LongHashMapEntry
{

    final long field_35834_a;
    Object field_35832_b;
    LongHashMapEntry field_35833_c;
    final int field_35831_d;

    LongHashMapEntry(int i, long l, Object obj, LongHashMapEntry longhashmapentry)
    {
        field_35832_b = obj;
        field_35833_c = longhashmapentry;
        field_35834_a = l;
        field_35831_d = i;
    }

    public final long func_35830_a()
    {
        return field_35834_a;
    }

    public final Object func_35829_b()
    {
        return field_35832_b;
    }

    public final boolean equals(Object obj)
    {
        if(!(obj instanceof LongHashMapEntry))
        {
            return false;
        }
        LongHashMapEntry longhashmapentry = (LongHashMapEntry)obj;
        Long long1 = Long.valueOf(func_35830_a());
        Long long2 = Long.valueOf(longhashmapentry.func_35830_a());
        if(long1 == long2 || long1 != null && long1.equals(long2))
        {
            Object obj1 = func_35829_b();
            Object obj2 = longhashmapentry.func_35829_b();
            if(obj1 == obj2 || obj1 != null && obj1.equals(obj2))
            {
                return true;
            }
        }
        return false;
    }

    public final int hashCode()
    {
        return LongHashMap.getHashCode(field_35834_a);
    }

    public final String toString()
    {
        return (new StringBuilder()).append(func_35830_a()).append("=").append(func_35829_b()).toString();
    }
}
