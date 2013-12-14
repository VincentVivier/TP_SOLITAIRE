package solitaire.presentation;

import interfacesControle.ICTasDeCartes;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JPanel;

import solitaire.controleur.CTasDeCartes;

/**
 * Un tas de cartes implémente Transferable car tous les drag an drop réalisés dans l'application
 * sont des tas de cartes.
 */
public class PTasDeCartes extends JPanel implements Transferable {

	private static final long serialVersionUID = 1L;
	
	ICTasDeCartes c;
	protected int dx, dy;
	
	public PTasDeCartes(ICTasDeCartes c) {
		this.c = c;
		// Tous les tas de cartes ont un layout null pour définir dynamiquement le placement des cartes.
		setLayout(null);
	}
	
	public CTasDeCartes getControle(){
		return (CTasDeCartes)c;
	}
	
	/**
	 * Supprime la carte souhaitée et redessine le résultat.
	 * @param pc
	 * 		Présentaion de carte à supprimer.
	 */
	public void depiler(PCarte pc){
		remove(pc);
		repaint();
	}
	
	/**
	 * Empile une carte avec un décalage en fonction du nombre de composants déjà présents.
	 * @param pc
	 */
	public void empiler(PCarte pc){
		pc.setLocation(getComponentCount()*dx, getComponentCount()*dy);
		add(pc, 0);
		repaint(); // Nécessaire sinon mauvais placement des cartes
	}
	
	/**
	 * Setter des décalages souhaités lors de l'ajout d'une carte.
	 * @param dx
	 * 		Décalage suivant x;
	 * @param dy
	 * 	Décalage suivant y.
	 */
	public void setDxDy(int dx, int dy){
		this.dx = dx;
		this.dy = dy;
	}

	//---------------------------------------------------------------------------------------------
	// Gestion du Drag and Drop
	//---------------------------------------------------------------------------------------------
	
	@Override
	public DataFlavor[] getTransferDataFlavors() { // Méthode non obligatoire dans notre cas.
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) { // Méthode non obligatoire dans notre cas.
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Retourne le PTasDeCartes en déplacement sous forme d'objet.
	 * Cette méthode joue le rôle de passerelle entre le conteneur du drop et l'objet en déplacement.
	 */
	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)){
			return (Object)this;
		}
		return null;
	}
}
