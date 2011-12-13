// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;
import java.util.*;

// Referenced classes of package net.minecraft.src:
//            J_JsonFormatter, EnumJsonNodeTypeMappingHelper, J_JsonNode, EnumJsonNodeType, 
//            J_JsonStringNode, J_JsonEscapedString, J_JsonRootNode

public final class J_CompactJsonFormatter
    implements J_JsonFormatter
{

    public J_CompactJsonFormatter()
    {
    }

    public String format(J_JsonRootNode j_jsonrootnode)
    {
        StringWriter stringwriter = new StringWriter();
        try
        {
            format(j_jsonrootnode, stringwriter);
        }
        catch(IOException ioexception)
        {
            throw new RuntimeException("Coding failure in Argo:  StringWriter gave an IOException", ioexception);
        }
        return stringwriter.toString();
    }

    public void format(J_JsonRootNode j_jsonrootnode, Writer writer)
        throws IOException
    {
        formatJsonNode(j_jsonrootnode, writer);
    }

    private void formatJsonNode(J_JsonNode j_jsonnode, Writer writer)
        throws IOException
    {
        boolean flag = true;
        switch(EnumJsonNodeTypeMappingHelper.enumJsonNodeTypeMappingArray[j_jsonnode.getType().ordinal()])
        {
        case 1: // '\001'
            writer.append('[');
            J_JsonNode j_jsonnode1;
            for(Iterator iterator = j_jsonnode.getElements().iterator(); iterator.hasNext(); formatJsonNode(j_jsonnode1, writer))
            {
                j_jsonnode1 = (J_JsonNode)iterator.next();
                if(!flag)
                {
                    writer.append(',');
                }
                flag = false;
            }

            writer.append(']');
            break;

        case 2: // '\002'
            writer.append('{');
            J_JsonStringNode j_jsonstringnode;
            for(Iterator iterator1 = (new TreeSet(j_jsonnode.getFields().keySet())).iterator(); iterator1.hasNext(); formatJsonNode((J_JsonNode)j_jsonnode.getFields().get(j_jsonstringnode), writer))
            {
                j_jsonstringnode = (J_JsonStringNode)iterator1.next();
                if(!flag)
                {
                    writer.append(',');
                }
                flag = false;
                formatJsonNode(((J_JsonNode) (j_jsonstringnode)), writer);
                writer.append(':');
            }

            writer.append('}');
            break;

        case 3: // '\003'
            writer.append('"').append((new J_JsonEscapedString(j_jsonnode.getText())).toString()).append('"');
            break;

        case 4: // '\004'
            writer.append(j_jsonnode.getText());
            break;

        case 5: // '\005'
            writer.append("false");
            break;

        case 6: // '\006'
            writer.append("true");
            break;

        case 7: // '\007'
            writer.append("null");
            break;

        default:
            throw new RuntimeException((new StringBuilder()).append("Coding failure in Argo:  Attempt to format a JsonNode of unknown type [").append(j_jsonnode.getType()).append("];").toString());
        }
    }
}
