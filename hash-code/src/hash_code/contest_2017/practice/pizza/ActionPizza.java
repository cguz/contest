package hash_code.contest_2017.practice.pizza;

import java.util.List;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.Node;
import hash_code.algorithms.graph.interfaces.ValueNode;
import hash_code.algorithms.graph.nodes.NodeImp;

public class ActionPizza implements Action {

	private Slide slide;
	private int indexSlide;
	
	public ActionPizza(Slide slide, int i) {
		indexSlide = i;
		this.slide = slide;
	}

	public ActionPizza(Slide slide) {
		this.slide = slide;
	}

	@Override
	public String toString() {
		return slide.getR1()+" "+slide.getC1()+" "+slide.getR2()+" "+slide.getC2();
	}

	/* (non-Javadoc)
	 * @see hash_code.algorithms.graph.interfaces.Action#execute(hash_code.algorithms.graph.interfaces.Node)
	 */
	@Override
	public Node execute(Node current) {
		
		Slide newSlide;

		// we create the new node value
		ValueNode newNode = new PizzaGridValue(current.getValue());
		
		// we get the current slides of the pizza
		List<Slide> currentSlides = ((PizzaGridValue)current.getValue()).getSlides();
		
		if(currentSlides.size() == 0){
			// we insert the first slide
			((PizzaGridValue)newNode).add(slide);
		}else{
			for(int i=0; i < currentSlides.size(); i++){
				if(i!=indexSlide){
					((PizzaGridValue)newNode).add(currentSlides.get(i));
				}else{
					// we insert the first slide
					((PizzaGridValue)newNode).add(slide);
					
					// we insert the second slide
					newSlide = currentSlides.get(indexSlide);
					((PizzaGridValue)newNode).add(new Slide(slide.getR1(),slide.getC2()+1, newSlide.getR2(), newSlide.getC2()));
				}
			}
		}
		
		return new NodeImp(newNode);
	}

	public Slide getSlide() {
		return slide;
	}

}
