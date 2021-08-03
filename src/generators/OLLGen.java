package generators;
import java.util.Random;

import cs.min2phase.Search;

public class OLLGen extends F2LGen{
	
	public OLLGen() {
		Random r = new Random();
		cOriCoord = new int[8];     //The default corner orientation
		eOriCoord = new int[12];    //All edges are oriented
		 
		randomEPerm = r.nextInt(24);  //24 is 4! for 4 changed pieces
		ePermCoord = createEdgePermCoord(randomEPerm);   //The same pieces are swapped so F2LGen is used
		ePermCoord = shiftCoord(3, 4, 11, ePermCoord);
		
		randomCPerm = r.nextInt(24);  
		cPermCoord = createCornerPermCoord(randomCPerm);
		cPermCoord = shiftCoord(7, 4, 7, cPermCoord);
		
		while(((findParity(ePermCoord) + findParity(cPermCoord))&1)!=0) {
			randomCPerm = r.nextInt(24);
			cPermCoord = createCornerPermCoord(randomCPerm);
			cPermCoord = shiftCoord(7, 4, 7, cPermCoord);
		}
		
		cornerArray = createCornerPermArray(cPermCoord);    //The string generation remains the same
		edgeArray = createEdgePermArray(ePermCoord);
		
		cubeArray = createFaceletArray(cornerArray,cOriCoord,edgeArray,eOriCoord);
		cubeString = createFaceletString(cubeArray);

		scramble = new Search().solution(cubeString, 25, 100000000, 0, Search.INVERSE_SOLUTION);
	}
	
}