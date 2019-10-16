import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

public class Grammar {
    private GrammarToken startSymbol;
    private HashMap<GrammarToken, ArrayList<Production>> rules = new HashMap<GrammarToken, ArrayList<Production>>();
    private HashSet<GrammarToken> symbols = new HashSet<GrammarToken>();
    private HashSet<GrammarToken> terminals = new HashSet<GrammarToken>();
    private HashSet<GrammarToken> variables = new HashSet<GrammarToken>();
    private HashSet<GrammarToken> epsilonProducers = new HashSet<GrammarToken>();
    private ArrayList<Production> productionByIndex = new ArrayList<Production>();

    public Grammar(ArrayList<Production> productions) { 
        startSymbol = productions.get(0).getLHS();

        for (Production prod : productions) {
            GrammarToken t = prod.getLHS();
            productionByIndex.add(prod);
            if (!rules.containsKey(t)) {
                rules.put(t, new ArrayList<Production>());
            } 
            rules.get(t).add(prod);

            if (!symbols.contains(t)) symbols.add(t);
            if (!variables.contains(t)) variables.add(t);

            for (GrammarToken token : prod.getRHS()) {
                if (!symbols.contains(token)) {
                    symbols.add(token);
                }
            }

            if (prod.getRHS().size() > 0 && prod.getRHS().get(0).getType() == GrammarToken.TokenType.EPSILON) {
                epsilonProducers.add(t);
            }
        }

        for (GrammarToken token : symbols) {
            if (!token.isVariable()) {
                terminals.add(token);
            } 
        }
    }

    public int getIndexOfProduction(Production p) {

        return productionByIndex.indexOf(p);
    }

    public Production getProductionByIndex(int index) {
        if (index >= 0 && index < productionByIndex.size())
            return productionByIndex.get(index);
        return null;
    }

    public HashMap<GrammarToken, ArrayList<Production>> getRules() {
        return rules;
    }

    public ArrayList<Production> getProductionsByVariable(GrammarToken lhs) {
        return rules.containsKey(lhs) ? rules.get(lhs) : new ArrayList<Production>();
    }

    public GrammarToken getStart() {
        return startSymbol;
    }

    public HashSet<GrammarToken> getSymbols() {
        return symbols;
    }

    public HashSet<GrammarToken> getTerminals() {
        return terminals;
    }

    public HashSet<GrammarToken> getVariables() {
        return variables;
    }

    public boolean producesEpislon(GrammarToken t) {
        return epsilonProducers.contains(t);
    }

    public ArrayList<Production> getRulesAsList() {
        return productionByIndex;
    }

    public void print() {

        for (int i = 0; i < productionByIndex.size(); ++i) {
            System.out.print(i + ": ");
            productionByIndex.get(i).print();
            System.out.println();
        }

    }


}