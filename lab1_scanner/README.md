# User's Manual of Java-like Language Scanner 

Yejin Yang

> ### How to Use

1. Download the scanner program

   https://github.com/yejineee/compiler/blob/master/lab1_scanner/JavaScanner.java

2. Compile the scanner program

   Enter the following command at the terminal:

   ```
   javac JavaScanner.java
   ```

3. Run the scanner program

   Enter the following command at the terminal:

   ```
   java JavaScanner.java
   ```

4. Enter the File path

   \- example

   ![스크린샷 2020-04-18 오전 1.40.45](/Users/yang-yejin/Desktop/compiler/lab1_scanner/testpath.png)

5. Wait for the result



> ### Rule of Java-like Language Scanner

The scanner largely divides each rexeme into a total of six tokens (Identifier, Number Literal, String Literal, Comment, Operator, and Special Symbols).

![overview_scanner](https://user-images.githubusercontent.com/43772082/79598588-34524e80-811f-11ea-8cf6-fd2be69a925e.png)

1. ##### Identifier

   Starts with $, _, alphabet and optionally followed by $, _, alphabets and numbers.

   \-Exception1: one letter, “_” cannot be used as an identifier

   \-Exception2: out.println interpreted as keyword or id with unique lexeme 

​	\- example

![testid](https://user-images.githubusercontent.com/43772082/79599102-ea1d9d00-811f-11ea-8bcd-b8d7b2a656ea.png)

2. **Comment**

   Comment is a line of sentences that starts with "//"

   \- example

   ![javalike](https://user-images.githubusercontent.com/43772082/79599690-ec342b80-8120-11ea-9752-d564c566abd7.png)

3. **String Literal**

   A sentence between double quot(\") is identified as string literal.

   \- example

   ![teststr](https://user-images.githubusercontent.com/43772082/79599113-ed188d80-811f-11ea-98bb-e88cff852c5a.png)
   

4. **Number Literal**

   Number Literal is a signed integer, an unsigned integer, a signed number with decimal, and an unsigned number with decimal.

   \- example

   ![testnum](https://user-images.githubusercontent.com/43772082/79599107-ebe76080-811f-11ea-8470-d805d51ff352.png)

5. **Operator**

   In the case of Operator, the scanner recognizes the following as operator:

   ![testop](https://user-images.githubusercontent.com/43772082/79599108-ebe76080-811f-11ea-9f46-2e0d8ccc3914.png)

6. **Special Symbol**

   In the case of Special Symbol, the scanner recognizes the following as Special Symbol:

   ![testspe](https://user-images.githubusercontent.com/43772082/79599112-ec7ff700-811f-11ea-8aee-49ad656e95b1.png)
