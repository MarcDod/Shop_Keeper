# Shop_Keeper
handle a fantasy Shop with Ultra HD Graphic 

![alt text](https://github.com/MarcDod/Shop_keeper/blob/master/core/assets/npc/npc/child0.png?raw=true)

# Befehle
I = Zahl <br>
R = RegisterA <br>
- = dont care <br>
[RRRR]A = register für A <br>
[RRRR]B = register für B

<details closed>
  <summary>ADDI</summary>
    &nbsp;&nbsp;&nbsp;0000 01II IIII [RRRR]A <br>
    &nbsp;&nbsp;&nbsp;A = A + I und c = 0 <br> 
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + I) >= 2^16 <strong>dann:</strong> c = 1 und A = 2^16 - (A + I) <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + I) = 0 <strong>dann:</strong> z = 1 sonst: z = 0
</details>
<details closed>
  <summary>ADD</summary>
    &nbsp;&nbsp;&nbsp;0000 01-- [RRRR]B [RRRR]A <br>
    &nbsp;&nbsp;&nbsp;A = A + B und c = 0 <br> 
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + B) >= 2^16 <strong>dann:</strong> c = 1 und A = 2^16 - (A + B) <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + B) = 0 <strong>dann:</strong> z = 1 sonst: z = 0
</details>
