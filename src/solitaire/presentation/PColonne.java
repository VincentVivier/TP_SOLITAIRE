package solitaire.presentation;

import java.awt.Color;
import java.awt.Cursor;
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
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import solitaire.controleur.CCarte;
import solitaire.controleur.CColonne;
import solitaire.controleur.CTasDeCartes;

public class PColonne extends JPanel {

	private static final long serialVersionUID = 1L;
	
	CColonne c;
	RetournerCarteListener rcl;
	PTasDeCartes cachees;
	PTasDeCartes visibles;
	Color oldColor;
	
	//Gestion DnD source
	DragSource ds;
	DragSourceListener myDSL;
	DragSourceMotionListener myDSML;
	DragGestureEvent theInitialEvent;
	PTasDeCartes ptcMove;
	Point origin;
	
	// Gestion Dnd drop
	DropTarget dt;
	DropTargetDropEvent theInitialDropEvent;
	//fin gestion Dnd
	
	public PColonne(CColonne c, PTasDeCartes cachees, PTasDeCartes visibles) {
		this.c = c;
		this.cachees = cachees;
		this.visibles = visibles;
		
		setLayout(null); // Pour pouvoir jouer avec le déplacement des panels de cartes
		setOpaque(false);
		setBorder(new javax.swing.border.BevelBorder(BevelBorder.LOWERED));
		setBackground(new Color(50, 50, 255));
		
		cachees.setDxDy(0, 15);
		visibles.setDxDy(0, 25);
		
		add(cachees);
		add(visibles, 0);

		setPreferredSize(new Dimension(72, 600));
		setSize(getPreferredSize());
		
		cachees.setBackground(Color.red);
		visibles.setBackground(Color.CYAN);
		visibles.setOpaque(false);
		cachees.setOpaque(false);
		
		visibles.setSize(72, 600);
		
		cachees.setSize(72, 200);
		
		rcl = new RetournerCarteListener();
		visibles.addMouseMotionListener(new GestionCurseur());
		
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
	
	public void setAffichage(){
		visibles.setLocation(0, cachees.getComponentCount()*cachees.dy);
	}
	
	public void activerRetournerCarte(){
		visibles.addMouseListener(rcl);
	}
	
	public void desactiverRetournerCarte(){
		visibles.removeMouseListener(rcl);
	}
	
	class RetournerCarteListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				c.retournerCarte();
				setAffichage(); // mise à jour de l'affihage
				repaint();
			} catch (Exception e1) {
				System.err.println("Tas impossible � retourner.");
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
	
	// Gestion curseur
	
	class GestionCurseur implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			c.c2p_sourisDetectee(visibles.getComponentAt(e.getPoint()));
		}
		
	}
	
	public void showCliquable(){
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void showNonCliquable(){
		setCursor(Cursor.getDefaultCursor());
	}
	
	//fin gestion curseur
	
	// gestion DnD source
	public void c2p_debutDnDKO(CCarte cc){
		System.out.println("tout est KOOOOOOOOO");
	}
	
	public void c2p_debutDndOK(CTasDeCartes ct){
		ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop, ct.getPresentation(), myDSL);
		ptcMove = ct.getPresentation();
		// Ajout du panel à déplacer
		ptcMove.setSize(72, ptcMove.getComponentCount()*25 + 71);
		getRootPane().add(ct.getPresentation(), 0);
	}
	
	class MyDragGestureListener implements DragGestureListener{

		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			theInitialEvent = dge;
			CCarte cc = null;
			try{
				origin = dge.getDragOrigin();
				PCarte pc = (PCarte)visibles.getComponentAt(origin);
				cc = pc.getControle();
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
			getRootPane().remove(ptcMove);
			getRootPane().repaint();
		}
	}
	
	class MyDragSourceMotionListener implements DragSourceMotionListener {

		@Override
		public void dragMouseMoved(DragSourceDragEvent dsde) {
			ptcMove.setLocation(dsde.getX() - getRootPane().getParent().getX() - origin.x,
					dsde.getY() - getRootPane().getParent().getY() - origin.y - 25);
			getRootPane().repaint();
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
		oldColor = visibles.getBackground();
		visibles.setBackground(Color.green);
		visibles.setOpaque(true);
	}
	
	public void c2p_showNonEmpilable(){
		oldColor = visibles.getBackground();
		visibles.setBackground(Color.red);
		visibles.setOpaque(true);
	}
	
	public void c2p_showNeutre(){
		visibles.setBackground(oldColor);
		visibles.setOpaque(false);
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
