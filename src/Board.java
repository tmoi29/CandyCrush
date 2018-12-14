import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JPanel{
	
	private Tile[][] board;
	private int score;
	private int moves;
	private JFrame f;
	private JLabel sl;
	private JLabel ml;
	private String name;
	private JLabel lb;
	Set<String> selected;
	
	
	public Board(JFrame frame, JLabel s, JLabel m, JLabel leaderboard){
		f = frame;
		this.setLayout(new GridLayout(6,6));
		sl = s;
		ml = m;
		selected = new TreeSet<String>();
		lb = leaderboard;
		reset();
	}
	
	public Board(int[][] b){
		board = new Tile[6][6];
		for (int i = 0; i < b.length; i ++) {
			for (int j = 0; j < b.length; j++) {
				board[i][j] = new Tile(b[i][j],j,i);
			}
		}
		score = 0;
		moves = 15;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getMoves() {
		return moves;
	}
	
	private void reset(){
		this.removeAll(); 
		
		//LEADERBOARD FUNCTIONS
		String st = "";
        FileInputStream in = null;
        Map<Integer,ArrayList<String>> m = new TreeMap<Integer,ArrayList<String>>();
        int c = 0;
        try {
        	in = new FileInputStream("files/scores.txt");
	       	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	       	String line = null;
	       	while ((line = br.readLine()) != null) {
	       		String[] val = line.split("\\s+");
		       	c ++;
	       		if (m.containsKey(Integer.parseInt(val[1]))) {
	       			m.get(Integer.parseInt(val[1])).add(val[0]);
	       		}
	       		else {
	       			ArrayList<String> a = new ArrayList<String>();
	       			a.add(val[0]);
	       			m.put(Integer.parseInt(val[1]),a);
	       		}
	       	}
	        
	       	br.close();
	    }
        catch(Exception e){
        	
        }
        
        Iterator<Integer> it = m.keySet().iterator();
        Iterator<String> it2;
        while (it.hasNext()) {
        	int v = it.next();
        	c -= m.get(v).size();
        	it2 = m.get(v).iterator();
        	if (c < 5) {
	        	while (it2.hasNext()) {
	        		String n = it2.next();
	        		st = (c+1) + ".&nbsp;" + n + "&nbsp;&nbsp;&nbsp;&nbsp;" + v + "<br>" + st;
	        	}
        	}
        }
        
        lb.setText("<html>&nbsp;&nbsp;LEADERBOARD&nbsp;&nbsp;<br><br>&nbsp;&nbsp;Name&nbsp;&"
        		+ "nbsp;&nbsp;&nbsp;Score&nbsp;&nbsp;<br>" + st + "</html>");
        
        //NAME INPUT
        
		String n = JOptionPane.showInputDialog("What's your name? No spaces please!");
		if (n != "") {
			name = n;
		}
		else {
			name = "unknown";
		}
		
		//INSTRUCTIONS 
		
		JOptionPane.showMessageDialog(f,
			    "<html><center><strong>INSTRUCTIONS</strong><center><br><p>It's Candy Crush! "
			    + "The game that everyone was addicted to!</p> <br>To play, all you need to do is "
			    + "swap adjacent candies to form a row or <br>column of 3 or more of the same candies."
			    + " These groups of candies will be <br>eliminated and the candies above them will" 
			    + " shift down. Each eliminated<br> candy is worth one point. Try to eliminate as many "
			    + "candies as <br>possible within 15 swaps and move up the leaderboard!</html>");
		
		board = new Tile[6][6];
		for (int i = 0; i < board.length; i ++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = new Tile((int) (Math.random()*5),j,i);
			}
		}
		update();
		moves = 15;
		score = 0;
		sl.setText("Score: " + score);
		ml.setText("Moves: " + moves);
		draw(this,sl, ml,selected);
	}
	
	public Map<Integer,TreeSet<Integer>> checkBoard() {
		Map<Integer,TreeSet<Integer>> m = new TreeMap<Integer,TreeSet<Integer>>();
		
		//Check by Rows
		for (int i = 0; i < board.length; i ++) {
			for (int j = 0; j + 2 < board.length; j++) {
				int val = board[i][j].getCandy();
				if ((val == board[i][j+1].getCandy()) && (val == board[i][j+2].getCandy())){
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
						if (val == board[i][j+k].getCandy()) {
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
				int val = board[j][i].getCandy();
				if ((val == board[j+1][i].getCandy()) && (val == board[j+2][i].getCandy())){
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
						if (val == board[j+k][i].getCandy()) {
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
	    			board[i][col].setCandy(board[i-1][col].getCandy());
	    		}
	    	}
	    	for (int i = 0; i < nums.size(); i ++) {
	    		board[i][col].setCandy((int) (Math.random()*5));
	    	}
	    }
	    return ret;
	}
	
	public void runOnce() {
		int val = removeCandy(checkBoard());
		score += val;
		if (val > 0) {
			moves -= 1;
		}
		
	}
	public int update(){
		int ret = 0;
		Map<Integer, TreeSet<Integer>> m = checkBoard();
		while (m.size() > 0) {
			ret += removeCandy(m);
			m = checkBoard();
		}
		return ret;
	}
	
	public boolean validSwap(int[] a, int[] b) {
		int ax = a[0];
		int ay = a[1];
		int bx = b[0];
		int by = b[1];
		
		if ((Math.abs(ax - bx) + Math.abs(ay-by)) < 2) {
			int old = board[ay][ax].getCandy();
			board[ay][ax].setCandy(board[by][bx].getCandy());
			board[by][bx].setCandy(old);
			if (checkBoard().keySet().size() > 0) {
				return true;
				
			}
			else {
				board[by][bx].setCandy(board[ay][ax].getCandy());
				board[ay][ax].setCandy(old);
				return false;
			}
		}
		return false;
	}
	public int[][] getBoard(){
		int[][] ret = new int[board.length][board.length];
		
		for (int i = 0; i < board.length; i ++) {
			for (int j = 0; j < board.length; j++) {
				ret[i][j] = board[i][j].getCandy();
			}
		}
		return ret;
	}
	
	public static boolean compareBoards(Board a, Board b) {
		int[][] aVal = a.getBoard();
		int[][] bVal = b.getBoard();
		for (int i = 0; i < aVal.length; i ++) {
			for (int j = 0; j < aVal.length; j++) {
				int candyA = aVal[i][j];
				int candyB = bVal[i][j];
				if ((candyA != -1) && (candyB != -1)) {
					if (candyA != candyB) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void deselect(int x, int y) {
		board[y][x].deselect();
	}
	
	public void draw(Board p, JLabel scoreL, JLabel movesL, Set<String> selected) {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board.length; x++) {
				Tile t = board[y][x];
				t.addActionListener(new ActionListener()
				{
				  public void actionPerformed(ActionEvent e)
				  {
					  int[] pt = t.getPoint();
					  if (t.getSelected()) {
						  selected.remove("(" + pt[0] + "," + pt[1] +")");
						  t.deselect();
					  }
					  else {
						  selected.add("(" + pt[0] + "," + pt[1] +")");
						  t.select();
						  if (selected.size() == 2) {
							  Iterator<String> it = selected.iterator();
							  int[][] pts = new int[2][2];
							  int c = 0;
							  while (it.hasNext()) {
								  String st = it.next();
								  String[] point = st.split(",");
								  int x = Integer.parseInt(point[0].substring(1));
								  int y = Integer.parseInt(point[1].substring(0,point[1].length()-1));
								  
								  pts[c] = new int[] {x,y};
								  c ++;	  	  
							  }
							  if (validSwap(pts[0], pts[1])) {
								  p.repaint();
								  score += update();
								  moves -= 1;
								  scoreL.setText("Score: " + score);
								  movesL.setText("Moves: " + moves);
								  if (moves == 0) {
									  try {
										  BufferedWriter writer = new BufferedWriter(new FileWriter("files/scores.txt", true));
										  writer.write(name + " " + score +"\n");
										  writer.close();
									  }
									  catch(Exception ex) {
									  
									  }
									  
									  int reply = JOptionPane.showConfirmDialog(
											  null, "Game Over! Do you want to play again?",
											  "Continue?", JOptionPane.YES_NO_OPTION);
									  if (reply == JOptionPane.YES_OPTION) {
								          p.reset();
								          f.revalidate();
								        }
								        else {
								           f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
								           System.exit(0);
								        }
								  }
								  
								  
							  }
							  deselect(pts[0][0], pts[0][1]);
							  deselect(pts[1][0], pts[1][1]);
							  selected.clear();
							  p.repaint();
							  
						  }
					  }
				  }
				});
				p.add(t);
			} 
		}                    
	}
	
	
	
}

