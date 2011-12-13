// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


interface J_Functor
{

    public abstract boolean matchsNode(Object obj);

    public abstract Object applyTo(Object obj);

    public abstract String shortForm();
}
