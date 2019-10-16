import java.util.List;

public class Runner {

	public static void main(String[] args) {
		try {
            SyntacticalAnalysisTests tester = new SyntacticalAnalysisTests();
            // tester.testSimpleWeirdAssignment();
            List<Token> results = LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int i = 'c'; }}");
			ParseTree<Token> tree = SyntacticAnalyser.parse(results);
			// System.out.println(tree);
		} catch (LexicalException e) {
			e.printStackTrace();
        } 
        catch (SyntaxException e) {
			e.printStackTrace();
		}

	}

}
 