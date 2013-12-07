package solitaire.controleur;

import interfacesControle.ICTasDeCartes;
import solitaire.application.Carte;
import solitaire.application.TasDeCartesAlternees;
import solitaire.presentation.PTasDeCartes;
import solitaire.presentation.PTasDeCartesAlternees;

public class CTasDeCartesAlternees extends TasDeCartesAlternees implements ICTasDeCartes {

	PTasDeCartes p;
	
	public CTasDeCartesAlternees(String nom, CUsine u) {
		super(nom, u);
		p = new PTasDeCartesAlternees(this);
	}
	
	public PTasDeCartes getPresentation(){
		return p;
	}
	
	public void depiler() throws Exception{
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}
	
	public void empiler(Carte c) {
		super.empiler(c);
		try {
			if (c == getSommet()){ 						  // vérification que l'application a bien fait son taf
				p.empiler(((CCarte) c).getPresentation());//avant de l'afficher
			}
		} catch (Exception e) {
			System.err.print("Exception relevée lors d'un empilage de " + c.toString());
			e.printStackTrace();
		}
	}
}
