package Integrators;

/**
 *A class to perform integration via the Simpson's Rule !NOW REDUNTANT!
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

public class SimpsonsRule {

	public SimpsonsRule() {
	}

	public double calcIntegral(double data[][]) {

		int n = data[0].length;

		// As only appears to work for one of odd or even n
		if (!isEven(n)) {
			n = n - 1;
			System.out.println("Simpson's rule has TRUNCATED LAST VALUE");
		}
		// WATCH OUT, h needs to be constant!
		double h = data[0][2] - data[0][1];
		System.out.println("h = " + h);
		double integral = integral(data, n, h);
		return integral;
	}

	// Performs the Simspson's integration
	public static double integral(double data[][], int n, double h) {
		double factor = h / 3.0;
		double area = 0.0;
		for (int i = 1; i < n; i++) {
			if (i == 1 || i == n - 1) {
				area += data[1][i];
			} else if (isEven(i)) {
				area += 4.0 * data[1][i];
			} else {
				area += 2.0 * data[1][i];
			}
		}
		return factor * area;
	}

	// Method to check if even
	public static boolean isEven(int n) {
		if (n % 2 == 0)
			return true;
		else
			return false;
	}
}
