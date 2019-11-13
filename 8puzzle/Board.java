import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;
import java.util.Iterator;
import java.util.Stack;
public final class Board {
	private final int[][] board;
	private int len;
    public Board(int[][] blocks) {           
    	len = blocks.length;                    
    	board = new int[len][len];
    	for (int i = 0; i < len; i++)
    		for (int j = 0; j < len; j++)
    			board[i][j] = blocks[i][j]; 
    }
    public int dimension() {                 
    	return len;
    }
    public int hamming() {                 // number of blocks out of place
    	int count = 0;
    	for (int i = 0; i < len; i++)
    		for (int j = 0; j < len; j++)
    			if (board[i][j] != i * len + j + 1)
    				count++;
    	return count - 1;
    }
    public int manhattan() {                // sum of Manhattan distances between blocks and goal
    	int sum = 0;
    	int digit1 = 0, digit2 = 0;
    	for (int i = 0; i < len; i++)
    		for (int j = 0; j < len; j++) {
    			if (board[i][j] == 0)
    				continue;
    			digit1 = Math.abs((board[i][j]  - 1) / len - i);
    			digit2 = Math.abs((board[i][j] - 1) % len - j);
    			sum += digit1 + digit2;
    		}
    	return sum;
    }
    public boolean isGoal() {                // is this board the goal board?
    	return manhattan() == 0;
    }
    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
      	Board twinBoard = new Board(board);
      	int key1 = 0, key2 = 0, key3 = 0, key4 = 0;
      	if (twinBoard.board[key1][key2] == 0)
      		key1++;
      	for (int i = 0; i < len; i++)
      		for (int j= 0; j < len; j++) {
      			if (twinBoard.board[i][j] != 0 && twinBoard.board[i][j] != twinBoard.board[key1][key2]) {
      				key3 = i; 
      				key4 = j;  				
      			}
      				
      		}
      	swap(twinBoard.board, key1, key2, key3, key4);
      	return twinBoard;
    }
    public boolean equals(Object y) { 
        if (y == null)
            return false;
        if (y == this)
            return true;
        if (y.getClass().isInstance(this)) {
            Board yb = (Board) y;
            if (yb.len != this.len)
                return false;
            else {
                for (int row = 0; row < len; row++)
                    for (int col = 0; col < len; col++)
                        if (yb.board[row][col] != board[row][col])
                            return false;
                return true;
            }
        } else
            return false;
    }
    public Iterable<Board> neighbors() {     // all neighboring boards
    	Stack<Board> st = new Stack<Board>();
    	int[][] newBlocks = new int[len][len];
    	int blankRow = len, blankCol = len;
    	for (int i = 0; i < len; i++)
    		for (int j = 0; j < len; j++) {
    			newBlocks[i][j] = board[i][j];
    			if(newBlocks[i][j] == 0) {
    				blankRow = i;
    				blankCol = j;
    			} 
    		}
    	int[] row = {blankRow + 1, blankRow - 1, blankRow, blankRow};
    	int[] col = {blankCol, blankCol, blankCol + 1, blankCol - 1};
    	for (int i = 0; i < 4; i++)
    		if (row[i] < len && col[i] < len && row[i] >= 0 && col[i] >= 0) {
    			Board newBoard = new Board(newBlocks);
    			swap(newBoard.board, row[i], col[i], blankRow, blankCol);
    			st.add(newBoard);
    		}
    	return st;
    }
    public String toString() {               // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(len + "\n");
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    private void swap(int[][] array, int a, int b, int c, int d) {
    	int temp = array[a][b];
    	array[a][b] = array[c][d];
    	array[c][d] = temp;
    }
   /* public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial);
        Iterable<Board> next = initial.neighbors();
        for (Board s : next) {
        	StdOut.println(s);
        	StdOut.println(s.hamming());
            StdOut.println(s.manhattan());
        }
        Board twin = initial.twin();
        StdOut.println(twin.hamming());
        StdOut.println(twin.manhattan());
        StdOut.println(twin);
    }*/
}
