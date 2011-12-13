// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Map;

// Referenced classes of package net.minecraft.src:
//            J_LeafFunctor, EnumJsonNodeType, J_JsonNode

class J_JsonObjectNodeSelector extends J_LeafFunctor
{

    J_JsonObjectNodeSelector()
    {
    }

    public boolean func_27070_a(J_JsonNode j_jsonnode)
    {
        return EnumJsonNodeType.OBJECT == j_jsonnode.getType();
    }

    public String shortForm()
    {
        return "A short form object";
    }

    public Map func_27071_b(J_JsonNode j_jsonnode)
    {
        return j_jsonnode.getFields();
    }

    public String toString()
    {
        return "an object";
    }

    public Object typeSafeApplyTo(Object obj)
    {
        return func_27071_b((J_JsonNode)obj);
    }

    public boolean matchsNode(Object obj)
    {
        return func_27070_a((J_JsonNode)obj);
    }
}
