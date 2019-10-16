import java.util.List;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;


public class SyntacticAnalyser {

    private static ParseTable parseTable;
    // private static Stack<GrammarToken> stack;
    private static Stack<Pair> stack;

    private static Queue<GrammarToken> translated;
    private static GrammarToken epsilon = new GrammarToken(GrammarToken.TokenType.EPSILON, false);
    private static Translator translator = new Translator();

	public static ParseTree<Token> parse(List<Token> tokens) throws SyntaxException {
        initialise(tokens);

        ParseTree<Token> parseTree = new ParseTree<Token>();

        ParseTree.TreeNode root = parseTree.new TreeNode(translator.grammarTokenToLabel(new GrammarToken(GrammarToken.TokenType.PROG_VAR, true)), null); 
        parseTree.setRoot(root);

        stack.push(new Pair(new GrammarToken(GrammarToken.TokenType.PROG_VAR, true), root));
        GrammarToken top = stack.peek().getToken();
        
        while (!stack.empty()) {

            GrammarToken currentToken = translated.isEmpty() ? epsilon : translated.peek();
            System.out.println(top + " " + currentToken);

            // System.out.println(currentToken + " + " +  top);
            // for parse tree, use the getToken method on currentToken if hasToken()
            if (currentToken.getType() == epsilon.getType()) throw new SyntaxException();
            if (currentToken.getType() == top.getType()) {
                translated.remove();
                ParseTree.TreeNode node = stack.pop().getSelf();

                if (!currentToken.isVariable()) {
                    node.setToken(translator.grammarTokenToToken(currentToken));
                }
            } else if (!top.isVariable() || !parseTable.hasEntry(top, currentToken)) {
                throw new SyntaxException();
            } else if (parseTable.hasEntry(top, currentToken)) {
                Production p = parseTable.getEntry(top, currentToken);
                ArrayList<GrammarToken> rhs = p.getRHS();
                ParseTree.TreeNode parent = stack.pop().getSelf();
                Stack<ParseTree.TreeNode> toAdd = new Stack<ParseTree.TreeNode>();

                for (int i = rhs.size()-1; i >= 0; --i) { // dunno abpout doing this in reverse?
                    GrammarToken token = rhs.get(i);
                    // System.out.println(top + ": " + token);
                    ParseTree.TreeNode child = parseTree.new TreeNode(translator.grammarTokenToLabel(token), parent); 
                    toAdd.push(child);
                    if (!token.equals(epsilon)) {
                        stack.push(new Pair(token, child));
                    } 
                }

                while (!toAdd.isEmpty()) {
                    parent.addChild(toAdd.pop());
                }
            }

            top = stack.empty() ? top : stack.peek().getToken();
        }

        // So type is node with one child --> terminal (with the exact token(type==type && value ==value e.g. int == int) )
        //  a variable, say i, is stored in a node of label terminal, that contains the tokentype ID and value i. It is the child of a node of type terminal
        // Terminals
        //      ID, TYPE, ANY SYMBOL, CHARLIT, STRINGLIT, NUM 
        ///     ANything represented by a token is a fucking terminal

        System.out.println(parseTree);
        // List<ParseTree<Token>.TreeNode> children = parseTree.getRoot().getChildren();
        // System.out.println(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(0).getLabel());


		return parseTree;
	}

    private static void initialise(List<Token> tokens) {
        Scanner scanner = new Scanner();

        ArrayList<Production> productions = scanner.scan("grammar.txt");
        Grammar grammar = new Grammar(productions);
        TableGenerator generator = new TableGenerator(grammar);

        // grammar.print();
        parseTable = new ParseTable(generator.generateTable(), grammar);
        // stack = new Stack<GrammarToken>();
        stack = new Stack<Pair>();
        translated = new LinkedList<GrammarToken>();

        // for (Token t : tokens) {
        //     switch (t.getType()) {
        //         case ID :
        //         case NUM :
        //         case CHARLIT :
        //         case STRINGLIT:
        //             translated.add(new GrammarToken(translator.tokenToGrammarToken(t), t.getValue(), false));
        //             break;
        //         case TYPE:
        //             translated.add(new GrammarToken(translator.tokenToGrammarToken(t), t.getValue(), true));
        //             break;
        //         default:
        //             translated.add(new GrammarToken(translator.tokenToGrammarToken(t), true));
        //             break;
        //     }
        // }

        for (Token t : tokens) {
            switch (t.getType()) {
                case ID :
                case NUM :
                case CHARLIT :
                case STRINGLIT:
                    translated.add(translator.tokenToGrammarToken(t));
                    break;
                case TYPE:
                    translated.add(translator.tokenToGrammarToken(t));
                    break;
                default:
                    translated.add(translator.tokenToGrammarToken(t));;
                    break;
            }
        }

        // parseTable.print(productions);
    }

    
}
 