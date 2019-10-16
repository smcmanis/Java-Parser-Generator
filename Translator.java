import java.util.HashMap;
import java.util.Optional;

public class Translator {

    private static final HashMap<GrammarToken.TokenType, Token.TokenType> grammarTokenToToken = new HashMap<GrammarToken.TokenType, Token.TokenType>() {{
        put(GrammarToken.TokenType.PUBLIC, Token.TokenType.PUBLIC);
        put(GrammarToken.TokenType.CLASS, Token.TokenType.CLASS);
        put(GrammarToken.TokenType.STATIC, Token.TokenType.STATIC);
        put(GrammarToken.TokenType.VOID, Token.TokenType.VOID);
        put(GrammarToken.TokenType.MAIN, Token.TokenType.MAIN);
        put(GrammarToken.TokenType.ARGS, Token.TokenType.ARGS);
        put(GrammarToken.TokenType.TYPE, Token.TokenType.TYPE);
        put(GrammarToken.TokenType.STRINGARR, Token.TokenType.STRINGARR);
        put(GrammarToken.TokenType.PRINT, Token.TokenType.PRINT);
        put(GrammarToken.TokenType.WHILE, Token.TokenType.WHILE);
        put(GrammarToken.TokenType.FOR, Token.TokenType.FOR);
        put(GrammarToken.TokenType.IF, Token.TokenType.IF);
        put(GrammarToken.TokenType.ELSE, Token.TokenType.ELSE);
        put(GrammarToken.TokenType.TRUE, Token.TokenType.TRUE);
        put(GrammarToken.TokenType.FALSE, Token.TokenType.FALSE);
        put(GrammarToken.TokenType.PLUS, Token.TokenType.PLUS);
        put(GrammarToken.TokenType.MINUS, Token.TokenType.MINUS);
        put(GrammarToken.TokenType.TIMES, Token.TokenType.TIMES);
        put(GrammarToken.TokenType.DIVIDE, Token.TokenType.DIVIDE);
        put(GrammarToken.TokenType.MOD, Token.TokenType.MOD);
        put(GrammarToken.TokenType.ASSIGN, Token.TokenType.ASSIGN);
        put(GrammarToken.TokenType.LT, Token.TokenType.LT);
        put(GrammarToken.TokenType.GT, Token.TokenType.GT);
        put(GrammarToken.TokenType.LPAREN, Token.TokenType.LPAREN);
        put(GrammarToken.TokenType.RPAREN, Token.TokenType.RPAREN);
        put(GrammarToken.TokenType.LBRACE, Token.TokenType.LBRACE);
        put(GrammarToken.TokenType.RBRACE, Token.TokenType.RBRACE);
        put(GrammarToken.TokenType.SEMICOLON, Token.TokenType.SEMICOLON);
        put(GrammarToken.TokenType.DQUOTE, Token.TokenType.DQUOTE);
        put(GrammarToken.TokenType.SQUOTE, Token.TokenType.SQUOTE);
        put(GrammarToken.TokenType.EQUAL, Token.TokenType.EQUAL);
        put(GrammarToken.TokenType.NEQUAL, Token.TokenType.NEQUAL);
        put(GrammarToken.TokenType.LE, Token.TokenType.LE);
        put(GrammarToken.TokenType.GE, Token.TokenType.GE);
        put(GrammarToken.TokenType.AND, Token.TokenType.AND);
        put(GrammarToken.TokenType.OR, Token.TokenType.OR);
        put(GrammarToken.TokenType.ID, Token.TokenType.ID);
        put(GrammarToken.TokenType.CHARLIT, Token.TokenType.CHARLIT);
        put(GrammarToken.TokenType.STRINGLIT, Token.TokenType.STRINGLIT);
        put(GrammarToken.TokenType.NUM, Token.TokenType.NUM);

    }};

    private static final HashMap<GrammarToken.TokenType, ParseTree.Label> grammarTokenToLabel= new HashMap<GrammarToken.TokenType, ParseTree.Label>() {{
        put(GrammarToken.TokenType.PROG_VAR, ParseTree.Label.prog);
        put(GrammarToken.TokenType.LOS_VAR, ParseTree.Label.los);
        put(GrammarToken.TokenType.STAT_VAR, ParseTree.Label.stat);
        put(GrammarToken.TokenType.WHILE_VAR, ParseTree.Label.whilestat);
        put(GrammarToken.TokenType.FOR_VAR, ParseTree.Label.forstat);
        put(GrammarToken.TokenType.FORSTART_VAR, ParseTree.Label.forstart);
        put(GrammarToken.TokenType.FORARITH_VAR, ParseTree.Label.forarith);
        put(GrammarToken.TokenType.IF_VAR, ParseTree.Label.ifstat);
        put(GrammarToken.TokenType.ELSEIF_VAR, ParseTree.Label.elseifstat);
        put(GrammarToken.TokenType.ELSEQIF_VAR, ParseTree.Label.elseorelseif);
        put(GrammarToken.TokenType.POSSIF_VAR, ParseTree.Label.possif);
        put(GrammarToken.TokenType.ASSIGN_VAR, ParseTree.Label.assign);
        put(GrammarToken.TokenType.DECL_VAR, ParseTree.Label.decl);
        put(GrammarToken.TokenType.POSSASSIGN_VAR, ParseTree.Label.possassign);
        put(GrammarToken.TokenType.PRINT_VAR, ParseTree.Label.print);
        put(GrammarToken.TokenType.TYPE_VAR, ParseTree.Label.type);
        put(GrammarToken.TokenType.EXPR_VAR, ParseTree.Label.expr);
        put(GrammarToken.TokenType.BOOLEXPRP_VAR, ParseTree.Label.boolexprprime);
        put(GrammarToken.TokenType.BOOLOP_VAR, ParseTree.Label.boolop);
        put(GrammarToken.TokenType.BOOLLOG_VAR, ParseTree.Label.boollog);
        put(GrammarToken.TokenType.BOOLEQ_VAR, ParseTree.Label.booleq);
        put(GrammarToken.TokenType.RELEXPR_VAR, ParseTree.Label.relexpr);
        put(GrammarToken.TokenType.RELEXPRP_VAR, ParseTree.Label.relexprprime);
        put(GrammarToken.TokenType.RELOP_VAR, ParseTree.Label.relop);
        put(GrammarToken.TokenType.ARITHEXPR_VAR, ParseTree.Label.arithexpr);
        put(GrammarToken.TokenType.ARITHEXPRP_VAR, ParseTree.Label.arithexprprime);
        put(GrammarToken.TokenType.TERM_VAR, ParseTree.Label.term);
        put(GrammarToken.TokenType.TERMP_VAR, ParseTree.Label.termprime);
        put(GrammarToken.TokenType.FACTOR_VAR, ParseTree.Label.factor);
        put(GrammarToken.TokenType.PRINTEXPR_VAR, ParseTree.Label.printexpr);
        put(GrammarToken.TokenType.ID, ParseTree.Label.ID);
        put(GrammarToken.TokenType.CHARLIT, ParseTree.Label.charexpr);
        put(GrammarToken.TokenType.STRINGLIT, ParseTree.Label.stringlit);
        put(GrammarToken.TokenType.NUM, ParseTree.Label.num);
        // put(GrammarToken.TokenType.TRUE, ParseTree.Label.boolconst);
        put(GrammarToken.TokenType.EPSILON, ParseTree.Label.epsilon);
        // put(GrammarToken.TokenType.FALSE, ParseTree.Label.terminal);
        // put(GrammarToken.TokenType.FALSE, ParseTree.Label.boolconst);
    }};

    // public GrammarToken.TokenType tokenToGrammarToken(Token token) {
    //     for (GrammarToken.TokenType gtype : grammarTokenToToken.keySet()) {
    //         if (grammarTokenToToken.get(gtype) == token.getType()) 
    //             return gtype;
    //     }
    //     return null;
    // }

    // public Token.TokenType grammarTokenToToken(GrammarToken t) {
    //     System.out.println(grammarTokenToToken.get(t.getType()));
    //     return grammarTokenToToken.get(t.getType());
    // }

    // public ParseTree.Label grammarTokenToLabel(GrammarToken gt) {
    //     if (grammarTokenToLabel.keySet().contains(gt.getType())) 
    //         return grammarTokenToLabel.get(gt.getType());

    //     if (grammarTokenToToken.keySet().contains(gt.getType())) 
    //         return ParseTree.Label.terminal;

    //     return null;
    // }

    public GrammarToken tokenToGrammarToken(Token token) {
        for (GrammarToken.TokenType gtype : grammarTokenToToken.keySet()) {
            if (grammarTokenToToken.get(gtype) == token.getType()) {
                return new GrammarToken(gtype, token);
            }
        }
        return null;
    }

    public Token grammarTokenToToken(GrammarToken t) {
        if (t.getToken() != null) return (Token) t.getToken(); 
        Token.TokenType type = grammarTokenToToken.get(t.getType());
        return new Token(type);
    }

    public ParseTree.Label grammarTokenToLabel(GrammarToken gt) {
        // ths switch may be redundant
        switch (gt.getType()) {
            case ID:
            case NUM:
            case CHARLIT:
            case STRINGLIT:
            case TYPE:
                return ParseTree.Label.terminal;
            default:
                break;
        }
        if (grammarTokenToLabel.keySet().contains(gt.getType())) 
            return grammarTokenToLabel.get(gt.getType());

        if (grammarTokenToToken.keySet().contains(gt.getType())) 
            return ParseTree.Label.terminal;

        return null;
    }

    public GrammarToken labelToGrammarToken(ParseTree.Label label) {
        for (GrammarToken.TokenType gtype : grammarTokenToLabel.keySet()) {
            if (grammarTokenToLabel.get(gtype) == label) {
                return new GrammarToken(gtype, true);
            }
        }
        return null;
    }

}