package solitaire.controleur;

import interfacesControle.ICTasDeCartes;
import solitaire.application.Carte;
import solitaire.application.TasDeCartes;
import solitaire.presentation.PTasDeCartes;

public class CTasDeCartes extends TasDeCartes implements ICTasDeCartes {

	PTasDeCartes p;
	
	public CTasDeCartes(String nom, CUsine u) {
		super(nom, u);
		p = new PTasDeCartes(this);
	}
	
	public void depiler() throws Exception{
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}

	public void empiler(Carte c) {
		if (isEmpilable(c)){
			super.empiler(c);
			try {
				if (c == getSommet()){ 						  // vérification que l'application a bien fait son taf
					p.empiler(((CCarte) c).getPresentation());//avant de l'afficher
				}
			} catch (Exception e) {
				System.err.print("Exception relevée lors d'un empilement de " + c.toString());
				e.printStackTrace();
			}
		}
	}
	
	public PTasDeCartes getPresentation(){
		return p;
	}

	
}
