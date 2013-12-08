package solitaire.controleur;

import solitaire.application.Colonne;
import solitaire.application.Tas;
import solitaire.presentation.PColonne;

/**
   * La classe contrôleur d'une colonne.
   * Responsable pour la logique de création d'une colonne, la gestion des drag 
   * et drop des cartes.
   * entre eux, dépilement des tas et setReserve?
   * @author Anthony Economides, Vincent Vivier
   */
public class CColonne extends Colonne {

	PColonne p;
	
	// gestion DnD
	CTasDeCartes ctc;
	//fin gestion DnD
	
        /**
        * Constructeur. Crée une colonne, avec un nom et contrôle d'usine
        * comme paramètres. Commande la présentation à afficher cette colonne,
        * et a retourner la dernière carte. 
        * @author Anthony Economides, Vincent Vivier
        * @param nom Le nom de la colonne.
        * @param u Le contrôle d'usine de cartes utilisé.
        */
	public CColonne(String nom, CUsine u) {
		super(nom, u);                                                  //creer colonne en utilisant la classe Colonne
		p = new PColonne(this, ((CTasDeCartes)cachees).getPresentation(),
						((CTasDeCartesAlternees)visibles).getPresentation());       //création de presentation de colonne
		p.activerRetournerCarte();
	}
	
        /**
        * Retourne la présentation de la colonne pour lui passer des commandes.
        * @author Anthony Economides, Vincent Vivier
        * @return La instance de classe présentation de la colonne.
        */
	public PColonne getPresentation(){
		return p;
	}
        
	/**
        * ?????????
        * @author Anthony Economides, Vincent Vivier
        * @param t Le tas de carte à ????????????setReserve.
        */
	public void setReserve(Tas t){
		super.setReserve(t);
		if (isCarteRetournable()){
			p.activerRetournerCarte();
		}
	}
	
        /**
        * pas sure ??????????????? Commande la presentation à retournér la derniere carte.
        * @author Anthony Economides, Vincent Vivier
        * @throws Exception [exception description]
        */
	public void retournerCarte() throws Exception{
		super.retournerCarte();
		if (!isCarteRetournable()){
			p.desactiverRetournerCarte();
		}
	}
	
        /**
        * pas sure ??????????????? Serve à depiler le tas.
        * @author Anthony Economides, Vincent Vivier
        * @throws Exception [exception description]
        */
	public void depiler() throws Exception{
		super.depiler();
		if (isCarteRetournable()){
			p.activerRetournerCarte();
		}
	}
	
	// gestion Dnd source
        
        /**
        * Gestion (à partir de la présentation au contrôle) du démarrage du Drag et Drop 
        * Plus d'info ????????????????????
        * @author Anthony Economides, Vincent Vivier
        * @param cc Le contrôle de Carte que ...
        */
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
	
        /**
        * Gestion (à partir de la présentation au contrôle) de la fin du Drag et Drop
        * Plus d'info ????????????????????
        * @author Anthony Economides, Vincent Vivier
        * @param success Un boolean qui défini le success du Drag et Drop. true=success
        */
	public void p2c_dragDropEnd(boolean success){
		System.out.println("Success drop end : " + success);
		if(!success){
			empiler(ctc);
		}
	}
	
        
	// Gestion DnD drop
	
        /**
        * Gestion (à partir de la présentation au contrôle) de l'entre du cursor à la colonne.
        * Commande la présentation à afficher une astuce au joueur, du fait que la carte peut 
        * être ajouter à cette colonne ou non.  
        * @author Anthony Economides, Vincent Vivier
        * @param ctc [argument description]
        */
	public void p2c_dragEnter(CTasDeCartes ctc){
		if (isEmpilable(ctc)){
			p.c2p_showEmpilable();
		}
		else {
			p.c2p_showNonEmpilable();
		}
	}
	
        /**
        * Gestion (à partir de la présentation au contrôle) de la sortie du cursor de la colonne.
        * Commande la présentation à plus afficher l'astuce. 
        * @author Anthony Economides, Vincent Vivier
        * @param ctc [argument description]
        */
	public void p2c_dragExit(CTasDeCartes ctc){
		p.c2p_showNeutre();
	}
	
        /**
        * Gestion (à partir de la présentation au contrôle) du drop d'une carte sur la colonne.
        * Si la carte est empilable au tas, il l'empile et commande la présentation a afficher
        * le nouveaux tas/colonne. Sinon c2p_dropKO()???????????
        * c2p_showNeutre()?????????????
        * @author Anthony Economides, Vincent Vivier
        * @param ctc [argument description]
        */
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
