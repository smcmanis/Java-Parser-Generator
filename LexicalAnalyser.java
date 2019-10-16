import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LexicalAnalyser {

    private enum LetterType {
        ALPHA, LITERAL, SSYMBOL, LASYMBOL, NUMBER, SPECIAL, SPACE, NONE,
    }

    private static final HashSet<LetterType> LetterTypes = new HashSet<LetterType>() {{
        add(LetterType.ALPHA);
        add(LetterType.LITERAL);
        add(LetterType.SSYMBOL);
        add(LetterType.LASYMBOL);
        add(LetterType.NUMBER);
        add(LetterType.SPECIAL);
        add(LetterType.SPACE);
        add(LetterType.NONE);
    }};
    

    private static final HashMap<String, Token.TokenType> reservedWordTokens = new HashMap<String, Token.TokenType>() {{
        put("public", Token.TokenType.PUBLIC);
        put("class", Token.TokenType.CLASS);
        put("static", Token.TokenType.STATIC);
        put("void", Token.TokenType.VOID);
        put("main", Token.TokenType.MAIN);
        put("args", Token.TokenType.ARGS);
        put("int", Token.TokenType.TYPE);
        put("boolean", Token.TokenType.TYPE);
        put("char", Token.TokenType.TYPE);
        put("String[]", Token.TokenType.STRINGARR);
        put("System.out.println", Token.TokenType.PRINT);
        put("while", Token.TokenType.WHILE);
        put("for", Token.TokenType.FOR);
        put("if", Token.TokenType.IF);
        put("else", Token.TokenType.ELSE);
        put("true", Token.TokenType.TRUE);
        put("false", Token.TokenType.FALSE);
    }};

    private static final HashMap<Character, Token.TokenType> singleSymbolTokens = new HashMap<Character, Token.TokenType>() {{
        put('+', Token.TokenType.PLUS);
        put('-', Token.TokenType.MINUS);
        put('*', Token.TokenType.TIMES);
        put('/', Token.TokenType.DIVIDE);
        put('%', Token.TokenType.MOD);
        put('=', Token.TokenType.ASSIGN);
        put('<', Token.TokenType.LT);
        put('>', Token.TokenType.GT);
        put('(', Token.TokenType.LPAREN);
        put(')', Token.TokenType.RPAREN);
        put('{', Token.TokenType.LBRACE);
        put('}', Token.TokenType.RBRACE);
        put(';', Token.TokenType.SEMICOLON);
        put('"', Token.TokenType.DQUOTE);
        put('\'', Token.TokenType.SQUOTE);
    }};
    
    private static final HashSet<Character> lookAheadTokens = new HashSet<Character>() {{
        add('=');
        add('!');
        add('<');
        add('>');
        add('&');
        add('|');
    }};
    
    private static final HashMap<String, Token.TokenType> multiSymbolTokens = new HashMap<String, Token.TokenType>() {{
        put("==", Token.TokenType.EQUAL);
        put("!=", Token.TokenType.NEQUAL);
        put("<=", Token.TokenType.LE);
        put(">=", Token.TokenType.GE);
        put("&&", Token.TokenType.AND);
        put("||", Token.TokenType.OR);
    }};


	public static List<Token> analyse(String sourceCode) throws LexicalException {

        List<Token> tokens = new ArrayList<Token>();                      

        int position = 0;
        while (position < sourceCode.length()) {
                try {
                    String word = "";
                    Token.TokenType type = null;
                    switch (typeOf(sourceCode.charAt(position))) {
                        case SPACE:
                            break;
                        case ALPHA:
                            word = extractWord(sourceCode.substring(position));
                            type = reservedWordTokens.containsKey(word) ? reservedWordTokens.get(word) : Token.TokenType.ID;
                            break;  
                        case LITERAL: 
                            word = extractLiteral(sourceCode.substring(position));
                            type = sourceCode.charAt(position) == '"' ? Token.TokenType.STRINGLIT : Token.TokenType.CHARLIT;
                            break;
                        case NUMBER:
                            word = extractNumber(sourceCode.substring(position));
                            type = Token.TokenType.NUM;
                            break;
                        case SPECIAL:
                        case NONE:
                            throw new LexicalException();
                        default: // i.e. SSymbol and LASymbol
                            word = extractSymbol(sourceCode.substring(position));
                            type = word.length() == 1 ? singleSymbolTokens.get(word.charAt(0)) : multiSymbolTokens.get(word); 
                            break;
                    }

                    if (type != null) {

                        if (type == Token.TokenType.NUM || type == Token.TokenType.ID || type == Token.TokenType.TYPE) {
                            tokens.add(new Token(type, word));
                        } else if (reservedWordTokens.containsKey(word)) {
                            tokens.add(new Token(reservedWordTokens.get(word)));
                        } else if (type == Token.TokenType.STRINGLIT || type == Token.TokenType.CHARLIT) {
                            tokens.add(new Token(type, word.substring(1, word.length()-1)));
                        } else {
                            tokens.add(new Token(type));
                        }
                    }

                    position += word.length() > 0 ? word.length() : 1;
                } catch (LexicalException e) {
                    throw new LexicalException();
                }
    
        }

		return tokens;
				
    }

    
    private static String stringScanner(String input, HashSet<LetterType> delimiterSet, char delimiter) throws LexicalException {

        boolean isLiteral = typeOf(input.charAt(0)) == LetterType.LITERAL;
        if (isLiteral) input = input.substring(1);

        String output = "";
        for (int i = 0; i < input.length(); ++i) {
            char current = input.charAt(i);

            if (typeOf(current) == LetterType.NONE && !isLiteral) 
                throw new LexicalException();
            if (current == delimiter || delimiterSet.contains(typeOf(current))) 
                break;
    
            // Check for special case
            if (typeOf(current) == LetterType.SPECIAL && !isLiteral) {
                if (current == '.' && input.startsWith("System.out.println")) 
                    return "System.out.println";
                else if (current == '[' && input.startsWith("String[]")) 
                    return "String[]";
                else {
                    throw new LexicalException();
                }
            }
    
            output += current;   
        }
    
        return output;
    }
        
    private static String extractWord(String input) throws LexicalException {
        HashSet<LetterType> delimiters = new HashSet<LetterType>(LetterTypes);
        delimiters.remove(LetterType.NUMBER);
        delimiters.remove(LetterType.ALPHA);
        delimiters.remove(LetterType.SPECIAL);
    
        try {
            String word = stringScanner(input, delimiters, ' ');
            return word;
        } catch (LexicalException e) {
            throw e;
        }
    }
    
    private static String extractLiteral(String input) throws LexicalException {
        if (input.length() < 2) throw new LexicalException();
        
        try {
            // first char will be ' or "
            String literal = stringScanner(input, new HashSet<LetterType>(), input.charAt(0));
            int length = literal.length();

            // length must be less than the length of the input
            if (length >= input.length()) throw new LexicalException();
            if (input.charAt(0) == input.charAt(literal.length()+1)) {
                return input.charAt(0) + literal + input.charAt(0); // complete the literal
            } else {
                throw new LexicalException();
            }
        } catch (LexicalException e) {
            throw e;
        }
    }
    
    private static String extractNumber(String input) throws LexicalException {
        HashSet<LetterType> delimiters = new HashSet<LetterType>(LetterTypes);
        delimiters.remove(LetterType.NUMBER);

        try {
            String number = stringScanner(input, delimiters, ' ');
            return number;
        } catch (LexicalException e) {
            throw e;
        }
    }
    
    private static String extractSymbol(String input) {
        if (input.length() < 2) return input; 
    
        char current = input.charAt(0), next = input.charAt(1);
        if (lookAheadTokens.contains(current)) {
            if (next == '=' || (current == next && (current == '&' || current == '|'))) 
                return "" + current + next;
        }
        
        return "" + current;
    }

    private static LetterType typeOf(char c) {

        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) 
            return LetterType.ALPHA;

        if (Character.isDigit(c)) 
            return LetterType.NUMBER;

        if (lookAheadTokens.contains(c)) 
            return LetterType.LASYMBOL;

        if (c == '\'' || c == '"') 
            return LetterType.LITERAL;

        if (singleSymbolTokens.containsKey(c)) 
            return LetterType.SSYMBOL;
            
        if (c == '[' || c == ']' || c== '.') 
            return LetterType.SPECIAL;

        if (c == ' ')
            return LetterType.SPACE;

        return LetterType.NONE;
    }
}
