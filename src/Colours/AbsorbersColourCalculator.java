package Colours;

import java.io.*;
import Transmission.MonteCarlo;
import Other.frequencyreader;
import Integrators.*;
import Transmission.DiffuseIGM;
import Transmission.LyAforest;

/**
 *This program calculators the colours when there are absorbers involved.
 *If called it will always include the Lyman Alpha forest (as in Meiksin 2006) and 
 *absorption by the diffuse IGM (Meiksin priv. communication). The properties of the LLSs
 *are decided by the user from the GUI input.
 * 
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

public class AbsorbersColourCalculator {

	public static void calcColour(String Filters[], String spectrumFileName,
			double z0, double z1, double z2, int MeiksinTau, int InoueTau,
			double Avalue, double gamma1, double gamma2, double gamma3,
			double AI, double beta, double N0, double spacing, int fileread,
			String inputname, int realisations, int indiv, int vSwitch,
			String outputFileName, int freq, String frequencyFileName1,
			String frequencyFileName2) throws IOException {

		// Creates files to write to
		PrintWriter out = new PrintWriter(new FileWriter("output//"
				+ outputFileName));
		PrintWriter absSys = new PrintWriter(new FileWriter("output//"
				+ "absorbers.dat"));
		PrintWriter avTrans = new PrintWriter(new FileWriter("output//"
				+ "averageTransmission.dat"));
		PrintWriter kValues = new PrintWriter(new FileWriter("output//"
				+ "kIGMValues.dat"));
		out.println(realisations);

		/* Set up the filters and normalises them */
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

		/* Set up interpolated spectrum */
		double fullSpectrumArray[][] = fillArray(spectrumFileName, 1 + z0);
		double spectrumArray1[] = new double[gaussian1.n + 1];
		double spectrumArray2[] = new double[gaussian2.n + 1];
		double spectrumArray3[] = new double[gaussian3.n + 1];
		double spectrumArray4[] = new double[gaussian4.n + 1];
		spectrumArray1 = gaussian1.interpolate(gaussian1, fullSpectrumArray);
		spectrumArray2 = gaussian2.interpolate(gaussian2, fullSpectrumArray);
		spectrumArray3 = gaussian3.interpolate(gaussian3, fullSpectrumArray);
		spectrumArray4 = gaussian4.interpolate(gaussian4, fullSpectrumArray);

		/* Set up transmission arrays */
		double transmissionArray1[] = new double[gaussian1.n + 1];
		double transmissionArray2[] = new double[gaussian2.n + 1];
		double transmissionArray3[] = new double[gaussian3.n + 1];
		double transmissionArray4[] = new double[gaussian4.n + 1];
		for (int p = 1; p < gaussian1.n; p++) {
			transmissionArray1[p] = 1;
			transmissionArray2[p] = 1;
			transmissionArray3[p] = 1;
			transmissionArray4[p] = 1;
		}

		// Calculates mags with no absorbers
		double mAB1A = magnitude(gaussian1, filterArray1, normalisation1G,
				spectrumArray1, transmissionArray1);
		double mAB2A = magnitude(gaussian2, filterArray2, normalisation2G,
				spectrumArray2, transmissionArray2);
		double mAB3A = magnitude(gaussian3, filterArray3, normalisation3G,
				spectrumArray3, transmissionArray3);
		double mAB4A = magnitude(gaussian4, filterArray4, normalisation4G,
				spectrumArray4, transmissionArray4);

		// If vega mags selected, calculates vega colour through filters
		double vegaMagCorrection1 = 0;
		double vegaMagCorrection2 = 0;
		double vegaMagCorrection3 = 0;
		double vegaMagCorrection4 = 0;
		if (vSwitch == 1) {
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
					normalisation1G, vegaspectrumArray1, transmissionArray1);
			vegaMagCorrection2 = magnitude(gaussian2, filterArray2, normalisation2G,
							vegaspectrumArray2, transmissionArray2);
			vegaMagCorrection3 = magnitude(gaussian3, filterArray3,
					normalisation3G, vegaspectrumArray3, transmissionArray3);
			vegaMagCorrection4 =  magnitude(gaussian4, filterArray4, normalisation4G,
							vegaspectrumArray4, transmissionArray4);
		}

		// Sets up a transmission array, only over the wavelength range of the
		// filters used
		double[] testLow = { fullFilterArray1[0][1], fullFilterArray2[0][1],
				fullFilterArray3[0][1], fullFilterArray4[0][1] };
		double[] testHigh = {
				fullFilterArray1[0][fullFilterArray1[0].length - 1],
				fullFilterArray2[0][fullFilterArray2[0].length - 1],
				fullFilterArray3[0][fullFilterArray3[0].length - 1],
				fullFilterArray4[0][fullFilterArray4[0].length - 1] };
		int lrange = (int) (testLow[0] - 5);

		for (int i = 0; i < 4; i++) {
			if (testLow[i] < lrange) {
				lrange = (int) (testLow[i] - 5);
			}
		}
		int urange = (int) (testHigh[0] + 5);
		for (int i = 0; i < 4; i++) {
			if (testHigh[i] > urange) {
				urange = (int) (testHigh[i] + 5);
			}
		}
		//int lrange = 1000;
		//int urange = 11000;

		int upperr = (int) ((double) urange / spacing);
		int lowerr = (int) ((double) lrange / spacing);
		int transLength = upperr - lowerr;
		double totalTrans[] = new double[transLength + 1];

		// Transmission that we don't Monte Carlo over // ie. LyAlpha Forest and diffuse IGM continuum
		double[] standardTransmissionArray = standardTransmission(lowerr,
				upperr, spacing, z0, Avalue);
		double[][] fullTransmissionArray = new double[2][transLength + 1];

		// Variables that will be summed and averaged over
		double total1 = 0;
		double total2 = 0;
		double k1 = 0;
		double k2 = 0;
		double k3 = 0;
		double k4 = 0;

		// array of colours for each realisation
		double[] colours1 = new double[realisations];
		double[] colours2 = new double[realisations];

		for (int r = 1; r < realisations + 1; r++) {
			absSys.println(r);
			// Creates an array of LLSs depending on the type of absorbers
			// chosen
			double[] LLSArray;
			if (MeiksinTau == 1) {
				LLSArray = MonteCarlo.MCSMeiksin(gamma1, beta, N0, z0, lrange,
						urange, indiv, spacing, inputname, absSys);
			} else if (InoueTau == 1) {
				LLSArray = MonteCarlo.MCSInoue(gamma1, gamma2, gamma3, beta,
						N0, z0, z1, z2, AI, lrange, urange, indiv, spacing,
						inputname, absSys);
			} else {
				LLSArray = MonteCarlo.MCSfileread(lrange, urange, spacing,
						inputname,z0);
			}
			// Total transmission arrays
			double[] result = new double[LLSArray.length];
			for (int t = 1; t < upperr; t++) {
				result[t] = standardTransmissionArray[t] * LLSArray[t];
			}
			int t = 0;
			for (int k = lowerr; k < upperr; k++) {
				t = t + 1;
				double j = k * spacing;
				fullTransmissionArray[0][t] = j;
				fullTransmissionArray[1][t] = result[k];
				totalTrans[t] += fullTransmissionArray[1][t];
			}
			transmissionArray1 = gaussian1.interpolate(gaussian1,
					fullTransmissionArray);
			transmissionArray2 = gaussian2.interpolate(gaussian2,
					fullTransmissionArray);
			transmissionArray3 = gaussian3.interpolate(gaussian3,
					fullTransmissionArray);
			transmissionArray4 = gaussian4.interpolate(gaussian4,
					fullTransmissionArray);

			// Calculates magnitudes with absorbers
			double mAB1 = magnitude(gaussian1, filterArray1, normalisation1G,
					spectrumArray1, transmissionArray1);
			double mAB2 = magnitude(gaussian2, filterArray2, normalisation2G,
					spectrumArray2, transmissionArray2);
			double mAB3 = magnitude(gaussian3, filterArray3, normalisation3G,
					spectrumArray3, transmissionArray3);
			double mAB4 = magnitude(gaussian4, filterArray4, normalisation4G,
					spectrumArray4, transmissionArray4);
			double colour1 = mAB1 - mAB2;
			double colour2 = mAB3 - mAB4;

			// If vega mags then takes off vega colour
			if (vSwitch == 1) {
				colour1 = colour1 - vegaMagCorrection1 + vegaMagCorrection2;
				colour2 = colour2 - vegaMagCorrection3 + vegaMagCorrection4;
			}

			// Prints out colour for every realisation (if requested)
			if (indiv == 1) {
				out.println(colour1 + " " + colour2);
			}

			// Creates an array of all the colours
			colours1[r - 1] = colour1;
			colours2[r - 1] = colour2;

			// Summing up totals
			total1 = total1 + colour1;
			total2 = total2 + colour2;

			// Working out k corrections
			k1 = k1 + (mAB1 - mAB1A);
			k2 = k2 + (mAB2 - mAB2A);
			k3 = k3 + (mAB3 - mAB3A);
			k4 = k4 + (mAB4 - mAB4A);

			System.out.println(100 * r / realisations + "%");
		}

		// Prints out average colour
		if (indiv == 0) {
			total1 = total1 / realisations;
			total2 = total2 / realisations;
			out.println(total1 + " " + total2);
			System.out.println("\nColour 1 average: " + total1);
			System.out.println("Colour 2 average: " + total2 + "\n");
		}

		// Works out averages k corrections
		k1 = k1 / realisations;
		k2 = k2 / realisations;
		k3 = k3 / realisations;
		k4 = k4 / realisations;
		kValues.println(k1 + " " + k2 + " " + k3 + " " + k4);

		// Prints out average transmission
		for (int i = 1; i < transLength; i++) {
			totalTrans[i] = totalTrans[i] / realisations;
			avTrans.println((lowerr + i * spacing - 1) + " " + totalTrans[i]);
		}

		out.close();
		absSys.close();
		avTrans.close();
		kValues.close();

		// Works our cumulative frequency (if requested)
		if (freq == 1) {
			frequencyreader.frequencycalculator(colours1, colours2,
					frequencyFileName1, frequencyFileName2, realisations);
		}

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

	// Method to normalise the filter array using gaussian
	public static double normalise(double filterArray[], Gaussian gaussian) {
		double filterArrayN[] = new double[gaussian.n + 1];
		for (int j = 0; j < filterArray.length; j++) {
			filterArrayN[j] = filterArray[j] / gaussian.xi[j];
		}
		double normalisation = gaussian.integrate(gaussian, filterArrayN);
		return normalisation;
	}

	// Method to work out the transmission that isn't Monte Carlo
	// ie.e LyAlpha Forest and diffuse IGM continuum
	public static double[] standardTransmission(int lowerr, int upperr,
			double spacing, double z0, double Avalue) {
		double[] standardTransmission = new double[upperr];
		for (int p = lowerr; p < upperr; p++) {
			standardTransmission[p] = LyAforest.taulya(p * spacing, z0);
		}
		int max = (int) (912.0 / spacing * (z0 + 1));
		for (int i = lowerr; i < max; i++) {
			standardTransmission[i] = standardTransmission[i]
					* DiffuseIGM.tauIGM(i * spacing, z0, Avalue);
		}
		return standardTransmission;
	}

	// Method to calculate the magnitudes, converts log(L) --> L
	public static double magnitude(Gaussian gaussian, double filterArray[],
			double normalisation, double spectrumArray[],
			double transmissionArray[]) throws IOException {
		double[] integralArray = new double[gaussian.n + 1];
		for (int j = 1; j < gaussian.n + 1; j++) {
			integralArray[j] = filterArray[j] * transmissionArray[j]
					* Math.pow(10, spectrumArray[j]) * gaussian.xi[j];
		}
		double integral = gaussian.integrate(gaussian, integralArray);

		double mAB = -2.5 * Math.log10(integral / normalisation);
		return mAB;
	}

}
