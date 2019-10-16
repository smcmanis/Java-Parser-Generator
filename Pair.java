public class Pair {

    private GrammarToken token;
    private ParseTree.TreeNode selfNode;
    // private ParseTree.TreeNode parent;

    public Pair(GrammarToken token, ParseTree.TreeNode selfNode) {
        this.token = token;
        this.selfNode = selfNode;
        // this.parent = parent;
    }

    public GrammarToken getToken() {
        return token;
    }

    public ParseTree.TreeNode getSelf() {
        return selfNode;
    }

    // public ParseTree.TreeNode getParent() {
    //     return parent;
    // }
}