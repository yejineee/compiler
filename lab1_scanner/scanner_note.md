





# Java-like Language Scanner

Yejin Yang



>  ### Regular Expression for each Lexical Unit



Alphabet = [a-z] | [A-Z]

Digit = [0-9]



1. ##### Identifier

   Identifier = out.println	|	$ ($|\_|alphabet|digit)*	| 	( \_ |alphabet )($|\_|alphabet|digit)?

2. ##### Number Literal

   Number Literal = (+|-)?digit+("."digit+)?

3. ##### String Literal

   String Literal = "(~")*"

4. ##### Comment

   Comment = //(~newline)*

5. ##### Operator

   Operator = ! | != | <> | = |== | "|" | "||" |  && |  & |  + |  - |  * |  / | < | > | <= | >= | 

   ​					++ | -- | += | -= | /= | *= | %= | |= | &= | >> | <<  

6. ##### Special Symbol

   - Statement terminator = ;
   - Left curly brace = {
   - Right curly brace = }
   - Left parenthesis = (
   - Right parentheses = )



> ### NFA & DFA

- ##### Overview

  ![overview_scanner](https://user-images.githubusercontent.com/43772082/79598588-34524e80-811f-11ea-8cf6-fd2be69a925e.png)



- ##### NFA

![nfa](https://user-images.githubusercontent.com/43772082/79599183-0c171f80-8120-11ea-82cf-18e0011ad4e3.jpg)

- ##### DFA

![dfa](https://user-images.githubusercontent.com/43772082/79598667-52b84a00-811f-11ea-96a2-c120ade95267.jpg)





> ### Program Simulation



1. test.txt

   - test.txt

     ```
     class MyClass{
       main(){ //comment1
         int $_Time0 = 22; 
         if ($_Time0 < 10){
           out.println("Good morning.");
         }
         else if ($_Time0 < 20){
           out.println("Good day."); 
         }
         else{
           out.println("Good evening."); 
         }
       }
     }
     ```

     

   - running output

     ![test](https://user-images.githubusercontent.com/43772082/79599086-e558e900-811f-11ea-8c23-9a1fcd318336.png)

     

2. test2.txt

   - test2.txt

   ```
   class TestClass {
     main() {//comment3
       int _iValue$ = 0;
       while (_iValue$ < 5){
         out.println(_iValue$ );
         _iValue$++;
       }
     }
   }
   ```

   

   - running output

     ![test2](https://user-images.githubusercontent.com/43772082/79599089-e722ac80-811f-11ea-8093-ef22fc81c616.png)

3. test3.txt

   - test3.txt

     ```
     class Example {
       main() {
         for (int _i$ = 0 ; _i$ <= 10 ; _i$ = _i$ + 2) {
           out.println(_i$);
         }
       }
     }
     ```

     

   - running output

     ![test3](https://user-images.githubusercontent.com/43772082/79599090-e7bb4300-811f-11ea-960a-a9572e54f440.png)

4. testEr.txt

   - testEr.txt

     ```
     class MyClass {
       main() { //comment1
         int $_*Time0 = 22; 
         if($_*Time0 < 10){
           out.println("Good morning.");
         }
         else if($_*Time0 < 20){
           out.println("Good day."); 
         }
         else{
           out.println("Good evening."); 
         }
       }
     }
     ```

     

   - running output

     ![testEr](https://user-images.githubusercontent.com/43772082/79599095-e853d980-811f-11ea-9bf4-cb303c43bcd5.png)

5. testEr2.txt

   - testEr2.txt

   ```
   class TestClass {
     main() { 
       int _iValue$ = 012$; // illegal literal
       while(_iValue$ < 5){
         out.println(_iValue$ );
         _iValue$++;
       }
     }
   }
   ```

   

   - running output

   ![testEr2](https://user-images.githubusercontent.com/43772082/79599096-e8ec7000-811f-11ea-81e1-7119292622ca.png)

6. testEr3.txt

   - testEr3.txt

   ```
   class 0Example {
     main() {
       for(int _i$ = 0; _i$ <= 10; _i$ = i + 2){
         out.println(_i$);
       }
     }
   }
   ```

   

   - running output

   ![testEr3](https://user-images.githubusercontent.com/43772082/79599098-e9850680-811f-11ea-96b4-3f22ec6de757.png)

7. Test Identifier

   ![testid](https://user-images.githubusercontent.com/43772082/79599102-ea1d9d00-811f-11ea-8bcd-b8d7b2a656ea.png)



​	\-Exception1: one letter, “_” cannot be used as an identifier

![testid2](https://user-images.githubusercontent.com/43772082/79599103-eab63380-811f-11ea-8ca7-0afee5db3e3b.png)

![testid3](https://user-images.githubusercontent.com/43772082/79599104-eb4eca00-811f-11ea-944e-f4df625fbf39.png)



\-Exception2: out.println interpreted as keyword or id with unique lexeme 

![testid4](https://user-images.githubusercontent.com/43772082/79599105-eb4eca00-811f-11ea-9b98-ef471cf4f20e.png)

8. Test String Literal

- string.txt

  ```
  "hello 
  my
  name
  is 
  yejin"
  
  
  "this is     string    literal   "
  
  "this is *not* string literal
  ```

  

- running output

  ![teststr](https://user-images.githubusercontent.com/43772082/79599113-ed188d80-811f-11ea-98bb-e88cff852c5a.png)
  

Since the last sentence is not end with ", it is not recognized as string literal.





9. Test Number Literal

   

   ![testnum](https://user-images.githubusercontent.com/43772082/79599107-ebe76080-811f-11ea-8470-d805d51ff352.png)

   Since the last one has no number after the decimal point, it is not recognized as number literal.





10. Test Operator

![testop](https://user-images.githubusercontent.com/43772082/79599108-ebe76080-811f-11ea-9f46-2e0d8ccc3914.png)

11. Test Comment

![testcom](https://user-images.githubusercontent.com/43772082/79599552-a9725380-8120-11ea-810c-12b95a91b2f1.png)

12. Test Special Symbol

![testspe](https://user-images.githubusercontent.com/43772082/79599112-ec7ff700-811f-11ea-8aee-49ad656e95b1.png)

