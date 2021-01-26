# User's Manual of Java-like LL(1)Parser

21700431 Yejin Yang

> ### How to Use

1. Download the scanner program

   - option 1) Using Terminal

   ```
   git clone https://github.com/yejineee/compiler.git
   ```

   - option 2) Download ZIP

   Download a zip file from https://github.com/yejineee/compiler

2. Compile the main program

   Enter the following command at the terminal:

   ```
   javac Main.java
   ```

3. Run the scanner program

   Enter the following command at the terminal:

   ```
   java main.java
   ```

4. Enter the File path

   \- example

   ![image](https://user-images.githubusercontent.com/43772082/85711749-8bc5f980-b722-11ea-93fe-9070f3e7a839.png)

5. Wait for the result



> ### Rule of Java-like Language Parser

1. **Class statement**

 - BNF

   < S > 		::= 		< class-stmt > 

   < class-stmt > 		:: = class identifier { main () { < stmt > }}



- First Sets

  First ( class-stmt )		= { class }

  

- Follow Sets

  Follow ( S )   					= { $' }

  Follow ( class-stmt) 		= { $'}

  

  \- example

```java
class MyClass{
  main(){ //comment1
     $_Time0 = 4 ;
  }
}

```
![스크린샷 2020-06-25 오후 7 53 38](https://user-images.githubusercontent.com/43772082/85710943-c0858100-b721-11ea-89c6-6adab6bc82fe.png)

​		\- error example 

​		parsing의 시작에서 keyword가 class와 main이 아니라면 파싱 에러이다.

​			(1) error test : classsss

```java
classsss MyClass{
  main(){ //comment1
     $_Time0 = 4 ;
  }
}

```

![스크린샷 2020-06-25 오후 7 54 04](https://user-images.githubusercontent.com/43772082/85710946-c1b6ae00-b721-11ea-81f5-ca3ef6b9a90e.png)

​		(2) error test : main$

```java
class MyClass{
  main$ (){ //comment1
     $_Time0 = 4 ;
  }
}
```

![스크린샷 2020-06-25 오후 7 56 14](https://user-images.githubusercontent.com/43772082/85710950-c24f4480-b721-11ea-85fd-71103396a073.png)

2. **if statement**

- BNF

  < if-stmt > 	::= 	if ( <comp_exp> ) { < stmt > } <_if > 

  < _if > 		   ::=     else < stmt >  | Epsilon



- Follow Sets

  Follow ( if-stmt )		   =  First ( stmt ) + Follow ( stmt )	

  ​								   	= { "}", while, for, if, identifier, out.println, comment , "{", keyword}

  Follow ( _if )		          = Follow( if-stmt )

  ​									   = { "}", while, for, if, identifier, out.println, comment, "{", keyword }

- First Sets

  First (  if-stmt ) = { if }

  First( _if )		= { else , Epsilon }



​		- example

If-stmt가 중첩된 경우, if나 else 다음에 for문이나 while문이 온 경우 모두 정상적으로 동작함을 알 수 있다. 

```java
class MyClass{
  main(){ //comment1
    int $_Time0 = 22; 
    if ($_Time0 < 10){
      out.println("Good morning.");
      for (int _i$ = 0 ; _i$ <= 4  ; _i$ = _i + 2 ) {
        out.println(_i$);
      }
      if ($_Time0 < 10){
      out.println("Good morning.");
      }
    }
    else if ($_Time0 < 20){
      out.println("Good day."); 
    }
    else if ($_Time0 < 20){
       while (_iValue$ < 5){
        out.println(_iValue$ );
        _iValue$++;
      }
    }
    else {
      out.println("Good evening."); 
    }
  }
}
```

![스크린샷 2020-06-25 오후 8 10 03](https://user-images.githubusercontent.com/43772082/85710964-c4190800-b721-11ea-90ae-d00611e99f41.png)

​		\- error example

​	if, else의 keyword가 일치하지 않는 경우, 에러가 발생한다.

​			(1) error test : else0 if 

```java
class MyClass{
  main(){ //comment1
    int $_Time0 = 22; 
    if ($_Time0 < 10){
      out.println("Good morning.");
    }
    else0 if ($_Time0 < 20){
      out.println("Good today."); 
    }
  }
}
```
![스크린샷 2020-06-25 오후 8 00 10](https://user-images.githubusercontent.com/43772082/85710952-c24f4480-b721-11ea-9df3-54f7b8681d36.png)

​		(2) error test : else0

```java
class MyClass{
  main(){ //comment1
    int $_Time0 = 22 ; 
    if ($_Time0 < 10){
      out.println("test...");
    }
    else0{
      out.println( $_Time0 ); 
    }
  }
}
```

![스크린샷 2020-06-25 오후 8 02 22](https://user-images.githubusercontent.com/43772082/85710956-c2e7db00-b721-11ea-810c-397c26ca663b.png)

​		(3) error test : if$

```java
class MyClass{
  main(){ //comment1
    int $_Time0 = 22 ; 
    if$ ($_Time0 < 10){
      out.println("test...");
    }
    else{
      out.println( $_Time0 ); 
    }
  }
}
```

![스크린샷 2020-06-25 오후 8 07 49](https://user-images.githubusercontent.com/43772082/85710960-c3807180-b721-11ea-8469-f4148907476c.png)

3. **while statement**

- BNF

  < while-stmt > 	::= 	while ( < comp_exp > ) { < stmt > }
  
  
  
- Follow Sets

  Follow ( while-stmt )   =   First ( stmt ) + Follow ( stmt )	

  ​									   = { "}", while, for, if, identifier, out.println, comment, "{", keyword }

  

- First Sets

  First ( while-stmt )  = { while }

\- example

```java
class TestClass {
  main() {//comment3
    int _iValue$ = 0;
    while (_iValue$ < 5){
      out.println(_iValue$ );
      if ($_Time0 < 10){
        out.println("Good morning.");
      }
      for (int _i$ = 0 ; _i$ <= 4 ; _i$ = _i + 2 ) {
        out.println(_i$);
      }
    }
  }
}
```



​	\- error example

​	(1) error test :  while ( **_iValue$ - 4** ){

```java
class TestClass {
  main() {//comment3
    int _iValue$ = 0;
    while ( _iValue$ - 4 ){
      out.println(_iValue$ );
      _iValue$++;
    }
  }
}
```

![스크린샷 2020-06-25 오후 8.12.58](/Users/yang-yejin/Desktop/스크린샷 2020-06-25 오후 8.12.58.png)

4. **for statement**

- BNF

  < for-stmt >             ::= for ( < init-stmt > ; < comp_exp > ; < assign-stmt > ) { < stmt > }

  

- Follow Sets

  Follow ( for-stmt )		=  First ( stmt ) + Follow ( stmt )		

  ​										= { "}", while, for, if, identifier, out.println, comment , "{",  keyword}

  

- First Sets

  First ( for-stmt )			 = { for }



​	\- example

```java
class Example {
  main() {
    for (int _i$ = 0 ; _i$ <= _$ + 10 ; _i$ = _i$ + 2) {
      out.println(_i$);
    }
  }
} 
```

​	![스크린샷 2020-06-25 오후 8 27 25](https://user-images.githubusercontent.com/43772082/85711477-4a354e80-b722-11ea-9675-9d2b4d940865.png)

\- error example

​	(1) error test :  for (int _i$ = 0 **; ;** _i$ = _i$ + 2) 

```java
class Example {
  main() {
    for (int _i$ = 0 ; ; _i$ = _i$ + 2) {
      out.println(_i$);
    }
  }
}
```

![스크린샷 2020-06-25 오후 8 27 46](https://user-images.githubusercontent.com/43772082/85711526-56211080-b722-11ea-9f37-fe65c867bd13.png)
