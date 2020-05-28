import java.util.Scanner ;
import java.util.ListIterator;
import java.io.* ;

public class Main {
  public static void main(String [] args){
    System.out.println("Enter your java file path : ") ;  
    Scanner in = new Scanner(System.in) ;
    String filepath = in.next() ; 

    JavaScanner jscanner = new JavaScanner() ; 
    if(!jscanner.ScanningCode(filepath)){
      return ; 
    } 
    
    JavaParser jparser = new JavaParser() ; 
    if(!jparser.Parsing(jscanner.lex_list)){
      return ; 
    }

    in.close() ; 
  }
}