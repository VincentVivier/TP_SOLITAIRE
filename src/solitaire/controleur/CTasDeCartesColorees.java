package solitaire.controleur;

import interfacesControle.ICTasDeCartes;
import solitaire.application.Carte;
import solitaire.application.TasDeCartesColorees;
import solitaire.presentation.PTasDeCartes;
import solitaire.presentation.PTasDeCartesColorees;

public class CTasDeCartesColorees extends TasDeCartesColorees implements ICTasDeCartes {
	
	PTasDeCartesColorees p;

	// gestion DnD source
	CTasDeCartes ct;
	//fin gestion DnD
	
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
				System.err.print("Exception relev�e lors d'un empilage de " + c.toString());
				e.printStackTrace();
			}
		}
	}
	
	// Gestion curseur
	
	public void p2c_sourisDetectee(){
		if (!isVide()){
			p.showCliquable();
		}
		else {
			p.showNonCliquable();
		}
	}
	
	// fin gestion curseur
	
	// gestion Dnd source
	
	public void p2c_debutDnd(CCarte cc){
		try {
			if (cc != null && cc == getSommet()){
				depiler();
				ct = new CTasDeCartes("carteSabot", null);
				ct.getPresentation().setDxDy(0, 0);
				ct.empiler(cc);
				p.c2p_debutDndOK(ct);
			}
			else{
				p.c2p_debutDnDKO(cc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void p2c_dragDropEnd(boolean success){
		System.out.println("Success drop end : " + success);
		if(!success){
			try {
				empiler(ct);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// Gestion DnD drop
	
	public void p2c_dragEnter(CTasDeCartes ctc){
		try {
			if (ctc.getNombre() == 1 && isEmpilable(ctc.getSommet())){ // isEmpilable(Tas t) renvoie toujours vrai...
				p.c2p_showEmpilable();
			}
			else {
				p.c2p_showNonEmpilable();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void p2c_dragExit(CTasDeCartes ctc){
		p.c2p_showNeutre();
	}
	
	public void p2c_drop(CTasDeCartes ctc){
		try {
			if (ctc.getNombre() == 1 && isEmpilable(ctc.getSommet())){// isEmpilable(Tas t) ne fonctionne pas :/
				empiler(ctc); // En revanche, le empiler(Tas t) fonctionne...
				p.c2p_dropOK();
			}
			else {
				p.c2p_dropKO();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.c2p_showNeutre();
	}
}
