package solitaire.controleur;

import java.awt.Component;

import solitaire.application.Sabot;
import solitaire.application.Tas;
import solitaire.presentation.PCarte;
import solitaire.presentation.PSabot;

/**
   * La classe contrôleur du Sabot.
   * Responsable pour la logique de création du sabot, la gestion des drag 
   * et drop des cartes, et de la sélection de 3 différentes cartes
   * chaque foi que le joueur clique sur le sabot.
   * @author Anthony Economides, Vincent Vivier
   */
public class CSabot extends Sabot {

	PSabot p;
	
	// gestion DnD source
	CTasDeCartes ct;
	//fin gestion DnD
	
        /**
        * Constructeur. Crée un sabot, avec un nom et contrôle d'usine
        * comme paramètres. Commande la présentation à afficher ce sabot. 
        * @author Anthony Economides, Vincent Vivier
        * @param nom Le nom du sabot.
        * @param u Le contrôle d'usine de cartes utilisé.
        */
	public CSabot(String nom, CUsine u) {
		super(nom, u);
		p = new PSabot(this, ((CTasDeCartes)cachees).getPresentation(), 
						((CTasDeCartes)visibles).getPresentation());
	}

        /**
        * Retourne la présentation du sabot pour lui passer des commandes.
        * @author Anthony Economides, Vincent Vivier
        * @return La instance de classe présentation du sabot.
        */
	public PSabot getPresentation(){
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
			p.desactiverRetournerTas();
		}
	}
	
        /**
        * Retourne toutes les cartes quand le joueur arrive à la fin de la liste du sabot.
        * Commande la présentation a afficher une carte retourné(default status).
        * @author Anthony Economides, Vincent Vivier
        * @throws Exception [exception description]
        */
	public void retourner() throws Exception{
		super.retourner();
		if (!isRetournable()){
			p.desactiverRetournerTas();
		}
		if (isCarteRetournable()){
			p.activerRetournerCarte();
		}
	}
	
        /**
        * ???????????.
        * @author Anthony Economides, Vincent Vivier
        * @throws Exception [exception description]
        */
	public void retournerCarte() throws Exception{
		p.effacerVisibles();
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
        * Depile une carte du sabot.
        * plus d'info?????????
        * @author Anthony Economides, Vincent Vivier
        * @throws Exception [exception description]
        */
	public void depiler() throws Exception{
		super.depiler();
		if (isRetournable()){
			p.desactiverRetournerTas();
		}
	}
	
	// Gestion curseur
	
	public void c2p_sourisVisiblesDetectee(Component c){
		try {
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
	
	public void c2p_sourisCacheesDetectee(){
		if (isCarteRetournable()){
			p.showCliquable();
		}
		else {
			p.showNonCliquable();
		}
	}
	
	// fin gestion curseur
	
	// gestion Dnd source
        
        /**
        * Gestion (à partir de la présentation au contrôle) du démarrage du Drag et Drop.
        * Depile une carte du sabot, si elle est au dessus des autres 2. Genere un nouveaux tas et lui empile la carte.
        * Commande la presentation de afficher le tout.
        * @author Anthony Economides, Vincent Vivier
        * @param cc Le contrôle de Carte que on va ...
        */
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
	
        /**
        * Gestion (à partir de la présentation au contrôle) de la fin du Drag et Drop.
        * Si un drag et drop est sans success empile la carte au tas du sabot d'ou elle était dépilé.
        * @author Anthony Economides, Vincent Vivier
        * @@param success Un boolean qui défini le success du Drag et Drop. true=success
        */
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
