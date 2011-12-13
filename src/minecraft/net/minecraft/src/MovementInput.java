// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            EntityPlayer

public class MovementInput
{

    public float moveStrafe;
    public float moveForward;
    public boolean field_1177_c;
    public boolean jump;
    public boolean sneak;

    public MovementInput()
    {
        moveStrafe = 0.0F;
        moveForward = 0.0F;
        field_1177_c = false;
        jump = false;
        sneak = false;
    }

    public void updatePlayerMoveState(EntityPlayer entityplayer)
    {
    }
}
