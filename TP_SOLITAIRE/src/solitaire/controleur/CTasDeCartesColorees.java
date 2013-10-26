package solitaire.controleur;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import interfacesControle.ICTasDeCartes;
import solitaire.application.Carte;
import solitaire.application.TasDeCartesColorees;
import solitaire.presentation.PCarte;
import solitaire.presentation.PTasDeCartes;
import solitaire.presentation.PTasDeCartesColorees;

public class CTasDeCartesColorees extends TasDeCartesColorees implements ICTasDeCartes {
	
	PTasDeCartes p;

	public CTasDeCartesColorees(String nom, int couleur, CUsine u) {
		super(nom, couleur, u);
		p = new PTasDeCartesColorees(this);
	}
	
	public PTasDeCartes getPresentation(){
		return p;
	}
	
	public void depiler() throws Exception{
		Carte s = getSommet();
		super.depiler();
		p.depiler(((CCarte) s).getPresentation());
	}
	
	public void empiler(Carte c) {
		if (isEmpilable(c)){
			super.empiler(c);
			try {
				if (c == getSommet()){ 						  // vérification que l'application a bien fait son taf
					p.empiler(((CCarte) c).getPresentation());//avant de l'afficher
				}
			} catch (Exception e) {
				System.err.print("Exception relevée lors d'un empilage de " + c.toString());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void beginDND() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDND() {
		// TODO Auto-generated method stub
		
	}
	
	public void p2c_dragEnter(CCarte cc){
		if (isEmpilable(cc)){
			((PTasDeCartesColorees) p).c2p_showEmpilable();
		}
		else {
			((PTasDeCartesColorees) p).c2p_showNonEmpilable();
		}
	}
	
	public void p2c_dragExit(CCarte cc){
		((PTasDeCartesColorees) p).c2p_showNeutre();
	}
	
	public void p2c_drop(CCarte cc){
		if (isEmpilable(cc)){
			empiler(cc);
			((PTasDeCartesColorees) p).c2p_dropOK();
		}
		else {
			((PTasDeCartesColorees) p).c2p_dropKO();
		}
		((PTasDeCartesColorees) p).c2p_showNeutre();
	}
}
