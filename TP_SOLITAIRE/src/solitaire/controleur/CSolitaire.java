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
		
		// Le solitaire initialise tous les composants afin de pouvoir les r�cup�rer
		this.initialiser();
		
		// R�cup�ration des pr�sentations de tas de cartes color�es
		PTasDeCartesColorees[] ptcc = new PTasDeCartesColorees[pilesColorees.length];
		for (int i = 0 ; i < pilesColorees.length ; i++){
			ptcc[i] = (PTasDeCartesColorees)((CTasDeCartesColorees)pilesColorees[i]).getPresentation();
		}
		// R�cup�ration des pr�sentations des colonnes
		PColonne[] ptca = new PColonne[pilesAlternees.length];
		for (int i = 0 ; i < pilesAlternees.length ; i++){
			ptca[i] = (PColonne)((CColonne)pilesAlternees[i]).getPresentation();
		}
		
		// Cr�ation de la fenetre avec tous les composants n�cessaires
		p = new PSolitaire(this, ((CSabot)sabot).getPresentation(), ptcc, ptca);
		
		
	}
	
	public PSolitaire getPresentation(){
		return p;
	}

}
