package hash_code.contest_2017.practice.pizza;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.ValueNode;
import hash_code.algorithms.graph.nodes.AbstractValueNode;
import hash_code.contest_2017.practice.common.ACTIONS_TOTAL;

public class PizzaGridValue extends AbstractValueNode {

	// specific attributes for the problem
	private int[][] grid;
	private List<Slide> slides;

	private int minNumberIngredients;
	private int maxTotalNumberCells;

	public PizzaGridValue(int[][] grid, int L, int H) {
		this.grid = grid;
		slides = new ArrayList<Slide>();
		minNumberIngredients = L;
		maxTotalNumberCells = H;
	}

	public PizzaGridValue(ValueNode valueNode) {
		grid = ((PizzaGridValue) valueNode).getGrid();
		minNumberIngredients = ((PizzaGridValue) valueNode).getMinNumberIngredients();
		maxTotalNumberCells = ((PizzaGridValue) valueNode).getMaxTotalNumberCells();
		
		// save the previous slides
		slides = new ArrayList<Slide>();
		for(Slide s: ((PizzaGridValue)valueNode).getSlides())
			add(s);
	}

	@Override
	public String toString() {
		String temp = "min ingredients: "+minNumberIngredients+", max cells: "+maxTotalNumberCells+"\n";
		
		for(int i=0; i < grid.length; i++){
			for(int j=0; j < grid[i].length; j++){
				temp+= (char)grid[i][j]+" ";
			}
			temp+="\n";
		}
		
		temp+=getScore() + "\n";
		Action[] slides = getPath();
		for (Action s : slides) {
			temp += s.toString() + " ["+((ActionPizza)s).getSlide().getScore()+"]" + "\n";
		}
		return temp;
	}


	// we implement the equals method	
	@Override
	public boolean equals(Object object) {
		if (object == null) return false;
	    if (object == this) return true;
	    if (!(object instanceof ValueNode))return false;
	    ValueNode objectValueNode = (ValueNode)object;
	    if(objectValueNode.hashCode()==this.hashCode())
	    	return true;
		return false;
	}
	
	// we implement the hashcode over the slides field	
	@Override
	public int hashCode() {	
		int hashCode = 1;
		Iterator<Slide> i = slides.iterator();
		while (i.hasNext()) {
			Object obj = i.next();
			hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
		}
		return hashCode;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see hash_code.algorithms.graph.AbstractValueNode#getScore()
	 */
	@Override
	public int getScore() {
		return score;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hash_code.algorithms.graph.interfaces.ValueNode#getPath()
	 */
	@Override
	public Action[] getPath() {

		List<Action> actionsList = new ArrayList<Action>();

		// for each slides
		for (int i = 0; i < slides.size(); i++)
			actionsList.add(new ActionPizza(slides.get(i)));

		Action[] act = new Action[actionsList.size()];
		return actionsList.toArray(act);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hash_code.algorithms.graph.AbstractState#getActions()
	 */
	@Override
	public Action[] getActions() {

		if (actions != null)
			return actions;
		
		List<Action> actionsList = new ArrayList<Action>();
		

		// for all actions
		for(Action act: ACTIONS_TOTAL.actionsList){
			if(!isEqual(act, slides) && !isInside(act, slides)){
				actionsList.add(act);
			}
		}

		System.out.println("** ACTIONS CAN BE EXECUTED **");
		System.out.println("["+actionsList.size()+"]"+actionsList.toString());
		System.out.println("");
		
		actions = new Action[actionsList.size()];
		return actions = actionsList.toArray(actions);

	}

	private boolean isEqual(Action act, List<Slide> slides2) {
		Slide slide;
		for (int i = 0; i < slides2.size(); i++) {
			slide = slides2.get(i);
			if(slide.equals(((ActionPizza)act).getSlide()))
				return true;
		}
		return false;
	}

	/**
	 * Verify if the new Action is not inside the previous slides
	 * @param act Action ( new slide )
	 * @param slides2 Set of slides
	 * @return true if the action intersect any previous slide
	 */
	private boolean isInside(Action act, List<Slide> slides2) {
		Slide slide;
		Slide actionSlide;
		for (int i = 0; i < slides2.size(); i++) {
			slide = slides2.get(i);
			actionSlide = ((ActionPizza)act).getSlide();
			
			// for each cell, verify if the cell is not inside the slide
			for(int r=actionSlide.getR1(); r <= actionSlide.getR2(); r++){
				for(int c=actionSlide.getC1(); c <= actionSlide.getC2();c++){
					if(slide.isInside(r, c))
						return true;	
				}
			}
		}
		return false;
	}

	/**
	 * add a given slide increasing the score
	 * 
	 * @param slide
	 *            Slide to add
	 */
	public void add(Slide slide) {
		score += slide.getScore();
		slides.add(slide);
	}

	public void removeIndex(int indexSlide) {
		Slide temp = slides.remove(indexSlide);
		score-=temp.getScore();
	}

	/**
	 * @return retrieve the list of slides
	 */
	public List<Slide> getSlides() {
		return slides;
	}

	/**
	 * @return retrieves the grid
	 */
	public int[][] getGrid() {
		return grid;
	}

	/**
	 * @return retrieve the constraints of the minimum number of ingredients
	 */
	public int getMinNumberIngredients() {
		return minNumberIngredients;
	}

	/**
	 * @return retrieve the constraints of the maximum number of cells
	 */
	public int getMaxTotalNumberCells() {
		return maxTotalNumberCells;
	}

}
