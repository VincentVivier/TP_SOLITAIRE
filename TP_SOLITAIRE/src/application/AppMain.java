package application;

import solitaire.controleur.CSolitaire;
import solitaire.controleur.CUsine;

public class AppMain {

	public static void main(String[] args) {
		CUsine u = new CUsine();
		new CSolitaire("GLI - Solitaire - Vince", u);
	}
}
