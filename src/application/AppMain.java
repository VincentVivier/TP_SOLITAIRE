package application;

import solitaire.application.Solitaire;
import solitaire.controleur.CSolitaire;
import solitaire.controleur.CUsine;

/**
   * lasse mère de l'application.
   * Démarre le contrôle de l'usine des cartes ainsi que celui du solitaire
   * et démarre la logique du jeux.
   */
public class AppMain {

	public static void main(String[] args) {
		
		CUsine u = new CUsine();
		Solitaire appli = new CSolitaire("GLI - Solitaire", u);
		appli.jouer();
	}
}
