// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Random;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GLAllocation, RenderEngine, Tessellator, GameSettings, 
//            ChatAllowedCharacters

public class FontRenderer
{

    private int charWidth[];
    public int fontTextureName;
    public int field_41063_b;
    private int fontDisplayLists;
    private IntBuffer buffer;
    public Random field_41064_c;

    public FontRenderer(GameSettings gamesettings, String s, RenderEngine renderengine)
    {
        charWidth = new int[256];
        fontTextureName = 0;
        field_41063_b = 8;
        buffer = GLAllocation.createDirectIntBuffer(1024 /*GL_FRONT_LEFT*/);
        field_41064_c = new Random();
        BufferedImage bufferedimage;
        try
        {
            bufferedimage = ImageIO.read((net.minecraft.src.RenderEngine.class).getResourceAsStream(s));
        }
        catch(IOException ioexception)
        {
            throw new RuntimeException(ioexception);
        }
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int ai[] = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, ai, 0, i);
        for(int k = 0; k < 256; k++)
        {
            int l = k % 16;
            int k1 = k / 16;
            int j2 = 7;
            do
            {
                if(j2 < 0)
                {
                    break;
                }
                int i3 = l * 8 + j2;
                boolean flag = true;
                for(int l3 = 0; l3 < 8 && flag; l3++)
                {
                    int i4 = (k1 * 8 + l3) * i;
                    int k4 = ai[i3 + i4] & 0xff;
                    if(k4 > 0)
                    {
                        flag = false;
                    }
                }

                if(!flag)
                {
                    break;
                }
                j2--;
            } while(true);
            if(k == 32)
            {
                j2 = 2;
            }
            charWidth[k] = j2 + 2;
        }

        fontTextureName = renderengine.allocateAndSetupTexture(bufferedimage);
        fontDisplayLists = GLAllocation.generateDisplayLists(288);
        Tessellator tessellator = Tessellator.instance;
        for(int i1 = 0; i1 < 256; i1++)
        {
            GL11.glNewList(fontDisplayLists + i1, 4864 /*GL_COMPILE*/);
            tessellator.startDrawingQuads();
            int l1 = (i1 % 16) * 8;
            int k2 = (i1 / 16) * 8;
            float f = 7.99F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            tessellator.addVertexWithUV(0.0D, 0.0F + f, 0.0D, (float)l1 / 128F + f1, ((float)k2 + f) / 128F + f2);
            tessellator.addVertexWithUV(0.0F + f, 0.0F + f, 0.0D, ((float)l1 + f) / 128F + f1, ((float)k2 + f) / 128F + f2);
            tessellator.addVertexWithUV(0.0F + f, 0.0D, 0.0D, ((float)l1 + f) / 128F + f1, (float)k2 / 128F + f2);
            tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (float)l1 / 128F + f1, (float)k2 / 128F + f2);
            tessellator.draw();
            GL11.glTranslatef(charWidth[i1], 0.0F, 0.0F);
            GL11.glEndList();
        }

        for(int j1 = 0; j1 < 32; j1++)
        {
            int i2 = (j1 >> 3 & 1) * 85;
            int l2 = (j1 >> 2 & 1) * 170 + i2;
            int j3 = (j1 >> 1 & 1) * 170 + i2;
            int k3 = (j1 >> 0 & 1) * 170 + i2;
            if(j1 == 6)
            {
                l2 += 85;
            }
            boolean flag1 = j1 >= 16;
            if(gamesettings.anaglyph)
            {
                int j4 = (l2 * 30 + j3 * 59 + k3 * 11) / 100;
                int l4 = (l2 * 30 + j3 * 70) / 100;
                int i5 = (l2 * 30 + k3 * 70) / 100;
                l2 = j4;
                j3 = l4;
                k3 = i5;
            }
            if(flag1)
            {
                l2 /= 4;
                j3 /= 4;
                k3 /= 4;
            }
            GL11.glNewList(fontDisplayLists + 256 + j1, 4864 /*GL_COMPILE*/);
            GL11.glColor3f((float)l2 / 255F, (float)j3 / 255F, (float)k3 / 255F);
            GL11.glEndList();
        }

    }

    public void drawStringWithShadow(String s, int i, int j, int k)
    {
        renderString(s, i + 1, j + 1, k, true);
        drawString(s, i, j, k);
    }

    public void drawString(String s, int i, int j, int k)
    {
        renderString(s, i, j, k, false);
    }

    public void renderString(String s, int i, int j, int k, boolean flag)
    {
        if(s == null)
        {
            return;
        }
        boolean flag1 = false;
        if(flag)
        {
            int l = k & 0xff000000;
            k = (k & 0xfcfcfc) >> 2;
            k += l;
        }
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, fontTextureName);
        float f = (float)(k >> 16 & 0xff) / 255F;
        float f1 = (float)(k >> 8 & 0xff) / 255F;
        float f2 = (float)(k & 0xff) / 255F;
        float f3 = (float)(k >> 24 & 0xff) / 255F;
        if(f3 == 0.0F)
        {
            f3 = 1.0F;
        }
        GL11.glColor4f(f, f1, f2, f3);
        buffer.clear();
        GL11.glPushMatrix();
        GL11.glTranslatef(i, j, 0.0F);
        for(int i1 = 0; i1 < s.length(); i1++)
        {
            for(; s.length() > i1 + 1 && s.charAt(i1) == '\247'; i1 += 2)
            {
                char c = s.toLowerCase().charAt(i1 + 1);
                if(c == 'k')
                {
                    flag1 = true;
                    continue;
                }
                flag1 = false;
                int k1 = "0123456789abcdef".indexOf(c);
                if(k1 < 0 || k1 > 15)
                {
                    k1 = 15;
                }
                buffer.put(fontDisplayLists + 256 + k1 + (flag ? 16 : 0));
                if(buffer.remaining() == 0)
                {
                    buffer.flip();
                    GL11.glCallLists(buffer);
                    buffer.clear();
                }
            }

            if(i1 < s.length())
            {
                int j1 = ChatAllowedCharacters.allowedCharacters.indexOf(s.charAt(i1));
                if(j1 >= 0)
                {
                    if(flag1)
                    {
                        int l1 = 0;
                        do
                        {
                            l1 = field_41064_c.nextInt(ChatAllowedCharacters.allowedCharacters.length());
                        } while(charWidth[j1 + 32] != charWidth[l1 + 32]);
                        buffer.put(fontDisplayLists + 256 + field_41064_c.nextInt(2) + 8 + (flag ? 16 : 0));
                        buffer.put(fontDisplayLists + l1 + 32);
                    } else
                    {
                        buffer.put(fontDisplayLists + j1 + 32);
                    }
                }
            }
            if(buffer.remaining() == 0)
            {
                buffer.flip();
                GL11.glCallLists(buffer);
                buffer.clear();
            }
        }

        buffer.flip();
        GL11.glCallLists(buffer);
        GL11.glPopMatrix();
    }

    public int getStringWidth(String s)
    {
        if(s == null)
        {
            return 0;
        }
        int i = 0;
        for(int j = 0; j < s.length(); j++)
        {
            if(s.charAt(j) == '\247')
            {
                j++;
                continue;
            }
            int k = ChatAllowedCharacters.allowedCharacters.indexOf(s.charAt(j));
            if(k >= 0)
            {
                i += charWidth[k + 32];
            }
        }

        return i;
    }

    public void drawSplitString(String s, int i, int j, int k, int l)
    {
        func_40609_a(s, i, j, k, l, false);
    }

    public void func_40609_a(String s, int i, int j, int k, int l, boolean flag)
    {
        String as[] = s.split("\n");
        if(as.length > 1)
        {
            for(int i1 = 0; i1 < as.length; i1++)
            {
                drawSplitString(as[i1], i, j, k, l);
                j += splitStringWidth(as[i1], k);
            }

            return;
        }
        String as1[] = s.split(" ");
        int j1 = 0;
        String s1 = "";
        do
        {
            if(j1 >= as1.length)
            {
                break;
            }
            String s2;
            for(s2 = (new StringBuilder()).append(s1).append(as1[j1++]).append(" ").toString(); j1 < as1.length && getStringWidth((new StringBuilder()).append(s2).append(as1[j1]).toString()) < k; s2 = (new StringBuilder()).append(s2).append(as1[j1++]).append(" ").toString()) { }
            int k1;
            for(; getStringWidth(s2) > k; s2 = (new StringBuilder()).append(s1).append(s2.substring(k1)).toString())
            {
                for(k1 = 0; getStringWidth(s2.substring(0, k1 + 1)) <= k; k1++) { }
                if(s2.substring(0, k1).trim().length() <= 0)
                {
                    continue;
                }
                String s3 = s2.substring(0, k1);
                if(s3.lastIndexOf("\247") >= 0)
                {
                    s1 = (new StringBuilder()).append("\247").append(s3.charAt(s3.lastIndexOf("\247") + 1)).toString();
                }
                renderString(s3, i, j, l, flag);
                j += field_41063_b;
            }

            if(getStringWidth(s2.trim()) > 0)
            {
                if(s2.lastIndexOf("\247") >= 0)
                {
                    s1 = (new StringBuilder()).append("\247").append(s2.charAt(s2.lastIndexOf("\247") + 1)).toString();
                }
                renderString(s2, i, j, l, flag);
                j += field_41063_b;
            }
        } while(true);
    }

    public int splitStringWidth(String s, int i)
    {
        String as[] = s.split("\n");
        if(as.length > 1)
        {
            int j = 0;
            for(int k = 0; k < as.length; k++)
            {
                j += splitStringWidth(as[k], i);
            }

            return j;
        }
        String as1[] = s.split(" ");
        int l = 0;
        int i1 = 0;
        do
        {
            if(l >= as1.length)
            {
                break;
            }
            String s1;
            for(s1 = (new StringBuilder()).append(as1[l++]).append(" ").toString(); l < as1.length && getStringWidth((new StringBuilder()).append(s1).append(as1[l]).toString()) < i; s1 = (new StringBuilder()).append(s1).append(as1[l++]).append(" ").toString()) { }
            int j1;
            for(; getStringWidth(s1) > i; s1 = s1.substring(j1))
            {
                for(j1 = 0; getStringWidth(s1.substring(0, j1 + 1)) <= i; j1++) { }
                if(s1.substring(0, j1).trim().length() > 0)
                {
                    i1 += field_41063_b;
                }
            }

            if(s1.trim().length() > 0)
            {
                i1 += field_41063_b;
            }
        } while(true);
        if(i1 < field_41063_b)
        {
            i1 += field_41063_b;
        }
        return i1;
    }
}
