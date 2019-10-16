import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParseTree<T> {

	public enum Label {
		prog, los, stat, whilestat, forstat, forstart, forarith, ifstat, elseifstat, elseorelseif, possif, assign, decl,
		possassign, print, type, expr, boolexprprime, boolop, booleq, boollog, relexpr, relexprprime, relop, arithexpr,
		arithexprprime, term, termprime, factor, printexpr, ID, num, charexpr, stringlit, boolconst, epsilon, terminal
	};
 
	class TreeNode {

		private Label label;
		private Optional<T> token; // What is this? only terminals
		private TreeNode parent;
		private List<TreeNode> children;

		public TreeNode(Label label, TreeNode parent) {
			this.label = label;
			this.token = Optional.empty();
			this.parent = parent;
			children = new ArrayList<TreeNode>();
		}
 
		public TreeNode(Label label, T token, TreeNode parent) {
			this.label = label;
			this.token = Optional.of(token);
			this.parent = parent;
			children = new ArrayList<TreeNode>();
		}

		public void addChild(TreeNode child) {
			children.add(child);
		}

		public Optional<T> getToken() {
			return this.token;
        }
        
        public void setToken(T token) {
            this.token = Optional.of(token);
        }

		public TreeNode getParent() {
			return this.parent;
		}

		public List<TreeNode> getChildren() {
			return this.children;
		}

		public Label getLabel() {
			return this.label;
		}

		@Override
		public String toString() {
			return "[" + this.label + ", " + this.token + "]";
		}

	}

	private TreeNode root;

	public ParseTree() {
		this.root = null;
	}

	public ParseTree(TreeNode root) {
		this.root = root;
	}

	public TreeNode getRoot() {
		return this.root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	private String spaces(int num) {
		String s = "";

		for (int i = 0; i < num - 1; i++)
			s += "| ";

		s += "|-";

		return s;
	}

	private String stringify(TreeNode current, int depth) {

		String s = current.toString() + "\n";
		if (current.getChildren().size() > 0) {
			for (int i = 0; i < current.getChildren().size() - 1; i++) {
				s += spaces(depth + 1) + stringify(current.getChildren().get(i), depth + 1);
			}
			s += spaces(depth + 1) + stringify(current.getChildren().get(current.getChildren().size() - 1), depth + 1);
		}

		return s;

	}

	@Override
	public String toString() {
		return stringify(root, 0);
	}
}
