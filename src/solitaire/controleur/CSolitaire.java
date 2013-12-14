package solitaire.controleur;

import solitaire.application.Solitaire;
import solitaire.application.Usine;
import solitaire.presentation.PColonne;
import solitaire.presentation.PSolitaire;
import solitaire.presentation.PTasDeCartesColorees;

public class CSolitaire extends Solitaire {
	
	PSolitaire p;

	public CSolitaire(String nom, Usine u) {
		super(nom, u);
		
		// Le solitaire initialise tous les composants afin de pouvoir les récupérer
		this.initialiser();
		
		// Récupération des présentations de tas de cartes colorées
		PTasDeCartesColorees[] ptcc = new PTasDeCartesColorees[pilesColorees.length];
		for (int i = 0 ; i < pilesColorees.length ; i++){
			ptcc[i] = (PTasDeCartesColorees)((CTasDeCartesColorees)pilesColorees[i]).getPresentation();
		}
		
		// Récupération des présentations des colonnes
		PColonne[] ptca = new PColonne[pilesAlternees.length];
		for (int i = 0 ; i < pilesAlternees.length ; i++){
			ptca[i] = (PColonne)((CColonne)pilesAlternees[i]).getPresentation();
			ptca[i].setAffichage();
		}
		
		// Création de la présentation (JFrame) avec tous les composants nécessaires
		p = new PSolitaire(this, ((CSabot)sabot).getPresentation(), ptcc, ptca);
		
	}
	
	/**
	 * Getteur sur la présentation du solitaire.
	 * @return
	 * 		La présentation du jeu.
	 */
	public PSolitaire getPresentation(){
		return p;
	}

}
