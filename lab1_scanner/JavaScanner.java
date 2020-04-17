import java.util.Scanner ;
import java.io.* ;

enum State{
	INVALID, 
	LCB, RCB, LP, RP, TERMINATOR, 
	NUMBER_LITERAL, STRING_LITERAL, 
	IDENTIFIER, COMMENT, OPERATOR ; 
}

public class JavaScanner
{
	public static String file = "" ;
	public static String[] msg = {
		"Illegal Identifier\n\nStop Scanning",
		"Left Curly Brace",
		"Right Curly Brace",
		"Left Parenthesis",
		"Right Parenthesis",
		"terminator",
		"Number Literal",
		"String Literal",
		"Identifier",
		"Comment",
		"Operator Symbol"
	};

	public static boolean check_opln = false ; 
	public static State state = null ; 
	public static int idx = 0 ;
	public static String lex = "" ; 
	public static char c ;
	public static char[] delim = {' ','\n','(', '{', '}', ')', ';', '+', '-'} ; 
	public static void main(String [] args)
	{
    System.out.println("Enter your java file path : ") ;  
    Scanner in = new Scanner(System.in) ;
    String filepath = in.next() ; 

		try {
			Scanner s = new Scanner(new File(filepath)) ;
			while (s.hasNextLine()) {
				file += s.nextLine() + "\n"; 
			}
			while(!file.isEmpty()){
				check_opln = false ; 
				state = null ; 
				idx = 0 ;
				lex = "" ; 
				c = file.charAt(idx) ; 
				if(c == ' ' || c == '\n'){
					file = file.substring(1, file.length()) ; 
					continue ; 
				}

				if(c == '{'){
					state = State.LCB ; 
					lex = "{" ; 
				}
				else if(c == '}'){
					state = State.RCB ; 
					lex = "}" ; 
				}
				else if(c == '('){
					state = State.LP ;
					lex = "(" ; 
				}
				else if(c == ')'){
					state = State.RP ;
					lex = ")" ; 
				}
				else if(c == ';'){
					state = State.TERMINATOR ;
					lex = ";" ; 
				}
				else if(Character.isDigit(c)){
					check_number_literal();
				}
				else if(c == '_' || c == '$' || Character.isLetter(c)){
					check_identifier();
				}
				else if(c == '/'){
					check_comment_or_operator();
				}
				else if(c == '\"'){
					check_string_literal();
				}
				else if(c == '+' || c == '-'){
					check_signeNumber_or_operator(c);
				}
				else if(c == '!' || c == '*' || c == '%' ||
				c == '=' || c == '>' || c == '<'
				|| c == '|' || c == '&'){
					check_operator() ; 
				}
				else{
					state = State.INVALID ; 
					lex += Character.toString(c) ; 
					idx++ ; 
					while(idx < file.length()){
						c = file.charAt(idx) ; 
						if(is_delim(c)) break ; 
						lex += Character.toString(c) ; 
						idx++ ; 
					}
				}
				
				if(check_opln){
					int i = 0 ; 
					char[] opln = "out.println".toCharArray() ; 
					boolean is_opln = true ; 

					if(lex.length() == opln.length){
						for(int x = 0 ; x < opln.length ; x++){
							if(lex.charAt(i) != opln[x]){
								is_opln = false ; 
								break; 
							}
							i++ ; 
						}
						if(is_opln){
							state = State.IDENTIFIER ; 
						}
					}
				}

				if(!lex.isEmpty()){
					file = file.substring(lex.length(), file.length()) ;
					System.out.println(String.format("%-15s%28s", lex, msg[state.ordinal()])) ; 
					if(state == State.INVALID){
						break ; 
					}
				}

				
			}
			s.close() ; 
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found") ;
			in.close() ; 
			return ;
		}

		in.close() ; 
	}


	public static boolean is_delim(char c){
		for(int i = 0 ; i < delim.length ; i++){
			if(c == delim[i]){
				return true ; 
			}
		}
		return false ; 
	}


	public static void check_operator(){
		state = State.OPERATOR ; 
		lex += Character.toString(c) ; 
		idx++ ; 
		if(idx < file.length()){
			if(c == '<'){
				c = file.charAt(idx) ; 
				if(c == '<' || c == '>' || c == '='){
					lex += Character.toString(c) ; 
				}
			}
			else if(c == '>'){
				c = file.charAt(idx) ; 
				if(c == '>' || c == '='){
					lex += Character.toString(c) ; 
				}
			}
			else if(c == '&'){
				c = file.charAt(idx) ; 
				if(c == '&' || c == '='){
					lex += Character.toString(c) ; 
				}
			}
			else if(c == '|'){
				c = file.charAt(idx) ; 
				if(c == '|' || c == '='){
					lex += Character.toString(c) ; 
				}
			}
			else{
				c = file.charAt(idx) ; 
				if(c == '='){
					lex += Character.toString(c) ; 
				}
			}
		}
	}


	public static void check_signeNumber_or_operator(char op){
		state = State.OPERATOR ; 
		lex += Character.toString(c) ; 
		idx++ ; 
		if(idx < file.length()){
			c = file.charAt(idx) ; 
			if(c == op || c == '='){
				lex += Character.toString(c) ; 
			}
			else if(Character.isDigit(c)){
				check_number_literal();
			}
		}
	}


	public static void check_string_literal(){
		state = State.INVALID ; 
		lex += Character.toString(c) ; 
		idx++ ; 
		while(idx < file.length()){
			c = file.charAt(idx) ; 
			lex += Character.toString(c) ; 
			if(c == '\"'){
				state = State.STRING_LITERAL ; 
				break ; 
			}
			idx++ ; 
		}
	}

	
	public static void check_comment_or_operator(){
		lex += Character.toString(c) ; 
		idx++ ; 
		if(idx < file.length()){
			c = file.charAt(idx) ; 
			if(is_delim(c)){
				state = State.OPERATOR ; 
			}
			else if(c == '/'){
				state = State.COMMENT ; 
				lex += Character.toString(c) ; 
				idx++ ; 
				while(idx < file.length()){
					c = file.charAt(idx) ; 
					if(c == '\n'){
						break ; 
					}
					lex += Character.toString(c) ; 
					idx++ ; 
				}
			}
			else if(c == '='){
				lex += Character.toString(c) ; 
				state = State.OPERATOR ; 
			}
			else{
				state = State.INVALID ; 
				lex += Character.toString(c) ; 
				idx++ ; 
				while(idx < file.length()){
					c = file.charAt(idx) ; 
					if(c == ' ' || c == '\n') return ; 
					lex += Character.toString(c) ; 
					idx++ ; 
				}
			}
		}
	}
	public static void check_identifier(){
		state = State.IDENTIFIER ; 
		lex += Character.toString(c) ; 
		idx++ ; 
		while(idx < file.length()){
			c = file.charAt(idx) ; 
			if(is_delim(c)){
				break ; 
			}
			else if(c == '_' || c == '$' || Character.isLetter(c) || Character.isDigit(c)){
				lex += Character.toString(c) ;
			}
			else{
				state = State.INVALID ; 
				lex += Character.toString(c) ; 
				if(c == '.'){
					check_opln = true ; 
				}
			}
			idx++ ; 
		}

		if(lex.length() == 1 && !lex.equals("$")){
			state = State.INVALID ; 
		}
	}
	public static void check_number_literal(){
		int dot_cnt = 0 ;
		state = State.NUMBER_LITERAL ; 

		do{
			lex += Character.toString(c) ;
			idx++ ; 
			if(idx < file.length()){
				c = file.charAt(idx) ; 
				if(Character.isDigit(c)){
					continue ; 
				}
				else if(is_delim(c)){
					break ; 
				}
				else if(c == '.'){
					if(dot_cnt > 0){
						state = State.INVALID ; 
						break ; 
					}
					dot_cnt++ ; 
				}
				else{
					state = State.INVALID ; 
					lex += Character.toString(c) ; 
					idx++ ; 
					while(idx < file.length()){
						c = file.charAt(idx) ; 
						if(is_delim(c)) return ; 
						lex += Character.toString(c) ; 
						idx++ ; 
					}
				}
			}
			else break ; 
		}while(true) ; 

		if(lex.charAt(lex.length()-1) == '.'){
			state = State.INVALID ;
		}
	}

}