import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

public class TableGenerator {

    private HashMap<GrammarToken, HashSet<GrammarToken>> firstSets = new HashMap<GrammarToken, HashSet<GrammarToken>>();
    private HashMap<GrammarToken, HashSet<GrammarToken>> followSets = new HashMap<GrammarToken, HashSet<GrammarToken>>();
    private Grammar grammar;
    private GrammarToken epsilon = new GrammarToken(GrammarToken.TokenType.EPSILON, false);

    public TableGenerator(Grammar g) {
        this.grammar = g;
        constructFirstSets();
        constructFollowSets();
    }

    public HashMap<GrammarToken.TokenType, HashMap<GrammarToken.TokenType, Integer>> generateTable() {
        HashMap<GrammarToken.TokenType, HashMap<GrammarToken.TokenType, Integer>> parseTable = new HashMap<GrammarToken.TokenType, HashMap<GrammarToken.TokenType, Integer>>();

        for (Production p : grammar.getRulesAsList()) {
            GrammarToken lhs = p.getLHS();
            ArrayList<GrammarToken> rhs = p.getRHS();
            int ruleIndex = grammar.getIndexOfProduction(p);

            if (!parseTable.containsKey(lhs.getType()))
                parseTable.put(lhs.getType(), new HashMap<GrammarToken.TokenType, Integer>());

            for (GrammarToken terminal : firstSets.get(lhs)) {
                if (firstSets.get(rhs.get(0)).contains(terminal))
                    parseTable.get(lhs.getType()).put(terminal.getType(), ruleIndex); 
                
                if (!terminal.equals(epsilon)) continue;

                for (GrammarToken follow : followSets.get(lhs)) {
                    parseTable.get(lhs.getType()).put(follow.getType(), ruleIndex);
                }
            }
        }

        return parseTable;
    }

    private void constructFirstSets() {

        // Initialise all first sets and add terminal if first rhs is terminal
        // Any epsilon productions will be added to first(X)
        for (GrammarToken token : grammar.getSymbols()) {
            firstSets.put(token, new HashSet<GrammarToken>());
            if (!token.isVariable()) {
                firstSets.get(token).add(token);
                continue;
            }
            for (Production p : grammar.getProductionsByVariable(token)) {
                GrammarToken first = p.getRHS().size() > 0 ? p.getRHS().get(0) : null;
                if (first != null && !first.isVariable()) {
                    firstSets.get(token).add(first);
                }
            }
        }

        while (true) {
            boolean updated = false;
            for (GrammarToken variable : grammar.getVariables()) {
                for (Production p : grammar.getProductionsByVariable(variable)) {
                    GrammarToken t = p.getRHS().get(0);
                    if (t.isVariable()) {
                        int setSize = firstSets.get(variable).size();
                        firstSets.get(variable).addAll(firstSets.get(t));
                        if (setSize != firstSets.get(variable).size()) updated = true;
                    }
                }
            }
            if (!updated) break;
        }    
    }

    private void constructFollowSets() {
        // initialise the follow sets 
        for (GrammarToken t : grammar.getVariables()) {
            followSets.put(t, new HashSet<GrammarToken>());
        }

        followSets.get(grammar.getStart()).add(new GrammarToken(GrammarToken.TokenType.$, false));
        while (true) {
            boolean updated = false;
            
            for (GrammarToken variable : grammar.getVariables()) {
                for (Production p : grammar.getProductionsByVariable(variable)) {
                    ArrayList<GrammarToken> rhs = p.getRHS();
                    for (int i = 0; i < rhs.size(); ++i) {
                        GrammarToken t = rhs.get(i);
                        if (rhs.get(i).isVariable()) {
                            int size = followSets.get(t).size();
                            if (i == rhs.size() - 1 || firstSets.get(rhs.get(i+1)).contains(epsilon)) 
                                followSets.get(t).addAll(followSets.get(variable));
                            
                            if (i < rhs.size()-1) 
                                followSets.get(t).addAll(firstSets.get(rhs.get(i+1)));
                            
                            if (size != followSets.get(t).size()) updated = true;
                        }
                    }
                }
            }

            if (!updated) break;
        }    

        for (HashSet<GrammarToken> set : followSets.values()) {
            if (set.contains(epsilon)) {
                set.remove(epsilon);
            }
        }
    }


    public void printFirst() {
        System.out.println("FIRST");
        for (GrammarToken t : grammar.getVariables()) {
            System.out.println("---------------");
            System.out.print(t + ": ");
            for(GrammarToken whew : firstSets.get(t)) {
                System.out.print(whew + ", "  );
            }
            System.out.println("---------------");
        }
    }

    public void printFollow() {
        System.out.println("FOLLOW");
        for (GrammarToken t : grammar.getVariables()) {
            System.out.println("---------------");
            System.out.print(t + ": ");
            for(GrammarToken whew : followSets.get(t)) {
                System.out.print(whew + ", "  );
            }
            System.out.println("---------------");
        }
    }
}