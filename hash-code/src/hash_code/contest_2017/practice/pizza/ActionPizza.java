package hash_code.contest_2017.practice.pizza;

import java.util.List;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.Node;
import hash_code.algorithms.graph.interfaces.ValueNode;
import hash_code.algorithms.graph.nodes.NodeImp;

public class ActionPizza implements Action {

	private Slide slide;
	
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
		
		// create the new node value
		ValueNode newNode = new PizzaGridValue(current.getValue());
		
		// get the current slides
		List<Slide> currentSlides = ((PizzaGridValue)current.getValue()).getSlides();
		
		// insert the new slide
		((PizzaGridValue)newNode).add(slide);
		
		// find if we need to split the slide
		int indexSlide = findIndexSlide(currentSlides);
		if(indexSlide != -1){
			
			Slide temp = currentSlides.get(indexSlide);
			
			// remove the previous slide
			((PizzaGridValue)newNode).removeIndex(indexSlide);
			
			((PizzaGridValue)newNode).add(new Slide(slide.getR1(),slide.getC2()+1, temp.getR2(), temp.getC2()));
			
		}
		
		return new NodeImp(newNode);
	}
	

	private int findIndexSlide(List<Slide> currentSlides) {
		int indexSlide = -1;
		for(int i=0; i < currentSlides.size(); ++i){
			Slide sli = currentSlides.get(i);
			if(sli.contains(slide)){
				indexSlide = i;
				break;
			}
		}
		return indexSlide;
	}
	

	public Slide getSlide() {
		return slide;
	}

}
