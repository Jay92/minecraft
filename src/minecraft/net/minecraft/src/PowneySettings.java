// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.io.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            KeyBinding, StringTranslate, StatCollector, EnumOptions, 
//            SoundManager, RenderGlobal, RenderEngine, EnumOptionsMappingHelper

public class PowneySettings
{


    public KeyBinding keyBindKnockback;
    public KeyBinding keyBindStepHack;
    public KeyBinding keyBindConfig;
    public KeyBinding keyBindings[];
    protected Minecraft mc;
    private File optionsFile;

    public PowneySettings(Minecraft minecraft, File file)
    {

        keyBindKnockback = new KeyBinding("key.knockback", Keyboard.KEY_K);
        keyBindStepHack = new KeyBinding("key.stephack", Keyboard.KEY_P);
        keyBindConfig = new KeyBinding("key.config", Keyboard.KEY_Y);
        keyBindings = (new KeyBinding[] {
            keyBindKnockback, keyBindStepHack,  keyBindConfig
        });
        mc = minecraft;
        optionsFile = new File(file, "powneyoptions.txt");
        loadOptions();
    }

    public PowneySettings()
    {

        keyBindKnockback = new KeyBinding("key.knockback", Keyboard.KEY_K);
        keyBindStepHack = new KeyBinding("key.stephack", Keyboard.KEY_P);
        keyBindConfig = new KeyBinding("key.config", Keyboard.KEY_Y);
        keyBindings = (new KeyBinding[] {
            keyBindKnockback, keyBindStepHack, keyBindConfig
        });
    }

    public String getKeyBindingDescription(int i)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        return stringtranslate.translateKey(keyBindings[i].keyDescription);
    }

    public String getOptionDisplayString(int i)
    {
        int j = keyBindings[i].keyCode;
        return func_41085_c(j);
    }

    public static String func_41085_c(int i)
    {
        if(i < 0)
        {
            return StatCollector.translateToLocalFormatted("key.mouseButton", new Object[] {
                Integer.valueOf(i + 101)
            });
        } else
        {
            return Keyboard.getKeyName(i);
        }
    }

    public void setKeyBinding(int i, int j)
    {
        keyBindings[i].keyCode = j;
        saveOptions();
    }

    public void setOptionFloatValue(EnumOptions enumoptions, float f)
    {

    }

    public void setOptionValue(EnumOptions enumoptions, int i)
    {
        saveOptions();
    }

    public float getOptionFloatValue(EnumOptions enumoptions)
    {
        
            return 0.0F;
        
    }

    public boolean getOptionOrdinalValue(EnumOptions enumoptions)
    {

        return false;
    }

    public String getKeyBinding(EnumOptions enumoptions)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        String s = (new StringBuilder()).append(stringtranslate.translateKey(enumoptions.getEnumString())).append(": ").toString();
        if(enumoptions.getEnumFloat())
        {
            float f = getOptionFloatValue(enumoptions);
            
        }
        if(enumoptions.getEnumBoolean())
        {
            boolean flag = getOptionOrdinalValue(enumoptions);
            if(flag)
            {
                return (new StringBuilder()).append(s).append(stringtranslate.translateKey("options.on")).toString();
            } else
            {
                return (new StringBuilder()).append(s).append(stringtranslate.translateKey("options.off")).toString();
            }
        }
         else
        {
            return s;
        }
    }

    public void loadOptions()
    {
        try
        {
            if(!optionsFile.exists())
            {
                return;
            }
            BufferedReader bufferedreader = new BufferedReader(new FileReader(optionsFile));
            for(String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                try
                {
                    String as[] = s.split(":");

                    int i = 0;
                    while(i < keyBindings.length) 
                    {
                        if(as[0].equals((new StringBuilder()).append("key_").append(keyBindings[i].keyDescription).toString()))
                        {
                            keyBindings[i].keyCode = Integer.parseInt(as[1]);
                        }
                        i++;
                    }
                }
                catch(Exception exception1)
                {
                    System.out.println((new StringBuilder()).append("Skipping bad option: ").append(s).toString());
                }
            }

            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedreader.close();
        }
        catch(Exception exception)
        {
            System.out.println("Failed to load options");
            exception.printStackTrace();
        }
    }

    private float parseFloat(String s)
    {
        if(s.equals("true"))
        {
            return 1.0F;
        }
        if(s.equals("false"))
        {
            return 0.0F;
        } else
        {
            return Float.parseFloat(s);
        }
    }

    public void saveOptions()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(optionsFile));
            for(int i = 0; i < keyBindings.length; i++)
            {
                printwriter.println((new StringBuilder()).append("key_").append(keyBindings[i].keyDescription).append(":").append(keyBindings[i].keyCode).toString());
            }

            printwriter.close();
        }
        catch(Exception exception)
        {
            System.out.println("Failed to save options");
            exception.printStackTrace();
        }
    }


}
