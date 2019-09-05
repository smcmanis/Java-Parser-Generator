import java.util.*;
import java.io.IOException;

public class LexicalAnalyser {

	private enum Symbol {

		ZERO, NONZERO, OPERATOR, DECIMAL, WHITESPACE, NONE;

	} 

	private static Map<Character, Map<Symbol, Character>> transitionTable; 			//for transition function
	static {
		// Any letter not in the alphabet gets the symbol NONE - causing an expression exception
		Map<Symbol, Character> q_a = Map.of(
			Symbol.DECIMAL, 'f',
			Symbol.WHITESPACE, 'a',
			Symbol.OPERATOR, 'g', 
			Symbol.ZERO, 'b', 
			Symbol.NONZERO, 'c',
			Symbol.NONE, 'g'
		);

		Map<Symbol, Character> q_b = Map.of(
			Symbol.DECIMAL, 'd',
			Symbol.WHITESPACE, 'e',
			Symbol.OPERATOR, 'a', 
			Symbol.ZERO, 'f', 
			Symbol.NONZERO, 'f',
			Symbol.NONE, 'g'
		);

		Map<Symbol, Character> q_d = Map.of(
			Symbol.DECIMAL, 'f',
			Symbol.WHITESPACE, 'f',
			Symbol.OPERATOR, 'f', 
			Symbol.ZERO, 'c', 
			Symbol.NONZERO, 'c',
			Symbol.NONE, 'g'
		);

		Map<Symbol, Character> q_c = Map.of(
			Symbol.DECIMAL, 'f',
			Symbol.WHITESPACE, 'e',
			Symbol.OPERATOR, 'a', 
			Symbol.ZERO, 'c', 
			Symbol.NONZERO, 'c',
			Symbol.NONE, 'g'
		);

		Map<Symbol, Character> q_e = Map.of(
			Symbol.DECIMAL, 'f',
			Symbol.WHITESPACE, 'e',
			Symbol.OPERATOR, 'a', 
			Symbol.ZERO, 'g', 
			Symbol.NONZERO, 'g',
			Symbol.NONE, 'g'
		);

		// Don't really need these last 2 maps, since immediately throw exception
		// But good to keep for conceptual reasons (represents the DFA sink states)
		Map<Symbol, Character> q_f = Map.of(
			Symbol.DECIMAL, 'f',
			Symbol.WHITESPACE, 'f',
			Symbol.OPERATOR, 'f', 
			Symbol.ZERO, 'f', 
			Symbol.NONZERO, 'f',
			Symbol.NONE, 'f' // debatable if it should be 6. An expression containing a letter not in aplhabet is invalid
		);

		Map<Symbol, Character> q_g = Map.of(
			Symbol.DECIMAL, 'g',
			Symbol.WHITESPACE, 'g',
			Symbol.OPERATOR, 'g', 
			Symbol.ZERO, 'g', 
			Symbol.NONZERO, 'g',
			Symbol.NONE, 'g'
		);


		transitionTable = Map.of(
			'a', q_a,
			'b', q_b,
			'd', q_d,
			'c', q_c,
			'e', q_e,
			'f', q_f,
			'g', q_g
		);
	}
	

	public static List<Token> analyse(String input) throws NumberException, ExpressionException {

		List<Token> tokens = new ArrayList<Token>();
		char state = 'a';
		String number = ""; 	// To store number substring

		for (int i = 0; i < input.length(); ++i) {

			char letter = input.charAt(i);
			state = transitionTable.get(state).get(typeOf(letter));

			switch(state) {
			case 'b':
			case 'd':
			case 'c':
				number += letter;
				break;
				// if at end of expression comtinue to case 0 to create token for number
				// if (number.length() > 0) tokens.add(new Token(Double.parseDouble(number)));
			case 'a': 
				// length > 0 indicates a number, decimal indicates it's a double
				if (number.length() > 0) tokens.add(new Token(Double.parseDouble(number)));
				number = "";
				if (typeOf(letter) == Symbol.OPERATOR) tokens.add(new Token(Token.typeOf(letter)));
				break;
			default: // Do nothing for states e, f, and g, or unexpected input. The states will take care of themselves
				break;
				
			}
		}

		if (number.length() > 0) tokens.add(new Token(Double.parseDouble(number)));

		if (state == 'd' | state == 'f') throw new NumberException();	// "0." is an invalid number
		if (state == 'g') throw new ExpressionException();

		return tokens;

	}



	public static Symbol typeOf(char symbol) {
		switch (symbol) {
		case '.':
			return Symbol.DECIMAL;
		case ' ':
			return Symbol.WHITESPACE;
		case '+':
		case '-':
		case '*':
		case '/':
			return Symbol.OPERATOR;
		case '0':
			return Symbol.ZERO;
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return Symbol.NONZERO;
		default:
			return Symbol.NONE;
		}
	}

	public static void main(String[] args) {


		List<Token> result = new ArrayList<Token>();
		try {
			result = LexicalAnalyser.analyse(getInput());
			System.out.print(resultToString(result));
		} catch (IOException e) {
			System.out.println("General I/O exception: " + e.getMessage());
		} catch (NumberException | ExpressionException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static String getInput() throws IOException {

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter mathematical expression: ");
			return scanner.nextLine();
		}

	}

	public static String resultToString(List<Token> result) {

		StringBuilder sb = new StringBuilder();

		sb.append("Token list: ");
		for (Token t: result) sb.append(t + " ");
		sb.append("\n");

		return sb.toString();

	}

}
