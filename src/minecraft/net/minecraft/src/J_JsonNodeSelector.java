// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            J_Functor, J_ChainedFunctor

public final class J_JsonNodeSelector
{

    final J_Functor valueGetter;

    J_JsonNodeSelector(J_Functor j_functor)
    {
        valueGetter = j_functor;
    }

    public boolean matchs(Object obj)
    {
        return valueGetter.matchsNode(obj);
    }

    public Object getValue(Object obj)
    {
        return valueGetter.applyTo(obj);
    }

    public J_JsonNodeSelector with(J_JsonNodeSelector j_jsonnodeselector)
    {
        return new J_JsonNodeSelector(new J_ChainedFunctor(this, j_jsonnodeselector));
    }

    String shortForm()
    {
        return valueGetter.shortForm();
    }

    public String toString()
    {
        return valueGetter.toString();
    }
}
