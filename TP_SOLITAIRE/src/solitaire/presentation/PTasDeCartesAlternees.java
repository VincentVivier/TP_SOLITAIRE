package solitaire.presentation;

import java.awt.Color;
import java.awt.Dimension;

import solitaire.controleur.CTasDeCartesAlternees;

public class PTasDeCartesAlternees extends PTasDeCartes {

	private static final long serialVersionUID = 1L;
	
	//ICTasDeCartes c;
	
	public PTasDeCartesAlternees(CTasDeCartesAlternees c) {
		super(c);
		
		setBackground(Color.ORANGE);
		
		setPreferredSize(new Dimension(100, 400));
		setSize(getPreferredSize());
		
	}

}
