package solitaire.controleur;

import java.awt.Component;

import solitaire.application.Sabot;
import solitaire.application.Tas;
import solitaire.presentation.PCarte;
import solitaire.presentation.PSabot;


public class CSabot extends Sabot {

	PSabot p;
	
	// gestion DnD source
	CTasDeCartes ct;
	//fin gestion DnD
	
    /**
     * Crée un sabot, avec un nom et contrôle d'usine comme paramètres.
     */
	public CSabot(String nom, CUsine u) {
		super(nom, u);
		// création de la présentation
		p = new PSabot(this, ((CTasDeCartes)cachees).getPresentation(), 
						((CTasDeCartes)visibles).getPresentation());
	}

    /**
     * Retourne l'instance de la présentation du sabot.
     */
	public PSabot getPresentation(){
		return p;
	}
	
    /**
     * Remise des cartes visibles en cachées dans le sabot.
     * @param t Le tas de carte à placer dans la réserve.
     */
	public void setReserve(Tas t){
		super.setReserve(t);
		// Activation du listener concernant le retournement de 3 cartes seulement si l'appli l'autorise.
		// Et désactivation du tas à retourner (en toute logique).
		if (isCarteRetournable()){
			p.activerRetournerCarte();
			p.desactiverRetournerTas();
		}
	}
	
    /**
     * Retourne toutes les cartes quand le joueur arrive à la fin de la liste du sabot.
     * @throws Exception
     * 		Si problème lors du retournement.
     */
	public void retourner() throws Exception{
		super.retourner();
		// Désactivation du listener si tas bien retourné (n'est plus retournable)
		if (!isRetournable()){
			p.desactiverRetournerTas();
		}
		// Activation de l'autre listener (rcl) s'il reste des cartes.
		if (isCarteRetournable()){
			p.activerRetournerCarte();
		}
	}
	
    /**
     * Retourne 3 cartes une par une en vérifiant que chaque carte est bien retournable.
     * @throws Exception [exception description]
     */
	public void retournerCarte() throws Exception{
		// On efface préalablement les visibles (les cartes restent présentes coté applicatif)
		p.effacerVisibles();
		// Retournement de 3 cartes avec vérif à chaque retournement
		for (int i = 0 ; i < 3 ; i++){ // retourner 3 cartes
			super.retournerCarte();
			if (isRetournable()){
				p.desactiverRetournerCarte();
				p.activerRetournerTas();
				break;
			}
		}
	}
	
    /**
     * Depile une carte des visibles du sabot.
     * @throws Exception
     * 		Si erreur (carte non dépilable)
     */
	public void depiler() throws Exception{
		super.depiler();
		if (!isRetournable()){
			p.desactiverRetournerTas();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du curseur
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Appelée lorque la souris est détectée sur les visibles.
	 * @param c
	 * 		Le composant pointé par la souris.
	 */
	public void c2p_sourisVisiblesDetectee(Component c){
		try {
			// On vérifie que le composant est bien une PCarte est que c'est le sommet (pas trouvé mieux)
			// avant de changer la forme du curseur.
			if (c instanceof PCarte && ((PCarte)c).getControle() == getSommet()){
				p.showCliquable();
			}
			else {
				p.showNonCliquable();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Appelée lorsque la souris est détectée sur les cachées.
	 * Le joueur peut cliquer seulement si une carte de la pioche est retournable.
	 */
	public void c2p_sourisCacheesDetectee(){
		if (isCarteRetournable()){
			p.showCliquable();
		}
		else {
			p.showNonCliquable();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du Drag and Drop : source
	//---------------------------------------------------------------------------------------------
        
        /**
        * Méthode appelée à la reconnaissance d'un nouveau DnD.
        * @param cc
        * 		Le contrôle de carte cliqué par la souris.
        */
	public void p2c_debutDnd(CCarte cc){
		try {
			if (cc == getSommet()){ // Seulement si la carte est le sommet
				depiler(); // On dépile coté appli
				
				//puis on crée le tas de cartes à déplacer (ici une seule carte à chaque fois)
				ct = new CTasDeCartes("carteSabot", null);
				ct.getPresentation().setDxDy(0, 0);
				ct.empiler(cc);
				
				// On demande à la présentation de démarrer le DnD avec le tas de carte en question.
				p.c2p_debutDnDOK(ct);
			}
			else{
				p.c2p_debutDnDKO(ct);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
    /**
     * Appelée à la fin d'un DnD ayant pour source ce conteneur.
     * @param success 
     * 		Définit le succès ou non de l'opération.
     */
	public void p2c_dragDropEnd(boolean success){
		//  On réempile le tas de cartes (ici une seule carte) si l'opération n'a pas aboutie.
		if(!success){
			try {
				empiler(ct);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//---------------------------------------------------------------------------------------------
	// Fin de gestion du Drag and Drop : source
	//---------------------------------------------------------------------------------------------
}
