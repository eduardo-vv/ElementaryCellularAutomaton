package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PreferencesFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	MainFrame frame;
	SimulationPanel panel;
	JButton confirmBtn = new JButton("Confirm");
	JLabel defaultCellSizeLbl = new JLabel("Default Cell Size (px): ");
	JLabel scaleLbl = new JLabel("Scale: ");
	JLabel nCellsLbl = new JLabel("Number of cells: ");
	JLabel timeStepsLbl = new JLabel("Time Steps: ");
	JLabel resolutionLbl = new JLabel("Resolution: ");
	JLabel resValueLbl = new JLabel("WidthxHeight");
	JLabel delayLbl = new JLabel("Delay (ms): ");
	JLabel warningLbl = new JLabel("Type an integer number!");
	
	
	JTextField defaultCellSizeTxtF;
	JTextField scaleTxtF;
	JTextField nCellsTxtF;
	JTextField timeStepsTxtF;
	JTextField delayTxtF;
	
	PreferencesFrame(MainFrame frame, SimulationPanel panel){
		//frame setup
		this.panel = panel;
		this.frame = frame;
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(260, 300);
		this.setLayout(null);
		
		//sloppy string casting
		defaultCellSizeTxtF  = new JTextField(String.format("%1$d", panel.getDefaultCellSize()));
		scaleTxtF            = new JTextField(String.format("%1$d", panel.getScale()));
		nCellsTxtF           = new JTextField(String.format("%1$d", panel.getNumCells()));
		timeStepsTxtF        = new JTextField(String.format("%1$d", panel.getSteps()));
		delayTxtF            = new JTextField(String.format("%1$d", frame.getDelay()));
		
		
		resValueLbl.setText(String.format("%1$d x %2$d", 
				    panel.getDefaultCellSize()*panel.getScale()*panel.getNumCells(),
					panel.getDefaultCellSize()*panel.getScale()*panel.getSteps()));
		
		//the ideal would be to create a font class
		delayLbl.setBounds(20, 20, 170, 22);
		delayLbl.setFont(new Font("Serif", Font.PLAIN, 14));
		delayTxtF.setBounds(190, 20, 50, 22);
		delayTxtF.createToolTip();
		delayTxtF.setToolTipText("Artificial delay between steps");
				
		//setting up components in the frame
		defaultCellSizeLbl.setBounds(20, 50, 170, 22);
		defaultCellSizeLbl.setFont(new Font("Serif", Font.PLAIN, 14));
		defaultCellSizeTxtF.setBounds(190, 50, 50, 22);
		
		textFieldListener(defaultCellSizeTxtF);		
		
		scaleLbl.setBounds(20, 80, 150, 22);
		scaleLbl.setFont(new Font("Serif", Font.PLAIN, 16));
		scaleTxtF.setBounds(190, 80, 50, 22);
		
		textFieldListener(scaleTxtF);
		
		nCellsLbl.setBounds(20, 110, 150, 22);
		nCellsLbl.setFont(new Font("Serif", Font.PLAIN, 16));
		nCellsTxtF.setBounds(190, 110, 50, 22);
		
		textFieldListener(nCellsTxtF);
		
		timeStepsLbl.setBounds(20, 140, 150, 22);
		timeStepsLbl.setFont(new Font("Serif", Font.PLAIN, 16));
		timeStepsTxtF.setBounds(190, 140, 50, 22);
		
		textFieldListener(timeStepsTxtF);
		
		resolutionLbl.setBounds(20, 170, 170, 22);
		resolutionLbl.setFont(new Font("Serif", Font.PLAIN, 16));
		
		resValueLbl.setBounds(120, 170, 170, 22);
		resValueLbl.setFont(new Font("Serif", Font.PLAIN, 16));
		
		warningLbl.setBounds(20, 200, 230, 22);
		warningLbl.setHorizontalAlignment(JLabel.CENTER);
		warningLbl.setFont(new Font("Serif", Font.PLAIN, 16));
		warningLbl.setForeground(Color.red);
		warningLbl.setVisible(false);
		
		confirmBtn.setBounds(20, 220, 230, 26);
		confirmBtn.setFocusable(true);		
		
		this.add(delayLbl);
		this.add(delayTxtF);		
		
		this.add(defaultCellSizeLbl);
		this.add(defaultCellSizeTxtF);
		
		this.add(scaleLbl);
		this.add(scaleTxtF);
		
		this.add(nCellsLbl);
		this.add(nCellsTxtF);
		
		this.add(timeStepsLbl);
		this.add(timeStepsTxtF);
		
		this.add(resolutionLbl);
		this.add(resValueLbl);
		
		confirmBtn.addActionListener(this);
		this.add(confirmBtn);
		
		this.add(warningLbl);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);	
		
	}
	
	private void textFieldListener(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
    		
            public void insertUpdate(DocumentEvent e) {
            	setResolutionLabel();
            }
            public void removeUpdate(DocumentEvent e) {
            	setResolutionLabel();
            }
            public void changedUpdate(DocumentEvent e) {
            	setResolutionLabel();
            }

        });
	}
	
	//dynamically calculates resolution based on field values
	private void setResolutionLabel() {
		int defaultCellSize = panel.getDefaultCellSize();
		int scale = panel.getScale();
		int nCells = panel.getNumCells();
		int steps = panel.getSteps();
    	
		//error handling	
		if(checkIfNumber(defaultCellSizeTxtF.getText()) &&
			checkIfNumber(scaleTxtF.getText())  &&
			checkIfNumber(nCellsTxtF.getText()) &&
			checkIfNumber(timeStepsTxtF.getText())) {	
			confirmBtn.setEnabled(true);
			warningLbl.setVisible(false);
			//if, for some reason, the user uses a decimal number
			//it catches as a double and casts as int
			defaultCellSize = (int)Double.parseDouble(defaultCellSizeTxtF.getText());
			scale = (int)Double.parseDouble(scaleTxtF.getText());
			nCells = (int)Double.parseDouble(nCellsTxtF.getText());
			steps = (int)Double.parseDouble(timeStepsTxtF.getText());
			resValueLbl.setText(String.format("%1$d x %2$d", 
					defaultCellSize*scale*nCells, defaultCellSize*scale*steps));
		} else {
			confirmBtn.setEnabled(false);
			resValueLbl.setText("Width x Height");
			warningLbl.setVisible(true);
			//JOptionPane.showMessageDialog(this, "Please type an integer number!");
		}
	}
	
	//return true if parsing is possible
	//should be false for anything that is not a number or null
	public boolean checkIfNumber(String string) {
		try {
			Double.parseDouble(string);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int defaultCellSize, scale, nCells, steps, delay;		
			if(e.getSource() == confirmBtn) {				
				//if, for some reason, the user uses a decimal number
				//with a . as separator it catches as a double and casts as int
				defaultCellSize = (int)Double.parseDouble(defaultCellSizeTxtF.getText());
				scale = (int)Double.parseDouble(scaleTxtF.getText());
				nCells = (int)Double.parseDouble(nCellsTxtF.getText());
				steps = (int)Double.parseDouble(timeStepsTxtF.getText());
				delay = (int)Double.parseDouble(delayTxtF.getText());				
			
				frame.dispose();
				this.dispose();
				
				//create a new frame with a new panel with the
				//desired changes
				MainFrame frame  = new MainFrame(
						new SimulationPanel(
								defaultCellSize, 
								scale, 
								nCells, 
								steps)
						);
				frame.setDelay(delay);
				frame.simulate(MainFrame.remember);			
			
		}
		
	}
	

}
