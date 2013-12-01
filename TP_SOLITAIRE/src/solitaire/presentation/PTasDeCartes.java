package solitaire.presentation;

import interfacesControle.ICTasDeCartes;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JPanel;

import solitaire.controleur.CTasDeCartes;

public class PTasDeCartes extends JPanel implements Transferable {

	private static final long serialVersionUID = 1L;
	
	ICTasDeCartes c;
	protected int dx, dy;
	
	public PTasDeCartes(ICTasDeCartes c) {
		this.c = c;
		setLayout(null);
	}
	
	public CTasDeCartes getControle(){
		return (CTasDeCartes)c;
	}
	
	public void depiler(PCarte pc){
		remove(pc);
		repaint();
	}
	
	public void empiler(PCarte pc){
		pc.setLocation(getComponentCount()*dx, getComponentCount()*dy);
		add(pc, 0);
		repaint(); // Nécessaire sinon mauvais placement des cartes
	}
	
	public void setDxDy(int dx, int dy){
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)){
			return (Object)this;
		}
		return null;
	}
}
