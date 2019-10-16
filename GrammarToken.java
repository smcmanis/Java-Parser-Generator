
import java.util.Optional;

public class GrammarToken {

    public enum TokenType {
        
        PROG_VAR, LOS_VAR, STAT_VAR, WHILE_VAR , 
        FOR_VAR, FORSTART_VAR, FORARITH_VAR, IF_VAR, 
        ELSEIF_VAR, ELSEQIF_VAR, POSSIF_VAR, ASSIGN_VAR, 
        DECL_VAR, POSSASSIGN_VAR, PRINT_VAR , TYPE_VAR, 
        EXPR_VAR, BOOLEXPRP_VAR, BOOLOP_VAR , BOOLEQ_VAR, 
        BOOLLOG_VAR , RELEXPR_VAR , RELEXPRP_VAR , 
        RELOP_VAR, ARITHEXPR_VAR, ARITHEXPRP_VAR, 
        TERM_VAR, TERMP_VAR, FACTOR_VAR, PRINTEXPR_VAR,

        PLUS, MINUS, TIMES, DIVIDE, MOD, ASSIGN, EQUAL, NEQUAL, LT, LE, GT, GE, LPAREN, RPAREN, LBRACE, RBRACE, AND, OR,
		SEMICOLON, PUBLIC, CLASS, STATIC, VOID, MAIN, STRINGARR, ARGS, TYPE, PRINT, WHILE, FOR, IF, ELSE, DQUOTE,
        SQUOTE, ID, NUM, CHARLIT, TRUE, FALSE, STRINGLIT,
        
        EPSILON, NONE, $
    };

    private TokenType type; 
    private Optional<String> value;
    private Optional<Token> token;
    private boolean isVariable;

    public GrammarToken(TokenType type, Token token) {
        this.type = type;
        switch (type) {
            case ID:
            case NUM:
            case CHARLIT:
            case STRINGLIT:
            case EPSILON:
            case TYPE: // controversial
                this.isVariable = false;
                break;
            default:
                this.isVariable = isVariable;
                break;
        }
        this.token = Optional.of(token);
    }

    public Token getToken() {
        return token.get();
    }

    public boolean hasToken() {
        return token != null;
    }

    public GrammarToken(TokenType type, boolean isVariable) {
        this.type = type;
        switch (type) {
            case ID:
            case NUM:
            case CHARLIT:
            case STRINGLIT:
            case EPSILON:
            case TYPE: // controversial
                this.isVariable = false;
                break;
            default:
                this.isVariable = isVariable;
                break;
        }
    }

    public GrammarToken(TokenType type, Optional<String> value, boolean isVariable) {
        this.type = type;
        switch (type) {
            case ID:
            case NUM:
            case CHARLIT:
            case STRINGLIT:
            case EPSILON:
            case TYPE: // controversial
                this.isVariable = false;
                break;
            default:
                this.isVariable = isVariable;
                break;
        }

        this.value = value;
    }

 	public GrammarToken(TokenType type, String value) {
        this.type = type;
        this.isVariable = false;
		this.value = Optional.of(value);
    }

    public boolean isVariable() {
        return isVariable;
    }

    public TokenType getType() {
        return type;
    }

	public Optional<String> getValue() {
		return this.value;
	}
    public static TokenType getTokenType(String token) {

        switch (token) {
            case "<<prog>>":
                return TokenType.PROG_VAR;
            case "<<los>>":
                return TokenType.LOS_VAR;
            case "<<stat>>":
                return TokenType.STAT_VAR;
            case "<<while>>":
                return TokenType.WHILE_VAR;
            case "<<for>>":
                return TokenType.FOR_VAR;
            case "<<forstart>>":
                return TokenType.FORSTART_VAR;
            case "<<forarith>>":
                return TokenType.FORARITH_VAR;
            case "<<if>>":
                return TokenType.IF_VAR;
            case "<<elseif>>":
                return TokenType.ELSEIF_VAR;
            case "<<else?if>>":
                return TokenType.ELSEQIF_VAR;
            case "<<possif>>":
                return TokenType.POSSIF_VAR;
            case "<<assign>>":
                return TokenType.ASSIGN_VAR;
            case "<<decl>>":
                return TokenType.DECL_VAR;
            case "<<possassign>>":
                return TokenType.POSSASSIGN_VAR;
            case "<<print>>":
                return TokenType.PRINT_VAR;
            case "<<type>>":
                return TokenType.TYPE_VAR;
            case "<<expr>>":
                return TokenType.EXPR_VAR;
            case "<<boolexpr'>>":
                return TokenType.BOOLEXPRP_VAR;
            case "<<boolop>>":
                return TokenType.BOOLOP_VAR;
            case "<<booleq>>":
                return TokenType.BOOLEQ_VAR;
            case "<<boollog>>":
                return TokenType.BOOLLOG_VAR;
            case "<<relexpr>>":
                return TokenType.RELEXPR_VAR;
            case "<<relexpr'>>":
                return TokenType.RELEXPRP_VAR;
            case "<<relop>>":
                return TokenType.RELOP_VAR;
            case "<<arithexpr>>":
                return TokenType.ARITHEXPR_VAR;
            case "<<arithexpr'>>":
                return TokenType.ARITHEXPRP_VAR;
            case "<<term>>":
                return TokenType.TERM_VAR;
            case "<<term'>>":
                return TokenType.TERMP_VAR;
            case "<<factor>>":
                return TokenType.FACTOR_VAR;
            case "<<printexpr>>":
                return TokenType.PRINTEXPR_VAR;
            case "+":
                return TokenType.PLUS; 
            case "-":
                return TokenType.MINUS;
            case "*":
                return TokenType.TIMES;
            case "/":
                return TokenType.DIVIDE;
            case "%":
                return TokenType.MOD;
            case "=":
                return TokenType.ASSIGN;
            case "==":
                return TokenType.EQUAL;
            case "!=":
                return TokenType.NEQUAL;
            case "<":
                return TokenType.LT;
            case "<=":
                return TokenType.LE;
            case ">":
                return TokenType.GT;
            case ">=":
                return TokenType.GE;
            case "(":
                return TokenType.LPAREN;
            case ")":
                return TokenType.RPAREN; 
            case "{":
                return TokenType.LBRACE;
            case "}":
                return TokenType.RBRACE;
            case "&&":
                return TokenType.AND;
            case "||":
                return TokenType.OR;
            case ";":
                return TokenType.SEMICOLON;
            case "public":
                return TokenType.PUBLIC;
            case "class":
                return TokenType.CLASS;
            case "static":
                return TokenType.STATIC;
            case "void":
                return TokenType.VOID;
            case "main":
                return TokenType.MAIN;
            case "String[]":
                return TokenType.STRINGARR;
            case "args":
                return TokenType.ARGS;
            case "System.out.print":
                return TokenType.PRINT;
            case "while":
                return TokenType.WHILE;
            case "for":
                return TokenType.FOR;
            case "if":
                return TokenType.IF;
            case "else":
                return TokenType.ELSE;
            case "\"":
                return TokenType.DQUOTE;
            case "'":
                return TokenType.SQUOTE;
            case "true":
                return TokenType.TRUE;
            case "false":
                return TokenType.FALSE;
            case "int":
            case "boolean":
            case "char":
                return TokenType.TYPE;
            case "<<ID>>":
                return TokenType.ID;
            case "<<num>>":
                return TokenType.NUM;
            case "<<charexpr>>":
                return TokenType.CHARLIT;
            case "<<stringlit>>":
                return TokenType.STRINGLIT;
            case "<<epsilon>>":
                return TokenType.EPSILON;
            default:
                return TokenType.NONE;
        }
    }
 
    public static GrammarToken.TokenType tokenToGrammarToken(Token token) {

        switch (token.getType()) {
            case PLUS:
                return TokenType.PLUS; 
            case MINUS:
                return TokenType.MINUS;
            case TIMES:
                return TokenType.TIMES;
            case DIVIDE:
                return TokenType.DIVIDE;
            case MOD:
                return TokenType.MOD;
            case ASSIGN:
                return TokenType.ASSIGN;
            case EQUAL:
                return TokenType.EQUAL;
            case NEQUAL:
                return TokenType.NEQUAL;
            case LT:
                return TokenType.LT;
            case LE:
                return TokenType.LE;
            case GT:
                return TokenType.GT;
            case GE:
                return TokenType.GE;
            case LPAREN:
                return TokenType.LPAREN;
            case RPAREN:
                return TokenType.RPAREN; 
            case LBRACE:
                return TokenType.LBRACE;
            case RBRACE:
                return TokenType.RBRACE;
            case AND:
                return TokenType.AND;
            case OR:
                return TokenType.OR;
            case SEMICOLON:
                return TokenType.SEMICOLON;
            case PUBLIC:
                return TokenType.PUBLIC;
            case CLASS:
                return TokenType.CLASS;
            case STATIC:
                return TokenType.STATIC;
            case VOID:
                return TokenType.VOID;
            case MAIN:
                return TokenType.MAIN;
            case STRINGARR:
                return TokenType.STRINGARR;
            case ARGS:
                return TokenType.ARGS;
            case PRINT:
                return TokenType.PRINT;
            case WHILE:
                return TokenType.WHILE;
            case FOR:
                return TokenType.FOR;
            case IF:
                return TokenType.IF;
            case ELSE:
                return TokenType.ELSE;
            case DQUOTE:
                return TokenType.DQUOTE;
            case SQUOTE:
                return TokenType.SQUOTE;
            case TRUE:
                return TokenType.TRUE;
            case FALSE:
                return TokenType.FALSE;
            case TYPE:
                return TokenType.TYPE;
            case ID:
                return TokenType.ID;
            case NUM:
                return TokenType.NUM;
            case CHARLIT:
                return TokenType.CHARLIT;
            case STRINGLIT:
                return TokenType.STRINGLIT;
            default:
                return TokenType.NONE;
        }

    }


    // public static TokenType getTokenType(ParseTree.Label token) {

    //     switch (token) {
    //         case prog:
    //             return TokenType.PROG_VAR;
    //         case los:
    //             return TokenType.LOS_VAR;
    //         case stat:
    //             return TokenType.STAT_VAR;
    //         case whilestat:
    //             return TokenType.WHILE_VAR;
    //         case forstat:
    //             return TokenType.FOR_VAR;
    //         case forstart:
    //             return TokenType.FORSTART_VAR;
    //         case forarith:
    //             return TokenType.FORARITH_VAR;
    //         case ifstat:
    //             return TokenType.IF_VAR;
    //         case elseifstat:
    //             return TokenType.ELSEIF_VAR;
    //         case elseorelseif:
    //             return TokenType.ELSEQIF_VAR;
    //         case possif:
    //             return TokenType.POSSIF_VAR;
    //         case assign:
    //             return TokenType.ASSIGN_VAR;
    //         case decl:
    //             return TokenType.DECL_VAR;
    //         case possassign:
    //             return TokenType.POSSASSIGN_VAR;
    //         case print:
    //             return TokenType.PRINT_VAR;
    //         case type:
    //             return TokenType.TYPE_VAR;
    //         case expr:
    //             return TokenType.EXPR_VAR;
    //         case boolexprprime:
    //             return TokenType.BOOLEXPRP_VAR;
    //         case boolop:
    //             return TokenType.BOOLOP_VAR;
    //         case booleq:
    //             return TokenType.BOOLEQ_VAR;
    //         case boollog:
    //             return TokenType.BOOLLOG_VAR;
    //         case relexpr:
    //             return TokenType.RELEXPR_VAR;
    //         case relexprprime:
    //             return TokenType.RELEXPRP_VAR;
    //         case relop:
    //             return TokenType.RELOP_VAR;
    //         case arithexpr:
    //             return TokenType.ARITHEXPR_VAR;
    //         case arithexprprime:
    //             return TokenType.ARITHEXPRP_VAR;
    //         case term:
    //             return TokenType.TERM_VAR;
    //         case termprime:
    //             return TokenType.TERMP_VAR;
    //         case factor:
    //             return TokenType.FACTOR_VAR;
    //         case printexp:
    //             return TokenType.PRINTEXPR_VAR;
    //     }
    // }
   

    @Override
    public String toString() {
    	switch (type) {
    	case TYPE : return "[" + type + ": " + value + "]";
    	default : return "[" + type + "]";
    	}
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
	public boolean equals(Object other) {
        if (other == null) return false;

		if (!GrammarToken.class.isAssignableFrom(other.getClass())) return false;

		GrammarToken t = (GrammarToken) other;
		if (t.type != this.type) {

            return false;
        }
		if (t.value == null || this.value == null) {
            return t.value == this.value;
        }

		return t.value.equals(this.value);
	}	
}

    