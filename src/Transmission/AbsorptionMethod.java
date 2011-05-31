package Transmission;


/**This class defines the AbsorptionMethod interface. It is intended to be a way of easily implementing
 * different physical absorption systems. It will calculate the transmission fraction for one wavelength 
 * and one Lyman Limit System (which has properties tau,z,dopplervel).
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

public interface AbsorptionMethod {

	/** This is the main interface method. It takes parameters and returns the calculated 
	 * transmission fraction.
	 * 
	 * @param lambda Wavelength under consideration
	 * @param tau Optical depth of LLS under consideration
	 * @param z Redshift of LLS
	 * @param dopplervel Doppler velocity of gas comprising LLS
	 * @return Transmission fraction between 1 and 0
	 */
	
	double am(double lambda, double tau, double z, double dopplervel);
	
}
