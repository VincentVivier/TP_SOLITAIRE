package solitaire.presentation;

import interfacesControle.ICTasDeCartes;

import javax.swing.JPanel;

public class PTasDeCartes extends JPanel{

	private static final long serialVersionUID = 1L;
	
	ICTasDeCartes c;
	protected int dx, dy;
	
	public PTasDeCartes(ICTasDeCartes c) {
		this.c = c;
		setLayout(null);
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
}
