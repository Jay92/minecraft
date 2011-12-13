// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.*;

// Referenced classes of package net.minecraft.src:
//            PotionEffect, Potion

public class PotionHelper
{

    public static final String field_40367_a = null;
    public static final String field_40365_b = "-0+1-2-3&4-4+13";
    public static final String field_40366_c = "+0-1-2-3&4-4+13";
    public static final String field_40363_d = "-0-1+2-3&4-4+13";
    public static final String field_40364_e = "-0+3-4+13";
    public static final String field_40361_f = "+0-1+2-3&4-4+13";
    public static final String field_40362_g = "+0-1-2+3&4-4+13";
    public static final String field_40374_h = "+0+1-2-3&4-4+13";
    public static final String field_40375_i = "-5+6-7";
    public static final String field_40372_j = "+5-6-7";
    public static final String field_40373_k = "+14&13-13";
    private static final HashMap field_40370_l;
    private static final HashMap field_40371_m;
    private static final HashMap field_40368_n = new HashMap();
    private static final String field_40369_o[] = {
        "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", 
        "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", 
        "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", 
        "potion.prefix.gross", "potion.prefix.stinky"
    };

    public PotionHelper()
    {
    }

    public static boolean func_40357_a(int i, int j)
    {
        return (i & 1 << j) != 0;
    }

    private static int func_40349_b(int i, int j)
    {
        return func_40357_a(i, j) ? 1 : 0;
    }

    private static int func_40353_c(int i, int j)
    {
        return func_40357_a(i, j) ? 0 : 1;
    }

    public static int func_40352_a(int i)
    {
        return func_40351_a(i, 5, 4, 3, 2, 1);
    }

    public static int func_40354_a(Collection collection)
    {
        int i = 0x385dc6;
        if(collection == null || collection.isEmpty())
        {
            return i;
        }
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        float f3 = 0.0F;
        for(Iterator iterator = collection.iterator(); iterator.hasNext();)
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            int j = Potion.potionTypes[potioneffect.getPotionID()].func_40621_j();
            int k = 0;
            while(k <= potioneffect.getAmplifier()) 
            {
                f += (float)(j >> 16 & 0xff) / 255F;
                f1 += (float)(j >> 8 & 0xff) / 255F;
                f2 += (float)(j >> 0 & 0xff) / 255F;
                f3++;
                k++;
            }
        }

        f = (f / f3) * 255F;
        f1 = (f1 / f3) * 255F;
        f2 = (f2 / f3) * 255F;
        return (int)f << 16 | (int)f1 << 8 | (int)f2;
    }

    public static int func_40358_a(int i, boolean flag)
    {
        if(!flag)
        {
            if(field_40368_n.containsKey(Integer.valueOf(i)))
            {
                return ((Integer)field_40368_n.get(Integer.valueOf(i))).intValue();
            } else
            {
                int j = func_40354_a(func_40360_b(i, false));
                field_40368_n.put(Integer.valueOf(i), Integer.valueOf(j));
                return j;
            }
        } else
        {
            return func_40354_a(func_40360_b(i, flag));
        }
    }

    public static String func_40359_b(int i)
    {
        int j = func_40352_a(i);
        return field_40369_o[j];
    }

    private static int func_40347_a(boolean flag, boolean flag1, boolean flag2, int i, int j, int k, int l)
    {
        int i1 = 0;
        if(flag)
        {
            i1 = func_40353_c(l, j);
        } else
        if(i != -1)
        {
            if(i == 0 && func_40348_c(l) == j)
            {
                i1 = 1;
            } else
            if(i == 1 && func_40348_c(l) > j)
            {
                i1 = 1;
            } else
            if(i == 2 && func_40348_c(l) < j)
            {
                i1 = 1;
            }
        } else
        {
            i1 = func_40349_b(l, j);
        }
        if(flag1)
        {
            i1 *= k;
        }
        if(flag2)
        {
            i1 *= -1;
        }
        return i1;
    }

    private static int func_40348_c(int i)
    {
        int j;
        for(j = 0; i > 0; j++)
        {
            i &= i - 1;
        }

        return j;
    }

    private static int func_40355_a(String s, int i, int j, int k)
    {
        if(i >= s.length() || j < 0 || i >= j)
        {
            return 0;
        }
        int l = s.indexOf('|', i);
        if(l >= 0 && l < j)
        {
            int i1 = func_40355_a(s, i, l - 1, k);
            if(i1 > 0)
            {
                return i1;
            }
            int k1 = func_40355_a(s, l + 1, j, k);
            if(k1 > 0)
            {
                return k1;
            } else
            {
                return 0;
            }
        }
        int j1 = s.indexOf('&', i);
        if(j1 >= 0 && j1 < j)
        {
            int l1 = func_40355_a(s, i, j1 - 1, k);
            if(l1 <= 0)
            {
                return 0;
            }
            int i2 = func_40355_a(s, j1 + 1, j, k);
            if(i2 <= 0)
            {
                return 0;
            }
            if(l1 > i2)
            {
                return l1;
            } else
            {
                return i2;
            }
        }
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        byte byte0 = -1;
        int j2 = 0;
        int k2 = 0;
        int l2 = 0;
        for(int i3 = i; i3 < j; i3++)
        {
            char c = s.charAt(i3);
            if(c >= '0' && c <= '9')
            {
                if(flag)
                {
                    k2 = c - 48;
                    flag1 = true;
                } else
                {
                    j2 *= 10;
                    j2 += c - 48;
                    flag2 = true;
                }
                continue;
            }
            if(c == '*')
            {
                flag = true;
                continue;
            }
            if(c == '!')
            {
                if(flag2)
                {
                    l2 += func_40347_a(flag3, flag1, flag4, byte0, j2, k2, k);
                    flag2 = flag1 = flag = flag4 = flag3 = false;
                    j2 = k2 = 0;
                    byte0 = -1;
                }
                flag3 = true;
                continue;
            }
            if(c == '-')
            {
                if(flag2)
                {
                    l2 += func_40347_a(flag3, flag1, flag4, byte0, j2, k2, k);
                    flag2 = flag1 = flag = flag4 = flag3 = false;
                    j2 = k2 = 0;
                    byte0 = -1;
                }
                flag4 = true;
                continue;
            }
            if(c == '=' || c == '<' || c == '>')
            {
                if(flag2)
                {
                    l2 += func_40347_a(flag3, flag1, flag4, byte0, j2, k2, k);
                    flag2 = flag1 = flag = flag4 = flag3 = false;
                    j2 = k2 = 0;
                    byte0 = -1;
                }
                if(c == '=')
                {
                    byte0 = 0;
                    continue;
                }
                if(c == '<')
                {
                    byte0 = 2;
                    continue;
                }
                if(c == '>')
                {
                    byte0 = 1;
                }
                continue;
            }
            if(c == '+' && flag2)
            {
                l2 += func_40347_a(flag3, flag1, flag4, byte0, j2, k2, k);
                flag2 = flag1 = flag = flag4 = flag3 = false;
                j2 = k2 = 0;
                byte0 = -1;
            }
        }

        if(flag2)
        {
            l2 += func_40347_a(flag3, flag1, flag4, byte0, j2, k2, k);
        }
        return l2;
    }

    public static List func_40360_b(int i, boolean flag)
    {
        ArrayList arraylist = null;
        Potion apotion[] = Potion.potionTypes;
        int j = apotion.length;
        for(int k = 0; k < j; k++)
        {
            Potion potion = apotion[k];
            if(potion == null || potion.func_40612_i() && !flag)
            {
                continue;
            }
            String s = (String)field_40370_l.get(Integer.valueOf(potion.func_40619_a()));
            if(s == null)
            {
                continue;
            }
            int l = func_40355_a(s, 0, s.length(), i);
            if(l <= 0)
            {
                continue;
            }
            int i1 = 0;
            String s1 = (String)field_40371_m.get(Integer.valueOf(potion.func_40619_a()));
            if(s1 != null)
            {
                i1 = func_40355_a(s1, 0, s1.length(), i);
                if(i1 < 0)
                {
                    i1 = 0;
                }
            }
            if(potion.func_40622_b())
            {
                l = 1;
            } else
            {
                l = 1200 * (l * 3 + (l - 1) * 2);
                l >>= i1;
                l = (int)Math.round((double)l * potion.func_40610_g());
                if((i & 0x4000) != 0)
                {
                    l = (int)Math.round((double)l * 0.75D + 0.5D);
                }
            }
            if(arraylist == null)
            {
                arraylist = new ArrayList();
            }
            arraylist.add(new PotionEffect(potion.func_40619_a(), l, i1));
        }

        return arraylist;
    }

    private static int func_40350_a(int i, int j, boolean flag, boolean flag1, boolean flag2)
    {
        if(flag2)
        {
            if(!func_40357_a(i, j))
            {
                return 0;
            }
        } else
        if(flag)
        {
            i &= ~(1 << j);
        } else
        if(flag1)
        {
            if((i & 1 << j) != 0)
            {
                i &= ~(1 << j);
            } else
            {
                i |= 1 << j;
            }
        } else
        {
            i |= 1 << j;
        }
        return i;
    }

    public static int func_40356_a(int i, String s)
    {
        boolean flag = false;
        int j = s.length();
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        int k = 0;
        for(int l = ((flag) ? 1 : 0); l < j; l++)
        {
            char c = s.charAt(l);
            if(c >= '0' && c <= '9')
            {
                k *= 10;
                k += c - 48;
                flag1 = true;
                continue;
            }
            if(c == '!')
            {
                if(flag1)
                {
                    i = func_40350_a(i, k, flag3, flag2, flag4);
                    flag1 = flag3 = flag2 = flag4 = false;
                    k = 0;
                }
                flag2 = true;
                continue;
            }
            if(c == '-')
            {
                if(flag1)
                {
                    i = func_40350_a(i, k, flag3, flag2, flag4);
                    flag1 = flag3 = flag2 = flag4 = false;
                    k = 0;
                }
                flag3 = true;
                continue;
            }
            if(c == '+')
            {
                if(flag1)
                {
                    i = func_40350_a(i, k, flag3, flag2, flag4);
                    flag1 = flag3 = flag2 = flag4 = false;
                    k = 0;
                }
                continue;
            }
            if(c != '&')
            {
                continue;
            }
            if(flag1)
            {
                i = func_40350_a(i, k, flag3, flag2, flag4);
                flag1 = flag3 = flag2 = flag4 = false;
                k = 0;
            }
            flag4 = true;
        }

        if(flag1)
        {
            i = func_40350_a(i, k, flag3, flag2, flag4);
        }
        return i & 0x7fff;
    }

    public static int func_40351_a(int i, int j, int k, int l, int i1, int j1)
    {
        return (func_40357_a(i, j) ? 0x10 : 0) | (func_40357_a(i, k) ? 8 : 0) | (func_40357_a(i, l) ? 4 : 0) | (func_40357_a(i, i1) ? 2 : 0) | (func_40357_a(i, j1) ? 1 : 0);
    }

    static 
    {
        field_40370_l = new HashMap();
        field_40371_m = new HashMap();
        field_40370_l.put(Integer.valueOf(Potion.potionRegeneration.func_40619_a()), "0 & !1 & !2 & !3 & 0+6");
        field_40370_l.put(Integer.valueOf(Potion.potionSpeed.func_40619_a()), "!0 & 1 & !2 & !3 & 1+6");
        field_40370_l.put(Integer.valueOf(Potion.potionFireReistance.func_40619_a()), "0 & 1 & !2 & !3 & 0+6");
        field_40370_l.put(Integer.valueOf(Potion.potionHeal.func_40619_a()), "0 & !1 & 2 & !3");
        field_40370_l.put(Integer.valueOf(Potion.potionPoison.func_40619_a()), "!0 & !1 & 2 & !3 & 2+6");
        field_40370_l.put(Integer.valueOf(Potion.potionWeakness.func_40619_a()), "!0 & !1 & !2 & 3 & 3+6");
        field_40370_l.put(Integer.valueOf(Potion.potionHealDamage.func_40619_a()), "!0 & !1 & 2 & 3");
        field_40370_l.put(Integer.valueOf(Potion.potionSlowdown.func_40619_a()), "!0 & 1 & !2 & 3 & 3+6");
        field_40370_l.put(Integer.valueOf(Potion.potionDamageBoost.func_40619_a()), "0 & !1 & !2 & 3 & 3+6");
        field_40371_m.put(Integer.valueOf(Potion.potionSpeed.func_40619_a()), "5");
        field_40371_m.put(Integer.valueOf(Potion.potionDigSpeed.func_40619_a()), "5");
        field_40371_m.put(Integer.valueOf(Potion.potionDamageBoost.func_40619_a()), "5");
        field_40371_m.put(Integer.valueOf(Potion.potionRegeneration.func_40619_a()), "5");
        field_40371_m.put(Integer.valueOf(Potion.potionHealDamage.func_40619_a()), "5");
        field_40371_m.put(Integer.valueOf(Potion.potionHeal.func_40619_a()), "5");
        field_40371_m.put(Integer.valueOf(Potion.potionResistance.func_40619_a()), "5");
        field_40371_m.put(Integer.valueOf(Potion.potionPoison.func_40619_a()), "5");
    }
}
