package Other;

/**A class the creates calculates the cumulative frequency of points
 *in a given array.
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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class frequencyreader {
	public static void frequencycalculator(double[] colours1,
			double[] colours2, String outputname1, String outputname2,
			double realisations) throws IOException {

		//Sets up files to write to
		PrintWriter out1 = new PrintWriter(new FileWriter("output//"
				+ outputname1));
		PrintWriter out2 = new PrintWriter(new FileWriter("output//"
				+ outputname2));
		//Finds the lowest value in the array
		double lrange1 = (colours1[0]);
		for (int i = 0; i < colours1.length; i++) {
			if (colours1[i] < lrange1) {
				lrange1 = colours1[i];
			}
		}
		//Finds the highest value in the array
		double urange1 = (colours1[0]);
		for (int i = 0; i < colours1.length; i++) {
			if (colours1[i] > urange1) {
				urange1 = colours1[i];
			}
		}
		
		//Decides on a spacing for binning the data
		//Here use 500 points between min and max
		double spacing1 = (urange1 - lrange1) / 500;
		urange1 = urange1 + 2 * spacing1;
		lrange1 = lrange1 - 2 * spacing1;
		int upperr1 = (int) ((double) urange1 / spacing1);
		int lowerr1 = (int) ((double) lrange1 / spacing1);
		if (upperr1 == lowerr1) {
			System.out.println("ERROR: No Scatter in colour 1");
		}
		
		//Runs through array and counts how many points occur in each bin
		if (upperr1 != lowerr1) {
			int nx = (int) (upperr1 - lowerr1);
			double[] frequency1 = new double[nx];
			double[] culmulative1 = new double[nx];
			double x = 0;
			for (int j = 0; j < colours1.length; j++) {
				x = lrange1;
				for (int i = 0; i < nx; i++) {
					if ((colours1[j] >= x) && (colours1[j] < (x + spacing1))) {
						frequency1[i] = frequency1[i] + 1;
					}
					x = x + spacing1;
				}
			}
			//Turns the above into a culmulative frequency and normalises
			x = lrange1 + spacing1;
			culmulative1[1] = 0.0;
			for (int j = 1; j < nx - 1; j++) {
				culmulative1[j] = frequency1[j] + culmulative1[j - 1];
				out1.println(x + " " + culmulative1[j] / realisations);
				x = x + spacing1;
			}
		}
		
		/*REPEATS THE ABOVE FOR THE SECOND ARRAY*/
		
		double lrange2 = (colours2[0]);
		for (int i = 0; i < colours2.length; i++) {
			if (colours2[i] < lrange2) {
				lrange2 = colours2[i];
			}
		}
		double urange2 = (colours2[0]);
		for (int i = 0; i < colours2.length; i++) {
			if (colours2[i] > urange2) {
				urange2 = colours2[i];
			}
		}
		double spacing2 = (urange2 - lrange2) / 500;
		urange2 = urange2 + 2 * spacing2;
		lrange2 = lrange2 - 2 * spacing2;

		int upperr2 = (int) ((double) urange2 / spacing2);
		int lowerr2 = (int) ((double) lrange2 / spacing2);
		if (upperr2 == lowerr2) {
			System.out.println("ERROR: No Scatter in colour 2");
		}
		if (upperr2 != lowerr2) {
			int nx2 = (int) (upperr2 - lowerr2);
			double[] frequency2 = new double[nx2];
			double[] culmulative2 = new double[nx2];
			double x = 0;

			for (int j = 0; j < colours2.length; j++) {
				x = lrange2;
				for (int i = 0; i < nx2; i++) {
					if ((colours2[j] >= x) && (colours2[j] < (x + spacing2))) {
						frequency2[i] = frequency2[i] + 1;
					}
					x = x + spacing2;
				}
			}

			x = lrange2 + spacing2;
			culmulative2[1] = 0.0;
			for (int j = 1; j < nx2 - 1; j++) {
				culmulative2[j] = frequency2[j] + culmulative2[j - 1];
				out2.println(x + " " + culmulative2[j] / realisations);
				x = x + spacing2;
			}

		}

		out1.close();
		out2.close();

	}
}
