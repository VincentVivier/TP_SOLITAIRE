package solitaire.controleur;

import interfacesControle.ICTasDeCartes;
import solitaire.application.Carte;
import solitaire.application.TasDeCartesColorees;
import solitaire.presentation.PTasDeCartes;
import solitaire.presentation.PTasDeCartesColorees;

public class CTasDeCartesColorees extends TasDeCartesColorees implements ICTasDeCartes {
	
	PTasDeCartes p;

	public CTasDeCartesColorees(String nom, int couleur, CUsine u) {
		super(nom, couleur, u);
		p = new PTasDeCartesColorees(this);
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
		if (isEmpilable(c)){
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
	
	public void p2c_dragEnter(CTasDeCartes ctc){
		if (isEmpilable(ctc)){
			((PTasDeCartesColorees) p).c2p_showEmpilable();
		}
		else {
			((PTasDeCartesColorees) p).c2p_showNonEmpilable();
		}
	}
	
	public void p2c_dragExit(CTasDeCartes ctc){
		((PTasDeCartesColorees) p).c2p_showNeutre();
	}
	
	public void p2c_drop(CTasDeCartes ctc){
		try {
			if (ctc.getNombre() == 1 && isEmpilable(ctc.getSommet())){// isEmpilable(Tas t) ne fonctionne pas :/
				empiler(ctc); // En revanche, le empiler(Tas t) fonctionne...
				((PTasDeCartesColorees) p).c2p_dropOK();
			}
			else {
				((PTasDeCartesColorees) p).c2p_dropKO();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((PTasDeCartesColorees) p).c2p_showNeutre();
	}
}
