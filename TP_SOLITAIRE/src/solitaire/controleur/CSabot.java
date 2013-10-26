package solitaire.controleur;

import solitaire.application.Sabot;
import solitaire.application.Tas;
import solitaire.presentation.PSabot;

public class CSabot extends Sabot {

	PSabot p;
	
	// gestion DnD
	CCarte cc;
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
		super.retournerCarte();
		if (isRetournable()){
			p.desactiverRetournerCarte();
			p.activerRetournerTas();
		}
	}
	
	public void depiler() throws Exception{
		super.depiler();
		if (isRetournable()){
			p.desactiverRetournerTas();
		}
	}
	
	// gestion Dnd
	public void p2c_debutDnd(CCarte cc){
		try {
			if (cc == getSommet()){
				depiler();
				p.c2p_debutDnDOK(cc);
				this.cc = cc;
			}
			else{
				p.c2p_debutDnDKO(cc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void p2c_dragDropEnd(boolean success){
		if(!success){
			empiler(cc);
		}
	}
	// fin gestion Dnd
}