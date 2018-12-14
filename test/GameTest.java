import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import org.junit.Test;
import java.util.*;

public class GameTest {
	Board board;
	@Before
	public void setup() {
		int[][] b = new int[][] 
				{{0,1,0,2,3,1},
				 {0,2,3,4,1,3},
				 {3,0,1,3,2,2},
				 {2,4,1,4,2,3},
				 {4,1,4,2,3,3},
				 {3,2,1,2,3,2}};
		board = new Board(b);	
	}
    
    @Test
    public void testValidSwapVerticalAndClear(){
        assertTrue(board.validSwap(new int[] {3,4}, new int[] {4,4}));
        int[][] a = new int[][] 
				{{0,1,0,2,3,1},
				 {0,2,3,4,1,3},
				 {3,0,1,3,2,2},
				 {2,4,1,4,2,3},
				 {4,1,4,3,2,3},
				 {3,2,1,2,3,2}};
		Board board2 = new Board(a);
		
		//Swap is successful
        assertTrue(Board.compareBoards(board, board2));
        
        //One clear
        a = new int[][] 
				{{0,1,0,2,-1,1},
				 {0,2,3,4,-1,3},
				 {3,0,1,3,-1,2},
				 {2,4,1,4,3,3},
				 {4,1,4,3,1,3},
				 {3,2,1,2,3,2}};
		board2 = new Board(a);
		board.runOnce();
		assertTrue(Board.compareBoards(board, board2));
		assertEquals(board.getScore(),3);
		assertEquals(board.getMoves(),14);
    }
    
    @Test
    public void testValidSwapHorizontalAndClear(){
        assertTrue(board.validSwap(new int[] {1,4}, new int[] {1,3}));
        int[][] a = new int[][] 
        		{{0,1,0,2,3,1},
				 {0,2,3,4,1,3},
				 {3,0,1,3,2,2},
				 {2,1,1,4,2,3},
				 {4,4,4,2,3,3},
				 {3,2,1,2,3,2}};
		Board board2 = new Board(a);
		
		//Swap is successful
        assertTrue(Board.compareBoards(board, board2));
        
        //One clear
        a = new int[][] 
        		{{-1,-1,-1,2,3,1},
				 {0,1,0,4,1,3},
				 {0,2,3,3,2,2},
				 {3,0,1,4,2,3},
				 {2,1,1,2,3,3},
				 {3,2,1,2,3,2}};
		board2 = new Board(a);
		board.runOnce();
		assertTrue(Board.compareBoards(board, board2));
		assertEquals(board.getScore(),3);
		assertEquals(board.getMoves(),14);
    }
    
    @Test
    public void testInvalidNotAdjacentSwapAndClear(){
        assertFalse(board.validSwap(new int[] {0,4}, new int[] {4,4}));
        int[][] a = new int[][] 
				{{0,1,0,2,3,1},
				 {0,2,3,4,1,3},
				 {3,0,1,3,2,2},
				 {2,4,1,4,2,3},
				 {4,1,4,2,3,3},
				 {3,2,1,2,3,2}};
		Board board2 = new Board(a);
		//Swap is unsuccessful
        assertTrue(Board.compareBoards(board, board2));
        
		assertEquals(board.getScore(),0);
		assertEquals(board.getMoves(),15);
    }
    
    @Test
    public void testInvalidAdjacentSwapAndClear(){
        assertFalse(board.validSwap(new int[] {1,3}, new int[] {1,2}));
        int[][] a = new int[][] 
				{{0,1,0,2,3,1},
				 {0,2,3,4,1,3},
				 {3,0,1,3,2,2},
				 {2,4,1,4,2,3},
				 {4,1,4,2,3,3},
				 {3,2,1,2,3,2}};
		Board board2 = new Board(a);
		//Swap is unsuccessful
        assertTrue(Board.compareBoards(board, board2));
        
		assertEquals(board.getScore(),0);
		assertEquals(board.getMoves(),15);
    }
    
    @Test
    public void testOverlapSwapAndClear(){
        int[][] a = new int[][] 
				{{0,1,3,2,3,1},
				 {0,2,3,4,1,3},
				 {3,3,1,3,2,2},
				 {2,4,3,4,2,3},
				 {4,1,4,2,3,3},
				 {3,0,2,2,3,2}};
		board = new Board(a);
		assertTrue(board.validSwap(new int[] {2,3}, new int[] {2,2}));
		
		a = new int[][] 
				{{0,1,3,2,3,1},
				 {0,2,3,4,1,3},
				 {3,3,3,3,2,2},
				 {2,4,1,4,2,3},
				 {4,1,4,2,3,3},
				 {3,0,2,2,3,2}};
		Board board2 = new Board(a);
		
		//Swap is successful
        assertTrue(Board.compareBoards(board, board2));
        
        //One clear
        a = new int[][] 
				{{-1,-1,-1,-1,3,1},
				 {0,1,-1,2,1,3},
				 {0,2,-1,4,2,2},
				 {2,4,1,4,2,3},
				 {4,1,4,2,3,3},
				 {3,0,2,2,3,2}};
		board2 = new Board(a);
		board.runOnce();
		assertTrue(Board.compareBoards(board, board2));
		assertEquals(board.getScore(),6);
		assertEquals(board.getMoves(),14);
    }
    
    @Test
    public void testMoreThanThreeSwapAndClear(){
        int[][] a = new int[][] 
				{{0,1,3,2,3,1},
				 {0,2,0,4,1,3},
				 {3,3,1,3,2,2},
				 {2,4,3,4,2,3},
				 {4,1,4,2,3,3},
				 {3,0,2,2,3,2}};
		board = new Board(a);
		assertTrue(board.validSwap(new int[] {2,3}, new int[] {2,2}));
		
		a = new int[][] 
				{{0,1,3,2,3,1},
				 {0,2,0,4,1,3},
				 {3,3,3,3,2,2},
				 {2,4,1,4,2,3},
				 {4,1,4,2,3,3},
				 {3,0,2,2,3,2}};
		Board board2 = new Board(a);
		
		//Swap is successful
        assertTrue(Board.compareBoards(board, board2));
        
        //One clear
        a = new int[][] 
				{{-1,-1,-1,-1,3,1},
				 {0,1,3,2,1,3},
				 {0,2,0,4,2,2},
				 {2,4,1,4,2,3},
				 {4,1,4,2,3,3},
				 {3,0,2,2,3,2}};
		board2 = new Board(a);
		board.runOnce();
		assertTrue(Board.compareBoards(board, board2));
		assertEquals(board.getScore(),4);
		assertEquals(board.getMoves(),14);
    }

}
