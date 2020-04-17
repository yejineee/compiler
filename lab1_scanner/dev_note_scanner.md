

# Developer Note of Java-like Language Scanner 

Yejin Yang

> ### Design of Scanner

1. 맨 처음의 character를 하나의 DFA를 따라가게 하며 state를 결정하였다. 

   - dfa

     ![dfa](https://user-images.githubusercontent.com/43772082/79598667-52b84a00-811f-11ea-96a2-c120ade95267.jpg)





2. state는 enum으로 두어서, 마지막에 최종 결정된 state를 출력하였다.

![state](https://user-images.githubusercontent.com/43772082/79599083-e38f2580-811f-11ea-8bfc-91ec0e356cda.png)

3. out.println 확인하는 방법

   identifier를 확인하는 절차에서 "."이 나오는 경우, 우선 lexeme을 저장해둔다. 후에 그 lexeme이 out.println과 일치하는지 확인하는 절차를 거친다. 

   

4. Keyword와 Identifier는 구분하지 않고, Identifier로만 결정하였다.



>  ### 어려웠던 점



1. token delimiter의 개념

   illegal identifier의 경우 illegal한 characger가 인식되는 경우 어디까지가 illegal identifier의 lexeme인지 어떻게 확인해야 할지 몰랐었다.  또한 $_*Time0의 경우 *가 operator로 인식될 수 있는 것은 아닌가하는 의문이 들었다. 

   

   이 고민들은 *token delimiter*의 개념을 이해함으로써 해결할 수 있었다.id나 symbol을 나누어주는 역할을 token delimiter한다. 따라서 token delimiter가 아닌 경우 계속해서 character를 읽어들여서 하나의 lexeme으로 인식해야 했다. 

   

   \*는 token delimiter가 아니기에 $\_*Time0는 하나의 lexeme이다. 이 lexeme은 identifier rule에 어긋나므로, illegal identifier로 인식되어야 한다. 

   

   token delimiter로는 다음을 사용하였다.

   ```c
   delim = {' ','\n','(', '{', '}', ')', ';', '+', '-'} 
   ```

   "+"와 "-"를 token delimiter로 넣은 이유는 다음과 같은 경우에 _iValue$를 identifier로 인식해야 했기 때문이다. identifier 바로 뒤에 "++"가 온 경우와 마찬가지로 "--"가 오는 경우도 있을 것이라 생각하여 "-" 또한 token delimiter로 두었다. 

<img width="144" alt="value" src="https://user-images.githubusercontent.com/43772082/79600037-73819f00-8121-11ea-9995-d71bed5fd0b6.png">

​			2. 어떤 DFA를 사용해야 하는가

​			lexical unit마다 dfa를 두어서 state를 결정해야 할지, 전체 lexical unit을 결정할 수 있는 하나의 dfa를 사용해서 	state를 결정해야 할지 고민이었다. 처음엔 각각의 경우마다 dfa를 두었고, 다시 만들 때는 하나의 dfa를 사용하였다. 걱정과는 달리 하나의 dfa를 사용한 스캐너가 훨씬 더 직관적으로 state를 결정하였다. 

