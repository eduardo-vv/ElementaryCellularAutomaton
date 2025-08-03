package main;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class CreditsFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	JTextArea credits = new JTextArea(10, 20);
	
	CreditsFrame(){
		credits.setEditable(false);
		credits.setLineWrap(true);
		credits.setFont(new Font("Serif", Font.PLAIN, 16));
		credits.setText("[1] Wikipedia. Available at: https://en.wikipedia.org/wiki/Elementary_cellular_automaton\n\n"
				+ "[2] Wolfram. Available at: https://mathworld.wolfram.com/ElementaryCellularAutomaton.html.");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(credits);        
        this.pack();
        this.setVisible(true);        

		
	}

}
