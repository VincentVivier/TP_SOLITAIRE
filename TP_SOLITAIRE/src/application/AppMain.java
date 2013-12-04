package application;

import solitaire.application.Solitaire;
import solitaire.controleur.CSolitaire;
import solitaire.controleur.CUsine;

public class AppMain {

	public static void main(String[] args) {
		
		CUsine u = new CUsine();
		Solitaire appli = new CSolitaire("GLI - Solitaire", u);
		appli.jouer();
	}
}
