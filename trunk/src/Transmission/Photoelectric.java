package Transmission;

/**This class represents photoelectiric absorption.
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

public class Photoelectric implements AbsorptionMethod{

	/**
	 * This is the method that is being implemented overriding the one in
	 * Absorptionmethod in order to represent Photoelectric absorption.
	 */
	
	 public double am(double lambda, double tau, double z, double dopplervel) {

		double zeff = 1+z;
		double lambdal2 = 912;	
		
		double k = lambda / (lambdal2*zeff);
		
		double out = Math.exp(-tau*k*k*k);
		
		//double out = Math.exp(-tau*Math.pow((lambda*Math.pow(lambdal2*zeff, -1)), 3));
		
		return out;
	}

}

