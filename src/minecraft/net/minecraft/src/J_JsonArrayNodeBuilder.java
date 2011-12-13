// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;

// Referenced classes of package net.minecraft.src:
//            J_JsonNodeBuilder, J_JsonNodeFactories, J_JsonRootNode, J_JsonNode

public final class J_JsonArrayNodeBuilder
    implements J_JsonNodeBuilder
{

    private final List elementBuilders = new LinkedList();

    J_JsonArrayNodeBuilder()
    {
    }

    public J_JsonArrayNodeBuilder withElement(J_JsonNodeBuilder j_jsonnodebuilder)
    {
        elementBuilders.add(j_jsonnodebuilder);
        return this;
    }

    public J_JsonRootNode build()
    {
        LinkedList linkedlist = new LinkedList();
        J_JsonNodeBuilder j_jsonnodebuilder;
        for(Iterator iterator = elementBuilders.iterator(); iterator.hasNext(); linkedlist.add(j_jsonnodebuilder.buildNode()))
        {
            j_jsonnodebuilder = (J_JsonNodeBuilder)iterator.next();
        }

        return J_JsonNodeFactories.func_27309_a(linkedlist);
    }

    public J_JsonNode buildNode()
    {
        return build();
    }
}
