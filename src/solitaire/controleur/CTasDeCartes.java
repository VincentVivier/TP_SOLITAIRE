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
	
	public PTasDeCartes getPresentation(){
		return p;
	}
	
	/**
	 * Dépile une carte du tas de carte coté application (si possible) et présentation.
	 * Une excetion sera remontée avant la mise à jour coté présentation si la carte n'est pas dépilable.
	 */
	public void depiler() throws Exception{
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}

	/**
	 * Empile une carte dans le tas de cartes (coté appli et présentation) si la carte est empilable.
	 */
	public void empiler(Carte c) {
		if (isEmpilable(c)){
			super.empiler(c);
			try {
				if (c == getSommet()){ // vérification que l'application a bien fait son travail
					p.empiler(((CCarte) c).getPresentation());//avant de l'afficher
				}
			} catch (Exception e) {
				System.err.print("Exception relevée lors d'un empilement de " + c.toString());
				e.printStackTrace();
			}
		}
	}
}
