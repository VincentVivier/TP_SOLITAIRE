package solitaire.presentation;

import solitaire.controleur.CTasDeCartesAlternees;

public class PTasDeCartesAlternees extends PTasDeCartes {

	private static final long serialVersionUID = 1L;
	
	
	public PTasDeCartesAlternees(CTasDeCartesAlternees c) {
		super(c);	
	}
	
	// Aucune méthode à définir dans cette classe de présentation car celle-ci représente un
	// tas de cartes.
	// Elle hérite donc de la classe PTasDeCarte qui, elle, implémente les méthodes nécessaires. 
}
