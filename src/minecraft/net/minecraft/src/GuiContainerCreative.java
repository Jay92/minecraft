// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            GuiContainer, ContainerCreative, EntityPlayer, AchievementList, 
//            PlayerController, GuiInventory, Slot, EntityPlayerSP, 
//            InventoryPlayer, ItemStack, Container, FontRenderer, 
//            RenderEngine, GuiButton, GuiAchievements, GuiStats, 
//            InventoryBasic

public class GuiContainerCreative extends GuiContainer
{

    private static InventoryBasic inventory = new InventoryBasic("tmp", 72);
    private float field_35312_g;
    private boolean field_35313_h;
    private boolean field_35314_i;

    public GuiContainerCreative(EntityPlayer entityplayer)
    {
        super(new ContainerCreative(entityplayer));
        field_35312_g = 0.0F;
        field_35313_h = false;
        entityplayer.craftingInventory = inventorySlots;
        allowUserInput = true;
        entityplayer.addStat(AchievementList.openInventory, 1);
        ySize = 208;
    }

    public void updateScreen()
    {
        if(!mc.playerController.isInCreativeMode())
        {
            mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
        }
    }

    protected void func_35309_a(Slot slot, int i, int j, boolean flag)
    {
        if(slot != null)
        {
            if(slot.inventory == inventory)
            {
                InventoryPlayer inventoryplayer = mc.thePlayer.inventory;
                ItemStack itemstack1 = inventoryplayer.getItemStack();
                ItemStack itemstack4 = slot.getStack();
                if(itemstack1 != null && itemstack4 != null && itemstack1.itemID == itemstack4.itemID)
                {
                    if(j == 0)
                    {
                        if(flag)
                        {
                            itemstack1.stackSize = itemstack1.getMaxStackSize();
                        } else
                        if(itemstack1.stackSize < itemstack1.getMaxStackSize())
                        {
                            itemstack1.stackSize++;
                        }
                    } else
                    if(itemstack1.stackSize <= 1)
                    {
                        inventoryplayer.setItemStack(null);
                    } else
                    {
                        itemstack1.stackSize--;
                    }
                } else
                if(itemstack1 != null)
                {
                    inventoryplayer.setItemStack(null);
                } else
                if(itemstack4 == null)
                {
                    inventoryplayer.setItemStack(null);
                } else
                if(itemstack1 == null || itemstack1.itemID != itemstack4.itemID)
                {
                    inventoryplayer.setItemStack(ItemStack.copyItemStack(itemstack4));
                    ItemStack itemstack2 = inventoryplayer.getItemStack();
                    if(flag)
                    {
                        itemstack2.stackSize = itemstack2.getMaxStackSize();
                    }
                }
            } else
            {
                inventorySlots.slotClick(slot.slotNumber, j, flag, mc.thePlayer);
                ItemStack itemstack = inventorySlots.getSlot(slot.slotNumber).getStack();
                mc.playerController.func_35637_a(itemstack, (slot.slotNumber - inventorySlots.inventorySlots.size()) + 9 + 36);
            }
        } else
        {
            InventoryPlayer inventoryplayer1 = mc.thePlayer.inventory;
            if(inventoryplayer1.getItemStack() != null)
            {
                if(j == 0)
                {
                    mc.thePlayer.dropPlayerItem(inventoryplayer1.getItemStack());
                    mc.playerController.func_35639_a(inventoryplayer1.getItemStack());
                    inventoryplayer1.setItemStack(null);
                }
                if(j == 1)
                {
                    ItemStack itemstack3 = inventoryplayer1.getItemStack().splitStack(1);
                    mc.thePlayer.dropPlayerItem(itemstack3);
                    mc.playerController.func_35639_a(itemstack3);
                    if(inventoryplayer1.getItemStack().stackSize == 0)
                    {
                        inventoryplayer1.setItemStack(null);
                    }
                }
            }
        }
    }

    public void initGui()
    {
        if(!mc.playerController.isInCreativeMode())
        {
            mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
        } else
        {
            super.initGui();
            controlList.clear();
        }
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Item selection", 8, 6, 0x404040);
    }

    public void handleMouseInput()
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if(i != 0)
        {
            int j = (((ContainerCreative)inventorySlots).itemList.size() / 8 - 8) + 1;
            if(i > 0)
            {
                i = 1;
            }
            if(i < 0)
            {
                i = -1;
            }
            field_35312_g -= (double)i / (double)j;
            if(field_35312_g < 0.0F)
            {
                field_35312_g = 0.0F;
            }
            if(field_35312_g > 1.0F)
            {
                field_35312_g = 1.0F;
            }
            ((ContainerCreative)inventorySlots).func_35374_a(field_35312_g);
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        boolean flag = Mouse.isButtonDown(0);
        int k = field_40216_e;
        int l = field_40215_f;
        int i1 = k + 155;
        int j1 = l + 17;
        int k1 = i1 + 14;
        int l1 = j1 + 160 + 2;
        if(!field_35314_i && flag && i >= i1 && j >= j1 && i < k1 && j < l1)
        {
            field_35313_h = true;
        }
        if(!flag)
        {
            field_35313_h = false;
        }
        field_35314_i = flag;
        if(field_35313_h)
        {
            field_35312_g = (float)(j - (j1 + 8)) / ((float)(l1 - j1) - 16F);
            if(field_35312_g < 0.0F)
            {
                field_35312_g = 0.0F;
            }
            if(field_35312_g > 1.0F)
            {
                field_35312_g = 1.0F;
            }
            ((ContainerCreative)inventorySlots).func_35374_a(field_35312_g);
        }
        super.drawScreen(i, j, f);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(2896 /*GL_LIGHTING*/);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int k = mc.renderEngine.getTexture("/gui/allitems.png");
        mc.renderEngine.bindTexture(k);
        int l = field_40216_e;
        int i1 = field_40215_f;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        int j1 = l + 155;
        int k1 = i1 + 17;
        int l1 = k1 + 160 + 2;
        drawTexturedModalRect(l + 154, i1 + 17 + (int)((float)(l1 - k1 - 17) * field_35312_g), 0, 208, 16, 16);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(guibutton.id == 0)
        {
            mc.displayGuiScreen(new GuiAchievements(mc.statFileWriter));
        }
        if(guibutton.id == 1)
        {
            mc.displayGuiScreen(new GuiStats(this, mc.statFileWriter));
        }
    }

    static InventoryBasic getInventory()
    {
        return inventory;
    }

}
