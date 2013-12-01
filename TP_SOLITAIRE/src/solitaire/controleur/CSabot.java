package solitaire.controleur;

import solitaire.application.Sabot;
import solitaire.application.Tas;
import solitaire.presentation.PSabot;

public class CSabot extends Sabot {

	PSabot p;
	
	// gestion DnD
	CTasDeCartes ct;
	//fin gestion DnD
	
	public CSabot(String nom, CUsine u) {
		super(nom, u);
		p = new PSabot(this, ((CTasDeCartes)cachees).getPresentation(), 
						((CTasDeCartes)visibles).getPresentation());
	}

	public PSabot getPresentation(){
		return p;
	}
	
	public void setReserve(Tas t){
		super.setReserve(t);
		if (isCarteRetournable()){
			p.activerRetournerCarte();
			p.desactiverRetournerTas();
		}
		System.out.println("SET RESERVE !!!");
	}
	
	public void retourner() throws Exception{
		super.retourner();
		if (!isRetournable()){
			p.desactiverRetournerTas();
		}
		if (isCarteRetournable()){
			p.activerRetournerCarte();
		}
	}
	
	public void retournerCarte() throws Exception{
		p.effacerVisibles();
		for (int i = 0 ; i < 3 ; i++){ // retourner 3 cartes
			super.retournerCarte();
			if (isRetournable()){
				System.out.println("cest retournable !");
				p.desactiverRetournerCarte();
				p.activerRetournerTas();
				break;
			}
			System.out.println("RETOURNER " + (i+1) + " CARTES !!!");
		}
	}
	
	public void depiler() throws Exception{
		super.depiler();
		if (isRetournable()){
			p.desactiverRetournerTas();
		}
		System.out.println("DEPILER !!!");
	}
	
	// gestion Dnd source
	public void p2c_debutDnd(CCarte cc){
		try {
			if (cc == getSommet()){
				depiler();
				ct = new CTasDeCartes("carteSabot", null);
				ct.getPresentation().setDxDy(0, 0);
				ct.empiler(cc);
				p.c2p_debutDnDOK(ct);
			}
			else{
				p.c2p_debutDnDKO(ct);
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
	// fin gestion Dnd
}
