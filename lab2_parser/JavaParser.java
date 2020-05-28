import java.util.ListIterator;
import java.util.LinkedList;

public class JavaParser {
  public static String line = "" ; 
  public static Pair p ; 
  public static State token ;
  public static String lex ;
  public static LinkedList<Pair> lex_list ;

  public static boolean Parsing(LinkedList<Pair> list){
    System.out.println("---------- Parser Running ----------") ; 
    lex_list = new LinkedList<Pair>(list) ;
    while (!lex_list.isEmpty()) {
      line = "" ; 
      get_next_if_exist() ; 
      if(lex.equals("while")){
        if(!while_stmt()){
          print_error(line) ; 
          return false ; 
        }
      }
      else if(lex.equals("for")){
        if(!for_stmt()){
          print_error(line) ; 
          return false ; 
        }
      }
      else if(lex.equals("if")){
        if(!if_stmt()){
          print_error(line) ; 
          return false ; 
        }
      }
    }
    System.out.println("___________Parsing Success____________" ) ; 
    return true ; 
  }

  public static boolean for_stmt(){
    //System.out.println("____for stmt____") ; 
    if(get_next_if_exist() && token == State.LP){
      if(for_init()){
        if(get_next_if_exist() && token == State.TERMINATOR){
          if(comp_exp()){
            if(get_next_if_exist() && token == State.TERMINATOR){
              if(for_update()){
                if(get_next_if_exist() && token == State.RP){
                  if(get_next_if_exist() && token == State.LCB){
                    if(stmt()){
                        System.out.println("for statement Parsing OK") ; 
                        line = "" ; 
                      return true; 
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return false ; 
  }
  public static boolean for_update(){
    if(get_next_if_exist() && variable()){
      if(get_next_if_exist()){
        if(lex.equals("=")){
          if(exp()){
            return true ; 
          }
        }
        if(lex.equals("++") || lex.equals("--")){
          return true ; 
        }
      }
    }
    return false ; 
  }
  public static boolean if_stmt(){
    line = "" ; 
    if(get_next_if_exist() && token == State.LP){
      if(comp_exp()){
        if(get_next_if_exist() && token == State.RP){
          if(get_next_if_exist() && token == State.LCB){
            if(stmt()){
              line = "" ; 
              if(!lex_list.isEmpty() && lex_list.get(0).token() == State.IDENTIFIER){
                try{
                  if(lex_list.get(1).lex().equals("if") || lex_list.get(1).lex().equals("{")){
                    if(get_next_if_exist()){
                      if(!lex.equals("else")) return false ; 
                      if(get_next_if_exist()){
                        if(lex.equals("if") || lex.equals("{")){
                          if(stmt()){
                            System.out.println("if statement Parsing OK") ; 
                            line = "" ; 
                            return true ; 
                          } 
                        }
                      }
                    }
                  }
                }catch(IndexOutOfBoundsException e){
                  return false ; 
                }
              }
              System.out.println("if statement Parsing OK") ; 
              line = "" ; 

              return true ; 
            }
          }
        }
      }
    }
    return false ; 
  }
  public static boolean stmt(){
    while(!lex_list.isEmpty()){
      get_next_if_exist() ;
      if(lex.equals("for")){
        if(!for_stmt()){
          return false ; 
        }
      }
      else if(lex.equals("while")){
        if(!while_stmt()){
          return false ; 
        }
      }
      else if(lex.equals("if")){
        if(!if_stmt()){
          return false ; 
        }
      }
      else if(lex.equals("}")){
        return true ; 
      }
    }
    return false ; 
  }

  public static boolean for_init(){
    if(get_next_if_exist() && variable()){
      if(get_next_if_exist() && variable()){
        if(get_next_if_exist() && lex.equals("=")){
          if(exp()){
            return true ; 
          }
        }
      }
    }
    return false ; 
  }
  public static boolean while_stmt(){
    line = "" ; 
    if(get_next_if_exist()){
      if(token == State.LP){
        if(comp_exp()){
          if(get_next_if_exist() && token == State.RP ){
            if(get_next_if_exist() && token == State.LCB){
              if(stmt()){
                System.out.println("while statement Parsing OK") ; 
                line = "" ; 
                return true ; 
              }
            }
          }
        }
      }
    }
    return false ; 
  }
  public static boolean comp_exp(){
    if(exp()){
      if(get_next_if_exist()){
        if(compare()){
          if(exp()){
            return true ; 
          }
        }
      }
    }
    return false ; 
  }
  public static boolean get_next_if_exist(){
    if(!lex_list.isEmpty()){
      p = (Pair)lex_list.pollFirst() ; 
      token = p.token() ; 
      lex = p.lex() ; 
      line += " " + lex ; 
      return true ; 
    }
    return false ; 
  }
  public static boolean compare(){
    if(token != State.OPERATOR) return false ;  
    if(lex.equals("<") || lex.equals(">") || lex.equals("<=") ||
    lex.equals(">=") || lex.equals("==") || lex.equals("!="))
      return true ;
    return false ;
  }
  public static boolean variable(){
    if(token != State.IDENTIFIER) return false ; 
    if(lex.equals("out.println")) return false ; 
    return true ; 
  }
  public static boolean exp(){
    if(get_next_if_exist()){
      if(token == State.LP){
        if(exp()){
          if(get_next_if_exist() && token == State.RP){
            return true ; 
          }
        }
      }
      else if(variable() || token == State.NUMBER_LITERAL){
        if(get_next_if_exist()){
          if(op()){
            if(exp()){
              return true ; 
            }
          }
          else{
            lex_list.addFirst(p) ;  
            line = line.substring(0, line.length()-2) ; 
            return true ; 
          }
        }
      }
    }
    return false ;
  }


  public static boolean op(){
    if(lex.equals("+") || lex.equals("-") || lex.equals("*") || lex.equals("/"))
      return true ; 
    return false ; 
  }

  public static void print_error(String line){
    System.out.println("___________Parsing Error____________" ) ; 
    System.out.println(line) ; 
  }
}