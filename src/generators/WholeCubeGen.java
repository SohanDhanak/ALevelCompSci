package generators;
import java.util.ArrayList;
import cs.min2phase.Search;
import java.util.Random;

public class WholeCubeGen {
	
	protected int randomCOri;
	protected int[] cOriCoord;
	protected int randomEOri;
	protected int[] eOriCoord;
	protected int randomEPerm;
	protected int[] ePermCoord;
	protected int randomCPerm;
	protected int[] cPermCoord;
	protected String[] cornerArray;
	protected String[] edgeArray;
	protected char[] cubeArray;
	protected String cubeString;
	protected String scramble;
	
	public WholeCubeGen() {
		Random r = new Random();   //Allows random number generators

		randomCOri = r.nextInt(2187);    //There are 3^7 unique corner orientations
		cOriCoord = createCornerOriCoord(randomCOri);

		randomEOri = r.nextInt(2048);    //There are 2^11 unique edge orientations
		eOriCoord = createEdgeOriCoord(randomEOri);

		randomCPerm = r.nextInt(40320);  //There are 8! unique corner permutations
		cPermCoord = createCornerPermCoord(randomCPerm);

		randomEPerm = r.nextInt(479001600); //There are 12! unique edge permutations
		ePermCoord = createEdgePermCoord(randomEPerm);

		while (((findParity(cPermCoord) + findParity(ePermCoord)) & 1) != 0) {
			randomEPerm = r.nextInt(479001600);     //The two coordinates must be compatible
			ePermCoord = createEdgePermCoord(randomEPerm);
		}

		cornerArray = createCornerPermArray(cPermCoord); //Both permutations arrays must be created
		edgeArray = createEdgePermArray(ePermCoord);

		cubeArray = createFaceletArray(cornerArray, cOriCoord, edgeArray, eOriCoord);
		cubeString = createFaceletString(cubeArray);    //The String and array are created
		
		scramble = new Search().solution(cubeString, 25, 100000000, 0, Search.INVERSE_SOLUTION);
	}

	protected int[] createCornerOriCoord(int cornerOri) {
		int[] coord = new int[8];
		int rem = 0;
		int sum = 0;
		int index = 7;

		while (cornerOri >= 1) {
			rem = cornerOri % 3;
			sum += rem;
			coord[index] = rem;
			cornerOri /= 3;
			index--;
		}

		coord[0] = ((15 - sum) % 3);

		return coord;
	}

	protected int[] createEdgeOriCoord(int edgeOri) {
		int[] coord = new int[12];
		int rem = 0;
		int sum = 0;
		int index = 11;

		while (edgeOri >= 1) {
			rem = edgeOri & 1;
			sum += rem;
			coord[index] = rem;
			edgeOri >>= 1;
			index--;
		}

		coord[0] = ((12 - sum) & 1);

		return coord;
	}

	protected int[] createCornerPermCoord(int cornerPerm) {
		int[] coord = new int[7];
		int rem = 0;
		int divider = 5040;

		for (int i = 7; i >= 1; i--) {
			rem = cornerPerm / divider;
			coord[i - 1] = rem;
			cornerPerm = cornerPerm % divider;
			divider /= i;
		}

		return coord;
	}

	protected int[] createEdgePermCoord(int edgePerm) {
		int[] coord = new int[11];
		int rem = 0;
		int divider = 39916800;

		for (int i = 11; i >= 1; i--) {
			rem = edgePerm / divider;
			coord[i - 1] = rem;
			edgePerm = edgePerm % divider;
			divider /= i;
		}

		return coord;
	}

	protected String[] createCornerPermArray(int[] cornerPermCoord) {
		int index = 0;
		ArrayList<String> defaultCorners = new ArrayList<String>();
		String[] cubeCornerArray = new String[8];
		defaultCorners.add("URF");
		defaultCorners.add("UFL");
		defaultCorners.add("ULB");
		defaultCorners.add("UBR");
		defaultCorners.add("DFR");
		defaultCorners.add("DLF");
		defaultCorners.add("DBL");
		defaultCorners.add("DRB");

		for (int i = 6; i >= 0; i--) {
			index = defaultCorners.size() - cornerPermCoord[i] - 1;
			cubeCornerArray[i + 1] = defaultCorners.get(index);
			defaultCorners.remove(index);
		}

		cubeCornerArray[0] = defaultCorners.get(0);
		return cubeCornerArray;
	}

	protected String[] createEdgePermArray(int[] edgePermCoord) {
		int index = 0;
		ArrayList<String> defaultEdges = new ArrayList<String>();
		String[] cubeEdgeArray = new String[12];
		defaultEdges.add("UR"); defaultEdges.add("UF"); defaultEdges.add("UL");
		defaultEdges.add("UB"); defaultEdges.add("DR"); defaultEdges.add("DF");
		defaultEdges.add("DL"); defaultEdges.add("DB"); defaultEdges.add("FR");
		defaultEdges.add("FL"); defaultEdges.add("BL"); defaultEdges.add("BR");

		for (int i = 10; i >= 0; i--) {
			index = defaultEdges.size() - edgePermCoord[i] - 1;
			cubeEdgeArray[i + 1] = defaultEdges.get(index);
			defaultEdges.remove(index);
		}

		cubeEdgeArray[0] = defaultEdges.get(0);
		return cubeEdgeArray;
	}

	
	
	

	protected char[] createFaceletArray(String[] cPA, int[] cO, String[] ePA, int[] eO) {
		char facelet;
		char[] faceletArray = new char[54];
		int[][] defaultCornerFacelet = {    //Each sub array represents each corner
				{ 8, 9, 20 },   { 6, 18, 38 },  { 0, 36, 47 },  { 2, 45, 11 },
				{ 29, 26, 15 }, { 27, 44, 24 }, { 33, 53, 42 }, { 35, 17, 51 } 
			};
		int[][] defaultEdgeFacelet = {      //Each sub array is the corresponding edge
				{5,10},{7,19},{3,37},{1,46},{32, 16},{28,25},
				{30,43},{34,52},{23,12},{21,41},{50,39},{48,14}
			 };

		faceletArray[4] = 'U'; // Centres
		faceletArray[13] = 'R';
		faceletArray[22] = 'F';
		faceletArray[31] = 'D';
		faceletArray[40] = 'L';
		faceletArray[49] = 'B';
					
		for(int i = 0; i < 8; i++)       //Iterates through each corner
			for(int j = 0; j < 3; j++) { //Goes to each individual index
				facelet = cPA[i].charAt(((j + 3) - cO[i]) % 3); //Finds the sticker
				faceletArray[defaultCornerFacelet[i][j]] = facelet;
			}
		for(int i = 0; i < 12; i++)      //Iterates through every edge
			for(int j = 0; j < 2; j++) { //Selects each index
				facelet =  ePA[i].charAt(((j + 2) - eO[i]) & 1); //Assigns the sticker
				faceletArray[defaultEdgeFacelet[i][j]] = facelet;
			}
		
		return faceletArray;
	}

	protected String createFaceletString(char[] faceletArray) {
		String faceletString = "";

		for (int i = 0; i < faceletArray.length; i++)
			faceletString += faceletArray[i];

		return faceletString;
	}

	protected int findParity(int[] permCoord) {
		int parity = 0;

		for (int i = 0; i < permCoord.length; i++)
			parity += permCoord[i];

		return (parity & 1);
	}
	
	public char[] getCubeArray() {
		return cubeArray;
	}
	
	public String getScramble() {
		return scramble;
	}

}