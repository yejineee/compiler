# Java-like Language LR(1) Parser

 	21700431 Yejin Yang

> ## Design UML

![image](https://user-images.githubusercontent.com/43772082/85711638-6df89480-b722-11ea-9222-b8d6d4832845.png)

- Left Factoring과 Left Recursion Removal을 하여 BNF를 만들고, 그에 맞는 Parsing Table을 구성한다.
- enum State로 Non terminal과 Terminal을 선언한다.

- Scanner 

  : Scanning에서 lexeme에 대한 Token이 정해진다.

- LL(1) parser 
  - Parsing Stack top이 terminal인 경우

    input과 일치하면 pop하고, 일치하지 않는다면 error를 리턴한다.

  - Parsing Stack top이 Nonterminal 경우, 

    Parsing table에서 해당 input의 top과 Non Terminal에 맞는 expression을 찾아 Non Terminal을 대체한다.

>  ## BNF & First Sets & Follow Sets 



1. **if-stmt**

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

     

2. **while-stmt**

   - BNF

     < while-stmt > 	::= 	while ( < comp_exp > ) { < stmt > }

     

   - Follow Sets

     Follow ( while-stmt )   =   First ( stmt ) + Follow ( stmt )	

     ​									   = { "}", while, for, if, identifier, out.println, comment, "{", keyword }

     

   - First Sets

     First ( while-stmt )  = { while }

     

3. **for-stmt**

   - BNF

     < for-stmt >             ::= for ( < init-stmt > ; < comp_exp > ; < assign-stmt > ) { < stmt > }

     

   - Follow Sets

     Follow ( for-stmt )		=  First ( stmt ) + Follow ( stmt )		

     ​										= { "}", while, for, if, identifier, out.println, comment , "{",  keyword}

     

   - First Sets

     First ( for-stmt )			 = { for }

     

4. **Class-stmt**

   - BNF

     < S > 		::= 		< class-stmt > 

     < class-stmt > 		:: = class identifier { main () { < stmt > }}

     

   - First Sets

     First ( class-stmt )		= { class }

     

   - Follow Sets

     Follow ( S )   					= { $' }

     Follow ( class-stmt) 		= { $'}

5. Etc

   - BNF

     < stmt > 					::= < while-stmt > < stmt > 

     ​										| < for-stmt> < stmt >

     ​										| < if-stmt > < stmt >

     ​										| < print-stmt > < stmt >

     ​										| comment < stmt >

     ​										|  identifier <_stmt > ;  < stmt > 

     ​										| { < stmt > }

     ​										| keyword  < assign-stmt > ; < stmt > 

     ​										| Epsilon

     < _stmt > 			   	::=  ++ | -- |  =  < exp >

     < print-stmt > 		   ::=    out.println ( < _print-stmt > 	

     < _print-stmt > 		 ::=  string_literal ) ; | identifier ) ;

     < init-stmt > 			  ::=  keyword < assign-stmt > 

     < assign-stmt >	::= identifier = < exp >

     < comp_exp > 		  ::=  < exp > < compare > < exp > 

     < exp > 				  	::=  < terminal > < exp' > | ( < exp > ) < exp' >  

     < exp' > 					 ::=  < op > < terminal > < exp' > | Epsilon

     < terminal> 	    	  ::=  identifier | Number_Literal

     < compare > 			::=  < | > | <= | >= | == | != 

     < op > 					   ::= + | - | * | /

   - Follow Sets

     Follow ( exp )				=  { ) } + First ( compare ) + Follow ( comp_exp ) 

     ​											 + Follow ( assign-stmt)  + Follow ( _stmt)

     ​										= { < , > , <= , >= , == , !=, ), ;  }

     Follow ( stmt )	  		= { "}" }

     Follow ( _stmt )		= { ; }

     Follow ( assign-stmt )	= follow(_stmt) + { ) }

     ​										 = { ; , ) }

     Follow ( print-stmt )		= First ( stmt ) + Follow ( stmt )	

     ​											= { "}", while, for, if, identifier, out.println, comment , "{", keyword}

     Follow ( print-stmt )			= { ) }

     Follow ( init-stmt )			= { ; }

     Follow ( comp_exp ) 	= { ) , ; }

     Follow ( compare ) 		= First ( exp )

     ​										= { Number_Literal, identifier, ( }

     Follow ( comment )		= { "}" }

     Follow ( terminal )		= First ( exp' ) + Follow(exp') + Folllow ( exp )

     ​										= { < , > , <= , >= , == , !=, ), ; }

     Follow ( exp' )				 = Follow ( exp )

     ​										= { < , > , <= , >= , == , !=, ), ;  }

     Follow ( op )				= First ( exp) + First ( terminal )

     ​									=  {  Number_Literal, identifier, ( }  

   - First Sets

     First ( stmt )			 = {while, for, if, identifier, out.println, comment,"{", keyword, Epsilon }

     First ( _stmt )			= { identifier, =, ++, -- }

     First ( init-stmt )				= { keyword  }

     First ( assign-stmt )		= { identifier }

     First ( print-stmt )		  = { out.println }

     First ( _print-stmt )		  = { identifier, string_literal }

     First ( self-exp )	= { identifier }

     First ( self-op ) = { ++, --}

     First ( comp_exp )  =  {Number_Literal, identifier, ( }

     First ( exp )			  = {  Number_Literal, identifier, ( }

     First ( exp' )			 = { + , - ,  *,  / , Epsilon }

     FIrst ( op )				=  { + , - ,  *,  / }

     First ( terminal )	 =  { identifier, Number_Literal }

     First ( compare )	 = { < , > , <= , >= , == , != }

     First ( op )			= { +,  - ,  *,  / } 

     

> ## Parsing Table

- **Parsing table**

(1)  < class-stmt > 		:: = class identifier { main () { < stmt > }}

(2) < stmt > 					::=  < while-stmt > < stmt > 

(3) < stmt > 					::=  < for-stmt > < stmt > 

(4) < stmt > 					::=   < if-stmt > < stmt > 

(5) < stmt > 					::=  < print-stmt > < stmt > 

(6) < stmt > 					::= comment < stmt > 

(7) < stmt >					::= identifier <_stmt > ;  < stmt > 

(8) < stmt > 				  ::=  keyword identifier < assign-stmt > ; < stmt > 

(9) <_stmt > 				  ::= =  < exp >

(10) <_stmt > 				  ::=  ++

(11) <_stmt > 				  ::=  --

(12) < print-stmt > 		::=  out.println (  < _print-stmt > 	

(13) < init-stmt > 			::=  keyword < assign-stmt > 

(14) < assign-stmt >		::= identifier = < exp >

(15)< comp_exp > 		  ::=  < exp > < compare > < exp > 

(16)< exp > 				  	::= < terminal > < exp' > 

(17) < exp > 				     ::=   ( exp ) < exp' >  

(18 ) < exp' > 					::=  < op > < terminal > < exp' > 

(19)< terminal> 	    	  ::=  identifier 

(20) < terminal> 				::= Number_Literal

(21) < for-stmt > 			 ::= for ( < init-stmt > ; < comp_exp > ; < assign-stmt > ) { < stmt > }

(22) < while-stmt >      	::= 	while ( < comp_exp > ) { < stmt > }

(23) < if-stmt > 				::= 	if ( <comp_exp> ) { < stmt > } <_if > 

(24) < _if > 		 			  ::=     else { < stmt > } 

(25) < _if > 		 			  ::=      Epsilon

(26)  < exp' > 					 ::=   Epsilon

(27) < compare > 			::=  < 

(28) < compare > 			::=   > 

(29) < compare > 			::=   <=

(30) < compare > 			::=   >= 

(31) < compare > 			::=  == 

(32) < compare > 			::=   != 

(33)< op > 					   ::= +

(34)< op > 					   ::=  - 

(35)< op > 					   ::=  *

(36)< op > 					   ::=  /

(37) <$>							::= < class-stmt > 

(38) < stmt > 					::= Epsilon

(39) < stmt > 					::= { < stmt > }

(40) < _print-stmt > 		::=  string_literal ) ;

(41) < _print-stmt > 		::=  identifier ) ;

- parsing table 1

| M[N, T]     | Class | while | for  | if   | out.println | Identifier | Comment | NumberLiteral |
| ----------- | ----- | ----- | ---- | ---- | ----------- | ---------- | ------- | ------------- |
| class-stmt  | (1)   |       |      |      |             |            |         |               |
| stmt        |       | (2)   | (3)  | (4)  | (5)         | (7)        | (6)     |               |
| _stmt       |       |       |      |      |             |            |         |               |
| init-stmt   |       |       |      |      |             |            |         |               |
| assign-stmt |       |       |      |      |             | (14)       |         |               |
| comp_exp    |       |       |      |      |             | (15)       |         | (15)          |
| exp         |       |       |      |      |             | (16)       |         | (16)          |
| exp'        |       |       |      |      |             |            |         |               |
| terminal    |       |       |      |      |             | (19)       |         | (20)          |
| compare     |       |       |      |      |             |            |         |               |
| op          |       |       |      |      |             |            |         |               |
| for-stmt    |       |       | (21) |      |             |            |         |               |
| while-stmt  |       | (22)  |      |      |             |            |         |               |
| if-stmt     |       |       |      | (23) |             |            |         |               |
| _if         |       | (25)  | (25) | (25) | (25)        | (25)       | (25)    |               |
| print-stmt  |       |       |      |      | (3)         |            |         |               |
| $           | (37)  |       |      |      |             |            |         |               |
| _print-stmt |       |       |      |      |             | (41)       |         |               |

- parsing table 2

| M[N, T]     | =    | ++   | --   | (    | )    | {    | }    | +    | -    | *    | /    |
| ----------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| class-stmt  |      |      |      |      |      |      |      |      |      |      |      |
| stmt        |      |      |      |      |      | (39) |      |      |      |      |      |
| _stmt       | (9)  | (10) | (11) |      |      |      |      |      |      |      |      |
| init-stmt   |      |      |      |      |      |      |      |      |      |      |      |
| assign-stmt |      |      |      |      |      |      |      |      |      |      |      |
| comp_exp    |      |      |      | (15) |      |      |      |      |      |      |      |
| exp         |      |      |      | (17) |      |      |      |      |      |      |      |
| exp'        |      |      |      |      | (26) |      |      | (18) | (18) | (18) | (18) |
| terminal    |      |      |      |      |      |      |      |      |      |      |      |
| compare     |      |      |      |      |      |      |      |      |      |      |      |
| op          |      |      |      |      |      |      |      | (33) | (34) | (35) | (36) |
| for-stmt    |      |      |      |      |      |      |      |      |      |      |      |
| while-stmt  |      |      |      |      |      |      |      |      |      |      |      |
| if-stmt     |      |      |      |      |      |      |      |      |      |      |      |
| _if         |      |      |      |      |      | (25) | (25) |      |      |      |      |
| print-stmt  |      |      |      |      |      |      |      |      |      |      |      |
| $           |      |      |      |      |      |      |      |      |      |      |      |
| _print-stmt |      |      |      |      |      |      |      |      |      |      |      |

 - parsing table 3

   | M[N, T]     | <    | \>   | <=   | \>=  | ==   | !=   | ;    | Else | $    | Keyword | String_literal |
   | ----------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ------- | -------------- |
   | class-stmt  |      |      |      |      |      |      |      |      |      |         |                |
   | stmt        |      |      |      |      |      |      |      |      |      | (8)     |                |
   | _stmt       |      |      |      |      |      |      |      |      |      |         |                |
   | init-stmt   |      |      |      |      |      |      |      |      |      | (13)    |                |
   | assign-stmt |      |      |      |      |      |      |      |      |      |         |                |
   | comp_exp    |      |      |      |      |      |      |      |      |      |         |                |
   | exp         |      |      |      |      |      |      |      |      |      |         |                |
   | exp'        | (26) | (26) | (26) | (26) | (26) | (26) | (26) |      |      |         |                |
   | terminal    |      |      |      |      |      |      |      |      |      |         |                |
   | compare     | <27> | <28> | <29> | <30> | <31> | <32> |      |      |      |         |                |
   | op          |      |      |      |      |      |      |      |      |      |         |                |
   | for-stmt    |      |      |      |      |      |      |      |      |      |         |                |
   | while-stmt  |      |      |      |      |      |      |      |      |      |         |                |
   | if-stmt     |      |      |      |      |      |      |      |      |      |         |                |
   | _if         |      |      |      |      |      |      |      | (24) |      | (25)    |                |
   | print-stmt  |      |      |      |      |      |      |      |      |      |         |                |
   | $           |      |      |      |      |      |      |      |      |      |         |                |
   | _print-stmt |      |      |      |      |      |      |      |      |      |         | (40)           |

