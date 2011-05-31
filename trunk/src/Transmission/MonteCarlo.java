package Transmission;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Runs the Monte Carlo integrations for LLSs photoelectric absorption
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

public class MonteCarlo {

	// Calculates LLS photoelectric absorbtion using Meiksin z and tau
	// distributions over the prescribed wavelength range
	public static double[] MCSMeiksin(double gamma, double beta, double N0,
			double z0, int lrange, int urange, int indiv, double spacing,
			String inputname, PrintWriter absSys) throws IOException {

		int upperr = (int) ((double) urange / spacing);
		int lowerr = (int) ((double) lrange / spacing);
		int nTransmission = (int) (upperr - lowerr);
		double[][] transmission = new double[2][nTransmission + 1];
		double dopplervel = 2 * Math.pow(10, 6); // doppler width control
		// System.out.println(dopplervel+" "+N0+" "+gamma+" "+z0+" "+beta);
		double avgN = N0 * (Math.pow((1 + z0), (gamma + 1)) - 1) / (gamma + 1);
		//System.out.println("avgN"+avgN);
		// average number of LLSs between here and emitter

		double taumax = Double.MAX_VALUE;
		// should be closer to infinity

		// Creates an new AbsorptionSys object
		AbsorptionSys myAbs = new AbsorptionSys(avgN, z0, taumax, gamma, beta,
				absSys);

		// Calculates photoelectric absorption of LLSs
		double[] result = myAbs.absorb(urange, lrange, dopplervel, spacing);
		int t = 0;
		for (int k = lowerr; k < upperr; k++) {
			t = t + 1;
			double j = k * spacing;
			transmission[0][t] = j;
			transmission[1][t] = result[k];
		}
		return result;

	}

	// Calculates LLS photoelectric absorption using Inoue z distribution and
	// Meiksin tau distribution over the prescribed wavelength range
	public static double[] MCSInoue(double gamma1, double gamma2,
			double gamma3, double beta, double N0, double z0, double z1,
			double z2, double AI, int lrange, int urange, int indiv,
			double spacing, String inputname, PrintWriter absSys)
			throws IOException {

		int upperr = (int) ((double) urange / spacing);
		int lowerr = (int) ((double) lrange / spacing);
		int nTransmission = (int) (upperr - lowerr);
		double[][] transmission = new double[2][nTransmission + 1];
		double dopplervel = 2 * Math.pow(10, 6); // doppler width control

		// Finds average number of absorbers between here an source
		double avgN = 0;
		if (z0 <= z1) {
			avgN = (AI / 688.4)
					* ((1 + z1) / (gamma1 + 1))
					* (Math.pow(((1 + z0) / (1 + z1)), (gamma1 + 1)) - Math.pow(
							(1 / (1 + z1)), (gamma1 + 1)));
			//System.out.println("z0<=z1 and avgN = " + avgN);
		} else if (z1 < z0 && z0 <= z2) {
			avgN = (AI / 688.4)
					* (((1 + z1) / (1 + gamma1))
							* (1 - Math.pow(1 / (1 + z1), gamma1 + 1)) + ((1 + z1) / (1 + gamma2))
							* (Math.pow((1 + z0) / (1 + z1), gamma2 + 1) - 1));
			//System.out.println("z1<z0<=z2 and avgN = " + avgN);
		} else if (z0 > z2) {
			avgN = (AI / 688.4)
					* (((1 + z1) / (1 + gamma1))
							* (1 - Math.pow(1 / (1 + z1), gamma1 + 1))
							+ ((1 + z1) / (1 + gamma2))
							* (Math.pow((1 + z2) / (1 + z1), gamma2 + 1) - 1)
							+ Math.pow((1 + z2) / (1 + z1), gamma2)
							* ((1 + z2) / (1 + gamma3))
							* ((Math.pow((1 + z0) / (1 + z2), gamma3 + 1)) - 1));
			//System.out.println("z2<z0 and avgN = " + avgN);
		} else {
			System.out.println("ERROR in finding average number of absorbers");
			System.exit(0);
		}
		double taumax = Double.MAX_VALUE;
		// should be closer to infinity

		// Creates an new AbsorptionSys object
		AbsorptionSys myAbs = new AbsorptionSys(avgN, z0, z1, z2, taumax,
				gamma1, gamma2, gamma3, beta, absSys);

		// Calculates photoelectric absorption of LLSs
		double[] result = myAbs.absorb(urange, lrange, dopplervel, spacing);
		int t = 0;
		for (int k = lowerr; k < upperr; k++) {
			t = t + 1;
			double j = k * spacing;
			transmission[0][t] = j;
			transmission[1][t] = result[k];
		}
		return result;

	}

	// Calculates LLS photoelectric absorption using LLS properties from an
	// input file
	public static double[] MCSfileread(int lrange, int urange, double spacing,
			String inputname,double z0) throws IOException {

		int upperr = (int) ((double) urange / spacing);
		int lowerr = (int) ((double) lrange / spacing);
		int nTransmission = (int) (upperr - lowerr);
		double[][] transmission = new double[2][nTransmission + 1];
		double dopplervel = 2 * Math.pow(10, 6); // doppler width control

		// Reads in properties
		AbsorptionSys inputabs;
		try {
			inputabs = Reader.filein(inputname,z0);
		} catch (Exception e) {
			e.printStackTrace();
			inputabs = new AbsorptionSys();
		}

		// Calculates absorption
		double[] result = inputabs.absorb(urange, lrange, dopplervel, spacing);
		int t = 0;
		for (int k = lowerr; k < upperr; k++) {
			t = t + 1;
			double j = k * spacing;
			transmission[0][t] = j;
			transmission[1][t] = result[k];
		}
		return result;
	}

}
