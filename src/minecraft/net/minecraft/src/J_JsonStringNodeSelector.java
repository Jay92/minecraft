// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            J_LeafFunctor, EnumJsonNodeType, J_JsonNode

class J_JsonStringNodeSelector extends J_LeafFunctor
{

    J_JsonStringNodeSelector()
    {
    }

    public boolean func_27072_a(J_JsonNode j_jsonnode)
    {
        return EnumJsonNodeType.STRING == j_jsonnode.getType();
    }

    public String shortForm()
    {
        return "A short form string";
    }

    public String func_27073_b(J_JsonNode j_jsonnode)
    {
        return j_jsonnode.getText();
    }

    public String toString()
    {
        return "a value that is a string";
    }

    public Object typeSafeApplyTo(Object obj)
    {
        return func_27073_b((J_JsonNode)obj);
    }

    public boolean matchsNode(Object obj)
    {
        return func_27072_a((J_JsonNode)obj);
    }
}
