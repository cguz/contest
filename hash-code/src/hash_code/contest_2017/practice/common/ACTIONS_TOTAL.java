package hash_code.contest_2017.practice.common;

import java.util.ArrayList;
import java.util.List;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.contest_2017.practice.pizza.ActionPizza;
import hash_code.contest_2017.practice.pizza.Slide;

public class ACTIONS_TOTAL {

	public static List<Action> actionsList;
	
	public static void generateActions(int[][] grid, int l, int h) {

		actionsList = new ArrayList<Action>();
		
		Slide slide;
		for (int startRow = 0; startRow < grid.length; startRow++) {
			for (int srtarCol = 0; srtarCol < grid[startRow].length; srtarCol++) {
				
				for(int r = startRow; r < grid.length; r++){
					for(int c = startRow; c < grid[r].length; ++c){
						slide = new Slide(startRow, srtarCol, r, c);
						if (isValidSlide(slide, grid, l, h)) {
							actionsList.add(new ActionPizza(slide));
						}
					}
				}
			}
		}
		
	}
		

	/**
	 * validate if a given slide is valid or not
	 * 
	 * @param slide Slide to validate
	 * @param grid 
	 * @param maxTotalNumberCells 
	 * @param minNumberIngredients 
	 * @return true if the slide is valid. Otherwise false
	 */
	private static boolean isValidSlide(Slide slide, int[][] grid, int minNumberIngredients, int maxTotalNumberCells) {

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

}
