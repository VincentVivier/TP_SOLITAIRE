package solitaire.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JPanel;

import solitaire.controleur.CCarte;
import solitaire.controleur.CColonne;
import solitaire.controleur.CTasDeCartes;

public class PColonne extends JPanel {

	private static final long serialVersionUID = 1L;
	
	CColonne c;
	RetournerCarteListener rcl;
	PTasDeCartes cachees;
	PTasDeCartes visibles;
	
	//Gestion DnD source
	DragSource ds;
	DragSourceListener myDSL;
	DragSourceMotionListener myDSML;
	DragGestureEvent theInitialEvent;
	PCarte pcMove;
	Point origin;
	
	// Gestion Dnd drop
	DropTarget dt;
	DropTargetDropEvent theInitialDropEvent;
	//fin gestion Dnd
	
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
		
		// Gestion Dnd source
		myDSL = new MyDragSourceListener() ;
		ds = new DragSource();
		ds.createDefaultDragGestureRecognizer (visibles, DnDConstants.ACTION_MOVE, new MyDragGestureListener ());
		myDSML = new MyDragSourceMotionListener();
		ds.addDragSourceMotionListener(myDSML);
		
		// Gestion DnD drop
		dt = new DropTarget(visibles, new MyDropTargetListener());
		// fin Gestion DnD
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
	
	// gestion DnD source
	public void c2p_debutDnDKO(CCarte cc){
		// TODO Ajouter quelque chose plus tard si besoin
		System.out.println("tout est KOOOOOOOOO");
	}
	
	public void c2p_debutDndOK(CTasDeCartes ct){
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop, ct.getPresentation(), myDSL);
		getParent().add(ct.getPresentation(), 0);
	}
	
	class MyDragGestureListener implements DragGestureListener{

		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			theInitialEvent = dge;
			pcMove = null;
			CCarte cc = null;
			try{
				origin = dge.getDragOrigin();
				pcMove = (PCarte)visibles.getComponentAt(origin);
				cc = pcMove.getControle();
				System.out.println("Carte a deplacer : " + pcMove.toString());
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
//			System.out.println(origin.x + " : " + origin.y);
//			pcMove.setLocation((1+dsde.getX()) - getRootPane().getParent().getX() - origin.x,
//					(1+dsde.getY()) - visibles.getY() + origin.y);
			pcMove.setLocation(dsde.getX()-(pcMove.getWidth()/2),
					dsde.getY() - getRootPane().getParent().getY() - origin.y - 25);
		}
		
	}
	
	// Gestion DnD drop
	
	public void c2p_dropOK(){
		theInitialDropEvent.acceptDrop(DnDConstants.ACTION_MOVE);
		theInitialDropEvent.getDropTargetContext().dropComplete(true);
	}
	
	public void c2p_dropKO(){
		theInitialDropEvent.rejectDrop();
	}
	
	public void c2p_showEmpilable(){
		setBackground(Color.green);
	}
	
	public void c2p_showNonEmpilable(){
		setBackground(Color.red);
	}
	
	public void c2p_showNeutre(){
		setBackground(null);
	}
	
	class MyDropTargetListener implements DropTargetListener{

		PTasDeCartes ptc;
		
		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
			try {
				ptc = (PTasDeCartes) dtde.getTransferable().getTransferData(new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType));
				c.p2c_dragEnter(ptc.getControle());
				
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragExit(DropTargetEvent dte) {
			c.p2c_dragExit(ptc.getControle());
		}

		@Override
		public void drop(DropTargetDropEvent dtde) {
			theInitialDropEvent = dtde;
			c.p2c_drop(ptc.getControle());
		}	
	}
	// fin gestion DnD
}
