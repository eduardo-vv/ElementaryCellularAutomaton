package main;

//Calculate the steps of an Elementary Cellular Automaton

//[1] Wikipedia. Available at: https://en.wikipedia.org/wiki/Elementary_cellular_automaton."
//[2] Wolfram. Available at: https://mathworld.wolfram.com/ElementaryCellularAutomaton.html."
public class Calculations {
	private SimulationPanel panel;
	private int steps, nCells;
	private boolean[][] array;
	public Calculations(SimulationPanel panel) {
		this.panel = panel;
		steps = panel.getSteps();
		nCells = panel.getNumCells();
		array = new boolean[steps][nCells];
	}
	
	public boolean[][] getArray(){
		return array;
	}
	
	//setting up the logic rules
	private boolean rule30(boolean p, boolean q, boolean r) {
		return p ^ (q || r);
	}
	
	private boolean rule90(boolean p, boolean q, boolean r) {
		return p ^ r;
	}	
	
	private boolean rule110(boolean p, boolean q, boolean r) {
		return !(p && q && r) && (q||r);
	}
	
	private boolean rule250(boolean p, boolean q, boolean r) {
		return p || r;
	}
	
	private boolean rule254(boolean p, boolean q, boolean r) {
		return p || q || r;
	}

	public boolean[][] evolve(int rule) {
		boolean p, q, r;
	
		
		//initialize the array
	    for(int i = 0; i < panel.getSteps(); i++) {
	    	for(int j = 0; j < panel.getNumCells(); j++) {
	    		array[i][j] = false;
	    	}        	
	    }
	    array[0][panel.getNumCells()/2] = true;
	    
	    //perform the updates
	    for(int i = 1; i < panel.getSteps(); i++) {
			for(int j = 1; j < panel.getNumCells() - 1; j++) {
				p = array[i - 1][ j - 1];
		        q = array[i - 1][j];
		        r = array[i - 1][j + 1];
		            
				switch(rule) {
					case 30:
						array[i][j] = rule30(p, q, r);
						break;
					case 90:
						array[i][j] = rule90(p, q, r);
						break;				
					
					case 110:
						array[i][j] = rule110(p, q, r);
						break;
					case 250:
						array[i][j] = rule250(p, q, r);
						break;
					case 254:
						array[i][j] = rule254(p, q, r);
						break;	
			    }
			}
		}		
		return array;
	}

	public int getSteps() {
		return steps;
	}

	public int getNumCells() {
		return nCells;
	}

}
