// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;

// Referenced classes of package net.minecraft.src:
//            J_JsonObjectNodeBuilder, J_JsonFieldBuilder

class J_JsonObjectNodeList extends HashMap
{

    final J_JsonObjectNodeBuilder nodeBuilder; /* synthetic field */

    J_JsonObjectNodeList(J_JsonObjectNodeBuilder j_jsonobjectnodebuilder)
    {
        nodeBuilder = j_jsonobjectnodebuilder;
//        super();
        J_JsonFieldBuilder j_jsonfieldbuilder;
        for(Iterator iterator = J_JsonObjectNodeBuilder.func_27236_a(nodeBuilder).iterator(); iterator.hasNext(); put(j_jsonfieldbuilder.func_27303_b(), j_jsonfieldbuilder.func_27302_c()))
        {
            j_jsonfieldbuilder = (J_JsonFieldBuilder)iterator.next();
        }

    }
}
