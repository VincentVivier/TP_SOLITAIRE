package solitaire.controleur;

import solitaire.application.Carte;
import solitaire.presentation.PCarte;

/**
   * Contrôleur d'une carte.
   * Responsable pour la logique de création de la présentation, et le
   * changement des sa face.
   */
public class CCarte extends Carte {

	private PCarte p;
	
        /**
        * Constructeur. Crée la présentation d'une carte, suivant sa valeur et sa couleur.
        * @param v La valeur de la carte (1-10 pour l'Ace et les numéros et 11-13 pour les Vallet, Reine, Roi)
        * @param c La couleur de la carte. (1 pour carreau, 2 pour pique, 3 pour cœur et 4 pour trèfle)
        */
	public CCarte(int v, int c) {
		super(Math.min(Math.max(1, v), 13), Math.min(Math.max(1, c), 4));   //le 13 représentant le nombre de carte par couleur et le 4 le nombre de couleurs
		
		//création de presentation de carte
		p= new PCarte(String.valueOf(valeurs[getValeur()-1])
						   + String.valueOf(couleurs[getCouleur()-1]), this);
		p.setFaceVisible(isFaceVisible());  
	}
	
        /**
        * Change la face de la carte du coté applicatif et présentation.
        * @param v 
        * 		true => face visible, false => face cachée.
        */
	public void setFaceVisible(boolean v){
		super.setFaceVisible(v);
		p.setFaceVisible(isFaceVisible());
	}
	
        /**
        * Retourne l'instance de présentation de la carte.
        * @return
        * 		L'instance de la présentation (PCarte) correspondante.
        */
	public PCarte getPresentation(){
		return p;
	}
}
