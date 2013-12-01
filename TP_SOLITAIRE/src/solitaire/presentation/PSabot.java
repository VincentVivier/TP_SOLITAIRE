package solitaire.presentation;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import solitaire.controleur.CCarte;
import solitaire.controleur.CSabot;
import solitaire.controleur.CTasDeCartes;

public class PSabot extends JPanel {

	private static final long serialVersionUID = 1L;
	
	CSabot c;
	RetournerTasListener rtl;
	RetournerCarteListener rcl;
	PTasDeCartes cachees;
	PTasDeCartes visibles;
	
	//Gestion DnD
	DragSource ds;
	DragSourceListener myDSL;
	DragSourceMotionListener myDSML;
	DragGestureEvent theInitialEvent;
	PTasDeCartes ptcMove;
	Point origin;
	//fin gestion Dnd

	public PSabot(CSabot c, PTasDeCartes cachees, PTasDeCartes visibles) {
		//TODO
		this.c = c;
		this.cachees = cachees;
		this.visibles = visibles;
		
		// modifications du panel cachees
		cachees.setPreferredSize(new Dimension(72, 96));
		cachees.setSize(cachees.getPreferredSize());
		
		// modifications cartes visibles
		visibles.setPreferredSize(new Dimension(122, 96));
		visibles.setSize(cachees.getPreferredSize());
		
		// ajout des tas de cartes
		add(cachees);
		add(visibles);
		
		cachees.setDxDy(0, 0);
		visibles.setDxDy(25, 0);
		
		rtl = new RetournerTasListener();
		rcl = new RetournerCarteListener();
		
		// Gestion Dnd source
		myDSL = new MyDragSourceListener() ;
		ds = new DragSource();
		ds.createDefaultDragGestureRecognizer (visibles, DnDConstants.ACTION_MOVE, new MyDragGestureListener ());
		myDSML = new MyDragSourceMotionListener();
		ds.addDragSourceMotionListener(myDSML);
		// fin Gestion DnD
	}
	
	public void activerRetournerCarte(){
		cachees.addMouseListener(rcl);
	}
	
	public void desactiverRetournerCarte(){
		cachees.removeMouseListener(rcl);
	}
	
	public void activerRetournerTas(){
		visibles.addMouseListener(rtl);
	}
	
	public void desactiverRetournerTas(){
		visibles.removeMouseListener(rtl);
	}
	
	public void effacerVisibles(){
		visibles.removeAll();
	}
	
	// gestion DnD
	public void c2p_debutDnDKO(CTasDeCartes ct){
		// TODO Ajouter quelque chose plus tard si besoin
	}
	
	public void c2p_debutDnDOK(CTasDeCartes ct){
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop, ct.getPresentation(), myDSL);
		ptcMove = ct.getPresentation();
		
		
		
		getParent().add(ct.getPresentation(), 0);
	}
	// fin gestion DnD
	
	class RetournerTasListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				c.retourner();
			} catch (Exception e1) {
				System.err.println("Tas impossible � retourner.");
				e1.printStackTrace();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
	}
	
	class RetournerCarteListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				c.retournerCarte();
			} catch (Exception e1) {
				System.err.println("Carte impossible � retourner.");
				e1.printStackTrace();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
	}
	
	// Gestion Dnd source
	class MyDragGestureListener implements DragGestureListener{

		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			theInitialEvent = dge;
			PCarte pcMove = null;
			CCarte cc = null;
			try{
				origin = dge.getDragOrigin();
				pcMove = (PCarte)visibles.getComponentAt(origin);
				cc = pcMove.getControle();
			}catch (Exception e){}
			c.p2c_debutDnd(cc);
		}
		
	}
	
	class MyDragSourceListener implements DragSourceListener{

		@Override
		public void dragEnter(DragSourceDragEvent dsde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragOver(DragSourceDragEvent dsde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dropActionChanged(DragSourceDragEvent dsde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragExit(DragSourceEvent dse) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragDropEnd(DragSourceDropEvent dsde) {
			c.p2c_dragDropEnd(dsde.getDropSuccess());
			repaint();
		}
		
	}
	
	class MyDragSourceMotionListener implements DragSourceMotionListener {

		@Override
		public void dragMouseMoved(DragSourceDragEvent dsde) {
			ptcMove.setLocation(dsde.getX()-( ptcMove.getWidth()/2),
								dsde.getY() - getRootPane().getParent().getY() - origin.y - 25);
		}
		
	}
	// fin gestion Dnd
}
