package Transmission;

/**This class represents diffuse IGM absorption as prescribed by Meiksin A.A.
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

public class DiffuseIGM {
	
	 public static double tauIGM(double lambda, double z,double A) {
		
		double zL = (lambda/912.0 - 1);
		double tau;
		tau = A*Math.pow((1+zL),4.4)*((1/Math.pow(1+zL,1.5))-(1/Math.pow(1+z,1.5)));			
		//tau = 0.805*Math.pow(1+zL,3)*((1/(1+zL))-(1/(1+z)));//OLD VERSION
		return Math.exp(-tau);
	}

}

