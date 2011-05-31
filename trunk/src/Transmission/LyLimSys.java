package Transmission;

import java.io.PrintWriter;
import java.util.*;

/**This class is an object containing the properties of a random Lyman Limit
 * system When called it automatically selects Z and Tau from appropriately
 * skewed distributions
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

public class LyLimSys {

	// The random number generator used throughout
	private Random generator = new Random();

	// @SuppressWarnings("unused")
	private double z, tau;

	/**
	 * This is the constructor for one lyman limit system, it stores randomly
	 * selected values of Z and Tau drawn from an appropriate distibution.
	 * 
	 * @param z
	 *            The randomly distributed redshift
	 * @param tau
	 *            The randomly distributed optical depth
	 * @param z0
	 *            The redshift of the emitting object
	 * @param taumax
	 *            The maximum optical depth considered
	 */

	public LyLimSys(double z0, double taumax, double gamma, double beta,
			PrintWriter absSys) {
		z = findzMeiksin(z0, gamma);
		tau = findtau(taumax, beta);
		absSys.println(z + " " + tau);
	}

	public LyLimSys(double z0, double z1, double z2, double taumax,
			double gamma1, double gamma2, double gamma3, double beta,
			PrintWriter absSys) {
		z = findzInoue(z0, z1, z2, gamma1, gamma2, gamma3);
		tau = findtau(taumax, beta);
		absSys.println(z + " " + tau);
	}

	/**
	 * This is the simplified constructor for a lyman limit system when you
	 * already know its parameters
	 */
	public LyLimSys(double z, double tau) {
		this.z = z;
		this.tau = tau;
	}

	/**
	 * This function randomly picks a Z from a skewed redshift distribution. The
	 * distribution is dN/dZ = (1+Z)^gamma as given by Meiksin 2006
	 * 
	 * @param z0
	 *            The redshift of the emitting object
	 * @return A randomly selected z picked from the skewed distribution
	 */

	public double findzMeiksin(double z0, double gamma) {

		double random = generator.nextDouble();
		double z = Math.pow(random * (Math.pow((1 + z0), gamma + 1) - 1) + 1,
				Math.pow((gamma + 1), -1)) - 1;

		return z;
	}

	/**
	 * This function randomly picks a z from a broken redshift distribution as
	 * given by Inoue 2008
	 * 
	 * @return A randomly selected z picked from the skewed distribution
	 */

	public double findzInoue(double z0, double z1, double z2, double gamma1,
			double gamma2, double gamma3) {
		double random = generator.nextDouble();

		double z = 0;
		if (z0 <= z1) {
			double normalisation1 = ((1 + z1) / (gamma1 + 1))
					* Math.pow(((1 + z0) / (z1 + 1)), (gamma1 + 1))
					- ((1 + z1) / (gamma1 + 1))
					* Math.pow((1 / (z1 + 1)), (gamma1 + 1));
			z = Math.pow(
					(random * normalisation1 * ((gamma1 + 1) / (1 + z1)) + Math
							.pow((1 / (1 + z1)), (gamma1 + 1))),
					(1 / (gamma1 + 1)))
					* (1 + z1) - 1;
		} else if (z1 < z0 && z0 <= z2) {
			double normalisation2 = ((1 + z1) / (1 + gamma1))
					* (1 - Math.pow((1 / (1 + z1)), (gamma1 + 1)))
					+ ((1 + z1) / (1 + gamma2))
					* (Math.pow(((1 + z0) / (1 + z1)), (gamma2 + 1)) - 1);
			double Fz1 = ((1 + z1) / (gamma1 + 1))
					* (1 - Math.pow((1 / (1 + z1)), (gamma1 + 1)))
					/ normalisation2;
			if (0 < random && random <= Fz1) {
				z = Math.pow((random * normalisation2
						* ((gamma1 + 1) / (1 + z1)) + Math.pow((1 / (1 + z1)),
						(gamma1 + 1))), (1 / (gamma1 + 1)))
						* (1 + z1) - 1;
			} else if (Fz1 < random && random <= 1) {
				z = Math.pow(((random * normalisation2 - Fz1 * normalisation2)
						* ((gamma2 + 1) / (1 + z1)) + 1), (1 / (gamma2 + 1)))
						* (1 + z1) - 1;
			} else {
				System.out.println("ERROR");
				System.exit(0);
			}
		} else if (z0 > z2) {
			double normalisation3 = ((1 + z1) / (gamma1 + 1))
					* (1 - Math.pow((1 / (1 + z1)), (gamma1 + 1)))
					+ ((1 + z1) / (gamma2 + 1))
					* (Math.pow(((1 + z2) / (1 + z1)), (gamma2 + 1)) - 1)
					+ ((1 + z2) / (gamma3 + 1))
					* (Math.pow(((1 + z2) / (1 + z1)), gamma2))
					* (Math.pow(((1 + z0) / (1 + z2)), (gamma3 + 1)) - 1);
			double Fz1 = (((1 + z1) / (gamma1 + 1)) * (1 - Math.pow(
					(1 / (1 + z1)), (gamma1 + 1))))
					/ normalisation3;
			double Fz2 = Fz1
					+ (((1 + z1) / (gamma2 + 1)) * (Math.pow(
							((1 + z2) / (1 + z1)), (gamma2 + 1)) - 1))
					/ normalisation3;
			// System.out.println("Fz1: " + Fz1 + " Fz2: " + Fz2);
			if (0 < random && random <= Fz1) {
				z = Math.pow((random * normalisation3
						* ((gamma1 + 1) / (1 + z1)) + Math.pow((1 / (1 + z1)),
						(gamma1 + 1))), (1 / (gamma1 + 1)))
						* (1 + z1) - 1;
			} else if (Fz1 < random && random <= Fz2) {
				z = Math.pow(
						((normalisation3 * random - ((1 + z1) / (gamma1 + 1))
								* (1 - Math.pow((1 / (1 + z1)), (gamma1 + 1))))
								* ((gamma2 + 1) / (1 + z1)) + 1),
						(1 / (gamma2 + 1)))
						* (1 + z1) - 1;
			} else if (random > Fz2) {

				z = Math
						.pow(
								(((gamma3 + 1) / (1 + z2))
										* Math.pow(((1 + z1) / (1 + z2)),
												gamma2)
										* (random
												* normalisation3
												- ((1 + z1) / (gamma1 + 1))
												* (1 - Math.pow((1 / (1 + z1)),
														(gamma1 + 1))) - ((1 + z1) / (gamma2 + 1))
												* (Math.pow(
														((1 + z2) / (1 + z1)),
														(gamma2 + 1)) - 1)) + 1),
								(1 / (gamma3 + 1)))
						* (1 + z2) - 1;
			} else {
				System.out.println("ERROR");
				System.exit(0);
			}
		} else {
			System.out.println("ERROR");
		}
		return z;
	}

	/**
	 * This function randomly selects tau from a skewed tau distribution. The
	 * distribution is skewed as dN/Dtau = tau^(-beta)
	 * 
	 * @param taumax
	 *            The maximum allowed tau
	 * @param beta
	 *            derived constant = 1.5 (Meiskin), 1.3 (Inoue) left as a
	 *            variable for generality
	 * @return A randomly selected tau picked from the skewed distribution
	 */

	public double findtau(double taumax, double beta) {

		double random = generator.nextDouble();
		double tau = Math.pow(1 + random * (Math.pow(taumax, 1 - beta) - 1),
				Math.pow((1 - beta), -1));

		return tau;
	}

	/* SETTERS AND GETTERS */
	public void settau(double tau) {
		this.tau = tau;
	}

	public void setZ(double Z) {
		this.z = Z;
	}

	public double gettau() {
		return this.tau;
	}

	public double getZ() {
		return this.z;
	}

}
