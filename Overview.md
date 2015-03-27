# Introduction #

**IGMtransmission** is a Java graphical user interface (GUI) that models the effect of intergalactic neutral hydrogen attenuation on the colours of objects based on the model of Meiksin(2006). The code allows the effects to be modelled for a range of galaxies and filters provided. The galaxy spectra are taken from the stellar + nebular emission line models of Leitherer et al. (1999). Photometric filters are included for the _Hubble_ _Space_ _Telescope_, the Keck telescope, the Mt. Palomar 200-inch, the SUBARU telescope and UKIRT. Alternative spectra and filters may be straightforwardly added by the user.

To model the colours of high-redshift galaxies a model galaxy spectrum is taken, shifted appropriately for a chosen redshift, and then attenuated to account for intervening neutral hydrogen using then chosen model and set of parameters. Monte Carlo simulations over many lines of sight are performed, with the option of obtaining each colour independently or calculating an average. To do this, a population of discrete, Lyman Limit Systems are drawn from the distributions, dN/dz and dN/dtauL, placed along random lines of sight and their total contribution to the intergalactic transmission function calculated. In addition to the default choice, the alternative Lyman Limit Systems distribution of Inoue & Iwata (2008) is allowed for. Alternatively, the parameter values defining the model may be chosen by the user. Specifying a fixed set of individual Lyman Limit Systems is also accommodated. Mean contributions to the opacity due the Ly-alpha forest and the photoelectric absorption of the optically thin IGM are then included. The colours and IGM k-corrections are computed through the chosen broad-band filters, given this amount of attenuation for many lines of sight. The cumulative probability distributions, over the different lines of sight, are also produced. The model spectra, broad-band filter combinations, attenuation model parameters and output options are all chosen via the GUI. All results may be saved to files. The code requires Java v.1.6. It has been tested on _Windows_ _Vista_, Debian Linux v5.0 and OS X v.10.5.8. The code uses the random number generat or included in the Sun Microsystems' Java Development Kit v.1.6. To query the default version of Java running on a given system, type ``java -version.'' If it is not v.1.6, assistance from a system administrator may be required to obtain it.

# Details #

A screenshot of the GUI and an explanation of how it is used is included in Appendix A of the manual (doc/IGMtransmission.pdf).

# References #

Inoue A. K., Iwata I. 2008, MNRAS, 387, 1681

Leitherer C. et al., 1999, ApJS, 123, 3

Meiksin A. A. 2006, MNRAS, 365, 807