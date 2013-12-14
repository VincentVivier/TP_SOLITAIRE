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
	
	/**
	 * Même méthode que le CTasDeCartes.
	 */
	public void depiler() throws Exception{
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}
	
	/**
	 * Même méthode que le CTasDeCartes.
	 */
	public void empiler(Carte c) {
		if (isEmpilable(c)){
			super.empiler(c);
			try {
				if (c == getSommet()){  // vérification que l'application a bien fait son travail
					p.empiler(((CCarte) c).getPresentation());//avant de l'afficher
				}
			} catch (Exception e) {
				System.err.print("Exception relevée lors d'un empilage de " + c.toString());
				e.printStackTrace();
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du curseur
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Méthode appelée lorsque la souris est détectée sur le tas coté présentation.
	 * Le controle demande le changement du curseur seulement si le tas possède une carte.
	 */
	public void p2c_sourisDetectee(){
		if (!isVide()){
			p.showCliquable();
		}
		else {
			p.showNonCliquable();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du Drag an Drop : source
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Méthode ayant le même principe que le début DnD à partir du sabot.
	 */
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
	
	/**
	 * Même principe que le DnD  à partir du sabot.
	 */
	public void p2c_dragDropEnd(boolean success){
		if(!success){
			try {
				empiler(ct);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du Drag and Drop : drop
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Méthode appelée par la présentation lorsqu'elle détecte un potentiel drop.
	 */
	public void p2c_dragEnter(CTasDeCartes ctc){
		try {
			// isEmpilable(Tas t) renvoie toujours vrai, on utilise donc un autre moyen de test :
			// Si le tas en question n'est constitué que d'une carte et qu'elle est empilable, tout est OK.
			if (ctc.getNombre() == 1 && isEmpilable(ctc.getSommet())){ 
				p.c2p_showEmpilable();
			}
			else {
				p.c2p_showNonEmpilable();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void p2c_dragExit(CTasDeCartes ctc){
		p.c2p_showNeutre();
	}
	
	public void p2c_drop(CTasDeCartes ctc){
		try {
			// isEmpilable(Tas t) renvoit toujours vrai
			// Même test que la méthode 'p2c_dragEnter()'.
			if (ctc.getNombre() == 1 && isEmpilable(ctc.getSommet())){
				empiler(ctc); // En revanche, le empiler(Tas t) fonctionne...
				p.c2p_dropOK();
			}
			else {
				p.c2p_dropKO();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		p.c2p_showNeutre();
	}
}
