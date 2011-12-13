// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            J_JsonNodeBuilder, J_JsonNodeFactories, J_JsonNode

final class J_JsonNumberNodeBuilder
    implements J_JsonNodeBuilder
{

    private final J_JsonNode field_27239_a;

    J_JsonNumberNodeBuilder(String s)
    {
        field_27239_a = J_JsonNodeFactories.func_27311_b(s);
    }

    public J_JsonNode buildNode()
    {
        return field_27239_a;
    }
}
