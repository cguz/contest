package hash_code.contest_2017.practice.pizza;

import java.util.List;

import hash_code.algorithms.graph.NodeImp;
import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.Node;
import hash_code.algorithms.graph.interfaces.State;

public class ActionPizza implements Action {

	private Slide slide;
	private int indexSlideCuted;
	
	public ActionPizza(Slide slide, int i) {
		indexSlideCuted = i;
	}

	@Override
	public Node execute(Node current) {
		
		PizzaGridImp state = ((PizzaGridImp) current);
		
		List<Slide> l_slides = state.getSlides();
		
		Slide toCut;
		
		State newNode = new PizzaGridImp(state.getGrid(), state.getMinNumberIngredients(), state.getMinNumberIngredients());
		for(int i=0; i < l_slides.size(); i++){
			if(i!=indexSlideCuted){
				((PizzaGridImp)newNode).add(l_slides.get(i));
			}else{
				((PizzaGridImp)newNode).add(slide);
				toCut = l_slides.get(indexSlideCuted);
				((PizzaGridImp)newNode).add(new Slide(slide.getR2(), slide.getC2(), toCut.getR2(), toCut.getC2()));
			}
		}
		
		return new NodeImp(newNode);
	}

}
