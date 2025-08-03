package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class SimulationPanel extends JPanel{
    private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private Color foregroundColor = Color.white;
	private Color backgroundColor = new Color(0x2B2B2B);
	private int defaultCellSize;
	private int scale;
	private int cellSize;
	private int nCells;
	private int steps;
	private int width;
	private int height;
	

	public SimulationPanel(int defaultCellSize, int scale, int nCells, int steps) {
		this.defaultCellSize  = defaultCellSize;
		this.scale = scale;
		this.nCells = nCells;
		this.steps  = steps;		
		this.cellSize = defaultCellSize * scale;
		this.width  = cellSize * nCells;
		this.height = cellSize * steps;
		
        this.setPreferredSize(new Dimension(width, height));
        setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        this.setBackground(backgroundColor);
    }
    
	//here the cells are drawn on the image
    public void draw(int col, int row) {
        Graphics2D g2 = getImage().createGraphics();
        g2.setColor(foregroundColor);
        g2.fillRect(col*cellSize, row*cellSize, cellSize, cellSize);
        g2.dispose();
        repaint();
    }
    
    //prepare the image for drawing the array
    public void setImage(String ruleNumber, Color backgroundColor, Color foregroundColor) { 
    	int labelPosX = width/25;
    	int labelPosY = height/10;
    	//error handling
    	if(backgroundColor == null) {
    		backgroundColor = this.backgroundColor; 
    	}
    	
    	if(foregroundColor == null) {
    		foregroundColor = this.foregroundColor; 
    	}
    	
    	setForegroundColor(foregroundColor);
    	setBackgroundColor(backgroundColor);
    	
	    Graphics2D g2 = image.createGraphics();
	    g2.setColor(backgroundColor);
	    g2.fillRect(0, 0, width, height);
	    g2.setColor(foregroundColor);
	    g2.setFont(new Font("Serif", Font.PLAIN, 25*width/1000));
	    //sloppy
	    g2.drawString(ruleNumber, labelPosX, labelPosY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }
    public void setForegroundColor(Color foregroundColor) {
    	this.foregroundColor = foregroundColor;    	
    }
    public void setBackgroundColor(Color backgroundColor) {
    	this.backgroundColor = backgroundColor;    	
    }
	public int getDefaultCellSize() {
		return defaultCellSize;
	}
	public int getScale() {
		return scale;
	}
	public int getNumCells() {
		return nCells;
	}
	public int getSteps() {
		return steps;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

   
	
}
