package solitaire.controleur;

import solitaire.application.Carte;
import solitaire.presentation.PCarte;

public class CCarte extends Carte {

	private PCarte p;
	
	public CCarte(int v, int c) {
		super(Math.min(Math.max(1, v), 13), Math.min(Math.max(1, c), 4));
		
		p= new PCarte(String.valueOf(valeurs[getValeur()-1])
						   + String.valueOf(couleurs[getCouleur()-1]), this);
		p.setFaceVisible(isFaceVisible());
	}
	
	public void setFaceVisible(boolean v){
		super.setFaceVisible(v);
		p.setFaceVisible(isFaceVisible());
	}
	
	public PCarte getPresentation(){
		return p;
	}
}
