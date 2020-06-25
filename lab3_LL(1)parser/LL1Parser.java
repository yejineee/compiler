import java.util.ListIterator;
import java.util.LinkedList;
import java.util.ArrayList ;


public class LL1Parser {
  public static Pair p ; 
  public static State token ;
  public static String lex ;
  public static LinkedList<Pair> input ;
  public static LinkedList<Pair> stack = new LinkedList<Pair>() ; 


  public static boolean Parsing(LinkedList<Pair> list){
    System.out.println("---------- Parser Running ----------") ; 
    input = new LinkedList<Pair>(list) ;
    stack.add(new Pair("B", State.StackBottom)) ; 
    State stack_token  ; 
    String stack_lex = "" ; 
    while (!input.isEmpty() && !stack.isEmpty()) { 
      stack_token = stack.peekLast().token() ; 
      stack_lex = stack.peekLast().lex() ; 
      if(stack_token.ordinal() < State.Num_OF_NT.ordinal()){
      //System.out.println("----push action-----") ; 
        p = (Pair)input.peekFirst() ; 
        token = p.token() ; 
        lex = p.lex() ; 
        //System.out.println(String.format("=> %10s (%s): %10s ", stack_lex, stack_token.toString(), lex) ) ;
       
        if(stack_token == State.StackBottom && lex.equals("class")){
          //System.out.println("-class_stmt-") ; 
          class_stmt() ;
        }
        else if(stack_token == State.STMT){
          if(token == State.COMMENT){
            stack.pollLast() ; 
            //System.out.println("-stmt/comment-") ; 
            comment() ;
          }
          else if(token == State.RCB){
            stack.pollLast() ; 
            //System.out.println("-stmt/Epsilon-") ;
          }
          else if(token == State.LCB){
            //System.out.println("-stmt/ LCB->STMT-") ;
            stack.pollLast() ; 
            stack.add(new Pair("}", State.RCB)) ; 
            stack.add(new Pair(null, State.STMT)) ; 
            stack.add(new Pair("{", State.LCB)) ; 
          }
          else if(lex.equals("while") ){
            //System.out.println("-stmt/while-") ;
            stack.pollLast() ; 
            stack.add(new Pair(null, State.STMT)) ; 
            stack.add(new Pair(null, State.WHILE_STMT)) ; 
          }
          else if(lex.equals("for")){
            //System.out.println("-stmt/for-") ;
            stack.pollLast() ; 
            stack.add(new Pair(null, State.STMT)) ; 
            stack.add(new Pair(null, State.FOR_STMT)) ; 
          }
          else if(lex.equals("if")){
            //System.out.println("-stmt/if-") ;
            stack.pollLast() ; 
            stack.add(new Pair(null, State.STMT)) ; 
            stack.add(new Pair(null, State.IF_STMT)) ; 
          }
          else if(lex.equals("out.println")){
            //System.out.println("-stmt/print_stmt-") ;
            stack.pollLast() ; 
            stack.add(new Pair(null, State.STMT)) ; 
            stack.add(new Pair(null, State.PRINT_STMT)) ; 
          }
          else if(token == State.KEYWORD){
            stack.pollLast() ; 
            //System.out.println("_____STMT ( init-stmt ) -") ; 
            stack.add(new Pair(null, State.STMT)) ; 
            stack.add(new Pair(";", State.TERMINATOR)) ; 
            stack.add(new Pair(null, State.ASSIGN_STMT)) ; 
            stack.add(new Pair(null, State.KEYWORD)) ; 
          }
          else if(is_variable()){
            //System.out.println("-stmt/___stmt-") ;
            stack.pollLast() ; 
            stack.add(new Pair(null, State.STMT)) ; 
            stack.add(new Pair(";", State.TERMINATOR)) ; 
            stack.add(new Pair(null, State._STMT)) ; 
            stack.add(new Pair(null, State.IDENTIFIER)) ; 

          }
          else break ; //parsing error
        }
        else if(stack_token == State.FOR_STMT && lex.equals("for")){
          stack.pollLast() ; 
          //System.out.println("-for-stmt-") ; 
          for_stmt() ;
        }
        else if(stack_token == State.IF_STMT && lex.equals("if")){
          stack.pollLast() ; 
          //System.out.println("-if-stmt-") ; 
          if_stmt() ;
        }
        else if(stack_token == State.WHILE_STMT && lex.equals("while")){
          stack.pollLast() ; 
          //System.out.println("-while-stmt-") ; 
          while_stmt() ;
        }
        else if(stack_token == State._IF){
          if(lex.equals("else")){
            stack.pollLast() ; 
            //System.out.println("_____IF ( else ) -") ; 
            stack.add(new Pair(null, State.STMT)) ; 
            stack.add(new Pair("else", State.IDENTIFIER)) ; 
          }
          else if(lex.equals("{") || lex.equals("}") || lex.equals("while") ||lex.equals("for") ||
          lex.equals("if") ||lex.equals("out.println") ||
          is_variable() || token == State.KEYWORD || token == State.COMMENT){
            //System.out.println("_____IF ( Epsilon)-") ; 
            stack.pollLast() ; 
          }
          else break ; 
        }
        else if(stack_token == State._STMT){
          if(lex.equals("=")){
            stack.pollLast() ; 
            //System.out.println("_____STMT ( assign-stmt ) -") ; 
            stack.add(new Pair(null, State.EXP)) ; 
            stack.add(new Pair("=", State.OPERATOR)) ; 
          }
          else if(lex.equals("++")){
            stack.pollLast() ; 
            //System.out.println("_____STMT ( increment ) -") ; 
            stack.add(new Pair("++", State.OPERATOR)) ; 
          }
          else if(lex.equals("--")){
            stack.pollLast() ; 
            //System.out.println("_____STMT ( decrement ) -") ; 
            stack.add(new Pair("--", State.OPERATOR)) ; 
          }
          else break ; 
        }
        else if(stack_token == State.INIT_STMT && token == State.KEYWORD){
          stack.pollLast() ; 
          //System.out.println("-init-stmt-") ; 
          init_stmt() ;
        }
        else if(stack_token == State.ASSIGN_STMT && is_variable()){
          stack.pollLast() ; 
          //System.out.println("-assign-stmt-") ; 
          assign_stmt() ;
        }
        else if(stack_token == State.COMP_EXP){
          if(token == State.NUMBER_LITERAL || is_variable() || lex.equals("(")){
            stack.pollLast() ; 
             //System.out.println("-comp_exp-") ; 
             stack.add(new Pair(null, State.EXP)) ; 
             stack.add(new Pair(null, State.COMPARE)) ; 
             stack.add(new Pair(null, State.EXP)) ; 
          }
          else break ; 
        }
        else if(stack_token == State.PRINT_STMT && lex.equals("out.println")){
          stack.pollLast() ; 
          //System.out.println("-print_stmt-") ; 
          print_stmt() ;
        }
        else if(stack_token == State._PRINT_STMT ){
          if(token == State.STRING_LITERAL){
            stack.pollLast() ; 
            stack.add(new Pair(";", State.TERMINATOR)) ; 
            stack.add(new Pair(")", State.RP)) ; 
            stack.add(new Pair(null, State.STRING_LITERAL)) ; 
          }
          else if(is_variable()){
            stack.pollLast() ; 
            stack.add(new Pair(";", State.TERMINATOR)) ; 
            stack.add(new Pair(")", State.RP)) ; 
            stack.add(new Pair(null, State.IDENTIFIER)) ; 
          }
          else break ; 

        }
        else if(stack_token == State.EXP){
          if(token == State.NUMBER_LITERAL || is_variable()){
            stack.pollLast() ; 
            //System.out.println("-exp(terminal)-") ; 
            stack.add(new Pair(null, State._EXP)) ; 
            stack.add(new Pair(null, State.TERMINAL)) ; 
          }
          else if(lex.equals("(")){
            stack.pollLast() ; 
            //System.out.println("-exp(LP)-") ; 
            stack.add(new Pair(null, State._EXP)) ; 
            stack.add(new Pair(")", State.RP)) ; 
            stack.add(new Pair(null, State.EXP)) ; 
            stack.add(new Pair("(", State.LP)) ; 
          }
          else break ; 
        }
        else if(stack_token == State._EXP){
          if(lex.equals("+") || lex.equals("-") ||
          lex.equals("/") || lex.equals("*") ){
            stack.pollLast() ; 
            //System.out.println("_____exp(op)-") ; 
            stack.add(new Pair(null, State._EXP)) ; 
            stack.add(new Pair(null, State.TERMINAL)) ; 
            stack.add(new Pair(null, State.OP)) ; 
          }
          else if(lex.equals(")")|| lex.equals("<") || lex.equals(">") 
          || lex.equals("<=") || lex.equals(">=") || lex.equals("==") 
          || lex.equals("!=") || lex.equals(";")){
            stack.pollLast() ; 
            //System.out.println("__EXP/Epsilon-") ;
          }
          else break ; 
        }
        else if(stack_token == State.TERMINAL){
          if(is_variable()){
            //System.out.println("-terminal/identifier-") ;
            stack.pollLast() ; 
            stack.add(new Pair(null, State.IDENTIFIER)) ; 
          }
          else if(token == State.NUMBER_LITERAL){
            //System.out.println("-terminal/number-") ;
            stack.pollLast() ; 
            stack.add(new Pair(null, State.NUMBER_LITERAL)) ; 
          }
          else break ; 
        }
        else if(stack_token == State.OP && is_op(lex)){
          stack.pollLast() ; 
          if(lex.equals("+")) stack.add(new Pair("+", State.OPERATOR)) ; 
          else if(lex.equals("-")) stack.add(new Pair("-", State.OPERATOR)) ; 
          else if(lex.equals("*")) stack.add(new Pair("*", State.OPERATOR)) ; 
          else if(lex.equals("/")) stack.add(new Pair("/", State.OPERATOR)) ; 
        }
        else if(stack_token == State.COMPARE && 
        (lex.equals("<") || lex.equals(">") || lex.equals("<=") ||
        lex.equals(">=") || lex.equals("==") || lex.equals("!="))){
          stack.pollLast() ; 
          //System.out.println("-compare-") ; 
          compare() ; 
        }
        else break ; 

        //System.out.println("|"); 
      }
      else{
      //System.out.println("----test match-----") ; 
        get_next_if_exist() ; 
        //System.out.println(String.format("=> %10s(%s): %10s ", stack_lex, stack_token.toString(), lex) ) ;
        if(stack_token == token){
          if(stack_lex != null && !stack_lex.equals(lex)){
            //System.out.println("parsing error (1)") ; 
            break ; // parsing error
          }
          //System.out.println(String.format("%10s(%s):%10s -> match", stack_lex, stack_token.toString(), lex) ) ;
          stack.pollLast() ; 
        }
        else break ; // parsing error
      }
    }
    if(input.isEmpty() && !stack.isEmpty() && stack.peekLast().token() == State.StackBottom){
      System.out.println("___________Parsing Success____________" ) ; 
      return true ; 
    }
    //System.out.println(String.format("%10s:%10s -> not match", stack_lex, lex) ) ;
    System.out.println("___________Parsing Error!____________" ) ; 
    return false ; 
  }
  public static void compare(){
    if(lex.equals("<")) stack.add(new Pair("<", State.OPERATOR)) ; 
    else if(lex.equals(">")) stack.add(new Pair(">", State.OPERATOR)) ; 
    else if(lex.equals("<=")) stack.add(new Pair("<=", State.OPERATOR)) ; 
    else if(lex.equals(">=")) stack.add(new Pair(">=", State.OPERATOR)) ; 
    else if(lex.equals("==")) stack.add(new Pair("==", State.OPERATOR)) ; 
    else if(lex.equals("!=")) stack.add(new Pair("!=", State.OPERATOR)) ; 
  }
  public static void init_stmt(){
    stack.add(new Pair(null, State.ASSIGN_STMT)) ; 
    stack.add(new Pair(null, State.KEYWORD)) ; 
  }
  public static void assign_stmt(){
    stack.add(new Pair(null, State.EXP)) ; 
    stack.add(new Pair("=", State.OPERATOR)) ; 
    stack.add(new Pair(null, State.IDENTIFIER)) ; 
  }
  public static void if_stmt(){
    stack.add(new Pair(null, State._IF)) ; 
    stack.add(new Pair("}", State.RCB)) ; 
    stack.add(new Pair(null, State.STMT)) ; 
    stack.add(new Pair("{", State.LCB)) ; 
    stack.add(new Pair(")", State.RP)) ; 
    stack.add(new Pair(null, State.COMP_EXP)) ; 
    stack.add(new Pair("(", State.LP)) ; 
    stack.add(new Pair("if", State.IDENTIFIER)) ; 
  }
  public static void for_stmt(){
    stack.add(new Pair("}", State.RCB)) ; 
    stack.add(new Pair(null, State.STMT)) ; 
    stack.add(new Pair("{", State.LCB)) ; 
    stack.add(new Pair(")", State.RP)) ; 
    stack.add(new Pair(null, State.ASSIGN_STMT)) ; 
    stack.add(new Pair(";", State.TERMINATOR)) ; 
    stack.add(new Pair(null, State.COMP_EXP)) ; 
    stack.add(new Pair(";", State.TERMINATOR)) ; 
    stack.add(new Pair(null, State.INIT_STMT)) ; 
    stack.add(new Pair("(", State.LP)) ; 
    stack.add(new Pair("for", State.IDENTIFIER)) ; 
  }
  public static void while_stmt(){
    stack.add(new Pair("}", State.RCB)) ; 
    stack.add(new Pair(null, State.STMT)) ; 
    stack.add(new Pair("{", State.LCB)) ; 
    stack.add(new Pair(")", State.RP)) ; 
    stack.add(new Pair(null, State.COMP_EXP)) ; 
    stack.add(new Pair("(", State.LP)) ; 
    stack.add(new Pair("while", State.IDENTIFIER)) ; 
  }
  public static void class_stmt(){
    stack.add(new Pair("}", State.RCB)) ; 
    stack.add(new Pair("}", State.RCB)) ; 
    stack.add(new Pair(null, State.STMT)) ; 
    stack.add(new Pair("{", State.LCB)) ; 
    stack.add(new Pair(")", State.RP)) ; 
    stack.add(new Pair("(", State.LP)) ; 
    stack.add(new Pair("main", State.IDENTIFIER)) ; 
    stack.add(new Pair("{", State.LCB)) ; 
    stack.add(new Pair(null, State.IDENTIFIER)) ; 
    stack.add(new Pair("class", State.IDENTIFIER)) ; 
  }

  public static void print_stmt(){ 
    stack.add(new Pair(null, State._PRINT_STMT)) ; 
    stack.add(new Pair("(", State.LP)) ; 
    stack.add(new Pair("out.println", State.IDENTIFIER)) ; 
  }
  public static void comment(){
    stack.add(new Pair(null, State.STMT)) ; 
    stack.add(new Pair(null, State.COMMENT)) ; 
  } 
 



  public static boolean get_next_if_exist(){
    if(!input.isEmpty()){
      Pair p = (Pair)input.pollFirst() ; 
      token = p.token() ; 
      lex = p.lex() ; 
      return true ; 
    }
    return false ; 
  }
  public static boolean is_variable(){
    if(token != State.IDENTIFIER) return false ; 
    if(lex.equals("out.println")) return false ; 
    return true ; 
  }

  public static boolean is_op(String lex){
    if(lex.equals("+") || lex.equals("-") || lex.equals("*") || lex.equals("/"))
      return true ; 
    return false ; 
  }

}