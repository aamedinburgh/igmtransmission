package Colours;

import java.io.*;
import Integrators.*;

/**
 *This program calculators galaxy colours without any IGM absorption.
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

public class colourCalculator {

	public static void calcColour(String Filters[], String spectrumFileName,
			double z0, int vSwitch, String outputFileName) throws IOException {

		PrintWriter out = new PrintWriter(new FileWriter("output//"
				+ outputFileName));

		/* Set up the filters */
		int N = 100;
		System.out.println(Filters[1]);
		double fullFilterArray1[][] = fillArray(Filters[1], 1);
		Gaussian gaussian1 = new Gaussian(fullFilterArray1, N + 1);
		double filterArray1[] = new double[gaussian1.n + 1];
		filterArray1 = gaussian1.interpolate(gaussian1, fullFilterArray1);
		double normalisation1G = normalise(filterArray1, gaussian1);
		System.out.println("Normalisation: " + normalisation1G);
		System.out.println(Filters[2]);
		double fullFilterArray2[][] = fillArray(Filters[2], 1);
		Gaussian gaussian2 = new Gaussian(fullFilterArray2, N + 1);
		double filterArray2[] = new double[gaussian2.n + 1];
		filterArray2 = gaussian2.interpolate(gaussian2, fullFilterArray2);
		double normalisation2G = normalise(filterArray2, gaussian2);
		System.out.println("Normalisation: " + normalisation2G);
		System.out.println(Filters[3]);
		double fullFilterArray3[][] = fillArray(Filters[3], 1);
		Gaussian gaussian3 = new Gaussian(fullFilterArray3, N + 1);
		double filterArray3[] = new double[gaussian3.n + 1];
		filterArray3 = gaussian3.interpolate(gaussian3, fullFilterArray3);
		double normalisation3G = normalise(filterArray3, gaussian3);
		System.out.println("Normalisation: " + normalisation3G);
		System.out.println(Filters[4]);
		double fullFilterArray4[][] = fillArray(Filters[4], 1);
		Gaussian gaussian4 = new Gaussian(fullFilterArray4, N + 1);
		double filterArray4[] = new double[gaussian4.n + 1];
		filterArray4 = gaussian4.interpolate(gaussian4, fullFilterArray4);
		double normalisation4G = normalise(filterArray4, gaussian4);
		System.out.println("Normalisation: " + normalisation4G);

		/* Set up interpolated galaxy spectrum */
		double fullSpectrumArray[][] = fillArray(spectrumFileName, 1 + z0);
		double spectrumArray1[] = new double[gaussian1.n + 1];
		double spectrumArray2[] = new double[gaussian2.n + 1];
		double spectrumArray3[] = new double[gaussian3.n + 1];
		double spectrumArray4[] = new double[gaussian4.n + 1];
		spectrumArray1 = gaussian1.interpolate(gaussian1, fullSpectrumArray);
		spectrumArray2 = gaussian2.interpolate(gaussian2, fullSpectrumArray);
		spectrumArray3 = gaussian3.interpolate(gaussian3, fullSpectrumArray);
		spectrumArray4 = gaussian4.interpolate(gaussian4, fullSpectrumArray);

		//Works out magnitudes
		double mAB1A = magnitude(gaussian1, filterArray1, normalisation1G,
				spectrumArray1);
		double mAB2A = magnitude(gaussian2, filterArray2, normalisation2G,
				spectrumArray2);
		double mAB3A = magnitude(gaussian3, filterArray3, normalisation3G,
				spectrumArray3);
		double mAB4A = magnitude(gaussian4, filterArray4, normalisation4G,
				spectrumArray4);
		
		//If requested converts to vega mags by using vega spectrum through filters
		double vegaMagCorrection1 = 0;
		double vegaMagCorrection2 = 0;
		double vegaMagCorrection3 = 0;
		double vegaMagCorrection4 = 0;
		if (vSwitch == 1) {
			//AT WHAT Z? SOULD I BE TAKING OFF COLOUR OR OFF MAGNITUDE?
			double vegaFullSpectrum[][] = fillArray(
					"starburstSpectra//" + "VegaLCB.dat", 1);
			double vegaspectrumArray1[] = new double[gaussian1.n + 1];
			double vegaspectrumArray2[] = new double[gaussian2.n + 1];
			double vegaspectrumArray3[] = new double[gaussian3.n + 1];
			double vegaspectrumArray4[] = new double[gaussian4.n + 1];
			vegaspectrumArray1 = gaussian1.interpolate(gaussian1,
					vegaFullSpectrum);
			vegaspectrumArray2 = gaussian2.interpolate(gaussian2,
					vegaFullSpectrum);
			vegaspectrumArray3 = gaussian3.interpolate(gaussian3,
					vegaFullSpectrum);
			vegaspectrumArray4 = gaussian4.interpolate(gaussian4,
					vegaFullSpectrum);
			vegaMagCorrection1 = magnitude(gaussian1, filterArray1,
					normalisation1G, vegaspectrumArray1);
			vegaMagCorrection2 = magnitude(gaussian2, filterArray2, normalisation2G,
							vegaspectrumArray2);
			vegaMagCorrection3 = magnitude(gaussian3, filterArray3,
					normalisation3G, vegaspectrumArray3);
			vegaMagCorrection4 =  magnitude(gaussian4, filterArray4, normalisation4G,
							vegaspectrumArray4);
		}

		//Prints to file the requested colour system
		if (vSwitch == 0) {
			out.println((mAB1A - mAB2A) + " " + (mAB3A - mAB4A));
			System.out.println("\nColour 1 average: " + (mAB1A - mAB2A));
			System.out.println("Colour 2 average: " + (mAB3A - mAB4A)+"\n");
		} else if (vSwitch == 1) {
			out.println(((mAB1A - mAB2A) - vegaMagCorrection1 + vegaMagCorrection2) + " "
					+ ((mAB3A - mAB4A) - vegaMagCorrection3 + vegaMagCorrection4));
			System.out.println("\nColour 1 average: " + ((mAB1A - mAB2A)-vegaMagCorrection1+vegaMagCorrection2));
			System.out.println("Colour 2 average: " + ((mAB3A - mAB4A)-vegaMagCorrection3+vegaMagCorrection4)+"\n");
		}
		out.close();

	}

	// Method to fill an array from an input file
	public static double[][] fillArray(String File, double zeff)
			throws IOException {
		FileReader fileReader = new FileReader(File);
		StreamTokenizer fileData = new StreamTokenizer(fileReader);
		fileData.nextToken();
		int nArray = (int) fileData.nval;
		fileData.nextToken();
		int convUnits = (int) fileData.nval;
		double fullArray[][] = new double[2][nArray + 1];
		for (int j = 1; j < nArray + 1; j++) {
			fileData.nextToken();
			fullArray[0][j] = zeff * convUnits * fileData.nval;
			fileData.nextToken();
			fullArray[1][j] = fileData.nval;
		}
		return fullArray;
	}

	// Method to normalise the filter array
	public static double normalise(double filterArray[], Gaussian gaussian) {
		double filterArrayN[] = new double[gaussian.n + 1];
		for (int j = 0; j < filterArray.length; j++) {
			filterArrayN[j] = filterArray[j] / gaussian.xi[j];
		}
		double normalisation = gaussian.integrate(gaussian, filterArrayN);
		return normalisation;
	}

	// Method to calculate the magnitude
	public static double magnitude(Gaussian gaussian, double filterArray[],
			double normalisation, double spectrumArray[]) throws IOException {
		double[] integralArray = new double[gaussian.n + 1];
		for (int j = 1; j < gaussian.n + 1; j++) {
			integralArray[j] = filterArray[j] * Math.pow(10, spectrumArray[j])
					* gaussian.xi[j];
		}
		double integral = gaussian.integrate(gaussian, integralArray);

		double mAB = -2.5 * Math.log10(integral / normalisation);
		return mAB;
	}

}
