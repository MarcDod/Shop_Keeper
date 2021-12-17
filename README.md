# Shop_Keeper
handle a fantasy Shop with Ultra HD Graphic 

![alt text](https://github.com/MarcDod/Shop_keeper/blob/master/core/assets/npc/npc/child0.png?raw=true)

# Befehle
I = Zahl <br>
R = RegisterA <br>
- = dont care <br>
A = register für A <br>
B = register für B
PC = Program Counter

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
    &nbsp;&nbsp;&nbsp;<strong>sont</strong> c = 0 und z = 1 
</details>
<details closed>
  <summary>COMPARE</summary>
    &nbsp;&nbsp;&nbsp;0001 10-- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> A < B <strong>dann:</strong> c = 1 und z = 0 <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> A > B <strong>dann:</strong> c = 0 und z = 0 <br> 
    &nbsp;&nbsp;&nbsp;<strong>sont</strong> c = 0 und z = 1 
</details>
<details closed>
  <summary>ANDI</summary>
    &nbsp;&nbsp;&nbsp;0010 01II IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A and I
</details>
<details closed>
  <summary>AND</summary>
    &nbsp;&nbsp;&nbsp;0010 00-- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A and B
</details>
<details closed>
  <summary>ORI</summary>
    &nbsp;&nbsp;&nbsp;0010 11II IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A or I
</details>
<details closed>
  <summary>OR</summary>
    &nbsp;&nbsp;&nbsp;0010 10-- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A or B
</details>
<details closed>
  <summary>XOR</summary>
    &nbsp;&nbsp;&nbsp;0011 01II IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A or I
</details>
<details closed>
  <summary>XOR</summary>
    &nbsp;&nbsp;&nbsp;0011 00-- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A or B
</details>
<details closed>
  <summary>MOVE</summary>
    &nbsp;&nbsp;&nbsp;0011 1--- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A = B
</details>
<details closed>
  <summary>SL0I</summary>
    &nbsp;&nbsp;&nbsp;0100 010- IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A << I mit 0 aufgefüllt
</details>
<details closed>
  <summary>SL0</summary>
    &nbsp;&nbsp;&nbsp;0100 000- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A << B mit 0 aufgefüllt
</details>
<details closed>
  <summary>SR0I</summary>
    &nbsp;&nbsp;&nbsp;0100 011- IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A >> I mit 0 aufgefüllt
</details>
<details closed>
  <summary>SR0</summary>
    &nbsp;&nbsp;&nbsp;0100 001- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A >> B mit 0 aufgefüllt
</details>
<details closed>
  <summary>SR0I</summary>
    &nbsp;&nbsp;&nbsp;0100 110- IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A << I mit 1 aufgefüllt
</details>
<details closed>
  <summary>SLI</summary>
    &nbsp;&nbsp;&nbsp;0100 100- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A << B mit 1 aufgefüllt
</details>
<details closed>
  <summary>SR1I</summary>
    &nbsp;&nbsp;&nbsp;0100 111- IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A >> I mit 1 aufgefüllt
</details>
<details closed>
  <summary>SR1</summary>
    &nbsp;&nbsp;&nbsp;0100 101- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A >> B mit 1 aufgefüllt
</details>
<details closed>
  <summary>RL</summary>
    &nbsp;&nbsp;&nbsp;0101 0--- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A = B[14 - 0] & B[15] <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> B[15] == 1 <strong>dann:</strong> c = 1 <strong>sonst:</strong> c = 0 <br>
</details>
<details closed>
  <summary>RR</summary>
    &nbsp;&nbsp;&nbsp;0101 1--- BBBB AAAA <br>
    &nbsp;&nbsp;&nbsp;A = B[0] & B[15 - 1] <br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn</strong> B[0] == 1 <strong>dann:</strong> c = 1 <strong>sonst:</strong> c = 0 <br>
</details>
<details closed>
  <summary>LOADL</summary>
    &nbsp;&nbsp;&nbsp;0111 IIII IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A = A[15 - 8] & I <br>
</details>
<details closed>
  <summary>LOADH</summary>
    &nbsp;&nbsp;&nbsp;0111 IIII IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;A = I & A[7 - 0] <br>
</details>
<details closed>
  <summary>STOREI</summary>
    &nbsp;&nbsp;&nbsp;1001 01II IIII AAAA <br>
    &nbsp;&nbsp;&nbsp;RAM(A) = I <br>
</details>
<details closed>
  <summary>STORE</summary>
    &nbsp;&nbsp;&nbsp;1001 00-- B AAAA <br>
    &nbsp;&nbsp;&nbsp;RAM(A) = B <br>
</details>
<details closed>
  <summary>OUTPUTI</summary>
    &nbsp;&nbsp;&nbsp;1001 11II IIII AAAA<br>
    &nbsp;&nbsp;&nbsp;OUTPUT(A) = I <br>
</details>
<details closed>
  <summary>OUTPUT</summary>
    &nbsp;&nbsp;&nbsp;1001 10-- BBBB AAAA<br>
    &nbsp;&nbsp;&nbsp;OUTPUT(A) = B <br>
</details>
<details closed>
  <summary>LOAD</summary>
    &nbsp;&nbsp;&nbsp;110- ---- BBBB AAAA<br>
    &nbsp;&nbsp;&nbsp;A = RAM(B) <br>
</details>
<details closed>
  <summary>INPUT</summary>
    &nbsp;&nbsp;&nbsp;1001 10-- BBBB AAAA<br>
    &nbsp;&nbsp;&nbsp;A = INPUT(B)<br>
</details>
<details closed>
  <summary>JUMP</summary>
    &nbsp;&nbsp;&nbsp;1011 ---- BBBB AAAA<br>
    &nbsp;&nbsp;&nbsp;A = PC<br>
    &nbsp;&nbsp;&nbsp;PC = B<br>
</details>
<details closed>
  <summary>JUMPC</summary>
    &nbsp;&nbsp;&nbsp;1010 0-1- BBBB AAAA<br>
    &nbsp;&nbsp;&nbsp;<strong>Wenn:</strong> C = 1 <strong>Dann</strong> <span style="color: green"> Some green text </span> 
</details>
