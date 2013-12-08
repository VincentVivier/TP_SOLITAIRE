package solitaire.presentation;

import java.awt.Color;
import solitaire.controleur.CTasDeCartesAlternees;

public class PTasDeCartesAlternees extends PTasDeCartes {

	private static final long serialVersionUID = 1L;
	
	//ICTasDeCartes c;
	
	public PTasDeCartesAlternees(CTasDeCartesAlternees c) {
		super(c);
	//	this.c = c;
		
		setBackground(Color.ORANGE);
		
	}
	
	// Empile les cartes centrées selon x et décalées suivant le nombre de composants sur y
//	public void empiler(PCarte pc){
//		pc.setLocation(getComponentCount()*dx, getComponentCount()*dy);
//	//	setSize(80, getComponentCount()*dy + 71);
//		add(pc, 0);
//		repaint(); // Nécessaire sinon mauvais placement des cartes
//	}
}
