import java.util.*;

public class Testing {

    public static void main(String[] args) {


        // String testString = "1";

		// List<Token> result = new ArrayList<>();
		// try {
        //     result = LexicalAnalyser.analyse(testString);
        // } catch (NumberException | ExpressionException e) {
		// 	System.err.println("error: " + e.getMessage());
		// }
        
        // System.out.println(result.size());
		LexicalAnalyserTest tester = new LexicalAnalyserTest();
	
            tester.testAnalyseSimpleNumber();
            tester.testAnalyseMultiDigitInteger();
            tester.testSmallDecimal();
            tester.testLongDecimal();
            tester.testSimplePlus();
            tester.testSimpleMinus();
            tester.testSimpleTimes();
            tester.testSimpleDivide();
        // try {
        //     tester.testDecimalNonZeroIntegerPart();
        //     tester.testDecimalNoIntegerPart();
        //     tester.testDecimalNoDecimalPart();
        //     tester.testRandomValidExpression();
        //     tester.testSimpleWrongExpression();
        //     tester.testUnexpectedSymbol();
        //     tester.testRandomWrongExpression();

        // } catch (NumberException e) {System.out.println("num exception");}
        // catch (ExpressionException e) { System.out.println("exp exception");}
            

    }




}