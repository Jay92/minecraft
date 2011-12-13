// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            J_ThingWithPosition

public final class J_InvalidSyntaxException extends Exception
{

    private final int column;
    private final int row;

    J_InvalidSyntaxException(String s, J_ThingWithPosition j_thingwithposition)
    {
        super((new StringBuilder()).append("At line ").append(j_thingwithposition.getRow()).append(", column ").append(j_thingwithposition.getColumn()).append(":  ").append(s).toString());
        column = j_thingwithposition.getColumn();
        row = j_thingwithposition.getRow();
    }

    J_InvalidSyntaxException(String s, Throwable throwable, J_ThingWithPosition j_thingwithposition)
    {
        super((new StringBuilder()).append("At line ").append(j_thingwithposition.getRow()).append(", column ").append(j_thingwithposition.getColumn()).append(":  ").append(s).toString(), throwable);
        column = j_thingwithposition.getColumn();
        row = j_thingwithposition.getRow();
    }
}
