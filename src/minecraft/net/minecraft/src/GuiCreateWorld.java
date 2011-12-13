// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, StatCollector, GuiTextField, StringTranslate, 
//            GuiButton, ChatAllowedCharacters, MathHelper, ISaveFormat, 
//            PlayerControllerCreative, PlayerControllerSP, WorldSettings

public class GuiCreateWorld extends GuiScreen
{

    private GuiScreen parentGuiScreen;
    private GuiTextField textboxWorldName;
    private GuiTextField textboxSeed;
    private String folderName;
    private String field_35364_f;
    private boolean field_35365_g;
    private boolean field_40232_h;
    private boolean createClicked;
    private boolean field_35368_i;
    private GuiButton field_35366_j;
    private GuiButton field_35367_k;
    private GuiButton field_35372_s;
    private GuiButton field_35371_t;
    private String field_35370_u;
    private String field_35369_v;
    private String field_41048_x;
    private String field_41047_y;

    public GuiCreateWorld(GuiScreen guiscreen)
    {
        field_35364_f = "survival";
        field_35365_g = true;
        field_40232_h = false;
        parentGuiScreen = guiscreen;
        field_41048_x = "";
        field_41047_y = StatCollector.translateToLocal("selectWorld.newWorld");
    }

    public void updateScreen()
    {
        textboxWorldName.updateCursorCounter();
        textboxSeed.updateCursorCounter();
    }

    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, stringtranslate.translateKey("selectWorld.create")));
        controlList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, stringtranslate.translateKey("gui.cancel")));
        controlList.add(field_35366_j = new GuiButton(2, width / 2 - 75, 100, 150, 20, stringtranslate.translateKey("selectWorld.gameMode")));
        controlList.add(field_35367_k = new GuiButton(3, width / 2 - 75, 172, 150, 20, stringtranslate.translateKey("selectWorld.moreWorldOptions")));
        controlList.add(field_35372_s = new GuiButton(4, width / 2 - 155, 100, 150, 20, stringtranslate.translateKey("selectWorld.mapFeatures")));
        field_35372_s.drawButton = false;
        controlList.add(field_35371_t = new GuiButton(5, width / 2 + 5, 100, 150, 20, stringtranslate.translateKey("selectWorld.mapType")));
        field_35371_t.drawButton = false;
        field_35371_t.enabled = false;
        textboxWorldName = new GuiTextField(this, fontRenderer, width / 2 - 100, 60, 200, 20, field_41047_y);
        textboxWorldName.isFocused = true;
        textboxWorldName.setMaxStringLength(32);
        textboxSeed = new GuiTextField(this, fontRenderer, width / 2 - 100, 60, 200, 20, field_41048_x);
        makeUseableName();
        func_35363_g();
    }

    private void makeUseableName()
    {
        folderName = textboxWorldName.getText().trim();
        char ac[] = ChatAllowedCharacters.allowedCharactersArray;
        int i = ac.length;
        for(int j = 0; j < i; j++)
        {
            char c = ac[j];
            folderName = folderName.replace(c, '_');
        }

        if(MathHelper.stringNullOrLengthZero(folderName))
        {
            folderName = "World";
        }
        folderName = generateUnusedFolderName(mc.getSaveLoader(), folderName);
    }

    private void func_35363_g()
    {
        StringTranslate stringtranslate;
        stringtranslate = StringTranslate.getInstance();
        field_35366_j.displayString = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.gameMode")).append(" ").append(stringtranslate.translateKey((new StringBuilder()).append("selectWorld.gameMode.").append(field_35364_f).toString())).toString();
        field_35370_u = stringtranslate.translateKey((new StringBuilder()).append("selectWorld.gameMode.").append(field_35364_f).append(".line1").toString());
        field_35369_v = stringtranslate.translateKey((new StringBuilder()).append("selectWorld.gameMode.").append(field_35364_f).append(".line2").toString());
        field_35372_s.displayString = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.mapFeatures")).append(" ").toString();
        if(field_35365_g)
        {
            field_35372_s.displayString += stringtranslate.translateKey("options.on");
        }
        else
        {
            field_35372_s.displayString += stringtranslate.translateKey("options.off");
        }
        field_35371_t.displayString = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.mapType")).append(" ").append(stringtranslate.translateKey("selectWorld.mapType.normal")).toString();
        return;
    }

    public static String generateUnusedFolderName(ISaveFormat isaveformat, String s)
    {
        for(; isaveformat.getWorldInfo(s) != null; s = (new StringBuilder()).append(s).append("-").toString()) { }
        return s;
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 1)
        {
            mc.displayGuiScreen(parentGuiScreen);
        } else
        if(guibutton.id == 0)
        {
            mc.displayGuiScreen(null);
            if(createClicked)
            {
                return;
            }
            createClicked = true;
            long l = (new Random()).nextLong();
            String s = textboxSeed.getText();
            if(!MathHelper.stringNullOrLengthZero(s))
            {
                try
                {
                    long l1 = Long.parseLong(s);
                    if(l1 != 0L)
                    {
                        l = l1;
                    }
                }
                catch(NumberFormatException numberformatexception)
                {
                    l = s.hashCode();
                }
            }
            int i = 0;
            if(field_35364_f.equals("creative"))
            {
                i = 1;
                mc.playerController = new PlayerControllerCreative(mc);
            } else
            {
                mc.playerController = new PlayerControllerSP(mc);
            }
            mc.startWorld(folderName, textboxWorldName.getText(), new WorldSettings(l, i, field_35365_g, field_40232_h));
            mc.displayGuiScreen(null);
        } else
        if(guibutton.id == 3)
        {
            field_35368_i = !field_35368_i;
            field_35366_j.drawButton = !field_35368_i;
            field_35372_s.drawButton = field_35368_i;
            field_35371_t.drawButton = field_35368_i;
            if(field_35368_i)
            {
                StringTranslate stringtranslate = StringTranslate.getInstance();
                field_35367_k.displayString = stringtranslate.translateKey("gui.done");
            } else
            {
                StringTranslate stringtranslate1 = StringTranslate.getInstance();
                field_35367_k.displayString = stringtranslate1.translateKey("selectWorld.moreWorldOptions");
            }
        } else
        if(guibutton.id == 2)
        {
            if(field_35364_f.equals("survival"))
            {
                field_40232_h = false;
                field_35364_f = "hardcore";
                field_40232_h = true;
                func_35363_g();
            } else
            if(field_35364_f.equals("hardcore"))
            {
                field_40232_h = false;
                field_35364_f = "creative";
                func_35363_g();
                field_40232_h = false;
            } else
            {
                field_35364_f = "survival";
                func_35363_g();
                field_40232_h = false;
            }
            func_35363_g();
        } else
        if(guibutton.id == 4)
        {
            field_35365_g = !field_35365_g;
            func_35363_g();
        }
    }

    protected void keyTyped(char c, int i)
    {
        if(textboxWorldName.isFocused && !field_35368_i)
        {
            textboxWorldName.textboxKeyTyped(c, i);
            field_41047_y = textboxWorldName.getText();
        } else
        if(textboxSeed.isFocused && field_35368_i)
        {
            textboxSeed.textboxKeyTyped(c, i);
            field_41048_x = textboxSeed.getText();
        }
        if(c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }
        ((GuiButton)controlList.get(0)).enabled = textboxWorldName.getText().length() > 0;
        makeUseableName();
    }

    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        if(!field_35368_i)
        {
            textboxWorldName.mouseClicked(i, j, k);
        } else
        {
            textboxSeed.mouseClicked(i, j, k);
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("selectWorld.create"), width / 2, 20, 0xffffff);
        if(!field_35368_i)
        {
            drawString(fontRenderer, stringtranslate.translateKey("selectWorld.enterName"), width / 2 - 100, 47, 0xa0a0a0);
            drawString(fontRenderer, (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.resultFolder")).append(" ").append(folderName).toString(), width / 2 - 100, 85, 0xa0a0a0);
            textboxWorldName.drawTextBox();
            drawString(fontRenderer, field_35370_u, width / 2 - 100, 122, 0xa0a0a0);
            drawString(fontRenderer, field_35369_v, width / 2 - 100, 134, 0xa0a0a0);
        } else
        {
            drawString(fontRenderer, stringtranslate.translateKey("selectWorld.enterSeed"), width / 2 - 100, 47, 0xa0a0a0);
            drawString(fontRenderer, stringtranslate.translateKey("selectWorld.seedInfo"), width / 2 - 100, 85, 0xa0a0a0);
            drawString(fontRenderer, stringtranslate.translateKey("selectWorld.mapFeatures.info"), width / 2 - 150, 122, 0xa0a0a0);
            textboxSeed.drawTextBox();
        }
        super.drawScreen(i, j, f);
    }

    public void selectNextField()
    {
        if(textboxWorldName.isFocused)
        {
            textboxWorldName.setFocused(false);
            textboxSeed.setFocused(true);
        } else
        {
            textboxWorldName.setFocused(true);
            textboxSeed.setFocused(false);
        }
    }
}
