// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            J_Functor, J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException

abstract class J_LeafFunctor
    implements J_Functor
{

    J_LeafFunctor()
    {
    }

    public final Object applyTo(Object obj)
    {
        if(!matchsNode(obj))
        {
            throw J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27322_a(this);
        } else
        {
            return typeSafeApplyTo(obj);
        }
    }

    protected abstract Object typeSafeApplyTo(Object obj);
}
