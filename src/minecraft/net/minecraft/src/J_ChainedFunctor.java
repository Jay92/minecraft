// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            J_Functor, J_JsonNodeSelector, J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException

final class J_ChainedFunctor
    implements J_Functor
{

    private final J_JsonNodeSelector parentJsonNodeSelector;
    private final J_JsonNodeSelector childJsonNodeSelector;

    J_ChainedFunctor(J_JsonNodeSelector j_jsonnodeselector, J_JsonNodeSelector j_jsonnodeselector1)
    {
        parentJsonNodeSelector = j_jsonnodeselector;
        childJsonNodeSelector = j_jsonnodeselector1;
    }

    public boolean matchsNode(Object obj)
    {
        return parentJsonNodeSelector.matchs(obj) && childJsonNodeSelector.matchs(parentJsonNodeSelector.getValue(obj));
    }

    public Object applyTo(Object obj)
    {
        Object obj1;
        try
        {
            obj1 = parentJsonNodeSelector.getValue(obj);
        }
        catch(J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_jsonnodedoesnotmatchchainedjsonnodeselectorexception)
        {
            throw J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27321_b(j_jsonnodedoesnotmatchchainedjsonnodeselectorexception, parentJsonNodeSelector);
        }
        Object obj2;
        try
        {
            obj2 = childJsonNodeSelector.getValue(obj1);
        }
        catch(J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_jsonnodedoesnotmatchchainedjsonnodeselectorexception1)
        {
            throw J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27323_a(j_jsonnodedoesnotmatchchainedjsonnodeselectorexception1, parentJsonNodeSelector);
        }
        return obj2;
    }

    public String shortForm()
    {
        return childJsonNodeSelector.shortForm();
    }

    public String toString()
    {
        return (new StringBuilder()).append(parentJsonNodeSelector.toString()).append(", with ").append(childJsonNodeSelector.toString()).toString();
    }
}
