package solitaire.controleur;

import solitaire.application.Solitaire;
import solitaire.application.Usine;
import solitaire.presentation.PColonne;
import solitaire.presentation.PSolitaire;
import solitaire.presentation.PTasDeCartesColorees;

/**
   * La classe contrôleur mére de la application.
   * @author Anthony Economides, Vincent Vivier
   */
public class CSolitaire extends Solitaire {
	
	PSolitaire p;

        /**
        * Constructeur. Crée un contrôleur de solitaire, avec un nom et contrôle d'usine
        * comme paramètres. 
        * @author Anthony Economides, Vincent Vivier
        * @param nom Le nom du ontrôleur.
        * @param u Le contrôle d'usine de cartes utilisé.
        */
	public CSolitaire(String nom, Usine u) {
		super(nom, u);
		
		// Le solitaire initialise tous les composants afin de pouvoir les r�cup�rer
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
		
		// Création de la fenetre avec tous les composants nécessaires
		p = new PSolitaire(this, ((CSabot)sabot).getPresentation(), ptcc, ptca);
		
		
	}
	
        /**
        * Retourne la présentation du Solitaire pour lui passer des commandes.
        * @author Anthony Economides, Vincent Vivier
        * @return La instance de classe présentation du solitaire(jframe).
        */
	public PSolitaire getPresentation(){
		return p;
	}

}
