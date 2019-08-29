import java.util.*;
import java.io.IOException;


// import javax.management.ImmutableDescriptor;

public class LexicalAnalyser {

	private enum Symbol {

		ZERO, NONZERO, OPERATOR, DECIMAL, WHITESPACE, NONE;

	} 

	private static Map<Integer, Map<Symbol, Integer>> transitionTable; 			//for transition function
	static {
		Map<Symbol, Integer> q0 = Map.of(
			Symbol.DECIMAL, 5,
			Symbol.WHITESPACE, 0,
			Symbol.OPERATOR, 6, 
			Symbol.ZERO, 1, 
			Symbol.NONZERO, 3,
			Symbol.NONE, 6
		);

		Map<Symbol, Integer> q1 = Map.of(
			Symbol.DECIMAL, 2,
			Symbol.WHITESPACE, 4,
			Symbol.OPERATOR, 0, 
			Symbol.ZERO, 5, 
			Symbol.NONZERO, 5,
			Symbol.NONE, 6
		);

		Map<Symbol, Integer> q2 = Map.of(
			Symbol.DECIMAL, 5,
			Symbol.WHITESPACE, 5,
			Symbol.OPERATOR, 5, 
			Symbol.ZERO, 3, 
			Symbol.NONZERO, 3,
			Symbol.NONE, 6
		);

		Map<Symbol, Integer> q3 = Map.of(
			Symbol.DECIMAL, 5,
			Symbol.WHITESPACE, 4,
			Symbol.OPERATOR, 0, 
			Symbol.ZERO, 3, 
			Symbol.NONZERO, 3,
			Symbol.NONE, 6
		);

		Map<Symbol, Integer> q4 = Map.of(
			Symbol.DECIMAL, 5,
			Symbol.WHITESPACE, 4,
			Symbol.OPERATOR, 0, 
			Symbol.ZERO, 6, 
			Symbol.NONZERO, 6,
			Symbol.NONE, 6
		);

		// Don't really need these last 2 maps, since immediately throw exception
		Map<Symbol, Integer> q5 = Map.of(
			Symbol.DECIMAL, 5,
			Symbol.WHITESPACE, 5,
			Symbol.OPERATOR, 5, 
			Symbol.ZERO, 5, 
			Symbol.NONZERO, 5,
			Symbol.NONE, 5
		);

		Map<Symbol, Integer> q6 = Map.of(
			Symbol.DECIMAL, 6,
			Symbol.WHITESPACE, 6,
			Symbol.OPERATOR, 6, 
			Symbol.ZERO, 6, 
			Symbol.NONZERO, 6,
			Symbol.NONE, 6
		);


		transitionTable = Map.of(
			0, q0,
			1, q1,
			2, q2,
			3, q3,
			4, q4,
			5, q5,
			6, q6
		);
	}
	

	public static List<Token> analyse(String input) throws NumberException, ExpressionException {

		List<Token> tokens = new ArrayList<Token>();
		int state = 0;
		String number = ""; 	// To store number substring (primarily for identifying doubles)

		for (int i = 0; i < input.length(); ++i) {

			char letter = input.charAt(i);
			state = transitionTable.get(state).get(typeOf(letter));

			switch(state) {
				case 1:
				case 2:
				case 3:
					number += letter;
					// if at end of expression comtinue to case 0 to create token for number
					if (i != input.length() - 1) break;
				case 0: 
					// length > 0 indicates a number, decimal indicates it's a double
					if (number.length() > 0 && number.indexOf('.') == -1) 
						tokens.add(new Token(Integer.parseInt(number)));
					else if (number.length() > 2) // valid double has at least 3 characters
						tokens.add(new Token(Double.parseDouble(number)));
					number = "";

					if (typeOf(letter) == Symbol.OPERATOR)
						tokens.add(new Token(Token.typeOf(letter)));
					break;
				case 4:
					break;
				case 5:
					throw new NumberException();
				case 6:
					throw new ExpressionException();
				
			}
		}

		// IS THIS AN FA APPROACH? Maybe add an extra state 
		if (state == 2) throw new NumberException();
		if (state != 3 && state != 1) throw new ExpressionException();

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
