package solitaire.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import solitaire.controleur.CColonne;

public class PColonne extends JPanel {

	private static final long serialVersionUID = 1L;
	
	CColonne c;
	RetournerCarteListener rcl;
	PTasDeCartes cachees;
	PTasDeCartes visibles;
	
	public PColonne(CColonne c, PTasDeCartes cachees, PTasDeCartes visibles) {
		this.c = c;
		this.cachees = cachees;
		this.visibles = visibles;
		
		add(cachees);
		add(visibles);

		setPreferredSize(new Dimension(80, 600));
		setSize(getPreferredSize());
		
		cachees.setBackground(Color.green);
		visibles.setBackground(Color.yellow);
		
		cachees.setPreferredSize(new Dimension(100, 200));
		cachees.setSize(getPreferredSize());
		
		cachees.setDxDy(0, 15);
		visibles.setDxDy(0, 25);
		
		rcl = new RetournerCarteListener();
	}
	
	public void activerRetournerCarte(){
		cachees.addMouseListener(rcl);
	}
	
	public void desactiverRetournerCarte(){
		cachees.removeMouseListener(rcl);
	}

	class RetournerCarteListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				c.retournerCarte();
			} catch (Exception e1) {
				System.err.println("Tas impossible à retourner.");
				e1.printStackTrace();
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
