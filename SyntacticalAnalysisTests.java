import static org.junit.Assert.*;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SyntacticalAnalysisTests {
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testEmptyInput() throws SyntaxException, LexicalException {
		
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse(""));
		
	}
	
	@Test
	public void testSimpleBadProgram1() throws LexicalException, SyntaxException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class { }"));
	}
	
	@Test
	public void testSimpleBadProgram2() throws LexicalException, SyntaxException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { }"));
	}
	
	@Test
	public void testSimpleBadProgram3() throws LexicalException, SyntaxException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public Test { public static void main(String[] args) {}}"));
	}
	
	@Test
	public void testSimpleBadProgram4() throws LexicalException, SyntaxException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public void main(String[] args) {}}"));
	}
	
	@Test
	public void testSimpleBadProgram5() throws LexicalException, SyntaxException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class { public static void main(String args) {} }"));
	}
	
	@Test
	public void testSimplestWorkingProgram() throws LexicalException, SyntaxException {
		ParseTree<Token> result = SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ }}"));
		assertEquals(result.getRoot().getLabel(), ParseTree.Label.prog);
		List<ParseTree<Token>.TreeNode> children = result.getRoot().getChildren();
		int i = 0;
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.PUBLIC);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.CLASS);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.ID);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.LBRACE);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.PUBLIC);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.STATIC);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.VOID);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.MAIN);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.LPAREN);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.STRINGARR);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.ARGS);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.RPAREN);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.LBRACE);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.los);
		assertEquals(children.get(i).getChildren().size(), 1);
		assertEquals(children.get(i).getChildren().get(0).getLabel(), ParseTree.Label.epsilon);
		assertEquals(children.get(i++).getChildren().get(0).getChildren().size(), 0);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.RBRACE);
		assertEquals(children.get(i).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(i++).getToken().get().getType(), Token.TokenType.RBRACE);
	}
	
	@Test
	public void testProgramWithSimplestStatement() throws SyntaxException, LexicalException {
		ParseTree<Token> result = SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ ; }}"));
		List<ParseTree<Token>.TreeNode> children = result.getRoot().getChildren();
		
		assertEquals(children.get(13).getLabel(), ParseTree.Label.los);
		assertEquals(children.get(13).getChildren().size(), 2);
		assertEquals(children.get(13).getChildren().get(0).getLabel(), ParseTree.Label.stat);
		assertEquals(children.get(13).getChildren().get(0).getChildren().size(), 1);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getToken().get().getType(), Token.TokenType.SEMICOLON);
		assertEquals(children.get(13).getChildren().get(1).getLabel(), ParseTree.Label.los);
		assertEquals(children.get(13).getChildren().get(1).getChildren().size(), 1);
		assertEquals(children.get(13).getChildren().get(1).getChildren().get(0).getLabel(), ParseTree.Label.epsilon);
		
	}
	
	@Test
	public void testProgramWithSimpleDeclaration() throws SyntaxException, LexicalException {
		ParseTree<Token> result = SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int i; }}"));
		List<ParseTree<Token>.TreeNode> children = result.getRoot().getChildren();
		assertEquals(children.get(13).getLabel(), ParseTree.Label.los);
		assertEquals(children.get(13).getChildren().size(), 2);
		assertEquals(children.get(13).getChildren().get(0).getLabel(), ParseTree.Label.stat);
		assertEquals(children.get(13).getChildren().get(0).getChildren().size(), 2);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.decl);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().size(), 3);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.type);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().size(), 1);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getToken().get().getType(), Token.TokenType.TYPE);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getToken().get().getValue().get(), "int");
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(1).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(1).getToken().get().getType(), Token.TokenType.ID);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(1).getToken().get().getValue().get(), "i");
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getLabel(), ParseTree.Label.possassign);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().size(), 1);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0).getLabel(), ParseTree.Label.epsilon);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(1).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(1).getToken().get().getType(), Token.TokenType.SEMICOLON);
		assertEquals(children.get(13).getChildren().get(1).getLabel(), ParseTree.Label.los);
		assertEquals(children.get(13).getChildren().get(1).getChildren().size(), 1);
		assertEquals(children.get(13).getChildren().get(1).getChildren().get(0).getLabel(), ParseTree.Label.epsilon);
		
	}
	
	@Test
	public void testMissingSemicolon() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int i }}"));
	}
	
	@Test
	public void testSimpleWeirdAssignment() throws SyntaxException, LexicalException {
		ParseTree<Token> result = SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int i = 'c'; }}"));
		List<ParseTree<Token>.TreeNode> children = result.getRoot().getChildren();
		assertEquals(children.get(13).getLabel(), ParseTree.Label.los);
		assertEquals(children.get(13).getChildren().size(), 2);
		assertEquals(children.get(13).getChildren().get(0).getLabel(), ParseTree.Label.stat);
		assertEquals(children.get(13).getChildren().get(0).getChildren().size(), 2);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.decl);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().size(), 3);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.type);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().size(), 1);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getToken().get().getType(), Token.TokenType.TYPE);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getToken().get().getValue().get(), "int");
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(1).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(1).getToken().get().getType(), Token.TokenType.ID);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(1).getToken().get().getValue().get(), "i");
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getLabel(), ParseTree.Label.possassign);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().size(), 2);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0).getToken().get().getType(), Token.TokenType.ASSIGN);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getLabel(), ParseTree.Label.expr);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().size(), 1);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getLabel(), ParseTree.Label.charexpr);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(0).getToken().get().getType(), Token.TokenType.SQUOTE);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(1).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(1).getToken().get().getType(), Token.TokenType.CHARLIT);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(1).getToken().get().getValue().get(), "c");
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(2).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(2).getToken().get().getType(), Token.TokenType.SQUOTE);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(1).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(1).getToken().get().getType(), Token.TokenType.SEMICOLON);
		assertEquals(children.get(13).getChildren().get(1).getLabel(), ParseTree.Label.los);
		assertEquals(children.get(13).getChildren().get(1).getChildren().size(), 1);
		assertEquals(children.get(13).getChildren().get(1).getChildren().get(0).getLabel(), ParseTree.Label.epsilon);
	}
	
	@Test
	public void testBooleanLitAssignment() throws SyntaxException, LexicalException {
		ParseTree<Token> result = SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ boolean b = true; }}"));
		List<ParseTree<Token>.TreeNode> children = result.getRoot().getChildren();
		assertEquals(children.get(13).getLabel(), ParseTree.Label.los);
		assertEquals(children.get(13).getChildren().size(), 2);
		assertEquals(children.get(13).getChildren().get(0).getLabel(), ParseTree.Label.stat);
		assertEquals(children.get(13).getChildren().get(0).getChildren().size(), 2);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.decl);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().size(), 3);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.type);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().size(), 1);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getToken().get().getType(), Token.TokenType.TYPE);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getToken().get().getValue().get(), "boolean");
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(1).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(1).getToken().get().getType(), Token.TokenType.ID);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(1).getToken().get().getValue().get(), "b");
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getLabel(), ParseTree.Label.possassign);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().size(), 2);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0).getToken().get().getType(), Token.TokenType.ASSIGN);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getLabel(), ParseTree.Label.expr);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().size(), 2);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getLabel(), ParseTree.Label.relexpr);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(0).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(0).getToken().get().getType(), Token.TokenType.TRUE);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(1).getLabel(), ParseTree.Label.boolexprprime);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1).getChildren().get(1).getChildren().get(0).getLabel(), ParseTree.Label.epsilon);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(1).getLabel(), ParseTree.Label.terminal);
		assertEquals(children.get(13).getChildren().get(0).getChildren().get(1).getToken().get().getType(), Token.TokenType.SEMICOLON);
		assertEquals(children.get(13).getChildren().get(1).getLabel(), ParseTree.Label.los);
		assertEquals(children.get(13).getChildren().get(1).getChildren().size(), 1);
		assertEquals(children.get(13).getChildren().get(1).getChildren().get(0).getLabel(), ParseTree.Label.epsilon);
	}
	
	@Test
	public void testAssignWithArithmetic() throws SyntaxException, LexicalException {
		ParseTree<Token> result = SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int i = 3 + 2 * 5; }}"));
		ParseTree<Token>.TreeNode exprNode = result.getRoot().getChildren().get(13).getChildren().get(0).getChildren().get(0).getChildren().get(2).getChildren().get(1);
		assertEquals(ParseTree.Label.expr, exprNode.getLabel());
		assertEquals(2, exprNode.getChildren().size());
		assertEquals(2, exprNode.getChildren().get(0).getChildren().size());
		assertEquals(ParseTree.Label.term, exprNode.getChildren().get(0).getChildren().get(0).getChildren().get(0).getLabel());
		assertEquals("3", exprNode.getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getToken().get().getValue().get());
		assertEquals(Token.TokenType.PLUS, exprNode.getChildren().get(0).getChildren().get(0).getChildren().get(1).getChildren().get(0).getToken().get().getType());
		assertEquals("2", exprNode.getChildren().get(0).getChildren().get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0).getChildren().get(0).getToken().get().getValue().get());
		assertEquals(Token.TokenType.TIMES, exprNode.getChildren().get(0).getChildren().get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1).getChildren().get(0).getToken().get().getType());
		assertEquals("5", exprNode.getChildren().get(0).getChildren().get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1).getChildren().get(1).getChildren().get(0).getToken().get().getValue().get());
	}
	
	@Test
	public void testBadArithmetic1() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int i = 3 + 2 * * 5; }}"));
	}
	
	@Test
	public void testBadArithmetic2() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int i = 3 2; }}"));
	}
	
	@Test
	public void testBadArithmetic3() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int i =  + / %; }}"));
	}
	
	@Test
	public void testBadID() throws SyntaxException, LexicalException {
		exception.expect(LexicalException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int _453; }}"));
	}
	
	@Test
	public void testSyntacticallyBadID() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int 453; }}"));
	}
	
	@Test
	public void testIfStatement() throws SyntaxException, LexicalException {
		ParseTree<Token> result = SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ if (true) {System.out.println(\"true\");} else { System.out.println(\"false\"); }}}"));
		ParseTree<Token>.TreeNode ifNode = result.getRoot().getChildren().get(13).getChildren().get(0).getChildren().get(0);

		assertEquals(ParseTree.Label.ifstat, ifNode.getLabel());
		assertEquals(9, ifNode.getChildren().size());
		assertEquals(Token.TokenType.IF, ifNode.getChildren().get(0).getToken().get().getType());
		assertEquals(Token.TokenType.LPAREN, ifNode.getChildren().get(1).getToken().get().getType());
		assertEquals(Token.TokenType.TRUE, ifNode.getChildren().get(2).getChildren().get(0).getToken().get().getType());
		assertEquals(Token.TokenType.RPAREN, ifNode.getChildren().get(4).getToken().get().getType());
		assertEquals(Token.TokenType.LBRACE, ifNode.getChildren().get(5).getToken().get().getType());
		assertEquals(Token.TokenType.RBRACE, ifNode.getChildren().get(7).getToken().get().getType());
		
		ParseTree<Token>.TreeNode elseNode = ifNode.getChildren().get(8);
		assertEquals(ParseTree.Label.elseifstat, elseNode.getLabel());
		assertEquals(5, elseNode.getChildren().size());
		assertEquals(Token.TokenType.ELSE, elseNode.getChildren().get(0).getChildren().get(0).getToken().get().getType());
		assertEquals(Token.TokenType.LBRACE, elseNode.getChildren().get(1).getToken().get().getType());
		assertEquals(Token.TokenType.RBRACE, elseNode.getChildren().get(3).getToken().get().getType());
		assertEquals(ParseTree.Label.epsilon, elseNode.getChildren().get(4).getChildren().get(0).getLabel());
	}
	
	@Test
	public void testBrokenIf1() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ if () {;} else { ; }}}"));
	}
	
	@Test
	public void testBrokenIf2() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ if () {;}  { ; }}}"));
	}
	
	@Test
	public void testBrokenIf3() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ if  {;} else { ; }}}"));
	}
	
	@Test
	public void testBrokenIf4() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ if ()  else { ; }}}"));
	}
	
	@Test
	public void testNotBrokenIf() throws SyntaxException, LexicalException {
		ParseTree<Token> result = SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ if (5) {;} else { ; }}}"));
		ParseTree<Token>.TreeNode ifNode = result.getRoot().getChildren().get(13).getChildren().get(0).getChildren().get(0);

		assertEquals("5", ifNode.getChildren().get(2).getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).getToken().get().getValue().get());
	}
	
	@Test
	public void testWhile() throws SyntaxException, LexicalException {
		ParseTree<Token> result = SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ while (true) { ; } }}"));
		
		ParseTree<Token>.TreeNode whileNode = result.getRoot().getChildren().get(13).getChildren().get(0).getChildren().get(0);
		
		assertEquals(ParseTree.Label.whilestat, whileNode.getLabel());
		assertEquals(8, whileNode.getChildren().size());
		assertEquals(Token.TokenType.WHILE, whileNode.getChildren().get(0).getToken().get().getType());
		assertEquals(Token.TokenType.LPAREN, whileNode.getChildren().get(1).getToken().get().getType());
		assertEquals(Token.TokenType.RPAREN, whileNode.getChildren().get(4).getToken().get().getType());
		assertEquals(Token.TokenType.LBRACE, whileNode.getChildren().get(5).getToken().get().getType());
		assertEquals(Token.TokenType.RBRACE, whileNode.getChildren().get(7).getToken().get().getType());
	}
	
	@Test
	public void testBrokenWhile1() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ while () { ; } }}"));
	}
	
	@Test
	public void testBrokenWhile2() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ while a || b { ; } }}"));
	}
	
	@Test
	public void testBrokenWhile3() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ while (true) }}"));
	}
	
	@Test
	public void testFor() throws SyntaxException, LexicalException {
		ParseTree<Token> result = SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ for ( ; 5 ;) {; } }}"));

		ParseTree<Token>.TreeNode forNode = result.getRoot().getChildren().get(13).getChildren().get(0).getChildren().get(0);
		assertEquals(ParseTree.Label.forstat, forNode.getLabel());
		assertEquals(12,forNode.getChildren().size());
		assertEquals(Token.TokenType.FOR, forNode.getChildren().get(0).getToken().get().getType());
		assertEquals(Token.TokenType.LPAREN, forNode.getChildren().get(1).getToken().get().getType());
		assertEquals(ParseTree.Label.forstart, forNode.getChildren().get(2).getLabel());
		assertEquals(Token.TokenType.SEMICOLON, forNode.getChildren().get(3).getToken().get().getType());
		assertEquals(ParseTree.Label.relexpr, forNode.getChildren().get(4).getLabel());
		assertEquals(ParseTree.Label.boolexprprime, forNode.getChildren().get(5).getLabel());
		assertEquals(Token.TokenType.SEMICOLON, forNode.getChildren().get(6).getToken().get().getType());
		assertEquals(ParseTree.Label.forarith, forNode.getChildren().get(7).getLabel());
		assertEquals(Token.TokenType.RPAREN, forNode.getChildren().get(8).getToken().get().getType());
		assertEquals(Token.TokenType.LBRACE, forNode.getChildren().get(9).getToken().get().getType());
		assertEquals(ParseTree.Label.los, forNode.getChildren().get(10).getLabel());
		assertEquals(Token.TokenType.RBRACE, forNode.getChildren().get(11).getToken().get().getType());
	}
	
	@Test
	public void testBrokenFor() throws SyntaxException, LexicalException {
		exception.expect(SyntaxException.class);
		SyntacticAnalyser.parse(LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ for ( ;  ;) {; } }}"));
	}

}
