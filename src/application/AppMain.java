package application;

import solitaire.application.Solitaire;
import solitaire.controleur.CSolitaire;
import solitaire.controleur.CUsine;

/**
   * La classe mère de l'application.
   * Démarre le contrôle de l'usine des cartes, affiche la JFrame de solitaire
   * et démarre la logique du jeux.
   * @author Anthony Economides, Vincent Vivier
   */
public class AppMain {
        /**
        * Méthode main
        * @author Anthony Economides, Vincent Vivier
        * @param String[] args
	*/
	public static void main(String[] args) {
		
		CUsine u = new CUsine();
		Solitaire appli = new CSolitaire("GLI - Solitaire", u);
		appli.jouer();
	}
}
