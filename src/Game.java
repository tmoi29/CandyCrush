/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        
    	final JFrame frame = new JFrame("CANDY CRUSH!");
        frame.setLocation(300, 300);

        final JPanel statusPanel = new JPanel();
        final JLabel scoreL = new JLabel();
        scoreL.setText("Score: 0");
        statusPanel.add(scoreL);
        final JLabel movesL = new JLabel();
        movesL.setText("Moves: 15");
        statusPanel.add(movesL);
        frame.add(statusPanel, BorderLayout.SOUTH);
        
        final JLabel leaderboard = new JLabel();
        
        JPanel leader = new JPanel();
        leader.add(leaderboard);
        frame.add(leader, BorderLayout.EAST);
        
        final JPanel gameBoard = new Board(frame, scoreL, movesL, leaderboard);
        frame.add(gameBoard, BorderLayout.WEST);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
   

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}