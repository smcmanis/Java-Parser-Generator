import java.util.ArrayList;
import java.util.HashSet;


public class Production {

    private GrammarToken variable;
    private ArrayList<GrammarToken> rightSide;
    private boolean isEpsilonProduction;

    public Production(GrammarToken left, ArrayList<GrammarToken> tokens) {
        variable = left;
        rightSide = tokens;
        isEpsilonProduction = !tokens.isEmpty() && tokens.get(0).getType() == GrammarToken.TokenType.EPSILON;
    }

    public ArrayList<GrammarToken> getRHS() {
        return rightSide;
    }

    public GrammarToken getLHS() {
        return variable;
    }

    public boolean isEpsilonProduction() {
        return isEpsilonProduction;
    }

    public void print() {
        System.out.print(variable + " --> ");
                        
        for (GrammarToken item : rightSide) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
    @Override
    public int hashCode() {
        int hash = variable.hashCode();

        for (GrammarToken t : rightSide) hash += t.hashCode();


        return hash;
    }

    @Override
	public boolean equals(Object other) {
        // return true;
		if (other == null) return false;
		if (!Production.class.isAssignableFrom(other.getClass())) return false;
		
        Production p = (Production) other;

		if (!p.variable.equals(this.variable)) return false;
        

        if (p.rightSide.size() != this.rightSide.size()) return false;

        for (GrammarToken t : p.rightSide)
            if (!this.rightSide.contains(t)) return false;

		return true;
	}	
}