// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;

// Referenced classes of package net.minecraft.src:
//            J_LeafFunctor, EnumJsonNodeType, J_JsonNode

class J_JsonArrayNodeSelector extends J_LeafFunctor
{

    J_JsonArrayNodeSelector()
    {
    }

    public boolean matchsNode_(J_JsonNode j_jsonnode)
    {
        return EnumJsonNodeType.ARRAY == j_jsonnode.getType();
    }

    public String shortForm()
    {
        return "A short form array";
    }

    public List typeSafeApplyTo(J_JsonNode j_jsonnode)
    {
        return j_jsonnode.getElements();
    }

    public String toString()
    {
        return "an array";
    }

    public Object typeSafeApplyTo(Object obj)
    {
        return typeSafeApplyTo((J_JsonNode)obj);
    }

    public boolean matchsNode(Object obj)
    {
        return matchsNode_((J_JsonNode)obj);
    }
}
