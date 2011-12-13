// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            Session

public class ThreadCheckHasPaid extends Thread
{

    final Minecraft mc; /* synthetic field */

    public ThreadCheckHasPaid(Minecraft minecraft)
    {
        mc = minecraft;
//        super();
    }

    public void run()
    {
        try
        {
            HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL((new StringBuilder()).append("https://login.minecraft.net/session?name=").append(mc.session.username).append("&session=").append(mc.session.sessionId).toString())).openConnection();
            httpurlconnection.connect();
            if(httpurlconnection.getResponseCode() == 400 && this == null)
            {
                Minecraft.hasPaidCheckTime = System.currentTimeMillis();
            }
            httpurlconnection.disconnect();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
