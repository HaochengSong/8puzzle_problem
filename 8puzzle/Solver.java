import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
public class Solver {
	private searchNode fin;
	private boolean isSol;
	private class searchNode implements Comparable<searchNode>{
		private Board board;
		private searchNode preNode;
		private int moves;
		private final int priority;
		private searchNode(Board board, searchNode preNode, int moves) {
			this.board = board;
			this.preNode = preNode;
			this.moves = moves;
			this.priority = this.board.manhattan() + this.moves;
		}
		public int compareTo(searchNode that) {
			return Integer.compare(this.priority, that.priority);

		}
	}
    public Solver(Board initial) {           
    	if (initial == null)
    		throw new java.lang.IllegalArgumentException("Cannot construct with null argument!");
    	searchNode start = new searchNode(initial, null, 0);
    	searchNode startTwin = new searchNode(initial.twin(), null, 0);
    	searchNode current;
    	searchNode currentTwin;
    	MinPQ<searchNode> pq = new MinPQ<searchNode>();
    	MinPQ<searchNode> pqTwin = new MinPQ<searchNode>();
    	pq.insert(start);
    	pqTwin.insert(startTwin);
    	while(true) {
    		current = pq.delMin();
    		currentTwin = pqTwin.delMin();
    		if (current.board.isGoal()) {
    			isSol = true;
    			break;
    		}
    		if (currentTwin.board.isGoal()) {
    			isSol = false;
    			break;
    		}
    		for (Board b : current.board.neighbors()) {
    			if (current.preNode == null || !b.equals(current.preNode.board))
    				pq.insert(new searchNode(b, current, current.moves + 1));
    		}
    		for (Board b : currentTwin.board.neighbors()) {
    			if ( currentTwin.preNode == null || !b.equals(currentTwin.preNode.board))
    				pqTwin.insert(new searchNode(b, currentTwin, currentTwin.moves + 1));
    		}
    	}
    	fin = current;
    }
    public boolean isSolvable() {            
    	return isSol;
    }
    public int moves() {                     
    	if (!isSolvable())
    		return -1;
    	return fin.moves;
    }
    public Iterable<Board> solution() {     
    	if (!isSolvable())
    		return null;
    	Stack<Board> st = new Stack<Board>(); 
    	searchNode current = fin;
    	while (current != null) {
    		st.push(current.board);
    		current = current.preNode;
    	}
    	return st;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        StdOut.println(solver.moves());
    }
}
