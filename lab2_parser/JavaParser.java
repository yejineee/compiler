import java.util.ListIterator;
import java.util.LinkedList;

public class JavaParser {

  public static ListIterator it ; 

  public static boolean Parsing(LinkedList lex_list){
    System.out.println("---------- Parser Running ----------") ; 
    it = lex_list.listIterator() ; 
    while (it.hasNext()) {
      Pair pair = (Pair)it.next() ; 
      State token = pair.token() ; 
      String lex = pair.lex() ; 

      if(lex.equals("while")){
        if(!while_stmt()){
          return false ; 
        }
      }
      else if(lex.equals("for")){
        if(!for_stmt()){
          return false ; 
        }
      }
    }
    return true ; 
  }
  // public static boolean for_stmt(){
  //   System.out.println("____for stmt____") ; 
  //   LinkedList<Pair> line = new LinkedList<>();
  //   Pair p ; 
  //   State token ;
  //   String lex ;
  //   if(it.hasNext()){
  //     p = (Pair)it.next() ; 
  //     token = p.token() ; 
  //     lex = p.lex() ; 
  //     if(token == State.LP){
  //       while(it.hasNext()){
  //         p = (Pair)it.next() ; 
  //         lex = p.lex() ;  
  //         token = p.token() ; 
  //         if(token != State.TERMINATOR){
  //           line.add(p) ;
  //         }
  //         else{
  //           if(comp_exp(line)){ 
  //             return true ; 
  //           }
  //           else break ;  
  //         }
  //       }
  //     }
  //   }
  //   print_error(line) ; 
  //   return false ; 
  // }
  public static boolean for_init(){
    return false ;
  }
  public static boolean while_stmt(){
    System.out.println("____while stmt____") ; 
    LinkedList<Pair> line = new LinkedList<>();
    Pair p ; 
    State token ;
    String lex ;
    if(it.hasNext()){
      p = (Pair)it.next() ; 
      token = p.token() ; 
      lex = p.lex() ; 
      if(token == State.LP){
        while(it.hasNext()){
          p = (Pair)it.next() ; 
          lex = p.lex() ;  
          token = p.token() ; 
          if(token != State.RP){
            line.add(p) ;
          }
          else{
            if(comp_exp(line)){ 
              return true ; 
            }
            else break ;  
          }
        }
      }
    }
    print_error(line) ; 
    return false ; 
  }
  public static boolean comp_exp(LinkedList<Pair> line){
    ListIterator it = line.listIterator() ; 
    Pair p ; 
    State token ;
    String lex ;
    System.out.println("\t____comp_exp____") ; 
    if(it.hasNext()){
      p = (Pair)it.next() ; 
      token = p.token() ; 
      lex = p.lex() ; 
      System.out.println(String.format("\t%-10s %10s", lex, token)) ; 
      if(!(variable(p) || token == State.NUMBER_LITERAL)){
        return false ; 
      }
      if(it.hasNext()){
        p = (Pair)it.next() ; 
        token = p.token() ; 
        lex = p.lex() ; 
        System.out.println(String.format("\t%-10s %10s", lex, token)) ; 
        if(!compare(p)){
          return false; 
        }
        if(it.hasNext()){
          p = (Pair)it.next() ; 
          token = p.token() ; 
          lex = p.lex() ; 
          System.out.println(String.format("\t%-10s %10s", lex, token)) ; 
          if(!(variable(p) || token == State.NUMBER_LITERAL)){
            return false ; 
          }
          return true ; 
        }
      }
    }
    return false ; 
  }

  public static boolean compare(Pair p){
    if(p.token() != State.OPERATOR) return false ; 
    String lex = p.lex() ; 
    if(lex.equals("<") || lex.equals(">") || lex.equals("<=") ||
    lex.equals(">=") || lex.equals("==") || lex.equals("!="))
      return true ;
    return false ;
  }
  public static boolean variable(Pair p){
    if(p.token() != State.IDENTIFIER) return false ; 
    if(p.lex().equals("out.println")) return false ; 
    return true ; 
  }
  
  public static void print_error(LinkedList<Pair> line){
    ListIterator it = line.listIterator() ; 
    while(it.hasNext()){
      System.out.print(String.format("%s ", ((Pair)it.next()).lex())) ; 
    }
    System.out.println("\nParsing Error") ; 
  }
  
}