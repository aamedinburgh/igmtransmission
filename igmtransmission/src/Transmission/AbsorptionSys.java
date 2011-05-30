package Transmission;

import java.io.PrintWriter;
import java.util.*;

/** This class contains the constructor and methods associated with an absorption
* system object. It creates an absorption system which can then be used in
* other objects to calculate the absorption profile of the system.
* 
* 
  * Copyright 2011 Savid Stock, Chris M. Harrison and Avery Meiksin
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

public class AbsorptionSys {

	/**
	 * This constructor initialises an array of Lyman Limit Systems the number
	 * of which is drawn from a Poisson distribution with an average of
	 * <code>avgN</code>.
	 * 
	 * @param avgN
	 */
	public LyLimSys[] abssystem;

	private Random generator = new Random();

	// Creates an array of Lyman Limit Systems for Meiksin distribution.
	// The number of which is taken from a Poisson Distribution
	public AbsorptionSys(double avgN, double z0, double taumax, double gamma,
			double beta, PrintWriter absSys) {
		int k = getNo(avgN);
		
		if ((avgN + 3 * Math.sqrt(avgN)) > 100) {
			System.out
					.println("WARNING cannot accurately produce LLSs as average number per line of sight is too large");
		}
		
		absSys.println("k = "+k);
		abssystem = new LyLimSys[k];
		for (int i = 0; i < k; i++) {
			abssystem[i] = new LyLimSys(z0, taumax, gamma, beta, absSys);
		}
	}

	// Creates an array of Lyman Limit Systems for Inoue distribution.
	// The number of which is taken from a Poisson Distribution
	public AbsorptionSys(double avgN, double z0, double z1, double z2,
			double taumax, double gamma1, double gamma2, double gamma3,
			double beta, PrintWriter absSys) {
		int k = getNo(avgN);
		absSys.println(k);
		abssystem = new LyLimSys[k];
		for (int i = 0; i < k; i++) {
			abssystem[i] = new LyLimSys(z0, z1, z2, taumax, gamma1, gamma2,
					gamma3, beta, absSys);
		}
	}

	// Override constructor which creates one Lyman Limit System in the array
	public AbsorptionSys() {
		abssystem = new LyLimSys[1];
		abssystem[0] = new LyLimSys(0, 0);
	}

	// This constructor is used if the properties of the systems have been
	// passed across from an input file.
	public AbsorptionSys(int no, double[] z, double[] tau) {
		abssystem = new LyLimSys[no];
		for (int k = 0; k < no; k++) {
			abssystem[k] = new LyLimSys(z[k], tau[k]);
		}
	}

	// Returns length of array
	public int getlength() {
		return this.abssystem.length;
	}

	// Returns a number from a Poisson Distribtution
	public int getNo(double avgN) {
		// the number of poisson probabilities to calculate
		// i.e. order = 10 calculates the first 10
		int order = 100;

		if ((avgN + 3 * Math.sqrt(avgN)) > 100) {
			System.out
					.println("WARNING cannot accurately produce lyman limit systems as average number per line of sight is too large");
		}

		// creating and filling an array to hold factorials
		double[] factorials = new double[order];
		factorials[0] = 1;
		for (int i = 1; i <= 30; i++) {
			factorials[i] = i * factorials[i - 1];
		}

		// creating and filling an array with the Poisson probabilities, goes to Gaussian for larger values.
		double[] probabilities = new double[order];
		for (int j = 0; j < order; j++) {
			if (j <= 30) {
				probabilities[j] = Math.pow(avgN, j) * Math.exp(-avgN)
						* Math.pow(factorials[j], -1);
			} else {
				probabilities[j] = Math.exp(j - avgN) * Math.pow((avgN / j), j)
						/ Math.sqrt(2 * Math.PI * j);
			}
			// System.out.println(j+" "+probabilities[j]);
		}

		// making and filling an array with the cumulative probs (which should
		// add up to one)
		double[] cumulative = new double[order];
		cumulative[0] = probabilities[0];
		for (int k = 1; k < order; k++) {
			cumulative[k] = cumulative[k - 1] + probabilities[k];
			// System.out.println(cumulative[k]);
		}

		double random = generator.nextDouble();

		if (random < cumulative[0]) {
			return 0;
		} // checking if the result should be zero

		for (int j = 1; j < order; j++) {
			if (random > cumulative[j - 1] && random < cumulative[j]) {
				// checking whether the point lies in the jth box
				return j;
			}

		}
		// if more than 100 LLSs - assumed to be blacked out
		return 100;

	}

	/**
	 * This method calculates the photoelectric absorption due to the LLSs for
	 * one random realization.
	 * 
	 * @return An array corresponding to wavelengths containing absorption
	 *         fractions.
	 * @throws IOException
	 */

	public double[] absorb(int urange, int lrange, double dopplervel,
			double spacing) {

		// Creates photoelectric absorber object
		Photoelectric PE = new Photoelectric();

		double lambdal2 = 912 / spacing; // The lyman edge

		int upperr = (int) ((double) urange / spacing);
		int lowerr = (int) ((double) lrange / spacing);

		double[] receive = new double[upperr]; // the received fraction in our
		// frame

		for (int h = 0; h < upperr; h++) {
			receive[h] = 1;
		} // filling the array

		int nosys = abssystem.length; // finding the number of systems

		for (int i = 0; i < nosys; i++) { // looping over systems
			double taul = abssystem[i].gettau(); // getting the tau values
			double z = abssystem[i].getZ(); // getting the z values
			double zeff = z + 1;

			for (int p = lowerr; p < upperr; p++) { // looping over wavelengths
				double wavelength = p * spacing;

				if (p < (int) (lambdal2 * zeff)) { // for wavelengths below
					// Lyman edge
					receive[p] = receive[p]
							* PE.am(wavelength, taul, z, dopplervel);
				}

			}

		}
		return receive;
	}

	/* GET LLS PROPERTIES */

	public double[] getallZ() {

		int length = this.abssystem.length;

		double[] z = new double[length];

		for (int i = 0; i < length; i++) {
			z[i] = this.abssystem[i].getZ();
			// System.out.println(z[i]);
		}

		return z;
	}

	public double[] getalltau() {

		int length = this.abssystem.length;

		double[] tau = new double[length];

		for (int i = 0; i < length; i++) {
			tau[i] = this.abssystem[i].gettau();
			// System.out.println(tau[i]);
		}

		return tau;

	}

}
