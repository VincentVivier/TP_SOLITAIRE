package solitaire.controleur;

import solitaire.application.Carte;
import solitaire.application.Colonne;
import solitaire.application.Sabot;
import solitaire.application.TasDeCartes;
import solitaire.application.TasDeCartesAlternees;
import solitaire.application.TasDeCartesColorees;
import solitaire.application.Usine;

/**
   * La classe contrôle de l'usine des cartes.
   * ????????
   * 
   * @author Anthony Economides, Vincent Vivier
   */
public class CUsine extends Usine {

	
	
	@Override
	public Carte newCarte(int arg0, int arg1) {
		return new CCarte(arg0, arg1);
	}
	
	@Override
	public Colonne newColonne(String arg0, Usine arg1) {
		return new CColonne(arg0, this);
	}
	
	@Override
	public Sabot newSabot(String arg0, Usine arg1) {
		return new CSabot(arg0, this);
	}
	
	@Override
	public TasDeCartes newTasDeCartes(String arg0, Usine arg1) {
		return new CTasDeCartes(arg0, this);
	}
	
	@Override
	public TasDeCartesAlternees newTasDeCartesAlternees(String arg0, Usine arg1) {
		return new CTasDeCartesAlternees(arg0, this);
	}
	
	@Override
	public TasDeCartesColorees newTasDeCartesColorees(String arg0, int arg1,
			Usine arg2) {
		return new CTasDeCartesColorees(arg0, arg1, this);
	}
	
	
}
