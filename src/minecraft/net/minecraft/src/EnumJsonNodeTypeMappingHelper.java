// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            EnumJsonNodeType

class EnumJsonNodeTypeMappingHelper
{

    static final int enumJsonNodeTypeMappingArray[]; /* synthetic field */

    static 
    {
        enumJsonNodeTypeMappingArray = new int[EnumJsonNodeType.values().length];
        try
        {
            enumJsonNodeTypeMappingArray[EnumJsonNodeType.ARRAY.ordinal()] = 1;
        }
        catch(NoSuchFieldError nosuchfielderror) { }
        try
        {
            enumJsonNodeTypeMappingArray[EnumJsonNodeType.OBJECT.ordinal()] = 2;
        }
        catch(NoSuchFieldError nosuchfielderror1) { }
        try
        {
            enumJsonNodeTypeMappingArray[EnumJsonNodeType.STRING.ordinal()] = 3;
        }
        catch(NoSuchFieldError nosuchfielderror2) { }
        try
        {
            enumJsonNodeTypeMappingArray[EnumJsonNodeType.NUMBER.ordinal()] = 4;
        }
        catch(NoSuchFieldError nosuchfielderror3) { }
        try
        {
            enumJsonNodeTypeMappingArray[EnumJsonNodeType.FALSE.ordinal()] = 5;
        }
        catch(NoSuchFieldError nosuchfielderror4) { }
        try
        {
            enumJsonNodeTypeMappingArray[EnumJsonNodeType.TRUE.ordinal()] = 6;
        }
        catch(NoSuchFieldError nosuchfielderror5) { }
        try
        {
            enumJsonNodeTypeMappingArray[EnumJsonNodeType.NULL.ordinal()] = 7;
        }
        catch(NoSuchFieldError nosuchfielderror6) { }
    }
}
