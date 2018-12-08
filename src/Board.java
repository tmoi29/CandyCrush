import java.util.*;

public class Board {
	
	int[][] board;
	
	public Board(){
		createBoard();
	}
	
	private void createBoard() {
		board = new int[6][6];
		for (int i = 0; i < board.length; i ++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = (int) (Math.random()*5);
			}
		}
		printBoard();
		System.out.println("=============");
		update();
	}
	
	private Map<Integer,TreeSet<Integer>> checkBoard() {
		Map<Integer,TreeSet<Integer>> m = new TreeMap<Integer,TreeSet<Integer>>();
		
		//Check by Rows
		for (int i = 0; i < board.length; i ++) {
			for (int j = 0; j + 2 < board.length; j++) {
				int val = board[i][j];
				if ((val == board[i][j+1]) && (val == board[i][j+2])){
					for (int k = 0; k < 3; k++) {
						if (m.containsKey(j+k)) {
							m.get(j+k).add(i);
						}
						else {
							TreeSet<Integer> n = new TreeSet<Integer>();
							n.add(i);
							m.put(j+k, n);
						}
					}
					for (int k = 3; j+k < board.length; k ++) {
						if (val == board[i][j+k]) {
							if (m.containsKey(j+k)) {
								m.get(j+k).add(i);
							}
							else {
								TreeSet<Integer> n = new TreeSet<Integer>();
								n.add(i);
								m.put(j+k, n);
							}
						}
						else {
							break;
						}
					}
				}
			}
		}
		//Check by Columns
		for (int i = 0; i < board.length; i ++) {
			for (int j = 0; j + 2 < board.length; j++) {
				int val = board[j][i];
				if ((val == board[j+1][i]) && (val == board[j+2][i])){
					for (int k = 0; k < 3; k++) {
						if (m.containsKey(i)) {
							m.get(i).add(j+k);
						}
						else {
							TreeSet<Integer> n = new TreeSet<Integer>();
							n.add(j+k);
							m.put(i, n);
						}
					}
					for (int k = 3; j+k < board.length; k ++) {
						if (val == board[j+k][i]) {
							if (m.containsKey(i)) {
								m.get(i).add(j+k);
							}
							else {
								TreeSet<Integer> n = new TreeSet<Integer>();
								n.add(j+k);
								m.put(i, n);
							}
						}
						else {
							break;
						}
					}
				}
			}
		}
		Iterator<Integer> it = m.keySet().iterator();
	    while(it.hasNext()){
	    	int c = it.next();
	    	Set<Integer> nums = m.get(c);
	    	Iterator<Integer> it2 = nums.iterator();
	    	while(it2.hasNext()) {
	    		System.out.println("("+it2.next()+"," + c + ")");
	    		
	    	}
	    }
	    return m;
	}
	
	private int removeCandy(Map<Integer,TreeSet<Integer>> pos) {
		int ret = 0;
		Iterator<Integer> it = pos.keySet().iterator();
	    while (it.hasNext()){
	    	int col = it.next();
	    	Set<Integer> nums = pos.get(col);
	    	Iterator<Integer> it2 = nums.iterator();
	    	while(it2.hasNext()) {
	    		int row = it2.next();
	    		ret ++;
	    		for (int i = row; i > 0; i --) {
	    			board[i][col] = board[i-1][col];
	    		}
	    	}
	    	for (int i = 0; i < nums.size(); i ++) {
	    		board[i][col] = (int) (Math.random()*5);
	    	}
	    }
	    System.out.println(ret);
	    return ret;
	}
	
	public int update() {
		int ret = 0;
		Map<Integer, TreeSet<Integer>> m = checkBoard();
		System.out.println(m.size());
		while (m.size() > 0) {
			ret += removeCandy(m);
			printBoard();
			System.out.println("=======================");
			m = checkBoard();
		}
		printBoard();
		return ret;
	}
	
	public boolean validSwap(int[] a, int[] b) {
		int ax = a[0];
		int ay = a[1];
		int bx = b[0];
		int by = b[1];
		
		if ((Math.abs(ax - bx) + Math.abs(ay-by)) < 2) {
			int old = board[ay][ax];
			board[ay][ax] = board[by][bx];
			board[by][bx] = old;
			if (checkBoard().keySet().size() > 0) {
				return true;
			}
			else {
				board[by][bx] = board[ay][ax];
				board[ay][ax] = old;
				return false;
			}
		}
		return false;
	}
	
	public void printBoard() {
		for (int i = 0; i < board.length; i ++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[i][j]);
				System.out.print(" ");
			}
			System.out.println(" ");
		}
	}
	
	public static void main(String[] args) {
		Board b = new Board();
		
	}
}

