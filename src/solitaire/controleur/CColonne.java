package solitaire.controleur;

import java.awt.Component;

import solitaire.application.Colonne;
import solitaire.application.Tas;
import solitaire.presentation.PCarte;
import solitaire.presentation.PColonne;

/**
   * La classe contrôleur d'une colonne.
   * Responsable de la logique du comportement d'une colonne.
   * Implémente les fonctionnalitées suivantes :
   * 	- Retourner une carte cachée si possible
   * 	- Drag and Drop sur les visibles
   * 	- Changement du curseur suivant la possibilité de cliquer
   */
public class CColonne extends Colonne {

	PColonne p;
	
	// gestion DnD
	CTasDeCartes ctc;
	// fin gestion DnD
	
	/**
        * Crée une colonne, avec un nom et contrôle d'usine comme paramètres. 
        * @param nom
        * 		Le nom de la colonne.
        * @param u 
        * 		Le contrôle d'usine de cartes utilisé.
        */
	public CColonne(String nom, CUsine u) {
		super(nom, u);
		
		// Création de la présentation de la colonne
		p = new PColonne(this, ((CTasDeCartes)cachees).getPresentation(),
						((CTasDeCartesAlternees)visibles).getPresentation());
	}
	
	/**
     * @return
     * 		L'instance de présentation d'une colonne.
     */
	public PColonne getPresentation(){
		return p;
	}
	
	/**
     * Effectue le remplissage de la réserve coté applicatif puis
     * active le listener de la présentation si une carte cachée est retournable.
     */
	public void setReserve(Tas t){
		super.setReserve(t);
		if (isCarteRetournable()){
			p.activerRetournerCarte();
		}
	}
	
	/**
        * Retourne une carte cachée du côté applicatif puis désactive le listener
        * de la présentation si la carte cachée suivante n'estpas retournable.
        * @throws Exception
        * 		Si la carte n'est pas retournable
        */
	public void retournerCarte() throws Exception{
		super.retournerCarte();
		if (!isCarteRetournable()){
			p.desactiverRetournerCarte();
		}
	}
	
	/**
        * Dépile une carte visible et active le listener de la présentation
        * seulement si l'application l'autorise (nbr cachée > 0 && nbre visibles = 0)
        * @throws Exception
        * 		Si la carte n'est pas retournable
        */
	public void depiler() throws Exception{
		super.depiler();
		if (isCarteRetournable()){
			p.activerRetournerCarte();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du curseur
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Méthode appelée lorsque le listener de la présentation a détecté un mouvement.
	 * Le listener est seulement présent sur les tas de cartes visibles.
	 * @param c
	 * 		Le composant pointé par la souris en mouvement
	 */
	public void c2p_sourisDetectee(Component c){
		try {
			// Le curseur MAIN est activé seulement si le composant est une PCarte (pas trouvé moins moche)
			if (c instanceof PCarte){
				p.showCliquable(); 
			}
			// ou si une carte cachée est retournable (aucun listener sur les cachées)
			else if (isCarteRetournable()) { 
				p.showCliquable();
			}
			// Sinon rien est cliquable
			else {
				p.showNonCliquable();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du Drag and Drop : source
	//---------------------------------------------------------------------------------------------
	
	/**
        * Applelée lors du démarrage détecté d'un drag and drop sur les visibles.
        * @param cc
        * 		Le contrôle de carte associé au DnD.
        */
	public void p2c_debutDnd(CCarte cc){
		try {
			if (cc != null){
				for (int i = 1 ; i <= getNombre() ; i++){
					// On cherche premièrement la carte sélectionnée avec un 'for' (pas trouvé mieux)
					if (cc == getCarte(i)){
						
						// Si carte trouvée : Création du Tas de cartes à déplacer
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
						// Puis on indique à la présentation que tout est OK en lui passant le tas à déplacer.
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
	
	/**
      * Appelée lorsqu'un DnD provenant d'une colonne est terminé.
      * @param success Un boolean qui défini le success du Drag et Drop. true=success
      */
	public void p2c_dragDropEnd(boolean success){
		// Si le DnD n'a pas abouti, on réempile le tas de cartes créé à l'amorçage de celui-ci.
		if(!success){
			empiler(ctc);
		}
	}
	
	//---------------------------------------------------------------------------------------------
	// Gestion du Drag and Drop : drop
	//---------------------------------------------------------------------------------------------
	
	/**
      * Méthode appelée lorsque la présentation détecte l'arrivée d'un tas de cartes.
      * Elle a pour principal but, l'affichage d'information pour le joueur.
      * @param ctc
      * 		Le tas de cartes du Drag and Drop concerné.
      */
	public void p2c_dragEnter(CTasDeCartes ctc){
		// Montrer que le tas de cartes est empilable si l'application le confirme.
		if (isEmpilable(ctc)){
			p.c2p_showEmpilable();
		}
		else {
			p.c2p_showNonEmpilable();
		}
	}
	
	/**
      * Appelée lorsque le composant DnD quite la zone de drop : Affichage neutre
      * L'argument ici n'a pas grand intérêt.
      */
	public void p2c_dragExit(CTasDeCartes ctc){
		p.c2p_showNeutre();
	}
	
	 /**
        * Appelée lorsque le clic est relaché.
        * Empile le tas après vérification que le tas est empilable
        * et termine le DnD du coté présentation.
        * @param ctc
        * 		Tas de cartes déplacé
        */
	public void p2c_drop(CTasDeCartes ctc){
		if (isEmpilable(ctc)){
			empiler(ctc);
			p.c2p_dropOK();
		}
		else {
			p.c2p_dropKO();
		}
		// Remise au neutre du background du conteneur quoi qu'il advienne
		p.c2p_showNeutre(); 
	}
	//---------------------------------------------------------------------------------------------
	// Fin de gestion du Drag and Drop
	//---------------------------------------------------------------------------------------------
}
