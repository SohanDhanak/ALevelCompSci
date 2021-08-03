package generators;
import java.util.Random;

import cs.min2phase.Search;

public class CrossGen extends WholeCubeGen{
	
	public CrossGen() {
		Random r = new Random();
		randomCOri = r.nextInt(2187);     //The corner orientations are the same
		cOriCoord = createCornerOriCoord(randomCOri);
		
		randomEOri = r.nextInt(128) << 4;  //128 = 2^7 and << 4 means that only the first 8 digits are changed
		eOriCoord = createEdgeOriCoord(randomEOri);
		eOriCoord = shiftCoord(12, 4, 12, eOriCoord);
		
		randomEPerm = r.nextInt(40320);    //40320 = 8! for 8 swaps
		ePermCoord = createEdgePermCoord(randomEPerm);
		ePermCoord = shiftCoord(7, 4, 11, ePermCoord);
		
		randomCPerm = r.nextInt(40320);  //The corner permutations are the same
		cPermCoord = createCornerPermCoord(randomCPerm);
		
		while(((findParity(ePermCoord) + findParity(cPermCoord))&1)!=0) {
			randomCPerm = r.nextInt(40320);  //The regeneration also remains the same
		    cPermCoord = createCornerPermCoord(randomCPerm);
		}
		
		cornerArray = createCornerPermArray(cPermCoord);   //creating arrays and strings are the same
		edgeArray = createEdgePermArray(ePermCoord);
		
		cubeArray = createFaceletArray(cornerArray,cOriCoord,edgeArray,eOriCoord);
		cubeString = createFaceletString(cubeArray);

		scramble = new Search().solution(cubeString, 25, 100000000, 0, Search.INVERSE_SOLUTION);

	}
	
	protected int[] shiftCoord(int loop, int shift, int mod, int[] originalCoord) {
		int[] coord = new int[mod];
		
		for(int i = 0; i < loop; i++)
			coord[(i+shift)%mod] = originalCoord[i];
		
		return coord;
	}
	
}