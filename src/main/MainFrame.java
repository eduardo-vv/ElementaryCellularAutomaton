package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MainFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private int col = 0, row = 0;
	
    SimulationPanel panel;   
    Calculations calc;    
    Color bColor = new Color(0x2B2B2B);
    Color fColor = Color.white;
    JMenuBar menuBar = new JMenuBar();
    public static int remember = 30;
    private int delay = 25;
    
    //menus
    JMenu fileMenu  = new JMenu("File");
    JMenu editMenu  = new JMenu("Edit");
    JMenu rulesMenu = new JMenu("Rules");     
    
    //edit menu items
    JMenu colorMenu = new JMenu("Color");
    JMenuItem backgroundColorMI = new JMenuItem("Background color");
    JMenuItem foregroundColorMI = new JMenuItem("Foreground color");
    JMenuItem creditsMI = new JMenuItem("Credits");
    
    JMenuItem preferencesMenu = new JMenuItem("Preferences");
    
    //file menu items
    JMenuItem saveFigMI = new JMenuItem("Save figure"); 
    JMenuItem saveAsTxtMI = new JMenuItem("Save as text file"); 
    
    //rules menu items
    JMenuItem rule30MI = new JMenuItem("Rule 30");
    JMenuItem rule90MI = new JMenuItem("Rule 90");
    JMenuItem rule110MI = new JMenuItem("Rule 110");
    JMenuItem rule250MI = new JMenuItem("Rule 250");
    JMenuItem rule254MI = new JMenuItem("Rule 254");
    JMenuItem customRuleMI = new JMenuItem("Custom rule...");
    
    //Rule number
    JLabel ruleLabel = new JLabel("Rule");
    
	MainFrame(SimulationPanel panel){
		this.panel = panel;
		calc = new Calculations(panel); 
		
		this.setTitle("Elementary cellular automaton");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(panel);              
        this.setVisible(true);
        this.setJMenuBar(menuBar);   
        
        menuBar.setFont(new Font("Serif", Font.PLAIN, 22));
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(rulesMenu);
        
        editMenu.add(preferencesMenu);
        preferencesMenu.addActionListener(this);
        editMenu.add(colorMenu);
        
        colorMenu.add(backgroundColorMI);
        backgroundColorMI.addActionListener(this);
        
        editMenu.add(foregroundColorMI);
        colorMenu.add(foregroundColorMI);
        foregroundColorMI.addActionListener(this);
        
        fileMenu.add(saveFigMI);
        saveFigMI.addActionListener(this);
        fileMenu.add(saveAsTxtMI);
        saveAsTxtMI.addActionListener(this);
        fileMenu.add(creditsMI);        
        creditsMI.addActionListener(this);
        
        
        rulesMenu.add(rule30MI);
        rule30MI.addActionListener(this);
        
        rulesMenu.add(rule90MI);
        rule90MI.addActionListener(this);
        
        rulesMenu.add(rule110MI);
        rule110MI.addActionListener(this);
        
        rulesMenu.add(rule250MI);
        rule250MI.addActionListener(this);

        rulesMenu.add(rule254MI);
        rule254MI.addActionListener(this);
        
        rulesMenu.add(customRuleMI);
        customRuleMI.addActionListener(this);

        this.pack();
        this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == rule30MI) {
			simulate(30);
			remember = 30;
		}else if(e.getSource() == rule90MI) {
			simulate(90);
			remember = 90;
		}else if(e.getSource() == rule110MI) {
			simulate(110);
			remember = 110;
		}else if(e.getSource() == rule250MI) {
			simulate(250);
			remember = 250;
		}else if(e.getSource() == rule254MI) {
			simulate(254);
			remember = 254;
		}
		
		if(e.getSource() == saveFigMI) {
			saveFig(panel.getImage(), "rule_"+remember);
		}
		
		if(e.getSource() == backgroundColorMI) {
			bColor =  JColorChooser.showDialog(this, "Choose a color", bColor);
			System.out.println(""+remember);
			simulate(remember);
		}
		
		if(e.getSource() == foregroundColorMI) {
			fColor =  JColorChooser.showDialog(this, "Choose a color", fColor);
			simulate(remember);
		}
		
		if(e.getSource()==preferencesMenu) {
			new PreferencesFrame(this, panel);
		}
		
		if(e.getSource()==creditsMI) {
			new CreditsFrame();
		}
		if(e.getSource() == saveAsTxtMI) {
			saveDataAsTxt(calc, "rule_"+remember);
		}
		
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void saveDataAsTxt(Calculations calc, String name) {
		SwingUtilities.invokeLater(() -> {
            JFileChooser chooser = new JFileChooser();
            
            // Set the chooser to directories only
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogTitle("Select where to save the image");
            chooser.setSelectedFile(new File(name+".txt"));
            
            int returnVal = chooser.showDialog(null, "Save");

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                
                File file = chooser.getSelectedFile();
                String address = file.getAbsolutePath();
                
                //file writing
                try {
                	FileWriter writer = new FileWriter(file);
                	int aux;
                	for(int i = 0; i < calc.getSteps(); i++) {
                		for(int j = 0; j < calc.getNumCells(); j++) {
                			if(calc.getArray()[i][j] == true)
                				aux = 1;
                			else
                				aux = 0;
                			writer.write(String.format("%1$d ", aux));
                		}
                		writer.write("\n");                		
                	}
                	writer.close();
                	System.out.println("The file was saved at: " + address);

    	        } catch (IOException e) {
    	            e.printStackTrace();
    	            System.out.println("Something went wrong!");
    	        }                

            } else {
                System.out.println("Cancelled.");
            }
        });
		
	}
	public void saveFig(BufferedImage image, String name) {
		//sets up a filechooser
		SwingUtilities.invokeLater(() -> {
            JFileChooser chooser = new JFileChooser();
            
            // Set the chooser to directories only
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogTitle("Select where to save the image");
            chooser.setSelectedFile(new File(name+".png"));
            
            int returnVal = chooser.showDialog(null, "Save");

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                
                File file = chooser.getSelectedFile();
                String address = file.getAbsolutePath();
                
                //error handling
                try {
    	            ImageIO.write(image, "png", file);
    	            System.out.println("The image was saved at: "+address);         

    	        } catch (IOException e) {
    	        	System.out.println("Something went wrong!");
    	            e.printStackTrace();  
    	        }

            } else {
                System.out.println("Cancelled.");
            }
        });
                        
	}
	
	public void simulate(int rule) {	
		col = 0; 
		row = 0;
		panel.setImage("Rule "+rule, bColor, fColor);
		
		boolean[][] array = calc.evolve(rule); 		
		Timer timer = new Timer(delay, new ActionListener() {        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	col = 0;
            	while(col < panel.getNumCells()) {
            		if(array[row][col] == true) {
            			panel.draw(col, row);
            		}            			
            		col++;
            	}
            	row ++;       
            	if (row >= panel.getSteps()) {
                    ((Timer) e.getSource()).stop();                    
                }
            }
        });
        timer.start();   
		
	}
	

}
