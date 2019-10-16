
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;




public class Scanner {

    public ArrayList<Production> scan(String fileName) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            ArrayList<Production> rules = new ArrayList<Production>();
            

            // int ruleID = 1; // starts at 1 so that grammar can be augmented
            String line = "";
            while ((line = reader.readLine()) != null) {          
                String[] tokenStrings = format(line);
                GrammarToken lhs = new GrammarToken(GrammarToken.getTokenType(tokenStrings[0].trim()), true);
                
                String rhs = tokenStrings[1];
                String[] rhsProductions = rhs.split("\\s\\|\\s");

                for (String production : rhsProductions) {
                    ArrayList<GrammarToken> tokenList = new ArrayList<GrammarToken>();
                    String[] tokens = production.trim().split("\\s");
                    

                    for (String token : tokens) {
                        // System.out.println special case
                        if (token.equals("System.out.print(")) {
                            tokenList.add(new GrammarToken(GrammarToken.TokenType.PRINT, false));
                            tokenList.add(new GrammarToken(GrammarToken.TokenType.LPAREN, false));
                        } else {
                            GrammarToken.TokenType type = GrammarToken.getTokenType(token);
                            boolean isVariable = token.startsWith("<<") && token.endsWith(">>");
                            if (type == GrammarToken.TokenType.TYPE) {
                                tokenList.add(new GrammarToken(type, token));
                            } else  {
                                tokenList.add(new GrammarToken(type, isVariable));
                            }
                        }
                    }
                    rules.add(new Production(lhs, tokenList));
                }
            }

            reader.close();

            return rules;
        } catch (IOException e) {
            System.err.print(e.toString());
            return null;
        }
    }

    private String[] format(String rule) {

        String[] matches = Pattern.compile("<<([a-z]*\\s.[^>>]*)>>").matcher(rule).results().map(MatchResult::group).toArray(String[]::new);
        
        for (String m : matches) {
            String newString = m.replaceAll(" ", "");
            rule = rule.replaceFirst(m, newString);
        }

        return rule.split("::=");
    }

}