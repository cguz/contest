package hash_code.contest_2017.practice.pizza;

import java.util.ArrayList;
import java.util.List;

import hash_code.algorithms.graph.AbstractState;
import hash_code.algorithms.graph.interfaces.Action;

public class PizzaGridImp extends AbstractState {

	// specific attributes for the problem
	private int[][] grid;
	private List<Slide> slides; 
	
	private int minNumberIngredients;
	private int maxTotalNumberCells;
	
	
	public PizzaGridImp(int[][] grid, int L, int H) {
		this.grid = grid;
		slides = new ArrayList<Slide>();
		minNumberIngredients = L;
		maxTotalNumberCells = H;
	}

	public void add(Slide slide) {
		score+=slide.getScore();
		slides.add(slide);
	}

	/* (non-Javadoc)
	 * @see hash_code.algorithms.graph.AbstractState#getActions()
	 */
	@Override
	public Action[] getActions() {

		Slide slide, s;
		
		if(actions != null)
			return actions;
		
		if(slides.size() == 0)
			return actions = new Action[0];
		
		List<Action> actionsList = new ArrayList<Action>();
		
		// for each slides 
		for(int i=0; i < slides.size(); i++){
			s = slides.get(i);
			for(int r = s.getR1(); r < s.getR2(); r++){
				for(int c = s.getC1(); c < s.getC2(); c++){
					slide = new Slide(s.getR1(), s.getC1(), r, c);
					if(isValidSlide(slide)){
						actionsList.add(new ActionPizza(slide, i));
					}
				}
			}
		}

		actions = new Action[actionsList.size()];
		return actions = actionsList.toArray(actions);
		
	}

	private boolean isValidSlide(Slide slide) {

		int ingredientsMushroom = 0;
		int ingredientsTomato = 0;
		
		for(int r = slide.getR1(); r < slide.getR2(); r++){
			for(int c = slide.getC1(); c < slide.getC2(); c++){
				if(grid[r][c]=='M'){
					ingredientsMushroom++;
				}
				
				if(grid[r][c]=='T'){
					ingredientsTomato++;
				}
			}
		}
		
		if(ingredientsMushroom >= minNumberIngredients && ingredientsTomato >= minNumberIngredients)
			if(slide.getCells() <= maxTotalNumberCells)
				return true;
			
		return false;
	}

	public List<Slide> getSlides() {
		return slides;
	}

	public int[][] getGrid() {
		return grid;
	}

	public int getMinNumberIngredients() {
		return minNumberIngredients;
	}

	public int getMaxTotalNumberCells() {
		return maxTotalNumberCells;
	}

}
