package Transmission;

/**Reads in file containing LLS properties
* 
 * Copyright 2011 Chris M. Harrison and Avery Meiksin
 * Contact: aam@roe.ac.uk
 * 
 *    This file is part of IGMtrasnsmission.
 *
 *    IGMtransmission is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    IGMtransmission is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with IGMtransmission.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.*;
import java.util.*;


public class Reader{
	
	public static AbsorptionSys filein(String filename,double z0) throws IOException {
		System.out.println("\nYou're adding some of your own LLSs.");
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String line; // (2)
		int loopind = 0;
		int tokenind = -1;

		line = input.readLine();

		int nosources = (int)Double.parseDouble(line);

		double[] forcedz = new double[nosources];
		double[] forcedt = new double[nosources];
        double zLLS = 999;
		
		while (( line = input.readLine() ) != null){

			Scanner tokens = new Scanner(line);

			while(tokens.hasNext()) {

				tokenind *=-1;
				String s = tokens.next();
				double val = Double.parseDouble(s);
				if(tokenind == 1){zLLS = val;}	
				if(tokenind == 1 && zLLS > z0){System.out.println("WARNING: LLS ["+(loopind+1)+"] has z > source z, ignoring this LLS.\n");}
			    if(tokenind == 1 && zLLS <= z0){forcedz[loopind] = val;}
				if(tokenind == -1 && zLLS <= z0){forcedt[loopind] = val;}
			
				}
			loopind += 1;
		}
		input.close(); // (5)
	
		AbsorptionSys forcedsystems = new AbsorptionSys(nosources, forcedz, forcedt);
		
		return forcedsystems;
	}
}

