package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.io.*;
import javax.swing.*;

import Colours.AbsorbersColourCalculator;
import Colours.colourCalculator;

/**
 *This is the program that sets up the gui and collects all the input
 *parameters and options
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

public class ColourGUI implements ActionListener {

	// Text boxes, labels, panels and check boxes to go into gui
	JPanel guipanel, spectrumpanel, spectrumUserpanel, colourpanel, LLSpanel, LLSpanel2,
			optionspanel, helppanel, colourgopanel, Meiksinpanel, Inouepanel,zPanel,
			fromFilepanel, noAttenuationPanel, aboutpanel, attenuationOptionPanel;
	JTextField zin, Ain, gammain, gamma1in, gamma2in, gamma3in, betaMin, betaIin, N0in,
			z1in, z2in, AIin, spacingin, numberin, filenamein, outputFilename,
			analyticFilename, frequencynamein1, frequencynamein2;
	JTextPane textArea, textAbout,noAttenl;
	JLabel helpl, zinl, colour1l, colour2l, ageinl, Zinl, Ainl, gammainl,
			gamma1inl, gamma2inl, gamma3inl, N0inl, betaMinl, betaIinl, spacinginl,
			filereaderl, numberinl, indivl, filenameinl, outputFilenamel,
			anaylticFilenamel, Meiksinl, Blankl, Blankl2, Blankl3, Blankl4, Blankl5,Blankl6,Blankl7,
			frequencyinl, uploadedSpecl;
	JCheckBox individualres, frequency;
	JButton colourGO;
	JRadioButton ABmagB, VegaMagB, starburst, continuous, Meiksin, Inoue, filereader, noAtten, SBspec, uploadedSpec;
	JTabbedPane master;
	JTabbedPane LLSmaster;
	JComboBox colour1fA, colour1fB, colour2fA, colour2fB, age, metallicity,uploadedSpectra;
	JScrollPane helpscroll;	
	
	// Sets up some standard borders and fonts
	Color background = new Color(185, 210, 230);
	Color background2 = new Color(180, 195, 220);
	Color background3 = new Color(180,180,230);
	Border blackline = BorderFactory.createLineBorder(background2);
	Border etched = BorderFactory.createEtchedBorder();
	Border loweredbevel = BorderFactory.createLoweredBevelBorder();
	Font title1 = new Font("SansSerif", Font.PLAIN, 13);
	Font title2 = new Font("SansSerif", Font.PLAIN, 11);
	Font title3 = new Font("SansSerif", Font.ITALIC, 11);
	Font text = new Font("SansSerif", Font.PLAIN, 11);
	Font text2 = new Font("SansSerif", Font.PLAIN, 11);

	public void addstuff(Container pane) {

		// Sets the properties of the panels
		guipanel = new JPanel();
		guipanel.setLayout(new BoxLayout(guipanel, BoxLayout.PAGE_AXIS));
		guipanel.setBackground(background2);
		spectrumpanel = new JPanel(new GridLayout(3, 3));
		spectrumpanel.setBackground(background2);
		spectrumUserpanel = new JPanel(new GridLayout(1,3));
		spectrumUserpanel.setBackground(background2);
		zPanel = new JPanel(new GridLayout(1,2));
		zPanel.setBackground(background2);
		colourpanel = new JPanel(new GridLayout(3, 3));
		colourpanel.setBackground(background);
		LLSpanel = new JPanel(new GridLayout(2, 2));
		LLSpanel.setBackground(background3);
		Meiksinpanel = new JPanel(new GridLayout(4, 2));
		Meiksinpanel.setBackground(background3);
		attenuationOptionPanel = new JPanel(new GridLayout(1, 4));
		attenuationOptionPanel.setBackground(background3);
		Inouepanel = new JPanel(new GridLayout(4, 3));
		Inouepanel.setBackground(background3);
		fromFilepanel = new JPanel(new GridLayout(4, 3));
		fromFilepanel.setBackground(background3);
		optionspanel = new JPanel(new GridLayout(3, 3));
		optionspanel.setBackground(background);
		colourgopanel = new JPanel(new GridLayout(1, 1));
		helppanel = new JPanel();
		helppanel.setLayout(new BoxLayout(helppanel, BoxLayout.X_AXIS));
		aboutpanel = new JPanel();
		aboutpanel.setLayout(new BoxLayout(aboutpanel, BoxLayout.X_AXIS));
		noAttenuationPanel = new JPanel(new GridLayout(1,1));	
		noAttenuationPanel.setBackground(background3);	
		
		// Creates the tabbed panels
		JTabbedPane master = new JTabbedPane();
		JTabbedPane LLSmaster = new JTabbedPane();
		master.addTab("Input", guipanel);
		master.addTab("Help", helppanel);
		master.addTab("About", aboutpanel);
		master.setFont(title1);
		LLSmaster.addTab("Info.", noAttenuationPanel);
		LLSmaster.addTab("Meiksin 06 params", Meiksinpanel);
		LLSmaster.addTab("Inoue-Iwata 08 params", Inouepanel);
		LLSmaster.addTab("LLSs input filename", fromFilepanel);
		LLSmaster.setFont(title2);
		pane.add(master, BorderLayout.CENTER);
		master.setBackgroundAt(1, background);
		master.setBackgroundAt(0, background);
		LLSmaster.setBackgroundAt(1, background2);
		LLSmaster.setBackgroundAt(0, background2);
		
		// Adds the panels to the main frame and calls addWidgets
		addWidgets();
		guipanel.add(colourpanel);
		guipanel.add(spectrumpanel);
		guipanel.add(spectrumUserpanel);
		guipanel.add(zPanel);
		guipanel.add(attenuationOptionPanel);
		guipanel.add(LLSmaster, BorderLayout.CENTER);
		guipanel.add(LLSpanel);
		guipanel.add(optionspanel);
		guipanel.add(colourgopanel);

	}

	// Method that sets properties of all the objects and adds them to the gui
	private void addWidgets() {

		// Colour Panel Objects
		colour1l = new JLabel("Colour 1", SwingConstants.LEFT);
		colour1l.setFont(title2);
		colour2l = new JLabel("Colour 2", SwingConstants.LEFT);
		colour2l.setFont(title2);
		File filters = new File("filters//");
		String[] filterlist = filters.list();
		colour1fA = new JComboBox(filterlist);
		colour1fA.setSelectedIndex(0);
		colour1fA.setFont(text);
		colour1fB = new JComboBox(filterlist);
		colour1fB.setSelectedIndex(0);
		colour1fB.setFont(text);
		colour2fA = new JComboBox(filterlist);
		colour2fA.setSelectedIndex(0);
		colour2fA.setFont(text);
		colour2fB = new JComboBox(filterlist);
		colour2fB.setSelectedIndex(0);
		colour2fB.setFont(text);
		ButtonGroup magsGroup = new ButtonGroup();
		ABmagB = new JRadioButton("AB mag");
		VegaMagB = new JRadioButton("Vega mag");
		magsGroup.add(ABmagB);
		magsGroup.add(VegaMagB);	
		ABmagB.setFont(title2);
		ABmagB.setBackground(background);
		VegaMagB.setFont(title2);
		VegaMagB.setBackground(background);
		ABmagB.setSelected(true);
		
		// Spectrum panel objects		
		ageinl = new JLabel("Galaxy age", SwingConstants.LEFT);
		ageinl.setFont(title2);
		String[] agelist = { "3Myrs", "10Myrs", "30Myrs", "100Myrs", "300Myrs",
				"600Myrs" };
		age = new JComboBox(agelist);
		age.setSelectedIndex(0);
		age.setFont(text);
		Zinl = new JLabel("Metallicity", SwingConstants.LEFT);
		Zinl.setFont(title2);
		String[] Zlist = { "0.004", "0.02", "0.04" };
		metallicity = new JComboBox(Zlist);
		metallicity.setSelectedIndex(0);
		metallicity.setFont(text);
		ButtonGroup sbGroup = new ButtonGroup();
		starburst = new JRadioButton("Instantaneous");
		sbGroup.add(starburst);
		starburst.setFont(title2);
		starburst.setBackground(background2);
		starburst.setSelected(true);
		continuous = new JRadioButton("Continuous");
		continuous.setFont(title2);
		continuous.setBackground(background2);
		sbGroup.add(continuous);
		starburst.setSelected(true);
		
		ButtonGroup specChoiceGroup = new ButtonGroup();
		SBspec = new JRadioButton("Starburst99 spectra.");	
		SBspec.setFont(title2);
		SBspec.setBackground(background2);
		SBspec.setSelected(true);
		specChoiceGroup.add(SBspec);
		uploadedSpec = new JRadioButton("Use uploaded spectrum.");	
		uploadedSpec.setFont(title2);
		uploadedSpec.setBackground(background2);
		specChoiceGroup.add(uploadedSpec);
		
		zinl = new JLabel("Place spectrum at redshift", SwingConstants.LEFT);
		zinl.setFont(title2);
		zin = new JTextField(2);
		zin.setText("3");
		zin.setFont(text);

		File userSpecFiles = new File("uploadedSpectra//");
		String[] speclist = userSpecFiles.list();
		uploadedSpectra = new JComboBox(speclist);
		uploadedSpectra.setSelectedIndex(0);
		uploadedSpectra.setFont(text);
		
		// Absorbers panel objects
		ButtonGroup llsGroup = new ButtonGroup();
		noAtten = new JRadioButton("No IGM attenuation");
		llsGroup.add(noAtten);
		noAtten.setFont(title2);
		noAtten.setBackground(background3);
		noAtten.setSelected(true);
		noAttenl = new JTextPane();
		noAttenl.setFont(text2);
		noAttenl.setBackground(background3);
		noAttenl.setText("--Select an option for distributing Lyman Limit Systems using the circular radio buttons." +
				"\n--Selecting 'No IGM attenuation' calculates colours with no attenuation at all, suppressing the contributions " +
				"from the Lyman Alpha forest, the diffuse IGM and from Lyman Limit Systems." +
				"\n--If one of the other options is selected, the Lyman Alpha Forest will also be included according to Meiksin 2006 " +
				"and a contribution from the diffuse IGM. Click on the appropriate tab if you wish " +
				"to change from the default parameters for the chosen LLS distribution or to chose an input file if you have selected 'LLSs from file.'");
		noAttenl.setEditable(false);
		Meiksinl = new JLabel("");
		Meiksin = new JRadioButton("Meiksin 06");
		llsGroup.add(Meiksin);
		Meiksin.setFont(title2);
		Meiksin.setBackground(background3);
		Blankl = new JLabel("");
		Blankl2 = new JLabel("");
		Blankl3 = new JLabel("");
		Blankl4 = new JLabel("Starburst99 Spectra");
		Blankl4.setFont(title2);
		Blankl5 = new JLabel("");
		Blankl6 = new JLabel("");
		Blankl7 = new JLabel("");
		Blankl7.setFont(title2);
		Inoue = new JRadioButton("Inoue-Iwata 08");
		llsGroup.add(Inoue);
		Inoue.setFont(title2);
		Inoue.setBackground(background3);
		Ainl = new JLabel("Diffuse IGM normalisation", SwingConstants.LEFT);
		Ainl.setFont(title2);
		Ain = new JTextField(2);
		Ain.setText("0.07553");
		Ain.setFont(text);
		N0inl = new JLabel("N0", SwingConstants.LEFT);
		N0inl.setFont(title2);
		gammainl = new JLabel("Gamma", SwingConstants.LEFT);
		gammainl.setFont(title2);
		gamma1inl = new JLabel("Gamma 1, z1", SwingConstants.LEFT);
		gamma1inl.setFont(title2);
		gamma2inl = new JLabel("Gamma 2, z2", SwingConstants.LEFT);
		gamma2inl.setFont(title2);
		gamma3inl = new JLabel("Gamma 3, A", SwingConstants.LEFT);
		gamma3inl.setFont(title2);
		gammain = new JTextField(2);
		gammain.setText("1.5");
		gammain.setFont(text);
		gamma1in = new JTextField(2);
		gamma1in.setText("0.2");
		gamma1in.setFont(text);
		gamma2in = new JTextField(2);
		gamma2in.setText("2.5");
		gamma2in.setFont(text);
		gamma3in = new JTextField(2);
		gamma3in.setText("4.0");
		gamma3in.setFont(text);
		z1in = new JTextField(2);
		z1in.setText("1.2");
		z1in.setFont(text);
		z2in = new JTextField(2);
		z2in.setText("4.0");
		z2in.setFont(text);
		AIin = new JTextField(2);
		AIin.setText("400");
		AIin.setFont(text);
		betaMin = new JTextField(2);
		betaMin.setText("1.5");
		betaMin.setFont(text);
		betaMinl = new JLabel("Beta", SwingConstants.LEFT);
		betaMinl.setFont(title2);
		betaIin = new JTextField(2);
		betaIin.setText("1.3");
		betaIin.setFont(text);
		betaIinl = new JLabel("Beta", SwingConstants.LEFT);
		betaIinl.setFont(title2);
		N0in = new JTextField(2);
		N0in.setText("0.25");
		N0in.setFont(text);
		numberin = new JTextField(2);
		numberin.setText("1000");
		numberin.setFont(text);
		spacinginl = new JLabel("Wavelength Granularity", SwingConstants.LEFT);
		spacinginl.setFont(title2);
		spacingin = new JTextField(2);
		spacingin.setText("1");
		spacingin.setFont(text);
		filereaderl = new JLabel("");
		filereader = new JRadioButton("LLSs from file");
		llsGroup.add(filereader);
		filereader.setFont(title2);
		filereader.setBackground(background3);
		filenameinl = new JLabel("External filename", SwingConstants.LEFT);
		filenameinl.setFont(title2);
		filenamein = new JTextField(2);
		filenamein.setText("input.dat");
		filenamein.setFont(text);

		// Options panel objects
		numberinl = new JLabel("Number of Runs:", SwingConstants.LEFT);
		numberinl.setFont(title2);
		numberin = new JTextField(2);
		numberin.setText("1000");
		numberin.setFont(text);
		individualres = new JCheckBox("Print individually?");
		individualres.setFont(title2);
		individualres.setBackground(background);
		indivl = new JLabel("(Otherwise prints average)", SwingConstants.LEFT);
		indivl.setFont(title2);
		outputFilenamel = new JLabel("Output file name:", SwingConstants.LEFT);
		outputFilenamel.setFont(title2);
		outputFilename = new JTextField(2);
		outputFilename.setText("colours.dat");
		outputFilename.setFont(text);
		frequency = new JCheckBox("Calculate cumulative frequencies?");
		frequency.setFont(title2);
		frequency.setBackground(background);
		frequencyinl = new JLabel("Choose output filenames:",
				SwingConstants.LEFT);
		frequencyinl.setFont(title2);
		frequencynamein1 = new JTextField(2);
		frequencynamein1.setText("CFcolour1.dat");
		frequencynamein1.setFont(text);
		frequencynamein2 = new JTextField(2);
		frequencynamein2.setText("CFcolour2.dat");
		frequencynamein2.setFont(text);

		// Colour go panel. Listen to events from the Go! button.
		colourGO = new JButton("Calculate colour");
		colourGO.addActionListener(this);

		// Help panel
		textArea = new JTextPane();
		textArea
				.setText("This program calculates the colours of the specified sources as looked through specified filters, with or without IGM attenuation."
						+ "\n\n1. Choose the filters to compute the colours through."
						+ "\n2. Choose which magnitude system you want to use. The Vega magnitude system is set so that Vega will have zero colour in all bands. The magnitudes are computed following the convention of Fukugita et al. (1996) AJ 111:1748."
						+ "\n3. Choose the properties of the source and a redshift to place it at. The Starburst99 spectra are taken from the stellar plus nebular emission models of Leitherer et al. 1999 ApJS 123:3. Alternatively a spectrum data file can be placed in the uploadedSpectra directory (in the same format as exmaple.dat) and then it will become available in the drop-down menu. For this option click 'Use uploaded spectrum instead' and select from the drop-down menu."
						+ "\n\nIf you do not want to include any IGM attenuation hit 'Calculate Colour' button now and ignore below. If you want to include IGM attenuation see below."
						+ "\n\n5. Choose how to distribute the LLSs, i.e. using Meiksin 06, Inoue-Iwata 08 or from an input file."
						+ "\n6. Set the parameters for the distribution functions (see below)."
						+ " \n7. Choose the wavelength granularity for computing the transmission."
						+ " \n8. Choose the number of realisations (lines of sight)."
						+ "\n9. There's the option to either print each realisation on a separate line in the output file. Otherwise it will print an average to the file."
						+ "\n10. There's the option to calculate the cumulative frequency distributions of the colours for random lines of sight. This is only relevant if a scatter is present."
						+ " \nNB. Files go to 'output' folder. Will also print average transmission profile of the absorbers used, the average IGM k-correction values for each filter and the absorber's properties used in each realisation to separate files."
						+ " These are named 'averageTransmission.dat', 'kIGMValues.dat' and 'absorbers.dat' respectively"
						+ "\n\nTHE LLS ABSORBERS"
						+ "\nYou have the option of using your own LLS absorbers and properties from an input file. This should be in the format:"
						+ "\nN"
						+ "\nz1 tau1"
						+ "\nz2 tau2"
						+ "\n..."
						+ "\nzN tauN"
						+ "\n\nMeiksin z distribution: The z distribution of LLSs is taken from Meiksin (2006) MNRAS 365:807."
						+ "\nInoue z distribution: The z distribution of LLSs is taken from Inoue & Iwata (2008) MNRAS 387:1681."
						+ "\nThe number actually intercepted along each line of sight (i.e. realisation) is a Possion process."
						+ "\nThe opacity of each LLS is taken from the distribution: dN/Dtau = tau^-Beta."
						+ "\nNOTE: If you decide to include any LLS absorbers, a contribution from the Ly Alpha Forest (Meiksin 2006) and a contribution from the diffuse IGM are also included. These two contributions are not included in the Monte Carlo, only the LLSs are.");
		textArea.setFont(text);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea); 
		
		//About Panel
		textAbout = new JTextPane();
		textAbout
		.setText("Created by Chris M. Harrison (1) (based on an earlier code by David Stock), under the supervision of Avery Meiksin at the University of Edinburgh." +
				"\n\n(1) Now at the Department of Physics, Durham University. Contact: c.m.harrison@dur.ac.uk");
		textAbout.setFont(text);
		textAbout.setEditable(false);
		
		// Add the widgets to the container.
		colourpanel.add(colour1l);
		colourpanel.add(colour1fA);
		colourpanel.add(colour1fB);
		colourpanel.add(colour2l);
		colourpanel.add(colour2fA);
		colourpanel.add(colour2fB);
		colourpanel.add(ABmagB);
		colourpanel.add(VegaMagB);
	
		spectrumpanel.add(Blankl3);
		spectrumpanel.add(ageinl);
		spectrumpanel.add(age);
		spectrumpanel.add(SBspec);
		spectrumpanel.add(Zinl);
		spectrumpanel.add(metallicity);
		spectrumpanel.add(Blankl6);
		spectrumpanel.add(starburst);
		spectrumpanel.add(continuous);
		spectrumUserpanel.add(uploadedSpec);
		spectrumUserpanel.add(uploadedSpectra);	
		spectrumUserpanel.add(Blankl7);		
		zPanel.add(zinl);
		zPanel.add(zin);
		
		LLSpanel.add(Ainl);
		LLSpanel.add(Ain);
		LLSpanel.add(spacinginl);
		LLSpanel.add(spacingin);

		attenuationOptionPanel.add(noAtten);
		attenuationOptionPanel.add(Meiksin);
		attenuationOptionPanel.add(Inoue);
		attenuationOptionPanel.add(filereader);	
		
		fromFilepanel.add(filereaderl);
		fromFilepanel.add(filenameinl);
		fromFilepanel.add(filenamein);

		noAttenuationPanel.add(noAttenl);	

		Meiksinpanel.add(N0inl);
		Meiksinpanel.add(N0in);
		Meiksinpanel.add(gammainl);
		Meiksinpanel.add(gammain);
		Meiksinpanel.add(betaMinl);
		Meiksinpanel.add(betaMin);
		
		
		Inouepanel.add(gamma1inl);
		Inouepanel.add(gamma1in);
		Inouepanel.add(z1in);
		Inouepanel.add(gamma2inl);
		Inouepanel.add(gamma2in);
		Inouepanel.add(z2in);
		Inouepanel.add(gamma3inl);
		Inouepanel.add(gamma3in);
		Inouepanel.add(AIin);
		Inouepanel.add(betaIinl);
		Inouepanel.add(betaIin);
		
		optionspanel.add(numberinl);
		optionspanel.add(numberin);
		optionspanel.add(individualres);
		//optionspanel.add(indivl);
		optionspanel.add(outputFilenamel);
		optionspanel.add(outputFilename);
		optionspanel.add(Blankl5);
		optionspanel.add(frequency);
		//optionspanel.add(frequencyinl);
		optionspanel.add(frequencynamein1);		
		optionspanel.add(frequencynamein2);

		helppanel.add(scrollPane);
		aboutpanel.add(textAbout);
		colourgopanel.add(colourGO);

		// Set borders of panels
		spectrumpanel.setBorder(BorderFactory.createTitledBorder(blackline,
				"Spectrum Options", 0, 0, title3));
		optionspanel.setBorder(BorderFactory.createTitledBorder(blackline,
				"Other Options", 0, 0, title3));
		attenuationOptionPanel.setBorder(BorderFactory.createTitledBorder(blackline,
				"Attenuation Options", 0, 0, title3));
	}

	// Method to define what happens when the GO button is hit
	public void actionPerformed(ActionEvent event) {

		// Collects the info from the colour panel
		int vSwitch;
		Object c1fAO = colour1fA.getSelectedItem();
		String colour1A = "filters//" + c1fAO.toString();
		Object c1fBO = colour1fB.getSelectedItem();
		String colour1B = "filters//" + c1fBO.toString();
		Object c2fAO = colour2fA.getSelectedItem();
		String colour2A = "filters//" + c2fAO.toString();
		Object c2fBO = colour2fB.getSelectedItem();
		String colour2B = "filters//" + c2fBO.toString();
		String[] Filters = { " ", colour1A, colour1B, colour2A, colour2B };
		if (VegaMagB.isSelected()) {
			vSwitch = 1;
		} else {
			vSwitch = 0;
		}

		// Collects the info form the spectrum panel
		double z0 = (Double.parseDouble(zin.getText()));
		System.out.println("");
		System.out.println("Spectrum at z= "+z0);
		String spectrumFileName = "No spectra selected";
		if (SBspec.isSelected()) {
			Object ageO = age.getSelectedItem();
			Object ZO = metallicity.getSelectedItem();
			String choice = "";
			if (starburst.isSelected()) {
				choice = "I";
			} else if (continuous.isSelected()) {
				choice = "C";
			}
			spectrumFileName = "starburstSpectra//" + ageO.toString() + choice + "Z"
			+ ZO.toString() + ".dat";
		} else if (uploadedSpec.isSelected()) {
			Object fileInSpec = uploadedSpectra.getSelectedItem();
			spectrumFileName = "uploadedSpectra//" + fileInSpec;
		} else { System.out.println("Problem with input spectrum selection.");
		}	
		System.out.println("Spectrum: "+spectrumFileName);
		System.out.println("");
		
		// Collects the info from the options panel
		int individual, freq;
		int realisations = (int) (Double.parseDouble(numberin.getText()));
		if (individualres.isSelected() == true) {
			individual = 1;
		} else {
			individual = 0;
		}
		if (frequency.isSelected() == true) {
			freq = 1;
		} else {
			freq = 0;
		}
		String outputFileName = outputFilename.getText();
		String frequencyFileName1 = frequencynamein1.getText();
		String frequencyFileName2 = frequencynamein2.getText();

		// Collects the info from the general absorbers panel
		int MeiksinTau, InoueTau, fileread;
		double A = (Double.parseDouble(Ain.getText()));
		double spacing = (Double.parseDouble(spacingin.getText()));
		if (filereader.isSelected()) {
			fileread = 1;
		} else {
			fileread = 0;
		}
		String inputname = filenamein.getText();

		// Finds out whether using Meiksin, Inoue, File input or no absorbers
		// and collects parameters
		if (Meiksin.isSelected() && Inoue.isSelected() || Meiksin.isSelected()
				&& filereader.isSelected() || filereader.isSelected()
				&& Inoue.isSelected() || Meiksin.isSelected()
				&& Inoue.isSelected() && filereader.isSelected()) {
			System.out.println("ERROR: Only select one type of absorber");
			System.exit(0);
		} else if (Meiksin.isSelected())
				 {
			MeiksinTau = 1;
			InoueTau = 0;
			double gamma = (Double.parseDouble(gammain.getText()));
			double beta = (Double.parseDouble(betaMin.getText()));
			double N0 = Double.parseDouble(N0in.getText());
			double z1 = 0;
			double z2 = 0;
			double gamma2 = 0;
			double gamma3 = 0;
			double AI = 0;
			// Sends parameters to AbsorbersColourCalculator
			try {
				AbsorbersColourCalculator.calcColour(Filters, spectrumFileName,
						z0, z1, z2, MeiksinTau, InoueTau, A, gamma, gamma2,
						gamma3, AI, beta, N0, spacing, fileread, inputname,
						realisations, individual, vSwitch, outputFileName,
						freq, frequencyFileName1, frequencyFileName2);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (Inoue.isSelected()) {
			InoueTau = 1;
			MeiksinTau = 0;
			double gamma1 = (Double.parseDouble(gamma1in.getText()));
			double z1 = (Double.parseDouble(z1in.getText()));
			double gamma2 = (Double.parseDouble(gamma2in.getText()));
			double z2 = (Double.parseDouble(z2in.getText()));
			double gamma3 = (Double.parseDouble(gamma3in.getText()));
			double AI = (Double.parseDouble(AIin.getText()));
			double beta = (Double.parseDouble(betaIin.getText()));
			double N0 = Double.parseDouble(N0in.getText());
			// Sends parameters to AbsorbersColourCalculator
			try {
				AbsorbersColourCalculator.calcColour(Filters, spectrumFileName,
						z0, z1, z2, MeiksinTau, InoueTau, A, gamma1, gamma2,
						gamma3, AI, beta, N0, spacing, fileread, inputname,
						realisations, individual, vSwitch, outputFileName,
						freq, frequencyFileName1, frequencyFileName2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (filereader.isSelected()) {
			// Sends parameters to AbsorbersColourCalculator
			try {
				AbsorbersColourCalculator.calcColour(Filters, spectrumFileName,
						z0, 0, 0, 0, 0, A, 0, 0, 0, 0, 0, 0, spacing, fileread,
						inputname, 1, individual, vSwitch, outputFileName,
						freq, frequencyFileName1, frequencyFileName2);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				colourCalculator.calcColour(Filters, spectrumFileName, z0,
						vSwitch, outputFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("IGMtransmission");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create and set up the content pane.
		ColourGUI demo = new ColourGUI();
		demo.addstuff(frame.getContentPane());
		// Display the window.
		frame.pack();
		frame.setVisible(true);
		frame.setSize(600, 750);

	}

	public static void main(String[] args) {
		// Creates and shows gui
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
