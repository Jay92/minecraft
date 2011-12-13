// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package net.minecraft.src:
//            J_JsonNode

class J_JsonNodeList extends ArrayList
{

    final Iterable field_27405_a; /* synthetic field */

    J_JsonNodeList(Iterable iterable)
    {
        field_27405_a = iterable;
//        super();
        J_JsonNode j_jsonnode;
        for(Iterator iterator = field_27405_a.iterator(); iterator.hasNext(); add(j_jsonnode))
        {
            j_jsonnode = (J_JsonNode)iterator.next();
        }

    }
}
