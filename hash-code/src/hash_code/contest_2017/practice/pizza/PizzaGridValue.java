package hash_code.contest_2017.practice.pizza;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.ValueNode;
import hash_code.algorithms.graph.nodes.AbstractValueNode;

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
		this.grid = ((PizzaGridValue) valueNode).getGrid();
		slides = new ArrayList<Slide>();
		minNumberIngredients = ((PizzaGridValue) valueNode).getMinNumberIngredients();
		maxTotalNumberCells = ((PizzaGridValue) valueNode).getMaxTotalNumberCells();
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

		Slide slide, slide2, s;

		int startRow = 0;
		int startCol = 0;
		
		int minusRow = grid.length;
		int minusCol = grid[0].length;
		
		if (actions != null)
			return actions;
		
		List<Action> actionsList = new ArrayList<Action>();

		// for each slides
		for (int i = 0; i < slides.size(); i++) {
			s = slides.get(i);
			for (int r = s.getR1(); r < s.getR2(); r++) {
				slide = new Slide(s.getR1(), s.getC1(), r, s.getC2());
				slide2 = new Slide(s.getR2(), s.getC1(), s.getR2(), s.getC2());
				if (isValidSlide(slide) && isValidSlide(slide2)) {
					actionsList.add(new ActionPizza(slide, i));
				}
			}
			
			for (int c = s.getC1(); c < s.getC2(); c++) {
				slide = new Slide(s.getR1(), s.getC1(), s.getR2(), c);
				slide2 = new Slide(slide.getR1(), slide.getC2()+1, s.getR2(), s.getC2());
				if (isValidSlide(slide) && isValidSlide(slide2)) {
					actionsList.add(new ActionPizza(slide, i));
				}
			}

			if((s.getR2()-s.getR1()) < minusRow)
				startRow = s.getR2();
			if((s.getC2()-s.getC1()) < minusCol)
				startCol = s.getC2();
		}
		
		// for the remaining part of the grid
		if(startRow != grid.length-1){
			for (int r = startRow+1; r < grid.length; r++) {
				slide = new Slide(startRow, startCol, r, grid[0].length-1);
				if (isValidSlide(slide)) {
					actionsList.add(new ActionPizza(slide, -1));
				}
			}
		}else
			startRow = 0;
		
		if(startCol != grid[startRow].length-1){
			for (int c = startCol+1; c < grid[startRow].length; c++) {
				slide = new Slide(startRow, startCol, grid.length-1, c);
				if (isValidSlide(slide)) {
					actionsList.add(new ActionPizza(slide, -1));
				}
			}
		}

		actions = new Action[actionsList.size()];
		return actions = actionsList.toArray(actions);

	}

	private boolean isInsideAnSlide(int r, int c) {
		Slide slide;
		for (int i = 0; i < slides.size(); i++) {
			slide = slides.get(i);
			if(slide.isInside(r, c))
				return true;
		}
		return false;
	}

	/**
	 * validate if a given slide is valid or not
	 * 
	 * @param slide
	 *            Slide to validate
	 * @return true if the slide is valid. Otherwise false
	 */
	private boolean isValidSlide(Slide slide) {

		int ingredientsMushroom = 0;
		int ingredientsTomato = 0;

		for (int r = slide.getR1(); r <= slide.getR2(); r++) {
			for (int c = slide.getC1(); c <= slide.getC2(); c++) {
				if (grid[r][c] == (int)'M') {
					ingredientsMushroom++;
				}

				if (grid[r][c] == (int)'T') {
					ingredientsTomato++;
				}
			}
		}

		if (ingredientsMushroom >= minNumberIngredients && ingredientsTomato >= minNumberIngredients)
			if (slide.getCells() <= maxTotalNumberCells)
				return true;

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
