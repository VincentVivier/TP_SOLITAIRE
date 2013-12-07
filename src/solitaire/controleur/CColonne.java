package solitaire.controleur;

import solitaire.application.Colonne;
import solitaire.application.Tas;
import solitaire.presentation.PColonne;

public class CColonne extends Colonne {

	PColonne p;
	
	// gestion DnD
	CTasDeCartes ctc;
	//fin gestion DnD
	
	public CColonne(String nom, CUsine u) {
		super(nom, u);
		p = new PColonne(this, ((CTasDeCartes)cachees).getPresentation(),
						((CTasDeCartesAlternees)visibles).getPresentation());
		p.activerRetournerCarte();
	}
	
	public PColonne getPresentation(){
		return p;
	}
	
	public void setReserve(Tas t){
		super.setReserve(t);
		if (isCarteRetournable()){
			p.activerRetournerCarte();
		}
	}
	
	public void retournerCarte() throws Exception{
		super.retournerCarte();
		if (!isCarteRetournable()){
			p.desactiverRetournerCarte();
		}
	}
	
	public void depiler() throws Exception{
		super.depiler();
		if (isCarteRetournable()){
			p.activerRetournerCarte();
		}
	}
	
	// gestion Dnd source
	public void p2c_debutDnd(CCarte cc){
		try {
			if (cc != null){
				for (int i = 1 ; i <= getNombre() ; i++){
					if (cc == getCarte(i)){ // Chercher la carte sélectionnée
						
						// Création du Tas de cartes à déplacer
						ctc = new CTasDeCartes("deplacees", null);
						ctc.getPresentation().setDxDy(0, 25);
						
						// Ajout des cartes dans le tas à déplacer
						for (int j = i ; j != 0 ; j--){
							ctc.empiler(getCarte(j));
						}
						// On dépile au niveau application seulement après, sinon conflit.
						for (int j = i ; j != 0 ; j--){
							depiler();
						}
						System.out.println("Nombre de cartes dans panel : " + ctc.getPresentation().getComponentCount());
						p.c2p_debutDndOK(ctc);
						break;
					}
				}
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
			empiler(ctc);
		}
	}
	
	// Gestion DnD drop
	
	public void p2c_dragEnter(CTasDeCartes ctc){
		if (isEmpilable(ctc)){
			p.c2p_showEmpilable();
		}
		else {
			p.c2p_showNonEmpilable();
		}
	}
	
	public void p2c_dragExit(CTasDeCartes ctc){
		p.c2p_showNeutre();
	}
	
	public void p2c_drop(CTasDeCartes ctc){
		if (isEmpilable(ctc)){
			empiler(ctc);
			p.c2p_dropOK();
		}
		else {
			p.c2p_dropKO();
		}
		p.c2p_showNeutre();
	}
	// fin gestion Dnd
}
