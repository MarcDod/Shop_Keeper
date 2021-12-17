# Shop_Keeper
handle a fantasy Shop with Ultra HD Graphic 

![alt text](https://github.com/MarcDod/Shop_keeper/blob/master/core/assets/npc/npc/child0.png?raw=true)

# Befehle
I = Zahl <br>
R = RegisterA <br>
- = dont care <br>
A = register für A <br>
B = register für B

<details closed>
  <summary>ADDI</summary>
    &nbsp;&nbsp;&nbsp;0000 01II IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A + I und c = 0 <br> 
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + I) >= 2^16 <strong>dann:</strong> c = 1 und A = 2^16 - (A + I) <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + I) = 0 <strong>dann:</strong> z = 1 sonst: z = 0
</details>
<details closed>
  <summary>ADD</summary>
    &nbsp;&nbsp;&nbsp;0000 00-- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A + B und c = 0 <br> 
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + B) >= 2^16 <strong>dann:</strong> c = 1 und A = 2^16 - (A + B) <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + B) = 0 <strong>dann:</strong> z = 1 sonst: z = 0
</details>
<details closed>
  <summary>ADDCI</summary>
    &nbsp;&nbsp;&nbsp;0000 11II IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A + I und c = 0 <br> 
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + I + C) >= 2^16 <strong>dann:</strong> c = 1 und A = 2^16 - (A + I + C) <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + I + C) = 0 <strong>dann:</strong> z = 1 sonst: z = 0
</details>
<details closed>
  <summary>ADDC</summary>
    &nbsp;&nbsp;&nbsp;0000 10-- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A + B + C und c = 0 <br> 
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + B + C) >= 2^16 <strong>dann:</strong> c = 1 und A = 2^16 - (A + B + C) <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A + B + C) = 0 <strong>dann:</strong> z = 1 sonst: z = 0
</details>
<details closed>
  <summary>SUBI</summary>
    &nbsp;&nbsp;&nbsp;0001 01II IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A - I und c = 0 <br> 
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A - I) < 0 <strong>dann:</strong> c = 1 und A = 2^16 + (A - I) <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A - I ) = 0 <strong>dann:</strong> z = 1 sonst: z = 0
</details>
<details closed>
  <summary>SUB</summary>
    &nbsp;&nbsp;&nbsp;0001 00-- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A - B und c = 0 <br> 
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A - B) < 0 <strong>dann:</strong> c = 1 und A = 2^16 + (A - B) <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> (A - B) = 0 <strong>dann:</strong> z = 1 sonst: z = 0
</details>
<details closed>
  <summary>COMPAREI</summary>
    &nbsp;&nbsp;&nbsp;0001 11II IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> A < I <strong>dann:</strong> c = 1 und z = 0 <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> A > I <strong>dann:</strong> c = 0 und z = 0 <br> 
    &nbsp;&nbsp;&nbsp;<strong>sont</strong> c = 0 und z = 1 <br>
</details>
