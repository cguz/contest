package contests.webOfCode21;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SolutionLetterN {
    
	public enum DIR {
		LEFT, RIGHT, INLINE;
	}

	static SolutionLetterN 	N 				= new SolutionLetterN();
	static int 				less 			= 90;
	static List<PointsTwo> 	twoPoints 		= new ArrayList<PointsTwo>();
	static List<LetterN>	lettersN 		= new ArrayList<LetterN>();
	static List<LetterN>	lettersNUnvalid	= new ArrayList<LetterN>();
	

    public static void main(String[] args) throws FileNotFoundException {

    	//File file = new File("./TEST/algorithms/webOfCode21/LetterN/input-test2.txt");
    	//Scanner in = new Scanner(file);
    	Scanner in = new Scanner(System.in);

    	N.read(in);
    	
    }
    
    private void read(Scanner in) {
    	
    	int N = in.nextInt();
    	int offset = 10000;
    	
    	List<PointSet> P = new ArrayList<PointSet>(N);
    	PointSet tmp;
    	int x,y,k;
    	for(int i=0; i < N; ++i){
    		x = in.nextInt()+offset;
    		y = in.nextInt()+offset;
    		
    		tmp = new PointSet(x);
    		k = Collections.binarySearch(P, tmp, PSComparators.comparatorX);
    		if(k < 0){
    			k = (k*-1)-1;
    			P.add(k, tmp);
    		}
			tmp = P.get(k);
			tmp.addPy(y);
    	}
    	
    	// System.out.println(P.toString());
        System.out.println(findTotalLettersN(P));
	}
	
	public int findTotalLettersN(List<PointSet> P){
    	
    	PointSet SP1, SP2;
    	
    	// for each value in x
    	for(int i=0; i < P.size(); i++){
    		SP1 = P.get(i);

			for(int j=i+1; j < P.size();j++){
				
				SP2 = P.get(j);

				SP1.saveLettersN(SP2);
				
			}
    		
    	}

    	return lettersN.size();
    	
    }
	
	
	
	
	
	public class Point{
		public int x;
		public int y;
		
		public Point(int x, Integer y){
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			
			if(x == ((Point)obj).x && y == ((Point)obj).y)
				return true;
			return false;
			
		}	
		
		@Override
		public String toString() {
			return "{" + x + ", "+y+"}";
		}	
	}
	
	public class PointsTwo{
		public Point P1;
		public Point P2;
		
		public Point PVector;
		public double module;
		
		public PointsTwo(Point p, Point q){
			P1 = p;
			P2 = q;
			
			PVector = new Point(P2.x - P1.x, P2.y - P1.y);
			module = Math.sqrt((Math.pow(PVector.x, 2))+(Math.pow(PVector.y, 2)));
		}
		
		@Override
		public boolean equals(Object obj) {
			
			if(((PointsTwo)obj).P1.equals(P1) && ((PointsTwo)obj).P2.equals(P2))
				return true;
			return false;
			
		}

		public DIR whereIsThePoint(Point P) {
			
			// value = (x1 - x0)(y2 - y0) - (x2 - x0)(y1 - y0);
			/*double value = ((P2.x - P1.x)*(P.y - P1.y)) - ((P2.x - P1.x)*(P2.y - P.y));	
			
			if(value > 0)
				return DIR.LEFT;
			
			if(value < 0)
				return DIR.RIGHT;
			
			return DIR.INLINE;*/
			
			if (P2.x-P1.x == 0) { // vertical line
		        if (P.x < P2.x) {
		            return P2.y > P1.y ? DIR.LEFT : DIR.RIGHT;
		        }
		        if (P.x > P2.x) {
		            return P2.y > P1.y ? DIR.RIGHT : DIR.LEFT;
		        } 
		        return DIR.INLINE;
		    }
			if (P2.y-P1.y == 0) { // horizontal line
		        if (P.y < P2.y) {
		            return P2.x > P1.x ? DIR.RIGHT : DIR.LEFT;
		        }
		        if (P.y > P2.y) {
		            return P2.x > P1.x ? DIR.LEFT : DIR.RIGHT;
		        } 
		        return DIR.INLINE;
		    }
		    double slope = (P2.y - P1.y) / (P2.x - P1.x);
		    double yIntercept = P1.y - P1.x * slope;
		    double cSolution = (slope*P.x) + yIntercept;
		    if (slope != 0) {
		        if (P.y > cSolution) {
		            return P2.x > P1.x ? DIR.LEFT : DIR.RIGHT;
		        }
		        if (P.y < cSolution) {
		            return P2.x > P1.x ?  DIR.RIGHT : DIR.LEFT;
		        }
		        return DIR.INLINE;
		    }
		    return DIR.INLINE;
		}
		

		
		private PointsTwo getPointsTwo() {

			int index = twoPoints.indexOf(this);
			if(index == -1){
				twoPoints.add(this);
				index = twoPoints.size()-1;
			}
			return twoPoints.get(index);
			
		}
		
		
		public boolean isAngleLessThan(Point P, int less, DIR left) {
			
			Point Pi=P2;
			
			if(left == DIR.LEFT) Pi = P1;
			
			PointsTwo PiP = new PointsTwo(Pi, P);
			PiP = PiP.getPointsTwo();
			
			double num = Math.abs((PVector.x*PiP.PVector.x)+(PVector.y*PiP.PVector.y));
			double den = module*PiP.module;
			
			double angle = Math.acos(num/den);
			
			if(angle <= less || angle >= (360-less))
				return true;
			
			return false;
		}
		
	}
	
	public class LetterN{
		public Point P1;
		public Point P2;
		public Point P3;
		public Point P4;
		
		public Point PVector;
		
		public LetterN(Point p, Point q, Point p1, Point q1){
			P1 = p;
			P2 = q;
			P3 = p1;
			P4 = q1;
		}
		
		@Override
		public boolean equals(Object obj) {
			
			if(((LetterN)obj).P1.equals(P1) 
					&& ((LetterN)obj).P2.equals(P2) 
					&& ((LetterN)obj).P3.equals(P3) 
					&& ((LetterN)obj).P4.equals(P4))
				return true;
			return false;
			
		}

		public LetterN getLetterN() {
			
			int index = lettersN.indexOf(this);
			if(index == -1){
				lettersN.add(this);
				index = lettersN.size()-1;
			}
			return lettersN.get(index);

		}
		
		public LetterN getLetterNUnValid() {
			
			int index = lettersNUnvalid.indexOf(this);
			if(index == -1){
				lettersNUnvalid.add(this);
				index = lettersNUnvalid.size()-1;
			}
			return lettersNUnvalid.get(index);

		}
		
		public boolean isInLetterNUnValid() {
			
			int index = lettersNUnvalid.indexOf(this);
			if(index == -1)
				return false;
			return true;

		}

		public void save() {

			// System.out.println("[Go to saved?] A: "+P1.toString()+" B:"+P2.toString()+" C:"+P3.toString()+" D:"+P4.toString());
			
			if(!isInLetterNUnValid()){
				getLetterN();
								
			}
		}
		
		public boolean isValid(DIR direction){
			
			// calculating BD vector
			PointsTwo BC;
			// if(direction == DIR.RIGHT)
				BC = new PointsTwo(P2, P3);
			// else
			//	BC = new PointsTwo(P3, P2);
			
			BC = BC.getPointsTwo();

			DIR dir = BC.whereIsThePoint(P1);
			// System.out.println("A: "+P1.toString()+" B:"+P2.toString()+" C:"+P3.toString()+" D:"+P4.toString());
			// System.out.println("A is to : "+dir);
			// System.out.println("A angle: "+BC.isAngleLessThan(P1, less, direction));
			
			// if the B is a valid one with respect to A and C
			if(dir == DIR.RIGHT && BC.isAngleLessThan(P1, less, direction)){ // the B is a valid one with respect to A and C

				dir = BC.whereIsThePoint(P4);
				// System.out.println("D is to : "+dir);
				// System.out.println("D angle: "+BC.isAngleLessThan(P4, less, ((direction==DIR.RIGHT)?DIR.LEFT:DIR.RIGHT)));
				
				// if the D is a valid one with respect to B and C
				if(dir == DIR.LEFT && BC.isAngleLessThan(P4, less, ((direction==DIR.RIGHT)?DIR.LEFT:DIR.RIGHT))){ 
					return true;
				}
				
			}
				
			
			return false;
		}

		public void saveAndShuffle() {
			save();
			
			// option 2										
			LetterN letter = new LetterN(P2,P4,P1,P3);
			if(letter.isValid(DIR.LEFT))
				letter.save();
			
			// option 3
			letter = new LetterN(P1,P3,P2,P4);
			if(letter.isValid(DIR.LEFT))
				letter.save();
			
			// option 4
			letter = new LetterN(P2,P1,P4,P3);
			if(letter.isValid(DIR.RIGHT))
				letter.save();

			// option 5
			letter = new LetterN(P4,P3,P2,P1);
			if(letter.isValid(DIR.LEFT)){
				letter.getLetterNUnValid();
			}
			
			// option 6
			letter = new LetterN(P3,P1,P4,P2);
			if(letter.isValid(DIR.RIGHT)){
				letter.getLetterNUnValid();
			}
			
		}
		
	}
	
	public class PointSet{
		public int Px;
		public List<Integer> Py=null;
		
		public PointSet(int x){
			Px = x;
		}
		
		public void addPy(int y){
			if(Py == null){
				Py = new ArrayList<Integer>();
				Py.add(y);
			}else{
				// we apply the insertion sort algorithm
				Py.add(y);
				for(int i=1; i < Py.size(); i++){
					int tmp = Py.get(i);
					int j = i-1;
					while(j >= 0 && Py.get(j) > tmp){
						Py.set(j+1, Py.get(j));
						j-=1;
					}
					Py.set(j+1, tmp);
				}
			}
		}

		public LetterN[] saveLettersN(PointSet SP2) {

			Point A, B, C, D;
			
			for(int Ay = 0; Ay < Py.size(); Ay++){
				
				A = new Point(Px, Py.get(Ay));
				
				for(int Cy = 0; Cy < SP2.Py.size(); Cy++){

					C = new Point(SP2.Px, SP2.Py.get(Cy));
					
					for(int By = Ay+1; By < Py.size(); By++){
						
						B = new Point(Px, Py.get(By));

						// calculating BD vector
						PointsTwo BC = new PointsTwo(B, C);
						BC = BC.getPointsTwo();
						
						// System.out.println("A: "+A.toString()+" B:"+B.toString()+" C:"+C.toString());

						// if the B is a valid one with respect to A and C
						DIR dir = BC.whereIsThePoint(A);
						if(dir == DIR.RIGHT && BC.isAngleLessThan(A, less, DIR.RIGHT)){ // the B is a valid one with respect to A and C
								
							for(int Dy = Cy+1; Dy < SP2.Py.size(); Dy++){
								
								D = new Point(SP2.Px, SP2.Py.get(Dy));
								
								// if the D is a valid one with respect to B and C
								dir = BC.whereIsThePoint(D);
								if(dir == DIR.LEFT && BC.isAngleLessThan(D, less, DIR.LEFT)){ 

									// option 1: the four points form the letter N
									LetterN letter = new LetterN(A,B,C,D);
									letter.saveAndShuffle();

									// System.out.println("A: "+A.toString()+" B:"+B.toString()+" C:"+C.toString()+" D:"+D.toString());

									
								}
							}
								
						}
					}
					
				}
		    	
    		}
			
			return null;
		}

		public Point[] getPoints(){
			Point[] points = new Point[Py.size()];
			for(int i=0; i <Py.size(); i++)
				points[i] = new Point(Px, Py.get(i));
			return points;
		}
		
		public int getMinY(){
			return Py.get(0);
		}
		
		public int getMaxY(){
			return Py.get(Py.size()-1);
		}

		@Override
		public String toString() {
			return "{" + Px + ", "+Py.toString()+"}";
		}		
		
	}
	
	/**
	 * DEFINING COMPARATORS
	 */
	public static class PSComparators{
		
		public static Comparator<PointSet> comparatorX = new Comparator<PointSet>() {
			public int compare(PointSet o1, PointSet o2) {
			    return o1.Px - o2.Px;
			}
		};
	}
	
}