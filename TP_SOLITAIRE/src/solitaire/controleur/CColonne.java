package solitaire.controleur;

import solitaire.application.Colonne;
import solitaire.presentation.PColonne;

public class CColonne extends Colonne {

	PColonne p;
	
	public CColonne(String nom, CUsine u) {
		super(nom, u);
		p = new PColonne(this, ((CTasDeCartes)cachees).getPresentation(),
						((CTasDeCartesAlternees)visibles).getPresentation());
	}
	
	public PColonne getPresentation(){
		return p;
	}
	
	public void retournerCarte() throws Exception{
		super.retournerCarte();
		if (isCarteRetournable()){
			p.desactiverRetournerCarte();
		}
	}
	

}
