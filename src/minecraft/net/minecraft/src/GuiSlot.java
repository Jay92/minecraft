// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GuiButton, Tessellator, RenderEngine

public abstract class GuiSlot
{

    private final Minecraft mc;
    private final int width;
    private final int height;
    protected final int top;
    protected final int bottom;
    private final int right;
    private final int left = 0;
    protected final int slotHeight;
    private int scrollUpButtonID;
    private int scrollDownButtonID;
    protected int field_35409_k;
    protected int field_35408_l;
    private float initialClickY;
    private float scrollMultiplier;
    private float amountScrolled;
    private int selectedElement;
    private long lastClicked;
    private boolean field_25123_p;
    private boolean field_27262_q;
    private int field_27261_r;

    public GuiSlot(Minecraft minecraft, int i, int j, int k, int l, int i1)
    {
        initialClickY = -2F;
        selectedElement = -1;
        lastClicked = 0L;
        field_25123_p = true;
        mc = minecraft;
        width = i;
        height = j;
        top = k;
        bottom = l;
        slotHeight = i1;
        right = i;
    }

    public void func_27258_a(boolean flag)
    {
        field_25123_p = flag;
    }

    protected void func_27259_a(boolean flag, int i)
    {
        field_27262_q = flag;
        field_27261_r = i;
        if(!flag)
        {
            field_27261_r = 0;
        }
    }

    protected abstract int getSize();

    protected abstract void elementClicked(int i, boolean flag);

    protected abstract boolean isSelected(int i);

    protected int getContentHeight()
    {
        return getSize() * slotHeight + field_27261_r;
    }

    protected abstract void drawBackground();

    protected abstract void drawSlot(int i, int j, int k, int l, Tessellator tessellator);

    protected void func_27260_a(int i, int j, Tessellator tessellator)
    {
    }

    protected void func_27255_a(int i, int j)
    {
    }

    protected void func_27257_b(int i, int j)
    {
    }

    public int func_27256_c(int i, int j)
    {
        int k = width / 2 - 110;
        int l = width / 2 + 110;
        int i1 = ((j - top - field_27261_r) + (int)amountScrolled) - 4;
        int j1 = i1 / slotHeight;
        if(i >= k && i <= l && j1 >= 0 && i1 >= 0 && j1 < getSize())
        {
            return j1;
        } else
        {
            return -1;
        }
    }

    public void registerScrollButtons(List list, int i, int j)
    {
        scrollUpButtonID = i;
        scrollDownButtonID = j;
    }

    private void bindAmountScrolled()
    {
        int i = getContentHeight() - (bottom - top - 4);
        if(i < 0)
        {
            i /= 2;
        }
        if(amountScrolled < 0.0F)
        {
            amountScrolled = 0.0F;
        }
        if(amountScrolled > (float)i)
        {
            amountScrolled = i;
        }
    }

    public void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == scrollUpButtonID)
        {
            amountScrolled -= (slotHeight * 2) / 3;
            initialClickY = -2F;
            bindAmountScrolled();
        } else
        if(guibutton.id == scrollDownButtonID)
        {
            amountScrolled += (slotHeight * 2) / 3;
            initialClickY = -2F;
            bindAmountScrolled();
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        field_35409_k = i;
        field_35408_l = j;
        drawBackground();
        int k = getSize();
        int l = width / 2 + 124;
        int i1 = l + 6;
        if(Mouse.isButtonDown(0))
        {
            if(initialClickY == -1F)
            {
                boolean flag = true;
                if(j >= top && j <= bottom)
                {
                    int k1 = width / 2 - 110;
                    int l1 = width / 2 + 110;
                    int j2 = ((j - top - field_27261_r) + (int)amountScrolled) - 4;
                    int l2 = j2 / slotHeight;
                    if(i >= k1 && i <= l1 && l2 >= 0 && j2 >= 0 && l2 < k)
                    {
                        boolean flag1 = l2 == selectedElement && System.currentTimeMillis() - lastClicked < 250L;
                        elementClicked(l2, flag1);
                        selectedElement = l2;
                        lastClicked = System.currentTimeMillis();
                    } else
                    if(i >= k1 && i <= l1 && j2 < 0)
                    {
                        func_27255_a(i - k1, ((j - top) + (int)amountScrolled) - 4);
                        flag = false;
                    }
                    if(i >= l && i <= i1)
                    {
                        scrollMultiplier = -1F;
                        int j3 = getContentHeight() - (bottom - top - 4);
                        if(j3 < 1)
                        {
                            j3 = 1;
                        }
                        int i4 = (int)((float)((bottom - top) * (bottom - top)) / (float)getContentHeight());
                        if(i4 < 32)
                        {
                            i4 = 32;
                        }
                        if(i4 > bottom - top - 8)
                        {
                            i4 = bottom - top - 8;
                        }
                        scrollMultiplier /= (float)(bottom - top - i4) / (float)j3;
                    } else
                    {
                        scrollMultiplier = 1.0F;
                    }
                    if(flag)
                    {
                        initialClickY = j;
                    } else
                    {
                        initialClickY = -2F;
                    }
                } else
                {
                    initialClickY = -2F;
                }
            } else
            if(initialClickY >= 0.0F)
            {
                amountScrolled -= ((float)j - initialClickY) * scrollMultiplier;
                initialClickY = j;
            }
        } else
        {
            do
            {
                if(!Mouse.next())
                {
                    break;
                }
                int j1 = Mouse.getEventDWheel();
                if(j1 != 0)
                {
                    if(j1 > 0)
                    {
                        j1 = -1;
                    } else
                    if(j1 < 0)
                    {
                        j1 = 1;
                    }
                    amountScrolled += (j1 * slotHeight) / 2;
                }
            } while(true);
            initialClickY = -1F;
        }
        bindAmountScrolled();
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(2912 /*GL_FOG*/);
        Tessellator tessellator = Tessellator.instance;
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f1 = 32F;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(0x202020);
        tessellator.addVertexWithUV(left, bottom, 0.0D, (float)left / f1, (float)(bottom + (int)amountScrolled) / f1);
        tessellator.addVertexWithUV(right, bottom, 0.0D, (float)right / f1, (float)(bottom + (int)amountScrolled) / f1);
        tessellator.addVertexWithUV(right, top, 0.0D, (float)right / f1, (float)(top + (int)amountScrolled) / f1);
        tessellator.addVertexWithUV(left, top, 0.0D, (float)left / f1, (float)(top + (int)amountScrolled) / f1);
        tessellator.draw();
        int i2 = width / 2 - 92 - 16;
        int k2 = (top + 4) - (int)amountScrolled;
        if(field_27262_q)
        {
            func_27260_a(i2, k2, tessellator);
        }
        for(int i3 = 0; i3 < k; i3++)
        {
            int k3 = k2 + i3 * slotHeight + field_27261_r;
            int j4 = slotHeight - 4;
            if(k3 > bottom || k3 + j4 < top)
            {
                continue;
            }
            if(field_25123_p && isSelected(i3))
            {
                int l4 = width / 2 - 110;
                int j5 = width / 2 + 110;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(0x808080);
                tessellator.addVertexWithUV(l4, k3 + j4 + 2, 0.0D, 0.0D, 1.0D);
                tessellator.addVertexWithUV(j5, k3 + j4 + 2, 0.0D, 1.0D, 1.0D);
                tessellator.addVertexWithUV(j5, k3 - 2, 0.0D, 1.0D, 0.0D);
                tessellator.addVertexWithUV(l4, k3 - 2, 0.0D, 0.0D, 0.0D);
                tessellator.setColorOpaque_I(0);
                tessellator.addVertexWithUV(l4 + 1, k3 + j4 + 1, 0.0D, 0.0D, 1.0D);
                tessellator.addVertexWithUV(j5 - 1, k3 + j4 + 1, 0.0D, 1.0D, 1.0D);
                tessellator.addVertexWithUV(j5 - 1, k3 - 1, 0.0D, 1.0D, 0.0D);
                tessellator.addVertexWithUV(l4 + 1, k3 - 1, 0.0D, 0.0D, 0.0D);
                tessellator.draw();
                GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
            }
            drawSlot(i3, i2, k3, j4, tessellator);
        }

        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        byte byte0 = 4;
        overlayBackground(0, top, 255, 255);
        overlayBackground(bottom, height, 255, 255);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        GL11.glShadeModel(7425 /*GL_SMOOTH*/);
        GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 0);
        tessellator.addVertexWithUV(left, top + byte0, 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(right, top + byte0, 0.0D, 1.0D, 1.0D);
        tessellator.setColorRGBA_I(0, 255);
        tessellator.addVertexWithUV(right, top, 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(left, top, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0, 255);
        tessellator.addVertexWithUV(left, bottom, 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(right, bottom, 0.0D, 1.0D, 1.0D);
        tessellator.setColorRGBA_I(0, 0);
        tessellator.addVertexWithUV(right, bottom - byte0, 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(left, bottom - byte0, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        int l3 = getContentHeight() - (bottom - top - 4);
        if(l3 > 0)
        {
            int k4 = ((bottom - top) * (bottom - top)) / getContentHeight();
            if(k4 < 32)
            {
                k4 = 32;
            }
            if(k4 > bottom - top - 8)
            {
                k4 = bottom - top - 8;
            }
            int i5 = ((int)amountScrolled * (bottom - top - k4)) / l3 + top;
            if(i5 < top)
            {
                i5 = top;
            }
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(0, 255);
            tessellator.addVertexWithUV(l, bottom, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV(i1, bottom, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV(i1, top, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(l, top, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(0x808080, 255);
            tessellator.addVertexWithUV(l, i5 + k4, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV(i1, i5 + k4, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV(i1, i5, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(l, i5, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_I(0xc0c0c0, 255);
            tessellator.addVertexWithUV(l, (i5 + k4) - 1, 0.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV(i1 - 1, (i5 + k4) - 1, 0.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV(i1 - 1, i5, 0.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(l, i5, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
        }
        func_27257_b(i, j);
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        GL11.glShadeModel(7424 /*GL_FLAT*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glDisable(3042 /*GL_BLEND*/);
    }

    private void overlayBackground(int i, int j, int k, int l)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32F;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0x404040, l);
        tessellator.addVertexWithUV(0.0D, j, 0.0D, 0.0D, (float)j / f);
        tessellator.addVertexWithUV(width, j, 0.0D, (float)width / f, (float)j / f);
        tessellator.setColorRGBA_I(0x404040, k);
        tessellator.addVertexWithUV(width, i, 0.0D, (float)width / f, (float)i / f);
        tessellator.addVertexWithUV(0.0D, i, 0.0D, 0.0D, (float)i / f);
        tessellator.draw();
    }
}
