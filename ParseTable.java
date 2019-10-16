import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

public class ParseTable {

    private HashMap<GrammarToken.TokenType, HashMap<GrammarToken.TokenType, Integer>> table;
    private Grammar grammar;

    public ParseTable(HashMap<GrammarToken.TokenType, HashMap<GrammarToken.TokenType, Integer>> table, Grammar g) {
        this.table = table;
        grammar = g;
    }

    public boolean hasEntry(GrammarToken var, GrammarToken term) {
        if (!table.keySet().contains(var.getType())) return false;
        return table.get(var.getType()).keySet().contains(term.getType());
    }

    public Production getEntry(GrammarToken var, GrammarToken term) {
        return grammar.getProductionByIndex(table.get(var.getType()).get(term.getType()));
    }

    public void print(ArrayList<Production> productions) {
        for (Production p : productions) {

        for (GrammarToken.TokenType row : table.keySet()) {
            for (GrammarToken.TokenType col : table.get(row).keySet()) {
                if (grammar.getIndexOfProduction(p) == table.get(row).get(col)) {

                                System.out.println("-----------");
                System.out.println(row + " + " + col + " --> " + table.get(row).get(col));
                grammar.getProductionByIndex(table.get(row).get(col)).print();
                System.out.println("-----------");
                    }
                }
            }
        }
    }
}