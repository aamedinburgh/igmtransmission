package Transmission;

/** This class calculates a contribution to optical depth by the lyman alpha forest according to Meiksin 2006. It returns a value based on
 * the wavelength being considered and the redshift of the object in question. 
* 
 * Copyright 2011 David Stock, Chris M. Harrison and Avery Meiksin
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

public class LyAforest {

	public static double taulya(double lambda, double z) {
		/* add Lyman series resonant scattering */

		int debug = 0;
		int nmax, n;
		double lambdaL = 911.75;
		double tauLyn;
		double tautot = 0;

		double lambdaLyn = lambdaL / (1. - 1. / 4.);
		double zLyn = lambda / lambdaLyn - 1.;
		double tauLyA = 0;
		double ratio = lambda / lambdaL;

		if (zLyn < z) {
			if (zLyn < 4.) {
				if (zLyn < 1.2)
					tauLyA = 0.0164 * Math.pow((1. + zLyn), 1.1);
				else
					tauLyA = 0.00211 * Math.pow((1. + zLyn), 3.7);
			} else
				tauLyA = 0.00058 * Math.pow((1. + zLyn), 4.5);
		}
		tautot += tauLyA;

		if (debug == 1) {
			System.out.println(tautot + " " + ratio);
		}

		if (ratio < (1. + z)) {
			nmax = 31; /* add full Lyman series, cut at n = 31 */
		} else
			nmax = (int) Math.pow((1. - (1. + z) / ratio), -0.5);
		if (nmax > 32) {
			nmax = 31;
		}

		for (n = 3; n <= nmax; n++) {
			switch (n) {

			case 3:
				lambdaLyn = lambdaL / (1. - 1. / 9.);
				zLyn = lambda / lambdaLyn - 1.;
				tauLyA = 0;
				if (zLyn < z) {
					if (zLyn < 4.) {
						if (zLyn < 1.2)
							tauLyA = 0.0164 * Math.pow((1. + zLyn), 1.1);
						else
							tauLyA = 0.00211 * Math.pow((1. + zLyn), 3.7);
					} else
						tauLyA = 0.00058 * Math.pow((1. + zLyn), 4.5);
				}
				if (zLyn < 3) {
					tauLyn = 0.34827 * Math.pow(0.25 * (1. + zLyn), (1. / 3.));
				} else
					tauLyn = 0.34827 * Math.pow(0.25 * (1. + zLyn), (1. / 6.));
				tautot += tauLyn * tauLyA;
				if (debug == 1) {
					System.out.println(tautot + " " + n);
				}

				break;

			case 4:
				lambdaLyn = lambdaL / (1. - 1. / 16.);
				zLyn = lambda / lambdaLyn - 1.;
				tauLyA = 0;
				if (zLyn < z) {
					if (zLyn < 4.) {
						if (zLyn < 1.2)
							tauLyA = 0.0164 * Math.pow((1. + zLyn), 1.1);
						else
							tauLyA = 0.00211 * Math.pow((1. + zLyn), 3.7);
					} else
						tauLyA = 0.00058 * Math.pow((1. + zLyn), 4.5);
				}
				if (zLyn < 3) {
					tauLyn = 0.17932 * Math.pow(0.25 * (1. + zLyn), (1. / 3.));
				} else
					tauLyn = 0.17932 * Math.pow(0.25 * (1. + zLyn), (1. / 6.));
				tautot += tauLyn * tauLyA;
				if (debug == 1) {
					System.out.println(tautot + " " + n);
				}
				break;

			case 5:
				lambdaLyn = lambdaL / (1. - 1. / 25.);
				zLyn = lambda / lambdaLyn - 1.;
				tauLyA = 0;
				if (zLyn < z) {
					if (zLyn < 4.) {
						if (zLyn < 1.2)
							tauLyA = 0.0164 * Math.pow((1. + zLyn), 1.1);
						else
							tauLyA = 0.00211 * Math.pow((1. + zLyn), 3.7);
					} else
						tauLyA = 0.00058 * Math.pow((1. + zLyn), 4.5);
				}
				tauLyn = 0.10872 * Math.pow(0.25 * (1. + zLyn), (1. / 3.));
				tautot += tauLyn * tauLyA;
				if (debug == 1) {
					System.out.println(tautot + " " + n);
				}
				break;

			case 6:
				lambdaLyn = lambdaL / (1. - 1. / 36.);
				zLyn = lambda / lambdaLyn - 1.;
				tauLyA = 0;
				if (zLyn < z) {
					if (zLyn < 4.) {
						if (zLyn < 1.2)
							tauLyA = 0.0164 * Math.pow((1. + zLyn), 1.1);
						else
							tauLyA = 0.00211 * Math.pow((1. + zLyn), 3.7);
					} else
						tauLyA = 0.00058 * Math.pow((1. + zLyn), 4.5);
				}
				tauLyn = 0.072242 * Math.pow(0.25 * (1. + zLyn), (1. / 3.));
				tautot += tauLyn * tauLyA;
				if (debug == 1) {
					System.out.println(tautot + " " + n);
				}
				break;

			case 7:
				lambdaLyn = lambdaL / (1. - 1. / 49.);
				zLyn = lambda / lambdaLyn - 1.;
				tauLyA = 0;
				if (zLyn < z) {
					if (zLyn < 4.) {
						if (zLyn < 1.2)
							tauLyA = 0.0164 * Math.pow((1. + zLyn), 1.1);
						else
							tauLyA = 0.00211 * Math.pow((1. + zLyn), 3.7);
					} else
						tauLyA = 0.00058 * Math.pow((1. + zLyn), 4.5);
				}
				tauLyn = 0.050852 * Math.pow(0.25 * (1. + zLyn), (1. / 3.));
				tautot += tauLyn * tauLyA;
				if (debug == 1) {
					System.out.println(tautot + " " + n);
				}
				break;

			case 8:
				lambdaLyn = lambdaL / (1. - 1. / 64.);
				zLyn = lambda / lambdaLyn - 1.;
				tauLyA = 0;
				if (zLyn < z) {
					if (zLyn < 4.) {
						if (zLyn < 1.2)
							tauLyA = 0.0164 * Math.pow((1. + zLyn), 1.1);
						else
							tauLyA = 0.00211 * Math.pow((1. + zLyn), 3.7);
					} else
						tauLyA = 0.00058 * Math.pow((1. + zLyn), 4.5);
				}
				tauLyn = 0.037336 * Math.pow(0.25 * (1. + zLyn), (1. / 3.));
				tautot += tauLyn * tauLyA;
				if (debug == 1) {
					System.out.println(tautot + " " + n);
				}
				break;

			case 9:
				lambdaLyn = lambdaL / (1. - 1. / 81.);
				zLyn = lambda / lambdaLyn - 1.;
				tauLyA = 0;
				if (zLyn < z) {
					if (zLyn < 4.) {
						if (zLyn < 1.2)
							tauLyA = 0.0164 * Math.pow((1. + zLyn), 1.1);
						else
							tauLyA = 0.00211 * Math.pow((1. + zLyn), 3.7);
					} else
						tauLyA = 0.00058 * Math.pow((1. + zLyn), 4.5);
				}
				tauLyn = 0.028266 * Math.pow(0.25 * (1. + zLyn), (1. / 3.));
				tautot += tauLyn * tauLyA;
				if (debug == 1) {
					System.out.println(tautot + " " + n);
				}
				break;

			default:
				lambdaLyn = lambdaL / (1. - 1. / (n * n));
				zLyn = lambda / lambdaLyn - 1.;
				tauLyA = 0;
				if (zLyn < z) {
					if (zLyn < 4.) {
						if (zLyn < 1.2)
							tauLyA = 0.0164 * Math.pow((1. + zLyn), 1.1);
						else
							tauLyA = 0.00211 * Math.pow((1. + zLyn), 3.7);
					} else
						tauLyA = 0.00058 * Math.pow((1. + zLyn), 4.5);
				}
				tauLyn = 0.028266 * (720. / (n * (n * n - 1.)))
						* Math.pow(0.25 * (1. + zLyn), (1. / 3.));
				tautot += tauLyn * tauLyA;
				if (debug == 1) {
					System.out.println(tautot + " " + n);
				}
				break;
			}
		}

		// System.out.println(nmax);
		return Math.exp(-tautot);

	}
}
