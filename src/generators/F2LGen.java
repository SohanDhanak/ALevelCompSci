package generators;
import java.util.Random;

import cs.min2phase.Search;

public class F2LGen extends CrossGen{
	
	public F2LGen() {
		Random r = new Random();
		randomCOri = r.nextInt(27)*81;       //27 is 3^3 and *81 shifts the bits 4 places to the left
		cOriCoord = createCornerOriCoord(randomCOri);
		cOriCoord = shiftCoord(8, 4, 8, cOriCoord);
		
		randomEOri = r.nextInt(8) << 8;      //8 is 2^3 and << 8 shifts the bits 8 places to the left
		eOriCoord = createEdgeOriCoord(randomEOri);
		eOriCoord = shiftCoord(12, 4, 12, eOriCoord);
		
		randomEPerm = r.nextInt(24);         //24 is 4! for 4 changed edges
		ePermCoord = createEdgePermCoord(randomEPerm);
		ePermCoord = shiftCoord(3, 4, 11, ePermCoord);
		
		randomCPerm = r.nextInt(24);         //24 is 4! for 4 changed corners
		cPermCoord = createCornerPermCoord(randomCPerm);
		cPermCoord = shiftCoord(7, 4, 7, cPermCoord);
		
		while(((findParity(ePermCoord) + findParity(cPermCoord))&1)!=0) {
			randomCPerm = r.nextInt(24);          
			cPermCoord = createCornerPermCoord(randomCPerm);
			cPermCoord = shiftCoord(7, 4, 7, cPermCoord);
		}
		
		cornerArray = createCornerPermArray(cPermCoord);
		edgeArray = createEdgePermArray(ePermCoord);
		
		cubeArray = createFaceletArray(cornerArray,cOriCoord,edgeArray,eOriCoord);
		cubeString = createFaceletString(cubeArray);

		scramble = new Search().solution(cubeString, 25, 100000000, 0, Search.INVERSE_SOLUTION);
	}
	
}