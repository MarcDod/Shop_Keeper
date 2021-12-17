# Shop_Keeper
handle a fantasy Shop with Ultra HD Graphic 

![alt text](https://github.com/MarcDod/Shop_keeper/blob/master/core/assets/npc/npc/child0.png?raw=true)

# Befehle
I = Zahl <br>
R = RegisterA <br>
<RRRR>A = register für A <br>
<RRRR>B = register für B

<details closed>
  <summary>ADD</summary>
    0000 01II IIII <RRRR>A <br>
    A = A + I und c = 0 <br> 
    Wenn (A + I) >= 2^16 dann: c = 1 und A = 2^16 - (A + I) <br>
    Wenn (A + I) = 0 dann: z = 1 sonst: z = 0
</details>
